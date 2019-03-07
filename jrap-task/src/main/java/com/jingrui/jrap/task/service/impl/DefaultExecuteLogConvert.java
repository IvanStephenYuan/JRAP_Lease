package com.jingrui.jrap.task.service.impl;

import com.jingrui.jrap.task.ExecuteLogConvertStrategy;
import com.jingrui.jrap.task.dto.TaskExecution;

import java.text.SimpleDateFormat;

/**
 * @author peng.jiang@jingrui.com
 * @date 2018/1/15
 **/
public class DefaultExecuteLogConvert implements ExecuteLogConvertStrategy{

    @Override
    public String convertLog(TaskExecution taskExecution) {
        StringBuffer s = new StringBuffer();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        s.append("任务执行基本信息:\r\n");
        s.append("执行编号:"+taskExecution.getExecutionNumber()+"\r\n");
        s.append("执行状态:"+taskExecution.getStatus()+"\r\n");
        s.append("任务代码:"+taskExecution.getTaskDetail().getCode()+"\r\n");
        s.append("任务名称:"+taskExecution.getTaskDetail().getName()+"\r\n");
        s.append("任务类名:"+taskExecution.getTaskDetail().getTaskClass()+"\r\n");
        s.append("任务描述:"+taskExecution.getTaskDetail().getDescription()+"\r\n");
        if (taskExecution.getStartTime() != null){
            s.append("开始时间:"+ formatter.format(taskExecution.getStartTime()) +"\r\n");
        }else {
            s.append("开始时间:\r\n");
        }
        if (taskExecution.getEndTime() != null){
            s.append("结束时间:"+ formatter.format(taskExecution.getEndTime()) +"\r\n");
        }else {
            s.append("结束时间:\r\n");
        }
        s.append("提交人:"+taskExecution.getUserId()+"\r\n");
        if (taskExecution.getLastExecuteDate() != null){
            s.append("上次执行时间:"+ formatter.format(taskExecution.getLastExecuteDate()) +"\r\n");
        }else {
            s.append("上次执行时间:\r\n");
        }
        if (taskExecution.getExecutionDescription() != null){
            s.append("执行描述:"+taskExecution.getExecutionDescription()+"\r\n");
        }else {
            s.append("执行描述:\r\n");
        }

        if (taskExecution.getTaskExecutionDetail().getParameter() != null){
            s.append("\r\n参数:\r\n");
            s.append(taskExecution.getTaskExecutionDetail().getParameter());
        }

        if (taskExecution.getTaskExecutionDetail().getStacktrace() != null){
            s.append("\r\n\r\n异常信息:\r\n");
            s.append(taskExecution.getTaskExecutionDetail().getStacktrace());
        }

        s.append("\r\n\r\n======================================================================");
        if (taskExecution.getTaskExecutionDetail().getExecutionLog() != null){
            s.append("\r\n\r\n执行日志:\r\n");
            s.append(taskExecution.getTaskExecutionDetail().getExecutionLog());
        }
        return s.toString();
    }
}
