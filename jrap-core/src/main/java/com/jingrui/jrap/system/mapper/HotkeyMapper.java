package com.jingrui.jrap.system.mapper;

import com.jingrui.jrap.mybatis.common.Mapper;
import com.jingrui.jrap.system.dto.Hotkey;

import java.util.List;

public interface HotkeyMapper extends Mapper<Hotkey>{

    List<Hotkey> queryAll();
}
