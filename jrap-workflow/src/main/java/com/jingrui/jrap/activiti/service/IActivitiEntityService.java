package com.jingrui.jrap.activiti.service;

import org.activiti.engine.impl.persistence.entity.UserEntity;

import java.util.List;

/**
 * @author shengyang.zhou@jingrui.com
 */
public interface IActivitiEntityService {

    /**
     * 查找申请者主管（上级）
     *
     * @param employeeCode 员工姓名
     * @return
     */
    String getDirector(String employeeCode);

    /**
     * 查找申请者部门领导
     *
     * @param employeeCode 员工姓名
     * @return
     */
    String getDeptDirector(String employeeCode);

    boolean isMemberOfGroup(String userId, String groupId);

    String getName(String userId);

    String getEmail(String userId);

    UserEntity getEmployee(String employeeCode);

    String getGroupName(String code);

    String getEmployeeCode(String employeeCode);

    /**
     * 查找部门员工
     *
     * @param positionCode 部门code
     * @return
     */
    List<String> getPositionEmp(String positionCode);

    /**
     * 查找角色下所有员工
     *
     * @param roleCode 角色code
     * @return
     */
    List<String> getRoleEmp(String roleCode);
}