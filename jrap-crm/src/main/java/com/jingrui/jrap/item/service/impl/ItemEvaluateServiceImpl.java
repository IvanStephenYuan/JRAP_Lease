package com.jingrui.jrap.item.service.impl;

import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.jingrui.jrap.item.dto.ItemEvaluate;
import com.jingrui.jrap.item.service.IItemEvaluateService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class ItemEvaluateServiceImpl extends BaseServiceImpl<ItemEvaluate> implements IItemEvaluateService{

}