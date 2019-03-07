package com.jingrui.jrap.activiti.approval.service;

import com.jingrui.jrap.activiti.approval.dto.BusinessRuleHeader;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.core.ProxySelf;
import com.jingrui.jrap.system.service.IBaseService;

import java.util.List;

public interface IBusinessRuleHeaderService
        extends IBaseService<BusinessRuleHeader>, ProxySelf<IBusinessRuleHeaderService> {

    BusinessRuleHeader createRule(BusinessRuleHeader header);

    BusinessRuleHeader updateRule(BusinessRuleHeader header);

    boolean batchDelete(IRequest request, List<BusinessRuleHeader> headers);

    List<BusinessRuleHeader> selectAll(IRequest request, BusinessRuleHeader header);
}