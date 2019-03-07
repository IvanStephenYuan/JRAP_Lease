package com.jingrui.jrap.task.dto;

import com.jingrui.jrap.account.dto.Role;
import com.jingrui.jrap.mybatis.annotation.Condition;
import com.jingrui.jrap.mybatis.annotation.ExtensionAttribute;
import com.jingrui.jrap.system.dto.BaseDTO;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 任务DTO.
 *
 * @author lijian.yin@jingrui.com
 * @date 2017/11/15.
 **/

@ExtensionAttribute(disable = true)
@Table(name = "sys_task_assign")
public class TaskAssign extends BaseDTO {

    public static final String FIELD_TASK_ASSIGN_ID = "taskAssignId";
    public static final String FIELD_TASK_ID = "taskId";
    public static final String FIELD_ASSIGN_ID = "assignId";
    public static final String FIELD_ASSIGN_TYPE = "assignType";
    public static final String FIELD_START_DATE = "startDate";
    public static final String FIELD_END_DATE = "endDate";

    @Id
    @GeneratedValue
    private Long taskAssignId;

    // 任务ID
    @NotNull
    private Long taskId;

    // 目标ID
    @NotNull
    private Long assignId;

    // 类型
    @NotEmpty
    @Length(max = 10)
    private String assignType;

    @Condition(operator = ">=")
    private Date startDate;

    @Condition(operator = "<=")
    private Date endDate;

    //角色信息
    @Transient
    private Role role;

    public void setTaskAssignId(Long taskAssignId) {
        this.taskAssignId = taskAssignId;
    }

    public Long getTaskAssignId() {
        return taskAssignId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setAssignId(Long assignId) {
        this.assignId = assignId;
    }

    public Long getAssignId() {
        return assignId;
    }

    public void setAssignType(String assignType) {
        this.assignType = assignType;
    }

    public String getAssignType() {
        return assignType;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
