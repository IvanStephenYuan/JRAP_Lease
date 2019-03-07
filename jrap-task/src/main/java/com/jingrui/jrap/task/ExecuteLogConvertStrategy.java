package com.jingrui.jrap.task;

import com.jingrui.jrap.task.dto.TaskExecution;

/**
 * @author peng.jiang@jingrui.com
 * @date 2018/1/15
 **/
public interface ExecuteLogConvertStrategy {


    /**
     * 日志输出
     * @param taskExecution
     * @return
     */
    String convertLog(TaskExecution taskExecution);
}
