package com.jingrui.jrap.activiti.service.impl;

import com.jingrui.jrap.activiti.dto.ApproveChainHeader;
import com.jingrui.jrap.activiti.mapper.ApproveChainHeaderMapper;
import com.jingrui.jrap.activiti.mapper.ApproveChainLineMapper;
import com.jingrui.jrap.activiti.service.IApproveChainHeaderService;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.core.util.CommonUtils;
import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ApproveChainHeaderServiceImpl extends BaseServiceImpl<ApproveChainHeader>
        implements IApproveChainHeaderService {

    @Autowired
    private ApproveChainHeaderMapper headerMapper;

    @Autowired
    private ApproveChainLineMapper lineMapper;

    @Override
    public ApproveChainHeader selectByUserTask(String processDefinitionId, String userTaskId) {
        return headerMapper.selectByUserTask(processDefinitionId, userTaskId);
    }

    @Override
    public List<ApproveChainHeader> updateHeadLine(IRequest requestCtx, List<ApproveChainHeader> dto) {

        for (ApproveChainHeader header : dto) {
            if (header.getApproveChainId() == null) {
                insertSelective(requestCtx, header);
            }
            CommonUtils.foreach(header.getLines(), (line) -> {
                line.setApproveChainId(header.getApproveChainId());
                if (line.getApproveChainLineId() == null) {
                    lineMapper.insertSelective(line);
                } else {
                    int count = lineMapper.updateByPrimaryKeySelective(line);
                    checkOvn(count, line);
                }
            });
        }

        return dto;
    }
}