/*
 * *
 *  @file com.maddyhome.idea.copyright.pattern.JavaCopyrightVariablesProvider$1@5d47d378$
 *  @CopyRight (C) 2018 ZheJiangJingRui Co. Ltd.
 *  @brief JingRui Application Platform
 *  @author $name$
 *  @email yulong.yuan@jr-info.cn
 *  @date $date$
 * /
 */

package com.jingrui.jrap.change.mapper;

import com.jingrui.jrap.mybatis.common.Mapper;
import com.jingrui.jrap.change.dto.ChangeAssign;
import java.util.List;

public interface ChangeAssignMapper extends Mapper<ChangeAssign> {

  List<ChangeAssign> selectByCgeId(Long ChangeId);

}