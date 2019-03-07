/*
 * Copyright ZheJiang JingRui Co.,Ltd.
 */

package com.jingrui.jrap.core;

import org.springframework.context.ApplicationContext;

/**
 * @author shengyang.zhou@jingrui.com
 */
public interface AppContextInitListener{
    void contextInitialized(ApplicationContext applicationContext);
}
