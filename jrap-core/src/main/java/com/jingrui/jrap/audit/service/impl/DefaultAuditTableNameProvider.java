/*
 * #{copyright}#
 */

package com.jingrui.jrap.audit.service.impl;


import com.jingrui.jrap.audit.service.IAuditTableNameProvider;

/**
 * default impl, add '_a' suffix to baseTableName.
 *
 * @author shengyang.zhou@jingrui.com
 */
public class DefaultAuditTableNameProvider implements IAuditTableNameProvider {

    public static DefaultAuditTableNameProvider instance = new DefaultAuditTableNameProvider();

    private DefaultAuditTableNameProvider() {
    }

    @Override
    public String getAuditTableName(String baseTableName) {
        return baseTableName + "_a";
    }
}
