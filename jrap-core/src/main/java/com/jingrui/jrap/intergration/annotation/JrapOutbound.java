/*
 * #{copyright}#
 */

package com.jingrui.jrap.intergration.annotation;

import java.lang.annotation.*;

/**
 * 出站请求引用此注解
 * @author xiangyu.qi@jingrui.com
 */
@Inherited
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface JrapOutbound {

    String apiName() default "";

    String sysName() default "";
}