package com.jingrui.jrap.activiti.custom.process;

import org.activiti.engine.impl.HistoricProcessInstanceQueryImpl;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.interceptor.CommandExecutor;

/**
 * @author njq.niu@jingrui.com
 */
public class CustomHistoricProcessInstanceQuery extends HistoricProcessInstanceQueryImpl {

    public CustomHistoricProcessInstanceQuery(CommandContext commandContext) {
        super(commandContext);
    }

    public CustomHistoricProcessInstanceQuery(CommandExecutor commandExecutor) {
        super(commandExecutor);
    }

    private String startUserName;

    private Boolean suspended;

    private String carbonCopyUser;

    private String readFlag;

    private Boolean multiLanguageOpen;

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

    public Boolean getMultiLanguageOpen() {
        return multiLanguageOpen;
    }

    public void setMultiLanguageOpen(Boolean multiLanguageOpen) {
        this.multiLanguageOpen = multiLanguageOpen;
    }

    @Override
    public String getOrderBy() {
        if (orderBy == null) {
            return super.getOrderBy();
        } else {
            return orderBy + " ,RES.ID_ asc";
        }
    }
}
