package com.jingrui.jrap.security.permission.service;

import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.core.ProxySelf;
import com.jingrui.jrap.system.service.IBaseService;
import com.jingrui.jrap.security.permission.dto.DataPermissionRule;

import java.util.List;

/**
 * @author jialong.zuo@jingrui.com on 2017/12/8
 */
public interface IDataPermissionRuleService extends IBaseService<DataPermissionRule>, ProxySelf<IDataPermissionRuleService> {

    /**删除规则并发送消息
     * @param dataPermissionRules 将要删除的规则
     */
    void removeRule(List<DataPermissionRule> dataPermissionRules);

    /**通过ruleDtail删除规则
     * @param dataPermissionRules 将要删除的规则
     */
    void removeRuleWithDetail(List<DataPermissionRule> dataPermissionRules);

    /** 选择未被选择的规则
     * @param dataPermissionRule 查询参数
     * @param iRequest IRequest环境
     * @param page 当前页数
     * @param pageSize 分页大小
     * @return 返回未被选中的屏蔽规则
     */
    List<DataPermissionRule> selectRuleWithoutTableSelect(DataPermissionRule dataPermissionRule, IRequest iRequest, int page, int pageSize);
}