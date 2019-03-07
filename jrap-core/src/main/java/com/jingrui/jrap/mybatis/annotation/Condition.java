/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2016 abel533@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.jingrui.jrap.mybatis.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * where condition configuration.
 *
 * @author jessen
 * @since 2015-05-30 19:07
 */
@Target({ ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Condition {

    /**
     * compare operation.
     * <p>
     * =<br>
     * &gt;<br>
     * &lt;<br>
     * &gt;=<br>
     * &lt;=<br>
     * LIKE<br>
     * &lt;&gt;<br>
     * != <p>
     * default =
     */
    String operator() default "=";

    /**
     * only work when operator=LIKE.
     * <p>
     * true : generate expression : xx LIKE concat('%',concat(#{yy},'%'))<br>
     * false : generate expression : xx LIKE #{yy}<br>
     * default true
     */
    boolean autoWrap() default true;

    /**
     * don't use this field as condition.
     * 
     * default false
     */
    boolean exclude() default false;
}
