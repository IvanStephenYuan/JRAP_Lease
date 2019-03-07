package com.jingrui.jrap.activiti.custom.task;

import org.activiti.engine.impl.TaskQueryImpl;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.interceptor.CommandExecutor;

/**
 * @author njq.niu@jingrui.com
 */
public class CustomTaskQuery extends TaskQueryImpl {

    public CustomTaskQuery(CommandContext commandContext) {
        super(commandContext);
    }

    public CustomTaskQuery(CommandExecutor commandExecutor) {
        super(commandExecutor);
    }


    public CustomTaskQuery(CommandExecutor commandExecutor, String databaseType) {
        super(commandExecutor, databaseType);
    }


    private String startUserName;

    public String getStartUserName() {
        return startUserName;
    }

    public void setStartUserName(String startUserName) {
        this.startUserName = startUserName;
    }

    @Override
    public String getOrderBy() {
        if (orderBy == null) {
            return super.getOrderBy();
        } else {
            return orderBy + " ,RES.CREATE_TIME_ desc";
        }
    }
}
