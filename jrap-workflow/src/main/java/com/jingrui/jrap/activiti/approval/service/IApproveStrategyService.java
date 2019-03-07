package com.jingrui.jrap.activiti.approval.service;

import com.jingrui.jrap.activiti.approval.dto.ApproveStrategy;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.core.ProxySelf;
import com.jingrui.jrap.system.service.IBaseService;

import java.util.List;

public interface IApproveStrategyService
        extends IBaseService<ApproveStrategy>, ProxySelf<IApproveStrategyService> {

    List<ApproveStrategy> selectAll(IRequest request, ApproveStrategy strategy);
}