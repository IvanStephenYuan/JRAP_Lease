package com.jingrui.jrap.activiti.custom;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.impl.db.DbSqlSession;
import org.activiti.engine.impl.db.DbSqlSessionFactory;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.interceptor.Session;

import java.sql.SQLException;

/**
 * @author njq.niu@jingrui.com
 */
public class CustomDbSqlSessionFactory extends DbSqlSessionFactory {


    public Session openSession(CommandContext commandContext) {
        DbSqlSession dbSqlSession = new CustomDbSqlSession(this, commandContext.getEntityCache());
        if (getDatabaseSchema() != null && getDatabaseSchema().length() > 0) {
            try {
                dbSqlSession.getSqlSession().getConnection().setSchema(getDatabaseSchema());
            } catch (SQLException e) {
                throw new ActivitiException("Could not set database schema on connection", e);
            }
        }
        if (getDatabaseCatalog() != null && getDatabaseCatalog().length() > 0) {
            try {
                dbSqlSession.getSqlSession().getConnection().setCatalog(getDatabaseCatalog());
            } catch (SQLException e) {
                throw new ActivitiException("Could not set database catalog on connection", e);
            }
        }
        return dbSqlSession;
    }

}