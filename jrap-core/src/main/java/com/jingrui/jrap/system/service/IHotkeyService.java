package com.jingrui.jrap.system.service;

import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.core.ProxySelf;
import com.jingrui.jrap.core.annotation.StdWho;
import com.jingrui.jrap.system.service.IBaseService;
import com.jingrui.jrap.system.dto.Hotkey;

import java.util.List;

public interface IHotkeyService extends IBaseService<Hotkey>, ProxySelf<IHotkeyService>{

    /**
     * 获取首选项展示的热键数据
     *
     * @return
     */
     List<Hotkey> preferenceQuery(IRequest iRequest);

}