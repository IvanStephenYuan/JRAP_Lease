package com.jingrui.jrap.finance.service.impl;

import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.jingrui.jrap.finance.dto.Bank;
import com.jingrui.jrap.finance.service.IBankService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class BankServiceImpl extends BaseServiceImpl<Bank> implements IBankService{

}