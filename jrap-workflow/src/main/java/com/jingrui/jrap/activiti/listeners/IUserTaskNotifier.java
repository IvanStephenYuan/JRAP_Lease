package com.jingrui.jrap.activiti.listeners;

import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.persistence.entity.UserEntity;

/**
 * @author shengyang.zhou@jingrui.com
 */
public interface IUserTaskNotifier {

    void onTaskCreate(TaskEntity task, UserEntity userEntity);

    void onTaskCreate(TaskEntity task, GroupEntity groupEntity);
}
