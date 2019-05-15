/*
 * *
 *  @file com.maddyhome.idea.copyright.pattern.JavaCopyrightVariablesProvider$1@93138e7$
 *  @CopyRight (C) 2018 ZheJiangJingRui Co. Ltd.
 *  @brief JingRui Application Platform
 *  @author $name$
 *  @email yulong.yuan@jr-info.cn
 *  @date $date$
 * /
 */

package com.jingrui.jrap.fnd.service.impl;

import com.jingrui.jrap.activiti.custom.IActivitiBean;
import com.jingrui.jrap.code.rule.service.ISysCodeRuleProcessService;
import com.jingrui.jrap.fnd.dto.Company;
import com.jingrui.jrap.fnd.dto.CompanyLimit;
import com.jingrui.jrap.fnd.mapper.CompanyLimitMapper;
import com.jingrui.jrap.fnd.mapper.CompanyMapper;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.impl.util.CollectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

public class RevocationLimitApprovedListener implements ExecutionListener, IActivitiBean {

  private WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
  @Autowired
  ISysCodeRuleProcessService codeRuleProcessService;


  private CompanyMapper companyMapper;

  private CompanyLimitMapper companyLimitMapper;

  /**
   * 商户入网申请-同意-监听处理
   * @param delegateExecution
   */
  @Override
  public void notify(DelegateExecution delegateExecution) {
    // 获取Mapper接口
    companyMapper = context.getBean(CompanyMapper.class);
    companyLimitMapper = context.getBean(CompanyLimitMapper.class);

    // 获取流程PK
    Long revocationId = Long.parseLong(delegateExecution.getProcessInstanceBusinessKey());
    // 通过流程pk来找到商户信息
    Company company = companyMapper.selectByPrimaryKey(revocationId);
    company.setStatus("VALID");// 改变状态
    companyMapper.updateByPrimaryKey(company);
    // 通过流程pk来找到额度信息
    CompanyLimit companyLimit = new CompanyLimit();
    companyLimit.setCompanyId(revocationId);
    List<CompanyLimit> limits = companyLimitMapper.select(companyLimit);
    if(CollectionUtil.isNotEmpty(limits)){
      for (CompanyLimit limit : limits) {
         limit.setBalance(limit.getLimitAmount());
         limit.setEnabledFlag("Y");// 改变是否可用标志
         companyLimitMapper.updateByPrimaryKey(limit);
      }
    }
  }
}
