/*
 * #{copyright}#
 */
package com.jingrui.jrap.job.dto;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Id;

/**
 *
 * @author shengyang.zhou@jingrui.com
 */
public class CronTriggerDto {
    @Id
    private String schedName;
    @Id
    private String triggerName;
    @Id
    private String triggerGroup;

    @NotEmpty
    private String cronExpression;

    private String timeZoneId;

    public String getSchedName() {
        return schedName;
    }

    public void setSchedName(String schedName) {
        this.schedName = schedName;
    }

    public String getTriggerName() {
        return triggerName;
    }

    public void setTriggerName(String triggerName) {
        this.triggerName = triggerName;
    }

    public String getTriggerGroup() {
        return triggerGroup;
    }

    public void setTriggerGroup(String triggerGroup) {
        this.triggerGroup = triggerGroup;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = StringUtils.trim(cronExpression);
    }

    public String getTimeZoneId() {
        return timeZoneId;
    }

    public void setTimeZoneId(String timeZoneId) {
        this.timeZoneId = StringUtils.trim(timeZoneId);
    }
}