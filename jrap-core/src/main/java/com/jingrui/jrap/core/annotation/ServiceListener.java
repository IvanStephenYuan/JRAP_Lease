/*
 * Copyright ZheJiang JingRui Co.,Ltd.
 */

package com.jingrui.jrap.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.jingrui.jrap.extensible.base.IServiceListener;

/**
 * 多语言特性注解.
 * 
 * @author shengyang.zhou@jingrui.com
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ServiceListener {
    Class<?> target();

    Class<? extends IServiceListener>[] before() default {};

    Class<? extends IServiceListener>[] after() default {};
}