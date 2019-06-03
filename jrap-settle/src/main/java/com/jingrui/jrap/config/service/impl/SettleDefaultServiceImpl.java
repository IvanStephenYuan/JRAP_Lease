package com.jingrui.jrap.config.service.impl;

import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.jingrui.jrap.config.dto.SettleDefault;
import com.jingrui.jrap.config.service.ISettleDefaultService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class SettleDefaultServiceImpl extends BaseServiceImpl<SettleDefault> implements ISettleDefaultService{

}