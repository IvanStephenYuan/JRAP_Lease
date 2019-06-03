/*
 * *
 *  @file com.maddyhome.idea.copyright.pattern.JavaCopyrightVariablesProvider$1@77c8112a$
 *  @CopyRight (C) 2018 ZheJiangJingRui Co. Ltd.
 *  @brief JingRui Application Platform
 *  @author $name$
 *  @email yulong.yuan@jr-info.cn
 *  @date $date$
 * /
 */

package com.jingrui.jrap.customer.service.impl;

import com.jingrui.jrap.customer.entity.PreloanQueryResponse;
import com.jingrui.jrap.customer.entity.PreloanSubmitResponse;
import com.jingrui.jrap.customer.service.ICustomerTongdunService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring/applicationContext02.xml"})
public class CustomerTongdunServiceImplTest {
    @Autowired
    private ICustomerTongdunService customerTongdunService;

    @Test
    public void apply() {
        Map<String, Object> params = new HashMap<String, Object>();

        params.put("name", "皮晴晴"); // 姓名
        params.put("id_number", "370404199006301915"); // 身份证号
        params.put("mobile", "13260665731"); // 手机号


        PreloanSubmitResponse riskPreloanResponse = customerTongdunService.apply(params);
        System.out.println(riskPreloanResponse.toString());

        if (riskPreloanResponse.getSuccess()) {
            // 等待一定时间后，可调用query接口查询结果。
            // 时间建议：5s后可调用
            try {
                Thread.sleep(5000);
            } catch (Exception e) {
                //
            }
            // query接口获取结果
            PreloanQueryResponse response = customerTongdunService.query(riskPreloanResponse.getReport_id());
            System.out.println(response.getReport_id());
            // 其他处理 。。。
        }
    }
}