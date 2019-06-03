/*
 * *
 *  @file com.maddyhome.idea.copyright.pattern.JavaCopyrightVariablesProvider$1@7f72a5f2$
 *  @CopyRight (C) 2018 ZheJiangJingRui Co. Ltd.
 *  @brief JingRui Application Platform
 *  @author $name$
 *  @email yulong.yuan@jr-info.cn
 *  @date $date$
 * /
 */

package com.jingrui.jrap.finance.service.impl;

import com.jingrui.jrap.finance.dto.PeriodRules;
import com.jingrui.jrap.finance.service.IPeriodRulesService;
import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class PeriodRulesServiceImpl extends BaseServiceImpl<PeriodRules> implements IPeriodRulesService {

}