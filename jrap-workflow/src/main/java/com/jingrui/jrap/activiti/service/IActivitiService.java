package com.jingrui.jrap.activiti.service;

import com.jingrui.jrap.activiti.dto.*;
import com.jingrui.jrap.activiti.exception.TaskActionException;
import com.jingrui.jrap.activiti.exception.WflSecurityException;
import com.jingrui.jrap.activiti.exception.dto.ActiviException;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.core.ProxySelf;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.repository.Model;
import org.activiti.engine.task.Task;
import org.activiti.rest.common.api.DataResponse;
import org.activiti.rest.service.api.history.HistoricProcessInstanceQueryRequest;
import org.activiti.rest.service.api.history.HistoricTaskInstanceQueryRequest;
import org.activiti.rest.service.api.runtime.process.ProcessInstanceCreateRequest;
import org.activiti.rest.service.api.runtime.process.ProcessInstanceResponse;
import org.activiti.rest.service.api.runtime.task.TaskQueryRequest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @author shengyang.zhou@jingrui.com
 */
public interface IActivitiService extends ProxySelf<IActivitiService> {

    ProcessInstanceResponse startProcess(IRequest iRequest, ProcessInstanceCreateRequest createRequest);

    Model deployModel(String modelId) throws Exception;

    void completeTask(IRequest request, Task taskEntity, TaskActionRequestExt actionRequest) throws TaskActionException;

    void delegateTask(IRequest request, Task taskEntity, TaskActionRequestExt actionRequest) throws TaskActionException;

    void carbonCopy(IRequest request, Task taskEntity, TaskActionRequestExt actionRequest) throws TaskActionException;

    void resolveTask(IRequest request, Task taskEntity, TaskActionRequestExt actionRequest) throws TaskActionException;

    void jumpTo(IRequest request, Task taskEntity, TaskActionRequestExt actionRequest);

    @Transactional
    void executeTaskAction(IRequest request, String taskId, TaskActionRequestExt taskActionRequest, boolean isAdmin)
            throws TaskActionException;

    List<ActivitiNode> getProcessNodes(IRequest request, String processDefinitionId);

    List<ActivitiNode> getUserTaskFromModelSource(IRequest request, String modelId);

    String getEmployeeName(String userId);

    String getGroupName(String groupId);

    TaskResponseExt getTaskDetails(IRequest request, String taskId) throws WflSecurityException;

    TaskResponseExt getTaskDetails(IRequest request, String taskId, boolean isAdmin) throws WflSecurityException;

    HistoricProcessInstanceResponseExt getInstanceDetail(IRequest request, String processInstanceId);

    DataResponse queryTaskList(IRequest iRequest, TaskQueryRequest taskQueryRequest,
                               Map<String, String> requestParams);

    public DataResponse queryProcessInstances(IRequest iRequest, HistoricProcessInstanceQueryRequest historicProcessInstanceQueryRequest, Map<String, String> requestParams, boolean showRetract);

  /*  DataResponse queryHistoricProcessInstance(IRequest iRequest, Map<String, String> params);*/

    DataResponse queryHistoricTaskInstances(IRequest iRequest, HistoricTaskInstanceQueryRequest queryRequest,
                                            @RequestParam Map<String, String> allRequestParams);

    Boolean isStartRecall(String procId, String employeeCode);

    Boolean isTaskRecall(String procId, String employeeCode);

    @Transactional(rollbackFor = Exception.class)
    void taskRecall(IRequest iRequest, String procId, String employeeCode);

    @Transactional(rollbackFor = Exception.class)
    void startRecall(IRequest iRequest, String procId, String employeeCode);

    List<ActiviException> queryException(ActiviException exception, int page, int pagesize);

    void executeTaskByAdmin(IRequest request, String procId, TaskActionRequestExt taskActionRequest)
            throws TaskActionException;

    List<ProcessInstanceForecast> processInstanceForecast(IRequest request, String processInstanceId);

    @Transactional(propagation = Propagation.REQUIRES_NEW, noRollbackFor = Exception.class)
    void saveException(String taskId, ActivitiException exception);

    void deleteDeployment(String deploymentId, Boolean cascade);

    void deleteProcessInstance(String processInstanceId);

    void processCarbonCopyRead(String processInstanceId, String employeeCode);

}
