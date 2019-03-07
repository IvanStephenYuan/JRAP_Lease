package com.jingrui.jrap.activiti.custom.task;

import org.activiti.rest.service.api.runtime.task.TaskQueryRequest;

/**
 * @author njq.niu@jingrui.com
 */
public class CustomTaskQueryRequest extends TaskQueryRequest {

    private String startUserName;

    public String getStartUserName() {
        return startUserName;
    }

    public void setStartUserName(String startUserName) {
        this.startUserName = startUserName;
    }
}
