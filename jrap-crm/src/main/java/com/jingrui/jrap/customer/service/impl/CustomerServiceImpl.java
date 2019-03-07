package com.jingrui.jrap.customer.service.impl;

import java.util.List;

import com.jingrui.jrap.code.rule.exception.CodeRuleException;
import com.jingrui.jrap.core.annotation.StdWho;
import com.jingrui.jrap.system.dto.ResponseData;
import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.jingrui.jrap.customer.dto.Customer;
import com.jingrui.jrap.customer.service.ICustomerService;
import org.springframework.transaction.annotation.Transactional;

import com.jingrui.jrap.core.IRequest;

@Service
@Transactional(rollbackFor = Exception.class)
public class CustomerServiceImpl extends BaseServiceImpl<Customer> implements ICustomerService{
    @Override
    public List<Customer> batchUpdate(IRequest request, List<Customer> list){
        List<Customer> dto = super.batchUpdate(request, list);
        return dto;
    };
}