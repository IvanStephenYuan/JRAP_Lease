package com.jingrui.jrap.activiti.approval.service;

import com.jingrui.jrap.activiti.approval.dto.ApproveCandidateRule;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.core.ProxySelf;
import com.jingrui.jrap.system.service.IBaseService;

import java.util.List;

public interface IApproveCandidateRuleService
        extends IBaseService<ApproveCandidateRule>, ProxySelf<IApproveCandidateRuleService> {

    List<ApproveCandidateRule> selectAll(IRequest request, ApproveCandidateRule rule);
}