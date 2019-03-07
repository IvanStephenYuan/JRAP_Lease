package com.jingrui.jrap.activiti.mapper;

import com.jingrui.jrap.activiti.dto.WflRules;
import com.jingrui.jrap.mybatis.common.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author shengyang.zhou@jingrui.com
 */
public interface RulesMapper extends Mapper<WflRules> {

    WflRules selectByExecution(@Param("processKey") String processKey, @Param("nodeId") String nodeId);

    WflRules selectByRuleCode(String ruleCode);

}
