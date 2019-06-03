/*
 * *
 *  @file com.maddyhome.idea.copyright.pattern.JavaCopyrightVariablesProvider$1@76ac8aa$
 *  @CopyRight (C) 2018 ZheJiangJingRui Co. Ltd.
 *  @brief JingRui Application Platform
 *  @author $name$
 *  @email yulong.yuan@jr-info.cn
 *  @date $date$
 * /
 */

package com.jingrui.jrap.finance.service;

import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.core.ProxySelf;
import com.jingrui.jrap.finance.dto.PeriodSets;
import com.jingrui.jrap.finance.dto.Periods;
import com.jingrui.jrap.system.service.IBaseService;

import java.util.List;

public interface IPeriodsService extends IBaseService<Periods>, ProxySelf<IPeriodsService>{

   List<Periods> creatPeriods(IRequest request, PeriodSets dto);

   void modify(IRequest request,Periods periods);
}