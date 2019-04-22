/*
 * *
 *  @file com.maddyhome.idea.copyright.pattern.JavaCopyrightVariablesProvider$1@2fc0908$
 *  @CopyRight (C) 2018 ZheJiangJingRui Co. Ltd.
 *  @brief JingRui Application Platform
 *  @author $name$
 *  @email yulong.yuan@jr-info.cn
 *  @date $date$
 * /
 */

package com.jingrui.jrap.product.calculate;

import com.jingrui.jrap.product.dto.ProductCalculateLine;
import com.jingrui.jrap.product.dto.ProductConfig;

import java.util.List;

public interface IProductCalculate {
    /**
     * 产品计算器
     * @return
     */
    public List<ProductCalculateLine> calculate(List<ProductConfig> configs);
}
