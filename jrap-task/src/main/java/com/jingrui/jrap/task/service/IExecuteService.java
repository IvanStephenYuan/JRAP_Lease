package com.jingrui.jrap.task.service;

import com.jingrui.jrap.task.info.TaskDataInfo;

/**
 * 任务执行处理-接口.
 *
 * @author lijian.yin@jingrui.com
 * @date 2017/11/6.
 **/

public interface IExecuteService {

    /**
     * 任务执行
     *
     * @param taskData  数据传输类-任务/任务组
     */
    void taskExecute(TaskDataInfo taskData);

}
