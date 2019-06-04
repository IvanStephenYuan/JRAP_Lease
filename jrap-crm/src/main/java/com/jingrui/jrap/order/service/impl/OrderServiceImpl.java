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

import java.util.List;

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
    private final String processDefinitionOrderSignKey = "ORDER_SIGN_CHECK";

    ProcessInstanceCreateRequest instanceCreateRequest;

    /*自然人订单审核工作流*/
    public void createVacationInstance(IRequest iRequest, Order order) {
        //设置流程状态为提交中
        instanceCreateRequest = new ProcessInstanceCreateRequest();
        order.setOrderStatus("SUBMIT");
        startWorkflow(iRequest, order, processDefinitionKey);
    }

    /*法人订单审核工作流*/
    public void createVacationLawInstance(IRequest iRequest, Order order) {
        //设置流程状态为提交中
        instanceCreateRequest = new ProcessInstanceCreateRequest();
        order.setOrderStatus("SUBMIT");
        startWorkflow(iRequest, order, processDefinitionLawKey);
    }

    /*启动订单签约工作流*/
    public void createVacationOrderSign(IRequest iRequest, Order order) {
        instanceCreateRequest = new ProcessInstanceCreateRequest();
        order.setOrderStatus("SIGNING");
        startWorkflow(iRequest, order, processDefinitionOrderSignKey);
    }

    public void startWorkflow(IRequest iRequest, Order order, String processDefinitKey) {
        orderMapper.updateByPrimaryKeySelective(order);
        instanceCreateRequest.setBusinessKey(String.valueOf(order.getOrderId()));
        instanceCreateRequest.setProcessDefinitionKey(processDefinitKey);
        //启动工作流
        activitiService.startProcess(iRequest, instanceCreateRequest);
    }

    public void updateOrderCheck(String status,Long id){
        //通过流程pk来找到申请单据信息
        Order qorder = new Order();
        qorder.setOrderId(id);
        List<Order> wklorder = orderMapper.selectOrder(qorder);
        for (Order uorder : wklorder) {
            uorder.setOrderStatus(status);
            orderMapper.updateByPrimaryKeySelective(uorder);
        }
    }
}