/*
 * *
 *  @file com.maddyhome.idea.copyright.pattern.JavaCopyrightVariablesProvider$1@6e1cea9d$
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
 * 鹏元-反欺诈报告
 */
public class AntiFraudReport {
    private String batchNo;
    private Date buildEndTime;
    private String isFrozen;
    private String hasSystemError;
    private String queryReasonID;
    private String refID;
    private String reportID;
    private String subReportTypes;
    private String subReportTypesShortCaption;
    private String treatResult;

    private String personAntiSpoofingDesc;  //个人反欺诈综述信息
    private double riskScore;  //风险评分分数
    private String suggest;  //风险建议，取值：建议拒绝，建议复核，建议通过
    private String riskLevel; //风险等级，取值：高，中度，低
    private String hitTypes; //命中类型
    private String econnoisserurState;   //是否命中羊毛党名单
    private String fraudRiskState;   //是否命中欺诈风险名单

    //信贷行为信息
    private int last1MthsLoanCnt;  //近1个月贷款放款笔数
    private int last3MthsLoanCnt;  //近3个月贷款放款笔数
    private int last6MthsLoanCnt;  //近6个月贷款放款笔数
    private int last12MthsLoanCnt; //近12个月贷款放款笔数
    private int loanOrgCnt;  //贷款机构总数
    private double avgCredits; //平均放款额度
    private int loanOrderCnt; //贷款总笔数
    private int loanClosedCnt; //已结清笔数
    private int loanNoClosedCnt; //未结清笔数
    private int undefinedCnt; // 未知笔数

    //个人反欺诈信贷逾期名单
    private double overdueAmount;  //逾期总金额
    private int overdueTotal;      //逾期总笔数
    private int overdueNoClosedCnt; //未结清的逾期笔数
    //逾期明细
    private List<OverdueDetail> overdueDetails;

    //个人反欺诈个人风险信息
    private int totleCount; //风险概述总条数
    private int alCount; //司法案例信息条数
    private int zxCount; //司法执行信息条数
    private int sxCount; //司法失信信息条数
    private int swCount; //税务行政执法信息条数
    private int cqggCount; //催欠公告信息条数
    private int wdyqCount; //网贷逾期信息条数
    //司法案例概要信息
    private List<AlsDetail> alsDetails;
    //司法执行概要信息
    private List<ZxsDetail> zxsDetails;
    //司法失信概要信息
    private List<SxsDetail> sxsDetails;
    //税务行政执法概要信息
    private List<SwsDetail> swsDetails;
    //催欠公告概要信息
    private List<CqsDetail> cqsDetails;
    //网贷逾期概要信息
    private List<WdyqsDetail> wdyqsDetails;

    //个人反欺诈近两年历史查询记录
    //疑似多头记录
    private int appplyCnt;  //多头申请总次数
    private int applyNetLoanCnt; //网络贷款类机构申请次数
    private int applyFinclCnt; //消费金融类机构申请次数
    //汇总类型查询记录
    private int last1Month; //近1个月各单位类型查询记录总数
    private int last3Month;
    private int last6Month;
    private int last12Month;
    private int last18Month;
    private int last24Month;
    //各单位类型查询记录
    private List<HistoryQueryItem> historyQueryItems;

    public AntiFraudReport() {

    }

    public AntiFraudReport(JSONObject returnValue) throws ParseException {
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        //头信息
        this.batchNo = returnValue.getString("batNo");
        JSONArray cisReports = returnValue.getJSONArray("cisReport");

        for (int k = 0; k < cisReports.size(); k++) {
            JSONObject cisReport = cisReports.getJSONObject(k);

            this.buildEndTime = dateTimeFormat.parse(cisReport.getString("buildEndTime"));
            this.isFrozen = cisReport.getString("isFrozen");
            this.hasSystemError = cisReport.getString("hasSystemError");
            this.queryReasonID = cisReport.getString("queryReasonID");
            this.refID = cisReport.getString("refID");
            this.reportID = cisReport.getString("reportID");
            this.subReportTypes = cisReport.getString("subReportTypes");
            this.reportID = cisReport.getString("reportID");
            this.subReportTypesShortCaption = cisReport.getString("subReportTypesShortCaption");
            this.treatResult = cisReport.getString("treatResult");


            if (!"true".equalsIgnoreCase(hasSystemError)) {
                JSONObject personAntiSpoofingDescInfo = cisReport.getJSONObject("personAntiSpoofingDescInfo");
                if (!personAntiSpoofingDescInfo.isEmpty()) {
                    this.personAntiSpoofingDesc = personAntiSpoofingDescInfo.getString("personAntiSpoofingDesc");
                }

                JSONObject personAntiSpoofingInfo = cisReport.getJSONObject("personAntiSpoofingInfo");
                if (!personAntiSpoofingInfo.isEmpty()) {
                    this.riskScore = personAntiSpoofingInfo.getDouble("riskScore");
                    this.suggest = personAntiSpoofingInfo.getString("suggest");
                    this.riskLevel = personAntiSpoofingInfo.getString("riskLevel");
                    this.hitTypes = personAntiSpoofingInfo.getString("hitTypes");
                }

                JSONObject econnoisserurInfo = cisReport.getJSONObject("econnoisserurInfo");
                if (!econnoisserurInfo.isEmpty()) {
                    this.econnoisserurState = econnoisserurInfo.getString("state");
                }

                JSONObject fraudRiskInfo = cisReport.getJSONObject("fraudRiskInfo");
                if (fraudRiskInfo != null && !fraudRiskInfo.isEmpty()) {
                    this.fraudRiskState = fraudRiskInfo.getString("state");
                }

                JSONObject creditBehaviorInfo = cisReport.getJSONObject("creditBehaviorInfo");
                if (creditBehaviorInfo != null && !creditBehaviorInfo.isEmpty()) {
                    this.last1MthsLoanCnt = creditBehaviorInfo.getIntValue("last1MthsLoanCnt");
                    this.last3MthsLoanCnt = creditBehaviorInfo.getIntValue("last3MthsLoanCnt");
                    this.last6MthsLoanCnt = creditBehaviorInfo.getIntValue("last6MthsLoanCnt");
                    this.last12MthsLoanCnt = creditBehaviorInfo.getIntValue("last12MthsLoanCnt");
                    this.loanOrgCnt = creditBehaviorInfo.getIntValue("loanOrgCnt");
                    this.avgCredits = creditBehaviorInfo.getDoubleValue("avgCredits");
                    this.loanOrderCnt = creditBehaviorInfo.getIntValue("loanOrderCnt");
                    this.loanClosedCnt = creditBehaviorInfo.getIntValue("loanClosedCnt");
                    this.loanNoClosedCnt = creditBehaviorInfo.getIntValue("loanNoClosedCnt");
                    this.undefinedCnt = creditBehaviorInfo.getIntValue("undefinedCnt");
                }


                JSONObject overdueStat = cisReport.getJSONObject("overdueStat");
                if (overdueStat != null && !overdueStat.isEmpty()) {
                    this.overdueAmount = overdueStat.getDoubleValue("overdueAmount");
                    this.overdueTotal = overdueStat.getIntValue("overdueTotal");
                    this.overdueNoClosedCnt = overdueStat.getIntValue("overdueNoClosedCnt");

                    JSONArray odDetails = cisReport.getJSONArray("overdueDetails");
                    this.overdueDetails = new ArrayList<OverdueDetail>();
                    for (int i = 0; i < odDetails.size(); i++) {
                        JSONObject record = odDetails.getJSONObject(i);
                        OverdueDetail overdueDetail = new OverdueDetail();
                        overdueDetail.setOverdueAmount(record.getDoubleValue("overdueAmount"));
                        overdueDetail.setOverdueDays(record.getString("overdueDays"));
                        overdueDetail.setSettlement(record.getString("settlement"));

                        this.overdueDetails.add(overdueDetail);
                    }
                }

                JSONObject personRiskInfo = cisReport.getJSONObject("personRiskInfo");
                if (personRiskInfo != null && !personRiskInfo.isEmpty()) {
                    JSONObject personRiskStat = personRiskInfo.getJSONObject("stat");
                    if (!personRiskStat.isEmpty()) {
                        this.totleCount = personRiskStat.getIntValue("totleCount");
                        this.alCount = personRiskStat.getIntValue("alCount");
                        this.cqggCount = personRiskStat.getIntValue("cqggCount");
                        this.swCount = personRiskStat.getIntValue("swCount");
                        this.sxCount = personRiskStat.getIntValue("sxCount");
                        this.wdyqCount = personRiskStat.getIntValue("wdyqCount");
                        this.zxCount = personRiskStat.getIntValue("zxCount");
                    }

                    JSONObject personRiskSummary = personRiskInfo.getJSONObject("summary");
                    this.alsDetails = new ArrayList<AlsDetail>();
                    if (personRiskSummary != null && !personRiskSummary.isEmpty()) {
                        JSONArray personRiskAls = personRiskSummary.getJSONArray("als");
                        for (int i = 0; i < personRiskAls.size(); i++) {
                            JSONObject record = personRiskAls.getJSONObject(i);
                            AlsDetail alsDetail = new AlsDetail();
                            alsDetail.setRecordId(record.getString("recordId"));
                            alsDetail.setAjlx(record.getString("ajlx"));
                            alsDetail.setBt(record.getString("bt"));
                            alsDetail.setDsrlx(record.getString("dsrlx"));
                            alsDetail.setSjnf(record.getString("sjnf"));
                            this.alsDetails.add(alsDetail);
                        }

                        JSONArray personRiskZxs = personRiskSummary.getJSONArray("zxs");
                        this.zxsDetails = new ArrayList<ZxsDetail>();
                        for (int i = 0; i < personRiskZxs.size(); i++) {
                            JSONObject record = personRiskZxs.getJSONObject(i);
                            ZxsDetail zxsDetail = new ZxsDetail();
                            zxsDetail.setRecordId(record.getString("recordId"));
                            zxsDetail.setBt(record.getString("bt"));
                            zxsDetail.setZxbd(record.getString("zxbd"));
                            zxsDetail.setLarq(dateFormat.parse(record.getString("larq")));
                            this.zxsDetails.add(zxsDetail);
                        }

                        JSONArray personRiskSxs = personRiskSummary.getJSONArray("sxs");
                        this.sxsDetails = new ArrayList<SxsDetail>();
                        for (int i = 0; i < personRiskSxs.size(); i++) {
                            JSONObject record = personRiskSxs.getJSONObject(i);
                            SxsDetail sxsDetail = new SxsDetail();
                            sxsDetail.setRecordId(record.getString("recordId"));
                            sxsDetail.setBt(record.getString("bt"));
                            sxsDetail.setLarq(dateFormat.parse(record.getString("larq")));
                            sxsDetail.setFbrq(dateFormat.parse(record.getString("fbrq")));
                            this.sxsDetails.add(sxsDetail);
                        }

                        JSONArray personRiskSws = personRiskSummary.getJSONArray("sws");
                        this.swsDetails = new ArrayList<SwsDetail>();
                        for (int i = 0; i < personRiskSws.size(); i++) {
                            JSONObject record = personRiskSws.getJSONObject(i);
                            SwsDetail swsDetail = new SwsDetail();
                            swsDetail.setRecordId(record.getString("recordId"));
                            swsDetail.setBt(record.getString("bt"));
                            swsDetail.setGgrq(dateFormat.parse(record.getString("ggrq")));
                            this.swsDetails.add(swsDetail);
                        }

                        JSONArray personRiskCqs = personRiskSummary.getJSONArray("cqs");
                        this.cqsDetails = new ArrayList<CqsDetail>();
                        for (int i = 0; i < personRiskCqs.size(); i++) {
                            JSONObject record = personRiskCqs.getJSONObject(i);
                            CqsDetail cqsDetail = new CqsDetail();
                            cqsDetail.setRecordId(record.getString("recordId"));
                            cqsDetail.setBt(record.getString("bt"));
                            cqsDetail.setFbrq(dateFormat.parse(record.getString("fbrq")));
                            this.cqsDetails.add(cqsDetail);
                        }

                        JSONArray personRiskWdyqs = personRiskSummary.getJSONArray("wdyqs");
                        this.wdyqsDetails = new ArrayList<WdyqsDetail>();
                        for (int i = 0; i < personRiskWdyqs.size(); i++) {
                            JSONObject record = personRiskWdyqs.getJSONObject(i);
                            WdyqsDetail wdyqsDetail = new WdyqsDetail();
                            wdyqsDetail.setRecordId(record.getString("recordId"));
                            wdyqsDetail.setBt(record.getString("bt"));
                            wdyqsDetail.setFbrq(dateFormat.parse(record.getString("fbrq")));
                            this.wdyqsDetails.add(wdyqsDetail);
                        }
                    }
                }

                JSONObject historySimpleQueryInfo = cisReport.getJSONObject("historySimpleQueryInfo");
                if (historySimpleQueryInfo != null && !historySimpleQueryInfo.isEmpty()) {
                    JSONObject suspectedBulllending = historySimpleQueryInfo.getJSONObject("suspectedBulllending");
                    if (!suspectedBulllending.isEmpty()) {
                        this.appplyCnt = suspectedBulllending.getIntValue("appplyCnt");
                        this.applyNetLoanCnt = suspectedBulllending.getIntValue("applyNetLoanCnt");
                        this.applyFinclCnt = suspectedBulllending.getIntValue("applyFinclCnt");
                    }

                    JSONObject historyQueryCount = historySimpleQueryInfo.getJSONObject("count");
                    if (!historyQueryCount.isEmpty()) {
                        this.last1Month = historyQueryCount.getIntValue("last1Month");
                        this.last3Month = historyQueryCount.getIntValue("last3Month");
                        this.last6Month = historyQueryCount.getIntValue("last6Month");
                        this.last12Month = historyQueryCount.getIntValue("last12Month");
                        this.last18Month = historyQueryCount.getIntValue("last18Month");
                        this.last24Month = historyQueryCount.getIntValue("last24Month");
                    }

                    JSONArray historyQueryItems = historySimpleQueryInfo.getJSONArray("items");
                    this.historyQueryItems = new ArrayList<HistoryQueryItem>();
                    for (int i = 0; i < historyQueryItems.size(); i++) {
                        JSONObject record = historyQueryItems.getJSONObject(i);
                        HistoryQueryItem historyQueryItem = new HistoryQueryItem();
                        historyQueryItem.setUnitMember(record.getString("unitMember"));
                        historyQueryItem.setLast1Month(record.getIntValue("last1Month"));
                        historyQueryItem.setLast3Month(record.getIntValue("last3Month"));
                        historyQueryItem.setLast6Month(record.getIntValue("last6Month"));
                        historyQueryItem.setLast12Month(record.getIntValue("last12Month"));
                        historyQueryItem.setLast18Month(record.getIntValue("last18Month"));
                        historyQueryItem.setLast24Month(record.getIntValue("last24Month"));
                        this.historyQueryItems.add(historyQueryItem);
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

    public String getPersonAntiSpoofingDesc() {
        return personAntiSpoofingDesc;
    }

    public void setPersonAntiSpoofingDesc(String personAntiSpoofingDesc) {
        this.personAntiSpoofingDesc = personAntiSpoofingDesc;
    }

    public double getRiskScore() {
        return riskScore;
    }

    public void setRiskScore(double riskScore) {
        this.riskScore = riskScore;
    }

    public String getSuggest() {
        return suggest;
    }

    public void setSuggest(String suggest) {
        this.suggest = suggest;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    public String getHitTypes() {
        return hitTypes;
    }

    public void setHitTypes(String hitTypes) {
        this.hitTypes = hitTypes;
    }

    public String getEconnoisserurState() {
        return econnoisserurState;
    }

    public void setEconnoisserurState(String econnoisserurState) {
        this.econnoisserurState = econnoisserurState;
    }

    public String getFraudRiskState() {
        return fraudRiskState;
    }

    public void setFraudRiskState(String fraudRiskState) {
        this.fraudRiskState = fraudRiskState;
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

    public int getLast12MthsLoanCnt() {
        return last12MthsLoanCnt;
    }

    public void setLast12MthsLoanCnt(int last12MthsLoanCnt) {
        this.last12MthsLoanCnt = last12MthsLoanCnt;
    }

    public int getLoanOrgCnt() {
        return loanOrgCnt;
    }

    public void setLoanOrgCnt(int loanOrgCnt) {
        this.loanOrgCnt = loanOrgCnt;
    }

    public double getAvgCredits() {
        return avgCredits;
    }

    public void setAvgCredits(double avgCredits) {
        this.avgCredits = avgCredits;
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

    public int getLoanNoClosedCnt() {
        return loanNoClosedCnt;
    }

    public void setLoanNoClosedCnt(int loanNoClosedCnt) {
        this.loanNoClosedCnt = loanNoClosedCnt;
    }

    public int getUndefinedCnt() {
        return undefinedCnt;
    }

    public void setUndefinedCnt(int undefinedCnt) {
        this.undefinedCnt = undefinedCnt;
    }

    public double getOverdueAmount() {
        return overdueAmount;
    }

    public void setOverdueAmount(double overdueAmount) {
        this.overdueAmount = overdueAmount;
    }

    public int getOverdueTotal() {
        return overdueTotal;
    }

    public void setOverdueTotal(int overdueTotal) {
        this.overdueTotal = overdueTotal;
    }

    public int getOverdueNoClosedCnt() {
        return overdueNoClosedCnt;
    }

    public void setOverdueNoClosedCnt(int overdueNoClosedCnt) {
        this.overdueNoClosedCnt = overdueNoClosedCnt;
    }

    public List<OverdueDetail> getOverdueDetails() {
        return overdueDetails;
    }

    public void setOverdueDetails(List<OverdueDetail> overdueDetails) {
        this.overdueDetails = overdueDetails;
    }

    public int getTotleCount() {
        return totleCount;
    }

    public void setTotleCount(int totleCount) {
        this.totleCount = totleCount;
    }

    public int getAlCount() {
        return alCount;
    }

    public void setAlCount(int alCount) {
        this.alCount = alCount;
    }

    public int getZxCount() {
        return zxCount;
    }

    public void setZxCount(int zxCount) {
        this.zxCount = zxCount;
    }

    public int getSxCount() {
        return sxCount;
    }

    public void setSxCount(int sxCount) {
        this.sxCount = sxCount;
    }

    public int getSwCount() {
        return swCount;
    }

    public void setSwCount(int swCount) {
        this.swCount = swCount;
    }

    public int getCqggCount() {
        return cqggCount;
    }

    public void setCqggCount(int cqggCount) {
        this.cqggCount = cqggCount;
    }

    public int getWdyqCount() {
        return wdyqCount;
    }

    public void setWdyqCount(int wdyqCount) {
        this.wdyqCount = wdyqCount;
    }

    public List<AlsDetail> getAlsDetails() {
        return alsDetails;
    }

    public void setAlsDetails(List<AlsDetail> alsDetails) {
        this.alsDetails = alsDetails;
    }

    public List<ZxsDetail> getZxsDetails() {
        return zxsDetails;
    }

    public void setZxsDetails(List<ZxsDetail> zxsDetails) {
        this.zxsDetails = zxsDetails;
    }

    public List<SxsDetail> getSxsDetails() {
        return sxsDetails;
    }

    public void setSxsDetails(List<SxsDetail> sxsDetails) {
        this.sxsDetails = sxsDetails;
    }

    public List<SwsDetail> getSwsDetails() {
        return swsDetails;
    }

    public void setSwsDetails(List<SwsDetail> swsDetails) {
        this.swsDetails = swsDetails;
    }

    public List<CqsDetail> getCqsDetails() {
        return cqsDetails;
    }

    public void setCqsDetails(List<CqsDetail> cqsDetails) {
        this.cqsDetails = cqsDetails;
    }

    public List<WdyqsDetail> getWdyqsDetails() {
        return wdyqsDetails;
    }

    public void setWdyqsDetails(List<WdyqsDetail> wdyqsDetails) {
        this.wdyqsDetails = wdyqsDetails;
    }

    public int getAppplyCnt() {
        return appplyCnt;
    }

    public void setAppplyCnt(int appplyCnt) {
        this.appplyCnt = appplyCnt;
    }

    public int getApplyNetLoanCnt() {
        return applyNetLoanCnt;
    }

    public void setApplyNetLoanCnt(int applyNetLoanCnt) {
        this.applyNetLoanCnt = applyNetLoanCnt;
    }

    public int getApplyFinclCnt() {
        return applyFinclCnt;
    }

    public void setApplyFinclCnt(int applyFinclCnt) {
        this.applyFinclCnt = applyFinclCnt;
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

    public List<HistoryQueryItem> getHistoryQueryItems() {
        return historyQueryItems;
    }

    public void setHistoryQueryItems(List<HistoryQueryItem> historyQueryItems) {
        this.historyQueryItems = historyQueryItems;
    }
}
