/*
 * *
 *  @file com.maddyhome.idea.copyright.pattern.JavaCopyrightVariablesProvider$1@3674d4aa$
 *  @CopyRight (C) 2018 ZheJiangJingRui Co. Ltd.
 *  @brief JingRui Application Platform
 *  @author $name$
 *  @email yulong.yuan@jr-info.cn
 *  @date $date$
 * /
 */

package com.jingrui.jrap.customer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 *
 */
@Component
public class PyConfig {
    // API域名
    @Value("${pengyuan.host}")
    private String host;

    // 返回报文不压缩的URL
    @Value("${pengyuan.path}")
    private String path;

    // 认证信息
    @Value("${pengyuan.userid}")
    private String userId;
    @Value("${pengyuan.password}")
    private String password;

    //证书信息
    @Value("${pengyuan.certpath}")
    private String certPath;

    @Value("${pengyuan.certpassword}")
    private String certPassword;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCertPath() {
        String filePath = this.getClass().getResource(this.certPath).getPath();
        return filePath;
    }

    public void setCertPath(String certPath) {
        this.certPath = certPath;
    }

    public String getCertPassword() {
        return certPassword;
    }

    public void setCertPassword(String certPassword) {
        this.certPassword = certPassword;
    }
}
