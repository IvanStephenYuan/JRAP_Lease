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
import com.jingrui.jrap.mybatis.common.Criteria;
import com.jingrui.jrap.mybatis.mapperhelper.EntityHelper;
import com.jingrui.jrap.mybatis.mapperhelper.MapperHelper;
import com.jingrui.jrap.mybatis.mapperhelper.MapperTemplate;
import com.jingrui.jrap.mybatis.mapperhelper.SqlHelper;
import com.jingrui.jrap.system.dto.BaseDTO;
import org.apache.ibatis.mapping.MappedStatement;

import java.util.Map;

/**
 * BaseSelectProvider实现类，基础方法实现类
 *
 * @author liuzh
 */
public class BaseSelectProvider extends MapperTemplate {

    public BaseSelectProvider() {}

    public BaseSelectProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    /**
     * 查询
     *
     * @param ms
     * @return
     */
    public String selectOne(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        boolean isMl = EntityHelper.getEntityTable(entityClass).isSupportMultiLanguage();
        //修改返回值类型为实体类型
        setResultType(ms, entityClass);
        StringBuilder sql = new StringBuilder();
        if (isMl) {
            sql.append(SqlHelper.selectAllColumns_TL(entityClass));
            sql.append(SqlHelper.fromTable_TL(entityClass, tableName(entityClass)));
            sql.append(SqlHelper.whereAllIfColumns_TL(entityClass, isNotEmpty(), true));
        } else {
            sql.append(SqlHelper.selectAllColumns(entityClass));
            sql.append(SqlHelper.fromTable(entityClass, tableName(entityClass)));
            sql.append(SqlHelper.whereAllIfColumns(entityClass, isNotEmpty()));
        }
        return sql.toString();
    }

    /**
     * 查询
     *
     * @param ms
     * @return
     */
    public String select(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);

        boolean isMl = EntityHelper.getEntityTable(entityClass).isSupportMultiLanguage();

        //修改返回值类型为实体类型
        setResultType(ms, entityClass);
        StringBuilder sql = new StringBuilder();
        if (isMl) {
            sql.append(SqlHelper.selectAllColumns_TL(entityClass));
            sql.append(SqlHelper.fromTable_TL(entityClass, tableName(entityClass)));
            sql.append(SqlHelper.whereAllIfColumns_TL(entityClass, isNotEmpty(), false));
            sql.append(SqlHelper.orderByDefault_TL(entityClass));
        } else {
            sql.append(SqlHelper.selectAllColumns(entityClass));
            sql.append(SqlHelper.fromTable(entityClass, tableName(entityClass)));
            sql.append(SqlHelper.whereAllIfColumns(entityClass, isNotEmpty()));
            sql.append(SqlHelper.orderByDefault(entityClass));
        }
        return sql.toString();
    }

    /**
     * 查询
     *
     * @param ms
     * @return
     */
    public String selectByRowBounds(MappedStatement ms) {
        return select(ms);
    }

    /**
     * 根据主键进行查询
     *
     * @param ms
     */
    public String selectByPrimaryKey(MappedStatement ms) {
        final Class<?> entityClass = getEntityClass(ms);

        boolean isMl = EntityHelper.getEntityTable(entityClass).isSupportMultiLanguage();

        //将返回值修改为实体类型
        setResultType(ms, entityClass);
        StringBuilder sql = new StringBuilder();
        if (isMl) {
            sql.append(SqlHelper.selectAllColumns_TL(entityClass));
            sql.append(SqlHelper.fromTable_TL(entityClass, tableName(entityClass)));
            sql.append(SqlHelper.wherePKColumns_TL(entityClass));
        } else {
            sql.append(SqlHelper.selectAllColumns(entityClass));
            sql.append(SqlHelper.fromTable(entityClass, tableName(entityClass)));
            sql.append(SqlHelper.wherePKColumns(entityClass));
        }
        return sql.toString();
    }

    /**
     * 查询总数
     *
     * @param ms
     * @return
     */
    public String selectCount(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.selectCount(entityClass));
        sql.append(SqlHelper.fromTable(entityClass, tableName(entityClass)));
        sql.append(SqlHelper.whereAllIfColumns(entityClass, isNotEmpty()));
        return sql.toString();
    }

    /**
     * 查询全部结果
     *
     * @param ms
     * @return
     */
    public String selectAll(MappedStatement ms) {
        final Class<?> entityClass = getEntityClass(ms);
        boolean isMl = EntityHelper.getEntityTable(entityClass).isSupportMultiLanguage();
        return selectAllResult(ms, isMl);
    }

    private String selectAllResult(MappedStatement ms, Boolean isMultiLanguage) {
        final Class<?> entityClass = getEntityClass(ms);
        //修改返回值类型为实体类型
        setResultType(ms, entityClass);
        StringBuilder sql = new StringBuilder();
        if (isMultiLanguage) {
            sql.append(SqlHelper.selectAllColumns_TL(entityClass));
            sql.append(SqlHelper.fromTable_TL(entityClass, tableName(entityClass)));
            sql.append(SqlHelper.orderByDefault_TL(entityClass));
        } else {
            sql.append(SqlHelper.selectAllColumns(entityClass));
            sql.append(SqlHelper.fromTable(entityClass, tableName(entityClass)));
            sql.append(SqlHelper.orderByDefault(entityClass));
        }
        return sql.toString();
    }


    public String selectAllWithoutMultiLanguage(MappedStatement ms) {
        return selectAllResult(ms, false);
    }


    private void initResultType(MappedStatement ms){
        Class<?> entityClass = getEntityClass(ms);
        setResultType(ms, entityClass);
    }

    public void selectOptions(MappedStatement ms) {
        initResultType(ms);
    }

    public void selectOptionsByPrimaryKey(MappedStatement ms) {
        initResultType(ms);
    }

    /**
     * 按照主键查询SQL.
     *
     * @param dto record
     * @return sql
     */
    public String selectOptionsByPrimaryKey(BaseDTO dto) {
        return SqlHelper.buildSelectByPrimaryKeySQL(dto);
    }


    /**
     * 动态查询SQL.
     *
     * @param parameter parameter
     * @return sql
     */
    public String selectOptions(Map<String,Object> parameter) {
        BaseDTO dto = (BaseDTO)parameter.get(BaseConstants.OPTIONS_DTO);
        Criteria criteria = (Criteria)parameter.get(BaseConstants.OPTIONS_CRITERIA);
        return SqlHelper.buildSelectSelectiveSql(dto, criteria);
    }
}
