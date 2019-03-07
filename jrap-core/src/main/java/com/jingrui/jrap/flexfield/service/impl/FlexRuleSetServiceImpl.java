package com.jingrui.jrap.flexfield.service.impl;

import com.github.pagehelper.PageHelper;
import com.jingrui.jrap.flexfield.dto.FlexRule;
import com.jingrui.jrap.flexfield.dto.FlexRuleSet;
import com.jingrui.jrap.flexfield.mapper.FlexRuleMapper;
import com.jingrui.jrap.flexfield.mapper.FlexRuleSetMapper;
import com.jingrui.jrap.flexfield.service.IFlexRuleService;
import com.jingrui.jrap.flexfield.service.IFlexRuleSetService;
import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FlexRuleSetServiceImpl extends BaseServiceImpl<FlexRuleSet> implements IFlexRuleSetService {

    @Autowired
    FlexRuleSetMapper flexRuleSetMapper;

    @Autowired
    FlexRuleMapper flexRuleMapper;

    @Autowired
    IFlexRuleService iFlexRuleService;

    @Override
    public List<FlexRuleSet> queryFlexModel(FlexRuleSet model, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return flexRuleSetMapper.queryFlexRuleSet(model);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRuleSet(List<FlexRuleSet> flexRuleSets) {
        for(FlexRuleSet ruleSet:flexRuleSets){
            FlexRule flexRule=new FlexRule();
            flexRule.setRuleSetId(ruleSet.getRuleSetId());
            List<FlexRule> flexRules=flexRuleMapper.select(flexRule);
            iFlexRuleService.deleteRule(flexRules);
            int updateCount = flexRuleSetMapper.delete(ruleSet);
            checkOvn(updateCount,ruleSet);
        }
    }
}