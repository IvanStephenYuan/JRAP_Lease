package com.jingrui.jrap.activiti.dto;

import com.jingrui.jrap.mybatis.annotation.ExtensionAttribute;
import com.jingrui.jrap.system.dto.BaseDTO;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

@ExtensionAttribute(disable = true)
@Table(name = "wfl_approve_chain_line")
public class ApproveChainLine extends BaseDTO {
    @Id
    @GeneratedValue
    private Long approveChainLineId;

    private Long approveChainId;

    @NotEmpty
    @Length(max = 255)
    private String name;

    @Length(max = 255)
    private String description;

    @Length(max = 40)
    private String approveType;

    @Length(max = 200)
    private String assignee;

    @Length(max = 200)
    private String assignGroup;

    @Transient
    private String assignGroupName;

    @Length(max = 255)
    private String formKey;

    @OrderBy
    private String sequence;

    @Length(max = 255)
    private String skipExpression;

    @Length(max = 1)
    private String breakOnSkip;

    @Length(max = 1)
    private String enableFlag;

    public void setApproveChainLineId(Long approveChainLineId) {
        this.approveChainLineId = approveChainLineId;
    }

    public Long getApproveChainLineId() {
        return approveChainLineId;
    }

    public void setApproveChainId(Long approveChainId) {
        this.approveChainId = approveChainId;
    }

    public Long getApproveChainId() {
        return approveChainId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setApproveType(String approveType) {
        this.approveType = approveType;
    }

    public String getApproveType() {
        return approveType;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getAssignee() {
        return assignee;
    }

    public String getAssignGroup() {
        return assignGroup;
    }

    public void setAssignGroup(String assignGroup) {
        this.assignGroup = assignGroup;
    }

    public String getAssignGroupName() {
        return assignGroupName;
    }

    public void setAssignGroupName(String assignGroupName) {
        this.assignGroupName = assignGroupName;
    }

    public String getFormKey() {
        return formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSkipExpression(String skipExpression) {
        this.skipExpression = skipExpression;
    }

    public String getSkipExpression() {
        return skipExpression;
    }

    public void setBreakOnSkip(String breakOnSkip) {
        this.breakOnSkip = breakOnSkip;
    }

    public String getBreakOnSkip() {
        return breakOnSkip;
    }

    public void setEnableFlag(String enableFlag) {
        this.enableFlag = enableFlag;
    }

    public String getEnableFlag() {
        return enableFlag;
    }

}
