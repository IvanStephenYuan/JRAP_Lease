/*
 * #{copyright}#
 */

package com.jingrui.jrap.intergration.annotation;

import java.lang.annotation.*;

/**
 * 入站请求引用此注解
 * @author xiangyu.qi@jingrui.com
 */
@Inherited
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface JrapInbound {

    String apiName() default "";
}