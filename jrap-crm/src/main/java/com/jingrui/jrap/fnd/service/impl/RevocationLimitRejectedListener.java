/*
 * *
 *  @file com.maddyhome.idea.copyright.pattern.JavaCopyrightVariablesProvider$1@3765ebd$
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
import com.jingrui.jrap.fnd.mapper.CompanyMapper;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;


public class RevocationLimitRejectedListener implements ExecutionListener, IActivitiBean {

    private WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
    @Autowired
    ISysCodeRuleProcessService codeRuleProcessService;


    private CompanyMapper companyMapper;

    @Override
    public void notify(DelegateExecution delegateExecution) {
        // 获取Mapper接口
        companyMapper = context.getBean(CompanyMapper.class);
        // 获取流程PK
        Long revocationId = Long.parseLong(delegateExecution.getProcessInstanceBusinessKey());
        // 通过流程pk来找到商户信息
        Company company = companyMapper.selectByPrimaryKey(revocationId);
        // 改变状态
        company.setStatus("NEW");
        companyMapper.updateByPrimaryKey(company);
    }
}
