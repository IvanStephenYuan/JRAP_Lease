package com.jingrui.jrap.item.service.impl;

import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.jingrui.jrap.item.dto.Warehouse;
import com.jingrui.jrap.item.service.IWarehouseService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class WarehouseServiceImpl extends BaseServiceImpl<Warehouse> implements IWarehouseService{

}