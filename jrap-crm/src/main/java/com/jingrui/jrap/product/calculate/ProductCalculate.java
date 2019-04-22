/*
 * *
 *  @file com.maddyhome.idea.copyright.pattern.JavaCopyrightVariablesProvider$1@bf6350e$
 *  @CopyRight (C) 2018 ZheJiangJingRui Co. Ltd.
 *  @brief JingRui Application Platform
 *  @author $name$
 *  @email yulong.yuan@jr-info.cn
 *  @date $date$
 * /
 */

package com.jingrui.jrap.product.calculate;

import com.jingrui.jrap.product.controllers.ProductController;
import com.jingrui.jrap.product.dto.ProductCalculateHead;
import com.jingrui.jrap.product.dto.ProductConfig;
import com.jingrui.jrap.product.dto.ProductFormula;
import com.jingrui.jrap.product.service.IProductFormulaService;
import com.singularsys.jep.Jep;
import com.singularsys.jep.JepException;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;


public class ProductCalculate {
    @Autowired
    private IProductFormulaService formulaService;
    private ProductCalculateHead calculateHead;
    private Map<String, Object> map;
    private Jep jep;

    static final Logger logger = LoggerFactory.getLogger(ProductCalculate.class);

    public ProductCalculate() {
        jep = new Jep();
        map = new HashMap<String, Object>();
    }

    private Jep getJep(Map map) throws JepException {
        Set<Map.Entry> set = map.entrySet();

        for (Map.Entry entry : set) {
            Object entryValue = entry.getValue();
            String entryKey = (String) entry.getKey();
            jep.addVariable(entryKey, entryValue);
        }
        return this.jep;
    }

    public void loadConfigMap(List<ProductConfig> configs) {
        this.map = new HashMap<String, Object>();
        for (ProductConfig config : configs) {
            List<ProductFormula> formulas = formulaService.selectByConfigId(config.getConfigId());
            try {
                this.jep = getJep(this.map);
                if (formulas.size() > 0) {
                    for (ProductFormula formula : formulas) {
                        this.jep.parse(formula.getCalcFormula());
                        Object result = jep.evaluate();
                        map.put(config.getColumnCode(), result);
                    }
                } else {
                    map.put(config.getColumnCode(), config.getDefaultValue());
                }
            }catch(JepException e){
                logger.error("注入参数错误：" + e.getMessage());
            }
        }
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public Jep getJep() {
        return jep;
    }
}
