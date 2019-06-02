package com.jingrui.jrap.db.data

import com.jingrui.jrap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath:"2016-09-26-init-migration.groovy"){

    changeSet(author: "jialong.zuo", id: "20170330-wfl-demo-vacation-1") {

        if (mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'ACT_DEMO_VACATION_S', startValue: "10001")
        }
        createTable(tableName: "ACT_DEMO_VACATION") {
            if (mhi.getDbType().isSupportAutoIncrement()) {
                column(name: "ID", type: "bigint", autoIncrement: "true", startWith: "10001", remarks: "表ID，主键，供其他表做外键") {
                    constraints(nullable: "false", primaryKey: "true", primaryKeyName: "ACT_DEMO_VACATION_PK")
                }
            } else {
                column(name: "ID", type: "bigint", remarks: "表ID，主键，供其他表做外键") {
                    constraints(nullable: "false", primaryKey: "true", primaryKeyName: "ACT_DEMO_VACATION_PK")
                }
            }

            column(name: "USER_CODE", type: "VARCHAR(225)", remarks: "用户编码") {
                constraints(nullable: "false")
            }
            column(name: "START_DATE", type: "DATETIME") {
                constraints(nullable: "false")
            }
            column(name: "NEED_DAYS", type: "DECIMAL(4,1)", remarks: "请假天数") {
                constraints(nullable: "false")
            }
            column(name: "LEAVE_REASON", type: "VARCHAR(255)", remarks: "请假理由")

        }
    }

    changeSet(author: "jialong.zuo", id: "20170405-wfl-exception") {

        if (mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'ACT_EXCEPTION_S', startValue: "10001")
        }
        createTable(tableName: "ACT_EXCEPTION") {
            if (mhi.getDbType().isSupportAutoIncrement()) {
                column(name: "ID", type: "bigint", autoIncrement: "true", startWith: "10001", remarks: "表ID，主键，供其他表做外键") {
                    constraints(nullable: "false", primaryKey: "true", primaryKeyName: "ACT_EXCEPTION_PK")
                }
            } else {
                column(name: "ID", type: "bigint", remarks: "表ID，主键，供其他表做外键") {
                    constraints(nullable: "false", primaryKey: "true", primaryKeyName: "ACT_EXCEPTION_PK")
                }
            }

            column(name: "PROC_ID", type: "VARCHAR(225)", remarks: "流程id") {
                constraints(nullable: "false")
            }
            column(name: "MESSAGE", type: "LONGBLOB", remarks: "异常信息") {
                constraints(nullable: "false")
            }
            column(name: "DUEDATE", type: "DATETIME") {
                constraints(nullable: "false")
            }

        }
    }

    changeSet(author: "jessen", id: "20161218-wfl-approve-chain-header-1") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'WFL_APPROVE_CHAIN_HEADER_S',startValue:"10001")
        }

        createTable (tableName: "WFL_APPROVE_CHAIN_HEADER", remarks: "审批链定义 头表") {
            if (mhi.getDbType().isSupportAutoIncrement()){
                column(name: "APPROVE_CHAIN_ID", type: "BIGINT", autoIncrement: "true", startWith: "10001", remarks: "PK") {
                    constraints(nullable: "false", primaryKey: "true",primaryKeyName:"WFL_APPROVE_CHAIN_HEADER_PK")
                }
            } else {
                column(name: "APPROVE_CHAIN_ID", type: "BIGINT", remarks: "PK") {
                    constraints(nullable: "false", primaryKey: "true",primaryKeyName:"WFL_APPROVE_CHAIN_HEADER_PK")
                }
            }


            column(name: "PROCESS_KEY", type:"VARCHAR(255)") {
                constraints(nullable: "false")
            }
            column(name: "USERTASK_ID", type:"VARCHAR(255)") {
                constraints(nullable: "false")
            }
            column(name:"ENABLE_FLAG",type:"VARCHAR(1)",remarks: "是否启用", defaultValue: "Y")

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
            addUniqueConstraint(columnNames:"PROCESS_KEY,USERTASK_ID",tableName:"WFL_APPROVE_CHAIN_HEADER",constraintName: "WFL_APPROVE_CHAIN_HEADER_U1")
        }else {
            addUniqueConstraint(columnNames:"APPROVE_CHAIN_ID,PROCESS_KEY,USERTASK_ID",tableName:"WFL_APPROVE_CHAIN_HEADER",constraintName: "WFL_APPROVE_CHAIN_HEADER_U1")
        }

    }

    changeSet(author: "jessen", id: "20161218-wfl-approve-chain-line-1") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'WFL_APPROVE_CHAIN_LINE_S',startValue:"10001")
        }

        createTable (tableName: "WFL_APPROVE_CHAIN_LINE", remarks: "审批链定义 行表") {
            if (mhi.getDbType().isSupportAutoIncrement()){
                column(name: "APPROVE_CHAIN_LINE_ID", type: "BIGINT", autoIncrement: "true", startWith: "10001", remarks: "PK") {
                    constraints(nullable: "false", primaryKey: "true",primaryKeyName:"WFL_APPROVE_CHAIN_LINE_PK")
                }
            } else {
                column(name: "APPROVE_CHAIN_LINE_ID", type: "BIGINT", remarks: "PK") {
                    constraints(nullable: "false", primaryKey: "true",primaryKeyName:"WFL_APPROVE_CHAIN_LINE_PK")
                }
            }

            column(name: "APPROVE_CHAIN_ID", type: "BIGINT", remarks: "头 id")

            if(!mhi.isDbType('postgresql')){
                column(name: "NAME", type: "VARCHAR(255)", remarks: "名称") {
                    constraints(nullable: "false", unique: "true", uniqueConstraintName:"WFL_APPROVE_CHAIN_LINE_U1")
                }
            }else {
                column(name: "NAME", type: "VARCHAR(255)", remarks: "名称") {
                    constraints(nullable: "false")
                }
            }

            column(name: "DESCRIPTION", type: "VARCHAR(255)", remarks: "描述")

            column(name: "APPROVE_TYPE",type:"VARCHAR(40)", defaultValue: "NONE",remarks: "审批类型")
            column(name: "ASSIGNEE",type:"VARCHAR(200)", remarks: "审批人")
            column(name: "ASSIGN_GROUP",type:"VARCHAR(200)", remarks: "审批组")
            column(name: "FORM_KEY",type:"VARCHAR(255)", remarks: "审批页面")

            column(name: "SEQUENCE", type: "DECIMAL(18,2)", defaultValue: "10", remarks: "排序号，可以为小数")
            column(name: "SKIP_EXPRESSION", type: "VARCHAR(255)", remarks: "跳过条件,留空表示不跳过 (false)")
            column(name: "BREAK_ON_SKIP", type: "VARCHAR(1)",defaultValue: "N", remarks: "该规则被跳过时,停止继续走其他规则")
            column(name: "ENABLE_FLAG",type:"VARCHAR(1)", defaultValue: "Y",remarks: "是否启用")

            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue : "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "CREATED_BY", type: "BIGINT", defaultValue : "-1")
            column(name: "CREATION_DATE", type: "DATETIME", defaultValueComputed : "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", defaultValueComputed : "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")
        }
        if(mhi.isDbType('postgresql')){
            addUniqueConstraint(columnNames: "APPROVE_CHAIN_LINE_ID,NAME", tableName: "WFL_APPROVE_CHAIN_LINE", constraintName: "WFL_APPROVE_CHAIN_LINE_U1")
        }
        createIndex(tableName:"WFL_APPROVE_CHAIN_LINE",indexName:"WFL_APPROVE_CHAIN_LINE_N1") {
            column(name: "APPROVE_CHAIN_ID", type: "BIGINT")
        }
    }


}
