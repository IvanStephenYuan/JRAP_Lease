package com.jingrui.jrap.security.oauth.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jingrui.jrap.core.BaseConstants;
import com.jingrui.jrap.mybatis.annotation.Condition;
import com.jingrui.jrap.mybatis.annotation.ExtensionAttribute;
import javax.persistence.Table;
import com.jingrui.jrap.system.dto.BaseDTO;
import java.util.Date;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

/**
 * @author qixiangyu
 */
@ExtensionAttribute(disable = true)
@Table(name = "sys_token_logs")
public class TokenLogs extends BaseDTO {
    @Id
    @GeneratedValue
    private Long id;

    /**
     * 用户ID
     */
    @NotNull
    private Long userId;

    /**
     * 客户端ID
     */
    private String clientId;

    /**
     * token
     */
    private String token;

    /**
     * token获取日期
     */
    private Date tokenAccessTime;

    /**
     * token失效日期
     */
    @Condition(operator = "&gt;")
    @JsonFormat(pattern = BaseConstants.DATE_TIME_FORMAT)
    private Date tokenExpiresTime;

    /**
     * 是否有效
     */
    private String revokeFlag ;

    /**
     * token获取方式
     */
    private String tokenAccessType;

    /**
     * 查询当前token状态,online,all
     */
    @Transient
    private String tokenStatus;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setTokenAccessTime(Date tokenAccessTime) {
        this.tokenAccessTime = tokenAccessTime;
    }

    public Date getTokenAccessTime() {
        return tokenAccessTime;
    }

    public void setTokenAccessType(String tokenAccessType) {
        this.tokenAccessType = tokenAccessType;
    }

    public String getTokenAccessType() {
        return tokenAccessType;
    }

    public String getRevokeFlag() {
        return revokeFlag;
    }

    public void setRevokeFlag(String revokeFlag) {
        this.revokeFlag = revokeFlag;
    }

    public Date getTokenExpiresTime() {
        return tokenExpiresTime;
    }

    public void setTokenExpiresTime(Date tokenExpiresTime) {
        this.tokenExpiresTime = tokenExpiresTime;
    }

    public String getTokenStatus() {
        return tokenStatus;
    }

    public void setTokenStatus(String tokenStatus) {
        this.tokenStatus = tokenStatus;
    }
}
