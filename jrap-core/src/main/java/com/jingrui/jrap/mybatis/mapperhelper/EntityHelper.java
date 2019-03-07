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

import java.lang.annotation.Annotation;
import java.util.*;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.criteria.JoinType;

import com.jingrui.jrap.core.BaseConstants;
import com.jingrui.jrap.mybatis.annotation.*;
import com.jingrui.jrap.mybatis.common.query.*;
import com.jingrui.jrap.system.dto.BaseDTO;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.UnknownTypeHandler;

import com.jingrui.jrap.core.annotation.MultiLanguage;
import com.jingrui.jrap.core.annotation.MultiLanguageField;
import com.jingrui.jrap.mybatis.code.IdentityDialect;
import com.jingrui.jrap.mybatis.code.Style;
import com.jingrui.jrap.mybatis.entity.Config;
import com.jingrui.jrap.mybatis.entity.EntityColumn;
import com.jingrui.jrap.mybatis.entity.EntityField;
import com.jingrui.jrap.mybatis.entity.EntityTable;
import com.jingrui.jrap.mybatis.util.StringUtil;

/**
 * 实体类工具类 - 处理实体和数据库表以及字段关键的一个类
 * <p/>
 * <p>
 * 项目地址 : <a href="https://github.com/abel533/Mapper" target=
 * "_blank">https://github.com/abel533/Mapper</a>
 * </p>
 *
 * @author liuzh
 * @author njq.niu@jingrui.com
 */
public class EntityHelper {

    private EntityHelper() {

    }

//    static {
//        ClassPool.getDefault().insertClassPath(new ClassClassPath(BaseDTO.class));
//    }

    /**
     * 实体类 => 表对象
     */
    private static final Map<Class<?>, EntityTable> entityTableMap = new HashMap<>();

    /**
     * 获取表对象
     *
     * @param entityClass
     * @return
     */
    public static EntityTable getEntityTable(Class<?> entityClass) {
        EntityTable entityTable = entityTableMap.get(entityClass);
        if (entityTable == null) {
            throw new RuntimeException("无法获取实体类" + entityClass.getCanonicalName() + "对应的表名!");
        }
        return entityTable;
    }

    public static EntityTable getEntityTable(String tableName){
        EntityTable entityTable=null;
        for(EntityTable entity:entityTableMap.values()){
            if(entity.getName().equalsIgnoreCase(tableName)) {
                entityTable = entity;
                break;
            }
        }
        if(null == entityTable){
            throw new RuntimeException("无法通过表名"+tableName+"获取对应的表实体类!");
        }
        return entityTable;
    }


    public static String buildJoinKey(JoinTable jt){
        return jt.target().getCanonicalName() + "." + jt.name();
    }

    /**
     * 获取默认的orderby语句
     *
     * @param entityClass
     * @return
     */
    public static String getOrderByClause(Class<?> entityClass) {
        EntityTable table = getEntityTable(entityClass);
        if (table.getOrderByClause() != null) {
            return table.getOrderByClause();
        }
        StringBuilder orderBy = new StringBuilder();
        for (EntityColumn column : table.getEntityClassColumns()) {
            if (column.getOrderBy() != null) {
                if (orderBy.length() != 0) {
                    orderBy.append(",");
                }
                orderBy.append(column.getColumn()).append(" ").append(column.getOrderBy());
            }
        }
        table.setOrderByClause(orderBy.toString());
        return table.getOrderByClause();
    }

    /**
     * 获取全部列
     *
     * @param entityClass
     * @return
     */
    public static Set<EntityColumn> getColumns(Class<?> entityClass) {
        return getEntityTable(entityClass).getEntityClassColumns();
    }

    /**
     * 获取主键信息
     *
     * @param entityClass
     * @return
     */
    public static Set<EntityColumn> getPKColumns(Class<?> entityClass) {
        return getEntityTable(entityClass).getEntityClassPKColumns();
    }

    /**
     * 获取查询的Select
     *
     * @param entityClass
     * @return
     */
    public static String getSelectColumns(Class<?> entityClass) {
        EntityTable entityTable = getEntityTable(entityClass);
        if (entityTable.getBaseSelect() != null) {
            return entityTable.getBaseSelect();
        }
        Set<EntityColumn> columnList = getColumns(entityClass);
        StringBuilder selectBuilder = new StringBuilder();
        boolean skipAlias = Map.class.isAssignableFrom(entityClass);
        for (EntityColumn entityColumn : columnList) {
            selectBuilder.append(entityColumn.getColumn());
            if (!skipAlias && !entityColumn.getColumn().equalsIgnoreCase(entityColumn.getProperty())) {
                //不等的时候分几种情况，例如`DESC`
                if (entityColumn.getColumn().substring(1, entityColumn.getColumn().length() - 1)
                        .equalsIgnoreCase(entityColumn.getProperty())) {
                    selectBuilder.append(",");
                } else {
                    selectBuilder.append(" AS ").append(entityColumn.getProperty()).append(",");
                }
            } else {
                selectBuilder.append(",");
            }
        }
        entityTable.setBaseSelect(selectBuilder.substring(0, selectBuilder.length() - 1));
        return entityTable.getBaseSelect();
    }

    /**
     * 获取查询的Select
     *
     * @param entityClass
     * @return
     * @deprecated 4.x版本移除该方法
     */
    @Deprecated
    public static String getAllColumns(Class<?> entityClass) {
        Set<EntityColumn> columnList = getColumns(entityClass);
        StringBuilder selectBuilder = new StringBuilder();
        for (EntityColumn entityColumn : columnList) {
            selectBuilder.append(entityColumn.getColumn()).append(",");
        }
        return selectBuilder.substring(0, selectBuilder.length() - 1);
    }

    /**
     * 获取主键的Where语句
     *
     * @param entityClass
     * @return
     * @deprecated 4.x版本移除该方法
     */
    @Deprecated
    public static String getPrimaryKeyWhere(Class<?> entityClass) {
        Set<EntityColumn> entityColumns = getPKColumns(entityClass);
        StringBuilder whereBuilder = new StringBuilder();
        for (EntityColumn column : entityColumns) {
            whereBuilder.append(column.getColumnEqualsHolder()).append(" AND ");
        }
        return whereBuilder.substring(0, whereBuilder.length() - 4);
    }

    /**
     * 初始化实体属性
     *
     * @param entityClass
     * @param config
     */
    public static synchronized void initEntityNameMap(Class<?> entityClass, Config config) {
        if (entityTableMap.get(entityClass) != null) {
            return;
        }

        Style style = config.getStyle();
        //style，该注解优先于全局配置
        if (entityClass.isAnnotationPresent(NameStyle.class)) {
            NameStyle nameStyle = entityClass.getAnnotation(NameStyle.class);
            style = nameStyle.value();
        }

        //创建并缓存EntityTable
        EntityTable entityTable = null;
        if (entityClass.isAnnotationPresent(Table.class)) {
            Table table = entityClass.getAnnotation(Table.class);
            if (!table.name().equals("")) {
                entityTable = new EntityTable(entityClass);
                entityTable.setTable(table);
            }
        }
        if (entityTable == null) {
            entityTable = new EntityTable(entityClass);
            //可以通过stye控制
            entityTable.setName(StringUtil.convertByStyle(entityClass.getSimpleName(), style));
        }
        if (entityClass.isAnnotationPresent(MultiLanguage.class)) {
            entityTable.setSupportMultiLanguage(true);
        }
        entityTable.setEntityClassColumns(new LinkedHashSet<>());
        entityTable.setEntityClassPKColumns(new LinkedHashSet<>());
        //处理所有列
        List<EntityField> fields;
        if (config.isEnableMethodAnnotation()) {
            fields = FieldHelper.getAll(entityClass);
        } else {
            fields = FieldHelper.getFields(entityClass);
        }
        ExtensionAttribute extensionAttribute = entityClass.getAnnotation(ExtensionAttribute.class);
        boolean useExt = extensionAttribute == null || !extensionAttribute.disable();
        for (EntityField field : fields) {
            if (!useExt && field.getName().matches("attribute(\\d+|Category)")) {
                continue;
            }
            processField(entityTable, style, field);
        }
        //当pk.size=0的时候使用所有列作为主键
        if (entityTable.getEntityClassPKColumns().isEmpty()) {
            entityTable.setEntityClassPKColumns(entityTable.getEntityClassColumns());
        }

        entityTableMap.put(entityClass, entityTable);
    }




    /**
     * 处理一列
     *
     * @param entityTable
     * @param style
     * @param field
     */
    private static void processField(EntityTable entityTable, Style style, EntityField field) {

        //Id
        EntityColumn entityColumn = new EntityColumn(entityTable);

        //排除字段
        if (field.isAnnotationPresent(Transient.class)) {
            entityColumn.setSelectable(false);
            entityColumn.setInsertable(false);
            entityColumn.setUpdatable(false);
        }

        entityColumn.setProperty(field.getName());
        entityColumn.setJavaType(field.getJavaType());

        if(entityTable.isSupportMultiLanguage()) {
            if (field.isAnnotationPresent(Id.class)) {
                JoinTable jt = new JoinTable(){
                    @Override
                    public Class<? extends java.lang.annotation.Annotation> annotationType() {
                        return JoinTable.class;
                    }

                    @Override
                    public String name() {
                        return "multiLanguageJoin";
                    }

                    @Override
                    public boolean joinMultiLanguageTable() {
                        return true;
                    }

                    @Override
                    public Class<?> target() {
                        return entityTable.getEntityClass();
                    }

                    @Override
                    public JoinType type() {
                        return JoinType.INNER;
                    }

                    @Override
                    public JoinOn[] on() {
                        JoinOn on1 = new JoinOn(){
                            @Override
                            public Class<? extends java.lang.annotation.Annotation> annotationType() {
                                return JoinOn.class;
                            }

                            @Override
                            public String joinField() {
                                return field.getName();
                            }

                            @Override
                            public String joinExpression() {
                                return "";
                            }
                        };

                        JoinOn on2 = new JoinOn(){
                            @Override
                            public Class<? extends java.lang.annotation.Annotation> annotationType() {
                                return JoinOn.class;
                            }

                            @Override
                            public String joinField() {
                                return BaseDTO.FIELD_LANG;
                            }

                            @Override
                            public String joinExpression() {
                                return BaseConstants.PLACEHOLDER_LOCALE;
                            }
                        };


                        return new JoinOn[]{on1,on2};
                    }


                };

                entityColumn.addJoinTable(jt);
                entityTable.createAlias(buildJoinKey(jt));
                entityTable.getJoinMapping().put(jt.name(), entityColumn);
            }

            if (field.isAnnotationPresent(MultiLanguageField.class)) {
                JoinColumn jc = new JoinColumn(){

                    @Override
                    public Class<? extends java.lang.annotation.Annotation> annotationType() {
                        return JoinColumn.class;
                    }

                    @Override
                    public String joinName() {
                        return "multiLanguageJoin";
                    }

                    @Override
                    public String field() {
                        return field.getName();
                    }

                    @Override
                    public String expression() {
                        return "";
                    }
                };
                entityColumn.setJoinColumn(jc);
                entityColumn.setSelectable(true);
            }
        }

        // @JoinTable
        JoinTable[] jts = field.getAnnotations(JoinTable.class);
        if (jts != null) {
            for(JoinTable joinTable: jts){
                entityColumn.addJoinTable(joinTable);
                entityTable.createAlias(buildJoinKey(joinTable));
                entityTable.getJoinMapping().put(joinTable.name(), entityColumn);
            }
        }

        // @Where
        if (field.isAnnotationPresent(Where.class)) {
            Where where = field.getAnnotation(Where.class);
            entityColumn.setWhere(where);
            entityTable.getWhereColumns().add(entityColumn);
        }

        //OrderBy
        if (field.isAnnotationPresent(OrderBy.class)) {
            OrderBy orderBy = field.getAnnotation(OrderBy.class);

            if (!"".equals(orderBy.value())) {
                entityColumn.setOrderBy(orderBy.value());
            }
            entityTable.getSortColumns().add(entityColumn);
        }

        // @JoinColumn
        if (field.isAnnotationPresent(JoinColumn.class)) {
            JoinColumn jc = field.getAnnotation(JoinColumn.class);
            entityColumn.setJoinColumn(jc);
            entityColumn.setSelectable(true);
            entityColumn.setInsertable(false);
            entityColumn.setUpdatable(false);
        }


        //===================================================
        if (field.isAnnotationPresent(Id.class)) {
            entityColumn.setId(true);
            entityTable.getEntityClassPKColumns().add(entityColumn);
            if(entityColumn.getWhere() == null){
                Where where = new Where(){
                    @Override
                    public Class<? extends Annotation> annotationType() {
                        return Where.class;
                    }

                    @Override
                    public Comparison comparison() {
                        return Comparison.EQUAL;
                    }

                    @Override
                    public String expression() {
                        return "";
                    }
                };
                entityColumn.setWhere(where);
                entityTable.getWhereColumns().add(entityColumn);
            }


        }

        //Column
        String columnName = null;
        if (field.isAnnotationPresent(Column.class)) {
            Column column = field.getAnnotation(Column.class);
            columnName = column.name();
            entityColumn.setUpdatable(column.updatable());
            entityColumn.setInsertable(column.insertable());
        }

        //ColumnType
        if (field.isAnnotationPresent(ColumnType.class)) {
            ColumnType columnType = field.getAnnotation(ColumnType.class);
            //column可以起到别名的作用
            if (StringUtil.isEmpty(columnName) && StringUtil.isNotEmpty(columnType.column())) {
                columnName = columnType.column();
            }
            if (columnType.jdbcType() != JdbcType.UNDEFINED) {
                entityColumn.setJdbcType(columnType.jdbcType());
            }
            if (columnType.typeHandler() != UnknownTypeHandler.class) {
                entityColumn.setTypeHandler(columnType.typeHandler());
            }
        } else {
            // add by jessen, to avoid data type error on sqlserver
            if (field.getJavaType() == Date.class) {
                entityColumn.setJdbcType(JdbcType.TIMESTAMP);
            }
        }
        // Column name
        if (StringUtil.isEmpty(columnName)) {
            columnName = StringUtil.convertByStyle(field.getName(), style);
        }
        entityColumn.setColumn(columnName);


        //主键策略 - Oracle序列，MySql自动增长，UUID
        if (field.isAnnotationPresent(SequenceGenerator.class)) {
            SequenceGenerator sequenceGenerator = field.getAnnotation(SequenceGenerator.class);
            if ("".equals(sequenceGenerator.sequenceName())) {
                throw new RuntimeException(entityTable.getEntityClass() + "字段" + field.getName()
                        + "的注解@SequenceGenerator未指定sequenceName!");
            }
            entityColumn.setSequenceName(sequenceGenerator.sequenceName());
        } else if (field.isAnnotationPresent(GeneratedValue.class)) {
            GeneratedValue generatedValue = field.getAnnotation(GeneratedValue.class);
            String generator = generatedValue.generator();
            if ("UUID".equals(generator)) {
                entityColumn.setUuid(true);
            } else if ("JDBC".equals(generator)) {
                entityColumn.setIdentity(true);
                entityColumn.setGenerator(generator);
                entityTable.setKeyProperties(entityColumn.getProperty());
                entityTable.setKeyColumns(entityColumn.getColumn());
            } else if ("SEQUENCE".equals(generator)) {
                // add by jessen, oracle sequence
                entityColumn.setIdentity(true);
                entityColumn.setGenerator(generator);
                entityTable.setKeyProperties(entityColumn.getProperty());
                entityTable.setKeyColumns(entityColumn.getColumn());
            } else if ("IDENTITY".equals(generator) || "".equals(generator)) {
                // add by jessen, use config IDENTITY
                entityColumn.setIdentity(true);
                entityColumn.setGenerator("IDENTITY");
                entityTable.setKeyProperties(entityColumn.getProperty());
                entityTable.setKeyColumns(entityColumn.getColumn());
            } else {
                //允许通过generator来设置获取id的sql,例如mysql=CALL IDENTITY(),hsqldb=SELECT SCOPE_IDENTITY()
                //允许通过拦截器参数设置公共的generator
                if (generatedValue.strategy() == GenerationType.IDENTITY) {
                    //mysql的自动增长
                    entityColumn.setIdentity(true);
                    if (!"".equals(generator)) {
                        IdentityDialect identityDialect = IdentityDialect.getDatabaseDialect(generator);
                        if (identityDialect != null) {
                            generator = identityDialect.getIdentityRetrievalStatement();
                        }
                        entityColumn.setGenerator(generator);
                    }
                } else {
                    throw new RuntimeException(field.getName() + " - 该字段@GeneratedValue配置只允许以下几种形式:"
                            + "\n1.全部数据库通用的@GeneratedValue(generator=\"UUID\")"
                            + "\n2.useGeneratedKeys的@GeneratedValue(generator=\"JDBC\")  "
                            + "\n3.useGeneratedKeys的@GeneratedValue(generator=\"SEQUENCE\")  "
                            + "\n4.类似mysql数据库的@GeneratedValue(strategy=GenerationType.IDENTITY[,generator=\"Mysql\"])");
                }
            }
        }
        if (field.isAnnotationPresent(MultiLanguageField.class)) {
            // add by jessen
            entityColumn.setMultiLanguageField(true);
        }
        // add by jessen
        entityColumn.setCondition(field.getAnnotation(Condition.class));


        if (!field.isAnnotationPresent(Transient.class)) {
            entityTable.getEntityClassColumns().add(entityColumn);
        }

        if(entityColumn.isSelectable())
        entityTable.getAllColumns().add(entityColumn);

    }
}