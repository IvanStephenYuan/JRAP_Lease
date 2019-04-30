/*
 * *
 *  @file com.maddyhome.idea.copyright.pattern.JavaCopyrightVariablesProvider$1@587b1826$
 *  @CopyRight (C) 2018 ZheJiangJingRui Co. Ltd.
 *  @brief JingRui Application Platform
 *  @author $name$
 *  @email yulong.yuan@jr-info.cn
 *  @date $date$
 * /
 */

package com.jingrui.jrap.product.util;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;

public class MethodReflectUtil {
    public static final String EXCEL_UTIL = "com.jingrui.jrap.product.util.ExcelUtil";

    public static Object invoke(String clazzName, String methodName, Object[] params) {
        try {
            Class<?> clazz = Class.forName(clazzName);
            Object obj = clazz.newInstance();
            Method[] methods = clazz.getDeclaredMethods();
            Method callMethod = null;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


            for (Method method : methods) {
                if (method.getName().equals(methodName) && method.getParameterCount() == params.length) {
                    callMethod = method;
                    break;
                }
            }

            if (callMethod != null) {
                //根据函数转参数
                Object[] methodParams = new Object[params.length];
                Class<?>[] paraTypes = callMethod.getParameterTypes();
                for (int i = 0; i < paraTypes.length; i++) {
                    Object paraType = paraTypes[i];
                    Object para = params[i];

                    if ("double".equalsIgnoreCase(((Class) paraType).getName())) {
                        methodParams[i] = Double.valueOf(String.valueOf(para));
                    } else if ("int".equalsIgnoreCase(((Class) paraType).getName())) {
                        methodParams[i] = Float.valueOf(String.valueOf(para)).intValue();
                    } else if ("date".equalsIgnoreCase(((Class) paraType).getName())) {
                        methodParams[i] = sdf.parse(String.valueOf(para));
                    } else {
                        methodParams[i] = para;
                    }

                }

                callMethod.setAccessible(true);
                return callMethod.invoke(obj, methodParams);
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        String expression = "pmt(7.2,12,12,1000000)";
        String method = expression.substring(0, expression.indexOf("("));
        String parm = expression.substring(expression.indexOf("(")+1, expression.indexOf(")"));
        String [] paras =parm.split(",");
        Object[] params = new Object[3];
        params[0] = (Double)7.2;
        params[1] = (int)12;
        params[2] = (Double)10000.0;

        Object value = MethodReflectUtil.invoke(MethodReflectUtil.EXCEL_UTIL, method, params);
        System.out.println(value.toString());
    }
}
