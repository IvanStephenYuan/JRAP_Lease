package com.jingrui.jrap.api.logs.service.impl;

import com.google.common.base.Throwables;
import com.jingrui.jrap.api.gateway.service.IApiServerService;
import com.jingrui.jrap.api.logs.dto.ApiResponseData;
import com.jingrui.jrap.cache.CacheManager;
import com.jingrui.jrap.core.components.UserLoginInfoCollection;
import com.jingrui.jrap.core.util.ExceptionUtil;
import com.jingrui.jrap.intergration.controllers.JrapInvokeRequestBodyAdvice;
import com.jingrui.jrap.intergration.exception.JrapApiException;
import com.jingrui.jrap.message.IMessagePublisher;
import com.jingrui.jrap.message.components.InvokeApiManager;
import com.jingrui.jrap.api.ApiConstants;
import com.jingrui.jrap.api.gateway.dto.ApiServer;
import com.jingrui.jrap.security.oauth.dto.Oauth2ClientDetails;
import net.sf.json.JSONObject;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import com.jingrui.jrap.api.logs.dto.ApiInvokeRecord;
import com.jingrui.jrap.api.logs.info.ApiInvokeInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author peng.jiang@jingrui.com
 * @date 2017/9/25.
 */
public class ApiRequestExecutionAdvice implements MethodInterceptor {

    @Autowired
    private IMessagePublisher messagePublisher;

    @Autowired
    private IApiServerService serverService;

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Long startTime = System.currentTimeMillis();
        ApiResponseData apiResponseData = new ApiResponseData();
        Throwable throwable = null;
        ApiInvokeRecord apiInvokeRecord = new ApiInvokeRecord();
        ApiInvokeInfo.API_INVOKE_RECORD.set(apiInvokeRecord);

        ApiServer server = null;
        try {

            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            OAuth2Authentication authentication = (OAuth2Authentication) SecurityContextHolder.getContext().getAuthentication();

            String cilentId = authentication.getOAuth2Request().getClientId();

            /**
             * 记录请求基本信息
             */
            apiInvokeRecord.setInvokeId(UUID.randomUUID().toString());
            apiInvokeRecord.setRequestTime(new Date());
            apiInvokeRecord.setClientId(cilentId);
            apiInvokeRecord.setClientId(cilentId);
            apiInvokeRecord.setApiUrl(StringUtils.substringAfter(request.getRequestURI(), request.getContextPath()));
            apiInvokeRecord.setRequestMethod(request.getMethod());
            apiInvokeRecord.setIp(UserLoginInfoCollection.getIpAddress(request));
            apiInvokeRecord.setUserAgent(request.getHeader("User-Agent"));
            apiInvokeRecord.setReferer(StringUtils.abbreviate(request.getHeader("Referer"), 240));

            server = getServer(invocation);

            apiResponseData = (ApiResponseData) invocation.proceed();

            apiResponseData.setCode(ApiConstants.RESPONSEDATA_CODE_SUCCESS);

        } catch (Exception e) {
            throwable = e;
            apiResponseData.setSuccess(false);
            apiResponseData.setMessage(Throwables.getRootCause(throwable).getMessage().replaceAll("\"",""));
            if (e instanceof JrapApiException){
                apiResponseData.setCode(((JrapApiException) e).getCode());
            }else {
                apiResponseData.setCode(JrapApiException.CODE_API_SYSTEM_EXCEPTION);
            }
            apiInvokeRecord.getApiInvokeRecordDetails().setApiRequestBodyParameter( JrapInvokeRequestBodyAdvice.getAndRemoveBody());
            apiInvokeRecord.getApiInvokeRecordDetails().setApiResponseContent(JSONObject.fromObject(apiResponseData).toString());

        } finally {
            apiResponseData.setRequestId(apiInvokeRecord.getInvokeId());
            apiInvokeRecord.setResponseStatus(ApiInvokeInfo.RESPONSE_SUCESS);

            if (server != null && ApiConstants.ENABLE_FLAG_Y.equals(server.getApiInterface().getInvokeRecordDetails())){
                apiInvokeRecord.getApiInvokeRecordDetails().setApiRequestBodyParameter( JrapInvokeRequestBodyAdvice.getAndRemoveBody());
                apiInvokeRecord.getApiInvokeRecordDetails().setApiResponseContent(JSONObject.fromObject(apiResponseData).toString());
            }

            apiInvokeRecord.setApiResponseTime(System.currentTimeMillis()-startTime);

            processExceptionInfo(apiInvokeRecord,throwable);
            messagePublisher.message(InvokeApiManager.API_INVOKE,apiInvokeRecord);
            ApiInvokeInfo.clear();
        }
        return apiResponseData;
    }

    /**
     * 查找服务信息
     * @param invocation
     * @throws Exception
     */
    private ApiServer getServer(MethodInvocation invocation) throws Exception{
        Object[] args = invocation.getArguments();
        String serverUrl = (String) args[1];
        String interfaceUrl = (String) args[2];
        return serverService.getByMappingUrl(serverUrl,interfaceUrl);
    }

    public static void processExceptionInfo(ApiInvokeRecord accessRecord, Throwable throwable){
        if (throwable != null) {
            // 获取异常堆栈
            accessRecord.getApiInvokeRecordDetails().setStacktrace(ExceptionUtil.getRootCauseStackTrace(throwable));
            accessRecord.setResponseStatus(ApiInvokeInfo.RESPONSE_FAILURE);
        }
    }

}
