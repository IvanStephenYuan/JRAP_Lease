package com.jingrui.jrap.mail.dto;

import com.jingrui.jrap.system.dto.BaseDTO;
import org.hibernate.validator.constraints.Length;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 消息账户.
 *
 * @author shengyang.zhou@jingrui.com
 */
@Table(name = "SYS_MESSAGE_ACCOUNT")
public class MessageAccount extends BaseDTO {

    @Id
    @GeneratedValue(generator = GENERATOR_TYPE)
    private Long accountId;

    @Length(max = 240)
    private String description;

    @Length(max = 50)
    private String accountType;

    @Length(max = 50)
    private String accountCode;

    @Length(max = 240)
    private String userName;

    @Length(max = 240)
    private String password;

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType == null ? null : accountType.trim();
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode == null ? null : accountCode.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}