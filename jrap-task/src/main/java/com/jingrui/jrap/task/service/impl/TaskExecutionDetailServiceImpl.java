package com.jingrui.jrap.task.service.impl;

import com.jingrui.jrap.core.annotation.StdWho;
import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import com.jingrui.jrap.task.dto.TaskExecutionDetail;
import com.jingrui.jrap.task.mapper.TaskExecutionDetailMapper;
import com.jingrui.jrap.task.service.ITaskExecutionDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 任务/任务组执行记录详情 Service - 实现类.
 *
 * @author peng.jiang@jingrui.com
 * @date 2017/11/16.
 **/

@Service
public class TaskExecutionDetailServiceImpl extends BaseServiceImpl<TaskExecutionDetail> implements ITaskExecutionDetailService {

    @Autowired
    private TaskExecutionDetailMapper taskExecutionDetailMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void updateStacktrace(@StdWho TaskExecutionDetail taskExecutionDetail) {
        taskExecutionDetailMapper.updateStacktrace(taskExecutionDetail);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateExecuteLog(@StdWho TaskExecutionDetail taskExecutionDetail) {
        taskExecutionDetailMapper.updateExecuteLog(taskExecutionDetail);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public TaskExecutionDetail getExecutionLog(TaskExecutionDetail taskExecutionDetail) {
        return taskExecutionDetailMapper.getExecutionLog(taskExecutionDetail);
    }
}