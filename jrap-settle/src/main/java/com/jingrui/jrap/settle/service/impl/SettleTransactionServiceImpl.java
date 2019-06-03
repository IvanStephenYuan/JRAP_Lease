package com.jingrui.jrap.settle.service.impl;

import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.jingrui.jrap.settle.dto.SettleTransaction;
import com.jingrui.jrap.settle.service.ISettleTransactionService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class SettleTransactionServiceImpl extends BaseServiceImpl<SettleTransaction> implements ISettleTransactionService {

}