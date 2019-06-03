/*
 * *
 *  @file com.maddyhome.idea.copyright.pattern.JavaCopyrightVariablesProvider$1@26ca20bb$
 *  @CopyRight (C) 2018 ZheJiangJingRui Co. Ltd.
 *  @brief JingRui Application Platform
 *  @author $name$
 *  @email yulong.yuan@jr-info.cn
 *  @date $date$
 * /
 */

package com.jingrui.jrap.fnd.service.impl;

import com.jingrui.jrap.activiti.custom.IActivitiBean;
import com.jingrui.jrap.fnd.dto.CompanyLimit;
import com.jingrui.jrap.fnd.dto.LimitChange;
import com.jingrui.jrap.fnd.mapper.CompanyLimitMapper;
import com.jingrui.jrap.fnd.mapper.LimitChangeMapper;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.impl.util.CollectionUtil;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;
import java.util.List;

public class LimitChangeApprovedListener implements ExecutionListener, IActivitiBean {

  private WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();

  private CompanyLimitMapper companyLimitMapper;

  private LimitChangeMapper limitChangeMapper;

  /**
   * 额度变更申请-同意-监听处理
   * @param delegateExecution
   */
  @Override
  public void notify(DelegateExecution delegateExecution) {
    // 获取Mapper接口
    companyLimitMapper = context.getBean(CompanyLimitMapper.class);
    limitChangeMapper = context.getBean(LimitChangeMapper.class);
    // 获取流程PK
    Long companyId = Long.parseLong(delegateExecution.getProcessInstanceBusinessKey());
    // 查询变更记录
    LimitChange change = new LimitChange();
    change.setCompanyId(companyId);
    change.setStatus("SUBMIT");
    List<LimitChange>  limitChanges = limitChangeMapper.select(change);
    if(CollectionUtil.isNotEmpty(limitChanges)){
      for (LimitChange limitChange : limitChanges) {
        // 查询新增的额度记录
        CompanyLimit limit = new CompanyLimit();
        limit.setCompanyId(limitChange.getCompanyId());
        limit.setLimitCompanyId(limitChange.getLimitCompanyId());
        limit.setEnabledFlag("N");
        List<CompanyLimit>  companyLists = companyLimitMapper.select(limit);
        if(CollectionUtil.isNotEmpty(companyLists)){
          CompanyLimit limitAdd1 =  companyLists.get(0);
          // 更新额度表
          limitAdd1.setEnabledFlag("Y");
          limitAdd1.setBalance(limitAdd1.getLimitAmount());
          companyLimitMapper.updateByPrimaryKeySelective(limitAdd1);
        }
        // 查询调整的额度记录
        limit.setEnabledFlag("Y");
        List<CompanyLimit>  companyLists1 = companyLimitMapper.select(limit);
        if(CollectionUtil.isNotEmpty(companyLists1)){
          CompanyLimit limitUpd1 =  companyLists1.get(0);
          // 更新额度表
          limitUpd1.setLimitAmount(limitUpd1.getLimitAmount()+limitChange.getLimitAmount());
          if(limitChange.getLimitAmount()>0){
            limitUpd1.setBalance(limitUpd1.getBalance()+limitChange.getLimitAmount());
          }
          companyLimitMapper.updateByPrimaryKeySelective(limitUpd1);
        }
        // 更新change表
        limitChange.setStatus("VALID");// 状态改为生效
        limitChange.setApprovedDate(new Date()); //设置审批日期
        limitChangeMapper.updateByPrimaryKeySelective(limitChange);
      }
    }
  }
}
