package com.jingrui.jrap.core.components;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @Author jialong.zuo@jingrui.com on 2017/11/1.
 */
@Component
public class ApplicationContextHelper implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextHelper.applicationContext =applicationContext;
    }

    public static ApplicationContext getApplicationContext(){
        return applicationContext;
    }
}
