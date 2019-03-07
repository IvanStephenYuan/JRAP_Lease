package com.jingrui.jrap.api.gateway.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.pagehelper.StringUtil;
import com.jingrui.jrap.api.ApiConstants;
import com.jingrui.jrap.api.gateway.dto.ApiServer;
import com.jingrui.jrap.api.gateway.service.IApiInvokeService;
import com.jingrui.jrap.core.ProxySelf;
import com.jingrui.jrap.intergration.beans.JrapTransferDataMapper;
import com.jingrui.jrap.intergration.exception.JrapApiException;
import com.jingrui.jrap.intergration.util.JSONAndMap;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * rest服务 访问映射 Service - 实现类.
 *
 * @author xiangyu.qi@jingrui.com
 * @date 2017/9/25.
 */

@Service
public class ApiRestInvokeServiceImpl implements IApiInvokeService, ProxySelf<IApiInvokeService> {

    /**
     * 匹配 {**}.
     */
    private static final Pattern pattern = Pattern.compile("\\{[A-z]{0,}\\}");
    private static final String CONTENT_TPY_XML = "xml";

    private static final Logger logger = LoggerFactory.getLogger(ApiRestInvokeServiceImpl.class);

    @Autowired
    HttpRequestAuthorization httpRequestAuthorization;

    @Override
    public String serverType() {
        return ApiConstants.SERVER_TYPE_REST;
    }

    @Override
    public Object invoke(ApiServer server, JSONObject inbound) throws Exception {
        logger.info("inbound:{}", inbound);
        // 获取请求参数
        String params = getRequestParams(server, inbound);
        // http请求 返回数据
        String responseData = self().apiInvoke(server, params);
        responseData = responseData.replaceAll("null", "\"\"");
        try {
            return StringUtils.isEmpty(responseData) ? responseData : JSONObject.fromObject(responseData);
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
            return responseData;
        }
    }

    @Override
    public String apiInvoke(ApiServer server, String params) throws Exception {
        // 获取请求方式
        CloseableHttpClient client = HttpClients.createDefault();
        String responseData;
        //1 get httpRequest
        HttpRequestBase httpRequest = getHttpRequest(server, params);
        //2 send
        HttpResponse response = client.execute(httpRequest);
        //3 deal data
        JrapTransferDataMapper mapper = getJrapTransferDataMapper(server);
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == ApiConstants.HTTP_RESPONSE_CODE_401
                && ApiConstants.AUTH_TYPE_OAUTH2.equalsIgnoreCase(server.getAuthType())) {
            // reSetToken(httpRequest, server);
            httpRequestAuthorization.updateToken(httpRequest, server);
            response = client.execute(httpRequest);
        }
        statusCode = response.getStatusLine().getStatusCode();
        HttpEntity entity = response.getEntity();
        responseData = EntityUtils.toString(entity, ApiConstants.ENCODE_UTF8);
        if (statusCode != ApiConstants.HTTP_RESPONSE_CODE_200) {
            throw new JrapApiException(JrapApiException.CODE_API_THIRD_REQUEST + "_" + statusCode,
                    responseData);
        }
        if (null != mapper) {
            responseData = mapper.responseDataMap(responseData);
        } else {
            if (responseData.length() <= 2) {
                return "";
            }
            Header[] head = response.getHeaders(HTTP.CONTENT_TYPE);
            String contentType = head[0].getValue();
            if (contentType.contains(CONTENT_TPY_XML)) {
                responseData = responseDataMap(responseData);
            }
        }
        return responseData;
    }


    /**
     * 获取数据格式转换Mapper.
     *
     * @param server 服务
     * @return 数据格式转换Mapper
     */
    private JrapTransferDataMapper getJrapTransferDataMapper(ApiServer server) {
        // 如果用户定义了包装类，那么就需要将inbound进行包装，本类一般都是客户化开发
        JrapTransferDataMapper mapper = null;
        if (StringUtil.isNotEmpty(server.getApiInterface().getMappingClass())) {
            ClassLoader cl = Thread.currentThread().getContextClassLoader();
            try {
                Class c = cl.loadClass(server.getApiInterface().getMappingClass());
                mapper = (JrapTransferDataMapper) c.newInstance();
            } catch (Exception e) {
                logger.error("IllegalAccessException:" + e.getMessage());
            }
        }
        return mapper;
    }

    /**
     * 获取请求参数
     *
     * @param server  ApiServer
     * @param inbound 参数
     * @return 请求参数
     */
    private String getRequestParams(ApiServer server, JSONObject inbound) {
        String params = "";
        String method = server.getApiInterface().getRequestMethod();
        // 处理url
        String url = server.getDomainUrl()
                + server.getApiInterface().getInterfaceUrl();
        // {}
        url = convertUrl(url, inbound);
        // 获取url ? 参数
        // get delete 拼接?
        String key;
        String value;
        if (null != inbound) {
            // delete get url拼接数据
            if (ApiConstants.REQUEST_DELETE.equalsIgnoreCase(method)
                    || ApiConstants.REQUEST_GET.equalsIgnoreCase(method)) {
                params += "?";
                Iterator iterator = inbound.keys();
                while (iterator.hasNext()) {
                    key = (String) iterator.next();
                    value = inbound.getString(key);
                    params += key + "=" + value + "&";
                }
                params = params.substring(0, params.length() - 1);
            } else {
                // 其他请求数据
                // 请求类型默认为json 若没有json，则使用第一个类型
                String contentType = ApiConstants.CONTENY_TYPE_JSON;
                String head = server.getApiInterface().getRequestHead();
                JSONArray jsonArray = JSONArray.fromObject(head);

                for (int i = 0, j = jsonArray.size(); i < j; i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    contentType = jsonObject.get(HTTP.CONTENT_TYPE).toString();
                }
                // 转换request data 格式
                JrapTransferDataMapper mapper = getJrapTransferDataMapper(server);
                if (null != mapper) {
                    params += mapper.requestDataMap(inbound);
                } else {
                    params += inbound.toString();
                    if (ApiConstants.CONTENT_TYPE_FORM.equalsIgnoreCase(contentType)) {
                        params += requestDataMap(inbound);
                    }
                }
            }
        }
        server.setMappingUrl(url);
        return params;

    }

    /**
     * 获取请求request
     *
     * @param server ApiServer
     * @param params 参数
     * @return HTTP
     * @throws UnsupportedEncodingException 编码不支持异常
     */
    private HttpRequestBase getHttpRequest(ApiServer server, String params)
            throws UnsupportedEncodingException {

        StringEntity entity = null;
        HttpRequestBase httpRequest = null;
        String method = server.getApiInterface().getRequestMethod();
        String url = server.getMappingUrl();
        if (!StringUtils.isEmpty(params)) {
            if (ApiConstants.REQUEST_DELETE.equalsIgnoreCase(method)
                    || ApiConstants.REQUEST_GET.equalsIgnoreCase(method)) {
                url += params;
            } else {
                entity = new StringEntity(params, ApiConstants.ENCODE_UTF8);
            }

        }
        // 根据method选取request
        switch (method.toUpperCase()) {
            case ApiConstants.REQUEST_GET:
                httpRequest = new HttpGet(url);
                break;
            case ApiConstants.REQUEST_POST:
                HttpPost post = new HttpPost(url);
                if (null != entity) {
                    post.setEntity(entity);
                }
                httpRequest = post;
                break;
            case ApiConstants.REQUEST_DELETE:
                httpRequest = new HttpDelete(url);
                break;
            case ApiConstants.REQUEST_PUT:
                HttpPut put = new HttpPut(url);
                if (null != entity) {
                    put.setEntity(entity);
                }
                httpRequest = put;
                break;
            default:
                break;
        }
        // 设置参数
        httpRequestAuthorization.setHttpRequestAuthorization(httpRequest, server, false);
        httpRequest.setHeader("charset", ApiConstants.ENCODE_UTF8);
        // 请求类型默认为application/json 否则使用第一个类型
        httpRequest.setHeader(HTTP.CONTENT_TYPE, convertData(server));
        return httpRequest;
    }

    /**
     * 转换请求路径中的参数.
     *
     * @param url     url
     * @param inbound 数据
     * @return 路径中的参数
     */
    private String convertUrl(String url, JSONObject inbound) {
        Matcher matcher = pattern.matcher(url);
        String str = null;
        String key = null;
        while (matcher.find()) {
            str = matcher.group();
            key = str.substring(1, str.length() - 1);
            Object obj = inbound.get(key);
            if (null != obj) {
                url = url.replace(str, obj.toString());
                inbound.remove(key);
            }
        }
        return url;
    }

    /**
     * 设置token（从缓存中读取token）
     *
     * @param http   HTTP
     * @param server server
     */
    private void setToken(HttpRequestBase http, ApiServer server) {
        String basicBase64;
        if (ApiConstants.AUTH_TYPE_BASIC.equalsIgnoreCase(server.getAuthType())) {
            String e1 = server.getAuthUsername() + ":" + server.getAuthPassword();
            basicBase64 = new String(Base64.encodeBase64(e1.getBytes()));
            http.setHeader("Authorization", "Basic " + basicBase64);
        } else if (ApiConstants.AUTH_TYPE_OAUTH2.equalsIgnoreCase(server.getAuthType())) {
            String accessToken = httpRequestAuthorization.getToken(server);
            // 获取token失败
            if (com.jingrui.jrap.mybatis.util.StringUtil.isEmpty(accessToken)) {
                logger.error("get access_token failure,check your config");
                throw new RuntimeException("get access_token failure,check your config");
            }
            http.setHeader("Authorization", "Bearer " + accessToken);
        }
    }

    /**
     * 重新设置token（更新缓存中的token，再读取）
     *
     * @param http   HTTP
     * @param server server
     */
    private void reSetToken(HttpRequestBase http, ApiServer server) {
        // 更新token 重新请求 此时token失效
        httpRequestAuthorization.updateToken(server);
        setToken(http, server);
    }

    /**
     * 默认请求数据格式转换.
     *
     * @param params 请求数据
     * @return 请求数据
     */
    private String requestDataMap(JSONObject params) {
        String resultData = "";
        if (params != null && params.size() > 0) {
            Iterator iterator = params.keys();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                try {
                    resultData += key + "=" + URLEncoder.encode(params.get(key).toString(),
                            ApiConstants.ENCODE_UTF8) + "&";
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

        }
        return resultData.substring(0, resultData.length() - 1);
    }

    /**
     * 默认返回数据格式转换XML
     *
     * @param params 返回数据
     * @return 格式化后数据
     */
    public String responseDataMap(String params) {
        Map<String, Object> map;
        try {
            map = JSONAndMap.xml2map(params);
        } catch (Exception e) {
            return params;
        }
        JSONObject jsonObject = new JSONObject();
        if (map != null && map.size() > 0) {
            jsonObject = JSONObject.fromObject(map);
        }
        return jsonObject.toString();
    }

    /**
     * 获取contentType.
     *
     * @param server server
     * @return contentType or ""
     */
    private String convertData(ApiServer server) {
        String head = server.getApiInterface().getRequestHead();
        if (StringUtils.isNotEmpty(head)) {
            JSONObject jsonObject = JSONArray.fromObject(head).getJSONObject(0);
            return jsonObject.get(HTTP.CONTENT_TYPE).toString();
        }
        return "";
    }
}
