/*
 * *
 *  @file com.maddyhome.idea.copyright.pattern.JavaCopyrightVariablesProvider$1@7c4e9811$
 *  @CopyRight (C) 2018 ZheJiangJingRui Co. Ltd.
 *  @brief JingRui Application Platform
 *  @author $name$
 *  @email yulong.yuan@jr-info.cn
 *  @date $date$
 * /
 */

package com.jingrui.jrap.product.calculate.impl;

import com.jingrui.jrap.product.calculate.ProductCalculate;
import com.jingrui.jrap.product.dto.ProductCalculateHead;
import com.jingrui.jrap.product.dto.ProductCalculateLine;
import com.jingrui.jrap.product.dto.ProductConfig;
import com.jingrui.jrap.product.calculate.IProductCalculate;
import com.jingrui.jrap.product.dto.ProductFormula;
import com.jingrui.jrap.product.service.IProductFormulaService;
import com.jingrui.jrap.product.service.impl.ProductFormulaServiceImpl;
import com.singularsys.jep.Jep;
import com.singularsys.jep.JepException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class ProductECICalculateImp extends ProductCalculate implements IProductCalculate {
    /**
     * 等额本息计算
     *
     * @return
     */
    @Override
    public List<ProductCalculateLine> calculate(List<ProductConfig> configs) {
        List<ProductCalculateLine> calculateLines = new ArrayList<>();
        this.loadConfigMap(configs);
        Map map = this.getMap();

        System.out.println(map.toString());
        return calculateLines;
    }
}
