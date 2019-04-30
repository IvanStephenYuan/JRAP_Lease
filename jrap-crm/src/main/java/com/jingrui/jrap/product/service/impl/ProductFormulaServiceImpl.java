package com.jingrui.jrap.product.service.impl;

import com.jingrui.jrap.product.mapper.ProductFormulaMapper;
import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jingrui.jrap.product.dto.ProductFormula;
import com.jingrui.jrap.product.service.IProductFormulaService;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class ProductFormulaServiceImpl extends BaseServiceImpl<ProductFormula> implements IProductFormulaService{
    @Autowired
    private ProductFormulaMapper productFormulaMapper;

    @Override
    protected boolean useSelectiveUpdate() {
        return false;
    }

    @Override
    public List<ProductFormula> selectByConfigId(Long configId){
        List<ProductFormula> formulas = productFormulaMapper.selectByConfigId(configId);
        return formulas;
    };
}