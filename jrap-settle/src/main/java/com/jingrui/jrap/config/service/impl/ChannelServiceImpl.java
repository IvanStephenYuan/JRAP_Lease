package com.jingrui.jrap.config.service.impl;

import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.jingrui.jrap.config.dto.Channel;
import com.jingrui.jrap.config.service.IChannelService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class ChannelServiceImpl extends BaseServiceImpl<Channel> implements IChannelService{

}