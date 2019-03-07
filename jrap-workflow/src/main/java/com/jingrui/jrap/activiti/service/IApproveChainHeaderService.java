package com.jingrui.jrap.activiti.service;

import com.jingrui.jrap.activiti.dto.ApproveChainHeader;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.core.ProxySelf;
import com.jingrui.jrap.system.service.IBaseService;

import java.util.List;

public interface IApproveChainHeaderService extends IBaseService<ApproveChainHeader>, ProxySelf<IApproveChainHeaderService> {

    ApproveChainHeader selectByUserTask(String ProcessDefinitionId, String userTaskId);

    List<ApproveChainHeader> updateHeadLine(IRequest requestCtx, List<ApproveChainHeader> dto);
}