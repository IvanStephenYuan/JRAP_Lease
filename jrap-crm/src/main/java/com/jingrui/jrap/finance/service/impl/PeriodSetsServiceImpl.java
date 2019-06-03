/*
 * *
 *  @file com.maddyhome.idea.copyright.pattern.JavaCopyrightVariablesProvider$1@52020750$
 *  @CopyRight (C) 2018 ZheJiangJingRui Co. Ltd.
 *  @brief JingRui Application Platform
 *  @author $name$
 *  @email yulong.yuan@jr-info.cn
 *  @date $date$
 * /
 */

package com.jingrui.jrap.finance.service.impl;

import com.jingrui.jrap.finance.dto.PeriodSets;
import com.jingrui.jrap.finance.service.IPeriodSetsService;
import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class PeriodSetsServiceImpl extends BaseServiceImpl<PeriodSets> implements IPeriodSetsService {

}