package com.jingrui.jrap.security.permission.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.jingrui.jrap.mybatis.annotation.ExtensionAttribute;
import com.jingrui.jrap.mybatis.common.query.JoinLov;
import com.jingrui.jrap.mybatis.common.query.Where;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Table;

import com.jingrui.jrap.system.dto.BaseDTO;

import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author jialong.zuo@jingrui.com
 */
@ExtensionAttribute(disable = true)
@Table(name = "sys_permission_rule_assign")
public class DataPermissionRuleAssign extends BaseDTO {

    public static final String FIELD_ASSIGN_ID = "assignId";
    public static final String FIELD_DETAIL_ID = "detailId";
    public static final String FIELD_ASSIGN_FIELD = "assignField";
    public static final String FIELD_ASSIGN_FIELD_VALUE = "assignFieldValue";


    @Id
    @GeneratedValue
    private Long assignId;

    //MANGE CODE
    @NotNull
    @Where
    private Long detailId;

    //层级
    @NotEmpty
    @Length(max = 250)
    private String assignField;

    //层级值
    @NotEmpty
    @Length(max = 250)
    private String assignFieldValue;

    @Transient
    private Long ruleId;

    @Transient
    private String ruleCode;

    @Transient
    @JoinLov(joinKey = FIELD_ASSIGN_FIELD_VALUE,dynamicLovColumn = FIELD_ASSIGN_FIELD)
    private String assignFieldName;


    public void setAssignId(Long assignId) {
        this.assignId = assignId;
    }

    public Long getAssignId() {
        return assignId;
    }

    public void setDetailId(Long detailId) {
        this.detailId = detailId;
    }

    public Long getDetailId() {
        return detailId;
    }

    public void setAssignField(String assignField) {
        this.assignField = assignField;
    }

    public String getAssignField() {
        return assignField;
    }

    public void setAssignFieldValue(String assignFieldValue) {
        this.assignFieldValue = assignFieldValue;
    }

    public String getAssignFieldValue() {
        return assignFieldValue;
    }

    public Long getRuleId() {
        return ruleId;
    }

    public void setRuleId(Long ruleId) {
        this.ruleId = ruleId;
    }

    public String getRuleCode() {
        return ruleCode;
    }

    public void setRuleCode(String ruleCode) {
        this.ruleCode = ruleCode;
    }

    public String getAssignFieldName() {
        return assignFieldName;
    }

    public void setAssignFieldName(String assignFieldName) {
        this.assignFieldName = assignFieldName;
    }
}
