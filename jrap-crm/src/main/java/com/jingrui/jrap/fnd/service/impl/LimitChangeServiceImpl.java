/*
 * *
 *  @file com.maddyhome.idea.copyright.pattern.JavaCopyrightVariablesProvider$1@bd260b0$
 *  @CopyRight (C) 2018 ZheJiangJingRui Co. Ltd.
 *  @brief JingRui Application Platform
 *  @author $name$
 *  @email yulong.yuan@jr-info.cn
 *  @date $date$
 * /
 */

package com.jingrui.jrap.fnd.service.impl;
import com.jingrui.jrap.fnd.dto.LimitChange;
import com.jingrui.jrap.fnd.service.ILimitChangeService;
import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class LimitChangeServiceImpl extends BaseServiceImpl<LimitChange> implements ILimitChangeService {

}