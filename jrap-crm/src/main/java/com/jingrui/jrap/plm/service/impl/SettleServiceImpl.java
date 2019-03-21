package com.jingrui.jrap.plm.service.impl;

import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.jingrui.jrap.plm.dto.Settle;
import com.jingrui.jrap.plm.service.ISettleService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class SettleServiceImpl extends BaseServiceImpl<Settle> implements ISettleService{

}