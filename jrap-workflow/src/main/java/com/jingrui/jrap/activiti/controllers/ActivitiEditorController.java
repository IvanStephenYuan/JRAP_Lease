package com.jingrui.jrap.activiti.controllers;

import org.activiti.engine.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @author shengyang.zhou@jingrui.com
 */
@Controller
public class ActivitiEditorController {

    @Autowired
    private RepositoryService repositoryService;

}
