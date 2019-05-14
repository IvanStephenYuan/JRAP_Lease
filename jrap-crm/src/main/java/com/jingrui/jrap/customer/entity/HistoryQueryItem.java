/*
 * *
 *  @file com.maddyhome.idea.copyright.pattern.JavaCopyrightVariablesProvider$1@2d2fa3d5$
 *  @CopyRight (C) 2018 ZheJiangJingRui Co. Ltd.
 *  @brief JingRui Application Platform
 *  @author $name$
 *  @email yulong.yuan@jr-info.cn
 *  @date $date$
 * /
 */
package com.jingrui.jrap.customer.entity;

/**
 * 各单位类型查询记录
 */
public class HistoryQueryItem {
    private String unitMember; //单位类型名称
    private int last1Month; //近1个月查询记录数
    private int last3Month;
    private int last6Month;
    private int last12Month;
    private int last18Month;
    private int last24Month;

    public String getUnitMember() {
        return unitMember;
    }

    public void setUnitMember(String unitMember) {
        this.unitMember = unitMember;
    }

    public int getLast1Month() {
        return last1Month;
    }

    public void setLast1Month(int last1Month) {
        this.last1Month = last1Month;
    }

    public int getLast3Month() {
        return last3Month;
    }

    public void setLast3Month(int last3Month) {
        this.last3Month = last3Month;
    }

    public int getLast6Month() {
        return last6Month;
    }

    public void setLast6Month(int last6Month) {
        this.last6Month = last6Month;
    }

    public int getLast12Month() {
        return last12Month;
    }

    public void setLast12Month(int last12Month) {
        this.last12Month = last12Month;
    }

    public int getLast18Month() {
        return last18Month;
    }

    public void setLast18Month(int last18Month) {
        this.last18Month = last18Month;
    }

    public int getLast24Month() {
        return last24Month;
    }

    public void setLast24Month(int last24Month) {
        this.last24Month = last24Month;
    }
}
