package com.jingrui.jrap.item.service.impl;

import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.jingrui.jrap.item.dto.PurchaseAssign;
import com.jingrui.jrap.item.service.IPurchaseAssignService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class PurchaseAssignServiceImpl extends BaseServiceImpl<PurchaseAssign> implements IPurchaseAssignService{

}