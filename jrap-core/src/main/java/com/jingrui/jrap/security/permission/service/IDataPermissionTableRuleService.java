package com.jingrui.jrap.security.permission.service;

import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.core.ProxySelf;
import com.jingrui.jrap.system.service.IBaseService;
import com.jingrui.jrap.security.permission.dto.DataPermissionTableRule;

import java.util.List;

/**
 * @author jialong.zuo@jingrui.com on 2017/12/8
 */
public interface IDataPermissionTableRuleService extends IBaseService<DataPermissionTableRule>, ProxySelf<IDataPermissionTableRuleService> {

    /**删除表规则
     * @param list 将要删除的规则
     */
    void removeRule(List<DataPermissionTableRule> list);

    /**更新分配表规则
     * @param request IRequest环境
     * @param list 将要更新的规则
     * @return 更新过后的规则
     */
    List<DataPermissionTableRule> updateRule(IRequest request, List<DataPermissionTableRule> list);
}