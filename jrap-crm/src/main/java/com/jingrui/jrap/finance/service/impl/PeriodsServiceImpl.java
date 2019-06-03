/*
 * *
 *  @file com.maddyhome.idea.copyright.pattern.JavaCopyrightVariablesProvider$1@6af48e8a$
 *  @CopyRight (C) 2018 ZheJiangJingRui Co. Ltd.
 *  @brief JingRui Application Platform
 *  @author $name$
 *  @email yulong.yuan@jr-info.cn
 *  @date $date$
 * /
 */

package com.jingrui.jrap.finance.service.impl;


import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.finance.dto.PeriodRules;
import com.jingrui.jrap.finance.dto.PeriodSets;
import com.jingrui.jrap.finance.dto.Periods;
import com.jingrui.jrap.finance.mapper.PeriodsMapper;
import com.jingrui.jrap.finance.service.IPeriodsService;
import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class PeriodsServiceImpl extends BaseServiceImpl<Periods> implements IPeriodsService {
    @Autowired
    private PeriodsMapper periodsMapper;

    /**
     * 根据规则定义生成期间
     *
     * @param request
     * @param dto
     * @return
     */
    @Override
    public List<Periods> creatPeriods(IRequest request, PeriodSets dto) {
        String setCode = dto.getSetCode();
        String additionalFlag = dto.getAdditionalFlag();
        Long yearFrom = dto.getYearFrom();
        Long yearTo = dto.getYearTo();
        List<PeriodRules> rules = dto.getRulesList();
        for (int i = 0; i <=yearTo - yearFrom; i++) {
            Long year = yearFrom + i;//年
            for (PeriodRules rule : rules) {
                Periods periods = new Periods();
                periods.setSetCode(setCode);//会计期间代码
                periods.setCompanyId(request.getCompanyId());// 公司
                periods.setPeriodYear(year);//年
                periods.setPeriodNum(rule.getPeriodNum());//序号
                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
                Date startDate = null;
                Date endDate = null;
                DecimalFormat df = new DecimalFormat("00");
                try {
                    startDate = sf.parse( year + "-" + df.format(rule.getMonthFrom()) + "-" + df.format(rule.getDateFrom()));
                    endDate = sf.parse(year + "-" + df.format(rule.getMonthTo()) + "-" + df.format(rule.getDateTo()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                periods.setStartDate(startDate);
                periods.setEndDate(endDate);
                periods.setQuarterNum(rule.getQuarterNum());//季度
                String periodName = null;
                if ("PREFIX".equalsIgnoreCase(additionalFlag)) {//前缀
                    periodName = rule.getAdditionalName() + "-" + year;
                } else {//后缀
                    periodName = year + "-" + rule.getAdditionalName();
                }
                periods.setPeriodName(periodName);//期间
                periods.setAdjustmentFlag(rule.getAdjustmentFlag());
                periods.setStatus("CLOSE");
                periods.setInternalPeriodNum(Long.valueOf(year + df.format(rule.getPeriodNum())));//内部期间
                //periods.setMonthFlag();
                periodsMapper.insertSelective(periods);
            }
        }
        return new ArrayList<>();
    }

    /**
     * 期间变更
     * @param request
     * @param periods
     */
    @Override
    public void modify(IRequest request, Periods periods) {
        periodsMapper.updateByPrimaryKeySelective(periods);
    }
}