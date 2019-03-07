package com.jingrui.jrap.system.service.impl;

import com.jingrui.jrap.system.mapper.ShortcutMapper;
import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jingrui.jrap.system.dto.Shortcut;
import com.jingrui.jrap.system.service.IShortcutService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ShortcutServiceImpl extends BaseServiceImpl<Shortcut> implements IShortcutService {

    @Autowired
    ShortcutMapper shortcutMapper;

    @Override
    public List<Shortcut> selectMyShortcutFunction(Long userId) {
        return shortcutMapper.selectMyShortcutFunction(userId);
    }
}