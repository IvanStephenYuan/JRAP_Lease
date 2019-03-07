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

package com.jingrui.jrap.mybatis.provider.base;

import com.jingrui.jrap.core.BaseConstants;
import org.apache.ibatis.mapping.MappedStatement;

import com.jingrui.jrap.mybatis.mapperhelper.MapperHelper;
import com.jingrui.jrap.mybatis.mapperhelper.MapperTemplate;
import com.jingrui.jrap.mybatis.mapperhelper.SqlHelper;
import com.jingrui.jrap.system.dto.BaseDTO;

/**
 * BaseUpdateProvider实现类，基础方法实现类
 *
 * @author liuzh
 */
public class BaseUpdateProvider extends MapperTemplate {

    public BaseUpdateProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    /**
     * 通过主键更新全部字段
     *
     * @param ms
     */
    public String updateByPrimaryKey(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.updateTable(entityClass, tableName(entityClass)));
        sql.append(SqlHelper.updateSetColumns(entityClass, null, false, false));
        sql.append(SqlHelper.wherePKColumns(entityClass));
        appendObjectVersionNumber(sql, entityClass);
        return sql.toString();
    }

    /**
     * 通过主键更新不为null的字段
     *
     * @param ms
     * @return
     */
    public String updateByPrimaryKeySelective(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.updateTable(entityClass, tableName(entityClass)));
        sql.append(SqlHelper.updateSetColumns(entityClass, null, true, isNotEmpty()));
        sql.append(SqlHelper.wherePKColumns(entityClass));
        appendObjectVersionNumber(sql, entityClass);
        return sql.toString();
    }

    /*
    * 通过options选取更新字段并根据主键更新。
    * */
    public String updateByPrimaryKeyOptions(MappedStatement ms){
        Class<?> entityClass = getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.updateTable(entityClass, tableName(entityClass)));
        sql.append(SqlHelper.updateSetColumnsWithOption(entityClass));
        sql.append(SqlHelper.wherePKColumns(entityClass, BaseConstants.OPTIONS_DTO));
        appendObjectVersionNumberWithOptions(sql, entityClass);
        return sql.toString();
    }

    public static void appendObjectVersionNumber(StringBuilder sb, Class<?> entityClass) {
        if (!(BaseDTO.class.isAssignableFrom(entityClass))) {
            return;
        }
        sb.append("<if test=\"objectVersionNumber!=null\">");
        sb.append(" AND OBJECT_VERSION_NUMBER=#{objectVersionNumber,jdbcType=DECIMAL}");
        sb.append("</if>");
    }

    public static void appendObjectVersionNumberWithOptions(StringBuilder sb, Class<?> entityClass) {
        if (!(BaseDTO.class.isAssignableFrom(entityClass))) {
            return;
        }
        sb.append("<if test=\"dto.objectVersionNumber!=null\">");
        sb.append(" AND OBJECT_VERSION_NUMBER=#{dto.objectVersionNumber,jdbcType=DECIMAL}");
        sb.append("</if>");
    }
}
