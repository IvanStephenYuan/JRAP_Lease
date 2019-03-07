package com.jingrui.jrap.task.service;

import com.jingrui.jrap.core.ProxySelf;
import com.jingrui.jrap.core.annotation.StdWho;
import com.jingrui.jrap.system.service.IBaseService;
import com.jingrui.jrap.task.dto.TaskExecutionDetail;

/**
 * 任务执行记录-接口.
 *
 * @author lijian.yin@jingrui.com
 * @date 2017/11/6.
 **/

public interface ITaskExecutionDetailService extends IBaseService<TaskExecutionDetail>,
        ProxySelf<ITaskExecutionDetailService> {

    /**
     * 写入执行异常信息
     *
     * @param taskExecutionDetail 任务/任务组执行 执行记录详情
     */
    void updateStacktrace(@StdWho TaskExecutionDetail taskExecutionDetail);

    /**
     * 写入执行日志
     *
     * @param taskExecutionDetail 任务/任务组执行 执行记录详情
     */
    void updateExecuteLog(@StdWho TaskExecutionDetail taskExecutionDetail);

    /**
     * 获取执行日志
     *
     * @param taskExecutionDetail 任务/任务组执行 执行记录详情
     * @return 任务/任务组执行 执行记录详情
     */
    TaskExecutionDetail getExecutionLog(TaskExecutionDetail taskExecutionDetail);
}