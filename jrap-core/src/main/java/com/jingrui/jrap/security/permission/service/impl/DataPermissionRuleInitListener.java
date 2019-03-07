package com.jingrui.jrap.security.permission.service.impl;

import com.jingrui.jrap.core.AppContextInitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**
 * @author jialong.zuo@jingrui.com on 2017/8/30.
 */
@Service
public class DataPermissionRuleInitListener implements AppContextInitListener {
    @Autowired
    DataPermissionCacheContainer container;

    @Override
    public void contextInitialized(ApplicationContext applicationContext) {
        container.initContainer();
    }
}
