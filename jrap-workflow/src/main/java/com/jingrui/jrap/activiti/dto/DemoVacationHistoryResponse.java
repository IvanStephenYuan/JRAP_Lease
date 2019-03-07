package com.jingrui.jrap.activiti.dto;

import org.activiti.engine.history.HistoricProcessInstance;

import java.util.Date;
import java.util.Map;

/**
 * Created by jialong.zuo@jingrui.com on 2017/3/31.
 */
public class DemoVacationHistoryResponse implements HistoricProcessInstance {

    private String id;

    private String businessKey;

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getBusinessKey() {
        return this.businessKey;
    }

    @Override
    public String getProcessDefinitionId() {
        return null;
    }

    @Override
    public String getProcessDefinitionName() {
        return null;
    }

    @Override
    public String getProcessDefinitionKey() {
        return null;
    }

    @Override
    public Integer getProcessDefinitionVersion() {
        return null;
    }

    @Override
    public String getDeploymentId() {
        return null;
    }

    @Override
    public Date getStartTime() {
        return null;
    }

    @Override
    public Date getEndTime() {
        return null;
    }

    @Override
    public Long getDurationInMillis() {
        return null;
    }

    @Override
    public String getEndActivityId() {
        return null;
    }

    @Override
    public String getStartUserId() {
        return null;
    }

    @Override
    public String getStartActivityId() {
        return null;
    }

    @Override
    public String getDeleteReason() {
        return null;
    }

    @Override
    public String getSuperProcessInstanceId() {
        return null;
    }

    @Override
    public String getTenantId() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public Map<String, Object> getProcessVariables() {
        return null;
    }
}
