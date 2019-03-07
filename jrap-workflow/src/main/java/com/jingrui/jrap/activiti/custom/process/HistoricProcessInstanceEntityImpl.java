package com.jingrui.jrap.activiti.custom.process;


/**
 * @author njq.niu@jingrui.com
 */
public class HistoricProcessInstanceEntityImpl
        extends org.activiti.engine.impl.persistence.entity.HistoricProcessInstanceEntityImpl  {

    private String taskDefKey;

    private Integer suspensionState;

    private String readFlag;

    public String getTaskDefKey() {
        return taskDefKey;
    }

    public void setTaskDefKey(String taskDefKey) {
        this.taskDefKey = taskDefKey;
    }

    public Integer getSuspensionState() {
        return suspensionState;
    }

    public void setSuspensionState(Integer suspensionState) {
        this.suspensionState = suspensionState;
    }

    public String getReadFlag() {
        return readFlag;
    }

    public void setReadFlag(String readFlag) {
        this.readFlag = readFlag;
    }
}
