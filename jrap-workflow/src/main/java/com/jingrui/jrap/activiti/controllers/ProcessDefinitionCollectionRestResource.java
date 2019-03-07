package com.jingrui.jrap.activiti.controllers;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import com.jingrui.jrap.activiti.components.ActivitiMultiLanguageManager;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.core.impl.RequestHelper;
import io.swagger.annotations.ApiParam;
import org.activiti.rest.common.api.DataResponse;
import org.activiti.rest.service.api.repository.ProcessDefinitionCollectionResource;
import org.activiti.rest.service.api.repository.ProcessDefinitionResponse;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qiang.zeng
 * @date 2018/9/13.
 */
@RestController
@RequestMapping(value = {"/wfl", "/api/wfl"})
public class ProcessDefinitionCollectionRestResource extends ProcessDefinitionCollectionResource {


    @RequestMapping(value = "/repository/process-definitions", method = RequestMethod.GET, produces = "application/json")
    public DataResponse getProcessDefinitions(@ApiParam(hidden = true) @RequestParam Map<String, String> allRequestParams, HttpServletRequest request) {
        IRequest iRequest = RequestHelper.createServiceRequest(request);
        if(ActivitiMultiLanguageManager.multiLanguageOpen){
            if (allRequestParams.containsKey("nameLike")) {
                allRequestParams.put("nameLike",ActivitiMultiLanguageManager.getMultiLanguageInfoByDescription(allRequestParams.get("nameLike"),iRequest));
            }
        }
        DataResponse dataResponse = super.getProcessDefinitions(allRequestParams, request);
        if (ActivitiMultiLanguageManager.multiLanguageOpen && CollectionUtils.isNotEmpty((List) dataResponse.getData())) {
            List<ProcessDefinitionResponse> processDefinitionResponses = (List<ProcessDefinitionResponse>) dataResponse.getData();
            for (ProcessDefinitionResponse processDefinitionResponse : processDefinitionResponses) {
                processDefinitionResponse.setName(ActivitiMultiLanguageManager.getMultLanguageInfoByCode(processDefinitionResponse.getName(), iRequest));
            }
        }
        return dataResponse;
    }

}
