package com.jingrui.jrap.plm.service.impl;

import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.jingrui.jrap.plm.dto.SettleDetail;
import com.jingrui.jrap.plm.service.ISettleDetailService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class SettleDetailServiceImpl extends BaseServiceImpl<SettleDetail> implements ISettleDetailService{

}