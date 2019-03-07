package com.jingrui.jrap.message.components;

import com.jingrui.jrap.message.IMessageConsumer;
import com.jingrui.jrap.message.TopicMonitor;
import com.jingrui.jrap.task.dto.TaskExecutionDetail;
import com.jingrui.jrap.task.service.ITaskExecutionDetailService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author peng.jiang@jingrui.com on 2017/9/23.
 */
@Component
@TopicMonitor(channel = {TaskExecuteLogManager.TASK_EXECUTE_LOG })
public class TaskExecuteLogManager implements IMessageConsumer<TaskExecutionDetail> , InitializingBean {

    public static final String TASK_EXECUTE_LOG = "task_execute_log";

    @Autowired
    private ITaskExecutionDetailService taskExecutionDetailService;

    @Override
    public void onMessage(TaskExecutionDetail message, String pattern) {
        taskExecutionDetailService.updateExecuteLog(message);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
    }
}
