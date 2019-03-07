/*
 * *
 *  @file com.maddyhome.idea.copyright.pattern.JavaCopyrightVariablesProvider$1@17e26754$
 *  @CopyRight (C) 2018 ZheJiangJingRui Co. Ltd.
 *  @brief JingRui Application Platform
 *  @author $name$
 *  @email yulong.yuan@jr-info.cn
 *  @date $date$
 * /
 */

package com.jingrui.jrap.customer.service.impl;

import com.baidu.aip.ocr.AipOcr;
import com.jingrui.jrap.customer.dto.CarLicense;
import com.jingrui.jrap.customer.dto.CustomerAccount;
import com.jingrui.jrap.customer.dto.CustomerID;
import com.jingrui.jrap.customer.dto.CustomerLicense;
import com.jingrui.jrap.customer.service.ICustomerOcrService;
import com.jingrui.jrap.customer.util.CustomerEnum;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Service
@Transactional(rollbackFor = Exception.class)
public class CustomerOcrServiceImpl implements ICustomerOcrService {
    @Value("${baidu.appId}")
    private String appId;

    @Value("${baidu.apiKey}")
    private String apiKey;

    @Value("${baidu.secretKey}")
    private String secretKey;

    @Override
    public CustomerID readCustomerPicture(String path, String side) throws ParseException, Exception {
        CustomerID customerOcr = new CustomerID();
        String errorCode;
        AipOcr client = new AipOcr(this.appId, this.apiKey, this.secretKey);
        // 设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("detect_direction", "true");
        options.put("detect_risk", "true");

        JSONObject res = client.idcard(path, side, options);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        if (res.isNull("error_code")) {
            customerOcr.setDirection(Integer.valueOf(res.get("direction").toString()));
            customerOcr.setImageStatus(res.get("image_status").toString());
            customerOcr.setRiskType(res.get("risk_type").toString());
            customerOcr.setEditTool(res.get("edit_tool").toString());
            customerOcr.setLogId(res.get("log_id").toString());

            JSONObject words = res.getJSONObject("words_result");
            if (!words.isNull(CustomerID.IADDRESS)) {
                JSONObject temp = words.getJSONObject(CustomerID.IADDRESS);
                customerOcr.setAddress(temp.get("words").toString());
            }

            if (!words.isNull(CustomerID.IIDNUMBER)) {
                JSONObject temp = words.getJSONObject(CustomerID.IIDNUMBER);
                customerOcr.setIdNumber(temp.get("words").toString());
            }

            if (!words.isNull(CustomerID.IBORNDATE)) {
                JSONObject temp = words.getJSONObject(CustomerID.IBORNDATE);
                String date = temp.get("words").toString();
                customerOcr.setBornDate(dateFormat.parse(date));
            }

            if (!words.isNull(CustomerID.INAME)) {
                JSONObject temp = words.getJSONObject(CustomerID.INAME);
                customerOcr.setCustomerName(temp.get("words").toString());
            }

            if (!words.isNull(CustomerID.ISEX)) {
                JSONObject temp = words.getJSONObject(CustomerID.ISEX);
                customerOcr.setSex(temp.get("words").toString());
            }

            if (!words.isNull(CustomerID.INATION)) {
                JSONObject temp = words.getJSONObject(CustomerID.INATION);
                customerOcr.setNation(temp.get("words").toString());
            }

            if (!words.isNull(CustomerID.INVALIDDATE)) {
                JSONObject temp = words.getJSONObject(CustomerID.INVALIDDATE);
                String date = temp.get("words").toString();
                customerOcr.setInvalidDate(dateFormat.parse(date));
            }

            if (!words.isNull(CustomerID.SIGNORG)) {
                JSONObject temp = words.getJSONObject(CustomerID.SIGNORG);
                customerOcr.setSignORG(temp.get("words").toString());
            }

            if (!words.isNull(CustomerID.SIGNDATE)) {
                JSONObject temp = words.getJSONObject(CustomerID.SIGNDATE);
                String date = temp.get("words").toString();
                customerOcr.setSignDate(dateFormat.parse(date));
            }
        } else {
            errorCode = res.get("error_code").toString();
            throw new Exception(CustomerEnum.getErrorDescription(errorCode));
        }

        return customerOcr;
    }

    /***
     * 驾照识别
     * @param path
     * @return
     * @throws ParseException
     * @throws Exception
     */
    public CustomerLicense readDrivingLicense(String path) throws ParseException, Exception{
        CustomerLicense customerOcr = new CustomerLicense();
        String errorCode;
        AipOcr client = new AipOcr(this.appId, this.apiKey, this.secretKey);
        // 设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("detect_direction", "true");
        options.put("unified_valid_period", "true");

        JSONObject res = client.drivingLicense(path, options);
        System.out.println(res.toString(2));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        if (res.isNull("error_code")) {
            customerOcr.setLogId(res.get("log_id").toString());

            JSONObject words = res.getJSONObject("words_result");
            if (!words.isNull(CustomerLicense.ILICENSENUM)) {
                JSONObject temp = words.getJSONObject(CustomerLicense.ILICENSENUM);
                customerOcr.setIdNumber(temp.get("words").toString());
            }

            if (!words.isNull(CustomerLicense.VALIDLIMIT)) {
                JSONObject temp = words.getJSONObject(CustomerLicense.VALIDLIMIT);
                String date = temp.get("words").toString();
                customerOcr.setValidLimit(dateFormat.parse(date));
            }

            if (!words.isNull(CustomerLicense.LICENSETYPE)) {
                JSONObject temp = words.getJSONObject(CustomerLicense.LICENSETYPE);
                customerOcr.setLicenseType(temp.get("words").toString());
            }

            if (!words.isNull(CustomerLicense.VALIDSTARTDATE)) {
                JSONObject temp = words.getJSONObject(CustomerLicense.VALIDSTARTDATE);
                String date = temp.get("words").toString();
                customerOcr.setValidStartDate(dateFormat.parse(date));
            }

            if (!words.isNull(CustomerLicense.IADDRESS)) {
                JSONObject temp = words.getJSONObject(CustomerLicense.IADDRESS);
                customerOcr.setAddress(temp.get("words").toString());
            }

            if (!words.isNull(CustomerLicense.INAME)) {
                JSONObject temp = words.getJSONObject(CustomerLicense.INAME);
                customerOcr.setCustomerName(temp.get("words").toString());
            }

            if (!words.isNull(CustomerLicense.ICOUNTRY)) {
                JSONObject temp = words.getJSONObject(CustomerLicense.ICOUNTRY);
                customerOcr.setCountry(temp.get("words").toString());
            }

            if (!words.isNull(CustomerLicense.IBORNDATE)) {
                JSONObject temp = words.getJSONObject(CustomerLicense.IBORNDATE);
                String date = temp.get("words").toString();
                customerOcr.setBornDate(dateFormat.parse(date));
            }

            if (!words.isNull(CustomerLicense.ISEX)) {
                JSONObject temp = words.getJSONObject(CustomerLicense.ISEX);
                customerOcr.setSex(temp.get("words").toString());
            }

            if (!words.isNull(CustomerLicense.LICENSESTARTDATE)) {
                JSONObject temp = words.getJSONObject(CustomerLicense.LICENSESTARTDATE);
                String date = temp.get("words").toString();
                customerOcr.setLicenseStartDate(dateFormat.parse(date));
            }
        } else {
            errorCode = res.get("error_code").toString();
            throw new Exception(CustomerEnum.getErrorDescription(errorCode));
        }

        return customerOcr;
    }

    @Override
    public CustomerAccount readCustomerAccount(String path) throws Exception {
        CustomerAccount customerAccount = new CustomerAccount();
        String errorCode;
        AipOcr client = new AipOcr(this.appId, this.apiKey, this.secretKey);
        // 设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        JSONObject res = client.bankcard(path, options);
        System.out.println(res.toString(2));

        if (res.isNull("error_code")) {
            customerAccount.setLogId(res.get("log_id").toString());

            JSONObject words = res.getJSONObject("result");
            if (!words.isNull(CustomerAccount.ACCOUNT)) {
                String temp = words.getString(CustomerAccount.ACCOUNT);
                customerAccount.setAccount(temp.replaceAll(" ", ""));
            }

            if (!words.isNull(CustomerAccount.BANKNAME)) {
                String temp = words.getString(CustomerAccount.BANKNAME);
                customerAccount.setBankName(temp);
            }

            if (!words.isNull(CustomerAccount.BANKTYPE)) {
                int temp = words.getInt(CustomerAccount.BANKTYPE);
                customerAccount.setBankType(temp);
            }
        } else {
            errorCode = res.get("error_code").toString();
            throw new Exception(CustomerEnum.getErrorDescription(errorCode));
        }

        return customerAccount;
    }

    @Override
    public CarLicense readCarLicense(String path) throws ParseException, Exception {
        CarLicense carLicense = new CarLicense();
        String errorCode;
        AipOcr client = new AipOcr(this.appId, this.apiKey, this.secretKey);
        // 设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("detect_direction", "true");
        options.put("accuracy", "normal");

        JSONObject res = client.vehicleLicense(path, options);
        System.out.println(res.toString(2));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        if (res.isNull("error_code")) {
            carLicense.setLogId(res.get("log_id").toString());

            JSONObject words = res.getJSONObject("words_result");
            if (!words.isNull(CarLicense.MODEL)) {
                JSONObject temp = words.getJSONObject(CarLicense.MODEL);
                carLicense.setModel(temp.get("words").toString());
            }

            if (!words.isNull(CarLicense.LICENSEDATE)) {
                JSONObject temp = words.getJSONObject(CarLicense.LICENSEDATE);
                String date = temp.get("words").toString();
                carLicense.setLicenseDate(dateFormat.parse(date));
            }

            if (!words.isNull(CarLicense.USETYPE)) {
                JSONObject temp = words.getJSONObject(CarLicense.USETYPE);
                carLicense.setUserType(temp.get("words").toString());
            }

            if (!words.isNull(CarLicense.ENGINE)) {
                JSONObject temp = words.getJSONObject(CarLicense.ENGINE);
                carLicense.setEngine(temp.get("words").toString());
            }

            if (!words.isNull(CarLicense.LICENSE)) {
                JSONObject temp = words.getJSONObject(CarLicense.LICENSE);
                carLicense.setLicense(temp.get("words").toString());
            }

            if (!words.isNull(CarLicense.OWNER)) {
                JSONObject temp = words.getJSONObject(CarLicense.OWNER);
                carLicense.setOwner(temp.get("words").toString());
            }

            if (!words.isNull(CarLicense.ADDRESS)) {
                JSONObject temp = words.getJSONObject(CarLicense.ADDRESS);
                carLicense.setAddress(temp.get("words").toString());
            }

            if (!words.isNull(CarLicense.SIGNDATE)) {
                JSONObject temp = words.getJSONObject(CarLicense.SIGNDATE);
                String date = temp.get("words").toString();
                carLicense.setSignDate(dateFormat.parse(date));
            }

            if (!words.isNull(CarLicense.VIN)) {
                JSONObject temp = words.getJSONObject(CarLicense.VIN);
                carLicense.setVin(temp.get("words").toString());
            }

            if (!words.isNull(CarLicense.CARTYPE)) {
                JSONObject temp = words.getJSONObject(CarLicense.CARTYPE);
                carLicense.setCarType(temp.get("words").toString());
            }
        } else {
            errorCode = res.get("error_code").toString();
            throw new Exception(CustomerEnum.getErrorDescription(errorCode));
        }

        return carLicense;
    }
}
