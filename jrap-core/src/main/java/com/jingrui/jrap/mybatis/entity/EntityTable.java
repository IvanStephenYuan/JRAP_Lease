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

package com.jingrui.jrap.mybatis.entity;

import com.jingrui.jrap.mybatis.util.StringUtil;
import org.apache.ibatis.mapping.ResultFlag;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.session.Configuration;

import javax.persistence.Table;
import java.util.*;

/**
 * 数据库表
 *
 * @author liuzh
 */
public class EntityTable {

    // 别名初始值
    private static final char ALIAS_START = 'A';

    // 别名序号
    private int currentAliasCharIndex = 0;


    private String name;
    private String catalog;
    private String schema;
    private String orderByClause;
    private String baseSelect;

    // 全部可查询列
    private Set<EntityColumn> allColumns = new LinkedHashSet<>();

    //实体类 => 全部列属性
    private Set<EntityColumn> entityClassColumns;
    //实体类 => 主键信息
    private Set<EntityColumn> entityClassPKColumns;
    //useGenerator包含多列的时候需要用到
    private List<String> keyProperties;
    private List<String> keyColumns;
    //resultMap对象
    private ResultMap resultMap;
    //类
    private Class<?> entityClass;

    // 可排序列
    private Set<EntityColumn> sortColumns = new LinkedHashSet<>();

    // 别名映射
    private Map<String, String> aliasMapping = new HashMap<>();

    // Join映射
    private Map<String, EntityColumn> joinMapping = new HashMap<>();

    // Where列
    private List<EntityColumn> whereColumns = new ArrayList<>();

    // add by jessen
    private boolean supportMultiLanguage = false;

    public EntityTable(Class<?> entityClass) {
        this.entityClass = entityClass;
        createAlias(entityClass.getCanonicalName());
    }

    public Class<?> getEntityClass() {
        return entityClass;
    }

    public void setTable(Table table) {
        this.name = table.name();
        this.catalog = table.catalog();
        this.schema = table.schema();
    }

    public void setKeyColumns(List<String> keyColumns) {
        this.keyColumns = keyColumns;
    }

    public void setKeyProperties(List<String> keyProperties) {
        this.keyProperties = keyProperties;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getBaseSelect() {
        return baseSelect;
    }

    public void setBaseSelect(String baseSelect) {
        this.baseSelect = baseSelect;
    }

    public String getPrefix() {
        if (StringUtil.isNotEmpty(catalog)) {
            return catalog;
        }
        if (StringUtil.isNotEmpty(schema)) {
            return schema;
        }
        return "";
    }

    public Set<EntityColumn> getEntityClassColumns() {
        return entityClassColumns;
    }

    public void setEntityClassColumns(Set<EntityColumn> entityClassColumns) {
        this.entityClassColumns = entityClassColumns;
    }

    public Set<EntityColumn> getEntityClassPKColumns() {
        return entityClassPKColumns;
    }

    public void setEntityClassPKColumns(Set<EntityColumn> entityClassPKColumns) {
        this.entityClassPKColumns = entityClassPKColumns;
    }

    public String[] getKeyProperties() {
        if (keyProperties != null && (!keyProperties.isEmpty())) {
            return keyProperties.toArray(new String[]{});
        }
        return new String[]{};
    }

    public void setKeyProperties(String keyProperty) {
        if (this.keyProperties == null) {
            this.keyProperties = new ArrayList<>();
            this.keyProperties.add(keyProperty);
        } else {
            this.keyProperties.add(keyProperty);
        }
    }

    public String[] getKeyColumns() {
        if (keyColumns != null && (!keyColumns.isEmpty())) {
            return keyColumns.toArray(new String[]{});
        }
        return new String[]{};
    }

    public void setKeyColumns(String keyColumn) {
        if (this.keyColumns == null) {
            this.keyColumns = new ArrayList<>();
            this.keyColumns.add(keyColumn);
        } else {
            this.keyColumns.add(keyColumn);
        }
    }

    public boolean isSupportMultiLanguage() {
        return supportMultiLanguage;
    }

    public void setSupportMultiLanguage(boolean supportMultiLanguage) {
        this.supportMultiLanguage = supportMultiLanguage;
    }

    /**
     * 生成当前实体的resultMap对象
     *
     * @param configuration
     * @return
     */
    public ResultMap getResultMap(Configuration configuration) {
        if (this.resultMap != null) {
            return this.resultMap;
        }


        if (allColumns == null || allColumns.isEmpty()) {
            return null;
        }
        List<ResultMapping> resultMappings = new ArrayList<>();
        for (EntityColumn entityColumn : allColumns) {
            ResultMapping.Builder builder = new ResultMapping.Builder(configuration, entityColumn.getProperty(), entityColumn.getColumn(), entityColumn.getJavaType());
            if (entityColumn.getJdbcType() != null) {
                builder.jdbcType(entityColumn.getJdbcType());
            }
            if (entityColumn.getTypeHandler() != null) {
                try {
                    builder.typeHandler(entityColumn.getTypeHandler().newInstance());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            List<ResultFlag> flags = new ArrayList<>();
            if (entityColumn.isId()) {
                flags.add(ResultFlag.ID);
            }
            builder.flags(flags);
            resultMappings.add(builder.build());
        }
        ResultMap.Builder builder = new ResultMap.Builder(configuration, "BaseMapperResultMap", this.entityClass, resultMappings, true);
        this.resultMap = builder.build();
        return this.resultMap;
    }


    public void createAlias(String key){
        if(!aliasMapping.containsKey(key)) {
            aliasMapping.put(key, generateAlias());
        }
    }

    public String getAlias(String key){
        return key == null ? aliasMapping.get(entityClass.getCanonicalName()) : aliasMapping.get(key);
    }

    public Map<String, EntityColumn> getJoinMapping() {
        return joinMapping;
    }

    public String getAlias(){
        return getAlias(null);
    }

    private String generateAlias() {
        return (String.valueOf((char) (ALIAS_START + currentAliasCharIndex++)));
    }

    public List<EntityColumn> getWhereColumns() {
        return whereColumns;
    }


    public Set<EntityColumn> getSortColumns() {
        return sortColumns;
    }

    public Set<EntityColumn> getAllColumns() {
        return allColumns;
    }

    public EntityColumn findColumnByProperty(String property){
        EntityColumn entityColumn = null;
        for (EntityColumn column : getAllColumns()) {
            if (column.getProperty().equals(property)) {
                entityColumn = column;
                break;
            }
        }
        return entityColumn;
    }

}
