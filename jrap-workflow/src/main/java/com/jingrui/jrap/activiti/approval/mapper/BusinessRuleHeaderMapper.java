package com.jingrui.jrap.activiti.approval.mapper;

import com.jingrui.jrap.activiti.approval.dto.BusinessRuleHeader;
import com.jingrui.jrap.mybatis.common.Mapper;

public interface BusinessRuleHeaderMapper extends Mapper<BusinessRuleHeader> {

    BusinessRuleHeader selectByCode(String code);
}