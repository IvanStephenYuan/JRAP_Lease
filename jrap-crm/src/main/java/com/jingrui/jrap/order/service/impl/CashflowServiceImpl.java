package com.jingrui.jrap.order.service.impl;

import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.jingrui.jrap.order.dto.Cashflow;
import com.jingrui.jrap.order.service.ICashflowService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class CashflowServiceImpl extends BaseServiceImpl<Cashflow> implements ICashflowService{

}