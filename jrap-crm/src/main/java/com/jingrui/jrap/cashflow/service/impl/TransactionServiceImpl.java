package com.jingrui.jrap.cashflow.service.impl;

import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.jingrui.jrap.cashflow.dto.Transaction;
import com.jingrui.jrap.cashflow.service.ITransactionService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class TransactionServiceImpl extends BaseServiceImpl<Transaction> implements ITransactionService{

}