/*
 * *
 *  @file com.maddyhome.idea.copyright.pattern.JavaCopyrightVariablesProvider$1@2cfde4fe$
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

public class LimitChangeRejectedListener implements ExecutionListener, IActivitiBean {

  private WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
  private CompanyLimitMapper companyLimitMapper;

  private LimitChangeMapper limitChangeMapper;

  /**
   * 额度变更申请-拒绝-监听处理
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
        // 查询调整的额度记录
        CompanyLimit limit = new CompanyLimit();
        limit.setCompanyId(limitChange.getCompanyId());
        limit.setLimitCompanyId(limitChange.getLimitCompanyId());
        limit.setEnabledFlag("Y");
        List<CompanyLimit>  companyLists1 = companyLimitMapper.select(limit);
        if(CollectionUtil.isNotEmpty(companyLists1)){
          CompanyLimit limitUpd =  companyLists1.get(0);
          // 恢复余额
          if(limitChange.getLimitAmount()<0){
            limitUpd.setBalance(limitUpd.getBalance()-limitChange.getLimitAmount()); // 减去负数等于加上正数
            companyLimitMapper.updateByPrimaryKeySelective(limitUpd);
          }
        }
        // 更新change表
        limitChange.setStatus("NEW");// 状态改为新建
        limitChange.setApprovedDate(new Date()); //设置审批日期
        limitChangeMapper.updateByPrimaryKeySelective(limitChange);
      }
    }
  }
}
