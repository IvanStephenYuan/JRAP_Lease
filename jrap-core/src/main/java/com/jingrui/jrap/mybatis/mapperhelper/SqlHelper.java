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

package com.jingrui.jrap.mybatis.mapperhelper;

import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.Table;
import javax.persistence.criteria.JoinType;

import com.jingrui.jrap.core.BaseConstants;
import com.jingrui.jrap.mybatis.annotation.Condition;
import com.jingrui.jrap.mybatis.common.Criteria;
import com.jingrui.jrap.mybatis.common.query.*;
import com.jingrui.jrap.mybatis.entity.EntityColumn;
import com.jingrui.jrap.mybatis.entity.EntityField;
import com.jingrui.jrap.mybatis.entity.EntityTable;
import com.jingrui.jrap.mybatis.entity.IDynamicTableName;
import com.jingrui.jrap.mybatis.util.StringUtil;
import com.jingrui.jrap.system.dto.BaseDTO;
import com.jingrui.jrap.system.dto.DTOClassInfo;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.jdbc.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 拼常用SQL的工具类
 *
 * @author liuzh
 * @since 2015-11-03 22:40
 */
public class SqlHelper {

    private static Logger logger = LoggerFactory.getLogger(SqlHelper.class);

    /**
     * 获取表名 - 支持动态表名
     *
     * @param entityClass
     * @param tableName
     * @return
     */
    public static String getDynamicTableName(Class<?> entityClass, String tableName) {
        if (IDynamicTableName.class.isAssignableFrom(entityClass)) {
            return "<if test=\"@com.jingrui.jrap.mybatis.util.OGNL@isDynamicParameter(_parameter) and dynamicTableName != null and dynamicTableName != ''\">\n"
                    + "${dynamicTableName}\n" + "</if>\n"
                    + "<if test=\"@com.jingrui.jrap.mybatis.util.OGNL@isNotDynamicParameter(_parameter) or dynamicTableName == null or dynamicTableName == ''\">\n"
                    + tableName + "\n" + "</if>";
        } else {
            return tableName;
        }
    }

    /**
     * 获取表名 - 支持动态表名，该方法用于多个入参时，通过parameterName指定入参中实体类的@Param的注解值
     *
     * @param entityClass
     * @param tableName
     * @param parameterName
     * @return
     */
    public static String getDynamicTableName(Class<?> entityClass, String tableName, String parameterName) {
        if (IDynamicTableName.class.isAssignableFrom(entityClass)) {
            if (StringUtil.isNotEmpty(parameterName)) {
                return "<if test=\"@com.jingrui.jrap.mybatis.util.OGNL@isDynamicParameter(" + parameterName + ") and " + parameterName
                        + ".dynamicTableName != null and " + parameterName + ".dynamicTableName != ''\">\n" + "${"
                        + parameterName + ".dynamicTableName}\n" + "</if>\n" + "<if test=\"@com.jingrui.jrap.mybatis.util.OGNL@isNotDynamicParameter("
                        + parameterName + ") or " + parameterName + ".dynamicTableName == null or " + parameterName
                        + ".dynamicTableName == ''\">\n" + tableName + "\n" + "</if>";
            } else {
                return getDynamicTableName(entityClass, tableName);
            }

        } else {
            return tableName;
        }
    }

    /**
     * <bind name="pattern" value="'%' + _parameter.getTitle() + '%'" />
     *
     * @param column
     * @return
     */
    public static String getBindCache(EntityColumn column) {
        StringBuilder sql = new StringBuilder();
        sql.append("<bind name=\"");
        sql.append(column.getProperty()).append("_cache\" ");
        sql.append("value=\"").append(column.getProperty()).append("\"/>");
        return sql.toString();
    }

    /**
     * <bind name="pattern" value="'%' + _parameter.getTitle() + '%'" />
     *
     * @param column
     * @return
     */
    public static String getBindValue(EntityColumn column, String value) {
        StringBuilder sql = new StringBuilder();
        sql.append("<bind name=\"");
        sql.append(column.getProperty()).append("_bind\" ");
        sql.append("value='").append(value).append("'/>");
        return sql.toString();
    }

    /**
     * <bind name="pattern" value="'%' + _parameter.getTitle() + '%'" />
     *
     * @param column
     * @return
     */
    public static String getIfCacheNotNull(EntityColumn column, String contents) {
        StringBuilder sql = new StringBuilder();
        sql.append("<if test=\"").append(column.getProperty()).append("_cache != null\">");
        sql.append(contents);
        sql.append("</if>");
        return sql.toString();
    }

    /**
     * 如果_cache == null
     *
     * @param column
     * @return
     */
    public static String getIfCacheIsNull(EntityColumn column, String contents) {
        StringBuilder sql = new StringBuilder();
        sql.append("<if test=\"").append(column.getProperty()).append("_cache == null\">");
        sql.append(contents);
        sql.append("</if>");
        return sql.toString();
    }

    /**
     * 判断自动!=null的条件结构
     *
     * @param column
     * @param contents
     * @param empty
     * @return
     */
    public static String getIfNotNull(EntityColumn column, String contents, boolean empty) {
        return getIfNotNull(null, column, contents, empty);
    }

    /**
     * 判断自动==null的条件结构
     *
     * @param column
     * @param contents
     * @param empty
     * @return
     */
    public static String getIfIsNull(EntityColumn column, String contents, boolean empty) {
        return getIfIsNull(null, column, contents, empty);
    }

    /**
     * 判断自动!=null的条件结构
     *
     * @param entityName
     * @param column
     * @param contents
     * @param empty
     * @return
     */
    public static String getIfNotNull(String entityName, EntityColumn column, String contents, boolean empty) {
        StringBuilder sql = new StringBuilder();
        sql.append("<if test=\"");
        if (StringUtil.isNotEmpty(entityName)) {
            sql.append(entityName).append(".");
        }
        sql.append(column.getProperty()).append(" != null");
        if (empty && column.getJavaType().equals(String.class)) {
            sql.append(" and ");
            if (StringUtil.isNotEmpty(entityName)) {
                sql.append(entityName).append(".");
            }
            sql.append(column.getProperty()).append(" != '' ");
        }
        sql.append("\">");
        sql.append(contents);
        sql.append("</if>");
        return sql.toString();
    }

    public static String getIfNotNullWithOptions(EntityColumn column, String contents) {
        StringBuilder sql = new StringBuilder();
        sql.append("<if test=\"");
        sql.append("null== criteria || null == criteria.updateFields || criteria.updateFields.isEmpty() || ");
        sql.append("criteria.updateFields").append(".contains('");
        sql.append(column.getProperty()).append("')");
        sql.append("\">");
        sql.append(contents);
        sql.append("</if>");
        return sql.toString();
    }

    /**
     * 判断自动==null的条件结构
     *
     * @param entityName
     * @param column
     * @param contents
     * @param empty
     * @return
     */
    public static String getIfIsNull(String entityName, EntityColumn column, String contents, boolean empty) {
        StringBuilder sql = new StringBuilder();
        sql.append("<if test=\"");
        if (StringUtil.isNotEmpty(entityName)) {
            sql.append(entityName).append(".");
        }
        sql.append(column.getProperty()).append(" == null");
        if (empty && column.getJavaType().equals(String.class)) {
            sql.append(" or ");
            if (StringUtil.isNotEmpty(entityName)) {
                sql.append(entityName).append(".");
            }
            sql.append(column.getProperty()).append(" == '' ");
        }
        sql.append("\">");
        sql.append(contents);
        sql.append("</if>");
        return sql.toString();
    }

    /**
     * 获取所有查询列，如id,name,code...
     *
     * @param entityClass
     * @return
     */
    public static String getAllColumns(Class<?> entityClass) {
        Set<EntityColumn> columnList = EntityHelper.getColumns(entityClass);
        StringBuilder sql = new StringBuilder();
        for (EntityColumn entityColumn : columnList) {
            sql.append(entityColumn.getColumn()).append(",");
        }
        return sql.substring(0, sql.length() - 1);
    }

    /**
     * select xxx,xxx...
     *
     * @param entityClass
     * @return
     */
    public static String selectAllColumns(Class<?> entityClass) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append(getAllColumns(entityClass));
        sql.append(" ");
        return sql.toString();
    }

    // add by jessen
    public static String getAllColumns_TL(Class<?> entityClass) {
        Set<EntityColumn> columnList = EntityHelper.getColumns(entityClass);
        StringBuilder sql = new StringBuilder();
        EntityTable entityTable = EntityHelper.getEntityTable(entityClass);
        for (EntityColumn entityColumn : columnList) {
            if (entityTable.isSupportMultiLanguage()) {
                sql.append(entityColumn.isMultiLanguageField() ? "t." : "b.");
            }
            sql.append(entityColumn.getColumn()).append(",");
        }
        return sql.substring(0, sql.length() - 1);
    }

    public static String selectAllColumns_TL(Class<?> entityClass) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append(getAllColumns_TL(entityClass));
        sql.append(" ");
        return sql.toString();
    }

    /**
     * select count(x)
     *
     * @param entityClass
     * @return
     */
    public static String selectCount(Class<?> entityClass) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        Set<EntityColumn> pkColumns = EntityHelper.getPKColumns(entityClass);
        if (pkColumns.size() == 1) {
            sql.append("COUNT(").append(pkColumns.iterator().next().getColumn()).append(") ");
        } else {
            sql.append("COUNT(*) ");
        }
        return sql.toString();
    }

    /**
     * from tableName - 动态表名
     *
     * @param entityClass
     * @param defaultTableName
     * @return
     */
    public static String fromTable(Class<?> entityClass, String defaultTableName) {
        StringBuilder sql = new StringBuilder();
        sql.append(" FROM ");
        sql.append(getDynamicTableName(entityClass, defaultTableName));
        sql.append(" ");
        return sql.toString();
    }

    // add by jessen
    public static String fromTable_TL(Class<?> entityClass, String defaultTableName) {
        StringBuilder sql = new StringBuilder();
        sql.append(" FROM ");
        String tableName = entityClass.getAnnotation(Table.class).name();
        EntityField[] ids = DTOClassInfo.getIdFields(entityClass);
        sql.append(tableName).append(" b ");
        if(tableName.toUpperCase().endsWith("_B")){
            sql.append("LEFT OUTER JOIN ").append(tableName.substring(0, tableName.length() - 2) + "_TL t ");
        }else{
            sql.append("LEFT OUTER JOIN ").append(tableName + "_TL t ");
        }
        sql.append("ON (");
        for (EntityField f : ids) {
            String columnName = DTOClassInfo.getColumnName(f);
            sql.append("b.").append(columnName).append("=t.").append(columnName).append(" AND ");
        }
        sql.append("t.LANG=#{request.locale,jdbcType=VARCHAR,javaType=java.lang.String}");
        sql.append(")");
        sql.append(" ");
        return sql.toString();
    }

    /**
     * update tableName - 动态表名
     *
     * @param entityClass
     * @param defaultTableName
     * @return
     */
    public static String updateTable(Class<?> entityClass, String defaultTableName) {
        return updateTable(entityClass, defaultTableName, null);
    }

    /**
     * update tableName - 动态表名
     *
     * @param entityClass
     * @param defaultTableName
     *            默认表名
     * @param entityName
     *            别名
     * @return
     */
    public static String updateTable(Class<?> entityClass, String defaultTableName, String entityName) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE ");
        sql.append(getDynamicTableName(entityClass, defaultTableName, entityName));
        sql.append(" ");
        return sql.toString();
    }

    /**
     * delete tableName - 动态表名
     *
     * @param entityClass
     * @param defaultTableName
     * @return
     */
    public static String deleteFromTable(Class<?> entityClass, String defaultTableName) {
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM ");
        sql.append(getDynamicTableName(entityClass, defaultTableName));
        sql.append(" ");
        return sql.toString();
    }

    /**
     * insert into tableName - 动态表名
     *
     * @param entityClass
     * @param defaultTableName
     * @return
     */
    public static String insertIntoTable(Class<?> entityClass, String defaultTableName) {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO ");
        sql.append(getDynamicTableName(entityClass, defaultTableName));
        sql.append(" ");
        return sql.toString();
    }

    /**
     * insert table()列
     *
     * @param entityClass
     * @param skipId
     *            是否从列中忽略id类型
     * @param notNull
     *            是否判断!=null
     * @param notEmpty
     *            是否判断String类型!=''
     * @return
     */
    public static String insertColumns(Class<?> entityClass, boolean skipId, boolean notNull, boolean notEmpty) {
        StringBuilder sql = new StringBuilder();
        sql.append("<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">");
        // 获取全部列
        Set<EntityColumn> columnList = EntityHelper.getColumns(entityClass);
        // 当某个列有主键策略时，不需要考虑他的属性是否为空，因为如果为空，一定会根据主键策略给他生成一个值
        for (EntityColumn column : columnList) {
            if (!column.isInsertable()) {
                continue;
            }
            if (skipId && column.isId()) {
                continue;
            }
            if (notNull) {
                sql.append(SqlHelper.getIfNotNull(column, column.getColumn() + ",", notEmpty));
            } else {
                sql.append(column.getColumn() + ",");
            }
        }
        sql.append("</trim>");
        return sql.toString();
    }

    /**
     * insert-values()列
     *
     * @param entityClass
     * @param skipId
     *            是否从列中忽略id类型
     * @param notNull
     *            是否判断!=null
     * @param notEmpty
     *            是否判断String类型!=''
     * @return
     */
    public static String insertValuesColumns(Class<?> entityClass, boolean skipId, boolean notNull, boolean notEmpty) {
        StringBuilder sql = new StringBuilder();
        sql.append("<trim prefix=\"VALUES (\" suffix=\")\" suffixOverrides=\",\">");
        // 获取全部列
        Set<EntityColumn> columnList = EntityHelper.getColumns(entityClass);
        // 当某个列有主键策略时，不需要考虑他的属性是否为空，因为如果为空，一定会根据主键策略给他生成一个值
        for (EntityColumn column : columnList) {
            if (!column.isInsertable()) {
                continue;
            }
            if (skipId && column.isId()) {
                continue;
            }
            if (notNull) {
                sql.append(SqlHelper.getIfNotNull(column, column.getColumnHolder() + ",", notEmpty));
            } else {
                sql.append(column.getColumnHolder() + ",");
            }
        }
        sql.append("</trim>");
        return sql.toString();
    }

    /**
     * update set列
     *
     * @param entityClass
     * @param entityName
     *            实体映射名
     * @param notNull
     *            是否判断!=null
     * @param notEmpty
     *            是否判断String类型!=''
     * @return
     */
    public static String updateSetColumns(Class<?> entityClass, String entityName, boolean notNull, boolean notEmpty) {
        StringBuilder sql = new StringBuilder();
        sql.append("<set>");
        // 获取全部列
        Set<EntityColumn> columnList = EntityHelper.getColumns(entityClass);
        // 当某个列有主键策略时，不需要考虑他的属性是否为空，因为如果为空，一定会根据主键策略给他生成一个值
        for (EntityColumn column : columnList) {
            if (!column.isId() && column.isUpdatable()) {
                if ("last_update_date".equalsIgnoreCase(column.getColumn())) {
                    sql.append(column.getColumn()).append("=").append("CURRENT_TIMESTAMP,");
                    continue;
                }
                if (notNull) {
                    sql.append(SqlHelper.getIfNotNull(entityName, column, column.getColumnEqualsHolder(entityName) + ",", notEmpty));
                } else {
                    sql.append(column.getColumnEqualsHolder(entityName) + ",");
                }
            } else if ("object_version_number".equalsIgnoreCase(column.getColumn())) {
                sql.append(column.getColumn()).append("=").append(column.getColumn()).append("+1,");
            }
        }
        sql.append("</set>");
        return sql.toString();
    }

    public static String updateSetColumnsWithOption(Class<?> entityClass) {
        StringBuilder sql = new StringBuilder();
        sql.append("<set>");
        // 获取全部列
        Set<EntityColumn> columnList = EntityHelper.getColumns(entityClass);
        // 当某个列有主键策略时，不需要考虑他的属性是否为空，因为如果为空，一定会根据主键策略给他生成一个值
        for (EntityColumn column : columnList) {
            if (!column.isId() && column.isUpdatable()) {
                if ("last_update_date".equalsIgnoreCase(column.getColumn())) {
                    sql.append(column.getColumn()).append("=").append("CURRENT_TIMESTAMP,");
                    continue;
                }
                sql.append(SqlHelper.getIfNotNullWithOptions(column, column.getColumnEqualsHolder(BaseConstants.OPTIONS_DTO) + ","));

            } else if ("object_version_number".equalsIgnoreCase(column.getColumn())) {
                sql.append(column.getColumn()).append("=").append(column.getColumn()).append("+1,");
            }

        }
        sql.append("</set>");
        return sql.toString();
    }

    /**
     * where主键条件
     *
     * @param entityClass
     * @return
     */
    public static String wherePKColumns(Class<?> entityClass) {
        return wherePKColumns(entityClass, null);
    }

    public static String wherePKColumns(Class<?> entityClass, String entityName) {
        StringBuilder sql = new StringBuilder();
        sql.append("<where>");
        // 获取全部列
        Set<EntityColumn> columnList = EntityHelper.getPKColumns(entityClass);
        // 当某个列有主键策略时，不需要考虑他的属性是否为空，因为如果为空，一定会根据主键策略给他生成一个值
        for (EntityColumn column : columnList) {
            sql.append(" AND " + column.getColumnEqualsHolder(entityName));
        }
        sql.append("</where>");
        return sql.toString();
    }

    // add by jessen
    public static String wherePKColumns_TL(Class<?> entityClass) {
        StringBuilder sql = new StringBuilder();
        sql.append("<where>");
        // 获取全部列
        Set<EntityColumn> columnList = EntityHelper.getPKColumns(entityClass);
        // 当某个列有主键策略时，不需要考虑他的属性是否为空，因为如果为空，一定会根据主键策略给他生成一个值
        for (EntityColumn column : columnList) {
            sql.append(" AND b." + column.getColumnEqualsHolder());
        }
        sql.append("</where>");
        return sql.toString();
    }

    /**
     * where所有列的条件，会判断是否!=null
     *
     * @param entityClass
     * @return
     */
    public static String whereAllIfColumns(Class<?> entityClass, boolean empty) {
        StringBuilder sql = new StringBuilder();
        sql.append("<where>");
        // 获取全部列
        Set<EntityColumn> columnList = EntityHelper.getColumns(entityClass);
        // 当某个列有主键策略时，不需要考虑他的属性是否为空，因为如果为空，一定会根据主键策略给他生成一个值
        for (EntityColumn column : columnList) {
            Condition condition = column.getCondition();
            if (condition == null) {
                sql.append(getIfNotNull(column, " AND " + column.getColumnEqualsHolder(), empty));
            } else if (!condition.exclude()) {
                sql.append(getIfNotNull(column,
                        " AND " + column.getColumnHolderWithOperator(condition.operator(), condition.autoWrap()),
                        empty));
            }
        }
        sql.append("</where>");
        return sql.toString();
    }

    // add by jessen
    public static String whereAllIfColumns_TL(Class<?> entityClass, boolean empty, boolean equals) {
        StringBuilder sql = new StringBuilder();
        sql.append("<where>");
        // 获取全部列
        Set<EntityColumn> columnList = EntityHelper.getColumns(entityClass);
        // 当某个列有主键策略时，不需要考虑他的属性是否为空，因为如果为空，一定会根据主键策略给他生成一个值
        for (EntityColumn column : columnList) {
            if (column.isMultiLanguageField()) {
                sql.append(getIfNotNull(column, " AND t." + (equals ? column.getColumnEqualsHolder() : column.getColumnLikeHolder()), empty));
            } else {
                Condition condition = column.getCondition();
                if (condition == null) {
                    sql.append(getIfNotNull(column, " AND b." + column.getColumnEqualsHolder(), empty));
                } else if (!condition.exclude()) {
                    sql.append(getIfNotNull(column,
                            " AND b." + column.getColumnHolderWithOperator(condition.operator(), condition.autoWrap()),
                            empty));
                }
            }
        }
        sql.append("</where>");
        return sql.toString();
    }

    /**
     * 获取默认的orderBy，通过注解设置的
     *
     * @param entityClass
     * @return
     */
    public static String orderByDefault(Class<?> entityClass) {
        StringBuilder sql = new StringBuilder();
        if (BaseDTO.class.isAssignableFrom(entityClass)) {
            sql.append(
                    "<bind name=\"__orderByClause\" value=\"@com.jingrui.jrap.mybatis.util.OGNL@getOrderByClause(_parameter)\"/>");
            sql.append("<if test=\"__orderByClause!=null\">");
            sql.append("ORDER BY ${__orderByClause}");
            sql.append("</if>");
        } else {
            EntityField[] idField = DTOClassInfo.getIdFields(entityClass);
            if (idField.length > 0) {
                sql.append("ORDER BY ").append(DTOClassInfo.getColumnName(idField[0])).append(" ASC");
            }
        }
        return sql.toString();
    }

    // add by jessen
    public static String orderByDefault_TL(Class<?> entityClass) {
        StringBuilder sql = new StringBuilder();
        if (BaseDTO.class.isAssignableFrom(entityClass)) {
            sql.append(
                    "<bind name=\"__orderByClause\" value=\"@com.jingrui.jrap.mybatis.util.OGNL@getOrderByClause_TL(_parameter)\"/>");
            sql.append("<if test=\"__orderByClause!=null\">");
            sql.append("ORDER BY ${__orderByClause}");
            sql.append("</if>");
        } else {
            EntityField[] idField = DTOClassInfo.getIdFields(entityClass);
            if (idField.length > 0) {
                sql.append("ORDER BY b.").append(DTOClassInfo.getColumnName(idField[0])).append(" ASC");
            }
        }
        return sql.toString();
    }

    /**
     * example支持查询指定列时
     *
     * @return
     */
    public static String exampleSelectColumns(Class<?> entityClass) {
        StringBuilder sql = new StringBuilder();
        sql.append("<if test=\"@com.jingrui.jrap.mybatis.util.OGNL@hasSelectColumns(_parameter)\">");
        sql.append("<foreach collection=\"_parameter.selectColumns\" item=\"selectColumn\" separator=\",\">");
        sql.append("${selectColumn}");
        sql.append("</foreach>");
        sql.append("</if>");
        // 不支持指定列的时候查询全部列
        sql.append("<if test=\"@com.jingrui.jrap.mybatis.util.OGNL@hasNoSelectColumns(_parameter)\">");
        sql.append(getAllColumns(entityClass));
        sql.append("</if>");
        return sql.toString();
    }

    /**
     * example查询中的orderBy条件，会判断默认orderBy
     *
     * @return
     */
    public static String exampleOrderBy(Class<?> entityClass) {
        StringBuilder sql = new StringBuilder();
        sql.append("<if test=\"orderByClause != null\">");
        sql.append("order by ${orderByClause}");
        sql.append("</if>");
        String orderByClause = EntityHelper.getOrderByClause(entityClass);
        if (orderByClause.length() > 0) {
            sql.append("<if test=\"orderByClause == null\">");
            sql.append("ORDER BY " + orderByClause);
            sql.append("</if>");
        }
        return sql.toString();
    }

    /**
     * Example查询中的where结构，用于只有一个Example参数时
     *
     * @return
     */
    public static String exampleWhereClause() {
        return "<if test=\"_parameter != null\">" + "<where>\n"
                + "  <foreach collection=\"oredCriteria\" item=\"criteria\" separator=\"or\">\n"
                + "    <if test=\"criteria.valid\">\n"
                + "      <trim prefix=\"(\" prefixOverrides=\"and\" suffix=\")\">\n"
                + "        <foreach collection=\"criteria.criteria\" item=\"criterion\">\n" + "          <choose>\n"
                + "            <when test=\"criterion.noValue\">\n" + "              and ${criterion.condition}\n"
                + "            </when>\n" + "            <when test=\"criterion.singleValue\">\n"
                + "              and ${criterion.condition} #{criterion.value}\n" + "            </when>\n"
                + "            <when test=\"criterion.betweenValue\">\n"
                + "              and ${criterion.condition} #{criterion.value} and #{criterion.secondValue}\n"
                + "            </when>\n" + "            <when test=\"criterion.listValue\">\n"
                + "              and ${criterion.condition}\n"
                + "              <foreach close=\")\" collection=\"criterion.value\" item=\"listItem\" open=\"(\" separator=\",\">\n"
                + "                #{listItem}\n" + "              </foreach>\n" + "            </when>\n"
                + "          </choose>\n" + "        </foreach>\n" + "      </trim>\n" + "    </if>\n"
                + "  </foreach>\n" + "</where>" + "</if>";
    }

    /**
     * Example-Update中的where结构，用于多个参数时，Example带@Param("example")注解时
     *
     * @return
     */
    public static String updateByExampleWhereClause() {
        return "<where>\n" + "  <foreach collection=\"example.oredCriteria\" item=\"criteria\" separator=\"or\">\n"
                + "    <if test=\"criteria.valid\">\n"
                + "      <trim prefix=\"(\" prefixOverrides=\"and\" suffix=\")\">\n"
                + "        <foreach collection=\"criteria.criteria\" item=\"criterion\">\n" + "          <choose>\n"
                + "            <when test=\"criterion.noValue\">\n" + "              and ${criterion.condition}\n"
                + "            </when>\n" + "            <when test=\"criterion.singleValue\">\n"
                + "              and ${criterion.condition} #{criterion.value}\n" + "            </when>\n"
                + "            <when test=\"criterion.betweenValue\">\n"
                + "              and ${criterion.condition} #{criterion.value} and #{criterion.secondValue}\n"
                + "            </when>\n" + "            <when test=\"criterion.listValue\">\n"
                + "              and ${criterion.condition}\n"
                + "              <foreach close=\")\" collection=\"criterion.value\" item=\"listItem\" open=\"(\" separator=\",\">\n"
                + "                #{listItem}\n" + "              </foreach>\n" + "            </when>\n"
                + "          </choose>\n" + "        </foreach>\n" + "      </trim>\n" + "    </if>\n"
                + "  </foreach>\n" + "</where>";
    }



//====================================================================================


    /**
     * 按照主键查询生成SQL.
     *
     * @param dto
     * @return sql
     */
    public static String buildSelectByPrimaryKeySQL(BaseDTO dto) {
        EntityTable table = EntityHelper.getEntityTable(dto.getClass());
        Criteria criteria = new Criteria();
        for(EntityColumn pkColumn : table.getEntityClassPKColumns()){
            criteria.where(pkColumn.getProperty());
        }
        return buildSelectSelectiveSql(dto, criteria);
    }


    /**
     * 按照查询条件生成SQL.
     *
     * @param dto
     * @return sql
     */
    public static String buildSelectSelectiveSql(BaseDTO dto, Criteria criteria) {
        EntityTable table = EntityHelper.getEntityTable(dto.getClass());
        List<Selection> selectFields = new ArrayList<>(50);
        List<Selection> selections = criteria.getSelectFields();
        if (selections == null || selections.isEmpty()) {
            for (EntityColumn column : table.getAllColumns()) {
                if (criteria.getExcludeSelectFields() == null || !criteria.getExcludeSelectFields().contains(column.getProperty())) {
                    selectFields.add(new Selection(column.getProperty()));
                }
            }
        } else {
            for (Selection selection : selections) {
                if (criteria.getExcludeSelectFields() == null || !criteria.getExcludeSelectFields().contains(selection.getField())) {
                    selectFields.add(selection);
                }
            }
        }


        String sql = new SQL() {
            {
                // SELECT
                for (Selection selection : selectFields) {
                    String selectionSql = generateSelectionSQL(dto, selection);
                    if (StringUtils.isNotEmpty(selectionSql))
                        SELECT(selectionSql);
                }
                // FROM
                FROM(table.getName() + " " + table.getAlias());

                // JOIN
                for (Map.Entry<String, EntityColumn> entry : table.getJoinMapping().entrySet()) {
                    EntityColumn column = entry.getValue();
                    JoinTable jt = column.findJoinTableByName(entry.getKey());
                    String joinSql = generateJoinSQL(dto, column, jt, selectFields);
                    if (StringUtils.isNotBlank(joinSql)){
                        JoinType joinType = jt.type();
                        switch (joinType){
                            case LEFT:
                                LEFT_OUTER_JOIN(joinSql);
                                break;
                            case INNER:
                                INNER_JOIN(joinSql);
                                break;
                            case RIGHT:
                                RIGHT_OUTER_JOIN(joinSql);
                                break;
                        }
                    }

                }
                //WHERE
                String whereSql = generateWhereClauseSQL(dto, criteria);
                if (StringUtils.isNotBlank(whereSql))
                    WHERE(whereSql);

                // ORDER BY
                List<SortField> sortFields = criteria.getSortFields();
                if(sortFields != null && !sortFields.isEmpty()) {
                    for (SortField sortField : sortFields) {
                        for (EntityColumn sortColumn : table.getSortColumns()) {
                            if(sortColumn.getProperty().equals(sortField.getField())){
                                ORDER_BY(findColumnNameByField(dto,sortField.getField(),false) + sortField.getSortType().sql());
                                break;
                            }
                        }
                    }
                } else {
                    //默认order by
                    if(CollectionUtils.isEmpty(table.getSortColumns())){
                        EntityField[] idField = DTOClassInfo.getIdFields(dto.getClass());
                        if (idField.length > 0) {
                            ORDER_BY(DTOClassInfo.getColumnName(idField[0]) + " ASC" );
                        }
                    }else {
                        for (EntityColumn sortColumn : table.getSortColumns()) {
                            if (sortColumn.getOrderBy() != null) {
                                ORDER_BY(findColumnNameByField(dto, sortColumn, false) + " " + sortColumn.getOrderBy());
                            }
                        }
                    }
                }


            }
        }.usingAppender(new StringBuilder()).toString();
        return sql;
    }

    /**
     * 生成查询字段SQL.
     *
     * @param dto
     * @param selection
     * @return SQL
     */
    private static String generateSelectionSQL(BaseDTO dto, Selection selection) {
        return findColumnNameByField(dto,selection.getField(),true);
    }

    /**
     * 按照属性名转换字段SQL.
     *
     * @param dto
     * @param field
     * @return
     */
    private static String findColumnNameByField(BaseDTO dto, String field, boolean withAlias){
        EntityTable table = EntityHelper.getEntityTable(dto.getClass());
        EntityColumn entityColumn = table.findColumnByProperty(field);
        return findColumnNameByField(dto, entityColumn, withAlias);
    }

    /**
     * 按照属性名转换字段SQL.
     *
     * @param dto
     * @param entityColumn
     * @return
     */
    private static String findColumnNameByField(BaseDTO dto, EntityColumn entityColumn, boolean withAlias){
        EntityTable table = EntityHelper.getEntityTable(dto.getClass());
        StringBuilder sb = new StringBuilder();
        if (entityColumn != null) {
            JoinColumn jc = entityColumn.getJoinColumn();
            if (jc != null) {
                EntityColumn joinField = table.getJoinMapping().get(jc.joinName());
                JoinTable joinTable = joinField.findJoinTableByName(jc.joinName());
                if (joinField != null && joinTable != null) {
                    EntityTable joinEntityTable = EntityHelper.getEntityTable(joinTable.target());
                    EntityColumn refColumn = joinEntityTable.findColumnByProperty(jc.field());
                    sb.append(table.getAlias(EntityHelper.buildJoinKey(joinTable))).append(".").append(refColumn.getColumn());
                    if(withAlias) {
                        sb.append(" AS ").append(entityColumn.getColumn());
                    }
                }
            } else {
                sb.append(table.getAlias()).append(".").append(entityColumn.getColumn());
            }
        }
        return sb.toString();
    }

    /**
     * 生成JOIN表的SQL.
     *
     * @param dto
     * @return SQL
     */
    private static String generateJoinSQL(BaseDTO dto, EntityColumn localColumn, JoinTable joinTable, List<Selection> selections) {
        StringBuilder sb = new StringBuilder();
        EntityTable localTable = EntityHelper.getEntityTable(dto.getClass());
        String joinKey = EntityHelper.buildJoinKey(joinTable);
        EntityTable foreignTable = EntityHelper.getEntityTable(joinTable.target());
        boolean foundJoinColumn = false;
        for (Selection selection : selections) {
            EntityColumn entityColumn = localTable.findColumnByProperty(selection.getField());
            if (entityColumn != null && entityColumn.getJoinColumn() != null && joinTable.name().equals(entityColumn.getJoinColumn().joinName())) {
                foundJoinColumn = true;
                break;
            }
        }
        if (foundJoinColumn) {
            String jointTableName = foreignTable.getName();
            if(joinTable.joinMultiLanguageTable()){
                if(jointTableName.toUpperCase().endsWith("_B")){
                    jointTableName = jointTableName.substring(0, jointTableName.length() - 2) + "_TL";
                }else{
                    jointTableName = jointTableName + "_TL";
                }
            }
            sb.append(jointTableName).append(" ").append(localTable.getAlias(joinKey)).append(" ON ");
            JoinOn[] joinOns = joinTable.on();
            for (int i = 0, j = joinOns.length; i < j; i++) {
                JoinOn joinOn = joinOns[i];
                String joinField = joinOn.joinField();
                if(StringUtils.isEmpty(joinField)) continue;
                if (i != 0) {
                    sb.append(" AND ");
                }
                EntityColumn foreignColumn = foreignTable.findColumnByProperty(joinField);
                String columnName = foreignColumn != null ? foreignColumn.getColumn() : StringUtil.camelhumpToUnderline(joinField);
                if (StringUtils.isEmpty(joinOn.joinExpression())) {
                    sb.append(localTable.getAlias()).append(".").append(localColumn.getColumn()).append(" = ");
                    sb.append(localTable.getAlias(joinKey)).append(".").append(columnName);
                } else {
                    sb.append(localTable.getAlias(joinKey)).append(".").append(columnName);
                    sb.append(" = ").append(joinOn.joinExpression());
                }
            }
        }
        return sb.toString();
    }


    /**
     * 生成Where的SQL.
     *
     * @param dto
     * @return SQL
     */
    private static String generateWhereClauseSQL(BaseDTO dto, Criteria criteria) {
        StringBuilder sb = new StringBuilder();
        List<WhereField> whereFields = criteria.getWhereFields();
        EntityTable table = EntityHelper.getEntityTable(dto.getClass());

        for (EntityColumn column : table.getWhereColumns()) {
            try {
                if (BeanUtils.getProperty(dto, column.getProperty()) != null) {
                    Where where = column.getWhere();
                    Comparison comparison = where.comparison();
                    boolean isWhereField = false;
                    if(whereFields != null && !whereFields.isEmpty()){
                        for (WhereField whereField : whereFields) {
                            String f = whereField.getField();
                            if (f != null && f.equals(column.getProperty())) {
                                isWhereField = true;
                                if(whereField.getComparison() != null) {
                                    comparison = whereField.getComparison();
                                }
                                break;
                            }
                        }
                        if(!isWhereField) continue;
                    }
                    if (sb.length() > 0) {
                        sb.append(" AND ");
                    }

                    String columnName = column.getColumn();
                    JoinColumn jc = column.getJoinColumn();
                    if(jc != null) {
                        EntityColumn joinField = table.getJoinMapping().get(jc.joinName());
                        JoinTable jt = joinField.findJoinTableByName(jc.joinName());
                        EntityTable foreignTable = EntityHelper.getEntityTable(jt.target());
                        EntityColumn foreignColumn = foreignTable.findColumnByProperty(jc.field());
                        columnName = foreignColumn.getColumn();
                        sb.append(table.getAlias(EntityHelper.buildJoinKey(jt))).append(".");
                    } else {
                        sb.append(table.getAlias()).append(".");
                    }
                    sb.append(columnName).append(formatComparisonSQL(comparison.sql(), column.getColumnHolder("dto")));
                }
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                if (logger.isErrorEnabled()) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return sb.toString();
    }

    /**
     * 格式化SQL
     * @param format
     * @param placeHolder
     * @return
     */
    private static String formatComparisonSQL(String format,String placeHolder){
        if (format.indexOf("{0}") != -1) {
            MessageFormat mf = new MessageFormat(format);
            return mf.format(new String[]{placeHolder});
        } else {
            return format + placeHolder;
        }
    }

}
