/*
 * *
 *  @file com.maddyhome.idea.copyright.pattern.JavaCopyrightVariablesProvider$1@f9f3be8$
 *  @CopyRight (C) 2018 ZheJiangJingRui Co. Ltd.
 *  @brief JingRui Application Platform
 *  @author $name$
 *  @email yulong.yuan@jr-info.cn
 *  @date $date$
 * /
 */

package com.jingrui.jrap.change.service.impl;

import com.jingrui.jrap.core.ProxySelf;
import com.jingrui.jrap.system.service.IBaseService;
import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import com.jingrui.jrap.change.dto.ChangeTrack;
import com.jingrui.jrap.change.service.IChangeTrackService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class ChangeTrackServiceImpl extends BaseServiceImpl<ChangeTrack> implements IChangeTrackService {

}