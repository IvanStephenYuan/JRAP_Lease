/**
 * Copyright 2016 www.extdo.com 
 */
package com.jingrui.jrap.mybatis.common.query;

import com.jingrui.jrap.mybatis.common.query.Comparison;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author njq.niu@jingrui.com
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Where {
    
    Comparison comparison() default Comparison.EQUAL;
    
    String expression() default "";
}
