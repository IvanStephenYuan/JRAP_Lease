package com.jingrui.jrap.product.service.impl;

import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.jingrui.jrap.product.dto.Product;
import com.jingrui.jrap.product.service.IProductService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class ProductServiceImpl extends BaseServiceImpl<Product> implements IProductService{
    @Override
    protected boolean useSelectiveUpdate() {
        return false;
    }
}