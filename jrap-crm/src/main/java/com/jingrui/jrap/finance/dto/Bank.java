package com.jingrui.jrap.finance.dto;

/**
 * Bank class
 *
 * @author IvanStephen
 * @date 2019/5/31
 */
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.jingrui.jrap.mybatis.annotation.ExtensionAttribute;
import org.hibernate.validator.constraints.Length;
import javax.persistence.Table;
import com.jingrui.jrap.system.dto.BaseDTO;
import org.hibernate.validator.constraints.NotEmpty;

@ExtensionAttribute(disable = true)
@Table(name = "afd_bank")
public class Bank extends BaseDTO {
    public static final String FIELD_BANK_ID = "bankId";
    public static final String FIELD_BANK_CODE = "bankCode";
    public static final String FIELD_SHORT_NAME = "shortName";
    public static final String FIELD_FULL_NAME = "fullName";
    public static final String FIELD_PAY_BANK_NUMBER = "payBankNumber";
    public static final String FIELD_PROVINCE = "province";
    public static final String FIELD_CITY = "city";
    public static final String FIELD_REMARK = "remark";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";
    public static final String FIELD_PROGRAM_APPLICATION_ID = "programApplicationId";
    public static final String FIELD_PROGRAM_UPDATE_DATE = "programUpdateDate";

    @Id
    @GeneratedValue
    private Long bankId;

    /**
     * 银行CODE
     */
    @Length(max = 30)
    private String bankCode;

    /**
     * 银行简称
     */
    @Length(max = 60)
    private String shortName;

    /**
     * 银行全称
     */
    @Length(max = 200)
    private String fullName;

    /**
     * 支付联行号
     */
    @NotEmpty
    @Length(max = 50)
    private String payBankNumber;

    /**
     * 省份
     */
    @Length(max = 150)
    private String province;

    /**
     * 城市
     */
    @Length(max = 150)
    private String city;

    /**
     * 备注说明
     */
    @Length(max = 2147483647)
    private String remark;

    /**
     * 启用标识
     */
    @Length(max = 1)
    private String enabledFlag;

    public Long getBankId() {
        return bankId;
    }

    public void setBankId(Long bankId) {
        this.bankId = bankId;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPayBankNumber() {
        return payBankNumber;
    }

    public void setPayBankNumber(String payBankNumber) {
        this.payBankNumber = payBankNumber;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getEnabledFlag() {
        return enabledFlag;
    }

    public void setEnabledFlag(String enabledFlag) {
        this.enabledFlag = enabledFlag;
    }
}
