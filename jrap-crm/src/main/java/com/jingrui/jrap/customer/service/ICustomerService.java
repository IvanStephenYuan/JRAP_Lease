package com.jingrui.jrap.customer.service;

import java.util.List;
import com.jingrui.jrap.core.ProxySelf;
import com.jingrui.jrap.system.service.IBaseService;
import com.jingrui.jrap.customer.dto.Customer;
import com.jingrui.jrap.core.IRequest;

public interface ICustomerService extends IBaseService<Customer>, ProxySelf<ICustomerService>{
    @Override
    public List<Customer> batchUpdate(IRequest request, List<Customer> list);
}