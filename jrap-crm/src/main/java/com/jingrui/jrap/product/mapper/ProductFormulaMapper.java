package com.jingrui.jrap.product.mapper;

import com.jingrui.jrap.mybatis.common.Mapper;
import com.jingrui.jrap.product.dto.ProductFormula;

import java.util.List;

public interface ProductFormulaMapper extends Mapper<ProductFormula>{
    List<ProductFormula> selectByConfigId(Long configId);
}