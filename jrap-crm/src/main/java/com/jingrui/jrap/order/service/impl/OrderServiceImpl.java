package com.jingrui.jrap.order.service.impl;

import com.jingrui.jrap.activiti.service.IActivitiService;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.order.mapper.OrderMapper;
import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import org.activiti.rest.service.api.runtime.process.ProcessInstanceCreateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jingrui.jrap.order.dto.Order;
import com.jingrui.jrap.order.service.IOrderService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class OrderServiceImpl extends BaseServiceImpl<Order> implements IOrderService {

  @Autowired
  private IActivitiService activitiService;  //工作流相关Service
  @Autowired
  private OrderMapper orderMapper;
  //产品发布流程标识，通过其启动流程
  private final String processDefinitionKey = "ORDER_NATURAL_CHECK";
  private final String processDefinitionLawKey = "ORDER_LAW_CHECK";

  public void createVacationInstance(IRequest iRequest, Order order) {
    //设置流程状态为提交中
    order.setOrderStatus("SUBMIT");
    orderMapper.updateByPrimaryKeySelective(order);
    ProcessInstanceCreateRequest instanceCreateRequest = new ProcessInstanceCreateRequest();
    instanceCreateRequest.setBusinessKey(String.valueOf(order.getOrderId()));
    instanceCreateRequest.setProcessDefinitionKey(processDefinitionKey);
    //启动工作流
    activitiService.startProcess(iRequest, instanceCreateRequest);
  }

  public void createVacationLawInstance(IRequest iRequest, Order order) {
    //设置流程状态为提交中
    order.setOrderStatus("SUBMIT");
    orderMapper.updateByPrimaryKeySelective(order);
    ProcessInstanceCreateRequest instanceCreateRequest = new ProcessInstanceCreateRequest();
    instanceCreateRequest.setBusinessKey(String.valueOf(order.getOrderId()));
    instanceCreateRequest.setProcessDefinitionKey(processDefinitionLawKey);
    //启动工作流
    activitiService.startProcess(iRequest, instanceCreateRequest);
  }

}