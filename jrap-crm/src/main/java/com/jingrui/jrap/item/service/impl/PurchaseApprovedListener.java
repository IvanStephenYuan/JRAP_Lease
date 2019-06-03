/*
 * *
 *  @file com.maddyhome.idea.copyright.pattern.JavaCopyrightVariablesProvider$1@10664452$
 *  @CopyRight (C) 2018 ZheJiangJingRui Co. Ltd.
 *  @brief JingRui Application Platform
 *  @author $name$
 *  @email yulong.yuan@jr-info.cn
 *  @date $date$
 * /
 */

package com.jingrui.jrap.item.service.impl;

import com.jingrui.jrap.activiti.custom.IActivitiBean;
import com.jingrui.jrap.fnd.dto.CompanyLimit;
import com.jingrui.jrap.fnd.dto.LimitChange;
import com.jingrui.jrap.fnd.mapper.CompanyLimitMapper;
import com.jingrui.jrap.fnd.mapper.LimitChangeMapper;
import com.jingrui.jrap.item.dto.Purchase;
import com.jingrui.jrap.item.mapper.PurchaseDetailMapper;
import com.jingrui.jrap.item.mapper.PurchaseMapper;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.impl.util.CollectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;


public class PurchaseApprovedListener implements ExecutionListener, IActivitiBean {

  private WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();

  private PurchaseMapper purchaseMapper;

  private PurchaseDetailMapper purchaseDetailMapper;

  /**
   * 采购申请-同意-监听处理
   * @param delegateExecution
   */
  @Override
  public void notify(DelegateExecution delegateExecution) {
    // 获取Mapper接口
    purchaseMapper = context.getBean(PurchaseMapper.class);
    // 获取流程PK
    Long purchaseId = Long.parseLong(delegateExecution.getProcessInstanceBusinessKey());
    // 查询采购申请信息
    Purchase purchase = purchaseMapper.selectByPrimaryKey(purchaseId);
    // 更新
    purchase.setStatus("APPROVE");//更改状态为通过
    purchase.setApprovedDate(new Date());
    purchaseMapper.updateByPrimaryKeySelective(purchase);
  }
}
