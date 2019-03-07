package com.jingrui.jrap.activiti.service;

import com.jingrui.jrap.activiti.dto.ApproveChainLine;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.core.ProxySelf;
import com.jingrui.jrap.system.service.IBaseService;

import java.util.List;

public interface IApproveChainLineService extends IBaseService<ApproveChainLine>, ProxySelf<IApproveChainLineService> {
    List<ApproveChainLine> selectByHeaderId(IRequest iRequest, Long headerId);

    List<ApproveChainLine> selectByNodeId(IRequest iRequest, String key, String nodeId);
}