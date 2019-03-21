package com.jingrui.jrap.product.service.impl;

import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.jingrui.jrap.product.dto.ItemModel;
import com.jingrui.jrap.product.service.IItemModelService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class ItemModelServiceImpl extends BaseServiceImpl<ItemModel> implements IItemModelService{

}