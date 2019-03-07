package com.jingrui.jrap.message.components;

import com.jingrui.jrap.api.application.dto.ApiApplication;
import com.jingrui.jrap.api.application.mapper.ApiApplicationMapper;
import com.jingrui.jrap.api.logs.InvokeApiStrategy;
import com.jingrui.jrap.api.logs.dto.ApiInvokeRecord;
import com.jingrui.jrap.message.QueueMonitor;
import com.jingrui.jrap.message.IMessageConsumer;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * api 消息接收
 * @author peng.jiang@jingrui.com
 * @date 017/9/23.
 */
@Component
@QueueMonitor(queue = InvokeApiManager.API_INVOKE)
public class InvokeApiManager implements IMessageConsumer<ApiInvokeRecord> , InitializingBean {

    public static final String API_INVOKE = "api_invoke";

    private InvokeApiStrategy invokeApiStrategy;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private ApiApplicationMapper apiApplicationMapper;


    @Value("${api.invoke.logStrategy.class:com.jingrui.jrap.api.logs.components.DefaultInvokeApiStrategy}")
    private String logStrategyClass;

    @Override
    public void onMessage(ApiInvokeRecord message, String pattern) {
        ApiApplication apiApplication = new ApiApplication();
        apiApplication.setClientId(message.getClientId());
        List<ApiApplication> list = apiApplicationMapper.selectApplications(apiApplication);
        if (list != null && !list.isEmpty()){
            message.setApplicationCode(list.get(0).getCode());
        }
        invokeApiStrategy.saveApiInvokeRecord(message);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        invokeApiStrategy = (InvokeApiStrategy) Class.forName(logStrategyClass).newInstance();
        applicationContext.getAutowireCapableBeanFactory().autowireBean(invokeApiStrategy);
    }

    public InvokeApiStrategy getInvokeApiStrategy(){
        return invokeApiStrategy;
    }

}
