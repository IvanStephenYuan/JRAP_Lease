package com.jingrui.jrap.activiti.dto;


import org.activiti.rest.service.api.runtime.task.TaskActionRequest;

/**
 * @author shengyang.zhou@jingrui.com
 */
public class TaskActionRequestExt extends TaskActionRequest {

    private String comment;

    private String jumpTarget;

    private String jumpTargetName;

    private String CarbonCopyUsers;

    private String currentTaskId;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getJumpTarget() {
        return jumpTarget;
    }

    public void setJumpTarget(String jumpTarget) {
        this.jumpTarget = jumpTarget;
    }

    public String getCarbonCopyUsers() {
        return CarbonCopyUsers;
    }

    public void setCarbonCopyUsers(String carbonCopyUsers) {
        CarbonCopyUsers = carbonCopyUsers;
    }

    public String getJumpTargetName() {
        return jumpTargetName;
    }

    public void setJumpTargetName(String jumpTargetName) {
        this.jumpTargetName = jumpTargetName;
    }

    public String getCurrentTaskId() {
        return currentTaskId;
    }

    public void setCurrentTaskId(String currentTaskId) {
        this.currentTaskId = currentTaskId;
    }
}
