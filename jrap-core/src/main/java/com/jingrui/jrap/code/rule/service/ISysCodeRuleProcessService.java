package com.jingrui.jrap.code.rule.service;

import com.jingrui.jrap.code.rule.exception.CodeRuleException;

import java.util.Map;

/**
 * @author xiangyu.qi@jingrui.com on 2017/8/23.
 */

public interface ISysCodeRuleProcessService {

    String getRuleCode(String ruleCode) throws CodeRuleException;


    String getRuleCode(String ruleCode ,Map<String,String> variables) throws CodeRuleException;
}
