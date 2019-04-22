/*
 * *
 *  @file com.maddyhome.idea.copyright.pattern.JavaCopyrightVariablesProvider$1@5fbedaa2$
 *  @CopyRight (C) 2018 ZheJiangJingRui Co. Ltd.
 *  @brief JingRui Application Platform
 *  @author $name$
 *  @email yulong.yuan@jr-info.cn
 *  @date $date$
 * /
 */

package com.jingrui.jrap.product.controllers;

import com.jingrui.jrap.activiti.custom.IActivitiBean;
import com.jingrui.jrap.code.rule.service.ISysCodeRuleProcessService;
import com.jingrui.jrap.product.dto.Good;
import com.jingrui.jrap.product.mapper.GoodMapper;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

public class RevocationRejectGoodListener implements ExecutionListener, IActivitiBean {

  private WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
  @Autowired
  ISysCodeRuleProcessService codeRuleProcessService;

  public GoodMapper wklgoodMapper;

  @Override
  public void notify(DelegateExecution delegateExecution) {
    // 获取Mapper接口
    wklgoodMapper = context.getBean(GoodMapper.class);
    codeRuleProcessService = context.getBean(ISysCodeRuleProcessService.class);
    // 获取流程PK
    Long revocationId = Long.parseLong(delegateExecution.getProcessInstanceBusinessKey());
    //通过流程pk来找到申请单据信息
    Good wklgood = wklgoodMapper.selectByPrimaryKey(revocationId);
    //将状态改变
    wklgood.setStatus("CLOSED");
    //更新状态
    wklgoodMapper.updateByPrimaryKey(wklgood);
  }

}
