/*
 * *
 *  @file com.maddyhome.idea.copyright.pattern.JavaCopyrightVariablesProvider$1@161dfac8$
 *  @CopyRight (C) 2018 ZheJiangJingRui Co. Ltd.
 *  @brief JingRui Application Platform
 *  @author $name$
 *  @email yulong.yuan@jr-info.cn
 *  @date $date$
 * /
 */

package com.jingrui.jrap.customer.service;

import com.jingrui.jrap.core.ProxySelf;
import com.jingrui.jrap.customer.dto.CarLicense;
import com.jingrui.jrap.customer.dto.CustomerAccount;
import com.jingrui.jrap.customer.dto.CustomerID;
import com.jingrui.jrap.customer.dto.CustomerLicense;

import java.text.ParseException;

public interface ICustomerOcrService extends ProxySelf<ICustomerOcrService> {
    /**
     * 身份证识别
     * @param path
     * @param side
     * @return
     * @throws ParseException
     * @throws Exception
     */
    public CustomerID readCustomerPicture(String path, String side) throws ParseException, Exception;

    /***
     * 驾照识别
     * @param path
     * @return
     * @throws ParseException
     * @throws Exception
     */
    public CustomerLicense readDrivingLicense(String path) throws ParseException, Exception;

    /**
     * 银行卡识别
     * @param path
     * @return
     * @throws ParseException
     * @throws Exception
     */
    public CustomerAccount readCustomerAccount(String path) throws Exception;

    /**
     * 行驶证识别
     * @param path
     * @return
     * @throws ParseException
     * @throws Exception
     */
    public CarLicense readCarLicense(String path) throws ParseException, Exception;
}
