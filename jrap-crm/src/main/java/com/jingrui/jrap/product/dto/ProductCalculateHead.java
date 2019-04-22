/*
 * *
 *  @file com.maddyhome.idea.copyright.pattern.JavaCopyrightVariablesProvider$1@742f39db$
 *  @CopyRight (C) 2018 ZheJiangJingRui Co. Ltd.
 *  @brief JingRui Application Platform
 *  @author $name$
 *  @email yulong.yuan@jr-info.cn
 *  @date $date$
 * /
 */

package com.jingrui.jrap.product.dto;

import org.hibernate.validator.constraints.Length;

import java.util.Date;

public class ProductCalculateHead {
    private Double leaseAmount; //租赁物价款

    private Double financeAmount; //融资金额

    private Double contractAmount; //合同总额（首付+各项费用+租金+尾款+留购价款)

    private Double netFinanceAmount; //不含税融资额

    private Double vatFinanceAmount; //融资额增值税

    private Double downPayment; //首付款

    private Double netDownPayment; //不含税首付金额

    private Double vatDownPayment; //首付款税额

    private Double finalPayment; //尾款

    private Double residualValue; //留购价款

    private Double vatInput; //进项增值税额

    private Double leaseTimes; //租赁期数

    @Length(max = 1)
    private String payType; //支付类型（1/先付 0/后付）

    private int payTimes; //支付期数

    private int annualPayTimes; //支付频率（年）

    private int leaseTerm; //租赁期限

    private Double baseRate; //基准利率

    private Double intRate; //租赁利率

    private Double intRateImplicit; //租赁利率（实际）

    private Double intRateReal; //税后实际利率

    @Length(max = 60)
    private String intRateType; //利率类型：浮动/FLOATING 固定/FIXED

    private Double platePrice; //牌照费

    private Double insuranceAmount; //保险费

    private Double mortgageFee; //抵押费

    private Double charge; //手续费

    private Double gpsFee; //GPS费

    private Double parkingFee; //停车费

    private Double propertyFee; //产调费

    private Double documentFee; //查档费

    private Double purchaseTax; //购置税

    private Double notarialFee; //公证费

    private Double creditFee; //征信费

    private Double licenseFee; //上牌费

    private Double otherFee01; //其他费用01

    private Double otherFee02; //其他费用02

    private Double otherFee03; //其他费用03

    private Double otherFee04; //其他费用04

    private Double otherFee05; //其他费用05

    private Double totalFee; //总费用

    private Double netTotalFee; //不含税费用金额

    private Double vatTotalFee; //费用增值税额

    private Double violationDeposit; //违章押金

    private Double insuranceDeposit; //保险押金

    private Double annualSurveyDeposit; //年检押金

    private Double otherDeposit01; //其他押金01

    private Double otherDeposit02; //其他押金02

    private Double otherDeposit03; //其他押金03

    private Double otherDeposit04; //其他押金04

    private Double otherDeposit05; //其他押金05

    private Double totalDeposit; //总押金

    private Double totalRental; //总租金

    private Double netTotalRental; //不含税租金

    private Double vatTotalRental; //租金增值税额

    private Double totalInterest; //利息总额

    private Double netTotalInterest; //不含税利息

    private Double vatTotalInterest; //利息增值税额

    private Double financeIncome; //租赁收入

    private Double netFinanceIncome; //不含税租赁收入

    private Double vatFinanceIncome; //租赁收入税额

    private Double receiveRental; //已收租金

    private Double receivePrincipal; //已收本金

    private Double receiveInterest; //已收利息

    private Double depositBalance; //押金余额

    private int intRatePrecision; //利率精度

    private Double irr; //内部收益率

    private Double irrAfterTax; //税后内部收益率

    private Double pmt; //等额本息租金

    private Double pmtFirst; //等额本息租金（首期）

    @Length(max = 100)
    private String calcPrompt; //租金计算结果提示

    @Length(max = 500)
    private String calcPromptMsg; //租金计算结果提示信息

    public Double getLeaseAmount() {
        return leaseAmount;
    }

    public void setLeaseAmount(Double leaseAmount) {
        this.leaseAmount = leaseAmount;
    }

    public Double getFinanceAmount() {
        return financeAmount;
    }

    public void setFinanceAmount(Double financeAmount) {
        this.financeAmount = financeAmount;
    }

    public Double getContractAmount() {
        return contractAmount;
    }

    public void setContractAmount(Double contractAmount) {
        this.contractAmount = contractAmount;
    }

    public Double getNetFinanceAmount() {
        return netFinanceAmount;
    }

    public void setNetFinanceAmount(Double netFinanceAmount) {
        this.netFinanceAmount = netFinanceAmount;
    }

    public Double getVatFinanceAmount() {
        return vatFinanceAmount;
    }

    public void setVatFinanceAmount(Double vatFinanceAmount) {
        this.vatFinanceAmount = vatFinanceAmount;
    }

    public Double getDownPayment() {
        return downPayment;
    }

    public void setDownPayment(Double downPayment) {
        this.downPayment = downPayment;
    }

    public Double getNetDownPayment() {
        return netDownPayment;
    }

    public void setNetDownPayment(Double netDownPayment) {
        this.netDownPayment = netDownPayment;
    }

    public Double getVatDownPayment() {
        return vatDownPayment;
    }

    public void setVatDownPayment(Double vatDownPayment) {
        this.vatDownPayment = vatDownPayment;
    }

    public Double getFinalPayment() {
        return finalPayment;
    }

    public void setFinalPayment(Double finalPayment) {
        this.finalPayment = finalPayment;
    }

    public Double getResidualValue() {
        return residualValue;
    }

    public void setResidualValue(Double residualValue) {
        this.residualValue = residualValue;
    }

    public Double getVatInput() {
        return vatInput;
    }

    public void setVatInput(Double vatInput) {
        this.vatInput = vatInput;
    }

    public Double getLeaseTimes() {
        return leaseTimes;
    }

    public void setLeaseTimes(Double leaseTimes) {
        this.leaseTimes = leaseTimes;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public int getPayTimes() {
        return payTimes;
    }

    public void setPayTimes(int payTimes) {
        this.payTimes = payTimes;
    }

    public int getAnnualPayTimes() {
        return annualPayTimes;
    }

    public void setAnnualPayTimes(int annualPayTimes) {
        this.annualPayTimes = annualPayTimes;
    }

    public int getLeaseTerm() {
        return leaseTerm;
    }

    public void setLeaseTerm(int leaseTerm) {
        this.leaseTerm = leaseTerm;
    }

    public Double getBaseRate() {
        return baseRate;
    }

    public void setBaseRate(Double baseRate) {
        this.baseRate = baseRate;
    }

    public Double getIntRate() {
        return intRate;
    }

    public void setIntRate(Double intRate) {
        this.intRate = intRate;
    }

    public Double getIntRateImplicit() {
        return intRateImplicit;
    }

    public void setIntRateImplicit(Double intRateImplicit) {
        this.intRateImplicit = intRateImplicit;
    }

    public Double getIntRateReal() {
        return intRateReal;
    }

    public void setIntRateReal(Double intRateReal) {
        this.intRateReal = intRateReal;
    }

    public String getIntRateType() {
        return intRateType;
    }

    public void setIntRateType(String intRateType) {
        this.intRateType = intRateType;
    }

    public Double getPlatePrice() {
        return platePrice;
    }

    public void setPlatePrice(Double platePrice) {
        this.platePrice = platePrice;
    }

    public Double getInsuranceAmount() {
        return insuranceAmount;
    }

    public void setInsuranceAmount(Double insuranceAmount) {
        this.insuranceAmount = insuranceAmount;
    }

    public Double getMortgageFee() {
        return mortgageFee;
    }

    public void setMortgageFee(Double mortgageFee) {
        this.mortgageFee = mortgageFee;
    }

    public Double getCharge() {
        return charge;
    }

    public void setCharge(Double charge) {
        this.charge = charge;
    }

    public Double getGpsFee() {
        return gpsFee;
    }

    public void setGpsFee(Double gpsFee) {
        this.gpsFee = gpsFee;
    }

    public Double getParkingFee() {
        return parkingFee;
    }

    public void setParkingFee(Double parkingFee) {
        this.parkingFee = parkingFee;
    }

    public Double getPropertyFee() {
        return propertyFee;
    }

    public void setPropertyFee(Double propertyFee) {
        this.propertyFee = propertyFee;
    }

    public Double getDocumentFee() {
        return documentFee;
    }

    public void setDocumentFee(Double documentFee) {
        this.documentFee = documentFee;
    }

    public Double getPurchaseTax() {
        return purchaseTax;
    }

    public void setPurchaseTax(Double purchaseTax) {
        this.purchaseTax = purchaseTax;
    }

    public Double getNotarialFee() {
        return notarialFee;
    }

    public void setNotarialFee(Double notarialFee) {
        this.notarialFee = notarialFee;
    }

    public Double getCreditFee() {
        return creditFee;
    }

    public void setCreditFee(Double creditFee) {
        this.creditFee = creditFee;
    }

    public Double getLicenseFee() {
        return licenseFee;
    }

    public void setLicenseFee(Double licenseFee) {
        this.licenseFee = licenseFee;
    }

    public Double getOtherFee01() {
        return otherFee01;
    }

    public void setOtherFee01(Double otherFee01) {
        this.otherFee01 = otherFee01;
    }

    public Double getOtherFee02() {
        return otherFee02;
    }

    public void setOtherFee02(Double otherFee02) {
        this.otherFee02 = otherFee02;
    }

    public Double getOtherFee03() {
        return otherFee03;
    }

    public void setOtherFee03(Double otherFee03) {
        this.otherFee03 = otherFee03;
    }

    public Double getOtherFee04() {
        return otherFee04;
    }

    public void setOtherFee04(Double otherFee04) {
        this.otherFee04 = otherFee04;
    }

    public Double getOtherFee05() {
        return otherFee05;
    }

    public void setOtherFee05(Double otherFee05) {
        this.otherFee05 = otherFee05;
    }

    public Double getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Double totalFee) {
        this.totalFee = totalFee;
    }

    public Double getNetTotalFee() {
        return netTotalFee;
    }

    public void setNetTotalFee(Double netTotalFee) {
        this.netTotalFee = netTotalFee;
    }

    public Double getVatTotalFee() {
        return vatTotalFee;
    }

    public void setVatTotalFee(Double vatTotalFee) {
        this.vatTotalFee = vatTotalFee;
    }

    public Double getViolationDeposit() {
        return violationDeposit;
    }

    public void setViolationDeposit(Double violationDeposit) {
        this.violationDeposit = violationDeposit;
    }

    public Double getInsuranceDeposit() {
        return insuranceDeposit;
    }

    public void setInsuranceDeposit(Double insuranceDeposit) {
        this.insuranceDeposit = insuranceDeposit;
    }

    public Double getAnnualSurveyDeposit() {
        return annualSurveyDeposit;
    }

    public void setAnnualSurveyDeposit(Double annualSurveyDeposit) {
        this.annualSurveyDeposit = annualSurveyDeposit;
    }

    public Double getOtherDeposit01() {
        return otherDeposit01;
    }

    public void setOtherDeposit01(Double otherDeposit01) {
        this.otherDeposit01 = otherDeposit01;
    }

    public Double getOtherDeposit02() {
        return otherDeposit02;
    }

    public void setOtherDeposit02(Double otherDeposit02) {
        this.otherDeposit02 = otherDeposit02;
    }

    public Double getOtherDeposit03() {
        return otherDeposit03;
    }

    public void setOtherDeposit03(Double otherDeposit03) {
        this.otherDeposit03 = otherDeposit03;
    }

    public Double getOtherDeposit04() {
        return otherDeposit04;
    }

    public void setOtherDeposit04(Double otherDeposit04) {
        this.otherDeposit04 = otherDeposit04;
    }

    public Double getOtherDeposit05() {
        return otherDeposit05;
    }

    public void setOtherDeposit05(Double otherDeposit05) {
        this.otherDeposit05 = otherDeposit05;
    }

    public Double getTotalDeposit() {
        return totalDeposit;
    }

    public void setTotalDeposit(Double totalDeposit) {
        this.totalDeposit = totalDeposit;
    }

    public Double getTotalRental() {
        return totalRental;
    }

    public void setTotalRental(Double totalRental) {
        this.totalRental = totalRental;
    }

    public Double getNetTotalRental() {
        return netTotalRental;
    }

    public void setNetTotalRental(Double netTotalRental) {
        this.netTotalRental = netTotalRental;
    }

    public Double getVatTotalRental() {
        return vatTotalRental;
    }

    public void setVatTotalRental(Double vatTotalRental) {
        this.vatTotalRental = vatTotalRental;
    }

    public Double getTotalInterest() {
        return totalInterest;
    }

    public void setTotalInterest(Double totalInterest) {
        this.totalInterest = totalInterest;
    }

    public Double getNetTotalInterest() {
        return netTotalInterest;
    }

    public void setNetTotalInterest(Double netTotalInterest) {
        this.netTotalInterest = netTotalInterest;
    }

    public Double getVatTotalInterest() {
        return vatTotalInterest;
    }

    public void setVatTotalInterest(Double vatTotalInterest) {
        this.vatTotalInterest = vatTotalInterest;
    }

    public Double getFinanceIncome() {
        return financeIncome;
    }

    public void setFinanceIncome(Double financeIncome) {
        this.financeIncome = financeIncome;
    }

    public Double getNetFinanceIncome() {
        return netFinanceIncome;
    }

    public void setNetFinanceIncome(Double netFinanceIncome) {
        this.netFinanceIncome = netFinanceIncome;
    }

    public Double getVatFinanceIncome() {
        return vatFinanceIncome;
    }

    public void setVatFinanceIncome(Double vatFinanceIncome) {
        this.vatFinanceIncome = vatFinanceIncome;
    }

    public Double getReceiveRental() {
        return receiveRental;
    }

    public void setReceiveRental(Double receiveRental) {
        this.receiveRental = receiveRental;
    }

    public Double getReceivePrincipal() {
        return receivePrincipal;
    }

    public void setReceivePrincipal(Double receivePrincipal) {
        this.receivePrincipal = receivePrincipal;
    }

    public Double getReceiveInterest() {
        return receiveInterest;
    }

    public void setReceiveInterest(Double receiveInterest) {
        this.receiveInterest = receiveInterest;
    }

    public Double getDepositBalance() {
        return depositBalance;
    }

    public void setDepositBalance(Double depositBalance) {
        this.depositBalance = depositBalance;
    }

    public int getIntRatePrecision() {
        return intRatePrecision;
    }

    public void setIntRatePrecision(int intRatePrecision) {
        this.intRatePrecision = intRatePrecision;
    }

    public Double getIrr() {
        return irr;
    }

    public void setIrr(Double irr) {
        this.irr = irr;
    }

    public Double getIrrAfterTax() {
        return irrAfterTax;
    }

    public void setIrrAfterTax(Double irrAfterTax) {
        this.irrAfterTax = irrAfterTax;
    }

    public Double getPmt() {
        return pmt;
    }

    public void setPmt(Double pmt) {
        this.pmt = pmt;
    }

    public Double getPmtFirst() {
        return pmtFirst;
    }

    public void setPmtFirst(Double pmtFirst) {
        this.pmtFirst = pmtFirst;
    }

    public String getCalcPrompt() {
        return calcPrompt;
    }

    public void setCalcPrompt(String calcPrompt) {
        this.calcPrompt = calcPrompt;
    }

    public String getCalcPromptMsg() {
        return calcPromptMsg;
    }

    public void setCalcPromptMsg(String calcPromptMsg) {
        this.calcPromptMsg = calcPromptMsg;
    }
}
