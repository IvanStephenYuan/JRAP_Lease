/*
 * *
 *  @file com.maddyhome.idea.copyright.pattern.JavaCopyrightVariablesProvider$1@37978d57$
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

@Component
public class TdConfig {
    @Value("${tongdun.queryUrl}")
    private String queryUrl;

    @Value("${tongdun.applyUrl}")
    private String applyUrl;

    @Value("${tongdun.partnerCode}")
    private String partnerCode;

    @Value("${tongdun.partnerKey}")
    private String partnerKey;

    @Value("${tongdun.partnerApp}")
    private String partnerApp;

    public String getQueryUrl() {
        return queryUrl;
    }

    public void setQueryUrl(String queryUrl) {
        this.queryUrl = queryUrl;
    }

    public String getApplyUrl() {
        return applyUrl;
    }

    public void setApplyUrl(String applyUrl) {
        this.applyUrl = applyUrl;
    }

    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }

    public String getPartnerKey() {
        return partnerKey;
    }

    public void setPartnerKey(String partnerKey) {
        this.partnerKey = partnerKey;
    }

    public String getPartnerApp() {
        return partnerApp;
    }

    public void setPartnerApp(String partnerApp) {
        this.partnerApp = partnerApp;
    }
}
