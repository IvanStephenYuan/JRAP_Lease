package com.jingrui.jrap.config.service.impl;

import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.jingrui.jrap.config.dto.Config;
import com.jingrui.jrap.config.service.IConfigService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class ConfigServiceImpl extends BaseServiceImpl<Config> implements IConfigService{

}