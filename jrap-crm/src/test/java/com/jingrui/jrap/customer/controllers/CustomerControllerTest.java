/*
 * *
 *  @file com.maddyhome.idea.copyright.pattern.JavaCopyrightVariablesProvider$1@28245f6c$
 *  @CopyRight (C) 2018 ZheJiangJingRui Co. Ltd.
 *  @brief JingRui Application Platform
 *  @author $name$
 *  @email yulong.yuan@jr-info.cn
 *  @date $date$
 * /
 */

package com.jingrui.jrap.customer.controllers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jingrui.jrap.customer.controllers.CustomerController;
import com.jingrui.jrap.customer.dto.Customer;
import com.jingrui.jrap.customer.service.impl.CustomerServiceImpl;
import com.jingrui.jrap.product.controllers.ProductConfigController;
import com.jingrui.jrap.product.service.impl.ProductConfigServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.hamcrest.Matchers.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring/applicationContext02.xml"})
//@Transactional
//@Rollback
public class CustomerControllerTest {
    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("UTF-8"));


    public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }

    @InjectMocks
    private CustomerController customerController;

    private MockMvc mockMvc;

    @Mock
    private CustomerServiceImpl customerService;

    @Mock
    private ProductConfigServiceImpl productConfigService;

    @InjectMocks
    private ProductConfigController productConfigController;


    @Mock
    private Validator validator;

    @Mock
    private MessageSource messageSource;


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    public void testGetCustomers() throws Exception {
        List<Customer> customerList = new ArrayList<Customer>();
        Customer firstCustomer = new Customer();
        firstCustomer.setCustomerId(1L);
        customerList.add(firstCustomer);

        when(customerService.select(anyObject(), anyObject(), anyInt(), anyInt())).thenReturn(customerList);
        int userId = 3;

        //测试get型action
        mockMvc.perform(
                get("/afd/customer/query"))
                //.andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("total", is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("success", is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("rows[0].customerId", is(1)));
    }

    @Test
    public void testUpdateCustomer() throws Exception {
        List<Customer> customerList = new ArrayList<Customer>();
        Customer firstCustomer = new Customer();
        firstCustomer.setCustomerId(2L);
        firstCustomer.setCustomerCode("CUS1811130002");
        customerList.add(firstCustomer);

        Customer secondCustomer = new Customer();
        secondCustomer.setCustomerId(3L);
        secondCustomer.setCustomerCode("CUS1811130003");
        customerList.add(secondCustomer);

        when(customerService.batchUpdate(anyObject(), anyObject())).thenReturn(customerList);
        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                BindingResult result = (BindingResult) invocation.getArguments()[1];
                //result.addError(new ObjectError("保存异常","无法存入数据库"));
                return null;
            }
        }).when(validator).validate(anyObject(), any(BindingResult.class));

        when(messageSource.getMessage(anyString(), anyObject(), anyObject(), anyObject())).thenReturn("abc");

        MvcResult result = mockMvc.perform(
                post("/afd/customer/submit")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(convertObjectToJsonBytes(customerList)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("total", is(2)))
                .andExpect(jsonPath("success", is(true)))
                .andReturn();
        String res = result.getResponse().getContentAsString();

        System.out.println(res);
    }

    @Test
    public void testDeleteCustomer() throws Exception {
        List<Customer> customerList = new ArrayList<Customer>();
        Customer firstCustomer = new Customer();
        firstCustomer.setCustomerId(1L);
        customerList.add(firstCustomer);

        when(customerService.batchDelete(customerList)).thenReturn(2);
        mockMvc.perform(
                post("/afd/customer/remove")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(convertObjectToJsonBytes(customerList)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("success", is(true)));
    }

    @Test
    public void testGetColumns()  throws Exception{
        String tableName = "con_order";
        MvcResult result = mockMvc.perform(
                post("/pro/product/getTable")
                        .contentType(APPLICATION_JSON_UTF8)
                        .param("tableName", tableName))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andReturn();
        String res = result.getResponse().getContentAsString();

        System.out.println(res);
    }
}