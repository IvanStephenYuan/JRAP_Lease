package com.jingrui.jrap.flexfield.mapper;

import com.jingrui.jrap.mybatis.common.Mapper;
import com.jingrui.jrap.flexfield.dto.FlexRule;

import java.util.List;

public interface FlexRuleMapper extends Mapper<FlexRule> {

    /** 匹配规则
     * @param ruleSetCode
     * @return
     */
    List<FlexRule> matchingRule(String ruleSetCode);

}