package com.jingrui.jrap.hr.mapper;

import com.jingrui.jrap.hr.dto.EmployeeAssign;
import com.jingrui.jrap.mybatis.common.Mapper;

import java.util.List;

/**
 * 员工岗位分配Mapper.
 *
 * @author shengyang.zhou@jingrui.com
 */
public interface EmployeeAssignMapper extends Mapper<EmployeeAssign> {
    /**
     * 通过员工Id查询岗位分配.
     *
     * @param employeeId 员工Id
     * @return 员工岗位分配列表
     */
    List<EmployeeAssign> selectByEmployeeId(Long employeeId);

    /**
     * 通过员工Id删除岗位分配.
     *
     * @param employeeId 员工Id
     * @return int
     */
    int deleteByEmployeeId(Long employeeId);

    /**
     * 通过岗位Id删除岗位分配
     *
     * @param positionId 岗位Id
     * @return int
     */
    int deleteByPositionId(Long positionId);

    /**
     * 通过员工Id查询公司Id.
     *
     * @param employeeId 员工Id
     * @return 公司Id
     */
    Long getCompanyByEmployeeId(Long employeeId);
}
