/*
 * #{copyright}#
 */
package com.jingrui.jrap.core.interceptor;

import static org.springframework.util.Assert.hasText;
import static org.springframework.util.Assert.notNull;

import javax.persistence.Table;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.jingrui.jrap.core.BaseConstants;
import com.jingrui.jrap.core.ILanguageProvider;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.core.ITlTableNameProvider;
import com.jingrui.jrap.core.annotation.MultiLanguage;
import com.jingrui.jrap.core.components.ApplicationContextHelper;
import com.jingrui.jrap.core.impl.DefaultTlTableNameProvider;
import com.jingrui.jrap.core.impl.RequestHelper;
import com.jingrui.jrap.mybatis.common.Criteria;
import com.jingrui.jrap.mybatis.entity.EntityField;
import com.jingrui.jrap.mybatis.mapperhelper.MultipleJdbc3KeyGenerator;
import com.jingrui.jrap.system.dto.BaseDTO;
import com.jingrui.jrap.system.dto.DTOClassInfo;
import com.jingrui.jrap.system.dto.Language;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.keygen.Jdbc3KeyGenerator;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 自动数据多语言支持.
 *
 * @author shengyang.zhou@jingrui.com
 */
@Intercepts({ @Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }) })
public class MultiLanguageInterceptor implements Interceptor {

    private ITlTableNameProvider tableNameProvider = DefaultTlTableNameProvider.getInstance();

    private ILanguageProvider languageProvider;

    private Logger logger = LoggerFactory.getLogger(MultiLanguageInterceptor.class);

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        Object target = invocation.getTarget();
        if (target instanceof Executor) {
            MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
            //如果是Jdbc3KeyGenerator，就设置为MultipleJdbc3KeyGenerator
            MetaObject msObject = SystemMetaObject.forObject(mappedStatement);
            KeyGenerator keyGenerator = mappedStatement.getKeyGenerator();
            if (keyGenerator instanceof Jdbc3KeyGenerator) {
                msObject.setValue("keyGenerator", new MultipleJdbc3KeyGenerator());
            }
            Object domain = invocation.getArgs()[1];
            Criteria criteria = null;
            if(domain instanceof MapperMethod.ParamMap){
                Map map = ((Map) domain);
                if (map.containsKey(BaseConstants.OPTIONS_CRITERIA)) {
                    criteria = (Criteria) ((Map) domain).get(BaseConstants.OPTIONS_CRITERIA);
                    domain = ((Map) domain).get(BaseConstants.OPTIONS_DTO);
                }
            }
            if (domain instanceof BaseDTO){
                BaseDTO dtoObj = (BaseDTO) domain;
                if (mappedStatement.getSqlCommandType() == SqlCommandType.INSERT
                        || mappedStatement.getSqlCommandType() == SqlCommandType.UPDATE) {
                    Object obj = invocation.proceed();
                    proceedMultiLanguage(dtoObj, invocation, mappedStatement,criteria);
                    return obj;
                } else if (mappedStatement.getSqlCommandType() == SqlCommandType.DELETE) {
                    Object obj = invocation.proceed();
                    proceedDeleteMultiLanguage(dtoObj, invocation);
                    return obj;
                }
            }
            return invocation.proceed();
        }
        return invocation.proceed();
    }

    private void proceedMultiLanguage(BaseDTO parameterObject, Invocation invocation, MappedStatement mappedStatement,Criteria criteria)
            throws Exception {
        List<String> updateFields = criteria !=null ? criteria.getUpdateFields():null;
        Class<?> clazz = parameterObject.getClass();
        MultiLanguage multiLanguageTable = clazz.getAnnotation(MultiLanguage.class);
        if (multiLanguageTable == null) {
            return;
        }
        if(null == languageProvider) {
            languageProvider = ApplicationContextHelper.getApplicationContext().getBean(ILanguageProvider.class);
        }
        Table table = clazz.getAnnotation(Table.class);
        notNull(table, "annotation @Table not found!");
        String tableName = table.name();
        hasText(tableName, "@Table name not found!");
        tableName = tableNameProvider.getTlTableName(tableName);
        if (mappedStatement.getSqlCommandType() == SqlCommandType.INSERT) {
            proceedInsertMultiLanguage(tableName, parameterObject, (Executor) invocation.getTarget());
        } else if (mappedStatement.getSqlCommandType() == SqlCommandType.UPDATE) {
            if (parameterObject.get__tls().isEmpty()) {
                proceedUpdateMultiLanguage(tableName, parameterObject, (Executor) invocation.getTarget(),updateFields);
            } else {
                proceedUpdateMultiLanguage2(tableName, parameterObject, (Executor) invocation.getTarget(),updateFields);
            }
        }
    }

    private void proceedDeleteMultiLanguage(BaseDTO parameterObject, Invocation invocation)
            throws Exception {
        Class<?> clazz = parameterObject.getClass();
        MultiLanguage multiLanguageTable = clazz.getAnnotation(MultiLanguage.class);
        if (multiLanguageTable == null) {
            return;
        }
        if(null == languageProvider) {
            languageProvider = ApplicationContextHelper.getApplicationContext().getBean(ILanguageProvider.class);
        }
        Table table = clazz.getAnnotation(Table.class);
        notNull(table, "annotation @Table not found!");
        String tableName = table.name();
        hasText(tableName, "@Table name not found!");
        tableName = tableNameProvider.getTlTableName(tableName);

        List<Object> objs = new ArrayList<>();
        List<String> keys = new ArrayList<>();
        for (EntityField f : DTOClassInfo.getIdFields(clazz)) {
            Object v = PropertyUtils.getProperty(parameterObject,f.getName());
            keys.add(DTOClassInfo.getColumnName(f) + "=?");
            objs.add(v);
        }
        for (Object pkv : objs) {
            if (pkv == null) {
                // 主键中有 null
                return;
            }
        }
        if (keys.size() > 0) {
            Executor executor = (Executor) invocation.getTarget();
            StringBuilder sql = new StringBuilder("DELETE FROM ");
            sql.append(tableName).append(" WHERE ").append(StringUtils.join(keys, " AND "));
            executeSql(executor.getTransaction().getConnection(), sql.toString(), objs);
        }
    }

    private void proceedInsertMultiLanguage(String tableName, BaseDTO parameterObject, Executor executor)
            throws Exception {

        Class<?> clazz = parameterObject.getClass();
        List<String> keys = new ArrayList<>();
        List<Object> objs = new ArrayList<>();
        List<String> placeholders = new ArrayList<>();
        StringBuilder sql = new StringBuilder("INSERT INTO " + tableName + "(");
        for (EntityField f : DTOClassInfo.getIdFields(clazz)) {
            String columnName = DTOClassInfo.getColumnName(f);
            keys.add(columnName);
            placeholders.add("?");
            objs.add(PropertyUtils.getProperty(parameterObject,f.getName()));
        }
        keys.add("LANG");
        placeholders.add("?");
        objs.add(null); // 占位符

        EntityField[] mlFields = DTOClassInfo.getMultiLanguageFields(clazz);
        for (EntityField f : mlFields) {
            keys.add(DTOClassInfo.getColumnName(f));
            placeholders.add("?");
            Map<String, String> tls = parameterObject.get__tls().get(f.getName());
            if (tls == null) {
                // if multi language value not exists in __tls, then use
                // value on current field
                objs.add(PropertyUtils.getProperty(parameterObject,f.getName()));
                continue;
            }
            objs.add(null); // 占位符
        }
        keys.add("CREATED_BY");
        placeholders.add("" + parameterObject.getCreatedBy());

        keys.add("CREATION_DATE");
        placeholders.add("CURRENT_TIMESTAMP");

        keys.add("LAST_UPDATED_BY");
        placeholders.add("" + parameterObject.getCreatedBy());

        keys.add("LAST_UPDATE_DATE");
        placeholders.add("CURRENT_TIMESTAMP");

        sql.append(StringUtils.join(keys, ","));
        sql.append(") VALUES (").append(StringUtils.join(placeholders, ",")).append(")");

        List<Language> languages = languageProvider.getSupportedLanguages();
        for (Language language : languages) {
            objs.set(objs.size() - mlFields.length - 1, language.getLangCode());
            for (int i = 0; i < mlFields.length; i++) {
                int idx = objs.size() - mlFields.length + i;
                Map<String, String> tls = parameterObject.get__tls().get(mlFields[i].getName());
                if (tls != null) {
                    objs.set(idx, tls.get(language.getLangCode()));
                }
                // 当tls为null时,不设置值(使用field的值,旧模式)
            }

            if (logger.isDebugEnabled()) {
                logger.debug("Insert TL(Batch):{}", sql.toString());
                logger.debug("Parameters:{}", StringUtils.join(objs, ", "));
            }
            executeSql(executor.getTransaction().getConnection(), sql.toString(), objs);
        }
    }

    private void proceedUpdateMultiLanguage(String tableName, BaseDTO parameterObject, Executor executor,List<String> updateFields)
            throws Exception {
        Class<?> clazz = parameterObject.getClass();
        List<String> sets = new ArrayList<>();
        List<Object> objs = new ArrayList<>();
        List<String> keys = new ArrayList<>();
        StringBuilder sql = new StringBuilder("UPDATE " + tableName + " SET ");
        for (EntityField field : DTOClassInfo.getMultiLanguageFields(clazz)) {
            if(null!=updateFields && !updateFields.isEmpty() && !updateFields.contains(field.getName())){
                continue;
            }
            Object value = PropertyUtils.getProperty(parameterObject,field.getName());
            if (value == null) {
                continue;
            }
            sets.add(DTOClassInfo.getColumnName(field) + "=?");
            objs.add(value);
        }
        if (sets.isEmpty()) {
            if (logger.isDebugEnabled()) {
                logger.debug("None multi language field has TL value. skip update.");
            }
            return;
        }

        sets.add("LAST_UPDATED_BY=" + parameterObject.getLastUpdatedBy());
        sets.add("LAST_UPDATE_DATE=CURRENT_TIMESTAMP");

        for (EntityField field : DTOClassInfo.getIdFields(clazz)) {
            keys.add(DTOClassInfo.getColumnName(field) + "=?");
            objs.add(PropertyUtils.getProperty(parameterObject,field.getName()));
        }
        keys.add("LANG=?");
        IRequest iRequest = RequestHelper.getCurrentRequest(true);
        objs.add(iRequest.getLocale());

        sql.append(StringUtils.join(sets, ","));
        sql.append(" WHERE ").append(StringUtils.join(keys, " AND "));
        if (logger.isDebugEnabled()) {
            logger.debug("Update TL(Classic):{}", sql.toString());
            logger.debug("Parameters:{}", StringUtils.join(objs, ","));
        }

        Connection connection = executor.getTransaction().getConnection();
        int updateCount = executeSql(connection, sql.toString(), objs);
        if (updateCount < 1) {
            if (logger.isWarnEnabled()) {
                logger.warn("Update TL failed(Classic). update count:" + updateCount);
            }
            doInsertForMissingTlData(tableName, iRequest.getLocale(), parameterObject, connection);
        }
    }

    private void proceedUpdateMultiLanguage2(String tableName, BaseDTO parameterObject, Executor executor,List<String> updateFields)
            throws Exception {

        Class<?> clazz = parameterObject.getClass();
        List<String> sets = new ArrayList<>();
        List<String> updateFieldNames = new ArrayList<>();

        List<Object> objs = new ArrayList<>();
        List<String> keys = new ArrayList<>();
        StringBuilder sql = new StringBuilder("UPDATE " + tableName + " SET ");
        for (EntityField field : DTOClassInfo.getMultiLanguageFields(clazz)) {
            if(null!=updateFields&& !updateFields.isEmpty()&&!updateFields.contains(field.getName())){
                continue;
            }
            Map<String, String> tls = parameterObject.get__tls().get(field.getName());
            if (tls == null) {
                if (logger.isDebugEnabled()) {
                    logger.debug("TL value for field '{}' not exists.", field.getName());
                }
                // if tl value not exists in __tls, skip.
                continue;
            }
            sets.add(DTOClassInfo.getColumnName(field) + "=?");
            updateFieldNames.add(field.getName());
            objs.add(null); // just a placeholder
        }
        if (sets.isEmpty()) {
            if (logger.isDebugEnabled()) {
                logger.debug("None multi language field has TL value. skip update.");
                return;
            }
        }

        sets.add("LAST_UPDATED_BY=" + parameterObject.getLastUpdatedBy());
        sets.add("LAST_UPDATE_DATE=CURRENT_TIMESTAMP");

        for (EntityField field : DTOClassInfo.getIdFields(clazz)) {
            keys.add(DTOClassInfo.getColumnName(field) + "=?");
            objs.add(PropertyUtils.getProperty(parameterObject,field.getName()));
        }
        keys.add("LANG=?");
        objs.add(null); // just a place holder

        sql.append(StringUtils.join(sets, ","));
        sql.append(" WHERE ").append(StringUtils.join(keys, " AND "));

        Connection connection = executor.getTransaction().getConnection();

        List<Language> languages = languageProvider.getSupportedLanguages();
        for (Language language : languages) {
            // 前面几个参数都是多语言数据,需要每次更新
            for (int i = 0; i < updateFieldNames.size(); i++) {
                Map<String, String> tls = parameterObject.get__tls().get(updateFieldNames.get(i));
                objs.set(i, tls.get(language.getLangCode()));
            }

            // 最后一个参数是语言环境
            objs.set(objs.size() - 1, language.getLangCode());

            if (logger.isDebugEnabled()) {
                logger.debug("Update TL(Batch):{}", sql.toString());
                logger.debug("Parameters:{}", StringUtils.join(objs, ", "));
            }
            int updateCount = executeSql(connection, sql.toString(), objs);
            if (updateCount < 1) {
                if (logger.isWarnEnabled()) {
                    logger.warn("Update TL failed(Batch). update count:{},lang:{}", updateCount,
                            language.getLangCode());
                }
                doInsertForMissingTlData(tableName, language.getLangCode(), parameterObject, connection);
            }
        }
    }

    private void doInsertForMissingTlData(String tableName, String lang, BaseDTO parameterObject, Connection connection)
            throws Exception {

        Class clazz = parameterObject.getClass();
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ").append(tableName).append(" (");
        List<Object> values = new ArrayList<>();
        int pn = 0;
        for (EntityField f : DTOClassInfo.getIdFields(clazz)) {
            sb.append(DTOClassInfo.getColumnName(f)).append(",");
            values.add(PropertyUtils.getProperty(parameterObject,f.getName()));
            pn++;
        }
        sb.append("LANG");
        pn++;
        values.add(lang);
        Map<String, Map<String, String>> tls = parameterObject.get__tls();
        for (EntityField f : DTOClassInfo.getMultiLanguageFields(clazz)) {
            sb.append(",").append(DTOClassInfo.getColumnName(f));
            if (tls != null && tls.get(f.getName()) != null) {
                values.add(tls.get(f.getName()).get(lang));
            } else {
                values.add(PropertyUtils.getProperty(parameterObject, f.getName()));
            }
            pn++;
        }
        sb.append(",CREATED_BY");
        values.add(parameterObject.getCreatedBy());
        sb.append(",CREATION_DATE");
        sb.append(",LAST_UPDATED_BY");
        values.add(parameterObject.getLastUpdatedBy());
        sb.append(",LAST_UPDATE_DATE");

        sb.append(") VALUES (");
        for (int i = 0; i < pn; i++) {
            sb.append("?,");
        }
        sb.append("?");
        sb.append(",CURRENT_TIMESTAMP");
        sb.append(",?");
        sb.append(",CURRENT_TIMESTAMP");
        sb.append(")");

        if (logger.isDebugEnabled()) {
            logger.debug("Insert Missing TL record:" + sb.toString());
            logger.debug("Parameters:" + StringUtils.join(values, ", "));
        }

        executeSql(connection, sb.toString(), values);

    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof Executor) {
            return Plugin.wrap(target, this);
        }
        return target;
    }

    @Override
    public void setProperties(Properties properties) {

    }

    public ITlTableNameProvider getTableNameProvider() {
        return tableNameProvider;
    }

    public void setTableNameProvider(ITlTableNameProvider tableNameProvider) {
        this.tableNameProvider = tableNameProvider;
    }

    protected int executeSql(Connection connection, String sql, List<Object> params) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            int i = 1;
            for (Object obj : params) {
                ps.setObject(i++, obj);
            }
            ps.execute();
            return ps.getUpdateCount();
        }
    }


}
