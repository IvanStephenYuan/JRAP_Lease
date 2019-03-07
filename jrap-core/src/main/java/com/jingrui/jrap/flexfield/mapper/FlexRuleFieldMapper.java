package com.jingrui.jrap.flexfield.mapper;

import com.jingrui.jrap.mybatis.common.Mapper;
import com.jingrui.jrap.flexfield.dto.FlexRuleField;

import java.util.List;

public interface FlexRuleFieldMapper extends Mapper<FlexRuleField> {

    /** 根据 rule查询对应ruleField
     * @param flexRuleField
     * @return
     */
    List<FlexRuleField> queryFlexField(FlexRuleField flexRuleField);

}