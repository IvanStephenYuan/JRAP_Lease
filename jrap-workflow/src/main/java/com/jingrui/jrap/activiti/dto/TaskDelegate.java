package com.jingrui.jrap.activiti.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.activiti.rest.common.util.DateToStringSerializer;

import java.util.Date;

/**
 * @author shengyang.zhou@jingrui.com
 */
public class TaskDelegate {

    private String fromUserId;

    private String fromUserName;

    @JsonSerialize(using = DateToStringSerializer.class, as = Date.class)
    private Date time;

    private String reason;

    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
