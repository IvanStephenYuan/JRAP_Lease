/*
 * *
 *  @file com.maddyhome.idea.copyright.pattern.JavaCopyrightVariablesProvider$1@1062e3d2$
 *  @CopyRight (C) 2018 ZheJiangJingRui Co. Ltd.
 *  @brief JingRui Application Platform
 *  @author $name$
 *  @email yulong.yuan@jr-info.cn
 *  @date $date$
 * /
 */

package com.jingrui.jrap.product.dto;

import io.swagger.annotations.ApiModel;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

@ApiModel(value = "productCalculateLine", description = "产品计算结果")
public class ProductCalculateLine {
    private String cfType; //现金流类型
    private String cfDirection; //现金流方向（INFLOW/OUTFLOW/NONCASH）
    private String cfStatus; //现金流状态（BLOCK/冻结 、RELEASE/下达、CANCEL/取消）
    private int times; //期数
    private Date calcDate; //计算日
    private Date dueDate; //支付日/到期日
    private Double dueAmount; //应收金额
    private Double netDueAmount; //不含税应收金额
    private Double vatDueAmount; //增值税额
    private Double principal; //应收本金
    private Double netPrincipal; //不含税本金
    private Double vatPrincipal; //本金增值税额
    private Double interest; //应收利息
    private Double netInterest; //不含税利息
    private Double vatInterest; //利息增值税额
    private Double principalImplicitRate; //实际利率法本金
    private Double netPrincipalImplicit; //实际利率法不含税本金
    private Double vatPrincipalImplicit; //实际利率法本金增值税额
    private Double interestImplicitRate; //实际利率法利息
    private Double netInterestImplicit; //实际利率法不含税利息
    private Double vatInterestImplicit; //实际利率法利息增值税额
    private Double outstandingRental; //当期剩余租金
    private Double outstandingPrincipal; //当期剩余本金
    private Double outstandingInterest; //当期剩余利息
    private Double interestPeriodDays; //计息天数（利息期天数）

    public String getCfType() {
        return cfType;
    }

    public void setCfType(String cfType) {
        this.cfType = cfType;
    }

    public String getCfDirection() {
        return cfDirection;
    }

    public void setCfDirection(String cfDirection) {
        this.cfDirection = cfDirection;
    }

    public String getCfStatus() {
        return cfStatus;
    }

    public void setCfStatus(String cfStatus) {
        this.cfStatus = cfStatus;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public Date getCalcDate() {
        return calcDate;
    }

    public void setCalcDate(Date calcDate) {
        this.calcDate = calcDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Double getDueAmount() {
        return dueAmount;
    }

    public void setDueAmount(Double dueAmount) {
        this.dueAmount = dueAmount;
    }

    public Double getNetDueAmount() {
        return netDueAmount;
    }

    public void setNetDueAmount(Double netDueAmount) {
        this.netDueAmount = netDueAmount;
    }

    public Double getVatDueAmount() {
        return vatDueAmount;
    }

    public void setVatDueAmount(Double vatDueAmount) {
        this.vatDueAmount = vatDueAmount;
    }

    public Double getPrincipal() {
        return principal;
    }

    public void setPrincipal(Double principal) {
        this.principal = principal;
    }

    public Double getNetPrincipal() {
        return netPrincipal;
    }

    public void setNetPrincipal(Double netPrincipal) {
        this.netPrincipal = netPrincipal;
    }

    public Double getVatPrincipal() {
        return vatPrincipal;
    }

    public void setVatPrincipal(Double vatPrincipal) {
        this.vatPrincipal = vatPrincipal;
    }

    public Double getInterest() {
        return interest;
    }

    public void setInterest(Double interest) {
        this.interest = interest;
    }

    public Double getNetInterest() {
        return netInterest;
    }

    public void setNetInterest(Double netInterest) {
        this.netInterest = netInterest;
    }

    public Double getVatInterest() {
        return vatInterest;
    }

    public void setVatInterest(Double vatInterest) {
        this.vatInterest = vatInterest;
    }

    public Double getPrincipalImplicitRate() {
        return principalImplicitRate;
    }

    public void setPrincipalImplicitRate(Double principalImplicitRate) {
        this.principalImplicitRate = principalImplicitRate;
    }

    public Double getNetPrincipalImplicit() {
        return netPrincipalImplicit;
    }

    public void setNetPrincipalImplicit(Double netPrincipalImplicit) {
        this.netPrincipalImplicit = netPrincipalImplicit;
    }

    public Double getVatPrincipalImplicit() {
        return vatPrincipalImplicit;
    }

    public void setVatPrincipalImplicit(Double vatPrincipalImplicit) {
        this.vatPrincipalImplicit = vatPrincipalImplicit;
    }

    public Double getInterestImplicitRate() {
        return interestImplicitRate;
    }

    public void setInterestImplicitRate(Double interestImplicitRate) {
        this.interestImplicitRate = interestImplicitRate;
    }

    public Double getNetInterestImplicit() {
        return netInterestImplicit;
    }

    public void setNetInterestImplicit(Double netInterestImplicit) {
        this.netInterestImplicit = netInterestImplicit;
    }

    public Double getVatInterestImplicit() {
        return vatInterestImplicit;
    }

    public void setVatInterestImplicit(Double vatInterestImplicit) {
        this.vatInterestImplicit = vatInterestImplicit;
    }

    public Double getOutstandingRental() {
        return outstandingRental;
    }

    public void setOutstandingRental(Double outstandingRental) {
        this.outstandingRental = outstandingRental;
    }

    public Double getOutstandingPrincipal() {
        return outstandingPrincipal;
    }

    public void setOutstandingPrincipal(Double outstandingPrincipal) {
        this.outstandingPrincipal = outstandingPrincipal;
    }

    public Double getOutstandingInterest() {
        return outstandingInterest;
    }

    public void setOutstandingInterest(Double outstandingInterest) {
        this.outstandingInterest = outstandingInterest;
    }

    public Double getInterestPeriodDays() {
        return interestPeriodDays;
    }

    public void setInterestPeriodDays(Double interestPeriodDays) {
        this.interestPeriodDays = interestPeriodDays;
    }
}
