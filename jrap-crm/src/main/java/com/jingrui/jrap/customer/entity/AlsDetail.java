/*
 * *
 *  @file com.maddyhome.idea.copyright.pattern.JavaCopyrightVariablesProvider$1@721ec8ae$
 *  @CopyRight (C) 2018 ZheJiangJingRui Co. Ltd.
 *  @brief JingRui Application Platform
 *  @author $name$
 *  @email yulong.yuan@jr-info.cn
 *  @date $date$
 * /
 */

package com.jingrui.jrap.customer.entity;

/**
 * 司法案例概要信息
 */
public class AlsDetail {
    private String recordId;  //记录编号
    private String bt; //标题
    private String ajlx; //案件类型
    private String sjnf; //审结年份
    private String dsrlx; //当事人类型

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

    public String getAjlx() {
        return ajlx;
    }

    public void setAjlx(String ajlx) {
        this.ajlx = ajlx;
    }

    public String getSjnf() {
        return sjnf;
    }

    public void setSjnf(String sjnf) {
        this.sjnf = sjnf;
    }

    public String getDsrlx() {
        return dsrlx;
    }

    public void setDsrlx(String dsrlx) {
        this.dsrlx = dsrlx;
    }
}
