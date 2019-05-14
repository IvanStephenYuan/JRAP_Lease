/*
 * *
 *  @file com.maddyhome.idea.copyright.pattern.JavaCopyrightVariablesProvider$1@71e19cdb$
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

import java.text.ParseException;

public interface ICustomerPengYuanService extends ProxySelf<ICustomerPengYuanService> {
    /**
     * 个人征信反欺诈查询
     * @param customerName
     * @param idNumber
     * @param phone
     * @throws ParseException
     * @throws Exception
     */
    public String readCustomerCredit(IRequest request, Long customerId, String customerName, String idNumber, String phone) throws ParseException, Exception;

    /**
     * 个人征信多头借贷查询
     * @param customerName
     * @param idNumber
     * @param phone
     * @throws ParseException
     * @throws Exception
     */
    public String readCustomerLoan(IRequest request, Long customerId, String customerName, String idNumber, String phone) throws ParseException, Exception;
}
