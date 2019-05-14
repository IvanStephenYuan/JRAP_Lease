/*
 * *
 *  @file com.maddyhome.idea.copyright.pattern.JavaCopyrightVariablesProvider$1@570642fe$
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
 * 司法执行概要信息
 */
public class ZxsDetail {
    private String recordId;  //记录编号
    private String bt; //标题
    private String zxbd; //执行标的
    private Date larq; //立案日期

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

    public String getZxbd() {
        return zxbd;
    }

    public void setZxbd(String zxbd) {
        this.zxbd = zxbd;
    }

    public Date getLarq() {
        return larq;
    }

    public void setLarq(Date larq) {
        this.larq = larq;
    }
}
