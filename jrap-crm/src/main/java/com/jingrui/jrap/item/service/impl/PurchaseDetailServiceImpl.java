package com.jingrui.jrap.item.service.impl;

import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.jingrui.jrap.item.dto.PurchaseDetail;
import com.jingrui.jrap.item.service.IPurchaseDetailService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class PurchaseDetailServiceImpl extends BaseServiceImpl<PurchaseDetail> implements IPurchaseDetailService{

}