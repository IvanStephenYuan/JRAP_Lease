/*
 * *
 *  @file com.maddyhome.idea.copyright.pattern.JavaCopyrightVariablesProvider$1@3196a763$
 *  @CopyRight (C) 2018 ZheJiangJingRui Co. Ltd.
 *  @brief JingRui Application Platform
 *  @author $name$
 *  @email yulong.yuan@jr-info.cn
 *  @date $date$
 * /
 */

package com.jingrui.jrap.customer.util;

import com.jingrui.jrap.customer.service.impl.CustomerTongdunServiceImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.*;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TdUtils {
    // 连接建立超时时间
    private static final int CONNECT_TIME_OUT = 1 * 1000;
    // 设置读取超时，建议设置为3000ms。若同时调用了信息核验服务，请与客户经理协商确认具体时间”
    private static final int SOCKET_TIME_OUT = 3 * 1000;
    // 连接不够用时从connection manager获取连接等待超时时间
    private static final int CONNECT_REQUST_TIME_OUT = 1 * 1000;

    private static CloseableHttpClient httpClient = null;
    private final static Object syncLock = new Object();
    private final static int RETRY_TIMES = 5;         // 最大重试次数

    private static final Log LOGGER = LogFactory.getLog(CustomerTongdunServiceImpl.class);

    /**
     * 对http请求做相关配置.
     *
     * @param httpRequestBase
     */
    private static void config(HttpRequestBase httpRequestBase) {
        // 配置超时时间
        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(CONNECT_REQUST_TIME_OUT).setConnectTimeout(CONNECT_TIME_OUT).setSocketTimeout(SOCKET_TIME_OUT).build();
        httpRequestBase.setConfig(requestConfig);
    }

    /**
     * 获取HttpClient对象
     *
     * @return
     */
    public static CloseableHttpClient getHttpClient(String url) {
        String hostname = url.split("/")[2];
        int port = 80;
        if (hostname.contains(":")) {
            String[] arr = hostname.split(":");
            hostname = arr[0];
            port = Integer.parseInt(arr[1]);
        }
        if (httpClient == null) {
            synchronized (syncLock) {
                if (httpClient == null) {
                    httpClient = createHttpClient(200, 40, 100, hostname, port);
                }
            }
        }
        return httpClient;
    }

    /**
     * 创建HttpClient对象 这里只设置一个路由.
     *
     * @return
     */
    public static CloseableHttpClient createHttpClient(int maxTotal, int maxPerRoute, int maxRoute, String hostname, int port) {
        ConnectionSocketFactory plainsf = PlainConnectionSocketFactory.getSocketFactory();
        LayeredConnectionSocketFactory sslsf = SSLConnectionSocketFactory.getSocketFactory();
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create().register("http", plainsf).register("https", sslsf).build();
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(registry);
        // 最大连接数
        cm.setMaxTotal(maxTotal);
        // 每个默认基础路由的连接数
        cm.setDefaultMaxPerRoute(maxPerRoute);
        HttpHost httpHost = new HttpHost(hostname, port);
        // 每个路由的最大连接数增加
        cm.setMaxPerRoute(new HttpRoute(httpHost), maxRoute);
        SocketConfig socketConfig = SocketConfig.custom().setSoKeepAlive(true).build();
        cm.setDefaultSocketConfig(socketConfig);

        // 请求重试处理
        HttpRequestRetryHandler httpRequestRetryHandler = new HttpRequestRetryHandler() {
            public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
                if (executionCount >= RETRY_TIMES) {               // 如果已经重试了5次，就放弃
                    return false;
                }
                if (exception instanceof NoHttpResponseException) {// 如果服务器丢掉了连接，那么就重试
                    return true;
                }
                if (exception instanceof SSLHandshakeException) {  // 不要重试SSL握手异常
                    return false;
                }
                if (exception instanceof InterruptedIOException) { // 超时
                    return false;
                }
                if (exception instanceof UnknownHostException) {   // 目标服务器不可达
                    return false;
                }
                if (exception instanceof ConnectTimeoutException) {// 连接被拒绝
                    return false;
                }
                if (exception instanceof SSLException) {           // SSL握手异常
                    return false;
                }

                HttpClientContext clientContext = HttpClientContext.adapt(context);
                HttpRequest request = clientContext.getRequest();
                // 如果请求是幂等的，就再次尝试
                if (!(request instanceof HttpEntityEnclosingRequest)) {
                    return true;
                }
                return false;
            }
        };

        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).setRetryHandler(
                httpRequestRetryHandler).build();
        return httpClient;
    }

    /**
     * 设置post请求参数.
     *
     * @param httpost
     * @param params
     */
    private static void setPostParams(HttpPost httpost, Map<String, Object> params) {
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        Set<String> keySet = params.keySet();
        for (String key : keySet) {
            nvps.add(new BasicNameValuePair(key, params.get(key).toString()));
        }
        try {
            httpost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            LOGGER.error(e);
        }
    }

    private static void closePool() {
        try {
            if (httpClient != null) {
                httpClient.close();
            }
        } catch (IOException e) {
            LOGGER.error(e);
        }
    }

    /**
     * apply request
     *
     * @param url
     * @param params
     * @return
     */
    public static String doPost(String url, Map<String, Object> params) {
        HttpPost httpPost = new HttpPost(url);
        config(httpPost);
        setPostParams(httpPost, params);
        CloseableHttpResponse response = null;
        String result = "";

        try {
            response = getHttpClient(url).execute(httpPost, HttpClientContext.create());
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, "utf-8");
            EntityUtils.consume(entity);
        } catch (Exception e) {
            LOGGER.error("[RiskServicePreloan] apply throw exception, details: " + e);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                LOGGER.error(e);
            }
        }
        return result;
    }

    public static String doGet(String urlString) {
        HttpsURLConnection conn;
        StringBuilder result = new StringBuilder();
        SSLSocketFactory ssf = (SSLSocketFactory) SSLSocketFactory.getDefault();

        try {
            URL url = new URL(urlString);
            conn = (HttpsURLConnection) url.openConnection();
            //设置https
            conn.setSSLSocketFactory(ssf);
            // 设置长链接
            conn.setRequestProperty("Connection", "Keep-Alive");
            // 设置连接超时
            conn.setConnectTimeout(1000);
            // 设置读取超时
            conn.setReadTimeout(500);
            // 提交参数
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();

            if (responseCode == 200) {
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(conn.getInputStream(), "utf-8"));
                result = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    result.append(line).append("\n");
                }
            }
        } catch (Exception e) {
            LOGGER.error("[RiskServicePreloan] query throw exception, details: " + e);
        }

        return result.toString();
    }
}
