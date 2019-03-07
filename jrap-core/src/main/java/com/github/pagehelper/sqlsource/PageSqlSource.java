package com.github.pagehelper.sqlsource;

import com.github.pagehelper.SqlUtil;
import com.github.pagehelper.parser.Parser;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.core.components.ApplicationContextHelper;
import com.jingrui.jrap.core.impl.RequestHelper;
import com.jingrui.jrap.security.permission.DataPermissionUtil;
import com.jingrui.jrap.security.permission.service.impl.DataPermissionCacheContainer;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

/**
 * 描述信息
 *
 * @author liuzh
 * @since 2015-06-29
 */
public abstract class PageSqlSource implements SqlSource {

    protected static final ThreadLocal<Parser> localParser = new ThreadLocal<Parser>();

    public void setParser(Parser parser) {
        localParser.set(parser);
    }

    public void removeParser(){
        localParser.remove();
    }

    /**
     * 返回值null - 普通,true - count,false - page
     *
     * @return
     */
    protected Boolean getCount() {
        return SqlUtil.getCOUNT();
    }

    /**
     * 获取正常的BoundSql
     *
     * @param parameterObject
     * @return
     */
    protected abstract BoundSql getDefaultBoundSql(Object parameterObject);

    /**
     * 获取Count查询的BoundSql
     *
     * @param parameterObject
     * @return
     */
    protected abstract BoundSql getCountBoundSql(Object parameterObject);

    /**
     * 获取分页查询的BoundSql
     *
     * @param parameterObject
     * @return
     */
    protected abstract BoundSql getPageBoundSql(Object parameterObject);

    /**
     * 获取BoundSql
     *
     * @param parameterObject
     * @return
     */
    @Override
    public BoundSql getBoundSql(Object parameterObject) {
        Boolean count = getCount();
        IRequest request = RequestHelper.getCurrentRequest();
        BoundSql boundSql;
        if (count == null) {
            boundSql = getDefaultBoundSql(parameterObject);
        } else if (count) {
            boundSql = getCountBoundSql(parameterObject);
        } else {
            boundSql = getPageBoundSql(parameterObject);
        }
        if(null == ApplicationContextHelper.getApplicationContext()||null ==ApplicationContextHelper.getApplicationContext().getBean(DataPermissionCacheContainer.class)){
            return boundSql;
        }
        String oldSql=boundSql.getSql();
        DataPermissionCacheContainer container=ApplicationContextHelper.getApplicationContext().getBean(DataPermissionCacheContainer.class);
        if(container.needPermission(oldSql)) {
            String newSql = DataPermissionUtil.getNewSql(oldSql, request);
            MetaObject metaObject = SystemMetaObject.forObject(boundSql);
            metaObject.setValue("sql", newSql);
        }
        return boundSql;
    }
}
