/*
 * *
 *  @file com.maddyhome.idea.copyright.pattern.JavaCopyrightVariablesProvider$1@54da90e$
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
 * 司法失信概要信息
 */
public class SxsDetail {
    private String recordId;  //记录编号
    private String bt; //标题
    private Date larq; //立案日期
    private Date fbrq; //发布日期

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

    public Date getLarq() {
        return larq;
    }

    public void setLarq(Date larq) {
        this.larq = larq;
    }

    public Date getFbrq() {
        return fbrq;
    }

    public void setFbrq(Date fbrq) {
        this.fbrq = fbrq;
    }
}
