/*
 * *
 *  @file com.maddyhome.idea.copyright.pattern.JavaCopyrightVariablesProvider$1@40fdc20d$
 *  @CopyRight (C) 2018 ZheJiangJingRui Co. Ltd.
 *  @brief JingRui Application Platform
 *  @author $name$
 *  @email yulong.yuan@jr-info.cn
 *  @date $date$
 * /
 */

package com.jingrui.jrap;

import java.util.*;
import javax.script.ScriptException;
import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;


public class SimpleExample {
    /**
     *
     * <p>执行字符串计算</p>
     * @param express
     * @param params
     * @return
     * @throws ScriptException
     */
    public static <T, E> E eval(String express, Map<String, T> params){
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
        E result = null;
        try {
            result = (E)engine.eval(express);
        } catch (ScriptException e) {
            System.out.println("表达式执行异常： " + e.getMessage());
        }
        return result;
    }

    /**
     * 解析字符串， 并将其当作表达式执行
     * @param express
     * @param params
     * @return
     * @throws ScriptException
     */
    public static <T> Boolean evalBoolean(String express, Map<String, T> params) {
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
        Boolean result = null;
        try {
            result = (Boolean)engine.eval(express);
        } catch (ScriptException e) {
            result = false;
            System.out.println("表达式执行异常： " + e.getMessage());
        }
        return result;
    }

    public static void main(String args[]){
        String express = "if(H1>H2){H1}else{H2}";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("H1", 0);
        map.put("H2", 1);
        Object result = SimpleExample.eval(express, map);
        System.out.println(result);
    }
}
