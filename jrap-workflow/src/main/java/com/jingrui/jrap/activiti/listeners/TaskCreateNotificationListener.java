package com.jingrui.jrap.activiti.listeners;

import com.jingrui.jrap.account.dto.User;
import com.jingrui.jrap.account.service.IUserService;
import com.jingrui.jrap.activiti.custom.process.CustomHistoricProcessInstanceQuery;
import com.jingrui.jrap.activiti.service.IActivitiEntityService;
import com.jingrui.jrap.core.AppContextInitListener;
import com.jingrui.jrap.system.service.IBadgeService;
import org.activiti.bpmn.model.Task;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.delegate.TransactionDependentTaskListener;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.el.ExpressionManager;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.task.IdentityLink;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.List;
import java.util.Map;

/**
 * TODO:重构代码
 *
 * @author xiangyu.qi@jingrui.com
 */
public class TaskCreateNotificationListener implements TransactionDependentTaskListener, TaskListener, AppContextInitListener {

    private Logger log = LoggerFactory.getLogger(getClass());


    private TaskService taskService;

    private HistoryService historyService;

    private RuntimeService runtimeService;

    @Autowired
    private IBadgeService badgeService;

    @Autowired
    private IUserService userService;


    @Autowired
    private IActivitiEntityService entityService;


    @Override
    public void notify(String processInstanceId, String executionId, Task task, Map<String, Object> executionVariables, Map<String, Object> customPropertiesMap) {
        ExecutionEntity execution = Context.getCommandContext().getExecutionEntityManager().findById(executionId);
        List<TaskEntity> tasks = Context.getCommandContext().getTaskEntityManager().findTasksByProcessInstanceId(processInstanceId);
        boolean noAssignee = (tasks.size() == 1 && StringUtils.isEmpty(tasks.get(0).getAssignee()) && CollectionUtils.isEmpty(tasks.get(0).getIdentityLinks()));
        if (noAssignee) {
            runtimeService.suspendProcessInstanceById(processInstanceId);
        } else {
            sendMessage(execution, task, processInstanceId, executionId);
        }
    }

    @Override
    public void contextInitialized(ApplicationContext applicationContext) {
        taskService = applicationContext.getBean(TaskService.class);
        historyService = applicationContext.getBean(HistoryService.class);
        runtimeService = applicationContext.getBean(RuntimeService.class);
    }

    @Override
    public void notify(DelegateTask delegateTask) {

    }


    /**
     * used by TaskEndNotification
     *
     * @param execution
     * @param task
     */
    public void sendMessage(DelegateExecution execution, Task task, String processInstanceId, String executionId) {
        UserTask userTask = (UserTask) task;
        ExpressionManager manager = Context.getProcessEngineConfiguration().getExpressionManager();
        if (StringUtils.isNotEmpty(userTask.getAssignee())) {
            Object assigneeObj = manager.createExpression(userTask.getAssignee()).getValue(execution);
            if (assigneeObj != null) {
                sendMessage(assigneeObj.toString());
            }
        } else {
            //候选人 候选组
            List<String> candidateUsers = userTask.getCandidateUsers();
            if (candidateUsers != null) {
                for (String user : candidateUsers) {
                    Object assigneeObj = manager.createExpression(user).getValue(execution);
                    if (assigneeObj != null) {
                        sendMessage(assigneeObj.toString());
                    }
                }
            }
            List<String> candidateGroups = userTask.getCandidateGroups();
            if (candidateGroups != null) {
                for (String group : candidateGroups) {
                    Object groupObj = manager.createExpression(group).getValue(execution);
                    if (groupObj != null) {
                        List<String> emps = entityService.getPositionEmp(groupObj.toString());
                        for (String emp : emps) {
                            sendMessage(emp);
                        }
                    }
                }
            }
        }
        if (StringUtils.isEmpty(userTask.getAssignee()) && userTask.getCandidateUsers().isEmpty() && userTask.getCandidateGroups().isEmpty()) {
            org.activiti.engine.task.Task taskEntity = taskService.createTaskQuery().processInstanceId(processInstanceId).executionId(executionId).singleResult();
            if (taskEntity == null) {
                return;
            }
            String assignee = taskEntity.getAssignee();
            if (StringUtils.isNotEmpty(assignee)) {
                sendMessage(assignee);
            } else {
                List<IdentityLink> idList = taskService.getIdentityLinksForTask(taskEntity.getId());
                for (IdentityLink il : idList) {
                    if (il.getGroupId() != null) {
                        List<String> emps = entityService.getPositionEmp(il.getGroupId());
                        for (String emp : emps) {
                            sendMessage(emp);
                        }
                    } else if (il.getUserId() != null) {
                        sendMessage(il.getUserId());
                    }
                }
            }
        }
    }

    public void sendMessage(String assignee) {
        List<User> users = userService.selectUserNameByEmployeeCode(assignee);
        if (users == null) {
            log.error("user not found !!!");
            return;
        }
        for (User user : users) {
            user.setEmployeeCode(assignee);
            sendMessage(user);
        }
    }

    public void sendMessage(User user) {
        String userName = user.getUserName();
        long count = taskService.createTaskQuery().taskCandidateOrAssigned(user.getEmployeeCode()).count();

        badgeService.sendBadgeMessageToUser(userName, "WFL_MY_TASK", count);
    }

    public void sendMessageByGroup(String groupId) {
        List<String> emps = entityService.getPositionEmp(groupId);
        for (String emp : emps) {
            sendMessage(emp);
        }
    }

    public void sendMessageForCC(String assignee) {
        List<User> users = userService.selectUserNameByEmployeeCode(assignee);
        if (users == null) {
            log.error("user not found !!!");
            return;
        }
        for (User user : users) {
            user.setEmployeeCode(assignee);
            sendMessageForCC(user);
        }
    }

    public void sendMessageForCC(User user) {
        String userName = user.getUserName();
        CustomHistoricProcessInstanceQuery query = (CustomHistoricProcessInstanceQuery) historyService.createHistoricProcessInstanceQuery();
        query.setCarbonCopyUser(user.getEmployeeCode());
        query.setReadFlag("N");
        long count = query.list().size();
        badgeService.sendBadgeMessageToUser(userName, "WFL_CARBON", count);
    }


}