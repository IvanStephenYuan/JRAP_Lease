/**
 * Copyright 2016 www.extdo.com 
 */
package com.jingrui.jrap.mybatis.common.query;


import com.jingrui.jrap.system.dto.BaseDTO;

import javax.persistence.criteria.JoinType;
import java.lang.annotation.*;

/**
 * @author njq.niu@jingrui.com
 */
@Repeatable(JoinTables.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface JoinTable {
    
    String name();
    
    Class<?> target();

    
    JoinType type() default JoinType.INNER;
    
    JoinOn[] on() default {};

    boolean joinMultiLanguageTable() default false;
}
