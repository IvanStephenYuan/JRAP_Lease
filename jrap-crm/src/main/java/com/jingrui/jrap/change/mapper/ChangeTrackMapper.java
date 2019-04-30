/*
 * *
 *  @file com.maddyhome.idea.copyright.pattern.JavaCopyrightVariablesProvider$1@348305b7$
 *  @CopyRight (C) 2018 ZheJiangJingRui Co. Ltd.
 *  @brief JingRui Application Platform
 *  @author $name$
 *  @email yulong.yuan@jr-info.cn
 *  @date $date$
 * /
 */

package com.jingrui.jrap.change.mapper;

import com.jingrui.jrap.mybatis.common.Mapper;
import com.jingrui.jrap.change.dto.ChangeTrack;
import java.util.List;

public interface ChangeTrackMapper extends Mapper<ChangeTrack> {

  List<ChangeTrack> selectByChangeId(Long ChangeId);

}