package com.jingrui.jrap.intergration.ws.interceptor;

import java.io.InputStream;
import java.io.Reader;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.jingrui.jrap.intergration.beans.JrapinterfaceBound;
import com.jingrui.jrap.intergration.util.JrapInvokeLogUtils;
import com.jingrui.jrap.message.IMessagePublisher;
import com.jingrui.jrap.message.components.InvokeLogManager;
import com.jingrui.jrap.mybatis.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.common.injection.NoJSR250Annotations;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.io.CachedOutputStream;
import org.apache.cxf.io.CachedWriter;
import org.apache.cxf.io.DelegatingInputStream;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.service.model.EndpointInfo;
import org.apache.cxf.service.model.ServiceInfo;
import org.apache.cxf.transport.http.AbstractHTTPDestination;
import org.springframework.beans.factory.annotation.Autowired;

import com.jingrui.jrap.intergration.beans.JrapInvokeInfo;
import com.jingrui.jrap.intergration.dto.JrapInterfaceInbound;
import com.jingrui.jrap.intergration.dto.JrapInterfaceOutbound;
import com.jingrui.jrap.intergration.service.IJrapInterfaceInboundService;
import com.jingrui.jrap.intergration.service.IJrapInterfaceOutboundService;

/**
 * 先JrapInvokeInInterceptor，后经过JrapInvokeOutInterceptor，表明是入站请求，反之是出站请求
 * @author  xiangyu.qi@jingrui.com on 2016/12/2.
 */
@NoJSR250Annotations
public class JrapInvokeInInterceptor extends AbstractPhaseInterceptor<Message> {

    @Autowired
    private IMessagePublisher messagePublisher;

    public JrapInvokeInInterceptor() {
        super(Phase.RECEIVE);
    }

    @Override
    public void handleMessage(Message message) throws Fault {
        // 判断是否先经过JrapInvokeOutInterceptor
        JrapInterfaceOutbound outbound = (JrapInterfaceOutbound) message.getExchange().get(JrapInvokeInfo.INVOKE_INFO_OUTBOUND);
        if (outbound == null) {
            inboundInvoke(message);
        } else {
            // 出站请求，调用第三方的ws，返回的内容记录
            outboundInvoke(outbound, message);
        }
    }

    @Override
    public void handleFault(Message message) {
        JrapInvokeLogUtils.processCxfHandleFault(message,messagePublisher);
    }

    /**
     * 处理入站请求记录
     * @param message
     */
    protected void inboundInvoke(Message message) {
        JrapInterfaceInbound inbound = new JrapInterfaceInbound();
        inbound.setRequestTime(new Date());
        // 设置INBOUND信息
        message.getExchange().put(JrapInvokeInfo.INVOKE_INFO_INBOUND,inbound);
        // 记录请求信息
        logRequestInfo(inbound,message);
        // 记录请求参数
        logRequestBody(message);
    }

    /**
     * 处理出站请求记录
     * @param outbound 出站请求记录
     * @param message
     */
    protected void outboundInvoke(JrapInterfaceOutbound outbound, Message message) {
        // 记录返回参数
        Object responseCode = message.get(Message.RESPONSE_CODE);
        if (responseCode != null) {
            outbound.setResponseCode(responseCode.toString());
        }
        logRequestBody(message);
        if ("200".equalsIgnoreCase(outbound.getResponseCode())) {
            outbound.setRequestStatus(JrapInvokeInfo.REQUEST_SUCESS);
        } else {
            outbound.setRequestStatus(JrapInvokeInfo.REQUEST_FAILURE);
        }
        outbound.setResponseTime(System.currentTimeMillis() - outbound.getRequestTime().getTime());
        messagePublisher.message(InvokeLogManager.CHANNEL_OUTBOUND,new JrapinterfaceBound(outbound));
    }

    protected void logRequestBody(Message message){
        try {
            InputStream is = message.getContent(InputStream.class);
            if (is != null) {
                logInputStream(message, is);
            } else {
                Reader reader = message.getContent(Reader.class);
                if (reader != null) {
                    logReader(message, reader);
                }
            }
        } catch (Exception e) {
            throw new Fault(e);
        }
    }

    protected void logRequestInfo(JrapInterfaceInbound inbound,Message message) {
        String httpMethod = (String) message.get(Message.HTTP_REQUEST_METHOD);
        if (httpMethod != null) {
            inbound.setRequestMethod(httpMethod);
        }

        String uri = (String) message.get(Message.REQUEST_URI);
        HttpServletRequest http = (HttpServletRequest) message.get(AbstractHTTPDestination.HTTP_REQUEST);
        if (uri != null) {
            if (http.getContextPath() != null) {
                uri = uri.replaceAll(http.getContextPath(), "");
            }
            inbound.setInterfaceUrl(uri);
            String query = (String) message.get(Message.QUERY_STRING);
            if (query != null) {
                inbound.setRequestHeaderParameter(query);
            }
        }
        JrapInvokeLogUtils.processRequestInfo(inbound,http);

        // 获得serviceName
        Endpoint ep = message.getExchange().getEndpoint();
        EndpointInfo endpoint = ep.getEndpointInfo();
        ServiceInfo serviceInfo =  endpoint.getService();
        String serviceName = "webService";
        if(serviceInfo != null){
            if(serviceInfo.getName() != null && StringUtils.isNotEmpty(serviceInfo.getName().getLocalPart())) {
                serviceName = serviceInfo.getName().getLocalPart();
            }
        }
        inbound.setInterfaceName(serviceName);
    }

    protected void logInputStream(Message message, InputStream is) throws Exception {

        CachedOutputStream bos = new CachedOutputStream(JrapInvokeOutInterceptor.DEFAULT_THRESHOLD);
        // use the appropriate input stream and restore it later
        InputStream bis = is instanceof DelegatingInputStream ? ((DelegatingInputStream) is).getInputStream() : is;
        // we can stream the rest
        IOUtils.copy(bis, bos);
        bos.flush();
        bis = bos.getInputStream();
        JrapInterfaceOutbound outbound = (JrapInterfaceOutbound) message.getExchange().get(JrapInvokeInfo.INVOKE_INFO_OUTBOUND);
        JrapInterfaceInbound inbound = (JrapInterfaceInbound) message.getExchange().get(JrapInvokeInfo.INVOKE_INFO_INBOUND);
        if (inbound != null) {
            inbound.setRequestBodyParameter(IOUtils.toString(bos.getInputStream()));
        } else if (outbound != null) {
            outbound.setResponseContent(IOUtils.toString(bos.getInputStream()));
        }
        // restore the delegating input stream or the input stream
        if (is instanceof DelegatingInputStream) {
            ((DelegatingInputStream) is).setInputStream(bis);
        } else {
            message.setContent(InputStream.class, bis);
        }
        bos.close();
    }

    protected void logReader(Message message, Reader reader) throws Exception {

        CachedWriter writer = new CachedWriter();
        IOUtils.copyAndCloseInput(reader, writer);
        message.setContent(Reader.class, writer.getReader());
        JrapInterfaceOutbound outbound = (JrapInterfaceOutbound) message.getExchange().get(JrapInvokeInfo.INVOKE_INFO_OUTBOUND);
        JrapInterfaceInbound inbound = (JrapInterfaceInbound) message.getExchange().get(JrapInvokeInfo.INVOKE_INFO_INBOUND);
        if (outbound == null) {
            inbound.setRequestBodyParameter(IOUtils.toString(writer.getReader()));
        } else {
            outbound.setResponseContent(IOUtils.toString(writer.getReader()));
        }
    }

}
