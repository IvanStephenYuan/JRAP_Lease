package com.jingrui.jrap.system.service;

import com.jingrui.jrap.core.ProxySelf;

import javax.servlet.http.HttpServletRequest;

/**
 * @author njq.niu@jingrui.com
 * @date 2016年3月7日
 */
public interface IAccessService extends ProxySelf<IAccessService> {

    /**
     * 判断是否有权限访问.
     *
     * @param accessCode 授权编码
     * @return 是否有权访问
     */
    boolean access(String accessCode);

    /**
     * 获取权限组件授权数据.
     *
     * @return 权限组件授权数据的JSON字符串
     */
    String accessData();

    /**
     * 是否具有维护权限.
     *
     * @return 是否具有权限
     */
    boolean accessMaintain();

    /**
     * 是否有范文功能的权限.
     *
     * @param functionCode 功能编码
     * @return 是否具有权限
     */
    boolean accessFunction(String functionCode);

    /**
     * 设置request.
     *
     * @param request 请求上下文
     */
    void setRequest(HttpServletRequest request);
}
