package com.jingrui.jrap.task.dto;

import java.util.Date;
import java.util.List;
import javax.persistence.*;

import com.jingrui.jrap.core.annotation.MultiLanguage;
import com.jingrui.jrap.core.annotation.MultiLanguageField;
import com.jingrui.jrap.mybatis.annotation.Condition;
import com.jingrui.jrap.mybatis.annotation.ExtensionAttribute;
import com.jingrui.jrap.system.dto.BaseDTO;
import com.jingrui.jrap.system.dto.ParameterConfig;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 任务/任务组 DTO.
 *
 * @author lijian.yin@jingrui.com
 * @date 2017/11/15.
 **/

@ExtensionAttribute(disable = true)
@MultiLanguage
@Table(name = "sys_task_detail_b")
public class TaskDetail extends BaseDTO {

    public static final String FIELD_TASK_ID = "taskId";
    public static final String FIELD_CODE = "code";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_TYPE = "type";
    public static final String FIELD_TASK_CLASS = "taskClass";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_IDS = "ids";
    public static final String FIELD_START_DATE = "startDate";
    public static final String FIELD_END_DATE = "endDate";

    @Id
    @GeneratedValue
    private Long taskId;

    // 代码
    @NotEmpty
    @Condition(operator = LIKE)
    @Length(max = 200)
    @OrderBy
    private String code;

    // 名称
    @MultiLanguageField
    @NotEmpty
    @Condition(operator = LIKE)
    @Length(max = 200)
    private String name;

    // 类型
    @NotEmpty
    @Length(max = 10)
    @OrderBy
    private String type;

    // 类名
    @Length(max = 255)
    private String taskClass;

    // 描述
    @MultiLanguageField
    @Length(max = 255)
    private String description;

    // 子任务id
    @Length(max = 1000)
    private String ids;

    @OrderBy
    private Date startDate;

    @OrderBy
    private Date endDate;

    // 任务组子任务
    @Transient
    private List<TaskDetail> childrenTasks;

    @Transient
    private List<ParameterConfig> parameterConfigs;

    @Transient
    private int order;

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public String getTaskClass() {
        return taskClass;
    }

    public void setTaskClass(String taskClass) {
        this.taskClass = taskClass;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getIds() {
        return ids;
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

    public List<TaskDetail> getChildrenTasks() {
        return childrenTasks;
    }

    public void setChildrenTasks(List<TaskDetail> childrenTasks) {
        this.childrenTasks = childrenTasks;
    }

    public List<ParameterConfig> getParameterConfigs() {
        return parameterConfigs;
    }

    public void setParameterConfigs(List<ParameterConfig> parameterConfigs) {
        this.parameterConfigs = parameterConfigs;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

}
