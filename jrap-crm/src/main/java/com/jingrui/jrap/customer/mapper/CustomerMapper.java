package com.jingrui.jrap.customer.mapper;

import com.jingrui.jrap.mybatis.common.Mapper;
import com.jingrui.jrap.customer.dto.Customer;
import java.util.List;

public interface CustomerMapper extends Mapper<Customer>{
    /**
     * 获取客商LOV
     * @param customer
     * @return
     */
    List<Customer> selectCustomer(Customer customer);
}