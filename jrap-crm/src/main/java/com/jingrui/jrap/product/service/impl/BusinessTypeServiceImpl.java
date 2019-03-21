package com.jingrui.jrap.product.service.impl;

import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.jingrui.jrap.product.dto.BusinessType;
import com.jingrui.jrap.product.service.IBusinessTypeService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class BusinessTypeServiceImpl extends BaseServiceImpl<BusinessType> implements IBusinessTypeService{

}