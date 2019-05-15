/*
 * *
 *  @file com.maddyhome.idea.copyright.pattern.JavaCopyrightVariablesProvider$1@20f16a40$
 *  @CopyRight (C) 2018 ZheJiangJingRui Co. Ltd.
 *  @brief JingRui Application Platform
 *  @author $name$
 *  @email yulong.yuan@jr-info.cn
 *  @date $date$
 * /
 */

package com.jingrui.jrap.customer.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.customer.dto.CustomerEvaluate;
import com.jingrui.jrap.customer.entity.AntiFraudReport;
import com.jingrui.jrap.customer.entity.LoanReport;
import com.jingrui.jrap.customer.config.PyConfig;
import com.jingrui.jrap.customer.entity.QueryConditions;
import com.jingrui.jrap.customer.entity.QueryCondition;
import com.jingrui.jrap.customer.service.ICustomerEvaluateService;
import com.jingrui.jrap.customer.service.ICustomerPengYuanService;
import com.jingrui.jrap.customer.util.*;
import com.alibaba.fastjson.JSON;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CustomerPengYuanServiceImpl implements ICustomerPengYuanService {
    @Autowired
    private PyConfig pyConfig;

    @Autowired
    private ICustomerEvaluateService customerEvaluateService;

    public final static String SUCCESS_STATUS = "1";
    public final static String ERROR_STATUS = "2";
    public final static String CREDIT_FRAUD_CODE = "96043"; //个人反欺诈
    public final static String CREDIT_LOAN_CODE = "13207";  //多头借贷
    public final static String CREDIT_CODE = "PYCREDIT";
    public final static String LOAN_CODE = "PYLOAN";
    public final static String EVALUATOR = "鹏元征信";

    static final Logger logger = LoggerFactory.getLogger(CustomerPengYuanServiceImpl.class);

    private String getQueryCondition(Long customerId, String customerName, String idNumber, String phone, String subreportIDs) throws Exception {
        // 使用JavaBean/Map方式（正式使用,仅供参考）
        QueryConditions queryConditions = new QueryConditions();
        List<QueryCondition> conditions = new ArrayList<QueryCondition>();
        QueryCondition queryCondition = new QueryCondition();

        // 查询类型
        queryCondition.setQueryType("25136");
        List<QueryCondition.Item> items = new ArrayList<QueryCondition.Item>();
        //查询者姓名
        items.add(new QueryCondition.Item("name", customerName));
        //证件号码
        items.add(new QueryCondition.Item("documentNo", idNumber));
        //查询者手机号码
        items.add(new QueryCondition.Item("phone", phone));
        // 收费子报告
        items.add(new QueryCondition.Item("subreportIDs", subreportIDs));
        // 查询原因
        items.add(new QueryCondition.Item("queryReasonID", "101"));
        // 业务流水号
        items.add(new QueryCondition.Item("refID", customerId.toString()));

        queryCondition.setItems(items);
        conditions.add(queryCondition);
        queryConditions.setConditions(conditions);
        return JSON.toJSONString(queryConditions);
    }

    public String requestApi(Long customerId, String customerName, String idNumber, String phone, String subreportIDs, String host, String path) throws Exception {
        logger.debug("================开始调用鹏元征信接口======================");
        // https双向认证使用,配合PySSLContextUtil中的DefaultSSLContext
        System.setProperty("javax.net.debug", "all");
        System.setProperty("javax.net.ssl.keyStore", pyConfig.getCertPath());
        System.setProperty("javax.net.ssl.keyStorePassword", pyConfig.getCertPassword());
        System.setProperty("javax.net.ssl.trustStore", pyConfig.getCertPath());
        System.setProperty("javax.net.ssl.trustStorePassword", pyConfig.getCertPassword());

        Map<String, String> headers = new HashMap<String, String>();
        Map<String, String> querys = null;
        Map<String, String> bodys = new HashMap<String, String>();

        //默认请求条件是JSON格式.如果请求条件是xml，需要指定格式。
        //bodys.put("format","xml")
        bodys.put("userID", pyConfig.getUserId());
        bodys.put("password", pyConfig.getPassword());
        bodys.put("queryCondition", getQueryCondition(customerId, customerName, idNumber, phone, subreportIDs));

        logger.debug("CertPath：" + pyConfig.getCertPath());
        logger.debug("CertPassword：" + pyConfig.getCertPassword());
        logger.debug("userID：" + pyConfig.getUserId());
        logger.debug("password：" + pyConfig.getPassword());

        HttpResponse response = HttpUtils.doPost(host, path, "POST", headers, querys, bodys, pyConfig.getCertPath(), pyConfig.getCertPassword());
        String result = EntityUtils.toString(response.getEntity());
        logger.debug("==========接口调用结束=================");

        return result;
    }

    @Override
    public String readCustomerCredit(IRequest request, Long customerId, String customerName, String idNumber, String phone) throws ParseException, Exception {
        String retStr = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String result = requestApi(customerId, customerName, idNumber, phone, CREDIT_FRAUD_CODE, pyConfig.getHost(), pyConfig.getPath());
        JSONObject resultJson = (JSONObject) JSONObject.parse(result);
        String status = resultJson.getString("status");
        System.out.println(resultJson.toString());

        if (SUCCESS_STATUS.equalsIgnoreCase(status)) {
            JSONObject returnValue = resultJson.getJSONObject("returnValue");
            JSONArray cisReports = returnValue.getJSONArray("cisReport");
            JSONObject cisReport = cisReports.getJSONObject(0);

            if (CREDIT_FRAUD_CODE.equalsIgnoreCase(cisReport.getString("subReportTypes"))
                    && customerId.toString().equalsIgnoreCase(cisReport.getString("refID"))) {
                if ("true".equalsIgnoreCase(cisReport.getString("hasSystemError"))) {
                    retStr = "[鹏元反欺诈查询失败]";
                } else {
                    AntiFraudReport report = new AntiFraudReport(returnValue);

                    CustomerEvaluate query = new CustomerEvaluate();
                    Date evaluateDate = dateFormat.parse(dateFormat.format(report.getBuildEndTime()));
                    query.setEvaluateDate(evaluateDate);
                    query.setCustomerId(customerId);
                    query.setEvaluateType(CREDIT_CODE);
                    List<CustomerEvaluate> list = customerEvaluateService.select(request, query, 1, 1);
                    CustomerEvaluate customerEvaluate;

                    if (list.size() == 1) {
                        customerEvaluate = list.get(0);
                        customerEvaluate.setEvaluator(EVALUATOR);
                        customerEvaluate.setCompositeScore(report.getRiskScore());
                        customerEvaluate.setEvaluateDate(report.getBuildEndTime());
                        customerEvaluate.setBrief(report.getPersonAntiSpoofingDesc());
                        customerEvaluate.setAttribute1(report.getReportID());
                        customerEvaluate.setAttribute2(report.getBatchNo());
                        customerEvaluate.setRemark(returnValue.toJSONString());

                        customerEvaluateService.updateByPrimaryKeySelective(request, customerEvaluate);
                    } else {
                        customerEvaluate = new CustomerEvaluate();
                        customerEvaluate.setCustomerId(customerId);
                        customerEvaluate.setEvaluateType(CREDIT_CODE);
                        customerEvaluate.setEvaluator(EVALUATOR);
                        customerEvaluate.setCompositeScore(report.getRiskScore());
                        customerEvaluate.setEvaluateDate(evaluateDate);
                        customerEvaluate.setBrief(report.getPersonAntiSpoofingDesc());
                        customerEvaluate.setAttribute1(report.getReportID());
                        customerEvaluate.setAttribute2(report.getBatchNo());
                        customerEvaluate.setRemark(returnValue.toJSONString());

                        customerEvaluateService.insertSelective(request, customerEvaluate);
                    }
                    retStr = "[鹏元反欺诈查询成功]";
                }
            } else {
                //throw new Exception("返回报告和查询数据不匹配");
                retStr = "[鹏元反欺诈查询失败(返回报告和查询数据不匹配)]";
            }
        } else if (ERROR_STATUS.equalsIgnoreCase(status)) {
            String errorMessage = resultJson.getString("errorMessage");
            retStr = "[鹏元反欺诈查询失败(" + errorMessage + ")]";
            //throw new Exception(errorMessage);
        }

        return retStr;
    }

    @Override
    public String readCustomerLoan(IRequest request, Long customerId, String customerName, String idNumber, String phone) throws ParseException, Exception {
        String retStr = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String result = requestApi(customerId, customerName, idNumber, phone, CREDIT_LOAN_CODE, pyConfig.getHost(), pyConfig.getPath());
        JSONObject resultJson = (JSONObject) JSONObject.parse(result);
        String status = resultJson.getString("status");
        System.out.println(resultJson.toString());

        if (SUCCESS_STATUS.equalsIgnoreCase(status)) {
            JSONObject returnValue = resultJson.getJSONObject("returnValue");
            JSONArray cisReports = returnValue.getJSONArray("cisReport");
            JSONObject cisReport = cisReports.getJSONObject(0);

            if (CREDIT_LOAN_CODE.equalsIgnoreCase(cisReport.getString("subReportTypes"))
                    && customerId.toString().equalsIgnoreCase(cisReport.getString("refID"))) {
                if ("true".equalsIgnoreCase(cisReport.getString("hasSystemError"))) {
                    retStr = "[鹏元多头借贷查询失败]";
                } else {
                    LoanReport report = new LoanReport(returnValue);

                    CustomerEvaluate query = new CustomerEvaluate();
                    Date evaluateDate = dateFormat.parse(dateFormat.format(report.getBuildEndTime()));
                    query.setEvaluateDate(evaluateDate);
                    query.setCustomerId(customerId);
                    query.setEvaluateType(LOAN_CODE);
                    List<CustomerEvaluate> list = customerEvaluateService.select(request, query, 1, 1);
                    CustomerEvaluate customerEvaluate;

                    if (list.size() == 1) {
                        customerEvaluate = list.get(0);
                        customerEvaluate.setEvaluator(EVALUATOR);
                        customerEvaluate.setEvaluateDate(report.getBuildEndTime());
                        customerEvaluate.setBrief(report.getSubReportTypesShortCaption());
                        customerEvaluate.setAttribute1(report.getReportID());
                        customerEvaluate.setAttribute2(report.getBatchNo());
                        customerEvaluate.setRemark(returnValue.toJSONString());
                        customerEvaluateService.updateByPrimaryKeySelective(request, customerEvaluate);
                    } else {
                        customerEvaluate = new CustomerEvaluate();
                        customerEvaluate.setCustomerId(customerId);
                        customerEvaluate.setEvaluateType(LOAN_CODE);
                        customerEvaluate.setEvaluator(EVALUATOR);
                        customerEvaluate.setEvaluateDate(evaluateDate);
                        customerEvaluate.setBrief(report.getSubReportTypesShortCaption());
                        customerEvaluate.setAttribute1(report.getReportID());
                        customerEvaluate.setAttribute2(report.getBatchNo());
                        customerEvaluate.setRemark(returnValue.toJSONString());

                        customerEvaluateService.insertSelective(request, customerEvaluate);
                    }
                    retStr = "[鹏元多头借贷查询成功]";
                }
            } else {
                //throw new Exception("返回报告和查询数据不匹配");
                retStr = "[鹏元多头借贷查询失败(返回报告和查询数据不匹配)]";
            }

        } else if (ERROR_STATUS.equalsIgnoreCase(status)) {
            String errorMessage = resultJson.getString("errorMessage");
            //throw new Exception(errorMessage);
            retStr = "[鹏元多头借贷查询失败(" + errorMessage + ")]";
        }

        return retStr;
    }
}
