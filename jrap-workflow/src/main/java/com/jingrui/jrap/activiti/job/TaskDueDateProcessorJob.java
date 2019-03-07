package com.jingrui.jrap.activiti.job;

import com.jingrui.jrap.activiti.custom.ICustomTaskProcessor;
import com.jingrui.jrap.activiti.mapper.TaskMapper;
import com.jingrui.jrap.job.AbstractJob;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


/**
 * Created by Qixiangyu on 2017/4/27.
 */

public class TaskDueDateProcessorJob extends AbstractJob {

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private ApplicationContext applicationContext;


    @Override
    public void safeExecute(JobExecutionContext context) throws Exception {

        List<ICustomTaskProcessor> taskProcessors = new ArrayList<>();
        Map<String, ICustomTaskProcessor> listeners = applicationContext.getBeansOfType(ICustomTaskProcessor.class);
        taskProcessors.addAll(listeners.values());
        Collections.sort(taskProcessors);

        List<Task> lists = taskMapper.selectTaskDueDateNotNull();
        for (Task task : lists) {
            for (ICustomTaskProcessor processor : taskProcessors) {
                task = processor.processPriority(task);
                if (!processor.processorContinue()) {
                    break;
                }
            }
            taskService.saveTask(task);
        }
    }

}

