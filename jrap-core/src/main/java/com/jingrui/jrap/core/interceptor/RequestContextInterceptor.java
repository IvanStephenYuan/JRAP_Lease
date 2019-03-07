/*
 * #{copyright}#
 */
package com.jingrui.jrap.core.interceptor;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.transaction.Transaction;

import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.core.impl.RequestHelper;

/**
 * 自动绑定 IRequest 参数到 BoundSql.
 * <p>
 * 拦截点:
 * <ul>
 * <li>增删改查时的参数绑定</li>
 * <li>查询时的,cacheKey获取</li>
 * </ul>
 * 
 * @author shengyang.zhou@jingrui.com
 */
@Intercepts({
        @Signature(type = Executor.class, method = "createCacheKey", args = { MappedStatement.class, Object.class,
                RowBounds.class, BoundSql.class }),
        @Signature(type = StatementHandler.class, method = "parameterize", args = { Statement.class }) })
public class RequestContextInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object target = invocation.getTarget();
        if (target instanceof StatementHandler) {
            StatementHandler handler = (StatementHandler) target;
            BoundSql boundSql = handler.getBoundSql();
            IRequest request = RequestHelper.getCurrentRequest(true);
            boundSql.setAdditionalParameter("request", request);
        }

        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        }
        if (target instanceof Executor) {
            final Executor e = (Executor) target;
            Executor executor = new Executor() {
                public int update(MappedStatement ms, Object parameter) throws SQLException {
                    return e.update(ms, parameter);
                }

                public <E> List<E> query(MappedStatement ms, Object parameter, RowBounds rowBounds,
                        ResultHandler resultHandler, CacheKey cacheKey, BoundSql boundSql) throws SQLException {
                    return e.query(ms, parameter, rowBounds, resultHandler, cacheKey, boundSql);
                }

                public <E> List<E> query(MappedStatement ms, Object parameter, RowBounds rowBounds,
                        ResultHandler resultHandler) throws SQLException {
                    BoundSql boundSql = ms.getBoundSql(parameter);
                    CacheKey key = createCacheKey(ms, parameter, rowBounds, boundSql);
                    return query(ms, parameter, rowBounds, resultHandler, key, boundSql);
                }

                public List<BatchResult> flushStatements() throws SQLException {
                    return e.flushStatements();
                }

                public void commit(boolean required) throws SQLException {
                    e.commit(required);
                }

                public void rollback(boolean required) throws SQLException {
                    e.rollback(required);
                }

                public CacheKey createCacheKey(MappedStatement ms, Object parameterObject, RowBounds rowBounds,
                        BoundSql boundSql) {
                    IRequest request = RequestHelper.getCurrentRequest(true);
                    boundSql.setAdditionalParameter("request", request);
                    return e.createCacheKey(ms, parameterObject, rowBounds, boundSql);
                }

                public boolean isCached(MappedStatement ms, CacheKey key) {
                    return e.isCached(ms, key);
                }

                public void clearLocalCache() {
                    e.clearLocalCache();
                }

                public void deferLoad(MappedStatement ms, MetaObject resultObject, String property, CacheKey key,
                        Class<?> targetType) {
                    e.deferLoad(ms, resultObject, property, key, targetType);
                }

                public Transaction getTransaction() {
                    return e.getTransaction();
                }

                public void close(boolean forceRollback) {
                    e.close(forceRollback);
                }

                public boolean isClosed() {
                    return e.isClosed();
                }

                public void setExecutorWrapper(Executor executor) {
                    e.setExecutorWrapper(executor);
                }
            };

            return executor;
            // return Plugin.wrap(executor, this);
        }
        return target;
    }

    @Override
    public void setProperties(Properties properties) {

    }
}