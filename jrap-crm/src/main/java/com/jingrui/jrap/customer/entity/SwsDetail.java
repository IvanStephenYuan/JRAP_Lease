/*
 * *
 *  @file com.maddyhome.idea.copyright.pattern.JavaCopyrightVariablesProvider$1@32218d$
 *  @CopyRight (C) 2018 ZheJiangJingRui Co. Ltd.
 *  @brief JingRui Application Platform
 *  @author $name$
 *  @email yulong.yuan@jr-info.cn
 *  @date $date$
 * /
 */

package com.jingrui.jrap.customer.entity;

import java.util.Date;

/**
 * 税务行政执法概要信息
 */
public class SwsDetail {
    private String recordId;  //记录编号
    private String bt; //标题
    private Date ggrq; //公告日期

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getBt() {
        return bt;
    }

    public void setBt(String bt) {
        this.bt = bt;
    }

    public Date getGgrq() {
        return ggrq;
    }

    public void setGgrq(Date ggrq) {
        this.ggrq = ggrq;
    }
}
