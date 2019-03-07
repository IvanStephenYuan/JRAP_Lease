package com.jingrui.jrap.intergration.util;

import com.google.common.base.Throwables;
import com.jingrui.jrap.core.components.UserLoginInfoCollection;
import com.jingrui.jrap.intergration.beans.JrapInvokeInfo;
import com.jingrui.jrap.intergration.beans.JrapinterfaceBound;
import com.jingrui.jrap.intergration.controllers.JrapInvokeRequestBodyAdvice;
import com.jingrui.jrap.intergration.dto.JrapInterfaceInbound;
import com.jingrui.jrap.intergration.dto.JrapInterfaceOutbound;
import com.jingrui.jrap.message.IMessagePublisher;
import com.jingrui.jrap.message.components.InvokeLogManager;
import org.apache.cxf.message.Message;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Qixiangyu on 2017/2/21.
 */
public class JrapInvokeLogUtils {


    /**
     *  截取root cause
     *  @return
     */
    public static String getRootCauseStackTrace(Throwable throwable){
        // 获取异常堆栈
        Throwable t = Throwables.getRootCause(throwable);
        String stackTrace = Throwables.getStackTraceAsString(t);
        return stackTrace;
    }

    public static void processRequestInfo(JrapInterfaceInbound inbound, HttpServletRequest request){
        inbound.setUserAgent(request.getHeader("User-Agent"));
        if (inbound.getRequestMethod() == null)
            inbound.setRequestMethod(request.getMethod());
        if (inbound.getInterfaceUrl() == null)
            inbound.setInterfaceUrl(request.getServletPath());
        if (inbound.getRequestHeaderParameter() == null)
            inbound.setRequestHeaderParameter(request.getQueryString());
        if (inbound.getRequestBodyParameter() == null)
            inbound.setRequestBodyParameter(JrapInvokeRequestBodyAdvice.getAndRemoveBody());
        inbound.setIp(UserLoginInfoCollection.getIpAddress(request));
    }

    public static void processExceptionInfo(JrapInterfaceInbound inbound,Throwable throwable){
        if (throwable != null) {
            // 获取异常堆栈
            inbound.setStackTrace(JrapInvokeLogUtils.getRootCauseStackTrace(throwable));
            inbound.setRequestStatus(JrapInvokeInfo.REQUEST_FAILURE);
        }
    }

    public static void processExceptionInfo(JrapInterfaceOutbound outbound, Throwable throwable){
        if (throwable != null) {
            // 获取异常堆栈
            outbound.setStackTrace(JrapInvokeLogUtils.getRootCauseStackTrace(throwable));
            outbound.setRequestStatus(JrapInvokeInfo.REQUEST_FAILURE);
        }
    }


    /**
     * 用与CXF Interceptor
     * this function should be write on another class
     * @param message
     */
   public static void processCxfHandleFault(Message message, IMessagePublisher messagePublisher){
       JrapInterfaceOutbound outbound = (JrapInterfaceOutbound) message.getExchange().get(JrapInvokeInfo.INVOKE_INFO_OUTBOUND);
       JrapInterfaceInbound inbound = (JrapInterfaceInbound) message.getExchange().get(JrapInvokeInfo.INVOKE_INFO_INBOUND);
       Exception fault = message.getContent(Exception.class);
       if (inbound != null) {
           inbound.setRequestStatus(JrapInvokeInfo.REQUEST_FAILURE);
           inbound.setResponseTime(System.currentTimeMillis() - inbound.getRequestTime().getTime());
           JrapInvokeLogUtils.processExceptionInfo(inbound,fault);
           messagePublisher.message(InvokeLogManager.CHANNEL_INBOUND,new JrapinterfaceBound(inbound));
       } else if (outbound != null) {
           JrapInvokeLogUtils.processExceptionInfo(outbound,fault);
           outbound.setResponseTime(System.currentTimeMillis() - outbound.getRequestTime().getTime());
           messagePublisher.message(InvokeLogManager.CHANNEL_OUTBOUND,new JrapinterfaceBound(outbound));
       }
   }

}
