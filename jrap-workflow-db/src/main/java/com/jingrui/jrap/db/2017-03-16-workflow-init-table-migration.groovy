package com.jingrui.jrap.db

import com.jingrui.jrap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()

databaseChangeLog(logicalFilePath:"2017-03-16-init-table-migration.groovy"){

    changeSet(author: "qixiangyu", id: "20170316-wfl-approve-candidate-1") {

        if(mhi.getDbType().isSupportSequence()){
           createSequence(sequenceName: 'WFL_APPROVE_CANDIDATE_RULE_S',startValue:"10001")
        }

        createTable (tableName: "WFL_APPROVE_CANDIDATE_RULE", remarks: "候选人审批规则") {
            if (mhi.getDbType().isSuppportAutoIncrement()) {
                column(name: "CANDIDATE_RULE_ID", type: "BIGINT", autoIncrement: "true", startWith: "10001", remarks: "PK") {
                    constraints(nullable: "false", primaryKey: "true",primaryKeyName:"WFL_APPROVE_CANDIDATE_PK")
                }
            } else {
                column(name: "CANDIDATE_RULE_ID", type: "BIGINT", remarks: "PK") {
                    constraints(nullable: "false", primaryKey: "true",primaryKeyName:"WFL_APPROVE_CANDIDATE_PK")
                }
            }


            column(name: "CODE", type:"VARCHAR(50)",remarks: "选人规则的唯一标示") {
                constraints(nullable: "false")
            }
            column(name: "DESCRIPTION", type:"VARCHAR(255)",remarks: "描述") {
                constraints(nullable: "false")
            }
            column(name: "EXPRESSION",type:"VARCHAR(100)",remarks: "activiti表达式"){
                constraints(nullable: "false")
            }
            column(name: "ENABLE_FLAG",type:"VARCHAR(5)",remarks: "启用标志")

            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue : "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "CREATED_BY", type: "BIGINT", defaultValue : "-1")
            column(name: "CREATION_DATE", type: "DATETIME", defaultValueComputed : "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", defaultValueComputed : "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")
        }
        if(!mhi.isDbType('postgresql')){
            addUniqueConstraint(columnNames:"CODE",tableName:"WFL_APPROVE_CANDIDATE_RULE",constraintName: "WFL_APPROVE_CANDIDATE_U1")
        }else {
            addUniqueConstraint(columnNames:"CANDIDATE_RULE_ID,CODE",tableName:"WFL_APPROVE_CANDIDATE_RULE",constraintName: "WFL_APPROVE_CANDIDATE_U1")
        }

    }

    changeSet(author: "qixiangyu", id: "20170316-wfl-approve-strategy-1") {

        if(mhi.getDbType().isSupportSequence()){
           createSequence(sequenceName: 'WFL_APPROVE_STRATEGY_S',startValue:"10001")
        }
        createTable (tableName: "WFL_APPROVE_STRATEGY", remarks: "审批策略") {
            if (mhi.getDbType().isSuppportAutoIncrement()) {
                column(name: "APPROVE_STRATEGY_ID", type: "BIGINT", autoIncrement: "true", startWith: "10001", remarks: "PK") {
                    constraints(nullable: "false", primaryKey: "true",primaryKeyName:"WFL_APPROVE_STRATEGY_PK")
                }
            } else {
                column(name: "APPROVE_STRATEGY_ID", type: "BIGINT", remarks: "PK") {
                    constraints(nullable: "false", primaryKey: "true",primaryKeyName:"WFL_APPROVE_STRATEGY_PK")
                }
            }

            column(name: "CODE", type:"VARCHAR(50)",remarks: "策略的唯一标识") {
                constraints(nullable: "false")
            }
            column(name: "DESCRIPTION", type:"VARCHAR(255)",remarks: "描述") {
                constraints(nullable: "false")
            }
            column(name: "CONDITIONS",type:"VARCHAR(500)",remarks: "审批通过条件"){
                constraints(nullable: "false")
            }
            column(name: "ENABLE_FLAG",type:"VARCHAR(5)",remarks: "启用标志")

            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue : "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "CREATED_BY", type: "BIGINT", defaultValue : "-1")
            column(name: "CREATION_DATE", type: "DATETIME", defaultValueComputed : "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", defaultValueComputed : "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")
        }
        if(!mhi.isDbType('postgresql')){
            addUniqueConstraint(columnNames:"CODE",tableName:"WFL_APPROVE_STRATEGY",constraintName: "WFL_APPROVE_STRATEGY_U1")
        }else {
            addUniqueConstraint(columnNames:"APPROVE_STRATEGY_ID,CODE",tableName:"WFL_APPROVE_STRATEGY",constraintName: "WFL_APPROVE_STRATEGY_U1")
        }

    }

    changeSet(author: "qixiangyu", id: "20170320-wfl-approve-business-rule-header-1") {

        if(mhi.getDbType().isSupportSequence()){
           createSequence(sequenceName: 'WFL_BUSINESS_RULE_HEADER_S',startValue:"10001")
        }
        createTable (tableName: "WFL_BUSINESS_RULE_HEADER", remarks: "审批权限规则 头表") {
            if (mhi.getDbType().isSuppportAutoIncrement()){
                column(name: "BUSINESS_RULE_ID", type: "BIGINT", autoIncrement: "true", startWith: "10001", remarks: "PK") {
                    constraints(nullable: "false", primaryKey: "true",primaryKeyName:"WFL_BUSINESS_RULE_HEADER_PK")
                }
            } else {
                column(name: "BUSINESS_RULE_ID", type: "BIGINT", remarks: "PK") {
                    constraints(nullable: "false", primaryKey: "true",primaryKeyName:"WFL_BUSINESS_RULE_HEADER_PK")
                }
            }

            column(name: "CODE", type:"VARCHAR(50)",remarks: "审批规则的唯一标识") {
                constraints(nullable: "false")
            }
            column(name: "DESCRIPTION", type:"VARCHAR(255)",remarks: "描述") {
                constraints(nullable: "false")
            }
            column(name: "WFL_TYPE", type:"VARCHAR(50)",remarks: "工作流类型")
            column(name: "ENABLE_FLAG",type:"VARCHAR(5)",remarks: "启用标志")
            column(name:"START_ACTIVE_DATE",type:"DATETIME",remarks: "有效期从")
            column(name:"END_ACTIVE_DATE",type:"DATETIME",remarks: "有效期至")

            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue : "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "CREATED_BY", type: "BIGINT", defaultValue : "-1")
            column(name: "CREATION_DATE", type: "DATETIME", defaultValueComputed : "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", defaultValueComputed : "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")
        }
        if(!mhi.isDbType('postgresql')){
            addUniqueConstraint(columnNames:"CODE",tableName:"WFL_BUSINESS_RULE_HEADER",constraintName: "WFL_BUSINESS_RULE_HEADER_U1")
        }else {
            addUniqueConstraint(columnNames:"BUSINESS_RULE_ID,CODE",tableName:"WFL_BUSINESS_RULE_HEADER",constraintName: "WFL_BUSINESS_RULE_HEADER_U1")
        }

    }

    changeSet(author: "qixiangyu", id: "20170320-wfl-approve-business-rule-line-1") {

        if(mhi.getDbType().isSupportSequence()){
           createSequence(sequenceName: 'WFL_BUSINESS_RULE_LINE_S',startValue:"10001")
        }
        createTable (tableName: "WFL_BUSINESS_RULE_LINE", remarks: "审批权限规则 行表") {
            if (mhi.getDbType().isSuppportAutoIncrement()) {
                column(name: "BUSINESS_RULE_LINE_ID", type: "BIGINT", autoIncrement: "true", startWith: "10001", remarks: "PK") {
                    constraints(nullable: "false", primaryKey: "true",primaryKeyName:"WFL_BUSINESS_RULE_LINE_PK")
                }
            } else {
                column(name: "BUSINESS_RULE_LINE_ID", type: "BIGINT", remarks: "PK") {
                    constraints(nullable: "false", primaryKey: "true",primaryKeyName:"WFL_BUSINESS_RULE_LINE_PK")
                }
            }

            column(name: "BUSINESS_RULE_ID", type: "BIGINT", remarks: "头 id")
            column(name: "DESCRIPTION", type:"VARCHAR(255)",remarks: "描述") {
                constraints(nullable: "false")
            }
            column(name: "CONDITIONS",type:"VARCHAR(500)",remarks: "审批权限表达式"){
                constraints(nullable: "false")
            }
            column(name: "ENABLE_FLAG",type:"VARCHAR(5)",remarks: "启用标志")

            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue : "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "CREATED_BY", type: "BIGINT", defaultValue : "-1")
            column(name: "CREATION_DATE", type: "DATETIME", defaultValueComputed : "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", defaultValueComputed : "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")
        }
    }


}