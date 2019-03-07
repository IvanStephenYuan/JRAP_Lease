package com.jingrui.jrap.mail.dto;

import com.jingrui.jrap.system.dto.BaseDTO;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 邮箱账号 vo.
 *
 * @author Clerifen Li
 */
@Table(name = "SYS_MESSAGE_EMAIL_ACCOUNT")
public class MessageEmailAccount extends BaseDTO {

    private static final long serialVersionUID = 9164922042055278043L;

    @Id
    @GeneratedValue(generator = GENERATOR_TYPE)
    private Long accountId;

    private Long configId;

    @Length(max = 50)
    @NotEmpty
    private String accountCode;

    @Length(max = 240)
    @NotEmpty
    private String userName;

    @Length(max = 240)
    @NotEmpty
    private String password;

    @Length(max = 240)
    private String description;

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getConfigId() {
        return configId;
    }

    public void setConfigId(Long configId) {
        this.configId = configId;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = StringUtils.trim(accountCode);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = StringUtils.trim(userName);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}