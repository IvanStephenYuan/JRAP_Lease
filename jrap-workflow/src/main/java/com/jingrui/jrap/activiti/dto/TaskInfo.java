package com.jingrui.jrap.activiti.dto;

/**
 * Created by qiang.zeng@jingrui.com on 2017/8/9.
 */
public class TaskInfo {

    private String taskId;
    private String taskName;
    private String assigneeCode;
    private String assigneeName;

    public TaskInfo() {
    }

    public TaskInfo(String taskId, String taskName, String assigneeCode, String assigneeName) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.assigneeCode = assigneeCode;
        this.assigneeName = assigneeName;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getAssigneeCode() {
        return assigneeCode;
    }

    public void setAssigneeCode(String assigneeCode) {
        this.assigneeCode = assigneeCode;
    }

    public String getAssigneeName() {
        return assigneeName;
    }

    public void setAssigneeName(String assigneeName) {
        this.assigneeName = assigneeName;
    }
}
