package com.jingrui.jrap.order.service.impl;

import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.jingrui.jrap.order.dto.OrderCycle;
import com.jingrui.jrap.order.service.IOrderCycleService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class OrderCycleServiceImpl extends BaseServiceImpl<OrderCycle> implements IOrderCycleService{

}