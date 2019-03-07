/**
 * Copyright (c) 2016. ZheJiang JingRui Company. All right reserved. Project
 * Name:hstaffParent Package Name:hstaff.core.aop Date:2016/11/21 0028 Create
 * By:xiangyu.qi@jingrui.com
 *
 */
package com.jingrui.jrap.intergration.aop;

import java.lang.reflect.Method;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.jingrui.jrap.core.AppContextInitListener;
import com.jingrui.jrap.intergration.annotation.JrapInbound;
import com.jingrui.jrap.intergration.annotation.JrapOutbound;
import com.jingrui.jrap.intergration.beans.JrapinterfaceBound;
import com.jingrui.jrap.intergration.util.JrapInvokeLogUtils;
import com.jingrui.jrap.message.IMessagePublisher;
import com.jingrui.jrap.message.components.InvokeLogManager;
import net.sf.json.JSONObject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jingrui.jrap.intergration.beans.JrapInvokeInfo;
import com.jingrui.jrap.intergration.dto.JrapInterfaceInbound;
import com.jingrui.jrap.intergration.dto.JrapInterfaceOutbound;
import com.jingrui.jrap.system.dto.ResponseData;

@Aspect
@Component
public class JrapInvokeAspect implements AppContextInitListener{

    private static final Logger logger = LoggerFactory.getLogger(JrapInvokeAspect.class);

    @Autowired
    private IMessagePublisher messagePublisher;

//    @Autowired
//    private IJrapInterfaceHeaderService headerService;

    @Autowired
    private ObjectMapper objectMapper;

    private Class interfaceHeaderServiceClazz;

    private Class interfaceHeaderClazz;

    private Object interfaceHeaderServiceImpl;

    @Override
    public void contextInitialized(ApplicationContext applicationContext) {
        try{
            interfaceHeaderServiceClazz = Class.forName("com.jingrui.jrap.intergration.service.IJrapInterfaceHeaderService");
            if (interfaceHeaderServiceClazz != null){
                interfaceHeaderClazz = Class.forName("com.jingrui.jrap.intergration.dto.JrapInterfaceHeader");
                interfaceHeaderServiceImpl = applicationContext.getParentBeanFactory().getBean("jrapInterfaceHeaderServiceImpl",interfaceHeaderServiceClazz);
            }
        }catch (Exception e){
            logger.debug("interface module class not found");
        }
    }



    @Pointcut("@annotation(com.jingrui.jrap.intergration.annotation.JrapOutbound)")
    public void outboundAspect() {
    }

    /*
     * 出站请求AOP处理
     */
    @Around("@annotation(bound)")
    public Object aroundMethod(ProceedingJoinPoint pjd,JrapOutbound bound) throws Throwable {

        Long startTime = System.currentTimeMillis();
        //JrapInvokeInfo.REQUEST_START_TIME.set(startTime);
        Object result = null;
        Throwable throwable = null;
       /* HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();*/
        JrapInterfaceOutbound outbound = new JrapInterfaceOutbound();
        JrapInvokeInfo.OUTBOUND.set(outbound);
        try {
            outbound.setRequestTime(new Date());
            Object[] args = pjd.getArgs();
            // 处理请求参数
            String sysName = null;
            String apiName = null;
            for (Object obj : args) {
                if (obj instanceof HttpServletRequest) {
                    sysName = ((HttpServletRequest)obj).getParameter("sysName");
                    apiName = ((HttpServletRequest)obj).getParameter("apiName");
                }
            }
            if(sysName == null && apiName ==null){
                sysName = bound.sysName();
                apiName = bound.apiName();
            }
            outbound.setInterfaceName(sysName + "-" + apiName);

            //interface 模块调用记录
            if (interfaceHeaderServiceClazz != null && interfaceHeaderClazz != null && interfaceHeaderServiceImpl != null){
                Method getHeaderAndLineMethod = interfaceHeaderServiceClazz.getMethod("getHeaderAndLine",String.class,String.class);
                Object hapInterfaceHeaderObject = getHeaderAndLineMethod.invoke(interfaceHeaderServiceImpl,sysName, apiName);
                if (hapInterfaceHeaderObject != null) {
                    Method getDomainUrl = interfaceHeaderClazz.getMethod("getDomainUrl");
                    Method getIftUrl = interfaceHeaderClazz.getMethod("getIftUrl");
                    outbound.setInterfaceUrl(getDomainUrl.invoke(hapInterfaceHeaderObject).toString() + getIftUrl.invoke(hapInterfaceHeaderObject).toString());
                } else {
                    outbound.setInterfaceUrl(" ");
                }
            }


            result = pjd.proceed();
            if (JrapInvokeInfo.OUTBOUND_REQUEST_PARAMETER.get() != null) {
                outbound.setRequestParameter(JrapInvokeInfo.OUTBOUND_REQUEST_PARAMETER.get());
            }
            if (JrapInvokeInfo.HTTP_RESPONSE_CODE.get() != null)
                outbound.setResponseCode(JrapInvokeInfo.HTTP_RESPONSE_CODE.get().toString());
            // 请求成功
            if(JrapInvokeInfo.OUTBOUND_RESPONSE_DATA.get() != null) {
                outbound.setResponseContent(JrapInvokeInfo.OUTBOUND_RESPONSE_DATA.get());
            }else if(result != null){
                outbound.setResponseContent(result.toString());
            }
            outbound.setRequestStatus(JrapInvokeInfo.REQUEST_SUCESS);

            // 如果同时监听inbound outbound 异常只会被捕捉一次，设置异常
            JrapInterfaceInbound inbound = JrapInvokeInfo.INBOUND.get();
            if (inbound != null) {
                if (inbound.getStackTrace() != null) {
                    outbound.setStackTrace(inbound.getStackTrace());
                    outbound.setRequestStatus(JrapInvokeInfo.REQUEST_FAILURE);
                }
                JrapInvokeInfo.INBOUND.remove();
            }

        } catch (Throwable e) {
            throwable = e;
            result = new JSONObject();
            ((JSONObject) result).put("error",e.getMessage());
        } finally {
            outbound.setResponseTime(System.currentTimeMillis()-startTime);
            JrapInvokeLogUtils.processExceptionInfo(outbound,throwable);
            JrapinterfaceBound hapinterfaceBound = new JrapinterfaceBound(outbound);
            messagePublisher.message(InvokeLogManager.CHANNEL_OUTBOUND,hapinterfaceBound);
            //outboundService.outboundInvoke(outbound, throwable);
            JrapInvokeInfo.clearOutboundInfo();
        }
        return result;

    }

    /*
     * 入站请求AOP处理
     */
    @Around("@annotation(bound)")
    public Object inaroundMethod(ProceedingJoinPoint pjd, JrapInbound bound) throws Throwable {
        Long startTime = System.currentTimeMillis();

        Object result = null;
        Throwable throwable = null;
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        JrapInterfaceInbound inbound = new JrapInterfaceInbound();
        JrapInvokeInfo.INBOUND.set(inbound);
        JrapInvokeInfo.TOKEN_TASK_COUNT.set(5);
        try {
            inbound.setRequestTime(new Date());
            inbound.setInterfaceName(bound.apiName());
            result = pjd.proceed();
            if (result != null) {
                String content = "";
                if (result instanceof ResponseData) {
                    content = objectMapper.writeValueAsString(result);

                } else if (result instanceof ModelAndView) {
                    content = result.toString();
                } else if (result instanceof String) {
                    content = (String) result;
                } else {
                    content = result.toString();
                }
                inbound.setResponseContent(content);
            }
            inbound.setRequestStatus(JrapInvokeInfo.REQUEST_SUCESS);
            JrapInterfaceOutbound outbound = JrapInvokeInfo.OUTBOUND.get();
            // 如果同时监听inbound outbound 异常只会被捕捉一次，设置异常
            if (outbound != null) {
                if (outbound.getStackTrace() != null) {
                    inbound.setStackTrace(outbound.getStackTrace());
                    inbound.setRequestStatus(JrapInvokeInfo.REQUEST_FAILURE);
                }
                JrapInvokeInfo.OUTBOUND.remove();
            }

        } catch (Throwable e) {
            throwable = e;
            //是否是拦截的JrapApiController
            if(interfaceHeaderServiceClazz != null && pjd.getTarget().getClass()
                    .getName().equalsIgnoreCase("com.jingrui.jrap.intergration.controllers.JrapApiController")) {
                result = new JSONObject();
                ((JSONObject) result).put("error", e.getMessage());
            }
        } finally {
            Long endTime = System.currentTimeMillis();
            inbound.setResponseTime(endTime - startTime);
            // 处理一些请求信息
            JrapInvokeLogUtils.processRequestInfo(inbound,request);
            JrapInvokeLogUtils.processExceptionInfo(inbound,throwable);
            JrapinterfaceBound hapinterfaceBound = new JrapinterfaceBound(inbound);
            messagePublisher.message(InvokeLogManager.CHANNEL_INBOUND,hapinterfaceBound);
            JrapInvokeInfo.TOKEN_TASK_COUNT.remove();
        }
        return result;
    }

}
