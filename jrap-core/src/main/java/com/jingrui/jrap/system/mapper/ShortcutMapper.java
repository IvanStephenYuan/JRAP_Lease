package com.jingrui.jrap.system.mapper;

import com.jingrui.jrap.mybatis.common.Mapper;
import com.jingrui.jrap.system.dto.Shortcut;

import java.util.List;

public interface ShortcutMapper extends Mapper<Shortcut> {

    List<Shortcut> selectMyShortcutFunction(Long userId);

}