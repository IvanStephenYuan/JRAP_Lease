/*
 * Copyright ZheJiang JingRui Co.,Ltd.
 */

package com.jingrui.jrap.testext.service.impl;

import org.springframework.stereotype.Component;

import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.core.annotation.ServiceListener;
import com.jingrui.jrap.extensible.base.ServiceListenerAdaptor;
import com.jingrui.jrap.extensible.base.ServiceListenerChain;
import com.jingrui.jrap.testext.dto.UserDemo;
import com.jingrui.jrap.testext.dto.UserDemoExt;
import com.jingrui.jrap.testext.dto.UserDemoExt2;

/**
 * @author shengyang.zhou@jingrui.com
 */
@Component
@ServiceListener(target = UserDemoServiceImpl.class)
public class UserDemoServiceListener extends ServiceListenerAdaptor<UserDemo> {

    @Override
    public void beforeUpdate(IRequest request, UserDemo record, ServiceListenerChain<UserDemo> chain) {
        System.out.println(getClass().getSimpleName() + "::beforeUpdate");
        System.out.println("extension attribute: userPhone:" + ((UserDemoExt) record).getUserPhone());
        System.out.println("extension attribute: endActiveTime:" + ((UserDemoExt2) record).getEndActiveTime());
        chain.beforeUpdate(request, record);
    }

    @Override
    public void afterUpdate(IRequest request, UserDemo record, ServiceListenerChain<UserDemo> chain) {
        System.out.println(getClass().getSimpleName() + "::afterUpdate");
        chain.afterUpdate(request, record);
    }
}
