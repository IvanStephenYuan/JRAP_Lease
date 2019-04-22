package com.jingrui.jrap.product.service;

import com.jingrui.jrap.core.ProxySelf;
import com.jingrui.jrap.system.service.IBaseService;
import com.jingrui.jrap.product.dto.ProductFormula;
import java.util.List;

public interface IProductFormulaService extends IBaseService<ProductFormula>, ProxySelf<IProductFormulaService>{
    /**
     * 根据配置查询表达式
     * @param configId
     * @return
     */
    List<ProductFormula> selectByConfigId(Long configId);
}