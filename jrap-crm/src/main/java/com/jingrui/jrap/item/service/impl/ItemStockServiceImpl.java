package com.jingrui.jrap.item.service.impl;

import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.jingrui.jrap.item.dto.ItemStock;
import com.jingrui.jrap.item.service.IItemStockService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class ItemStockServiceImpl extends BaseServiceImpl<ItemStock> implements IItemStockService{

}