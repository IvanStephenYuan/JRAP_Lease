package com.jingrui.jrap.product.service.impl;

import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.jingrui.jrap.product.dto.UnitAssign;
import com.jingrui.jrap.product.service.IUnitAssignService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class UnitAssignServiceImpl extends BaseServiceImpl<UnitAssign> implements IUnitAssignService{

}