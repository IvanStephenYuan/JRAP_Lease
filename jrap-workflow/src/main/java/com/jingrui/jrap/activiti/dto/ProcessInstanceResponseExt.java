package com.jingrui.jrap.activiti.dto;

import org.activiti.rest.service.api.runtime.process.ProcessInstanceResponse;

import java.util.Date;

/**
 * @author shengyang.zhou@jingrui.com
 */
public class ProcessInstanceResponseExt extends ProcessInstanceResponse {

    private Date startTime;
    private String startUserId;
    private String startUserName;
    private String processDefinitionName;

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getStartUserId() {
        return startUserId;
    }

    public void setStartUserId(String startUserId) {
        this.startUserId = startUserId;
    }

    public String getStartUserName() {
        return startUserName;
    }

    public void setStartUserName(String startUserName) {
        this.startUserName = startUserName;
    }

    public String getProcessDefinitionName() {
        return processDefinitionName;
    }

    public void setProcessDefinitionName(String processDefinitionName) {
        this.processDefinitionName = processDefinitionName;
    }
}
