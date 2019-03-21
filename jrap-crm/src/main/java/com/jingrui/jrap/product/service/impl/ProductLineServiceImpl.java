package com.jingrui.jrap.product.service.impl;

import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.jingrui.jrap.product.dto.ProductLine;
import com.jingrui.jrap.product.service.IProductLineService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class ProductLineServiceImpl extends BaseServiceImpl<ProductLine> implements IProductLineService{

}