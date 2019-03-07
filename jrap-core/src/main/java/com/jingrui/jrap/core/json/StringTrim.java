package com.jingrui.jrap.core.json;

import java.lang.annotation.*;

/**
 * @author peng.jiang@jingrui.com
 * @date 2018/1/16
 **/

@Inherited
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface StringTrim {
}
