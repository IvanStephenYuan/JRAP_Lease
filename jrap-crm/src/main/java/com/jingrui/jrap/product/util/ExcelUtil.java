/*
 * *
 *  @file com.maddyhome.idea.copyright.pattern.JavaCopyrightVariablesProvider$1@65bddf85$
 *  @CopyRight (C) 2018 ZheJiangJingRui Co. Ltd.
 *  @brief JingRui Application Platform
 *  @author $name$
 *  @email yulong.yuan@jr-info.cn
 *  @date $date$
 * /
 */

package com.jingrui.jrap.product.util;

import org.apache.poi.ss.formula.functions.Finance;

import java.math.BigDecimal;

public class ExcelUtil {
    //精度
    private static final int PERCISE = 2;

    /**
     *  pmt计算公式
     * @param rate 年利率(%)
     * @param nper 贷款期数(月)
     * @param pv 贷款金额
     * @param fv 留购价f款
     * @param type 先付/后付
     * @return
     */
    public static double pmt(double rate, int nper, double pv, double fv, int type) {
        double pmtAmount = 0;
        if(rate > 0 &&  pv > 0){
            double monthRate = rate / (12 * 100);
            pmtAmount = Finance.pmt(monthRate, nper, (-1)*pv, fv, type);
            pmtAmount = new BigDecimal(pmtAmount).setScale(ExcelUtil.PERCISE, BigDecimal.ROUND_HALF_UP).doubleValue();
        }

        return pmtAmount;
    }

    /**
     * pmt计算公式
     * @param rate 年利率(%)
     * @param nper 贷款期数(月)
     * @param pv 贷款金额
     * @param fv 留购价f款
     * @return
     */
    public static double pmt(double rate, int nper, double pv, double fv) {
       return ExcelUtil.pmt(rate, nper, pv, fv, 0);
    }

    /**
     * pmt计算公式
     * @param rate 年利率(%)
     * @param nper 贷款期数(月)
     * @param pv 贷款金额
     * @return
     */
    public static double pmt(double rate, int nper, double pv) {
        return ExcelUtil.pmt(rate, nper, pv, 0, 0);
    }

    /**
     *  ipmt计算公式
     * @param rate 年利率(%)
     * @param nper 贷款期数(月)
     * @param pv 贷款金额
     * @param fv 留购价f款
     * @param type 先付/后付
     * @return
     */
    public static double ipmt(double rate,int per, int nper, double pv, double fv, int type) {
        double pmtAmount = 0;
        if(rate > 0 &&  pv > 0){
            double monthRate = rate / (12 * 100);
            pmtAmount = Finance.ipmt(monthRate, per, nper, (-1)*pv, fv, type);
            pmtAmount = new BigDecimal(pmtAmount).setScale(ExcelUtil.PERCISE, BigDecimal.ROUND_HALF_UP).doubleValue();
        }

        return pmtAmount;
    }

    /**
     * pmt计算公式
     * @param rate 年利率(%)
     * @param nper 贷款期数(月)
     * @param pv 贷款金额
     * @param fv 留购价f款
     * @return
     */
    public static double ipmt(double rate,int per,int nper, double pv, double fv) {
        return ExcelUtil.ipmt(rate, per, nper, pv, fv, 0);
    }

    /**
     * pmt计算公式
     * @param rate 年利率(%)
     * @param nper 贷款期数(月)
     * @param pv 贷款金额
     * @return
     */
    public static double ipmt(double rate, int per, int nper, double pv) {
        return ExcelUtil.ipmt(rate, per, nper, pv, 0, 0);
    }

    /**
     *  ppmt计算公式
     * @param rate 年利率(%)
     * @param nper 贷款期数(月)
     * @param pv 贷款金额
     * @param fv 留购价f款
     * @param type 先付/后付
     * @return
     */
    public static double ppmt(double rate,int per, int nper, double pv, double fv, int type) {
        double pmtAmount = 0;
        if(rate > 0 &&  pv > 0){
            double monthRate = rate / (12 * 100);
            pmtAmount = Finance.ppmt(monthRate, per, nper, (-1)*pv, fv, type);
            pmtAmount = new BigDecimal(pmtAmount).setScale(ExcelUtil.PERCISE, BigDecimal.ROUND_HALF_UP).doubleValue();
        }

        return pmtAmount;
    }

    /**
     * ppmt计算公式
     * @param rate 年利率(%)
     * @param nper 贷款期数(月)
     * @param pv 贷款金额
     * @param fv 留购价f款
     * @return
     */
    public static double ppmt(double rate, int per, int nper, double pv, double fv) {
        return ExcelUtil.ppmt(rate, per, nper, pv, fv, 0);
    }

    /**
     * ppmt计算公式
     * @param rate 年利率(%)
     * @param nper 贷款期数(月)
     * @param pv 贷款金额
     * @return
     */
    public static double ppmt(double rate, int per, int nper, double pv) {
        return ExcelUtil.ppmt(rate, per, nper, pv, 0, 0);
    }

    public static void main(String[]args){
        System.out.println(ExcelUtil.ipmt(7.2, 12, 12, 1000000));
    }
}
