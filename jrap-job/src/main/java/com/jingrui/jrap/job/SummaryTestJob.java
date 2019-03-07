/*
 * #{copyright}#
 */

package com.jingrui.jrap.job;

import java.util.Date;

import org.quartz.JobExecutionContext;

/**
 * @author liyan.shi@jingrui.com
 */
public class SummaryTestJob extends AbstractJob {

    @Override
    public void safeExecute(JobExecutionContext context) {

        this.setExecutionSummary("Finish at " + new Date());
    }

    @Override
    public boolean isRefireImmediatelyWhenException() {
        return false;
    }

}