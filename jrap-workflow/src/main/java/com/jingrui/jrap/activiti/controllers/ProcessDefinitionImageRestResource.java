package com.jingrui.jrap.activiti.controllers;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import com.jingrui.jrap.activiti.components.ActivitiMultiLanguageManager;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.core.impl.RequestHelper;
import io.swagger.annotations.ApiParam;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowNode;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.image.ProcessDiagramGenerator;
import org.activiti.rest.service.api.repository.BaseProcessDefinitionResource;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qiang.zeng
 * @date 2018/9/13.
 */
@RestController
@RequestMapping(value = {"/wfl", "/api/wfl"})
public class ProcessDefinitionImageRestResource extends BaseProcessDefinitionResource {

    @Autowired
    protected ProcessEngineConfigurationImpl processEngineConfiguration;

    @RequestMapping(value = "/repository/process-definitions/{processDefinitionId}/image", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getModelResource(@ApiParam(name = "processDefinitionId") @PathVariable String processDefinitionId, HttpServletRequest request) {
        IRequest iRequest = RequestHelper.createServiceRequest(request);
        ProcessDefinition processDefinition = getProcessDefinitionFromRequest(processDefinitionId);
        //add by qiang.zeng start
        InputStream imageStream = null;
        if (!ActivitiMultiLanguageManager.multiLanguageOpen) {
            imageStream = repositoryService.getProcessDiagram(processDefinition.getId());
        } else {
            if (processDefinition != null && processDefinition.hasGraphicalNotation()) {
                BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinition.getId());
                ProcessDiagramGenerator diagramGenerator = processEngineConfiguration.getProcessDiagramGenerator();
                //存储原节点名称
                Map<String, String> oldFlowNodeName = new HashMap<>();
                Collection<FlowNode> flowNodes = bpmnModel.getMainProcess().findFlowElementsOfType(FlowNode.class);
                //展示流程图时 替换节点多语言名称
                for (FlowNode flowNode : flowNodes) {
                    oldFlowNodeName.put(flowNode.getId(), flowNode.getName());
                    flowNode.setName(ActivitiMultiLanguageManager.getMultLanguageInfoByCode(flowNode.getName(), iRequest));
                }

                imageStream = diagramGenerator.generateDiagram(bpmnModel, "png", new ArrayList<>(),
                        new ArrayList<>(), processEngineConfiguration.getActivityFontName(),
                        processEngineConfiguration.getLabelFontName(), processEngineConfiguration.getAnnotationFontName(),
                        processEngineConfiguration.getClassLoader(), 1.0);

                //生成流程图后 还原节点名称
                for (FlowNode flowNode : flowNodes) {
                    flowNode.setName(oldFlowNodeName.get(flowNode.getId()));
                }
            }
        }
        //add by qiang.zeng end

        if (imageStream != null) {
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("Content-Type", "image/png");
            try {
                return new ResponseEntity<byte[]>(IOUtils.toByteArray(imageStream), responseHeaders, HttpStatus.OK);
            } catch (Exception e) {
                throw new ActivitiException("Error reading image stream", e);
            }
        } else {
            throw new ActivitiIllegalArgumentException("Process definition with id '" + processDefinition.getId() + "' has no image.");
        }
    }
}
