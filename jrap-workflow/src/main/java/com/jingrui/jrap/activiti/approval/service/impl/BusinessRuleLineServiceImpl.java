package com.jingrui.jrap.activiti.approval.service.impl;

import com.jingrui.jrap.activiti.approval.dto.BusinessRuleLine;
import com.jingrui.jrap.activiti.approval.service.IBusinessRuleLineService;
import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BusinessRuleLineServiceImpl extends BaseServiceImpl<BusinessRuleLine> implements IBusinessRuleLineService {

}