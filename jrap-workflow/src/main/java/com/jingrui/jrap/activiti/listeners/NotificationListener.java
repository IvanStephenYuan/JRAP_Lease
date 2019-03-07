package com.jingrui.jrap.activiti.listeners;

import com.jingrui.jrap.activiti.core.IActivitiConstants;
import com.jingrui.jrap.core.AppContextInitListener;
import com.jingrui.jrap.core.BaseConstants;
import com.jingrui.jrap.message.IMessagePublisher;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.IdentityLinkEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.activiti.engine.impl.persistence.entity.data.GroupDataManager;
import org.activiti.engine.impl.persistence.entity.data.UserDataManager;
import org.activiti.engine.task.IdentityLinkType;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.*;

public class NotificationListener implements TaskListener, AppContextInitListener {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserDataManager userDataManager;

    @Autowired
    private GroupDataManager groupDataManager;

    @Autowired
    private IMessagePublisher messagePublisher;

    private FormService formService;

    private HistoryService historyService;

    private Collection<IUserTaskNotifier> notifiers = Collections.emptyList();

    @Override
    public void notify(DelegateTask delegateTask) {
        // 任务创建 通知用户
        if (TaskListener.EVENTNAME_CREATE.equals(delegateTask.getEventName())) {
            TaskEntity task = (TaskEntity) delegateTask;
            String assignee = task.getAssignee();
            List<IdentityLinkEntity> identityLinks = task.getIdentityLinks();

            if (StringUtils.isNotEmpty(assignee)) {
                UserEntity user = userDataManager.findById(assignee);
                sendNotification(user, task);
            }

            for (IdentityLinkEntity link : identityLinks) {
                if (IdentityLinkType.CANDIDATE.equals(link.getType())) {
                    if (link.isUser()) {
                        UserEntity user = userDataManager.findById(link.getUserId());
                        sendNotification(user, task);
                    }
                    if (link.isGroup()) {
                        sendNotification(groupDataManager.findById(link.getGroupId()), task);
                    }
                }
            }
        } else if (TaskListener.EVENTNAME_COMPLETE.equals(delegateTask.getEventName())) {
            // 任务完成时，判断是否自动抄送
            List<FormProperty> properties = formService.getTaskFormData(delegateTask.getId()).getFormProperties();
            boolean autoCC = false;
            if (properties != null) {
                for (FormProperty property : properties) {
                    if (IActivitiConstants.AUTO_CARBON_COPY_FLAG.equals(property.getId())
                            && BaseConstants.YES.equals(property.getName())) {
                        autoCC = true;
                        break;
                    }
                }
            }
            if (autoCC) {
                String approveResult = delegateTask.getVariable(IActivitiConstants.PROP_APPROVE_RESULT).toString();
                if (StringUtils.equals(IActivitiConstants.REJECTED, approveResult)) {
                    List<HistoricTaskInstance> history = historyService.createHistoricTaskInstanceQuery()
                            .processInstanceId(delegateTask.getProcessInstanceId()).list();
                    Set<String> users = new HashSet<>();
                    history.forEach(t -> {
                        if (!delegateTask.getAssignee().equals(t.getAssignee())) {
                            users.add(t.getAssignee());
                        }
                    });
                    Map<String, Object> params = new HashMap<>();
                    params.put(IActivitiConstants.MSG_PAEM_PROCESSINSTANCEID, delegateTask.getProcessInstanceId());
                    params.put(IActivitiConstants.MSG_PARM_USERS, users);
                    messagePublisher.message(IActivitiConstants.CHANNEL_CARBON_COPY, params);
                }
            }
        }
    }

    public void sendNotification(GroupEntity groupEntity, TaskEntity taskEntity) {
        log.debug("send email notification to group:" + groupEntity);
        for (IUserTaskNotifier notifier : notifiers) {
            notifier.onTaskCreate(taskEntity, groupEntity);
        }
    }

    public void sendNotification(UserEntity user, TaskEntity task) {
        log.debug("send email notification to user:" + user);
        for (IUserTaskNotifier notifier : notifiers) {
            notifier.onTaskCreate(task, user);
        }
        if (user == null) {
            throw new ActivitiException("Employee Not Found.");
        }

        String message = user.getFirstName() + " 你好:<br/>" + "你有一个工作流需要审批.";

        // if (true) {
        // log.debug(message);
        // return;
        // }
        log.debug(message);
        /*
         * HtmlEmail email = new HtmlEmail(); email.setCharset("GBK"); try {
         * email.setHtmlMsg(message); email.addTo(user.getEmail());
         * email.setSubject("[JRAP]工作流提醒"); setMailServerProperties(email); email.send();
         * } catch (EmailException e) { throw new
         * ActivitiException("Could not send e-mail:" + e.getMessage(), e); }
         */
    }

    protected void setMailServerProperties(Email email) throws EmailException {
        ProcessEngineConfigurationImpl processEngineConfiguration = Context.getProcessEngineConfiguration();

        String host = processEngineConfiguration.getMailServerHost();
        if (host == null) {
            throw new ActivitiException("Could not send email: no SMTP host is configured");
        }
        email.setHostName(host);

        int port = processEngineConfiguration.getMailServerPort();
        email.setSmtpPort(port);

        String user = processEngineConfiguration.getMailServerUsername();
        String password = processEngineConfiguration.getMailServerPassword();
        if (user != null && password != null) {
            email.setFrom(user);
            email.setAuthentication(user, password);
        }
    }

    @Override
    public void contextInitialized(ApplicationContext applicationContext) {
        Map<String, IUserTaskNotifier> map = applicationContext.getBeansOfType(IUserTaskNotifier.class);
        notifiers = map.values();
        historyService = applicationContext.getBean(HistoryService.class);
        formService = applicationContext.getBean(FormService.class);
    }
}