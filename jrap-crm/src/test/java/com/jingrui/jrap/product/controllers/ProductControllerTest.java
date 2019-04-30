/*
 * *
 *  @file com.maddyhome.idea.copyright.pattern.JavaCopyrightVariablesProvider$1@1b94f9cb$
 *  @CopyRight (C) 2018 ZheJiangJingRui Co. Ltd.
 *  @brief JingRui Application Platform
 *  @author $name$
 *  @email yulong.yuan@jr-info.cn
 *  @date $date$
 * /
 */

package com.jingrui.jrap.product.controllers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jingrui.jrap.product.dto.Product;
import com.jingrui.jrap.product.dto.ProductConfig;
import com.jingrui.jrap.product.service.impl.ProductConfigServiceImpl;
import com.jingrui.jrap.product.service.impl.ProductFormulaServiceImpl;
import com.jingrui.jrap.product.service.impl.ProductServiceImpl;
import com.jingrui.jrap.product.calculate.impl.ProductECICalculateImp;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Validator;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring/applicationContext02.xml"})
public class ProductControllerTest {
    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("UTF-8"));


    public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }

    @InjectMocks
    private ProductController productController;

    private MockMvc mockMvc;

    @Mock
    private ProductServiceImpl productService;

    @Mock
    private ProductConfigServiceImpl configService;

    @Mock
    private ProductFormulaServiceImpl formulaService;

    @Mock
    private Validator validator;

    @Mock
    private MessageSource messageSource;

    @InjectMocks
    private ProductECICalculateImp calculateImp;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }
    @Test
    public void calculate() throws Exception{
        List<ProductConfig> configs = new ArrayList<ProductConfig>();
        Product product = new Product();
        product.setCalculate("ECI");

        ProductConfig config = new ProductConfig();
        config.setColumnCode("H1");
        config.setColumnName("LEASE_AMONT");
        configs.add(config);

        config.setColumnCode("H2");
        config.setColumnName("DOWN_PAYMENT");
        configs.add(config);

        when(productService.selectByPrimaryKey(anyObject(), anyObject())).thenReturn(product);
        when(configService.select(anyObject(), anyObject(), anyInt(), anyInt())).thenReturn(configs);
        when(formulaService.selectByConfigId(anyObject())).thenReturn(null);

        mockMvc.perform(
                get("/pro/product/calculate?productCode=SH100010005"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("rows[0].customerId", is(1)));
    }
}