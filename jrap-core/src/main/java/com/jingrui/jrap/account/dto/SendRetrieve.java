package com.jingrui.jrap.account.dto;

import java.util.Date;

import javax.persistence.Table;

import com.jingrui.jrap.system.dto.BaseDTO;

/**
 * 发送次数限制DTO.
 *
 * @author shengyang.zhou@jingrui.com
 * @date 2016/6/10
 */

@Table(name = "SYS_ACCOUNT_RETRIEVE")
public class SendRetrieve extends BaseDTO {
    private Long userId;

    private String retrieveType;

    private Date retrieveDate;
    
    private String email;
    
    private String phoneNo;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getRetrieveType() {
        return retrieveType;
    }

    public void setRetrieveType(String retrieveType) {
        this.retrieveType = retrieveType == null ? null : retrieveType.trim();
    }

    public Date getRetrieveDate() {
        return retrieveDate;
    }

    public void setRetrieveDate(Date retrieveDate) {
        this.retrieveDate = retrieveDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
    
}