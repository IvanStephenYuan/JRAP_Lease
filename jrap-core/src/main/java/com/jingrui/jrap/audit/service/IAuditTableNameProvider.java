package com.jingrui.jrap.audit.service;

/**
 * translate baseTableName to auditTableName.
 *
 * @author shengyang.zhou@jingrui.com
 */
public interface IAuditTableNameProvider {

    /**
     * @param baseTableName base table name
     * @return audit table name
     */
    String getAuditTableName(String baseTableName);
}
