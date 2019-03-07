package com.jingrui.jrap.activiti.custom;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.impl.db.DbSqlSession;
import org.activiti.engine.impl.db.DbSqlSessionFactory;
import org.activiti.engine.impl.db.HasRevision;
import org.activiti.engine.impl.persistence.cache.EntityCache;
import org.activiti.engine.impl.persistence.entity.Entity;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * @author njq.niu@jingrui.com
 */
public class CustomDbSqlSession extends DbSqlSession {

    public CustomDbSqlSession(DbSqlSessionFactory dbSqlSessionFactory, EntityCache entityCache) {
        super(dbSqlSessionFactory, entityCache);
    }

    public CustomDbSqlSession(DbSqlSessionFactory dbSqlSessionFactory, EntityCache entityCache, Connection connection, String catalog, String schema) {
        super(dbSqlSessionFactory, entityCache, connection, catalog, schema);
    }

    protected void flushBulkInsert(Collection<Entity> entities, Class<? extends Entity> clazz) {
        String insertStatement = dbSqlSessionFactory.getInsertStatement(clazz);
        insertStatement = dbSqlSessionFactory.mapStatement(insertStatement);

        if (insertStatement == null) {
            throw new ActivitiException("no insert statement for " + entities.iterator().next().getClass() + " in the ibatis mapping files");
        }

        Iterator<Entity> entityIterator = entities.iterator();
        Boolean hasRevision = null;

        while (entityIterator.hasNext()) {
            List<Entity> subList = new ArrayList<Entity>();
            int index = 0;
            while (entityIterator.hasNext() && index < dbSqlSessionFactory.getMaxNrOfStatementsInBulkInsert()) {
                Entity entity = entityIterator.next();
                subList.add(entity);

                if (hasRevision == null) {
                    hasRevision = entity instanceof HasRevision;
                }
                index++;
            }
            for (Entity entity : subList) {
                sqlSession.insert(insertStatement, entity);
            }

        }

        if (hasRevision != null && hasRevision) {
            entityIterator = entities.iterator();
            while (entityIterator.hasNext()) {
                incrementRevision(entityIterator.next());
            }
        }

    }
}
