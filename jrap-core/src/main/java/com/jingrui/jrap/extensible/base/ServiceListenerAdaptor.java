/*
 * Copyright ZheJiang JingRui Co.,Ltd.
 */

package com.jingrui.jrap.extensible.base;

import com.jingrui.jrap.core.IRequest;

/**
 * @author shengyang.zhou@jingrui.com
 */
public class ServiceListenerAdaptor<T> implements IServiceListener<T> {
    @Override
    public void beforeInsert(IRequest request, T record, ServiceListenerChain<T> chain) {

    }

    @Override
    public void afterInsert(IRequest request, T record, ServiceListenerChain<T> chain) {

    }

    @Override
    public void beforeUpdate(IRequest request, T record, ServiceListenerChain<T> chain) {

    }

    @Override
    public void afterUpdate(IRequest request, T record, ServiceListenerChain<T> chain) {

    }

    @Override
    public void beforeDelete(IRequest request, T record, ServiceListenerChain<T> chain) {

    }

    @Override
    public void afterDelete(IRequest request, T record, ServiceListenerChain<T> chain) {

    }
}
