package com.jingrui.jrap.activiti.custom.task;

import org.activiti.engine.impl.TaskQueryProperty;
import org.activiti.engine.query.QueryProperty;
import org.activiti.engine.task.DelegationState;
import org.activiti.engine.task.TaskQuery;
import org.activiti.rest.common.api.DataResponse;
import org.activiti.rest.service.api.runtime.task.TaskBaseResource;
import org.activiti.rest.service.api.runtime.task.TaskPaginateList;
import org.activiti.rest.service.api.runtime.task.TaskQueryRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author njq.niu@jingrui.com
 */
@RestController
public class CustomTaskQueryResource extends TaskBaseResource {

    private static HashMap<String, QueryProperty> properties = new HashMap<String, QueryProperty>();

    static {
        properties.put("id", TaskQueryProperty.TASK_ID);
        properties.put("name", TaskQueryProperty.NAME);
        properties.put("description", TaskQueryProperty.DESCRIPTION);
        properties.put("dueDate", TaskQueryProperty.DUE_DATE);
        properties.put("createTime", TaskQueryProperty.CREATE_TIME);
        properties.put("priority", TaskQueryProperty.PRIORITY);
        properties.put("executionId", TaskQueryProperty.EXECUTION_ID);
        properties.put("processInstanceId", TaskQueryProperty.PROCESS_INSTANCE_ID);
        properties.put("tenantId", TaskQueryProperty.TENANT_ID);
    }

    @RequestMapping(value = "/query/tasks", method = RequestMethod.POST, produces = "application/json")
    public DataResponse getQueryResult(@RequestBody TaskQueryRequest request, @RequestParam Map<String, String> requestParams, HttpServletRequest httpRequest) {

        return getTasksFromQueryRequest(request, requestParams);
    }

    protected DataResponse getTasksFromQueryRequest(TaskQueryRequest request, Map<String, String> requestParams) {
        TaskQuery taskQuery = taskService.createTaskQuery();
        if (taskQuery instanceof CustomTaskQuery && request instanceof CustomTaskQueryRequest) {
            String startUserName = ((CustomTaskQueryRequest) request).getStartUserName();
            if (startUserName != null) {
                ((CustomTaskQuery) taskQuery).setStartUserName(startUserName);
            }
        }
        // Populate filter-parameters
        if (request.getName() != null) {
            taskQuery.taskName(request.getName());
        }
        if (request.getNameLike() != null) {
            taskQuery.taskNameLike(request.getNameLike());
        }
        if (request.getDescription() != null) {
            taskQuery.taskDescription(request.getDescription());
        }
        if (request.getDescriptionLike() != null) {
            taskQuery.taskDescriptionLike(request.getDescriptionLike());
        }
        if (request.getPriority() != null) {
            taskQuery.taskPriority(request.getPriority());
        }
        if (request.getMinimumPriority() != null) {
            taskQuery.taskMinPriority(request.getMinimumPriority());
        }
        if (request.getMaximumPriority() != null) {
            taskQuery.taskMaxPriority(request.getMaximumPriority());
        }
        if (request.getAssignee() != null) {
            taskQuery.taskAssignee(request.getAssignee());
        }
        if (request.getAssigneeLike() != null) {
            taskQuery.taskAssigneeLike(request.getAssigneeLike());
        }
        if (request.getOwner() != null) {
            taskQuery.taskOwner(request.getOwner());
        }
        if (request.getOwnerLike() != null) {
            taskQuery.taskOwnerLike(request.getOwnerLike());
        }
        if (request.getUnassigned() != null) {
            taskQuery.taskUnassigned();
        }
        if (request.getDelegationState() != null) {
            DelegationState state = getDelegationState(request.getDelegationState());
            if (state != null) {
                taskQuery.taskDelegationState(state);
            }
        }
        if (request.getCandidateUser() != null) {
            taskQuery.taskCandidateUser(request.getCandidateUser());
        }
        if (request.getInvolvedUser() != null) {
            taskQuery.taskInvolvedUser(request.getInvolvedUser());
        }
        if (request.getCandidateGroup() != null) {
            taskQuery.taskCandidateGroup(request.getCandidateGroup());
        }
        if (request.getCandidateGroupIn() != null) {
            taskQuery.taskCandidateGroupIn(request.getCandidateGroupIn());
        }
        if (request.getProcessInstanceId() != null) {
            taskQuery.processInstanceId(request.getProcessInstanceId());
        }
        if (request.getProcessInstanceBusinessKey() != null) {
            taskQuery.processInstanceBusinessKey(request.getProcessInstanceBusinessKey());
        }
        if (request.getExecutionId() != null) {
            taskQuery.executionId(request.getExecutionId());
        }
        if (request.getCreatedOn() != null) {
            taskQuery.taskCreatedOn(request.getCreatedOn());
        }
        if (request.getCreatedBefore() != null) {
            taskQuery.taskCreatedBefore(request.getCreatedBefore());
        }
        if (request.getCreatedAfter() != null) {
            taskQuery.taskCreatedAfter(request.getCreatedAfter());
        }
        if (request.getExcludeSubTasks() != null) {
            if (request.getExcludeSubTasks().booleanValue()) {
                taskQuery.excludeSubtasks();
            }
        }

        if (request.getTaskDefinitionKey() != null) {
            taskQuery.taskDefinitionKey(request.getTaskDefinitionKey());
        }

        if (request.getTaskDefinitionKeyLike() != null) {
            taskQuery.taskDefinitionKeyLike(request.getTaskDefinitionKeyLike());
        }
        if (request.getDueDate() != null) {
            taskQuery.taskDueDate(request.getDueDate());
        }
        if (request.getDueBefore() != null) {
            taskQuery.taskDueBefore(request.getDueBefore());
        }
        if (request.getDueAfter() != null) {
            taskQuery.taskDueAfter(request.getDueAfter());
        }
        if (request.getWithoutDueDate() != null && request.getWithoutDueDate()) {
            taskQuery.withoutTaskDueDate();
        }

        if (request.getActive() != null) {
            if (request.getActive().booleanValue()) {
                taskQuery.active();
            } else {
                taskQuery.suspended();
            }
        }

        if (request.getIncludeTaskLocalVariables() != null) {
            if (request.getIncludeTaskLocalVariables()) {
                taskQuery.includeTaskLocalVariables();
            }
        }
        if (request.getIncludeProcessVariables() != null) {
            if (request.getIncludeProcessVariables()) {
                taskQuery.includeProcessVariables();
            }
        }

        if (request.getProcessInstanceBusinessKeyLike() != null) {
            taskQuery.processInstanceBusinessKeyLike(request.getProcessInstanceBusinessKeyLike());
        }

        if (request.getProcessDefinitionId() != null) {
            taskQuery.processDefinitionId(request.getProcessDefinitionId());
        }

        if (request.getProcessDefinitionKey() != null) {
            taskQuery.processDefinitionKey(request.getProcessDefinitionKey());
        }

        if (request.getProcessDefinitionKeyLike() != null) {
            taskQuery.processDefinitionKeyLike(request.getProcessDefinitionKeyLike());
        }

        if (request.getProcessDefinitionName() != null) {
            taskQuery.processDefinitionName(request.getProcessDefinitionName());
        }

        if (request.getProcessDefinitionNameLike() != null) {
            taskQuery.processDefinitionNameLike(request.getProcessDefinitionNameLike());
        }

        if (request.getTaskVariables() != null) {
            addTaskvariables(taskQuery, request.getTaskVariables());
        }

        if (request.getProcessInstanceVariables() != null) {
            addProcessvariables(taskQuery, request.getProcessInstanceVariables());
        }

        if (request.getTenantId() != null) {
            taskQuery.taskTenantId(request.getTenantId());
        }

        if (request.getTenantIdLike() != null) {
            taskQuery.taskTenantIdLike(request.getTenantIdLike());
        }

        if (Boolean.TRUE.equals(request.getWithoutTenantId())) {
            taskQuery.taskWithoutTenantId();
        }

        if (request.getCandidateOrAssigned() != null) {
            taskQuery.taskCandidateOrAssigned(request.getCandidateOrAssigned());
        }

        if (request.getCategory() != null) {
            taskQuery.taskCategory(request.getCategory());
        }

        return new TaskPaginateList(restResponseFactory).paginateList(requestParams, request, taskQuery, "id", properties);
    }
}
