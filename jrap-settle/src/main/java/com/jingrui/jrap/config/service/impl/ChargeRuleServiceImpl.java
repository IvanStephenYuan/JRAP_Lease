package com.jingrui.jrap.config.service.impl;

import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.jingrui.jrap.config.dto.ChargeRule;
import com.jingrui.jrap.config.service.IChargeRuleService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class ChargeRuleServiceImpl extends BaseServiceImpl<ChargeRule> implements IChargeRuleService{

}