package com.jingrui.jrap.mail;

import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.List;

/**
 * 消息服务,继承了Spring mail sender.
 *
 * @author Clerifen Li
 */
public class MailSender extends JavaMailSenderImpl {

    private Integer tryTimes = 3;

    private String environment;

    private List<String> whiteList;

    private String messageAccount;

    public String getMessageAccount() {
        return messageAccount;
    }

    public void setMessageAccount(String messageAccount) {
        this.messageAccount = messageAccount;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public Integer getTryTimes() {
        return tryTimes;
    }

    public void setTryTimes(Integer tryTimes) {
        this.tryTimes = tryTimes;
    }

    public List<String> getWhiteList() {
        return whiteList;
    }

    public void setWhiteList(List<String> whiteList) {
        this.whiteList = whiteList;
    }

}
