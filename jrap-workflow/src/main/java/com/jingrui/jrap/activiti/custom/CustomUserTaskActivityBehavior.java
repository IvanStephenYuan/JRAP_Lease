/*
 * Copyright ZheJiang JingRui Co.,Ltd.
 */

package com.jingrui.jrap.activiti.custom;

import com.jingrui.jrap.activiti.dto.ApproveChainLine;
import com.jingrui.jrap.activiti.service.impl.JrapApproveChain;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.apache.commons.lang.StringUtils;

import java.util.Collections;

/**
 * @author shengyang.zhou@jingrui.com
 */
public class CustomUserTaskActivityBehavior extends UserTaskActivityBehavior {

    public CustomUserTaskActivityBehavior(UserTask userTask) {
        super(userTask);
    }

    @Override
    public void execute(DelegateExecution execution) {
        if (JrapApproveChain.CURRENT_LINE.get() == null) {
            super.execute(execution);
            return;
        }

        new UserTaskActivityBehavior(byApproveChain(this.userTask)).execute(execution);
    }

    private UserTask byApproveChain(UserTask task) {
        ApproveChainLine currentLine = JrapApproveChain.CURRENT_LINE.get();
        JrapApproveChain.CURRENT_LINE.remove();
        if (currentLine == null) {
            return task;
        }
        UserTask userTask = task.clone();

        userTask.setName(task.getName() + " - " + currentLine.getName());

        if (StringUtils.isNotEmpty(currentLine.getAssignee())) {
            userTask.setAssignee(currentLine.getAssignee());
        } else if (StringUtils.isNotEmpty(currentLine.getAssignGroup())) {
            userTask.setCandidateGroups(Collections.singletonList(currentLine.getAssignGroup()));
            if (userTask.getCandidateUsers() != null) {
                userTask.setCandidateUsers(null);
            }
        }


        if (StringUtils.isNotEmpty(currentLine.getFormKey())) {
            userTask.setFormKey(currentLine.getFormKey());
        }

        if (StringUtils.isEmpty(userTask.getDocumentation())) {
            userTask.setDocumentation(currentLine.getName());
        }
        return userTask;
    }

}
