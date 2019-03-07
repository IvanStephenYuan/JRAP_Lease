package com.jingrui.jrap.system.service;

import com.jingrui.jrap.core.ProxySelf;
import com.jingrui.jrap.system.service.IBaseService;
import com.jingrui.jrap.system.dto.Shortcut;

import java.util.List;

public interface IShortcutService extends IBaseService<Shortcut>, ProxySelf<IShortcutService> {

    List<Shortcut> selectMyShortcutFunction(Long userId);

}