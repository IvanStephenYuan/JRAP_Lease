/*
 * #{copyright}#
 */

package com.jingrui.jrap.job.dto;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotEmpty;

import com.jingrui.jrap.core.annotation.Children;

/**
 * @author shengyang.zhou@jingrui.com
 */
public class JobCreateDto extends TriggerDto {

//    @Size(min = 20, max = 40)
    @NotEmpty
    private String jobClassName;

    private String cronExpression;
    private Date start;

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    
    private Date end;

    private String repeatCount;
    private String repeatInterval;
    @Children
    private List<JobData> jobDatas;

    public String getJobClassName() {
        return jobClassName;
    }

    public void setJobClassName(String jobClassName) {
        this.jobClassName = StringUtils.trim(jobClassName);
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public String getRepeatCount() {
        return repeatCount;
    }

    public void setRepeatCount(String repeatCount) {
        this.repeatCount = repeatCount;
    }

    public String getRepeatInterval() {
        return repeatInterval;
    }

    public void setRepeatInterval(String repeatInterval) {
        this.repeatInterval = repeatInterval;
    }

    public List<JobData> getJobDatas() {
        return jobDatas;
    }

    public void setJobDatas(List<JobData> jobDatas) {
        this.jobDatas = jobDatas;
    }
}
