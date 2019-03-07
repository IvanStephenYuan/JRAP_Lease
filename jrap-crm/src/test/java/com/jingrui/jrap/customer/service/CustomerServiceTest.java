/*
 * *
 *  @file com.maddyhome.idea.copyright.pattern.JavaCopyrightVariablesProvider$1@4f6a21f8$
 *  @CopyRight (C) 2018 ZheJiangJingRui Co. Ltd.
 *  @brief JingRui Application Platform
 *  @author $name$
 *  @email yulong.yuan@jr-info.cn
 *  @date $date$
 * /
 */

package com.jingrui.jrap.customer.service;

import java.util.List;
import com.jingrui.jrap.customer.dto.Customer;
import com.jingrui.jrap.customer.mapper.CustomerMapper;
import com.jingrui.jrap.customer.service.ICustomerService;
import com.jingrui.jrap.customer.service.impl.CustomerServiceImpl;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.core.impl.RequestHelper;
import com.jingrui.jrap.core.impl.ServiceRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring/applicationContext.xml"})
@Transactional
@Rollback
public class CustomerServiceTest {

    @Mock
    private CustomerMapper customerMapper;

    @InjectMocks
    private CustomerServiceImpl customerService;

    IRequest request;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        request= new ServiceRequest();
        request.setLocale("zh_CN");
        RequestHelper.setCurrentRequest(request);
    }

    @Test
    public void testQueryCustomer(){
        Customer queryCustomer = new Customer();
        queryCustomer.setCustomerId(1L);

        List<Customer> responseCustomers = customerService.select(request, queryCustomer, 1, 10);
        assertEquals((long)responseCustomers.get(0).getCustomerId(),1L);
    }
}