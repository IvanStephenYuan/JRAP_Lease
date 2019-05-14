/*
 * *
 *  @file com.maddyhome.idea.copyright.pattern.JavaCopyrightVariablesProvider$1@7033af9c$
 *  @CopyRight (C) 2018 ZheJiangJingRui Co. Ltd.
 *  @brief JingRui Application Platform
 *  @author $name$
 *  @email yulong.yuan@jr-info.cn
 *  @date $date$
 * /
 */

package com.jingrui.jrap.customer.entity;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 鹏元-多头借贷报告
 */
public class LoanReport {
    private String batchNo;
    private Date buildEndTime;  //报告生成结束时间
    private String isFrozen;
    private String hasSystemError;
    private String queryReasonID;
    private String refID;
    private String reportID;
    private String subReportTypes;
    private String subReportTypesShortCaption;
    private String treatResult;

    //多头借贷雷达监控
    private Date creditReportDate;  //信息获取日期

    //多头借贷雷达监控—申请监测
    private int queryOrgCnt;        //查询机构总数
    private int queryNetLoanOrgCnt; //查询网络贷款类机构数
    private int queryFinclOrgCnt; //查询消费金融类机构数
    private int queryOtherOrgCnt; //查询其他机构数
    private int totalQueryCnt; //近12个月总查询笔数
    private Date latestQueryTime; //最近一次查询时间
    private int last1MthsQueryCnt; //近1个月贷款机构查询笔数
    private int last3MthsQueryCnt; //近3个月贷款机构查询笔数
    private int last6MthsQueryCnt; //近6个月贷款机构查询笔数

    //多头借贷雷达监控—行为监测
    private int loanOrderCnt; //查询机构总数
    private int loanClosedCnt; //贷款已结清笔数
    private int loanOverdueCnt; //贷款逾期笔数
    private int loanOrgCnt; //贷款机构总数
    private int loanNetLoanOrgCnt; //网络贷款类机构数
    private int loanFinclOrgCnt; //消费金融类机构数
    private int last1MthsLoanCnt; //近1个月贷款放款笔数
    private int last3MthsLoanCnt; //近3个月贷款放款笔数
    private int last6MthsLoanCnt; //近6个月贷款放款笔数
    private String creditLoanDur; //信用贷款时长
    private Date latestLoanTime; //最近一次贷款时间

    //多头借贷雷达监控—信用现状
    private int netLoanOrgCnt; //网络贷款类机构数
    private double netLoanOrgMaxCredits; //网络贷款机构最大授信额度
    private double netLoanOrgAvgCredits; //网络贷款机构平均授信额度
    private int inUseFinclOrgCnt; //消费金融类机构数
    private double finclOrgMaxCredits; //消费金融类机构最大授信额度
    private double finclOrgAvgCredits; //消费金融类机构平均授信额度

    //多头借贷逾期记录
    private Date overdueReportDate;//信息获取日期
    //多头借贷逾期记录—逾期汇总
    private int orgCount; //查询机构总数
    private int orderCount; //逾期订单数
    private double debtAmount; //负债金额

    //多头借贷逾期记录—逾期详情
    List<LoanOverdueDetail> overdueDetails;

    public LoanReport() {

    }

    public LoanReport(JSONObject returnValue) throws ParseException {
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

        //头信息
        this.batchNo = returnValue.getString("batNo");
        JSONArray loanReports = returnValue.getJSONArray("cisReport");

        for (int k = 0; k < loanReports.size(); k++) {
            JSONObject loanReport = loanReports.getJSONObject(k);

            this.buildEndTime = dateTimeFormat.parse(loanReport.getString("buildEndTime"));
            this.isFrozen = loanReport.getString("isFrozen");
            this.hasSystemError = loanReport.getString("hasSystemError");
            this.queryReasonID = loanReport.getString("queryReasonID");
            this.refID = loanReport.getString("refID");
            this.reportID = loanReport.getString("reportID");
            this.subReportTypes = loanReport.getString("subReportTypes");
            this.reportID = loanReport.getString("reportID");
            this.subReportTypesShortCaption = loanReport.getString("subReportTypesShortCaption");
            this.treatResult = loanReport.getString("treatResult");

            if (!"true".equalsIgnoreCase(hasSystemError)) {
                JSONObject creditReportInfo = loanReport.getJSONObject("creditReportInfo");
                if (!creditReportInfo.isEmpty()) {
                    this.creditReportDate = dateFormat.parse(creditReportInfo.getString("infoDate"));

                    JSONObject applyMonitorInfo = creditReportInfo.getJSONObject("applyMonitorInfo");
                    if (applyMonitorInfo != null && !applyMonitorInfo.isEmpty()) {
                        this.queryOrgCnt = applyMonitorInfo.getIntValue("queryOrgCnt");
                        this.queryNetLoanOrgCnt = applyMonitorInfo.getIntValue("queryNetLoanOrgCnt");
                        this.queryFinclOrgCnt = applyMonitorInfo.getIntValue("queryFinclOrgCnt");
                        this.queryOtherOrgCnt = applyMonitorInfo.getIntValue("queryOtherOrgCnt");
                        this.totalQueryCnt = applyMonitorInfo.getIntValue("totalQueryCnt");
                        if (applyMonitorInfo.getString("latestQueryTime") != null && !"".equalsIgnoreCase(applyMonitorInfo.getString("latestQueryTime"))) {
                            this.latestQueryTime = dateFormat.parse(applyMonitorInfo.getString("latestQueryTime"));
                        }
                        this.last1MthsQueryCnt = applyMonitorInfo.getIntValue("last1MthsQueryCnt");
                        this.last3MthsQueryCnt = applyMonitorInfo.getIntValue("last3MthsQueryCnt");
                        this.last6MthsQueryCnt = applyMonitorInfo.getIntValue("last6MthsQueryCnt");
                    }

                    JSONObject behaviorMonitorInfo = creditReportInfo.getJSONObject("behaviorMonitorInfo");
                    if (behaviorMonitorInfo != null && !behaviorMonitorInfo.isEmpty()) {
                        this.loanOrderCnt = behaviorMonitorInfo.getIntValue("loanOrderCnt");
                        this.loanClosedCnt = behaviorMonitorInfo.getIntValue("loanClosedCnt");
                        this.loanOverdueCnt = behaviorMonitorInfo.getIntValue("loanOverdueCnt");
                        this.loanOrgCnt = behaviorMonitorInfo.getIntValue("loanOrgCnt");
                        this.loanNetLoanOrgCnt = behaviorMonitorInfo.getIntValue("loanNetLoanOrgCnt");
                        this.loanFinclOrgCnt = behaviorMonitorInfo.getIntValue("loanFinclOrgCnt");
                        this.last1MthsLoanCnt = behaviorMonitorInfo.getIntValue("last1MthsLoanCnt");
                        this.last3MthsLoanCnt = behaviorMonitorInfo.getIntValue("last3MthsLoanCnt");
                        this.last6MthsLoanCnt = behaviorMonitorInfo.getIntValue("last6MthsLoanCnt");
                        this.creditLoanDur = behaviorMonitorInfo.getString("creditLoanDur");
                        if (behaviorMonitorInfo.getString("latestLoanTime") != null && !"".equalsIgnoreCase(behaviorMonitorInfo.getString("latestLoanTime"))) {
                            this.latestLoanTime = dateFormat.parse(behaviorMonitorInfo.getString("latestLoanTime"));
                        }
                    }

                    JSONObject creditStatusInfo = creditReportInfo.getJSONObject("creditStatusInfo");
                    if (creditStatusInfo != null && !creditStatusInfo.isEmpty()) {
                        this.netLoanOrgCnt = creditStatusInfo.getIntValue("netLoanOrgCnt");
                        this.netLoanOrgMaxCredits = creditStatusInfo.getDoubleValue("netLoanOrgMaxCredits");
                        this.netLoanOrgAvgCredits = creditStatusInfo.getDoubleValue("netLoanOrgAvgCredits");
                        this.inUseFinclOrgCnt = creditStatusInfo.getIntValue("inUseFinclOrgCnt");
                        this.finclOrgMaxCredits = creditStatusInfo.getDoubleValue("finclOrgMaxCredits");
                        this.finclOrgAvgCredits = creditStatusInfo.getDoubleValue("finclOrgAvgCredits");
                    }
                }

                JSONObject overdueReportInfo = loanReport.getJSONObject("overdueReportInfo");
                if (overdueReportInfo != null && !overdueReportInfo.isEmpty()) {
                    this.overdueReportDate = dateFormat.parse(overdueReportInfo.getString("infoDate"));

                    JSONObject overdueStat = overdueReportInfo.getJSONObject("overdueStat");
                    if (overdueStat != null && !overdueStat.isEmpty()) {
                        this.orgCount = overdueStat.getIntValue("orgCount");
                        this.orderCount = overdueStat.getIntValue("orderCount");
                        this.debtAmount = overdueStat.getDoubleValue("debtAmount");
                    }

                    JSONArray overdueDetails = overdueReportInfo.getJSONArray("overdueDetails");
                    this.overdueDetails = new ArrayList<LoanOverdueDetail>();
                    for (int i = 0; i < overdueDetails.size(); i++) {
                        JSONObject record = overdueDetails.getJSONObject(i);
                        LoanOverdueDetail overdueDetail = new LoanOverdueDetail();
                        overdueDetail.setOverdueDate(record.getString("overdueDate"));
                        overdueDetail.setAmount(record.getString("overdueAmount"));
                        overdueDetail.setOverdueDays(record.getString("overdueDays"));
                        overdueDetail.setSettlement(record.getString("settlement"));

                        this.overdueDetails.add(overdueDetail);
                    }
                }
            }
        }
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public Date getBuildEndTime() {
        return buildEndTime;
    }

    public void setBuildEndTime(Date buildEndTime) {
        this.buildEndTime = buildEndTime;
    }

    public String getIsFrozen() {
        return isFrozen;
    }

    public void setIsFrozen(String isFrozen) {
        this.isFrozen = isFrozen;
    }

    public String getHasSystemError() {
        return hasSystemError;
    }

    public void setHasSystemError(String hasSystemError) {
        this.hasSystemError = hasSystemError;
    }

    public String getQueryReasonID() {
        return queryReasonID;
    }

    public void setQueryReasonID(String queryReasonID) {
        this.queryReasonID = queryReasonID;
    }

    public String getRefID() {
        return refID;
    }

    public void setRefID(String refID) {
        this.refID = refID;
    }

    public String getReportID() {
        return reportID;
    }

    public void setReportID(String reportID) {
        this.reportID = reportID;
    }

    public String getSubReportTypes() {
        return subReportTypes;
    }

    public void setSubReportTypes(String subReportTypes) {
        this.subReportTypes = subReportTypes;
    }

    public String getSubReportTypesShortCaption() {
        return subReportTypesShortCaption;
    }

    public void setSubReportTypesShortCaption(String subReportTypesShortCaption) {
        this.subReportTypesShortCaption = subReportTypesShortCaption;
    }

    public String getTreatResult() {
        return treatResult;
    }

    public void setTreatResult(String treatResult) {
        this.treatResult = treatResult;
    }

    public Date getCreditReportDate() {
        return creditReportDate;
    }

    public void setCreditReportDate(Date creditReportDate) {
        this.creditReportDate = creditReportDate;
    }

    public int getQueryOrgCnt() {
        return queryOrgCnt;
    }

    public void setQueryOrgCnt(int queryOrgCnt) {
        this.queryOrgCnt = queryOrgCnt;
    }

    public int getQueryNetLoanOrgCnt() {
        return queryNetLoanOrgCnt;
    }

    public void setQueryNetLoanOrgCnt(int queryNetLoanOrgCnt) {
        this.queryNetLoanOrgCnt = queryNetLoanOrgCnt;
    }

    public int getQueryFinclOrgCnt() {
        return queryFinclOrgCnt;
    }

    public void setQueryFinclOrgCnt(int queryFinclOrgCnt) {
        this.queryFinclOrgCnt = queryFinclOrgCnt;
    }

    public int getQueryOtherOrgCnt() {
        return queryOtherOrgCnt;
    }

    public void setQueryOtherOrgCnt(int queryOtherOrgCnt) {
        this.queryOtherOrgCnt = queryOtherOrgCnt;
    }

    public int getTotalQueryCnt() {
        return totalQueryCnt;
    }

    public void setTotalQueryCnt(int totalQueryCnt) {
        this.totalQueryCnt = totalQueryCnt;
    }

    public Date getLatestQueryTime() {
        return latestQueryTime;
    }

    public void setLatestQueryTime(Date latestQueryTime) {
        this.latestQueryTime = latestQueryTime;
    }

    public int getLast1MthsQueryCnt() {
        return last1MthsQueryCnt;
    }

    public void setLast1MthsQueryCnt(int last1MthsQueryCnt) {
        this.last1MthsQueryCnt = last1MthsQueryCnt;
    }

    public int getLast3MthsQueryCnt() {
        return last3MthsQueryCnt;
    }

    public void setLast3MthsQueryCnt(int last3MthsQueryCnt) {
        this.last3MthsQueryCnt = last3MthsQueryCnt;
    }

    public int getLast6MthsQueryCnt() {
        return last6MthsQueryCnt;
    }

    public void setLast6MthsQueryCnt(int last6MthsQueryCnt) {
        this.last6MthsQueryCnt = last6MthsQueryCnt;
    }

    public int getLoanOrderCnt() {
        return loanOrderCnt;
    }

    public void setLoanOrderCnt(int loanOrderCnt) {
        this.loanOrderCnt = loanOrderCnt;
    }

    public int getLoanClosedCnt() {
        return loanClosedCnt;
    }

    public void setLoanClosedCnt(int loanClosedCnt) {
        this.loanClosedCnt = loanClosedCnt;
    }

    public int getLoanOverdueCnt() {
        return loanOverdueCnt;
    }

    public void setLoanOverdueCnt(int loanOverdueCnt) {
        this.loanOverdueCnt = loanOverdueCnt;
    }

    public int getLoanOrgCnt() {
        return loanOrgCnt;
    }

    public void setLoanOrgCnt(int loanOrgCnt) {
        this.loanOrgCnt = loanOrgCnt;
    }

    public int getLoanNetLoanOrgCnt() {
        return loanNetLoanOrgCnt;
    }

    public void setLoanNetLoanOrgCnt(int loanNetLoanOrgCnt) {
        this.loanNetLoanOrgCnt = loanNetLoanOrgCnt;
    }

    public int getLoanFinclOrgCnt() {
        return loanFinclOrgCnt;
    }

    public void setLoanFinclOrgCnt(int loanFinclOrgCnt) {
        this.loanFinclOrgCnt = loanFinclOrgCnt;
    }

    public int getLast1MthsLoanCnt() {
        return last1MthsLoanCnt;
    }

    public void setLast1MthsLoanCnt(int last1MthsLoanCnt) {
        this.last1MthsLoanCnt = last1MthsLoanCnt;
    }

    public int getLast3MthsLoanCnt() {
        return last3MthsLoanCnt;
    }

    public void setLast3MthsLoanCnt(int last3MthsLoanCnt) {
        this.last3MthsLoanCnt = last3MthsLoanCnt;
    }

    public int getLast6MthsLoanCnt() {
        return last6MthsLoanCnt;
    }

    public void setLast6MthsLoanCnt(int last6MthsLoanCnt) {
        this.last6MthsLoanCnt = last6MthsLoanCnt;
    }

    public String getCreditLoanDur() {
        return creditLoanDur;
    }

    public void setCreditLoanDur(String creditLoanDur) {
        this.creditLoanDur = creditLoanDur;
    }

    public Date getLatestLoanTime() {
        return latestLoanTime;
    }

    public void setLatestLoanTime(Date latestLoanTime) {
        this.latestLoanTime = latestLoanTime;
    }

    public int getNetLoanOrgCnt() {
        return netLoanOrgCnt;
    }

    public void setNetLoanOrgCnt(int netLoanOrgCnt) {
        this.netLoanOrgCnt = netLoanOrgCnt;
    }

    public double getNetLoanOrgMaxCredits() {
        return netLoanOrgMaxCredits;
    }

    public void setNetLoanOrgMaxCredits(double netLoanOrgMaxCredits) {
        this.netLoanOrgMaxCredits = netLoanOrgMaxCredits;
    }

    public double getNetLoanOrgAvgCredits() {
        return netLoanOrgAvgCredits;
    }

    public void setNetLoanOrgAvgCredits(double netLoanOrgAvgCredits) {
        this.netLoanOrgAvgCredits = netLoanOrgAvgCredits;
    }

    public int getInUseFinclOrgCnt() {
        return inUseFinclOrgCnt;
    }

    public void setInUseFinclOrgCnt(int inUseFinclOrgCnt) {
        this.inUseFinclOrgCnt = inUseFinclOrgCnt;
    }

    public double getFinclOrgMaxCredits() {
        return finclOrgMaxCredits;
    }

    public void setFinclOrgMaxCredits(double finclOrgMaxCredits) {
        this.finclOrgMaxCredits = finclOrgMaxCredits;
    }

    public double getFinclOrgAvgCredits() {
        return finclOrgAvgCredits;
    }

    public void setFinclOrgAvgCredits(double finclOrgAvgCredits) {
        this.finclOrgAvgCredits = finclOrgAvgCredits;
    }

    public Date getOverdueReportDate() {
        return overdueReportDate;
    }

    public void setOverdueReportDate(Date overdueReportDate) {
        this.overdueReportDate = overdueReportDate;
    }

    public int getOrgCount() {
        return orgCount;
    }

    public void setOrgCount(int orgCount) {
        this.orgCount = orgCount;
    }

    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }

    public double getDebtAmount() {
        return debtAmount;
    }

    public void setDebtAmount(double debtAmount) {
        this.debtAmount = debtAmount;
    }

    public List<LoanOverdueDetail> getOverdueDetails() {
        return overdueDetails;
    }

    public void setOverdueDetails(List<LoanOverdueDetail> overdueDetails) {
        this.overdueDetails = overdueDetails;
    }
}
