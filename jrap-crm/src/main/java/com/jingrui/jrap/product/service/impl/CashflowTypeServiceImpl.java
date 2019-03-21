package com.jingrui.jrap.product.service.impl;

import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.jingrui.jrap.product.dto.CashflowType;
import com.jingrui.jrap.product.service.ICashflowTypeService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class CashflowTypeServiceImpl extends BaseServiceImpl<CashflowType> implements ICashflowTypeService{

}