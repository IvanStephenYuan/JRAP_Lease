package com.jingrui.jrap.activiti.approval.service.impl;

import com.jingrui.jrap.activiti.approval.dto.ApproveStrategy;
import com.jingrui.jrap.activiti.approval.mapper.ApproveStrategyMapper;
import com.jingrui.jrap.activiti.approval.service.IApproveStrategyService;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ApproveStrategyServiceImpl extends BaseServiceImpl<ApproveStrategy> implements IApproveStrategyService {

    @Autowired
    private ApproveStrategyMapper mapper;

    @Override
    public List<ApproveStrategy> selectAll(IRequest request, ApproveStrategy strategy) {
        return mapper.select(strategy);
    }
}