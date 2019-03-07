package com.jingrui.jrap.api.gateway.service.impl;

import com.jingrui.jrap.api.gateway.service.IApiInvokeService;
import com.jingrui.jrap.core.AppContextInitListener;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取IApiInvokeService bean.
 *
 * @author peng.jiang@jingrui.com
 * @date 2017/9/28.
 **/

@Service
public class ApiInvokeServiceManager implements AppContextInitListener {

    private List<IApiInvokeService> invokeServices;

    @Override
    public void contextInitialized(ApplicationContext applicationContext) {
        invokeServices = new ArrayList<>(applicationContext.getBeansOfType(IApiInvokeService.class).values());
    }

    public List<IApiInvokeService> getInvokeServices() {
        return invokeServices;
    }

}
