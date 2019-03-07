package com.jingrui.jrap.activiti.service.impl;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.google.common.base.Throwables;
import com.jingrui.jrap.activiti.components.ActivitiMultiLanguageManager;
import com.jingrui.jrap.activiti.core.IActivitiConstants;
import com.jingrui.jrap.activiti.custom.ForecastActivityCmd;
import com.jingrui.jrap.activiti.custom.GetExpressionValueCmd;
import com.jingrui.jrap.activiti.custom.ICustomTaskProcessor;
import com.jingrui.jrap.activiti.custom.JumpActivityCmd;
import com.jingrui.jrap.activiti.custom.process.CustomHistoricProcessInstanceQueryResource;
import com.jingrui.jrap.activiti.custom.task.CustomTaskQueryResource;
import com.jingrui.jrap.activiti.dto.*;
import com.jingrui.jrap.activiti.event.TaskRecallEvent;
import com.jingrui.jrap.activiti.event.dto.TaskRecallInfo;
import com.jingrui.jrap.activiti.exception.TaskActionException;
import com.jingrui.jrap.activiti.exception.WflSecurityException;
import com.jingrui.jrap.activiti.exception.dto.ActiviException;
import com.jingrui.jrap.activiti.exception.mapper.ActiviExceptionMapper;
import com.jingrui.jrap.activiti.listeners.TaskCreateNotificationListener;
import com.jingrui.jrap.activiti.mapper.HiIdentitylinkMapper;
import com.jingrui.jrap.activiti.service.IActivitiEntityService;
import com.jingrui.jrap.activiti.service.IActivitiService;
import com.jingrui.jrap.activiti.service.IApproveChainHeaderService;
import com.jingrui.jrap.activiti.util.ActivitiUtils;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.hr.dto.Employee;
import com.jingrui.jrap.hr.service.IEmployeeService;
import com.jingrui.jrap.message.IMessagePublisher;
import com.jingrui.jrap.mybatis.util.StringUtil;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.UserTask;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.*;
import org.activiti.engine.form.FormData;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.identity.Group;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.persistence.entity.CommentEntityImpl;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.activiti.engine.impl.persistence.entity.data.GroupDataManager;
import org.activiti.engine.impl.persistence.entity.data.UserDataManager;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.activiti.rest.common.api.DataResponse;
import org.activiti.rest.service.api.RestResponseFactory;
import org.activiti.rest.service.api.engine.variable.RestVariable;
import org.activiti.rest.service.api.history.HistoricProcessInstanceCollectionResource;
import org.activiti.rest.service.api.history.HistoricProcessInstanceQueryRequest;
import org.activiti.rest.service.api.history.HistoricTaskInstanceQueryRequest;
import org.activiti.rest.service.api.history.HistoricTaskInstanceQueryResource;
import org.activiti.rest.service.api.management.DeadLetterJobCollectionResource;
import org.activiti.rest.service.api.runtime.process.ExecutionVariableCollectionResource;
import org.activiti.rest.service.api.runtime.process.ProcessInstanceCollectionResource;
import org.activiti.rest.service.api.runtime.process.ProcessInstanceCreateRequest;
import org.activiti.rest.service.api.runtime.process.ProcessInstanceResponse;
import org.activiti.rest.service.api.runtime.task.TaskActionRequest;
import org.activiti.rest.service.api.runtime.task.TaskQueryRequest;
import org.activiti.rest.service.api.runtime.task.TaskResource;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.stereotype.Service;

/**
 * @author shengyang.zhou@jingrui.com
 * @author njq.niu@jingrui.com
 */
@Service
public class ActivitiServiceImpl implements IActivitiService, IActivitiConstants, InitializingBean {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private GroupDataManager groupDataManager;

    @Autowired
    private UserDataManager userDataManager;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private FormService formService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private RestResponseFactory restResponseFactory;

    @Autowired
    private ProcessEngineConfigurationImpl processEngineConfiguration;

    @Autowired
    private ActiviExceptionMapper exceptionMapper;

    @Autowired
    private IEmployeeService employeeService;

    @Autowired
    private IActivitiEntityService entityService;

    @Autowired
    private ForecastActivityCmd forecastActivityCmd;

    @Autowired
    private HiIdentitylinkMapper hiIdentitylinkMapper;

    @Autowired
    private TaskCreateNotificationListener taskCreateNotificationListener;

    @Autowired
    IApproveChainHeaderService approveChainHeaderService;

    @Autowired
    private IMessagePublisher messagePublisher;

    @Autowired
    private ApplicationEventPublisher publisher;

    /* 以下 为 手动注入的 bean */
    private TaskResource taskResource = new TaskResource();

    private ExecutionVariableCollectionResource executionVariableCollectionResource = new ExecutionVariableCollectionResource();

    private CustomTaskQueryResource taskQueryResource = new CustomTaskQueryResource();

    private CustomHistoricProcessInstanceQueryResource historicProcessInstanceQueryResource = new CustomHistoricProcessInstanceQueryResource();

    private ProcessInstanceCollectionResource processInstanceCollectionResource = new ProcessInstanceCollectionResource();

    private HistoricTaskInstanceQueryResource historicTaskInstanceQueryResource = new HistoricTaskInstanceQueryResource();

    private HistoricProcessInstanceCollectionResource historicProcessInstanceCollectionResource = new HistoricProcessInstanceCollectionResource();

    private DeadLetterJobCollectionResource deadLetterJobCollectionResource = new DeadLetterJobCollectionResource();

    /* Fake request,response,used to call rest api */
    private HttpServletRequest fakeRequest = new MockHttpServletRequest();
    private HttpServletResponse fakeResponse = new MockHttpServletResponse();

    private List<ICustomTaskProcessor> taskProcessors;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public ProcessInstanceResponse startProcess(IRequest iRequest, ProcessInstanceCreateRequest createRequest) {
        try {
            String employeeCode = iRequest.getEmployeeCode();
            Authentication.setAuthenticatedUserId(employeeCode);
            return processInstanceCollectionResource.createProcessInstance(createRequest, fakeRequest, fakeResponse);
        } finally {
            Authentication.setAuthenticatedUserId(null);
        }
    }

    public DataResponse getInvolvedProcess(IRequest request, Map<String, String> allParameters) {
        return processInstanceCollectionResource.getProcessInstances(allParameters, fakeRequest);
    }

    @Override
    public Model deployModel(String modelId) throws Exception {
        Model model = repositoryService.getModel(modelId);

        byte[] modelData = repositoryService.getModelEditorSource(modelId);
        JsonNode jsonNode = objectMapper.readTree(modelData);
        BpmnModel bpmnModel = new BpmnJsonConverter().convertToBpmnModel(jsonNode);

        // byte[] xmlBytes = new BpmnXMLConverter().convertToXML(bpmnModel,
        // "UTF-8");

        Deployment deploy = repositoryService.createDeployment().category(model.getCategory()).name(model.getName())
                .key(model.getKey()).addBpmnModel(model.getKey() + ".bpmn20.xml", bpmnModel).deploy();

        model.setDeploymentId(deploy.getId());
        repositoryService.saveModel(model);
        return model;

    }

    @Override
    public void completeTask(IRequest request, Task taskEntity, TaskActionRequestExt actionRequest)
            throws TaskActionException {
        if (!TaskActionRequest.ACTION_COMPLETE.equalsIgnoreCase(actionRequest.getAction())) {
            return;
        }
        Authentication.setAuthenticatedUserId(request.getEmployeeCode());
        String action = null;// 本次操作执行的动作
        String taskId = taskEntity.getId();
        List<RestVariable> vars = actionRequest.getVariables();
        if (vars != null) {
            for (RestVariable rv : vars) {
                if (PROP_APPROVE_RESULT.equalsIgnoreCase(rv.getName())) {
                    action = String.valueOf(rv.getValue());
                    break;
                }
            }
        }
        actionRequest.setAssignee(request.getEmployeeCode());
        taskService.addComment(taskId, taskEntity.getProcessInstanceId(), COMMENT_ACTION, action);
        taskService.addComment(taskId, taskEntity.getProcessInstanceId(), PROP_COMMENT, actionRequest.getComment());
        taskResource.executeTaskAction(taskId, actionRequest);
        if (StringUtils.isNotEmpty(actionRequest.getAction())) {
            taskCreateNotificationListener.sendMessage(actionRequest.getAssignee());
        }
        taskCreateNotificationListener.sendMessage(taskEntity.getAssignee());
        /*
         * if (StringUtils.isEmpty(taskEntity.getAssignee())) {
         * actionRequest.setAssignee(request.getEmployeeCode()); // 自动 claim
         * taskService.claim(taskId, request.getEmployeeCode());
         * taskService.addComment(taskId, taskEntity.getProcessInstanceId(),
         * COMMENT_ACTION, action); taskService.addComment(taskId,
         * taskEntity.getProcessInstanceId(), PROP_COMMENT, actionRequest.getComment());
         * taskResource.executeTaskAction(taskId, actionRequest); } else {
         * actionRequest.setAssignee(request.getEmployeeCode());
         * taskService.addComment(taskId, taskEntity.getProcessInstanceId(),
         * COMMENT_ACTION, action); taskService.addComment(taskId,
         * taskEntity.getProcessInstanceId(), PROP_COMMENT, actionRequest.getComment());
         * taskResource.executeTaskAction(taskId, actionRequest); }
         */
    }

    @Override
    public void delegateTask(IRequest request, Task taskEntity, TaskActionRequestExt actionRequest)
            throws TaskActionException {
        if (!TaskActionRequest.ACTION_DELEGATE.equalsIgnoreCase(actionRequest.getAction())) {
            return;
        }
        Authentication.setAuthenticatedUserId(request.getEmployeeCode());
        String assignee = actionRequest.getAssignee();
        if (StringUtils.isEmpty(assignee)) {
            throw new TaskActionException(TaskActionException.DELEGATE_NO_ASSIGNEE);
        }

        String taskId = taskEntity.getId();
        taskService.addComment(taskId, taskEntity.getProcessInstanceId(), COMMENT_ACTION, DELEGATE);
        taskService.addComment(taskId, taskEntity.getProcessInstanceId(), PROP_COMMENT,
                request.getEmployeeCode() + "转交给" + assignee + "  " + actionRequest.getComment());
        /*
         * taskService.addComment(taskId, taskEntity.getProcessInstanceId(),
         * COMMENT_DELEGATE_BY, actionRequest.getComment());
         */
        taskService.setAssignee(taskId, assignee);
        taskCreateNotificationListener.sendMessage(taskEntity.getAssignee());
        taskCreateNotificationListener.sendMessage(assignee);

        /*
         * DelegationState state = taskEntity.getDelegationState(); if (state != null &&
         * state == DelegationState.PENDING) { // 正在转交中 throw new
         * TaskActionException(TaskActionException.DELEGATE_IN_PENDING); }
         * 
         * if (taskEntity.getOwner() != null) {
         * 
         * if (eq(taskEntity.getOwner(), assignee)) { throw new
         * TaskActionException(TaskActionException.DELEGATE_TO_OWNER); }
         * 
         * if (!hasRight(request.getEmployeeCode(), taskEntity.getOwner())) { throw new
         * TaskActionException(TaskActionException.DELEGATE_NEED_OWNER_OR_ADMIN); } }
         * 
         * taskEntity.setOwner(assignee);// change owner when delegate
         * taskService.saveTask(taskEntity);
         * 
         * if (StringUtils.isEmpty(taskEntity.getAssignee())) { //
         * actionRequest.setAssignee(request.getEmployeeCode());
         * taskService.addComment(taskId, taskEntity.getProcessInstanceId(),
         * COMMENT_DELEGATE_BY, actionRequest.getComment());
         * taskResource.executeTaskAction(taskId, actionRequest); } else if
         * (hasRight(request.getEmployeeCode(), taskEntity.getAssignee())) { //
         * actionRequest.setAssignee(request.getEmployeeCode());
         * taskService.addComment(taskId, taskEntity.getProcessInstanceId(),
         * COMMENT_DELEGATE_BY, actionRequest.getComment());
         * taskResource.executeTaskAction(taskId, actionRequest); } else { throw new
         * TaskActionException(TaskActionException.COMPLETE_TASK_NEED_ASSIGNEE_OR_ADMIN)
         * ; }
         */
    }

    @Override
    public void resolveTask(IRequest request, Task taskEntity, TaskActionRequestExt actionRequest)
            throws TaskActionException {
        if (!TaskActionRequest.ACTION_RESOLVE.equalsIgnoreCase(actionRequest.getAction())) {
            return;
        }
        Authentication.setAuthenticatedUserId(request.getEmployeeCode());

        String taskId = taskEntity.getId();

        /*
         * if (!hasRight(request.getEmployeeCode(), taskEntity.getOwner())) { throw new
         * TaskActionException(TaskActionException.RESOLVE_NEED_OWNER_OR_ADMIN); }
         * taskResource.executeTaskAction(taskId, actionRequest);
         */
        String action = null;// 本次操作执行的动作

        List<RestVariable> vars = actionRequest.getVariables();
        if (vars != null) {
            for (RestVariable rv : vars) {
                if (PROP_APPROVE_RESULT.equalsIgnoreCase(rv.getName())) {
                    action = String.valueOf(rv.getValue());
                    break;
                }
            }
        }
        taskService.addComment(taskId, taskEntity.getProcessInstanceId(), COMMENT_ACTION, action);
        taskService.addComment(taskId, taskEntity.getProcessInstanceId(), PROP_COMMENT, actionRequest.getComment());
        taskService.resolveTask(taskId);
        taskCreateNotificationListener.sendMessage(taskEntity.getOwner());
        taskCreateNotificationListener.sendMessage(taskEntity.getAssignee());
    }

    public void addSignTask(IRequest request, Task taskEntity, TaskActionRequestExt actionRequest) {
        if (!ACTION_ADD_SIGN.equalsIgnoreCase(actionRequest.getAction())) {
            return;
        }
        Authentication.setAuthenticatedUserId(request.getEmployeeCode());
        String assignee = actionRequest.getAssignee();

        String taskId = taskEntity.getId();
        taskService.addComment(taskId, taskEntity.getProcessInstanceId(), COMMENT_ACTION, ADD_SIGN);
        taskService.addComment(taskId, taskEntity.getProcessInstanceId(), PROP_COMMENT, actionRequest.getComment());
        taskService.delegateTask(taskId, assignee);
        taskCreateNotificationListener.sendMessage(assignee);
        taskCreateNotificationListener.sendMessage(taskEntity.getAssignee());
    }

    public void carbonCopy(IRequest request, Task taskEntity, TaskActionRequestExt actionRequest)
            throws TaskActionException {

        Authentication.setAuthenticatedUserId(request.getEmployeeCode());
        String assignee = actionRequest.getCarbonCopyUsers();
        if (StringUtil.isNotEmpty(assignee)) {
            Set<String> users = org.springframework.util.StringUtils.commaDelimitedListToSet(assignee);
            String processInstanceId = taskEntity.getProcessInstanceId();
            Map<String, Object> params = new HashMap<>();
            params.put(MSG_PAEM_PROCESSINSTANCEID, processInstanceId);
            params.put(MSG_PARM_USERS, users);
            String comment = request.getEmployeeCode() + " 抄送给 " + assignee;
            taskService.addComment(taskEntity.getId(), processInstanceId, COMMENT_ACTION, CARBON_COPY);
            taskService.addComment(taskEntity.getId(), processInstanceId, PROP_COMMENT, comment);
            messagePublisher.message(IActivitiConstants.CHANNEL_CARBON_COPY, params);
        }
    }

    public void processCarbonCopyRead(String processInstanceId, String employeeCode) {
        HiIdentitylink dto = new HiIdentitylink();
        dto.setProcInstId_(processInstanceId);
        dto.setReadFlag_("Y");
        dto.setUserId_(employeeCode);
        hiIdentitylinkMapper.updateReadFlag(dto);
        taskCreateNotificationListener.sendMessageForCC(employeeCode);
    }

    /*
     * public void updateCarbonCopyMsg(String user){
     * CustomHistoricProcessInstanceQueryRequest historicProcessInstanceQueryRequest
     * = new CustomHistoricProcessInstanceQueryRequest();
     * historicProcessInstanceQueryRequest.setCarbonCopyUser(user);
     * historicProcessInstanceQueryRequest.setReadFlag("N"); Map<String, String>
     * requestParams = new HashMap<>(); long count =
     * historicProcessInstanceQueryResource.queryProcessInstances(
     * historicProcessInstanceQueryRequest, requestParams, fakeRequest).getTotal();
     * taskCreateNotificationListener.sendMessageForCC(user,count); }
     */

    @Override
    public void jumpTo(IRequest request, Task taskEntity, TaskActionRequestExt actionRequest) {
        if (!ACTION_JUMP.equalsIgnoreCase(actionRequest.getAction())) {
            return;
        }
        Authentication.setAuthenticatedUserId(request.getEmployeeCode());
        String taskId = taskEntity.getId();
        taskService.addComment(taskId, taskEntity.getProcessInstanceId(), COMMENT_ACTION, JUMP);
        taskService.addComment(taskId, taskEntity.getProcessInstanceId(), PROP_COMMENT,
                "从<" + taskEntity.getName() + ">跳转至<" + actionRequest.getJumpTargetName() + ">");
        JumpActivityCmd cmd = new JumpActivityCmd(taskId, actionRequest.getJumpTarget());
        processEngineConfiguration.getCommandExecutor().execute(cmd);
        if (StringUtils.isNotEmpty(taskEntity.getAssignee())) {
            taskCreateNotificationListener.sendMessage(taskEntity.getAssignee());
        }
    }

    protected boolean hasRight(String a, String b, boolean isAdmin) {
        return isAdmin || eq(a, b);
    }

    protected boolean eq(Object o1, Object o2) {
        return o1 == null ? o2 == null : o1.equals(o2);
    }

    @Override
    public void executeTaskAction(IRequest request, String taskId, TaskActionRequestExt actionRequest, boolean isAdmin)
            throws TaskActionException {
        if (StringUtils.isEmpty(actionRequest.getAction())) {
            throw new IllegalArgumentException("Action is required.");
        }
        Task taskEntity = getTaskById(taskId);
        // 处理配置候选人候选组的情况
        List<IdentityLink> idList = null;
        //TODO:检查
        if (taskEntity.getAssignee() == null) {
            // 自动 claim
            idList = taskService.getIdentityLinksForTask(taskId);
            if (isAdmin) {
                taskService.claim(taskId, request.getEmployeeCode());
            } else {
                Set<String> nameList = new HashSet<>();
                List<Group> userGroup = null;
                boolean isCandi = false;
                for (IdentityLink il : idList) {
                    if (il.getUserId() != null) {
                        if (eq(request.getEmployeeCode(), il.getUserId())) {
                            isCandi = true;
                            break;
                        }
                    } else if (il.getGroupId() != null) {
                        userGroup = processEngineConfiguration.getUserDataManager()
                                .findGroupsByUser(request.getEmployeeCode());
                        for (Group g : userGroup) {
                            if (eq(g.getId(), il.getGroupId())) {
                                isCandi = true;
                                break;
                            }
                        }
                    }
                }
                if (!isCandi) {
                    throw new TaskActionException(WflSecurityException.NEED_ASSIGNEE_OR_ADMIN);
                }
                taskService.claim(taskId, request.getEmployeeCode());
                taskEntity.setAssignee(request.getEmployeeCode());
            }
        }
        if (!hasRight(request.getEmployeeCode(), taskEntity.getAssignee(), isAdmin)) {
            throw new TaskActionException(TaskActionException.COMPLETE_TASK_NEED_ASSIGNEE_OR_ADMIN);
        }
        Authentication.setAuthenticatedUserId(request.getEmployeeCode());

        try {
            // 处理抄送
            carbonCopy(request, taskEntity, actionRequest);
            if (TaskActionRequest.ACTION_COMPLETE.equalsIgnoreCase(actionRequest.getAction())) {
                completeTask(request, taskEntity, actionRequest);
                return;
            }
            if (TaskActionRequest.ACTION_DELEGATE.equalsIgnoreCase(actionRequest.getAction())) {
                delegateTask(request, taskEntity, actionRequest);
                processCandidateMsg(idList);
                return;
            }

            if (TaskActionRequest.ACTION_RESOLVE.equalsIgnoreCase(actionRequest.getAction())) {
                resolveTask(request, taskEntity, actionRequest);
                return;
            }

            if (ACTION_JUMP.equalsIgnoreCase(actionRequest.getAction())) {
                jumpTo(request, taskEntity, actionRequest);
                return;
            }
            if (ACTION_ADD_SIGN.equals(actionRequest.getAction())) {
                addSignTask(request, taskEntity, actionRequest);
                processCandidateMsg(idList);
                return;
            }

        } catch (ActivitiException e) {
            self().saveException(taskId, e);
            throw e;
        }
    }

    private void processCandidateMsg(List<IdentityLink> idList) {
        if (idList != null) {
            for (IdentityLink il : idList) {
                if (il.getUserId() != null) {
                    taskCreateNotificationListener.sendMessage(il.getUserId());
                } else if (il.getGroupId() != null) {
                    taskCreateNotificationListener.sendMessageByGroup(il.getGroupId());
                }
            }
        }
    }

    public void saveException(String taskId, ActivitiException e) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String mess = Throwables.getStackTraceAsString(e);
       /* byte[] mesb = null;
        try {
            mesb = mess.getBytes("utf-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }*/
        exceptionMapper.insertSelective(new ActiviException(task.getProcessInstanceId(), mess, new Date()));
    }

    @Override
    public void deleteDeployment(String deploymentId, Boolean cascade) {

        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .deploymentId(deploymentId).singleResult();
        String processDefinitionId = processDefinition.getId();
        List<Task> taskList = taskService.createTaskQuery().processDefinitionId(processDefinitionId).list();
        Set<String> users = new HashSet<>();
        taskList.forEach(t -> {
            if (t.getAssignee() != null) {
                users.add(t.getAssignee());
            }
        });
        if (cascade) {
            repositoryService.deleteDeployment(deploymentId, true);
        } else {
            repositoryService.deleteDeployment(deploymentId);
        }
        for (String user : users) {
            taskCreateNotificationListener.sendMessage(user);
            taskCreateNotificationListener.sendMessageForCC(user);
        }
    }

    @Override
    public void deleteProcessInstance(String processInstanceId) {
        List<Task> taskList = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
        Set<String> users = new HashSet<>();
        taskList.forEach(t -> {
            if (t.getAssignee() != null) {
                users.add(t.getAssignee());
            }
        });
        runtimeService.deleteProcessInstance(processInstanceId, IActivitiConstants.ACT_STOP);
        for (String user : users) {
            taskCreateNotificationListener.sendMessage(user);
        }
    }

    @Override
    public List<ActivitiNode> getProcessNodes(IRequest request, String processInstanceId) {
        /*
         * Process process = ProcessDefinitionUtil.getProcess(processDefinitionId);
         * List<ActivitiNode> list = new ArrayList<>(); Collection<FlowElement> eles =
         * process.getFlowElements(); for (FlowElement fe : eles) { if (fe instanceof
         * UserTask) { ActivitiNode node = new ActivitiNode();
         * node.setName(fe.getName()); node.setNodeId(fe.getId());
         * node.setType("UserTask"); list.add(node); } }
         */
        List<HistoricActivityInstance> historicActivityInstanceList = historyService
                .createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).activityType("userTask")
                .orderByHistoricActivityInstanceEndTime().asc().list();
        String processDefinitionId = historicActivityInstanceList.get(0).getProcessDefinitionId();
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
        List<ActivitiNode> list = new ArrayList<>();
        Collection<FlowElement> flowElements = bpmnModel.getMainProcess().getFlowElements();
        for (FlowElement flowElement : flowElements) {
            if (flowElement instanceof UserTask) {
                ActivitiNode node = new ActivitiNode();
                node.setName(ActivitiMultiLanguageManager.getMultLanguageInfoByCode(flowElement.getName(), request));
                node.setNodeId(flowElement.getId());
                node.setType("UserTask");
                list.add(node);
            }
        }
        return list;
    }

    @Override
    public List<ActivitiNode> getUserTaskFromModelSource(IRequest request, String modelId) {
        List<ActivitiNode> list = new ArrayList<>();
        byte[] data = repositoryService.getModelEditorSource(modelId);
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(data);
            BpmnModel bpmnModel = new BpmnJsonConverter().convertToBpmnModel(jsonNode);
            Process process = bpmnModel.getMainProcess();
            Collection<FlowElement> elements = process.getFlowElements();
            for (FlowElement flowElement : elements) {
                if (flowElement instanceof UserTask) {
                    ActivitiNode node = new ActivitiNode();
                    node.setNodeId(flowElement.getId());
                    node.setName(ActivitiMultiLanguageManager.getMultLanguageInfoByCode(flowElement.getName(), request));
                    node.setType("UserTask");
                    list.add(node);
                }
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return list;
    }

    @Override
    public String getEmployeeName(String userId) {
        UserEntity userEntity = userDataManager.findById(userId);
        if (userEntity != null && StringUtils.isNotEmpty(userEntity.getFirstName())) {
            return userEntity.getFirstName();
        }
        return userId;
    }

    @Override
    public String getGroupName(String groupId) {
        Group group = groupDataManager.findById(groupId);
        if (group != null) {
            return group.getName();
        }
        return groupId;
    }

    @Override
    public HistoricProcessInstanceResponseExt getInstanceDetail(IRequest request, String processInstanceId) {
        HistoricProcessInstanceResponseExt historicProcessInstanceResponseExt = new HistoricProcessInstanceResponseExt();
        // 查询流程实例历史
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
                .processInstanceId(processInstanceId).list().iterator().next();
        // 设置申请人，流程名称
        historicProcessInstanceResponseExt.setStartUserId(historicProcessInstance.getStartUserId());
        historicProcessInstanceResponseExt.setStartUserName(getEmployeeName(historicProcessInstance.getStartUserId()));
        historicProcessInstanceResponseExt.setProcessName(ActivitiMultiLanguageManager.getMultLanguageInfoByCode(historicProcessInstance.getProcessDefinitionName(), request));
        historicProcessInstanceResponseExt.setStartTime(historicProcessInstance.getStartTime());

        // 获取流程活动历史
        List<HistoricActivityInstance> historicActivityInstanceList = historyService
                .createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).list();
        List<HistoricTaskInstanceResponseExt> list = new ArrayList<>();
        for (HistoricActivityInstance historicActivityInstance : historicActivityInstanceList) {
            setHistoricActivityInstanceResponseExt(historicActivityInstance, list, processInstanceId, request);
        }
        list.sort(Comparator.comparing(HistoricTaskInstanceResponseExt::getEndTime));
        historicProcessInstanceResponseExt.getHistoricTaskList().addAll(list);

        // 设置全局表单url
        String key = formService.getStartFormKey(historicProcessInstance.getProcessDefinitionId());
        historicProcessInstanceResponseExt.setBusinessKey(historicProcessInstance.getBusinessKey());
        historicProcessInstanceResponseExt.setFormKey(key);

        List<HistoricVariableInstance> variables = historyService.createHistoricVariableInstanceQuery().processInstanceId(historicProcessInstance.getId()).list();
        historicProcessInstanceResponseExt.setDescription(getProcessDescription(historicProcessInstance.getProcessDefinitionId()
                , variables.stream().collect(Collectors.toMap(vk -> vk.getVariableName(), value -> value.getValue() == null ? "" : value.getValue(), (existingValue, newValue) -> newValue))));

        return historicProcessInstanceResponseExt;
    }

    @Override
    public TaskResponseExt getTaskDetails(IRequest request, String taskId, boolean isAdmin)
            throws WflSecurityException {
        Task task = getTaskById(taskId);

        TaskResponseExt taskExt = new TaskResponseExt(task);
        List<Group> userGroup = null;

        // display name of assignee or group
        if (StringUtils.isNotEmpty(taskExt.getAssignee())) {
            // privilege check
            if (!hasRight(request.getEmployeeCode(), taskExt.getAssignee(), isAdmin)) {
                throw new WflSecurityException(WflSecurityException.NEED_ASSIGNEE_OR_ADMIN);
            }
            taskExt.setAssigneeName(getEmployeeName(taskExt.getAssignee()));
        } else {
            List<IdentityLink> idList = taskService.getIdentityLinksForTask(task.getId());
            List<String> nameList = new ArrayList<>();
            boolean isCandi = isAdmin;
            for (IdentityLink il : idList) {
                if (il.getGroupId() != null) {
                    // privilege check
                    if (!isCandi) {
                        if (userGroup == null) {
                            userGroup = processEngineConfiguration.getUserDataManager()
                                    .findGroupsByUser(request.getEmployeeCode());
                        }
                        for (Group g : userGroup) {
                            if (eq(g.getId(), il.getGroupId())) {
                                isCandi = true;
                                break;
                            }
                        }
                    }
                    // privilege check end

                    nameList.add(getGroupName(il.getGroupId()));
                } else if (il.getUserId() != null) {
                    if (!isCandi && eq(request.getEmployeeCode(), il.getUserId())) {
                        // privilege check
                        isCandi = true;
                    }
                    nameList.add(getEmployeeName(il.getUserId()));
                }
            }
            if (!isCandi) {
                if (!hasRight(request.getEmployeeCode(), taskExt.getAssignee(), isAdmin)) {
                    throw new WflSecurityException(WflSecurityException.NEED_ASSIGNEE_OR_ADMIN);
                }
            }
            taskExt.setAssigneeName(StringUtils.join(nameList.toArray(), ";"));
        }

        // attachment
        List<org.activiti.engine.task.Attachment> attaList = taskService.getTaskAttachments(taskId);
        taskExt.setAttachments(attaList);

        // form data:formVariables
        FormData formData = formService.getTaskFormData(taskId);
        taskExt.setFormData(restResponseFactory.createFormDataResponse(formData));

        // approve history
        /*
         * List<HistoricTaskInstance> historicTaskInstanceList =
         * historyService.createHistoricTaskInstanceQuery()
         * .processInstanceId(task.getProcessInstanceId()).finished().list(); for
         * (HistoricTaskInstance aHistoricTaskInstanceList : historicTaskInstanceList) {
         * HistoricTaskInstanceResponseExt taskHistory = new
         * HistoricTaskInstanceResponseExt( aHistoricTaskInstanceList); if
         * (StringUtil.isNotEmpty(taskHistory.getAssignee())) {
         * taskHistory.setAssigneeName(getEmployeeName(taskHistory.getAssignee())); }
         * 
         * taskHistory.setComment(getCommentOfType(taskHistory.getId(), PROP_COMMENT));
         * taskHistory.setAction(getCommentOfType(taskHistory.getId(), COMMENT_ACTION));
         * 
         * taskExt.getHistoricTaskList().add(taskHistory); }
         */
        List<HistoricActivityInstance> historicActivityInstanceList = historyService
                .createHistoricActivityInstanceQuery().processInstanceId(task.getProcessInstanceId()).list();
        List<HistoricTaskInstanceResponseExt> list = new ArrayList<>();
        for (HistoricActivityInstance historicActivityInstance : historicActivityInstanceList) {
            setHistoricActivityInstanceResponseExt(historicActivityInstance, list, task.getProcessInstanceId(), request);
        }
        list.sort(Comparator.comparing(HistoricTaskInstanceResponseExt::getEndTime));
        taskExt.getHistoricTaskList().addAll(list);

        // delegate
        List<Comment> comments = taskService.getTaskComments(task.getId(), COMMENT_DELEGATE_BY);
        if (!comments.isEmpty()) {
            Comment comment = comments.get(comments.size() - 1);
            TaskDelegate taskDelegate = new TaskDelegate();

            taskDelegate.setFromUserId(comment.getUserId());
            taskDelegate.setFromUserName(getEmployeeName(comment.getUserId()));
            taskDelegate.setTime(comment.getTime());
            taskDelegate.setReason(comment.getFullMessage());
            taskExt.setTaskDelegate(taskDelegate);
        }

        // processInstance
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                .processInstanceId(task.getProcessInstanceId()).singleResult();
        ProcessInstanceResponseExt processInstanceResp = (ProcessInstanceResponseExt) restResponseFactory
                .createProcessInstanceResponse(processInstance);
        processInstanceResp.setProcessDefinitionName(ActivitiMultiLanguageManager.getMultLanguageInfoByCode(processInstanceResp.getProcessDefinitionName(), request));
        processInstanceResp.setStartUserName(getEmployeeName(processInstanceResp.getStartUserId()));
        taskExt.setProcessInstance(processInstanceResp);

        //流程描述
        taskExt.setDescription(getProcessDescription(processInstance.getProcessDefinitionId(), taskExt.getExecutionId()));

        // execution variable
        List<RestVariable> vars = executionVariableCollectionResource.getVariables(task.getExecutionId(), null,
                fakeRequest);

        taskExt.setExecutionVariables(vars);
        return taskExt;
    }

    @Override
    public TaskResponseExt getTaskDetails(IRequest request, String taskId) throws WflSecurityException {
        return getTaskDetails(request, taskId, false);
    }

    @Override
    public DataResponse queryTaskList(IRequest iRequest, TaskQueryRequest taskQueryRequest,
                                      Map<String, String> requestParams) {

        DataResponse dataResponse = taskQueryResource.getQueryResult(taskQueryRequest, requestParams, fakeRequest);
        List<TaskResponseExt> list = (List<TaskResponseExt>) dataResponse.getData();
        for (TaskResponseExt taskResponse : list) {
            if (StringUtils.isNotEmpty(taskResponse.getOwner())) {
                taskResponse.setOwner(getEmployeeName(taskResponse.getOwner()));
            }
            if (StringUtils.isNotEmpty(taskResponse.getAssignee())) {
                taskResponse.setAssigneeName(getEmployeeName(taskResponse.getAssignee()));
            } else {
                List<IdentityLink> idList = taskService.getIdentityLinksForTask(taskResponse.getId());
                List<String> nameList = new ArrayList<>();
                for (IdentityLink il : idList) {
                    if (il.getGroupId() != null) {
                        nameList.add(getGroupName(il.getGroupId()));
                    } else if (il.getUserId() != null) {
                        nameList.add(processApproveName(il.getUserId()));
                    }
                }
                taskResponse.setAssigneeName(StringUtils.join(nameList.toArray(), ";"));
            }
            ProcessInstance procInstance = runtimeService.createProcessInstanceQuery()
                    .processInstanceId(taskResponse.getProcessInstanceId()).list().iterator().next();

            taskResponse.setProcessName(procInstance.getProcessDefinitionName());
            taskResponse.setStartUserId(procInstance.getStartUserId());
            taskResponse.setStartUserName(getEmployeeName(procInstance.getStartUserId()));

            //流程描述
            taskResponse.setDescription(getProcessDescription(procInstance.getProcessDefinitionId(), taskResponse.getExecutionId()));

            Date dueDate = taskResponse.getDueDate();
            if (dueDate != null) {
                Long dueTime = ActivitiUtils.secondsBetweenDate(taskResponse.getCreateTime(), dueDate);
                for (ICustomTaskProcessor processor : taskProcessors) {
                    taskResponse.setDueTime(processor.getDueTime(taskResponse.getCreateTime(), dueTime));
                    if (!processor.processorContinue()) {
                        break;
                    }
                }
            }
            taskResponse.setProcessName(ActivitiMultiLanguageManager.getMultLanguageInfoByCode(taskResponse.getProcessName(), iRequest));
            taskResponse.setName(ActivitiMultiLanguageManager.getMultLanguageInfoByCode(taskResponse.getName(), iRequest));
        }
        return dataResponse;
    }

    @Override
    public DataResponse queryProcessInstances(IRequest iRequest,
                                              HistoricProcessInstanceQueryRequest historicProcessInstanceQueryRequest, Map<String, String> requestParams,
                                              boolean showRetract) {
        DataResponse dataResponse = historicProcessInstanceQueryResource
                .queryProcessInstances(historicProcessInstanceQueryRequest, requestParams, fakeRequest);
        for (HistoricProcessInstanceResponseExt his : (List<HistoricProcessInstanceResponseExt>) dataResponse
                .getData()) {
            if (StringUtils.isNotEmpty(his.getStartUserId())) {
                his.setStartUserName(getEmployeeName(his.getStartUserId()));
            }
            if (StringUtils.isNotEmpty(his.getTaskDefKey())) {
                BpmnModel bpmnModel = repositoryService.getBpmnModel(his.getProcessDefinitionId());
                Collection<FlowElement> flowElements = bpmnModel.getMainProcess().getFlowElements();
                for (FlowElement flowElement : flowElements) {
                    if (his.getTaskDefKey().equals(flowElement.getId())) {
                        his.setTaskName(flowElement.getName());
                        break;
                    }
                }
            }
            // TODO: 优化！
            List<Execution> list1 = runtimeService.createExecutionQuery().processInstanceId(his.getId()).list();
            for (Execution ls : list1) {
                if (ls.isSuspended()) {
                    his.setSuspended(true);
                    break;
                }
            }
            List<Task> tasks = taskService.createTaskQuery().processInstanceId(his.getId()).list();
            if (!tasks.isEmpty()) {
                String[] currentApprovers = new String[tasks.size()];
                List<TaskInfo> currentTasks = new ArrayList<>();
                Set taskName = new HashSet();
                for (int i = 0; i < tasks.size(); i++) {
                    Task task = tasks.get(i);
                    //获取节点多语言
                    task.setName(ActivitiMultiLanguageManager.getMultLanguageInfoByCode(task.getName(), iRequest));
                    taskName.add(task.getName());
                    currentApprovers[i] = getTaskApprove(task);
                    currentTasks.add(new TaskInfo(task.getId(), task.getName(), task.getAssignee(),
                            getEmployeeName(task.getAssignee())));
                }
                //设置当前节点
                his.setTaskName(org.springframework.util.StringUtils.collectionToCommaDelimitedString(taskName));
                // 设置当前审批人
                his.setCurrentApprover(StringUtils.join(currentApprovers, ","));
                his.setCurrentTasks(currentTasks);
                //流程描述
                his.setDescription(getProcessDescription(his.getProcessDefinitionId(), tasks.iterator().next().getExecutionId()));
            } else {
                //任务结束,处理流程描述
                if (his.getEndTime() != null) {
                    List<HistoricVariableInstance> variables = historyService.createHistoricVariableInstanceQuery().processInstanceId(his.getId()).list();
                    his.setDescription(getProcessDescription(his.getProcessDefinitionId()
                            , variables.stream().collect(Collectors.toMap(key -> key.getVariableName(), value -> value.getValue() == null ? "" : value.getValue(), (existingValue, newValue) -> newValue))));
                }
            }
            if (showRetract && his.getEndTime() == null) {
                his.setRecall(isStartRecall(his.getId(), iRequest.getEmployeeCode())
                        || isTaskRecall(his.getId(), iRequest.getEmployeeCode()));
            }
            his.setProcessName(ActivitiMultiLanguageManager.getMultLanguageInfoByCode(his.getProcessName(), iRequest));
        }
        return dataResponse;
    }

    private String getProcessDescription(String processDefinitionId, Map<String, Object> variables) {
        return getProcessDescription(processDefinitionId, null, variables);
    }

    private String getProcessDescription(String processDefinitionId, String executionId) {
        return getProcessDescription(processDefinitionId, executionId, null);
    }

    private String getProcessDescription(String processDefinitionId, String executionId, Map<String, Object> variables) {
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
        String description = bpmnModel.getMainProcess().getDocumentation();
        if (StringUtils.isNotEmpty(description)) {
            description = processEngineConfiguration.getCommandExecutor()
                    .execute(new GetExpressionValueCmd(executionId, description, variables)).toString();
        }
        return description;
    }


    /*
     * @Override public DataResponse queryHistoricProcessInstance(IRequest iRequest,
     * Map<String, String> params) {
     * 
     * if ("involve".equalsIgnoreCase(params.get("queryType"))) {
     * params.put("involvedUser", iRequest.getEmployeeCode());
     * params.remove("startedBy"); } else if
     * ("create".equalsIgnoreCase(params.get("queryType"))) {
     * params.put("startedBy", iRequest.getEmployeeCode());
     * params.remove("involvedUser"); } else if
     * ("any".equalsIgnoreCase(params.get("queryType"))) { if
     * (!isAdmin(iRequest.getEmployeeCode())) { throw new RuntimeException(new
     * WflSecurityException(WflSecurityException.NEED_ASSIGNEE_OR_ADMIN)); } }
     * 
     * DataResponse dataResponse; List<HistoricProcessInstanceResponseExt> list; if
     * ("true".equalsIgnoreCase(params.get("suspend"))) { Map<String, String> param
     * = new HashMap<>(); param.put("suspended", "true"); dataResponse =
     * processInstanceCollectionResource.getProcessInstances(param, fakeRequest); //
     * List<ProcessInstance> processInstances =
     * runtimeService.createProcessInstanceQuery().suspended().list(); list = new
     * ArrayList<>(); for (ProcessInstanceResponseExt processInstance :
     * (List<ProcessInstanceResponseExt>) dataResponse.getData()) {
     * HistoricProcessInstanceResponseExt historicTaskInstanceExt = new
     * HistoricProcessInstanceResponseExt();
     * historicTaskInstanceExt.setId(processInstance.getId());
     * historicTaskInstanceExt.setStartTime(processInstance.getStartTime());
     * historicTaskInstanceExt.setStartUserName(processInstance.getStartUserId());
     * historicTaskInstanceExt.setSuspended(true);
     * historicTaskInstanceExt.setProcessName(processInstance.
     * getProcessDefinitionName());
     * historicTaskInstanceExt.setProcessDefinitionId(processInstance.
     * getProcessDefinitionId()); list.add(historicTaskInstanceExt); }
     * dataResponse.setData(list); } else { dataResponse =
     * historicProcessInstanceCollectionResource.getHistoricProcessInstances(params,
     * fakeRequest); }
     * 
     * 
     * for (HistoricProcessInstanceResponseExt his :
     * (List<HistoricProcessInstanceResponseExt>) dataResponse.getData()) { if
     * (StringUtils.isNotEmpty(his.getStartUserId())) {
     * his.setStartUserName(getEmployeeName(his.getStartUserId())); } //设置全局表单url
     * String key = formService.getStartFormKey(his.getProcessDefinitionId());
     * his.setFormKey(key);
     * 
     * List<Task> tasks =
     * taskService.createTaskQuery().processInstanceId(his.getId()).list(); if
     * (!tasks.isEmpty()) { if( tasks.get(0).getName() != null) { StringBuilder
     * taskName = new StringBuilder(tasks.get(0).getName());
     * his.setTaskName(taskName.toString()); } StringBuilder currentApprover = new
     * StringBuilder(getTaskApprove(tasks.get(0))); for (int i = 1; i <
     * tasks.size(); i++) { currentApprover =
     * currentApprover.append(",").append(getTaskApprove(tasks.get(i))); } //设置当前审批人
     * his.setCurrentApprover(currentApprover.toString());
     * his.setRecall(isStartRecall(his.getId(), iRequest.getEmployeeCode()) ||
     * isTaskRecall(his.getId(), iRequest.getEmployeeCode())); } List<Execution>
     * list1 =
     * runtimeService.createExecutionQuery().processInstanceId(his.getId()).list();
     * for (Execution ls : list1) { if (ls.isSuspended()) { his.setSuspended(true);
     * break; } } // 最后审批人
     *//*
        * List<HistoricTaskInstance> historicTaskInstanceList =
        * historyService.createHistoricTaskInstanceQuery()
        * .processInstanceId(his.getId()).orderByHistoricTaskInstanceEndTime().desc().
        * list(); if (historicTaskInstanceList != null &&
        * !historicTaskInstanceList.isEmpty()) { for (int i = 0; i <
        * historicTaskInstanceList.size(); i++) { HistoricTaskInstanceResponseExt
        * taskLastResponse = new
        * HistoricTaskInstanceResponseExt(historicTaskInstanceList.get(i));
        * List<Comment> comments = getCommentOfType(taskLastResponse.getId(),
        * COMMENT_ACTION); if (comments == null) { continue; } if
        * (StringUtil.isNotEmpty(taskLastResponse.getAssignee()) &&
        * StringUtil.isNotEmpty(comments.get(comments.size() - 1).getFullMessage())) {
        * if (StringUtil.isNotEmpty(comments.get(comments.size() - 1).getUserId())) {
        * his.setLastApprover(getEmployeeName(comments.get(comments.size() -
        * 1).getUserId())); his.setLastApproverCode(comments.get(comments.size() -
        * 1).getUserId()); his.setLastApproveAction(comments.get(comments.size() -
        * 1).getFullMessage()); } break; }
        * 
        * } }
        *//*
           * }
           * 
           * return dataResponse; }
           */
    @Override
    public DataResponse queryHistoricTaskInstances(IRequest iRequest, HistoricTaskInstanceQueryRequest queryRequest,
                                                   Map<String, String> allRequestParams) {
        if (allRequestParams == null) {
            allRequestParams = Collections.emptyMap();
        }

        List<HistoricActivityInstance> datas = historyService.createHistoricActivityInstanceQuery()
                .processInstanceId(queryRequest.getProcessInstanceId()).orderByHistoricActivityInstanceStartTime().asc()
                .list();

        DataResponse dataResponse = new DataResponse();
        List<HistoricTaskInstanceResponseExt> list = new ArrayList<>();

        for (HistoricActivityInstance historicActivityInstance : datas) {
            setHistoricActivityInstanceResponseExt(historicActivityInstance, list, queryRequest.getProcessInstanceId(), iRequest);
        }

        Iterator<HistoricTaskInstanceResponseExt> it = list.iterator();
        while (it.hasNext()) {
            HistoricTaskInstanceResponseExt i = it.next();
            if (null == i.getEndTime()) {
                it.remove();
            }
        }
        boolean key = false;
        HistoricActivityInstance endDom = null;
        for (HistoricActivityInstance ext : datas) {
            if (ext.getDeleteReason() != null) {
                key = true;
                endDom = ext;
                break;
            }
        }
        if (key) {
            HistoricTaskInstanceResponseExt ext = new HistoricTaskInstanceResponseExt();
            if (IActivitiConstants.ACT_RETRACT.equals(endDom.getDeleteReason())) {
                ext.setName("用户撤销");
            } else {
                ext.setName("管理员关闭");
            }
            ext.setAction("终止");
            ext.setEndTime(datas.get(datas.size() - 1).getEndTime());
            list.add(ext);
        }

        dataResponse.setData(list);
        return dataResponse;
    }

    /*
     * protected boolean isAdmin(String userId) { return
     * "ADMIN".equalsIgnoreCase(userId); }
     */

    protected Task getTaskById(String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task == null) {
            throw new ActivitiObjectNotFoundException("Could not find a task with id '" + taskId + "'.", Task.class);
        }
        return task;
    }

    private void setHistoricActivityInstanceResponseExt(HistoricActivityInstance historicActivityInstance,
                                                        List<HistoricTaskInstanceResponseExt> list, String processInstanceId, IRequest request) {
        String activityType = historicActivityInstance.getActivityType();
        if ("userTask".equals(activityType)) {
            List<Comment> comments = getCommentOfType(historicActivityInstance.getTaskId(), PROP_COMMENT);
            List<Comment> actions = getCommentOfType(historicActivityInstance.getTaskId(), COMMENT_ACTION);
            if (comments != null && comments.size() != 0) {
                for (int index = comments.size() - 1; index >= 0; index--) {
                    HistoricTaskInstanceResponseExt historicTaskInstanceResponseExt = new HistoricTaskInstanceResponseExt(
                            historicActivityInstance);
                    CommentEntityImpl commentEntity = (CommentEntityImpl) comments.get(index);
                    historicTaskInstanceResponseExt.setComment(commentEntity.getMessage());
                    historicTaskInstanceResponseExt.setAction(actions.get(index).getFullMessage());
                    historicTaskInstanceResponseExt.setAssignee(actions.get(index).getUserId());
                    StringBuilder sb = new StringBuilder();
                    String temp = getEmployeeName(actions.get(index).getUserId());
                    sb.append(StringUtil.isEmpty(temp) ? "" : temp);
                    sb.append(StringUtil.isEmpty(actions.get(index).getUserId()) ? "" : "(" + actions.get(index).getUserId() + ")");
                    historicTaskInstanceResponseExt.setAssigneeName(sb.toString());
                    historicTaskInstanceResponseExt.setEndTime(actions.get(index).getTime());
                    historicTaskInstanceResponseExt.setName(ActivitiMultiLanguageManager.getMultLanguageInfoByCode(historicTaskInstanceResponseExt.getName(), request));
                    list.add(historicTaskInstanceResponseExt);
                }
            }
            return;
        }
        HistoricTaskInstanceResponseExt historicTaskInstanceResponseExt = new HistoricTaskInstanceResponseExt(
                historicActivityInstance);
        if ("startEvent".equalsIgnoreCase(activityType)
                && StringUtil.isEmpty(historicActivityInstance.getActivityName())) {
            String startUser = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId)
                    .list().get(0).getStartUserId();
            historicTaskInstanceResponseExt.setAssignee(startUser);
            historicTaskInstanceResponseExt.setAssigneeName(getEmployeeName(startUser) + "(" + startUser + ")");
            String start = "开始";
            if (ActivitiMultiLanguageManager.multiLanguageOpen) {
                start = ActivitiMultiLanguageManager.getMultLanguageInfoByCode(MULTI_INFO_START, request);
            }
            historicTaskInstanceResponseExt.setName(start);
        }
        if ("endEvent".equalsIgnoreCase(activityType)
                && StringUtil.isEmpty(historicActivityInstance.getActivityName())) {
            String end = "结束";
            if (ActivitiMultiLanguageManager.multiLanguageOpen) {
                end = ActivitiMultiLanguageManager.getMultLanguageInfoByCode(MULTI_INFO_END, request);
            }
            historicTaskInstanceResponseExt.setName(end);
        }
        if (!"exclusiveGateway".equals(activityType) && !"parallelGateway".equals(activityType)
                && !"eventBasedGateway".equals(activityType) && !"inclusiveGateway".equals(activityType)
                && null == historicActivityInstance.getDeleteReason()) {
            historicTaskInstanceResponseExt.setName(ActivitiMultiLanguageManager.getMultLanguageInfoByCode(historicTaskInstanceResponseExt.getName(), request));
            list.add(historicTaskInstanceResponseExt);
        }
    }

    protected List<Comment> getCommentOfType(String taskId, String type) {
        List<Comment> list = taskService.getTaskComments(taskId, type);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        AutowireCapableBeanFactory autowireCapableBeanFactory = applicationContext.getAutowireCapableBeanFactory();
        autowireCapableBeanFactory.autowireBean(taskResource);
        autowireCapableBeanFactory.autowireBean(executionVariableCollectionResource);
        autowireCapableBeanFactory.autowireBean(taskQueryResource);
        autowireCapableBeanFactory.autowireBean(historicProcessInstanceQueryResource);
        autowireCapableBeanFactory.autowireBean(processInstanceCollectionResource);
        autowireCapableBeanFactory.autowireBean(historicTaskInstanceQueryResource);
        autowireCapableBeanFactory.autowireBean(historicProcessInstanceCollectionResource);
        autowireCapableBeanFactory.autowireBean(deadLetterJobCollectionResource);

        Map<String, ICustomTaskProcessor> listeners = applicationContext.getBeansOfType(ICustomTaskProcessor.class);
        taskProcessors = new ArrayList<>();
        taskProcessors.addAll(listeners.values());
        Collections.sort(taskProcessors);
    }

    @Override
    public Boolean isStartRecall(String procId, String employeeCode) {
        List<Comment> list = taskService.getProcessInstanceComments(procId);
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(procId)
                .suspended().singleResult();
        String userId = historyService.createHistoricProcessInstanceQuery().processInstanceId(procId).list().get(0)
                .getStartUserId();
        if (list.isEmpty() && null == processInstance && employeeCode.equalsIgnoreCase(userId)) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean isTaskRecall(String procId, String employeeCode) {
        //TODO： 检查逻辑
        try {
            // 处于挂起或者结束状态的流程 不能撤回
            ProcessInstance suspendedProc = runtimeService.createProcessInstanceQuery().processInstanceId(procId)
                    .suspended().singleResult();
            if (suspendedProc != null) {
                return false;
            }

            HistoricProcessInstance finishedProc = historyService.createHistoricProcessInstanceQuery()
                    .processInstanceId(procId).finished().singleResult();
            if (finishedProc != null) {
                return false;
            }
            // 加签的任务不能撤回
            List<Task> tasks = taskService.createTaskQuery().processInstanceId(procId).list();
            if (tasks != null) {
                for (Task task : tasks) {
                    if (task.getOwner() != null) {
                        return false;
                    }
                }
            }


            List<HistoricActivityInstance> historicActivityInstances = historyService.createHistoricActivityInstanceQuery()
                    .processInstanceId(procId).orderByHistoricActivityInstanceStartTime().asc().list();
            HistoricActivityInstance lastHistoricActivityInstance = historicActivityInstances
                    .get(historicActivityInstances.size() - 1);
            // 获取活动历史表最后一条记录的executionId 根据executionId找到execution的父execution
            // 找出父execution的孩子execution
            List<Task> taskList = taskService.createTaskQuery().processInstanceId(procId).list();
            if (taskList.size() == 0) {
                return false;
            }
            String executionId = taskList.iterator().next().getExecutionId();
            Execution execution = runtimeService.createExecutionQuery().processInstanceId(procId).executionId(executionId)
                    .singleResult();
            List<Execution> childExecutionList = runtimeService.createExecutionQuery().processInstanceId(procId)
                    .parentId(execution.getParentId()).list();
            // 获取当前处于激活状态的执行器
            String activityId = lastHistoricActivityInstance.getActivityId();
            List<Execution> activeExecutionList = runtimeService.createExecutionQuery().processInstanceId(procId)
                    .activityId(activityId).list();
            // 孩子execution>1 表示当前是多实例 比较孩子execution和激动状态的execution数量 如果数量不一致 表示多实例情况下 有人审批了
            // 不允许撤回
            if (childExecutionList.size() > 1 && activeExecutionList.size() != childExecutionList.size()) {
                return false;
            }

            if ("userTask".equalsIgnoreCase(lastHistoricActivityInstance.getActivityType())
                    && lastHistoricActivityInstance.getEndTime() == null) {
                String multiActivityId = "";
                boolean isLastApprove = true;
                for (int i = historicActivityInstances.size() - 1; i >= 0; i--) {
                    HistoricActivityInstance historicActivityInstance = historicActivityInstances.get(i);
                    String assignee = historicActivityInstance.getAssignee();
                    String activityType = historicActivityInstance.getActivityType();
                    if ("userTask".equalsIgnoreCase(activityType) && historicActivityInstance.getEndTime() == null) {
                        continue;
                    }
                    if ("parallelGateway".equalsIgnoreCase(activityType)
                            || "exclusiveGateway".equalsIgnoreCase(activityType)
                            || "eventBasedGateway".equalsIgnoreCase(activityType)
                            || "inclusiveGateway".equalsIgnoreCase(activityType)) {
                        continue;
                    }
                    // 最近审批的那个人
                    if (isLastApprove) {
                        // 如果当前任务审批人与最近的一次任务审批人为同一人 获取最近审批的act_id
                        if ("userTask".equalsIgnoreCase(activityType) && assignee != null
                                && assignee.equalsIgnoreCase(employeeCode)) {
                            multiActivityId = historicActivityInstance.getActivityId();
                            isLastApprove = false;
                            // 连续两个任务都是同一人审批，且撤回了，最近一次审批可能是撤回操作
                            String deleteReson = historicActivityInstance.getDeleteReason();
                            if ("jump".equalsIgnoreCase(deleteReson)) {
                                return false;
                            }
                            BpmnModel bpmnModel = repositoryService.getBpmnModel(historicActivityInstance.getProcessDefinitionId());
                            FlowElement flowElement = bpmnModel.getMainProcess().getFlowElement(historicActivityInstance.getActivityId());
                            if (!ActivitiUtils.isEnabledRevoke((UserTask) flowElement)) {
                                return false;
                            }
                            continue;
                        } else {
                            return false;
                        }
                    }
                    // 如果相同act_id 连续数量超过1 表示前面是个多实例任务 不允许撤回
                    if (multiActivityId.equalsIgnoreCase(historicActivityInstance.getActivityId())) {
                        return false;
                    } else {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return false;
    }

    @Override
    public void taskRecall(IRequest iRequest, String procId, String employeeCode) {
        List<HistoricActivityInstance> historicActivityInstances = historyService.createHistoricActivityInstanceQuery()
                .processInstanceId(procId).activityType("userTask").orderByHistoricActivityInstanceStartTime().asc().list();
        String taskId = historicActivityInstances.get(historicActivityInstances.size() - 1).getTaskId();
        String activityId = "";
        for (int i = historicActivityInstances.size() - 1; i >= 0; i--) {
            HistoricActivityInstance historicActivityInstance = historicActivityInstances.get(i);
            String assignee = historicActivityInstance.getAssignee();
            String activityType = historicActivityInstance.getActivityType();
            if ("userTask".equalsIgnoreCase(activityType) && historicActivityInstance.getEndTime() == null) {
                continue;
            }
            if ("userTask".equalsIgnoreCase(activityType) && assignee.equalsIgnoreCase(employeeCode)) {
                activityId = historicActivityInstance.getActivityId();
            }
            break;
        }
        ProcessInstance procInstance = runtimeService.createProcessInstanceQuery()
                .processInstanceId(procId).list().iterator().next();
        TaskRecallEvent event = new TaskRecallEvent(new TaskRecallInfo(procInstance, taskService.getVariables(taskId), employeeCode));
        event.setiRequest(iRequest);
        Authentication.setAuthenticatedUserId(employeeCode);
        taskService.addComment(taskId, procId, COMMENT_ACTION, RECALL);
        taskService.addComment(taskId, procId, PROP_COMMENT, employeeCode + "撤回审批");
        JumpActivityCmd cmd = new JumpActivityCmd(taskId, activityId);
        processEngineConfiguration.getCommandExecutor().execute(cmd);
        publisher.publishEvent(event);
    }

    @Override
    public void startRecall(IRequest iRequest, String procId, String employeeCode) {
        String taskId = taskService.createTaskQuery().processInstanceId(procId).list().iterator().next().getId();
        ProcessInstance procInstance = runtimeService.createProcessInstanceQuery()
                .processInstanceId(procId).list().iterator().next();
        TaskRecallEvent event = new TaskRecallEvent(new TaskRecallInfo(procInstance, taskService.getVariables(taskId), employeeCode, true));
        event.setiRequest(iRequest);
        taskService.addComment(taskId, procId, COMMENT_ACTION, RECALL);
        taskService.addComment(taskId, procId, PROP_COMMENT, employeeCode + "撤回审批");
        runtimeService.deleteProcessInstance(procId, IActivitiConstants.ACT_RETRACT);
        publisher.publishEvent(event);
    }

    @Override
    public List<ProcessInstanceForecast> processInstanceForecast(IRequest request, String processInstanceId) {


        Map<String, List<HistoricTaskInstanceResponseExt>> history = new HashMap<>();
        // 获取流程活动历史
        List<HistoricActivityInstance> historicActivityInstanceList = historyService
                .createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).activityType("userTask")
                .orderByHistoricActivityInstanceEndTime().asc().list();
        if (historicActivityInstanceList == null || historicActivityInstanceList.isEmpty()) {
            return null;
        }
        String processDefinitionId = historicActivityInstanceList.get(0).getProcessDefinitionId();
        for (HistoricActivityInstance historicActivityInstance : historicActivityInstanceList) {
            List<HistoricTaskInstanceResponseExt> list = new ArrayList<>();
            setHistoricActivityInstanceResponseExt(historicActivityInstance, list, processInstanceId, request);
            List<HistoricTaskInstanceResponseExt> value = history.get(historicActivityInstance.getActivityId());
            if (value != null && !value.isEmpty()) {
                value.addAll(list);
            } else {
                value = list;
            }
            if (!value.isEmpty()) {
                for (HistoricTaskInstanceResponseExt hisExt : value) {
                    Employee employee = employeeService.queryInfoByCode(hisExt.getAssignee());
                    hisExt.setPositionName(employee.getPositionName());
                    hisExt.setUnitName(employee.getUnitName());
                }
                history.put(historicActivityInstance.getActivityId(), value);
            }
        }

        List<ProcessInstanceForecast> processInstanceForecastList = new ArrayList<>();
        // 获取当前活动节点execution
        List<Execution> executions = runtimeService.createExecutionQuery().processInstanceId(processInstanceId).list();
        boolean isFinish = false;
        //获取正在运行中的任务 审批预测情况
        Map<String, Set<Employee>> forecastTask = new HashMap<>();
        if (executions == null || executions.isEmpty()) {
            isFinish = true;
        } else {
            Execution execution = executions.get(executions.size() - 1);
            ForecastActivityCmd.executionId.set(execution.getId());
            for (Execution execution1 : executions) {
                List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstanceId).executionId(execution1.getId()).list();
                if (CollectionUtils.isNotEmpty(tasks)) {
                    tasks.forEach(task -> {
                        if (forecastTask.containsKey(task.getTaskDefinitionKey())) {
                            forecastTask.get(task.getTaskDefinitionKey()).addAll(getTaskApproveInfo(task));
                        } else {
                            forecastTask.put(task.getTaskDefinitionKey(), getTaskApproveInfo(task));
                        }
                    });
                }
            }
        }
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
        // 从model中获取用户任务信息
        for (UserTask flowNode : bpmnModel.getProcesses().get(0).findFlowElementsOfType(UserTask.class)) {
            ProcessInstanceForecast forecast = new ProcessInstanceForecast();
            String key = flowNode.getId();
            forecast.setGraphicInfo(bpmnModel.getGraphicInfo(key));
            forecast.setTaskId(key);
            forecast.setTaskName(ActivitiMultiLanguageManager.getMultLanguageInfoByCode(flowNode.getName(), request));
            if (isFinish) {
                forecast.setExecuted(true);
            }
            // 判断当前节点是否审批过
            List<HistoricTaskInstanceResponseExt> historyInfo = history.get(key);
            if (historyInfo != null) {
                HistoricTaskInstanceResponseExt info = historyInfo.get(0);
                // 如果是撤回操作，当前节点其实还未审批
                if (!RECALL.equalsIgnoreCase(info.getAction())) {
                    forecast.setExecuted(true);
                }
                //如果当前节点已经有人审批过了 且还有人未审批 将还未审批的人添加到审批预测
                if (forecastTask.containsKey(key)) {
                    forecast.setForecast(forecastTask.get(key));
                }
                forecast.setHistory(historyInfo);
            }
            boolean needFrecast = !forecast.isExecuted();
            boolean isApproveChain = isApproveChain(flowNode, processDefinitionId);
            int executedCount = 0;
            // 审批链情况 获取执行过的审批链数量
            if (isApproveChain) {
                if (historyInfo != null) {
                    boolean hasAddSign = false;
                    for (HistoricTaskInstanceResponseExt index : historyInfo) {
                        if (ADD_SIGN.equalsIgnoreCase(index.getAction())) {
                            hasAddSign = true;
                        }
                        if (ADD_SIGN.equalsIgnoreCase(index.getAction())
                                || DELEGATE.equalsIgnoreCase(index.getAction())
                                || JUMP.equalsIgnoreCase(index.getAction())
                                || RECALL.equalsIgnoreCase(index.getAction())
                                || AUTO_DELEGATE.equalsIgnoreCase(index.getAction())) {
                        } else {
                            // 加签的人审批
                            if (hasAddSign) {
                                hasAddSign = false;
                            } else {
                                executedCount++;
                            }
                        }
                    }
                }
            }

            ForecastActivityCmd.executedCount.set(executedCount);
            if (needFrecast) {
                ForecastActivityCmd.userTask.set(flowNode);
                boolean unknown = false;
                Set<Employee> approve = (Set) processEngineConfiguration.getCommandExecutor()
                        .execute(forecastActivityCmd);
                if (approve == null || approve.isEmpty()) {
                    unknown = true;
                }
                // 审批链情况，预测结果是当前审批者和表达式预测
                if (forecast.getForecast() == null) {
                    forecast.setForecast(approve);
                } else if (!unknown) {
                    forecast.getForecast().addAll(approve);
                }
                ForecastActivityCmd.userTask.remove();
            }
            processInstanceForecastList.add(forecast);
        }
        if (ForecastActivityCmd.executionId.get() != null) {
            ForecastActivityCmd.executionId.remove();
        }
        if (ForecastActivityCmd.executedCount.get() != null) {
            ForecastActivityCmd.executedCount.remove();
        }
        return processInstanceForecastList;
    }

    private boolean isApproveChain(UserTask task, String processdefinitionid) {
        if (ActivitiUtils.isUseNewModelEditor(task) || !ActivitiUtils.isAddApproveChain(task)) {
            return false;
        }
        ApproveChainHeader approveChainHeader = approveChainHeaderService
                .selectByUserTask(StringUtils.substringBefore(processdefinitionid, ":"), task.getId());
        if (approveChainHeader == null) {
            return false;
        }
        return true;
    }

    @Override
    public List<ActiviException> queryException(ActiviException exception, int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        List<ActiviException> list = exceptionMapper.selectAllException(exception);
        return list;
    }

    @Override
    public void executeTaskByAdmin(IRequest request, String procId, TaskActionRequestExt taskActionRequest)
            throws TaskActionException {
        // Task task =
        // taskService.createTaskQuery().processInstanceId(procId).list().get(0);
        this.executeTaskAction(request, taskActionRequest.getCurrentTaskId(), taskActionRequest, true);
    }

    private String processApproveName(String userId) {
        String employeeName = getEmployeeName(userId);
        if (StringUtils.isNotEmpty(employeeName)) {
            return getEmployeeName(userId) + "(" + userId + ")";
        } else {
            return userId;
        }
    }

    private String getTaskApprove(Task task) {
        if (StringUtils.isNotEmpty(task.getAssignee())) {
            return processApproveName(task.getAssignee());
        } else {
            List<IdentityLink> idList = taskService.getIdentityLinksForTask(task.getId());
            List<String> nameList = new ArrayList<>();
            for (IdentityLink il : idList) {
                if (il.getGroupId() != null) {
                    nameList.add(getGroupName(il.getGroupId()));
                } else if (il.getUserId() != null) {
                    nameList.add(processApproveName(il.getUserId()));
                }
            }
            return StringUtils.join(nameList.toArray(), ";");
        }
    }

    private Set<Employee> getTaskApproveInfo(Task task) {
        Set<Employee> employees = new LinkedHashSet<>();
        if (StringUtils.isNotEmpty(task.getAssignee())) {
            Employee e = employeeService.queryInfoByCode(task.getAssignee());
            if (e != null) {
                employees.add(e);
            }
        } else {
            List<IdentityLink> idList = taskService.getIdentityLinksForTask(task.getId());
            for (IdentityLink il : idList) {
                if (il.getGroupId() != null) {
                    List<Employee> emps = employeeService.selectByPostionCode(il.getGroupId());
                    employees.addAll(emps);
                } else if (il.getUserId() != null) {
                    Employee e = employeeService.queryInfoByCode(il.getUserId());
                    if (e != null) {
                        employees.add(e);
                    }
                }
            }
        }
        return employees;
    }

}
