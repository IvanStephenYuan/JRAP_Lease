package com.jingrui.jrap.activiti.custom.process;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import com.jingrui.jrap.activiti.components.ActivitiMultiLanguageManager;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.impl.HistoricProcessInstanceQueryProperty;
import org.activiti.engine.query.QueryProperty;
import org.activiti.rest.common.api.DataResponse;
import org.activiti.rest.service.api.history.HistoricProcessInstancePaginateList;
import org.activiti.rest.service.api.history.HistoricProcessInstanceQueryRequest;
import org.activiti.rest.service.api.history.HistoricProcessInstanceQueryResource;

/**
 * @author njq.niu@jingrui.com
 */
public class CustomHistoricProcessInstanceQueryResource extends HistoricProcessInstanceQueryResource {


    private static Map<String, QueryProperty> allowedSortProperties = new HashMap<>();

    static {
        allowedSortProperties.put("processInstanceId", HistoricProcessInstanceQueryProperty.PROCESS_INSTANCE_ID_);
        allowedSortProperties.put("processDefinitionId", HistoricProcessInstanceQueryProperty.PROCESS_DEFINITION_ID);
        allowedSortProperties.put("businessKey", HistoricProcessInstanceQueryProperty.BUSINESS_KEY);
        allowedSortProperties.put("startTime", HistoricProcessInstanceQueryProperty.START_TIME);
        allowedSortProperties.put("endTime", HistoricProcessInstanceQueryProperty.END_TIME);
        allowedSortProperties.put("duration", HistoricProcessInstanceQueryProperty.DURATION);
        allowedSortProperties.put("tenantId", HistoricProcessInstanceQueryProperty.TENANT_ID);
        allowedSortProperties.put("readFlag", new HistoricProcessInstanceQueryProperty("READ_FLAG_"));
    }

    protected DataResponse getQueryResponse(HistoricProcessInstanceQueryRequest queryRequest, Map<String, String> allRequestParams) {
        HistoricProcessInstanceQuery query = historyService.createHistoricProcessInstanceQuery();

        if (queryRequest instanceof CustomHistoricProcessInstanceQueryRequest) {
            CustomHistoricProcessInstanceQueryRequest customHistoricProcessInstanceQueryRequest = (CustomHistoricProcessInstanceQueryRequest) queryRequest;
            String processDefinitionName = customHistoricProcessInstanceQueryRequest.getProcessDefinitionNameLike();
            if (processDefinitionName != null) {
                query.processDefinitionName(processDefinitionName);
            }

            String startUserName = customHistoricProcessInstanceQueryRequest.getStartUserName();
            String carbonCopyUser = customHistoricProcessInstanceQueryRequest.getCarbonCopyUser();
            String readFlag = customHistoricProcessInstanceQueryRequest.getReadFlag();
            if (query instanceof CustomHistoricProcessInstanceQuery) {
                CustomHistoricProcessInstanceQuery customHistoricProcessInstanceQuery = (CustomHistoricProcessInstanceQuery) query;
                if (startUserName != null) {
                    customHistoricProcessInstanceQuery.setStartUserName(startUserName);
                }
                if (carbonCopyUser != null) {
                    customHistoricProcessInstanceQuery.setCarbonCopyUser(carbonCopyUser);
                }
                if (readFlag != null) {
                    customHistoricProcessInstanceQuery.setReadFlag(readFlag);
                }
                customHistoricProcessInstanceQuery.setMultiLanguageOpen(ActivitiMultiLanguageManager.multiLanguageOpen);
                customHistoricProcessInstanceQuery.setSuspended(customHistoricProcessInstanceQueryRequest.isSuspended());

            }
        }


        // Populate query based on request
        if (queryRequest.getProcessInstanceId() != null) {
            query.processInstanceId(queryRequest.getProcessInstanceId());
        }
        if (queryRequest.getProcessInstanceIds() != null && !queryRequest.getProcessInstanceIds().isEmpty()) {
            query.processInstanceIds(new HashSet<String>(queryRequest.getProcessInstanceIds()));
        }
        if (queryRequest.getProcessDefinitionKey() != null) {
            query.processDefinitionKey(queryRequest.getProcessDefinitionKey());
        }
        if (queryRequest.getProcessDefinitionId() != null) {
            query.processDefinitionId(queryRequest.getProcessDefinitionId());
        }
        if (queryRequest.getProcessBusinessKey() != null) {
            query.processInstanceBusinessKey(queryRequest.getProcessBusinessKey());
        }
        if (queryRequest.getInvolvedUser() != null) {
            query.involvedUser(queryRequest.getInvolvedUser());
        }
        if (queryRequest.getSuperProcessInstanceId() != null) {
            query.superProcessInstanceId(queryRequest.getSuperProcessInstanceId());
        }
        if (queryRequest.getExcludeSubprocesses() != null) {
            query.excludeSubprocesses(queryRequest.getExcludeSubprocesses());
        }
        if (queryRequest.getFinishedAfter() != null) {
            query.finishedAfter(queryRequest.getFinishedAfter());
        }
        if (queryRequest.getFinishedBefore() != null) {
            query.finishedBefore(queryRequest.getFinishedBefore());
        }
        if (queryRequest.getStartedAfter() != null) {
            query.startedAfter(queryRequest.getStartedAfter());
        }
        if (queryRequest.getStartedBefore() != null) {
            query.startedBefore(queryRequest.getStartedBefore());
        }
        if (queryRequest.getStartedBy() != null) {
            query.startedBy(queryRequest.getStartedBy());
        }
        if (queryRequest.getFinished() != null) {
            if (queryRequest.getFinished()) {
                query.finished();
            } else {
                query.unfinished();
            }
        }
        if (queryRequest.getIncludeProcessVariables() != null) {
            if (queryRequest.getIncludeProcessVariables()) {
                query.includeProcessVariables();
            }
        }
        if (queryRequest.getVariables() != null) {
            addVariables(query, queryRequest.getVariables());
        }

        if (queryRequest.getTenantId() != null) {
            query.processInstanceTenantId(queryRequest.getTenantId());
        }

        if (queryRequest.getTenantIdLike() != null) {
            query.processInstanceTenantIdLike(queryRequest.getTenantIdLike());
        }

        if (Boolean.TRUE.equals(queryRequest.getWithoutTenantId())) {
            query.processInstanceWithoutTenantId();
        }

        return new HistoricProcessInstancePaginateList(restResponseFactory).paginateList(allRequestParams, queryRequest, query, "processInstanceId", allowedSortProperties);
    }

    public DataResponse getQueryResponse(boolean custom, HistoricProcessInstanceQueryRequest queryRequest, Map<String, String> allRequestParams) {
        return getQueryResponse(queryRequest, allRequestParams);
    }
}
