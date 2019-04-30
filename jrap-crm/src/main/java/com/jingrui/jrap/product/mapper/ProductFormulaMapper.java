package com.jingrui.jrap.product.mapper;

import com.jingrui.jrap.mybatis.common.Mapper;
import com.jingrui.jrap.product.dto.ProductFormula;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductFormulaMapper extends Mapper<ProductFormula>{
    /**
     * 根据配置文件查询
     * @param configId
     * @return
     */
    public List<ProductFormula> selectByConfigId(@Param("configId") Long configId);
}