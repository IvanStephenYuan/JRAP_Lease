package com.jingrui.jrap.task.service;

import com.jingrui.jrap.account.dto.Role;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.core.ProxySelf;
import com.jingrui.jrap.system.service.IBaseService;
import com.jingrui.jrap.task.dto.TaskAssign;

import java.util.List;

/**
 * 任务授权Service.
 *
 * @author lijian.yin@jingrui.com
 * @date 2017/11/6.
 **/

public interface ITaskAssignService extends IBaseService<TaskAssign>, ProxySelf<ITaskAssignService> {

    /**
     * 查询角色列表.
     *
     * @param request   IRequest
     * @param condition 任务分配条件
     * @return 任务
     */
    List<TaskAssign> query(IRequest request, TaskAssign condition);

    /**
     * 查询未绑定的角色列表
     *
     * @param request IRequest
     * @param idList  角色ID集合
     * @return 角色集合
     */
    List<Role> queryUnbound(IRequest request, List<String> idList);

    /**
     * 查询该用户下权限下的任务Id
     *
     * @param iRequest IRequest
     * @param isAdmin  是否是管理员
     * @return 任务ID
     */
    List<Long> queryTaskId(IRequest iRequest, boolean isAdmin);

}