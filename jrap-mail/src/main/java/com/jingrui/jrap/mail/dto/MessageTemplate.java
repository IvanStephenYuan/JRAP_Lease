package com.jingrui.jrap.mail.dto;

import com.jingrui.jrap.system.dto.BaseDTO;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 消息模板.
 *
 * @author qiang.zeng@jingrui.com
 */
@Table(name = "SYS_MESSAGE_TEMPLATE")
public class MessageTemplate extends BaseDTO {

    @Id
    @GeneratedValue(generator = GENERATOR_TYPE)
    private Long templateId;

    private Long accountId;

    @Length(max = 50)
    @NotEmpty
    private String templateCode;

    @Length(max = 240)
    private String description;

    @Length(max = 50)
    private String templateType;

    @Length(max = 50)
    private String priorityLevel;

    @NotEmpty
    private String subject;

    @NotEmpty
    private String content;

    @Transient
    private String meaning;

    @Length(max = 50)
    private String sendType;

    @Transient
    private String userName;

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = StringUtils.trim(templateCode);
    }

    public String getTemplateType() {
        return templateType;
    }

    public void setTemplateType(String templateType) {
        this.templateType = templateType;
    }

    public String getPriorityLevel() {
        return priorityLevel;
    }

    public void setPriorityLevel(String priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public String getSendType() {
        return sendType;
    }

    public void setSendType(String sendType) {
        this.sendType = sendType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}