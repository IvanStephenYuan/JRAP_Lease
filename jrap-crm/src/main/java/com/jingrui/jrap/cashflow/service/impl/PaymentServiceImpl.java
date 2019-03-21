package com.jingrui.jrap.cashflow.service.impl;

import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.jingrui.jrap.cashflow.dto.Payment;
import com.jingrui.jrap.cashflow.service.IPaymentService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class PaymentServiceImpl extends BaseServiceImpl<Payment> implements IPaymentService{

}