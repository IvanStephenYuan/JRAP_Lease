/**
 * Copyright (c) 2016. ZheJiang JingRui Company. All right reserved. Project
 * Name:hstaffParent Package Name:hstaff.core.aop Date:2016/11/21 0028 Create
 * By:xiangyu.qi@jingrui.com
 *
 */
package com.jingrui.jrap.intergration.aop;

import com.jingrui.jrap.core.interceptor.MonitorInterceptor;
import com.jingrui.jrap.intergration.annotation.JrapInbound;
import com.jingrui.jrap.intergration.beans.JrapInvokeInfo;
import com.jingrui.jrap.intergration.beans.JrapinterfaceBound;
import com.jingrui.jrap.intergration.dto.JrapInterfaceInbound;
import com.jingrui.jrap.intergration.util.JrapInvokeLogUtils;
import com.jingrui.jrap.message.IMessagePublisher;
import com.jingrui.jrap.message.components.InvokeLogManager;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

@Aspect
@Component
public class JrapInvokeExceptionAspect {


    @Autowired
    private IMessagePublisher messagePublisher;

    private static final Logger logger = LoggerFactory.getLogger(JrapInvokeExceptionAspect.class);

    /*
     * 入站请求异常AOP处理
     */
    @Before("@annotation(exceptionHander)")
    public void beforeMethod(JoinPoint joinpoint, ExceptionHandler exceptionHander) {
        // 这次请求的方法
        HandlerMethod handler = (HandlerMethod) MonitorInterceptor.REST_INVOKE_HANDLER.get();
        if(handler == null) {
            return;
        }
        Method method = handler.getMethod();
        JrapInbound hapInbound = method.getAnnotation(JrapInbound.class);
        if (hapInbound != null) {
            // 记录错误信息
            Exception e = null;
            Object[] parms = joinpoint.getArgs();
            for (Object obj : parms) {
                if (obj instanceof Exception)
                    e = (Exception) obj;
            }
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                    .getRequest();
            JrapInterfaceInbound inbound = new JrapInterfaceInbound();
            inbound.setInterfaceName(hapInbound.apiName());
            inbound.setRequestTime(new Date());
            inbound.setRequestStatus(JrapInvokeInfo.REQUEST_FAILURE);
            inbound.setResponseTime(0L);
            JrapInvokeLogUtils.processRequestInfo(inbound,request);
            JrapInvokeLogUtils.processExceptionInfo(inbound,e);
            JrapinterfaceBound bound = new JrapinterfaceBound(inbound);
            messagePublisher.message(InvokeLogManager.CHANNEL_INBOUND,bound);
        }
    }
}
