package com.jingrui.jrap.product.service.impl;

import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.jingrui.jrap.product.dto.ProductFormula;
import com.jingrui.jrap.product.service.IProductFormulaService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class ProductFormulaServiceImpl extends BaseServiceImpl<ProductFormula> implements IProductFormulaService{
    @Override
    protected boolean useSelectiveUpdate() {
        return false;
    }
}