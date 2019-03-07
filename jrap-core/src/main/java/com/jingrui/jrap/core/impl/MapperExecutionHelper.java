/*
 * #{copyright}#
 */

package com.jingrui.jrap.core.impl;

import com.jingrui.jrap.core.IRequest;

/**
 * 维护 IRequest 实例.
 *
 * @author shengyang.zhou@jingrui.com
 *
 */
@Deprecated
public final class MapperExecutionHelper {

    private static ThreadLocal<IRequest> localRequestContext = new ThreadLocal<>();

    private MapperExecutionHelper() {

    }

    /**
     * 设置 IRequest.
     * <p>
     * 不检查是否已经存在实例.(存在的话将被替换)
     *
     * @param request
     *            新的 IRequest 实例
     */
    public static void setCurrentRequest(IRequest request) {
        localRequestContext.set(request);
    }

    /**
     * 清除当前实例.
     * <p>
     * 理论上优于 setCurrentRequest(null)
     */
    public static void clearCurrentRequest() {
        localRequestContext.remove();
    }

    /**
     * 当前session信息.
     *
     * @return 当前session信息
     */
    public static IRequest getCurrentRequest() {
        return getCurrentRequest(false);
    }

    /**
     * 取得当前线程 IRequest.
     * <p>
     *
     * @param returnEmptyForNull
     *            是否在没有值的时候返回一个空的实例.<br>
     *            注意,返回的空的实例不会设置为当前实例
     * @return 当前 IRequest 实例,或者一个空的实例
     */
    public static IRequest getCurrentRequest(boolean returnEmptyForNull) {
        IRequest request = localRequestContext.get();
        if (request == null && returnEmptyForNull) {
            return new ServiceRequest();
        }
        return request;
    }
}
