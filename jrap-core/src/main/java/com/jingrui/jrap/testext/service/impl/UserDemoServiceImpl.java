package com.jingrui.jrap.testext.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import com.jingrui.jrap.testext.dto.UserDemo;
import com.jingrui.jrap.testext.service.IUserDemoService;

@Service
@Transactional
public class UserDemoServiceImpl extends BaseServiceImpl<UserDemo> implements IUserDemoService {

}