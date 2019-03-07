package com.jingrui.jrap.activiti.custom.process;

import org.activiti.rest.service.api.history.HistoricProcessInstanceQueryRequest;

/**
 * @author njq.niu@jingrui.com
 */
public class CustomHistoricProcessInstanceQueryRequest extends HistoricProcessInstanceQueryRequest {

    private String processDefinitionNameLike;
    private String startUserName;
    private Boolean suspended;
    private String carbonCopyUser;
    private String readFlag;

    public String getProcessDefinitionNameLike() {
        return processDefinitionNameLike;
    }

    public void setProcessDefinitionNameLike(String processDefinitionNameLike) {
        this.processDefinitionNameLike = processDefinitionNameLike;
    }

    public String getStartUserName() {
        return startUserName;
    }

    public void setStartUserName(String startUserName) {
        this.startUserName = startUserName;
    }

    public Boolean isSuspended() {
        return suspended;
    }

    public void setSuspended(Boolean suspended) {
        this.suspended = suspended;
    }

    public String getCarbonCopyUser() {
        return carbonCopyUser;
    }

    public void setCarbonCopyUser(String carbonCopyUser) {
        this.carbonCopyUser = carbonCopyUser;
    }

    public String getReadFlag() {
        return readFlag;
    }

    public void setReadFlag(String readFlag) {
        this.readFlag = readFlag;
    }
}
