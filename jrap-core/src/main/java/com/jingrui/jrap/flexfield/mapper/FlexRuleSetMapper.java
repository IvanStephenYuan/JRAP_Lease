package com.jingrui.jrap.flexfield.mapper;

import com.jingrui.jrap.flexfield.dto.FlexModel;
import com.jingrui.jrap.mybatis.common.Mapper;
import com.jingrui.jrap.flexfield.dto.FlexRuleSet;

import java.util.List;

public interface FlexRuleSetMapper extends Mapper<FlexRuleSet> {


    /** 查询规则集
     * @param model
     * @return
     */
    List<FlexRuleSet> queryFlexRuleSet(FlexRuleSet model);

}