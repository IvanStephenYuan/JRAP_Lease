/*
 * *
 *  @file com.maddyhome.idea.copyright.pattern.JavaCopyrightVariablesProvider$1@23cd7aa9$
 *  @CopyRight (C) 2018 ZheJiangJingRui Co. Ltd.
 *  @brief JingRui Application Platform
 *  @author $name$
 *  @email yulong.yuan@jr-info.cn
 *  @date $date$
 * /
 */

package com.jingrui.jrap.customer.service.impl;

import com.jingrui.jrap.customer.dto.CarLicense;
import com.jingrui.jrap.customer.dto.CustomerAccount;
import com.jingrui.jrap.customer.dto.CustomerID;
import com.jingrui.jrap.customer.dto.CustomerLicense;
import com.jingrui.jrap.customer.service.ICustomerOcrService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.ParseException;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring/applicationContext02.xml"})
public class CustomerOcrServiceImplTest {
    @Autowired
    private ICustomerOcrService customerOcrService;

    @Test
    public void readCustomerPicture() {
        String frontPath = "C:/Users/ivan_/Pictures/IMG_20180820_142925.jpg";
        String backPath = "C:/Users/ivan_/Pictures/IMG_20180820_142933.jpg";
        String frontSide = "front";
        String backSide = "back";
        try{
            CustomerID customerOcr = customerOcrService.readCustomerPicture(frontPath, frontSide);
            assertEquals(customerOcr.getIdNumber(), "42070319910916273X");
            assertEquals(customerOcr.getCustomerName(), "袁雨龙");

            customerOcr = customerOcrService.readCustomerPicture(backPath, backSide);
            assertEquals(customerOcr.getSignORG(), "鄂州市公安局开发区公安局");
            assertEquals(customerOcr.getInvalidDate(), "20380511");
        }catch (ParseException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void readDrivingLicense() {
        String path = "C:/Users/ivan_/Pictures/20190306100939.jpg";
        try{
            CustomerLicense customerLicense = customerOcrService.readDrivingLicense(path);
            assertEquals(customerLicense.getIdNumber(), "513022198901204893");
            assertEquals(customerLicense.getCustomerName(), "赵开鸿");
        }catch (ParseException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void readCustomerAccount() {
        String path = "C:/Users/ivan_/Pictures/20190306134532.jpg";
        try{
            CustomerAccount customerAccount = customerOcrService.readCustomerAccount(path);
            assertEquals(customerAccount.getAccount(), "6217251400022192076");
            assertEquals(customerAccount.getBankName(), "中国银行");
        }catch (ParseException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void readCarLicense() {
        String path = "C:/Users/ivan_/Pictures/20190306134452.jpg";
        try{
            CarLicense carLicense = customerOcrService.readCarLicense(path);
            assertEquals(carLicense.getEngine(), "H4CB0016306");
            assertEquals(carLicense.getLicense(), "浙B8B6K3");
            assertEquals(carLicense.getVin(), "L6T7742Z4HN456791");
        }catch (ParseException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}