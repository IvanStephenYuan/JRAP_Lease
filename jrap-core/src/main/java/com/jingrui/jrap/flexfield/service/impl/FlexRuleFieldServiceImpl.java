package com.jingrui.jrap.flexfield.service.impl;

import com.jingrui.jrap.flexfield.mapper.FlexRuleFieldMapper;
import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jingrui.jrap.flexfield.dto.FlexRuleField;
import com.jingrui.jrap.flexfield.service.IFlexRuleFieldService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FlexRuleFieldServiceImpl extends BaseServiceImpl<FlexRuleField> implements IFlexRuleFieldService {

    @Autowired
    FlexRuleFieldMapper flexRuleFieldMapper;

    @Override
    public List<FlexRuleField> queryFlexRuleField(FlexRuleField flexRuleField) {
        return flexRuleFieldMapper.queryFlexField(flexRuleField);
    }

}