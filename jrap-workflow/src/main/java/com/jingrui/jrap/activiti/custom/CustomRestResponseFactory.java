package com.jingrui.jrap.activiti.custom;

import com.jingrui.jrap.activiti.custom.process.HistoricProcessInstanceEntityImpl;
import com.jingrui.jrap.activiti.dto.*;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.persistence.entity.SuspensionState;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.rest.service.api.RestResponseFactory;
import org.activiti.rest.service.api.RestUrlBuilder;
import org.activiti.rest.service.api.RestUrls;
import org.activiti.rest.service.api.engine.variable.RestVariable;
import org.activiti.rest.service.api.history.HistoricProcessInstanceResponse;
import org.activiti.rest.service.api.history.HistoricTaskInstanceResponse;
import org.activiti.rest.service.api.repository.DeploymentResponse;
import org.activiti.rest.service.api.repository.ModelResponse;
import org.activiti.rest.service.api.runtime.process.ProcessInstanceResponse;
import org.activiti.rest.service.api.runtime.task.TaskResponse;

import java.util.Map;

/**
 * @author shengyang.zhou@jingrui.com
 * @author njq.niu@jingrui.com
 */
public class CustomRestResponseFactory extends RestResponseFactory {

    @Override
    public TaskResponse createTaskResponse(Task task, RestUrlBuilder urlBuilder) {
        TaskResponseExt response = new TaskResponseExt(task);
        response.setUrl(urlBuilder.buildUrl(RestUrls.URL_TASK, task.getId()));

        // Add references to other resources, if needed
        if (response.getParentTaskId() != null) {
            response.setParentTaskUrl(urlBuilder.buildUrl(RestUrls.URL_TASK, response.getParentTaskId()));
        }
        if (response.getProcessDefinitionId() != null) {
            response.setProcessDefinitionUrl(urlBuilder.buildUrl(RestUrls.URL_PROCESS_DEFINITION, response.getProcessDefinitionId()));
        }
        if (response.getExecutionId() != null) {
            response.setExecutionUrl(urlBuilder.buildUrl(RestUrls.URL_EXECUTION, response.getExecutionId()));
        }
        if (response.getProcessInstanceId() != null) {
            response.setProcessInstanceUrl(urlBuilder.buildUrl(RestUrls.URL_PROCESS_INSTANCE, response.getProcessInstanceId()));
        }

        if (task.getProcessVariables() != null) {
            Map<String, Object> variableMap = task.getProcessVariables();
            for (String name : variableMap.keySet()) {
                response.addVariable(createRestVariable(name, variableMap.get(name), RestVariable.RestVariableScope.GLOBAL, task.getId(), VARIABLE_TASK, false, urlBuilder));
            }
        }
        if (task.getTaskLocalVariables() != null) {
            Map<String, Object> variableMap = task.getTaskLocalVariables();
            for (String name : variableMap.keySet()) {
                response.addVariable(createRestVariable(name, variableMap.get(name), RestVariable.RestVariableScope.LOCAL, task.getId(), VARIABLE_TASK, false, urlBuilder));
            }
        }

        return response;
    }

    @Override
    public HistoricTaskInstanceResponse createHistoricTaskInstanceResponse(HistoricTaskInstance taskInstance, RestUrlBuilder urlBuilder) {
        HistoricTaskInstanceResponseExt result = new HistoricTaskInstanceResponseExt(taskInstance);
        if (taskInstance.getProcessDefinitionId() != null) {
            result.setProcessDefinitionUrl(urlBuilder.buildUrl(RestUrls.URL_PROCESS_DEFINITION, taskInstance.getProcessDefinitionId()));
        }
        result.setProcessInstanceId(taskInstance.getProcessInstanceId());
        if (taskInstance.getProcessInstanceId() != null) {
            result.setProcessInstanceUrl(urlBuilder.buildUrl(RestUrls.URL_HISTORIC_PROCESS_INSTANCE, taskInstance.getProcessInstanceId()));
        }
        result.setStartTime(taskInstance.getStartTime());
        result.setTaskDefinitionKey(taskInstance.getTaskDefinitionKey());
        result.setWorkTimeInMillis(taskInstance.getWorkTimeInMillis());
        result.setUrl(urlBuilder.buildUrl(RestUrls.URL_HISTORIC_TASK_INSTANCE, taskInstance.getId()));
        if (taskInstance.getProcessVariables() != null) {
            Map<String, Object> variableMap = taskInstance.getProcessVariables();
            for (String name : variableMap.keySet()) {
                result.addVariable(createRestVariable(name, variableMap.get(name), RestVariable.RestVariableScope.GLOBAL, taskInstance.getId(), VARIABLE_HISTORY_TASK, false, urlBuilder));
            }
        }
        if (taskInstance.getTaskLocalVariables() != null) {
            Map<String, Object> variableMap = taskInstance.getTaskLocalVariables();
            for (String name : variableMap.keySet()) {
                result.addVariable(createRestVariable(name, variableMap.get(name), RestVariable.RestVariableScope.LOCAL, taskInstance.getId(), VARIABLE_HISTORY_TASK, false, urlBuilder));
            }
        }
        return result;
    }

    @Override
    public HistoricProcessInstanceResponse createHistoricProcessInstanceResponse(HistoricProcessInstance processInstance, RestUrlBuilder urlBuilder) {
        HistoricProcessInstanceResponseExt result = new HistoricProcessInstanceResponseExt();
        result.setProcessName(processInstance.getProcessDefinitionName());

        result.setBusinessKey(processInstance.getBusinessKey());
        result.setDeleteReason(processInstance.getDeleteReason());
        result.setDurationInMillis(processInstance.getDurationInMillis());
        result.setEndActivityId(processInstance.getEndActivityId());
        result.setEndTime(processInstance.getEndTime());
        result.setId(processInstance.getId());
        result.setProcessDefinitionId(processInstance.getProcessDefinitionId());
        result.setProcessDefinitionUrl(urlBuilder.buildUrl(RestUrls.URL_PROCESS_DEFINITION, processInstance.getProcessDefinitionId()));
        result.setStartActivityId(processInstance.getStartActivityId());
        result.setStartTime(processInstance.getStartTime());
        result.setStartUserId(processInstance.getStartUserId());
        result.setSuperProcessInstanceId(processInstance.getSuperProcessInstanceId());
        result.setUrl(urlBuilder.buildUrl(RestUrls.URL_HISTORIC_PROCESS_INSTANCE, processInstance.getId()));
        if (processInstance.getProcessVariables() != null) {
            Map<String, Object> variableMap = processInstance.getProcessVariables();
            for (String name : variableMap.keySet()) {
                result.addVariable(createRestVariable(name, variableMap.get(name), RestVariable.RestVariableScope.LOCAL, processInstance.getId(), VARIABLE_HISTORY_PROCESS, false, urlBuilder));
            }
        }
        result.setTenantId(processInstance.getTenantId());


        if (processInstance instanceof HistoricProcessInstanceEntityImpl) {
            HistoricProcessInstanceEntityImpl customHistoricProcessInstanceEntity = (HistoricProcessInstanceEntityImpl) processInstance;
            result.setTaskDefKey(customHistoricProcessInstanceEntity.getTaskDefKey());
            if (customHistoricProcessInstanceEntity.getSuspensionState() != null) {
                result.setSuspended(SuspensionState.SUSPENDED.getStateCode() == customHistoricProcessInstanceEntity.getSuspensionState());
            }
            result.setReadFlag(customHistoricProcessInstanceEntity.getReadFlag());

        }
        return result;
    }

    @Override
    public ProcessInstanceResponse createProcessInstanceResponse(ProcessInstance processInstance, RestUrlBuilder urlBuilder) {
        ProcessInstanceResponseExt result = new ProcessInstanceResponseExt();
        result.setStartTime(processInstance.getStartTime());
        result.setStartUserId(processInstance.getStartUserId());
        result.setProcessDefinitionName(processInstance.getProcessDefinitionName());

        result.setActivityId(processInstance.getActivityId());
        result.setBusinessKey(processInstance.getBusinessKey());
        result.setId(processInstance.getId());
        result.setProcessDefinitionId(processInstance.getProcessDefinitionId());
        result.setProcessDefinitionUrl(urlBuilder.buildUrl(RestUrls.URL_PROCESS_DEFINITION, processInstance.getProcessDefinitionId()));
        result.setEnded(processInstance.isEnded());
        result.setSuspended(processInstance.isSuspended());
        result.setUrl(urlBuilder.buildUrl(RestUrls.URL_PROCESS_INSTANCE, processInstance.getId()));
        result.setTenantId(processInstance.getTenantId());

        // Added by Ryan Johnston
        if (processInstance.isEnded()) {
            // Process complete. Note the same in the result.
            result.setCompleted(true);
        } else {
            // Process not complete. Note the same in the result.
            result.setCompleted(false);
        }
        // End Added by Ryan Johnston

        if (processInstance.getProcessVariables() != null) {
            Map<String, Object> variableMap = processInstance.getProcessVariables();
            for (String name : variableMap.keySet()) {
                result.addVariable(createRestVariable(name, variableMap.get(name), RestVariable.RestVariableScope.LOCAL, processInstance.getId(), VARIABLE_PROCESS, false, urlBuilder));
            }
        }

        return result;
    }

    @Override
    public ModelResponse createModelResponse(Model model, RestUrlBuilder urlBuilder) {
        ModelResponseExt response = new ModelResponseExt();
        response.setCategory(model.getCategory());
        response.setCreateTime(model.getCreateTime());
        response.setId(model.getId());
        response.setKey(model.getKey());
        response.setLastUpdateTime(model.getLastUpdateTime());
        response.setMetaInfo(model.getMetaInfo());
        response.setName(model.getName());
        response.setDeploymentId(model.getDeploymentId());
        response.setVersion(model.getVersion());
        response.setTenantId(model.getTenantId());

        response.setUrl(urlBuilder.buildUrl(RestUrls.URL_MODEL, model.getId()));
        if (model.getDeploymentId() != null) {
            response.setDeploymentUrl(urlBuilder.buildUrl(RestUrls.URL_DEPLOYMENT, model.getDeploymentId()));
        }

        if (model.hasEditorSource()) {
            response.setSourceUrl(urlBuilder.buildUrl(RestUrls.URL_MODEL_SOURCE, model.getId()));
        }

        if (model.hasEditorSourceExtra()) {
            response.setSourceExtraUrl(urlBuilder.buildUrl(RestUrls.URL_MODEL_SOURCE_EXTRA, model.getId()));
        }
        return response;
    }

    @Override
    public DeploymentResponse createDeploymentResponse(Deployment deployment, RestUrlBuilder urlBuilder) {
        return new DeploymentResponseExt(deployment, urlBuilder.buildUrl(RestUrls.URL_DEPLOYMENT, deployment.getId()));
    }
}
