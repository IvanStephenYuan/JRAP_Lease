package com.jingrui.jrap.config.service.impl;

import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.jingrui.jrap.config.dto.SettleItem;
import com.jingrui.jrap.config.service.ISettleItemService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class SettleItemServiceImpl extends BaseServiceImpl<SettleItem> implements ISettleItemService{

}