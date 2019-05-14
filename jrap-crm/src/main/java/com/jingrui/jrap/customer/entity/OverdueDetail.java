/*
 * *
 *  @file com.maddyhome.idea.copyright.pattern.JavaCopyrightVariablesProvider$1@2546c53d$
 *  @CopyRight (C) 2018 ZheJiangJingRui Co. Ltd.
 *  @brief JingRui Application Platform
 *  @author $name$
 *  @email yulong.yuan@jr-info.cn
 *  @date $date$
 * /
 */

package com.jingrui.jrap.customer.entity;

/**
 * 信贷逾期名单概要信息
 */
public class OverdueDetail {
    private String overdueDays; //逾期天数:逾期1-6天，逾期7-29天，逾期30-59天，逾期60-89天，逾期90天以上
    private double overdueAmount; //逾期金额
    private String settlement; //是否结清:已结清，未结清，未知

    public String getOverdueDays() {
        return overdueDays;
    }

    public void setOverdueDays(String overdueDays) {
        this.overdueDays = overdueDays;
    }

    public double getOverdueAmount() {
        return overdueAmount;
    }

    public void setOverdueAmount(double overdueAmount) {
        this.overdueAmount = overdueAmount;
    }

    public String getSettlement() {
        return settlement;
    }

    public void setSettlement(String settlement) {
        this.settlement = settlement;
    }
}
