package com.jingrui.jrap.security.permission.service;

import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.core.ProxySelf;
import com.jingrui.jrap.system.service.IBaseService;
import com.jingrui.jrap.security.permission.dto.DataPermissionRuleAssign;

import java.util.List;

/**
 * @author jialong.zuo@jingrui.com on 2017/12/8
 */
public interface IDataPermissionRuleAssignService extends IBaseService<DataPermissionRuleAssign>, ProxySelf<IDataPermissionRuleAssignService> {


    /** 选择规则分配
     * @param dto 查询参数
     * @param page 当前页数
     * @param pageSize 分页大小
     * @param request IRequest环境
     * @return 查询当前detail所分配的规则
     * @throws IllegalAccessException
     */
    List<DataPermissionRuleAssign> selectRuleAssign(DataPermissionRuleAssign dto, int page, int pageSize, IRequest request) throws IllegalAccessException;

    /** 删除规则分配
     * @param dataMaskRuleAssigns 将要删除的RuleAssign
     */
    void removeDataMaskRuleAssign(List<DataPermissionRuleAssign> dataMaskRuleAssigns);

    /** 更新规则分配
     * @param request IRequest环境
     * @param dataMaskRuleAssigns 将要更新的RuleAssign
     * @return 更新的RuleAssign
     */
    List<DataPermissionRuleAssign> updateDataMaskRuleAssign(IRequest request, List<DataPermissionRuleAssign> dataMaskRuleAssigns);
}