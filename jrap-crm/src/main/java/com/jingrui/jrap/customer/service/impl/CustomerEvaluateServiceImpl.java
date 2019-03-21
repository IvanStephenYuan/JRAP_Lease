package com.jingrui.jrap.customer.service.impl;

import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.jingrui.jrap.customer.dto.CustomerEvaluate;
import com.jingrui.jrap.customer.service.ICustomerEvaluateService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class CustomerEvaluateServiceImpl extends BaseServiceImpl<CustomerEvaluate> implements ICustomerEvaluateService{

}