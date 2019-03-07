package com.jingrui.jrap.security.permission.service.impl;

import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.message.IMessagePublisher;
import com.jingrui.jrap.security.permission.dto.DataPermissionRuleDetail;
import com.jingrui.jrap.security.permission.dto.DataPermissionTableRule;
import com.jingrui.jrap.security.permission.mapper.DataPermissionRuleDetailMapper;
import com.jingrui.jrap.security.permission.mapper.DataPermissionRuleMapper;
import com.jingrui.jrap.security.permission.mapper.DataPermissionTableRuleMapper;
import com.jingrui.jrap.security.permission.service.IDataPermissionRuleDetailService;
import com.jingrui.jrap.security.permission.service.IDataPermissionTableRuleService;
import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jingrui.jrap.security.permission.dto.DataPermissionRule;
import com.jingrui.jrap.security.permission.service.IDataPermissionRuleService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author jialong.zuo@jingrui.com
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class DataPermissionRuleServiceImpl extends BaseServiceImpl<DataPermissionRule> implements IDataPermissionRuleService {
    @Autowired
    DataPermissionRuleMapper dataPermissionRuleMapper;

    @Autowired
    DataPermissionRuleDetailMapper dataPermissionRuleDetailMapper;

    @Autowired
    IDataPermissionRuleDetailService iDataPermissionRuleDetailService;

    @Autowired
    IDataPermissionTableRuleService iDataPermissionTableRuleService;

    @Autowired
    DataPermissionTableRuleMapper dataPermissionTableRuleMapper;

    @Autowired
    IMessagePublisher iMessagePublisher;

    @Override
    public List<DataPermissionRule> selectRuleWithoutTableSelect(DataPermissionRule dataPermissionRule, IRequest iRequest, int page, int pageSize) {
        return dataPermissionRuleMapper.selectRuleWithoutTableSelect(dataPermissionRule);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeRuleWithDetail(List<DataPermissionRule> dataMaskRuleManages) {

        dataMaskRuleManages.forEach(v -> {
            DataPermissionRuleDetail detail = new DataPermissionRuleDetail();
            detail.setRuleId(v.getRuleId());
            List<DataPermissionRuleDetail> detailList = dataPermissionRuleDetailMapper.select(detail);
            iDataPermissionRuleDetailService.removeDataMaskRuleDetailWithAssign(detailList);

            DataPermissionTableRule tableRule = new DataPermissionTableRule();
            tableRule.setRuleId(v.getRuleId());
            List<DataPermissionTableRule> ruleList = dataPermissionTableRuleMapper.select(tableRule);
            iDataPermissionTableRuleService.removeRule(ruleList);
        });

        batchDelete(dataMaskRuleManages);

    }

    @Override
    public void removeRule(List<DataPermissionRule> dataPermissionRules) {
        self().removeRuleWithDetail(dataPermissionRules);
        dataPermissionRules.forEach(v -> {
            updateCache(v.getRuleId(), "dataPermission.ruleRemove");
        });
    }

    private void updateCache(Long ruleId, String channel) {
        iMessagePublisher.publish(channel, ruleId);
    }

}