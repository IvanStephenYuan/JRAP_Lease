/*
 * Copyright ZheJiang JingRui Co.,Ltd.
 */

package com.jingrui.jrap.extensible.base;

import com.jingrui.jrap.core.IRequest;

/**
 * @author shengyang.zhou@jingrui.com
 */
public interface IServiceListener<T> {
    void beforeInsert(IRequest request, T record, ServiceListenerChain<T> chain);

    void afterInsert(IRequest request, T record, ServiceListenerChain<T> chain);

    void beforeUpdate(IRequest request, T record, ServiceListenerChain<T> chain);

    void afterUpdate(IRequest request, T record, ServiceListenerChain<T> chain);

    void beforeDelete(IRequest request, T record, ServiceListenerChain<T> chain);

    void afterDelete(IRequest request, T record, ServiceListenerChain<T> chain);
}
