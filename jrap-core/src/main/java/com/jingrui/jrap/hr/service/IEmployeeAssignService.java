package com.jingrui.jrap.hr.service;

import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.core.ProxySelf;
import com.jingrui.jrap.hr.dto.EmployeeAssign;
import com.jingrui.jrap.system.service.IBaseService;

import java.util.List;

/**
 * 员工岗位分配服务接口.
 *
 * @author yuliao.chen@jingrui.com
 */
public interface IEmployeeAssignService extends IBaseService<EmployeeAssign>, ProxySelf<IEmployeeAssignService> {
    /**
     * 通过员工Id查询岗位分配
     *
     * @param requestContext IRequest
     * @param employeeId     员工Id
     * @param page           页码
     * @param pageSize       页数
     * @return 员工岗位分配列表
     */
    List<EmployeeAssign> selectByEmployeeId(IRequest requestContext, Long employeeId, int page, int pageSize);

    /**
     * 通过员工Id查询公司Id.
     *
     * @param employeeId 员工Id
     * @return 公司Id
     */
    Long getCompanyByEmployeeId(Long employeeId);
}
