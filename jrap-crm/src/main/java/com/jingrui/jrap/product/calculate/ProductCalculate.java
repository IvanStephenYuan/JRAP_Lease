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

import com.jingrui.jrap.product.calculate.impl.ProductECICalculateImp;
import com.jingrui.jrap.product.dto.ProductCalculateHead;
import com.jingrui.jrap.product.dto.ProductConfig;
import com.jingrui.jrap.product.dto.ProductFormula;
import com.jingrui.jrap.product.service.IProductFormulaService;
import com.jingrui.jrap.product.util.MethodReflectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.lang.reflect.Method;
import java.util.*;
import java.text.SimpleDateFormat;


public class ProductCalculate {
    static final Logger logger = LoggerFactory.getLogger(ProductECICalculateImp.class);
    protected ProductCalculateHead calculateHead;
    protected Map<String, Object> map;

    public ProductCalculate() {
        map = new HashMap<String, Object>();
    }


    /**
     * map put
     * @param key
     * @param value
     * @param type
     */
    public void mapPut(String key, String value, String type){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        if("NUMBER".equalsIgnoreCase(type)){
            map.put(key, Double.valueOf(value));
        }else if("STRING".equalsIgnoreCase(type)){
            map.put(key, value);
        }else if("DATE".equalsIgnoreCase(type)) {
            try {
                map.put(key, sdf.parse(value));
            }catch (java.text.ParseException e){
                map.put(key, null);
                logger.error(e.getMessage());
            }
        }
    }

    /**
     * 将config插入map
     * @param configs
     */
    public void convertConfigMap(List<ProductConfig> configs) {
        WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
        IProductFormulaService productFormulaService = (IProductFormulaService) wac.getBean("productFormulaServiceImpl");

        for (ProductConfig config : configs) {
            List<ProductFormula> formulas = productFormulaService.selectByConfigId(config.getConfigId());

            try {
                if (formulas.size() > 0) {
                    for (ProductFormula formula : formulas) {
                        String expression = formula.getCalcFormula();
                        System.out.println(config.getColumnCode() + "=" +expression);
                        Object result = null;

                        //判断是否是函数，如果是调用函数
                        try {
                            Class<?> clazz = Class.forName(MethodReflectUtil.EXCEL_UTIL);
                            Method[] methods = clazz.getDeclaredMethods();
                            for (Method method : methods) {
                                if (expression.indexOf(method.getName()) >= 0) {
                                    result = invoke(expression, map);
                                    break;
                                }
                            }
                        }catch (Exception e) {
                            e.printStackTrace();
                        }

                        if(result != null){
                            mapPut(config.getColumnCode(), result.toString(), config.getDataType());
                        }else{
                            result = eval(expression, map);
                            mapPut(config.getColumnCode(), result.toString(), config.getDataType());
                        }
                    }
                } else if (config.getDefaultValue() != null) {
                    mapPut(config.getColumnCode(), config.getDefaultValue(), config.getDataType());
                }
            } catch (ScriptException e) {
                logger.error("执行Script错误：" + e.getMessage());
            }
        }
    }

    /**
     *
     * <p>执行字符串计算</p>
     * @param express
     * @param params
     * @return
     * @throws ScriptException
     */
    public <T, E> E eval(String express, Map<String, T> params) throws ScriptException{
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("js");
        if(params == null){
            params = new HashMap<String,T>();
        }
        Iterator<Map.Entry<String, T>> iter = params.entrySet().iterator();
        Map.Entry<String, T> entry = null;
        while(iter.hasNext()){
            entry = iter.next();
            engine.put(entry.getKey(), entry.getValue());
        }
        E result =  (E)engine.eval(express);
        return result;
    }

    /**
     * 调用方法
     * @param expression
     * @return
     */
    public Object invoke(String expression, Map<String, Object> map){
        String method = expression.substring(0, expression.indexOf("("));
        String paramStr = expression.substring(expression.indexOf("(")+1, expression.indexOf(")"));

        String[] paramS = paramStr.split(",");
        Object[] paramR = new Object[paramS.length];

        Set<Map.Entry<String, Object>> set = map.entrySet();
        for(int i=0; i<paramS.length; i++){
            for (Map.Entry entry : set) {
                Object entryValue = entry.getValue();
                String entryKey = (String) entry.getKey();

                if(entryKey.equalsIgnoreCase(paramS[i].trim())){
                    paramR[i] = entryValue;
                    break;
                }
            }

            if(paramR[i]  == null){
                paramR[i] = paramS[i].trim();
            }
        }

        Object value = MethodReflectUtil.invoke(MethodReflectUtil.EXCEL_UTIL, method, paramR);
        return value;
    }

    public Map<String, Object> getMap() {
        return map;
    }
}
