package com.jingrui.jrap.order.service.impl;

import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.jingrui.jrap.order.dto.Guarantor;
import com.jingrui.jrap.order.service.IGuarantorService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class GuarantorServiceImpl extends BaseServiceImpl<Guarantor> implements IGuarantorService{

}