/**
 * Copyright 2016 www.extdo.com 
 */
package com.jingrui.jrap.mybatis.common.query;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author njq.niu@jingrui.com
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface JoinOn {

    String joinField();

    String joinExpression() default "";
}
