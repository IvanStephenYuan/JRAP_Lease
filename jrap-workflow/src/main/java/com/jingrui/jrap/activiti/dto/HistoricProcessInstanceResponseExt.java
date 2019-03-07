package com.jingrui.jrap.activiti.dto;

import org.activiti.rest.service.api.history.HistoricProcessInstanceResponse;
import org.activiti.rest.service.api.history.HistoricTaskInstanceResponse;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author shengyang.zhou@jingrui.com
 * @author njq.niu@jingrui.com
 */
public class HistoricProcessInstanceResponseExt extends HistoricProcessInstanceResponse {
    private String processName;

    // 申请人
    private String startUserName;

    // 最后审批人
    private String lastApprover;
    // 最后审批操作
    private String lastApproveAction;

    private String lastApproverCode;

    //当前节点审批人
    private String currentApprover;

    private String formKey;

    private String taskName;

    private String taskDefKey;

    private boolean suspended;//流程是否挂起

    private boolean recall;

    private String readFlag;

    private List<TaskInfo> currentTasks;

    private List<HistoricTaskInstanceResponse> historicTaskList = new ArrayList<>();

    private String description;

    protected Date startTime;

    protected Date endTime;

    public List<HistoricTaskInstanceResponse> getHistoricTaskList() {
        return historicTaskList;
    }

    public void setHistoricTaskList(List<HistoricTaskInstanceResponse> historicTaskList) {
        this.historicTaskList = historicTaskList;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getStartUserName() {
        return startUserName;
    }

    public void setStartUserName(String startUserName) {
        this.startUserName = startUserName;
    }

    public String getLastApprover() {
        return lastApprover;
    }

    public void setLastApprover(String lastApprover) {
        this.lastApprover = lastApprover;
    }

    public String getLastApproveAction() {
        return lastApproveAction;
    }

    public void setLastApproveAction(String lastApproveAction) {
        this.lastApproveAction = lastApproveAction;
    }


    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public boolean isSuspended() {
        return suspended;
    }

    public void setSuspended(boolean suspended) {
        this.suspended = suspended;
    }

    public boolean isRecall() {
        return recall;
    }

    public void setRecall(boolean recall) {
        this.recall = recall;
    }

    public String getLastApproverCode() {
        return lastApproverCode;
    }

    public void setLastApproverCode(String lastApproverCode) {
        this.lastApproverCode = lastApproverCode;
    }

    public String getFormKey() {
        return formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getCurrentApprover() {
        return currentApprover;
    }

    public void setCurrentApprover(String currentApprover) {
        this.currentApprover = currentApprover;
    }

    public String getTaskDefKey() {
        return taskDefKey;
    }

    public void setTaskDefKey(String taskDefKey) {
        this.taskDefKey = taskDefKey;
    }

    public String getReadFlag() {
        return readFlag;
    }

    public void setReadFlag(String readFlag) {
        this.readFlag = readFlag;
    }

    public List<TaskInfo> getCurrentTasks() {
        return currentTasks;
    }

    public void setCurrentTasks(List<TaskInfo> currentTasks) {
        this.currentTasks = currentTasks;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
