package com.jingrui.jrap.product.service.impl;

import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.jingrui.jrap.product.dto.ProductConfig;
import com.jingrui.jrap.product.service.IProductConfigService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class ProductConfigServiceImpl extends BaseServiceImpl<ProductConfig> implements IProductConfigService{
    @Override
    protected boolean useSelectiveUpdate() {
        return false;
    }
}