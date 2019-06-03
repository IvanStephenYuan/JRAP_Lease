package com.jingrui.jrap.item.service.impl;

import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.jingrui.jrap.item.dto.ItemAllocate;
import com.jingrui.jrap.item.service.IItemAllocateService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class ItemAllocateServiceImpl extends BaseServiceImpl<ItemAllocate> implements IItemAllocateService{

}