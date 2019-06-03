/*
 * *
 *  @file com.maddyhome.idea.copyright.pattern.JavaCopyrightVariablesProvider$1@6c885b5a$
 *  @CopyRight (C) 2018 ZheJiangJingRui Co. Ltd.
 *  @brief JingRui Application Platform
 *  @author $name$
 *  @email yulong.yuan@jr-info.cn
 *  @date $date$
 * /
 */

package com.jingrui.jrap.order.controllers;

import com.jingrui.jrap.activiti.custom.IActivitiBean;
import com.jingrui.jrap.code.rule.service.ISysCodeRuleProcessService;
import com.jingrui.jrap.order.dto.Order;
import com.jingrui.jrap.order.mapper.OrderMapper;
import com.jingrui.jrap.product.dto.Good;
import com.jingrui.jrap.product.mapper.GoodMapper;
import java.util.List;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

public class RevocationApproveOrderListener implements ExecutionListener, IActivitiBean {

  private WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
  @Autowired
  ISysCodeRuleProcessService codeRuleProcessService;

  public OrderMapper orderMapper;

  @Override
  public void notify(DelegateExecution delegateExecution) {
    // 获取Mapper接口
    orderMapper = context.getBean(OrderMapper.class);
    codeRuleProcessService = context.getBean(ISysCodeRuleProcessService.class);
    // 获取流程PK
    Long revocationId = Long.parseLong(delegateExecution.getProcessInstanceBusinessKey());
    //通过流程pk来找到申请单据信息
    Order qorder = new Order();
    qorder.setOrderId(revocationId);
    List<Order> wklorder = orderMapper.selectOrder(qorder);
    for (Order uorder : wklorder) {
      //将状态改变
      uorder.setOrderStatus("APPROVED");
      //更新状态
      orderMapper.updateByPrimaryKeySelective(uorder);
    }
  }

}
