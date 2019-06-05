/*
 * *
 *  @file com.maddyhome.idea.copyright.pattern.JavaCopyrightVariablesProvider$1@48cb570a$
 *  @CopyRight (C) 2018 ZheJiangJingRui Co. Ltd.
 *  @brief JingRui Application Platform
 *  @author $name$
 *  @email yulong.yuan@jr-info.cn
 *  @date $date$
 * /
 */
package com.jingrui.jrap.customer.dto;
import java.util.Date;

public class BusinessLicense {
    public final static String INAME = "单位名称";
    public final static String ILEGALPERSON = "法人";
    public final static String IADDRESS = "地址";
    public final static String IBORNDATE = "成立日期";
    public final static String IVVALIDITY = "有效期";
    public final static String IDNUMBER = "证件编号";
    public final static String IUSCC = "社会信用代码";

    private int direction;
    private String riskType;
    private String editTool;
    private String logId;

    private String companyName;
    private String legelPerson;
    private String address;
    private Date bornDate;
    private String validity;
    private String idNumber;
    private String uscc;

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getLegelPerson() {
        return legelPerson;
    }

    public void setLegelPerson(String legelPerson) {
        this.legelPerson = legelPerson;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getBornDate() {
        return bornDate;
    }

    public void setBornDate(Date bornDate) {
        this.bornDate = bornDate;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getUscc() {
        return uscc;
    }

    public void setUscc(String uscc) {
        this.uscc = uscc;
    }
}
