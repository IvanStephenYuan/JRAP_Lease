/*
 * *
 *  @file com.maddyhome.idea.copyright.pattern.JavaCopyrightVariablesProvider$1@19aec248$
 *  @CopyRight (C) 2018 ZheJiangJingRui Co. Ltd.
 *  @brief JingRui Application Platform
 *  @author $name$
 *  @email yulong.yuan@jr-info.cn
 *  @date $date$
 * /
 */

package com.jingrui.jrap.customer.service;

import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.core.ProxySelf;
import com.jingrui.jrap.customer.entity.PreloanQueryResponse;
import com.jingrui.jrap.customer.entity.PreloanSubmitResponse;

import java.text.ParseException;
import java.util.Map;

public interface ICustomerTongdunService extends ProxySelf<ICustomerPengYuanService> {
    /**
     * 征信查询
     * @param params
     * @return
     */
    public PreloanSubmitResponse apply(Map<String, Object> params);

    /**
     * 结果查询
     * @param reportId
     */
    public PreloanQueryResponse query(String reportId);

    /**
     * 铜盾贷款申请查询
     * @param request
     * @param customerId
     * @param customerName
     * @param idNumber
     * @param phone
     * @return
     * @throws Exception
     */
    public String readCustomerCredit(IRequest request, Long customerId, String customerName, String idNumber, String phone) throws Exception;
}
