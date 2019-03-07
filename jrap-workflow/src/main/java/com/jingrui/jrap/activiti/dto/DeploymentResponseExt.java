package com.jingrui.jrap.activiti.dto;

import org.activiti.engine.repository.Deployment;
import org.activiti.rest.service.api.repository.DeploymentResponse;

import java.util.Date;

/**
 * @author xiangyu.qi@jingrui.com on 2018/1/4.
 */
public class DeploymentResponseExt extends DeploymentResponse {

    Date deploymentTime;

    public DeploymentResponseExt(Deployment deployment, String url) {
        super(deployment, url);
    }
}
