package com.jingrui.jrap.basic.service.impl;

import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.jingrui.jrap.basic.dto.BasicAccount;
import com.jingrui.jrap.basic.service.IBasicAccountService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class BasicAccountServiceImpl extends BaseServiceImpl<BasicAccount> implements IBasicAccountService {

}