/*
 * *
 *  @file com.maddyhome.idea.copyright.pattern.JavaCopyrightVariablesProvider$1@71f10105$
 *  @CopyRight (C) 2018 ZheJiangJingRui Co. Ltd.
 *  @brief JingRui Application Platform
 *  @author $name$
 *  @email yulong.yuan@jr-info.cn
 *  @date $date$
 * /
 */

package com.jingrui.jrap.customer.service.impl;

import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.customer.config.TdConfig;
import com.jingrui.jrap.customer.dto.CustomerEvaluate;
import com.jingrui.jrap.customer.service.ICustomerEvaluateService;
import com.jingrui.jrap.customer.service.ICustomerTongdunService;
import com.jingrui.jrap.customer.entity.PreloanSubmitResponse;
import com.jingrui.jrap.customer.entity.PreloanQueryResponse;

import java.util.*;

import com.alibaba.fastjson.JSON;
import com.jingrui.jrap.customer.util.TdUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerTongdunServiceImpl implements ICustomerTongdunService {
    @Autowired
    TdConfig config;

    @Autowired
    private ICustomerEvaluateService customerEvaluateService;

    static final Logger logger = LoggerFactory.getLogger(CustomerTongdunServiceImpl.class);

    private static final String EVENT_TYPE = "loan";    // 事件类型
    private static final String VERSION = "v1";      // 表单版本号

    @Override
    public PreloanSubmitResponse apply(Map<String, Object> params) {
        PreloanSubmitResponse submitResponse = null;
        String url = new StringBuilder()
                .append(config.getApplyUrl())
                .append("?partner_code=").append(config.getPartnerCode())
                .append("&partner_key=").append(config.getPartnerKey())
                .append("&app_name=").append(config.getPartnerApp())
                .append("&event_type=").append(EVENT_TYPE)
                .append("&version=").append(VERSION).toString();
        String result = TdUtils.doPost(url, params);

        if(result != null && !"".equalsIgnoreCase(result)){
            submitResponse = JSON.parseObject(result.trim(), PreloanSubmitResponse.class);
        }else{
            submitResponse = new PreloanSubmitResponse();
            submitResponse.setSuccess(false);
            submitResponse.setReason_desc("请求异常，请联系管理员！");
        }

        return submitResponse;
    }

    @Override
    public PreloanQueryResponse query(String reportId) {
        PreloanQueryResponse queryResponse;
        String urlString = new StringBuilder().append(config.getQueryUrl())
                .append("?partner_code=").append(config.getPartnerCode())
                .append("&partner_key=").append(config.getPartnerKey())
                .append("&report_id=").append(reportId).toString();

        String result = TdUtils.doGet(urlString);
        System.out.println(result);
        queryResponse = JSON.parseObject(result.trim(), PreloanQueryResponse.class);
        return queryResponse;
    }

    @Override
    public String readCustomerCredit(IRequest request, Long customerId, String customerName, String idNumber, String phone) throws Exception {
        String result = "";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("name", customerName); // 姓名
        params.put("id_number", idNumber); // 身份证号
        params.put("mobile", phone); // 手机号

        logger.debug("================开始调用铜盾征信接口======================");
        logger.debug("请求参数：" + params.toString());
        PreloanSubmitResponse riskPreloanResponse = this.apply(params);
        logger.debug("提交返回：" + riskPreloanResponse.toString());

        if (riskPreloanResponse.getSuccess()) {
            try {
                Thread.sleep(5000);
            } catch (Exception e) {
            }

            // query接口获取结果
            PreloanQueryResponse response = this.query(riskPreloanResponse.getReport_id());
            if(response.getSuccess()){
                logger.debug("提交返回：" + response.toString());
                CustomerEvaluate customerEvaluate = new CustomerEvaluate();
                customerEvaluate.setCustomerId(customerId);
                customerEvaluate.setEvaluateType("TDLOAN");
                customerEvaluate.setEvaluator("铜盾征信");
                customerEvaluate.setCompositeScore(Double.valueOf(response.getFinal_score()));
                customerEvaluate.setEvaluateDate(new Date(response.getReport_time()));
                customerEvaluate.setBrief(response.getFinal_decision());
                customerEvaluate.setAttribute1(response.getReport_id());
                customerEvaluate.setAttribute2(response.getApplication_id());
                customerEvaluate.setRemark(response.getRisk_items().toJSONString());

                customerEvaluateService.insertSelective(request, customerEvaluate);
                result = "[铜盾查询成功]";
            }else{
                logger.debug(response.getReason_desc());
                result = "[铜盾查询失败(" + response.getReason_desc() + ")]";
            }
        }else{
            logger.debug(riskPreloanResponse.getReason_desc());
            result = "[铜盾查询失败(" + riskPreloanResponse.getReason_desc() + ")]";
        }

        return result;
    }
}
