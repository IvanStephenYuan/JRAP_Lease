/*
 * *
 *  @file com.maddyhome.idea.copyright.pattern.JavaCopyrightVariablesProvider$1@76417bdd$
 *  @CopyRight (C) 2018 ZheJiangJingRui Co. Ltd.
 *  @brief JingRui Application Platform
 *  @author $name$
 *  @email yulong.yuan@jr-info.cn
 *  @date $date$
 * /
 */

package com.jingrui.jrap.product.calculate.impl;

import com.jingrui.jrap.product.calculate.IProductCalculate;
import com.jingrui.jrap.product.calculate.ProductCalculate;
import com.jingrui.jrap.product.dto.ProductCalculateLine;
import com.jingrui.jrap.product.dto.ProductConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ProductECICalculateImp extends ProductCalculate implements IProductCalculate {
    /**
     * 等额本息计算
     *
     * @return
     */
    @Override
    public List<ProductCalculateLine> calculate(List<ProductConfig> configs) {
        List<ProductCalculateLine> calculateLines = new ArrayList<>();
        super.convertConfigMap(configs);
        Map map = this.getMap();

        //计算头数据
        for(ProductConfig config : configs){
            Set<Map.Entry<String, Object>> set = map.entrySet();
            for (Map.Entry entry : set) {
                Object entryValue = entry.getValue();
                String entryKey = (String) entry.getKey();

                if(entryKey.equalsIgnoreCase(config.getColumnCode())){
                    config.setDefaultValue(String.valueOf(entryValue));
                    break;
                }
            }
        }

        //计算行数据


        return calculateLines;
    }
}
