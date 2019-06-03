package com.jingrui.jrap.report.service.impl;

import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.jingrui.jrap.report.dto.Charge;
import com.jingrui.jrap.report.service.IChargeService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class ChargeServiceImpl extends BaseServiceImpl<Charge> implements IChargeService{

}