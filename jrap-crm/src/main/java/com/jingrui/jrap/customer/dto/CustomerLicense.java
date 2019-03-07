/*
 * *
 *  @file com.maddyhome.idea.copyright.pattern.JavaCopyrightVariablesProvider$1@4931690a$
 *  @CopyRight (C) 2018 ZheJiangJingRui Co. Ltd.
 *  @brief JingRui Application Platform
 *  @author $name$
 *  @email yulong.yuan@jr-info.cn
 *  @date $date$
 * /
 */
package com.jingrui.jrap.customer.dto;
import java.util.Date;

public class CustomerLicense {
    /*** 身份证正面 **/
    public final static String IADDRESS = "住址";
    public final static String ILICENSENUM = "证号";
    public final static String IBORNDATE = "出生日期";
    public final static String INAME = "姓名";
    public final static String ISEX = "性别";
    public final static String ICOUNTRY = "国家";
    public final static String VALIDLIMIT = "有效期限";
    public final static String VALIDSTARTDATE = "有效起始日期";
    public final static String LICENSESTARTDATE = "初次领证日期";
    public final static String LICENSETYPE = "准驾车型";

    private int direction;
    private String imageStatus;
    private String riskType;
    private String editTool;
    private String logId;
    private String address;
    private String idNumber;
    private Date validLimit;
    private String licenseType;
    private Date bornDate;
    private String customerName;
    private String sex;
    private String country;
    private Date validStartDate;
    private Date licenseStartDate;

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public String getImageStatus() {
        return imageStatus;
    }

    public void setImageStatus(String imageStatus) {
        this.imageStatus = imageStatus;
    }

    public String getRiskType() {
        return riskType;
    }

    public void setRiskType(String riskType) {
        this.riskType = riskType;
    }

    public String getEditTool() {
        return editTool;
    }

    public void setEditTool(String editTool) {
        this.editTool = editTool;
    }

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public Date getBornDate() {
        return bornDate;
    }

    public void setBornDate(Date bornDate) {
        this.bornDate = bornDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getValidLimit() {
        return validLimit;
    }

    public void setValidLimit(Date validLimit) {
        this.validLimit = validLimit;
    }

    public String getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(String licenseType) {
        this.licenseType = licenseType;
    }

    public Date getValidStartDate() {
        return validStartDate;
    }

    public void setValidStartDate(Date validStartDate) {
        this.validStartDate = validStartDate;
    }

    public Date getLicenseStartDate() {
        return licenseStartDate;
    }

    public void setLicenseStartDate(Date licenseStartDate) {
        this.licenseStartDate = licenseStartDate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
