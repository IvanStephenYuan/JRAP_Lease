package com.jingrui.jrap.security.permission.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.jingrui.jrap.core.BaseConstants;
import com.jingrui.jrap.fnd.dto.Company;
import com.jingrui.jrap.mybatis.annotation.ExtensionAttribute;
import com.jingrui.jrap.mybatis.common.query.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Table;

import com.jingrui.jrap.system.dto.BaseDTO;

import javax.persistence.Transient;
import javax.persistence.criteria.JoinType;
import javax.validation.constraints.NotNull;

/**
 * @author jialong.zuo@jingrui.com
 */
@ExtensionAttribute(disable = true)
@Table(name = "sys_permission_rule_detail")
public class DataPermissionRuleDetail extends BaseDTO {

    public static final String FIELD_DETAIL_ID = "detailId";
    public static final String FIELD_RULE_ID = "ruleId";
    public static final String FIELD_PERMISSION_FIELD_VALUE = "permissionFieldValue";
    public static final String FIELD_PERMISSION_FIELD = "permissionField";
    public static final String FIELD_PERMISSION_FIELD_SQL_VALUE = "permissionFieldSqlValue";

    @Id
    @GeneratedValue
    private Long detailId;

    //MANGE CODE
    @NotNull
    @Where
    @JoinTable(name = "fieldJoin", joinMultiLanguageTable = false, target = DataPermissionRule.class, type = JoinType.LEFT, on = {@JoinOn(joinField = DataPermissionRule.FIELD_RULE_ID)})
    private Long ruleId;

    //安全性字段值
    @Length(max = 200)
    private String permissionFieldValue;

    //安全性sql字段值
    @Length(max = 2000)
    private String permissionFieldSqlValue;

    @Transient
    @JoinLov(joinKey = FIELD_PERMISSION_FIELD_VALUE,dynamicLovColumn = FIELD_PERMISSION_FIELD)
    private String permissionFieldName;

    @Transient
    @JoinColumn(joinName = "fieldJoin", field = DataPermissionRule.FIELD_PERMISSION_FIELD)
    private String permissionField;

    @Transient
    private String ruleCode;

    public void setDetailId(Long detailId) {
        this.detailId = detailId;
    }

    public Long getDetailId() {
        return detailId;
    }

    public void setRuleId(Long ruleId) {
        this.ruleId = ruleId;
    }

    public Long getRuleId() {
        return ruleId;
    }

    public void setPermissionFieldValue(String permissionFieldValue) {
        this.permissionFieldValue = permissionFieldValue;
    }

    public String getPermissionFieldValue() {
        return permissionFieldValue;
    }

    public void setPermissionFieldSqlValue(String permissionFieldSqlValue) {
        this.permissionFieldSqlValue = permissionFieldSqlValue;
    }

    public String getPermissionFieldSqlValue() {
        return permissionFieldSqlValue;
    }

    public String getPermissionFieldName() {
        return permissionFieldName;
    }

    public void setPermissionFieldName(String permissionFieldName) {
        this.permissionFieldName = permissionFieldName;
    }

    public String getPermissionField() {
        return permissionField;
    }

    public void setPermissionField(String permissionField) {
        this.permissionField = permissionField;
    }

    public String getRuleCode() {
        return ruleCode;
    }

    public void setRuleCode(String ruleCode) {
        this.ruleCode = ruleCode;
    }
}
