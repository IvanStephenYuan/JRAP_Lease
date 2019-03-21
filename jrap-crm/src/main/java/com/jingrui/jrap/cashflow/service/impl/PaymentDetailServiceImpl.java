package com.jingrui.jrap.cashflow.service.impl;

import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.jingrui.jrap.cashflow.dto.PaymentDetail;
import com.jingrui.jrap.cashflow.service.IPaymentDetailService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class PaymentDetailServiceImpl extends BaseServiceImpl<PaymentDetail> implements IPaymentDetailService{

}