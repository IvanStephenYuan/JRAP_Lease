package com.jingrui.jrap.order.service;

import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.core.ProxySelf;
import com.jingrui.jrap.system.service.IBaseService;
import com.jingrui.jrap.order.dto.Order;

public interface IOrderService extends IBaseService<Order>, ProxySelf<IOrderService>{
  /*自然人订单启动工作流*/
  void createVacationInstance(IRequest iRequest, Order order);
  /*法人订单启动工作流*/
  void createVacationLawInstance(IRequest iRequest, Order order);
  /*启动订单签约工作流*/
  void createVacationOrderSign(IRequest iRequest, Order order);
}