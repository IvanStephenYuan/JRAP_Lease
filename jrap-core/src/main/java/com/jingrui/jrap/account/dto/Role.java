package com.jingrui.jrap.account.dto;

import com.jingrui.jrap.account.service.IRole;
import com.jingrui.jrap.core.annotation.MultiLanguage;
import com.jingrui.jrap.core.annotation.MultiLanguageField;
import com.jingrui.jrap.mybatis.annotation.Condition;
import com.jingrui.jrap.mybatis.common.query.Where;
import com.jingrui.jrap.system.dto.BaseDTO;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.Date;

/**
 * 角色DTO.
 *
 * @author shengyang.zhou@jingrui.com
 * @date 2016/6/9
 */

@MultiLanguage
@Table(name = "sys_role_b")
public class Role extends BaseDTO implements IRole {

    public static final String FIELD_ROLE_ID = "roleId";
    public static final String FIELD_ROLE_CODE = "roleCode";
    public static final String FIELD_ROLE_NAME = "roleName";
    public static final String FIELD_ROLE_DESCRIPTION = "roleDescription";
    public static final String FIELD_START_ACTIVE_DATE = "startActiveDate";
    public static final String FIELD_END_ACTIVE_DATE = "endActiveDate";
    public static final String FIELD_ENABLE_FLAG = "enableFlag";

    @Id
    @Column
    @Where
    @GeneratedValue(generator = GENERATOR_TYPE)
    private Long roleId;

    @Column
    @NotEmpty
    @Length(max = 40)
    @OrderBy("ASC")
    @Where
    private String roleCode;

    @Column
    @MultiLanguageField
    @NotEmpty
    @Length(max = 150)
    @Where
    @OrderBy
    private String roleName;

    @Column
    @MultiLanguageField
    @Length(max = 240)
    @Where
    private String roleDescription;

    @Column
    @Condition(operator = ">=")
    @OrderBy
    private Date startActiveDate;

    @Column
    @Condition(operator = "<=")
    @OrderBy
    private Date endActiveDate;

    @Column
    private String enableFlag;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = StringUtils.trim(roleCode);
    }

    public String getRoleName() {
        return roleName;
    }

    @Override
    public boolean isEnabled() {
        return YES.equals(enableFlag);
    }

    @Override
    public boolean isActive() {
        return (startActiveDate == null || startActiveDate.getTime() <= System.currentTimeMillis())
                && (endActiveDate == null || endActiveDate.getTime() >= System.currentTimeMillis());
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }

    public Date getStartActiveDate() {
        return startActiveDate;
    }

    public void setStartActiveDate(Date startActiveDate) {
        this.startActiveDate = startActiveDate;
    }

    public Date getEndActiveDate() {
        return endActiveDate;
    }

    public void setEndActiveDate(Date endActiveDate) {
        this.endActiveDate = endActiveDate;
    }

    public String getEnableFlag() {
        return enableFlag;
    }

    public void setEnableFlag(String enableFlag) {
        this.enableFlag = enableFlag;
    }
}
