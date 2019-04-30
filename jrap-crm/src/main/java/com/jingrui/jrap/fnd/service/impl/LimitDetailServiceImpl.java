/*
 * *
 *  @file com.maddyhome.idea.copyright.pattern.JavaCopyrightVariablesProvider$1@1730f43f$
 *  @CopyRight (C) 2018 ZheJiangJingRui Co. Ltd.
 *  @brief JingRui Application Platform
 *  @author $name$
 *  @email yulong.yuan@jr-info.cn
 *  @date $date$
 * /
 */

package com.jingrui.jrap.fnd.service.impl;

import com.jingrui.jrap.core.ProxySelf;
import com.jingrui.jrap.system.service.IBaseService;
import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import com.jingrui.jrap.fnd.dto.LimitDetail;
import com.jingrui.jrap.fnd.service.ILimitDetailService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class LimitDetailServiceImpl extends BaseServiceImpl<LimitDetail> implements ILimitDetailService {

}