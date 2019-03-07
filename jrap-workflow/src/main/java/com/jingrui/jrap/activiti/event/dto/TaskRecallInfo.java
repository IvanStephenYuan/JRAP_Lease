package com.jingrui.jrap.activiti.event.dto;

import org.activiti.engine.runtime.ProcessInstance;

import java.util.Map;

/**
 * @author xiangyu.qi@jingrui.com on 2017/11/7.
 */
public class TaskRecallInfo {

    private Map<String, Object> variables;

    private String processInstanceId;

    private String processDefinitionKey;

    private String recallUser;

    private String businessKey;

    private boolean isStartRecall = false;

    public TaskRecallInfo(ProcessInstance processInstance, Map<String, Object> variables, String recallUser) {
        this.variables = variables;
        this.processInstanceId = processInstance.getProcessInstanceId();
        this.processDefinitionKey = processInstance.getProcessDefinitionKey();
        this.businessKey = processInstance.getBusinessKey();
        this.recallUser = recallUser;
    }

    public TaskRecallInfo(ProcessInstance processInstance, Map<String, Object> variables, String recallUser, boolean isStartRecall) {
        this(processInstance, variables, recallUser);
        this.isStartRecall = isStartRecall;
    }

    public Map<String, Object> getVariables() {
        return variables;
    }

    public void setVariables(Map<String, Object> variables) {
        this.variables = variables;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getProcessDefinitionKey() {
        return processDefinitionKey;
    }

    public void setProcessDefinitionKey(String processDefinitionKey) {
        this.processDefinitionKey = processDefinitionKey;
    }

    public String getRecallUser() {
        return recallUser;
    }

    public void setRecallUser(String recallUser) {
        this.recallUser = recallUser;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public boolean isStartRecall() {
        return isStartRecall;
    }

    public void setStartRecall(boolean startRecall) {
        isStartRecall = startRecall;
    }
}
