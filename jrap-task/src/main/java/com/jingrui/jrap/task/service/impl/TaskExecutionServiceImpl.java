package com.jingrui.jrap.task.service.impl;

import com.github.pagehelper.PageHelper;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.message.IMessagePublisher;
import com.jingrui.jrap.message.components.TaskCancel;
import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import com.jingrui.jrap.task.ExecuteLogConvertStrategy;
import com.jingrui.jrap.task.TaskConstants;
import com.jingrui.jrap.task.dto.TaskExecution;
import com.jingrui.jrap.task.mapper.TaskExecutionMapper;
import com.jingrui.jrap.task.service.ITaskExecutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 任务执行记录service - 实现类.
 *
 * @author lijian.yin@jingrui.com
 */

@Service
public class TaskExecutionServiceImpl extends BaseServiceImpl<TaskExecution>
        implements ITaskExecutionService {

    @Autowired
    private TaskExecutionMapper taskExecutionMapper;

    @Autowired
    private IMessagePublisher messagePublisher;

    @Value("${task.execute.logConvert.class:com.jingrui.jrap.task.service.impl.DefaultExecuteLogConvert}")
    private String logConvertClass;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public List<TaskExecution> queryExecutions(IRequest iRequest, TaskExecution dto, boolean isAdmin, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return taskExecutionMapper.queryExecutions(dto, isAdmin);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public List<TaskExecution> queryExecutionGroup(IRequest iRequest, TaskExecution taskExecution) {
        return taskExecutionMapper.queryExecutionGroup(taskExecution);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public List<TaskExecution> queryExecutionDetail(IRequest iRequest, TaskExecution taskExecution) {
        return taskExecutionMapper.queryExecutionDetail(taskExecution);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertExecution(IRequest iRequest, TaskExecution taskExecution) {
        taskExecution.setUserId(iRequest.getUserId());
        taskExecution.setStatus(TaskConstants.EXECUTION_READY);
        taskExecutionMapper.insertSelective(taskExecution);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void updateStatus(Long executionId, String status) {
        taskExecutionMapper.updateStatus(executionId, status);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void batchUpdateStatus(Long executionId, String befStatus, String aftStatus) {
        taskExecutionMapper.batchUpdateStatus(executionId, befStatus, aftStatus);
    }

    @Override
    public boolean cancelExecute(TaskExecution dto) {
        messagePublisher.publish(TaskCancel.TASK_CANCEL, dto);
        return true;
    }

    @Override
    public String generateString (TaskExecution taskExecution){
        String logString = "";
        try {
            ExecuteLogConvertStrategy executeLogConvertStrategy = (ExecuteLogConvertStrategy) Class.forName(logConvertClass).newInstance();
            logString = executeLogConvertStrategy.convertLog(taskExecution);
        }catch (Exception e){
            e.printStackTrace();
        }
        return logString;

    }

}