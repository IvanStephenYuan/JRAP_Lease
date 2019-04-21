package com.jingrui.jrap.product.mapper;

import com.jingrui.jrap.mybatis.common.Mapper;
import com.jingrui.jrap.product.dto.Product;
import java.util.List;

public interface ProductMapper extends Mapper<Product>{
    /**
     * 自定义产品查询
     * @param product
     * @return
     */
    List<Product> selectProduct(Product product);
}