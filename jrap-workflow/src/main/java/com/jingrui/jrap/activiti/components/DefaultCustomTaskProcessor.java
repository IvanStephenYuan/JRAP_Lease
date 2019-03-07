package com.jingrui.jrap.activiti.components;

import com.jingrui.jrap.activiti.custom.ICustomTaskProcessor;
import com.jingrui.jrap.activiti.util.ActivitiUtils;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by Qixiangyu on 2017/4/26.
 */
@Component
public class DefaultCustomTaskProcessor implements ICustomTaskProcessor {

    @Override
    public Task processPriority(Task task) {

        Long dueTime = ActivitiUtils.secondsBetweenDate(task.getCreateTime(), task.getDueDate());
        Long interval = getDueTime(task.getCreateTime(), dueTime);
        int priority = task.getPriority();

        if (interval <= 0) {
            // 超时,设置优先级为最高
            task.setPriority(100);
        } else {
            // 距离限定时间不到3分之1
            if (interval <= (dueTime / 3)) {
                task.setPriority(90);
            } else if (interval <= (dueTime / 3 * 2)) {
                // 距离限定时间不到3分之2
                if (priority < 33) {
                    task.setPriority(50);
                } else {
                    task.setPriority(80);
                }
            }
        }

        return task;
    }

    @Override
    public Long getDueTime(Date startDate, Long dueTime) {
        // 任务创建至今的时间
        Long elapsedTime = ActivitiUtils.secondsBetweenDate(startDate, new Date());
        Long processDueTime = dueTime - elapsedTime;
        return processDueTime;
    }

    @Override
    public boolean processorContinue() {
        return true;
    }

    @Override
    public int getOrder() {
        return 999;
    }
}
