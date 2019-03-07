package com.jingrui.jrap.core.interceptor;

import com.jingrui.jrap.cache.CacheResolve;
import com.jingrui.jrap.core.BaseConstants;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.core.impl.RequestHelper;
import com.jingrui.jrap.mybatis.common.Criteria;
import com.jingrui.jrap.mybatis.common.query.JoinCache;
import com.jingrui.jrap.mybatis.common.query.JoinCode;
import com.jingrui.jrap.mybatis.common.query.JoinLov;
import com.jingrui.jrap.mybatis.common.query.Selection;
import com.jingrui.jrap.mybatis.entity.EntityField;
import com.jingrui.jrap.system.dto.BaseDTO;
import com.jingrui.jrap.system.dto.DTOClassInfo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.StatementType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author jialong.zuo@jingrui.com on 2017/5/31.
 */

@Intercepts({@Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
public class CacheJoinInterceptor implements Interceptor {

    private Logger logger = LoggerFactory.getLogger(CacheJoinInterceptor.class);

    private Map<String, CacheResolve> cacheJoinType;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object target = invocation.getTarget();
        if (target instanceof Executor) {
            MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
            if(!mappedStatement.getId().endsWith("selectOptions")){
                 return invocation.proceed();
            }
            Object domain = invocation.getArgs()[1];
            Criteria criteria = null;
            if (domain instanceof MapperMethod.ParamMap) {
                Map m = (Map) domain;
                if (m.containsKey(BaseConstants.OPTIONS_CRITERIA)) {
                    criteria = (Criteria) ((Map) domain).get(BaseConstants.OPTIONS_CRITERIA);
                }
                if (m.containsKey(BaseConstants.OPTIONS_DTO)) {
                    domain = ((Map) domain).get(BaseConstants.OPTIONS_DTO);
                }
            }
            if (domain instanceof BaseDTO) {
                BaseDTO dtoObj = (BaseDTO) domain;
                if (mappedStatement.getSqlCommandType() == SqlCommandType.SELECT) {
                    Object obj = invocation.proceed();
                    if (!mappedStatement.getId().contains("!")) {
                        setFieldValueFromCache(dtoObj, criteria, obj);
                    }
                    return obj;
                }
            }
        }
        return invocation.proceed();
    }

    //从缓存中查询缓存字段
    private void setFieldValueFromCache(BaseDTO parameterObject, Criteria criteria, Object resultObject) throws Exception {
        criteria = criteria == null ? new Criteria() : criteria;
        List<String> selectFields = null;
        List<String> unSelectFields = criteria.getExcludeSelectFields();
        List<Selection> selectionList = CollectionUtils.isEmpty(unSelectFields) ? criteria.getSelectFields() : null;
        if (selectionList != null) {
            selectFields = selectionList.stream().map(v -> v.getField()).collect(Collectors.toList());
        }

        Class<?> clazz = parameterObject.getClass();
        Map<String, Annotation> cacheEntityMap = new HashMap<>();
        cacheEntityMap.putAll(getEntityField(clazz, JoinCache.class, selectFields, unSelectFields));
        cacheEntityMap.putAll(getEntityField(clazz, JoinCode.class, selectFields, unSelectFields));
        cacheEntityMap.putAll(getEntityField(clazz, JoinLov.class, selectFields, unSelectFields));

        if (!cacheEntityMap.isEmpty()) {
            if (resultObject instanceof ArrayList) {
                ArrayList resultList = (ArrayList) resultObject;
                for (int i = 0; i < resultList.size(); i++) {
                    //查询缓存操作
                    addCacheColumn(resultList.get(i), cacheEntityMap);
                }
            }
        }
    }

    //获取缓存注解字段
    public Map getEntityField(Class clazz, Class annotation, List<String> selectFields, List<String> unSelectFields) {
        Map cacheColumns = new HashMap<String, Annotation>();

        EntityField[] entityFields = DTOClassInfo.getFieldsOfAnnotation(clazz, annotation);
        for (EntityField entityField : entityFields) {
            String fieldName = entityField.getName();
            if(entityField.getAnnotation(annotation) instanceof JoinCode){
                JoinCode code=(JoinCode) entityField.getAnnotation(annotation);
                fieldName=code.joinKey();
            }else if(entityField.getAnnotation(annotation) instanceof JoinCache){
                JoinCache cache=(JoinCache) entityField.getAnnotation(annotation);
                fieldName=cache.joinKey();
            }else if(entityField.getAnnotation(annotation) instanceof JoinLov){
                JoinLov cache=(JoinLov) entityField.getAnnotation(annotation);
                fieldName=cache.joinKey();
            }

            if (null != unSelectFields && !unSelectFields.isEmpty()) {
                if (unSelectFields.contains(fieldName)) {
                    continue;
                }
            } else {
                if (null != selectFields && !selectFields.isEmpty() && !selectFields.contains(fieldName)) {
                    continue;
                }
            }
            cacheColumns.put(entityField.getName(), entityField.getAnnotation(annotation));

        }
        return cacheColumns;
    }


    //将缓存中查出的值插入dto
    private void addCacheColumn(Object resultMap, Map cacheEntityMap) {

        Iterator<Map.Entry<String, Annotation>> cacheIterator = cacheEntityMap.entrySet().iterator();
        while (cacheIterator.hasNext()) {
            Map.Entry<String, Annotation> cacheEntity = cacheIterator.next();
            String cacheKey = cacheEntity.getKey();
            try {
                Object columnValue = getColumnValueByCache(resultMap, cacheEntity);
                if(null == columnValue){
                    continue;
                }
                Field column = resultMap.getClass().getDeclaredField(cacheKey);
                column.setAccessible(true);
                column.set(resultMap, columnValue);
            } catch (NoSuchFieldException e) {
                logger.error(e.getMessage(), e);
            } catch (IllegalAccessException e) {
                logger.error(e.getMessage(), e);
            }

        }
    }

    //从缓存中获取值
    private Object getColumnValueByCache(Object resultMap, Map.Entry<String, Annotation> cacheEntity) throws NoSuchFieldException, IllegalAccessException {
        IRequest iRequest = RequestHelper.getCurrentRequest(true);
        String lang = iRequest.getLocale();
        Object result = null;
       // Field field = null;
        CacheResolve cacheResolve = null;
        if (cacheEntity.getValue() instanceof JoinCache) {
            JoinCache joinCache = (JoinCache) cacheEntity.getValue();
            cacheResolve = cacheJoinType.get(joinCache.cacheName());
        } else if (cacheEntity.getValue() instanceof JoinCode) {
            cacheResolve = cacheJoinType.get("_code");
        } else if(cacheEntity.getValue() instanceof JoinLov){
            cacheResolve = cacheJoinType.get("_lov");
        }
        result = cacheResolve.resolve(cacheEntity.getValue(), resultMap, lang);

        return result;
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

    public Map<String, CacheResolve> getCacheJoinType() {
        return cacheJoinType;
    }

    public void setCacheJoinType(Map<String, CacheResolve> cacheJoinType) {
        this.cacheJoinType = cacheJoinType;
    }
}
