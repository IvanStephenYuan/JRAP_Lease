/*
 * *
 *  @file com.maddyhome.idea.copyright.pattern.JavaCopyrightVariablesProvider$1@10586072$
 *  @CopyRight (C) 2018 ZheJiangJingRui Co. Ltd.
 *  @brief JingRui Application Platform
 *  @author $name$
 *  @email yulong.yuan@jr-info.cn
 *  @date $date$
 * /
 */

package com.jingrui.jrap;

import com.singularsys.jep.Jep;
import com.singularsys.jep.JepException;

public class SimpleExample {
    public static void main(String[] args) {
        Jep jep = new Jep(); //一个数学表达式
        String exp = "((a+b)*(c+b))/(c+a)/b"; //给变量赋值

        try { //执行
            jep.addVariable("a", 10);
            jep.addVariable("b", 10);
            jep.addVariable("c", 10);

            jep.parse(exp);
            Object result = jep.evaluate();
            System.out.println("计算结果： " + result);
        } catch (JepException e) {
            System.out.println("An error occured: " + e.getMessage());
        }
    }
}
