package com.jingrui.jrap.core.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import com.jingrui.jrap.cache.Cache;
import com.jingrui.jrap.cache.CacheDelete;
import com.jingrui.jrap.cache.CacheManager;
import com.jingrui.jrap.cache.CacheSet;
import com.jingrui.jrap.cache.impl.HashStringRedisCache;
import com.jingrui.jrap.cache.impl.HashStringRedisCacheGroup;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.core.IRequestAware;
import com.jingrui.jrap.core.annotation.StdWho;
import com.jingrui.jrap.mybatis.entity.EntityField;
import com.jingrui.jrap.system.dto.BaseDTO;
import com.jingrui.jrap.system.dto.DTOClassInfo;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.StringUtils;

/**
 * service 方法执行通知.
 * <p>
 * 拦截所有 Service 方法执行,处理与 IRequest 相关的参数.
 * <p>
 * service 方法执行时间统计.
 * <p>
 * 从 request 中 copy MDC property 到 真正的 MDC
 *
 * @author shengyang.zhou@jingrui.com
 */
public class ServiceExecutionAdvice implements MethodInterceptor {

    private Logger logger = LoggerFactory.getLogger(ServiceExecutionAdvice.class);

    @Autowired
    private CacheManager cacheManager;

    void initMDC(IRequest request) {
        if (request != null) {
            request.getAttributeMap().forEach((key, v) -> {
                if (key.startsWith(IRequest.MDC_PREFIX)) {
                    String mdcProperty = key.substring(IRequest.MDC_PREFIX.length());
                    MDC.put(mdcProperty, (String) v);
                }
            });
        }
    }

    public void before(Method method, Object[] args, Object target) throws Throwable {
        Class<?>[] types = method.getParameterTypes();
        int idx = -1;

        for (int i = 0; i < types.length; ++i) {
            if (IRequest.class.isAssignableFrom(types[i])) {
                idx = i;
                break;
            }
        }
        if (idx == -1) {
            // method has no argument of type IRequest
            return;
        }

        IRequest requestContext = (IRequest) args[idx];
        if (requestContext != null) {
            RequestHelper.setCurrentRequest(requestContext);
            initMDC(requestContext);
        } else {
            if (logger.isWarnEnabled()) {
                logger.warn("{}'s IRequest argument is null.", method);
            }
            return;
        }
        Parameter[] parameters = method.getParameters();

        for (int i = 0; i < types.length; ++i) {
            StdWho who = parameters[i].getAnnotation(StdWho.class);
            if (who != null) {
                if (logger.isTraceEnabled()) {
                    logger.trace("enable StdWho for argument {}, type: {}", i, types[i].getName());
                }
            }
            if (args[i] instanceof IRequestAware) {
                ((IRequestAware) args[i]).setRequest(requestContext);
            }
            if (args[i] instanceof BaseDTO) {
                BaseDTO baseDTO = (BaseDTO) args[i];
                autoAssignStdProperty(logger, requestContext, baseDTO, who != null);
            } else if (args[i] instanceof Collection) {
                for (Object o : (Collection) args[i]) {
                    if (args[i] instanceof IRequestAware) {
                        ((IRequestAware) args[i]).setRequest(requestContext);
                    }
                    if (o instanceof BaseDTO) {
                        autoAssignStdProperty(logger, requestContext, (BaseDTO) o, who != null);
                    }
                }
            }
        }
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object target = invocation.getThis();
        Method method = invocation.getMethod();
        Logger serviceLogger = LoggerFactory.getLogger(target.getClass());
        String methodStamp = target + "#" + method.getName();
        if (logger.isTraceEnabled()) {
            serviceLogger.trace("{}, parameters: {}", methodStamp, Arrays.toString(invocation.getArguments()));
        }
        long ts = System.currentTimeMillis();
        try {
            before(method, invocation.getArguments(), target);
            Object ret = invocation.proceed();
            if (logger.isTraceEnabled()) {
                serviceLogger.trace("{}, return value: {}", methodStamp, ret);
            }
            proceedAutoCacheOperation(invocation, ret);
            return ret;
        } finally {
            long timeUsed = System.currentTimeMillis() - ts;
            if (logger.isTraceEnabled()) {
                logger.trace("{}, execution time: {}ms.", methodStamp, timeUsed);
            }
        }
    }

    /**
     * 检查一个被@Children 标注的属性的类型,是否被支持.
     *
     * @param type class type
     * @return the type is supported or not
     */
    protected boolean checkChildrenType(Class<?> type) {
        if (BaseDTO.class.isAssignableFrom(type)) {
            return true;
        }
        return Collection.class.isAssignableFrom(type);
    }

    private void autoAssignStdProperty(Logger logger, IRequest request, BaseDTO dto, boolean stdWhoSupport) {
        if (stdWhoSupport) {
            for (EntityField f : DTOClassInfo.getIdFields(dto.getClass())) {
                try {
                    if (PropertyUtils.getProperty(dto, f.getName()) == null) {
                        dto.setCreatedBy(request.getUserId());
                        dto.setCreationDate(new Date());
                        break;
                    }
                } catch (Exception e) {
                }
            }
            dto.setLastUpdateDate(new Date());
            dto.setLastUpdatedBy(request.getUserId());
            dto.setLastUpdateLogin(request.getUserId());
        }
        for (EntityField field : DTOClassInfo.getChildrenFields(dto.getClass())) {
            if (!checkChildrenType(field.getJavaType())) {
                if (logger.isWarnEnabled()) {
                    logger.warn("property '{}' is annotated by @Children, incorrect usage.", field.getName());
                }
                return;
            }
            try {
                Object p = PropertyUtils.getProperty(dto, field.getName());
                if (p instanceof BaseDTO) {
                    autoAssignStdProperty(logger, request, (BaseDTO) p, stdWhoSupport);
                } else if (p instanceof Collection) {
                    for (Object o : (Collection) p) {
                        if (o instanceof BaseDTO) {
                            autoAssignStdProperty(logger, request, (BaseDTO) o, stdWhoSupport);
                        }
                    }
                }
            } catch (Exception e) {
                if (logger.isErrorEnabled()) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
    }

    private void proceedAutoCacheOperation(MethodInvocation invocation, Object ret)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Object target = invocation.getThis();
        Method method = invocation.getMethod();
        method = target.getClass().getMethod(method.getName(), method.getParameterTypes());
        CacheSet cacheSet = AnnotationUtils.findAnnotation(method, CacheSet.class);
        if (cacheSet != null) {
            proceedCacheSet(method, cacheSet, ret);
        }
        CacheDelete cacheDelete = AnnotationUtils.findAnnotation(method, CacheDelete.class);
        if (cacheDelete != null) {
            proceedCacheDelete(invocation, cacheDelete);
        }
    }

    @SuppressWarnings("unchecked")
    private void proceedCacheSet(Method method, CacheSet cacheSet, Object ret)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String cacheName = cacheSet.cache();
        if (!StringUtils.hasText(cacheName)) {
            throw new IllegalArgumentException("cache: " + cacheName + " is not valid.");
        }
        Cache cache = cacheManager.getCache(cacheName);
        if (cache == null) {
            if (logger.isWarnEnabled()) {
                logger.warn("cache: {} not found.", cacheName);
            }
            return;
        }
        String key = cache.getCacheKey(ret);
        if (cache instanceof HashStringRedisCache) {
            String valueField = ((HashStringRedisCache) cache).getValueField();
            if (valueField != null) {
                ret = PropertyUtils.getProperty(ret, valueField);
            }
        }
        cache.setValue(key, ret);
        if (logger.isDebugEnabled()) {
            logger.debug("{} cache auto set. key={}", method.getName(), key);
        }
    }

    @SuppressWarnings("unchecked")
    private void proceedCacheDelete(MethodInvocation invocation, CacheDelete cacheDelete) throws NoSuchMethodException {
        String cacheName = cacheDelete.cache();
        if (!StringUtils.hasText(cacheName)) {
            throw new IllegalArgumentException("cache: " + cacheName + " is not valid.");
        }
        Cache cache = cacheManager.getCache(cacheName);
        if (cache == null) {
            if (logger.isWarnEnabled()) {
                logger.warn("cache: {} not found.", cacheName);
            }
            return;
        }
        Object dto = invocation.getArguments()[0];
        String key = cache.getCacheKey(dto);
        if (cache instanceof HashStringRedisCacheGroup) {
            HashStringRedisCacheGroup hashStringRedisCacheGroup = (HashStringRedisCacheGroup) cache;
            String group = hashStringRedisCacheGroup.getGroupValue(dto);
            hashStringRedisCacheGroup.remove(group, key);
        } else {
            cache.remove(key);
        }

        if (logger.isDebugEnabled()) {
            logger.debug("{} cache auto remove. key={}", invocation.getMethod().getName(), key);
        }
    }
}
