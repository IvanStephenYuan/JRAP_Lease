package com.jingrui.jrap.function.dto;

import com.jingrui.jrap.system.dto.BaseDTO;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 角色功能DTO.
 *
 * @author wuyichu
 */
@Table(name = "sys_role_function")
public class RoleFunction extends BaseDTO {

    private static final long serialVersionUID = 688371423171208115L;
    public static final String FIELD_SRF_ID = "srfId";
    public static final String FIELD_FUNCTION_ID = "functionId";
    public static final String FIELD_ROLE_ID = "roleId";

    @Id
    @Column
    @GeneratedValue(generator = GENERATOR_TYPE)
    private Long srfId;
    @Column
    private Long functionId;
    @Column
    private Long roleId;

    public Long getFunctionId() {
        return functionId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public Long getSrfId() {
        return srfId;
    }

    public void setFunctionId(Long functionId) {
        this.functionId = functionId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public void setSrfId(Long srfId) {
        this.srfId = srfId;
    }

}