package com.jingrui.jrap.activiti.service.impl;

import com.jingrui.jrap.activiti.dto.ApproveChainLine;
import com.jingrui.jrap.activiti.mapper.ApproveChainLineMapper;
import com.jingrui.jrap.activiti.service.IApproveChainLineService;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import org.activiti.engine.identity.Group;
import org.activiti.engine.impl.persistence.entity.data.GroupDataManager;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ApproveChainLineServiceImpl extends BaseServiceImpl<ApproveChainLine> implements IApproveChainLineService {

    @Autowired
    private ApproveChainLineMapper lineMapper;

    @Autowired
    private GroupDataManager groupDataManager;

    @Override
    public List<ApproveChainLine> selectByHeaderId(IRequest iRequest ,Long headerId) {
        List<ApproveChainLine> list = lineMapper.selectByHeaderId(headerId);
        for (ApproveChainLine line : list) {
            if (StringUtils.isEmpty(line.getAssignee()) && StringUtils.isNotEmpty(line.getAssignGroup())) {
                Group group = groupDataManager.findById(line.getAssignGroup());
                if (group != null) {
                    line.setAssignGroupName(group.getName());
                }
            }
        }
        return list;
    }

    @Override
    public List<ApproveChainLine> selectByNodeId(IRequest iRequest ,String key, String nodeId) {
        List<ApproveChainLine> list = lineMapper.selectByNodeId(key, nodeId);
        for (ApproveChainLine line : list) {
            if (StringUtils.isEmpty(line.getAssignee()) && StringUtils.isNotEmpty(line.getAssignGroup())) {
                Group group = groupDataManager.findById(line.getAssignGroup());
                if (group != null) {
                    line.setAssignGroupName(group.getName());
                }
            }
        }
        return list;
    }
}