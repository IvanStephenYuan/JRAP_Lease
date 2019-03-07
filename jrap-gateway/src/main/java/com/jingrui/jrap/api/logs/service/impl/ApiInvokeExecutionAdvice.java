package com.jingrui.jrap.api.logs.service.impl;

import com.jingrui.jrap.intergration.exception.JrapApiException;
import com.jingrui.jrap.api.ApiConstants;
import com.jingrui.jrap.api.logs.dto.ApiInvokeRecord;
import com.jingrui.jrap.api.gateway.dto.ApiServer;
import com.jingrui.jrap.api.logs.info.ApiInvokeInfo;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @author peng.jiang@jingrui.com
 * @date 2017/9/25.
 */
public class ApiInvokeExecutionAdvice implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Long startTime = System.currentTimeMillis();
        ApiInvokeRecord apiInvokeRecord = ApiInvokeInfo.API_INVOKE_RECORD.get();
        Object result ;
        Object[] args = invocation.getArguments();
        ApiServer server = (ApiServer) args[0];
        String parameter = (String) args[1];
        try {
            apiInvokeRecord.setServerCode(server.getCode());
            apiInvokeRecord.setServerName(server.getName());
            apiInvokeRecord.setInterfaceType(server.getServiceType());
            if (server.getServiceType().equals(ApiConstants.SERVER_TYPE_SOAP)){
                apiInvokeRecord.setRequestUrl((server.getDomainUrl() != null ? server.getDomainUrl() : ""));
            }else {
                apiInvokeRecord.setRequestUrl((server.getDomainUrl() != null ? server.getDomainUrl() : "")
                        + (server.getApiInterface().getInterfaceUrl() != null ? server.getApiInterface().getInterfaceUrl() : ""));
            }

            result = invocation.proceed();
            if (ApiConstants.ENABLE_FLAG_Y.equals(server.getApiInterface().getInvokeRecordDetails())){
                apiInvokeRecord.getApiInvokeRecordDetails().setRequestBodyParameter(parameter);
                apiInvokeRecord.getApiInvokeRecordDetails().setResponseContent(result.toString());
            }

        }catch (Exception e){
            e.printStackTrace();
            //进入异常，记录请求详细信息
            apiInvokeRecord.getApiInvokeRecordDetails().setRequestBodyParameter(parameter);
            if (e instanceof JrapApiException && ((JrapApiException)e).getCode().startsWith(JrapApiException.CODE_API_THIRD_REQUEST)){
                apiInvokeRecord.getApiInvokeRecordDetails().setResponseContent(((JrapApiException) e).getDescriptionKey());
            }
            throw e;
        }finally {

            apiInvokeRecord.setResponseTime(System.currentTimeMillis() - startTime);
        }

        return result;
    }

}
