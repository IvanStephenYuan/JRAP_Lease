package com.jingrui.jrap.db

import com.jingrui.jrap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath:"2016-06-09-init-table-migration.groovy"){
    changeSet(author: "hailor", id: "20160609-hailor-10") {
        // sqlFile(path: MigrationHelper.getInstance().dataPath("com/jingrui/jrap/db/data/"+dbType+"/tables/sys_message.sql"), encoding: "UTF-8")
        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'SYS_MESSAGE_S', startValue:"10001")
        }
        createTable(tableName: "SYS_MESSAGE"){
            if (mhi.getDbType().isSuppportAutoIncrement()){
                column(name:"MESSAGE_ID",type:"bigint",autoIncrement: "true", startWith:"10001",remarks: "表ID，主键"){
                    constraints(nullable: "false", primaryKey: "true",primaryKeyName: "SYS_MESSAGE_PK")
                }
            } else {
                column(name:"MESSAGE_ID",type:"bigint",remarks: "表ID，主键"){
                    constraints(nullable: "false", primaryKey: "true",primaryKeyName: "SYS_MESSAGE_PK")
                }
            }

            column(name:"MESSAGE_TYPE",type:"varchar(10)",remarks: "信息类型,email/sms")
            column(name:"MESSAGE_HOST",type:"varchar(255)",remarks: "消息服务器")
            column(name:"MESSAGE_FROM",type:"varchar(255)", remarks: "消息发出人")
            column(name:"SUBJECT",type:"varchar(255)",remarks: " 标题")
            column(name:"CONTENT",type:"clob",remarks: "内容")
            column(name:"PRIORITY_LEVEL",type:"varchar(25)",remarks: "优先级")
            column(name:"SEND_FLAG",type: "varchar(1)",defaultValue: "N", remarks: "发送标记")
            column(name:"MESSAGE_SOURCE",type: "varchar(255)",remarks: "消息来源")
            column(name:"OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "bigint", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "bigint", defaultValue : "-1")
            column(name: "CREATED_BY", type: "bigint", defaultValue : "-1")
            column(name: "CREATION_DATE", type: "datetime", defaultValueComputed : "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATED_BY", type: "bigint", defaultValue : "-1")
            column(name: "LAST_UPDATE_DATE", type: "datetime", defaultValueComputed : "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATE_LOGIN", type: "bigint", defaultValue : "-1")
            column(name:"ATTRIBUTE_CATEGORY",type:"varchar(30)")
            column(name:"ATTRIBUTE1",type:"varchar(240)")
            column(name:"ATTRIBUTE2",type:"varchar(240)")
            column(name:"ATTRIBUTE3",type:"varchar(240)")
            column(name:"ATTRIBUTE4",type:"varchar(240)")
            column(name:"ATTRIBUTE5",type:"varchar(240)")
            column(name:"ATTRIBUTE6",type:"varchar(240)")
            column(name:"ATTRIBUTE7",type:"varchar(240)")
            column(name:"ATTRIBUTE8",type:"varchar(240)")
            column(name:"ATTRIBUTE9",type:"varchar(240)")
            column(name:"ATTRIBUTE10",type:"varchar(240)")
            column(name:"ATTRIBUTE11",type:"varchar(240)")
            column(name:"ATTRIBUTE12",type:"varchar(240)")
            column(name:"ATTRIBUTE13",type:"varchar(240)")
            column(name:"ATTRIBUTE14",type:"varchar(240)")
            column(name:"ATTRIBUTE15",type:"varchar(240)")
        }
        createIndex(tableName:"SYS_MESSAGE",indexName:"SYS_MESSAGE_N1"){column(name: "MESSAGE_TYPE",type: "varchar(10)")}

    }

    changeSet(author: "hailor", id: "20160609-hailor-11") {
        // sqlFile(path: MigrationHelper.getInstance().dataPath("com/jingrui/jrap/db/data/"+dbType+"/tables/sys_message_account.sql"), encoding: "UTF-8")
        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'SYS_MESSAGE_ACCOUNT_S', startValue:"10001")
        }
        createTable(tableName: "SYS_MESSAGE_ACCOUNT"){
            if (mhi.getDbType().isSuppportAutoIncrement()){
                column(name:"ACCOUNT_ID",type:"bigint",autoIncrement: "true", startWith:"10001",remarks: "表ID，主键"){
                    constraints(nullable: "false", primaryKey: "true",primaryKeyName: "SYS_MESSAGE_ACCOUNT_PK")
                }
            } else {
                column(name:"ACCOUNT_ID",type:"bigint",remarks: "表ID，主键"){
                    constraints(nullable: "false", primaryKey: "true",primaryKeyName: "SYS_MESSAGE_ACCOUNT_PK")
                }
            }

            column(name:"ACCOUNT_TYPE",type:"varchar(50)",remarks: "账号类型")
            column(name:"ACCOUNT_CODE",type:"varchar(50)",remarks: "编号")
            column(name:"USER_NAME",type:"varchar(240)", remarks: "用户名")
            column(name:"PASSWORD",type:"varchar(240)",remarks: " 密码")
            column(name:"DESCRIPTION",type:"varchar(240)",remarks: "描述")
            column(name:"OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "bigint", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "bigint", defaultValue : "-1")
            column(name: "CREATED_BY", type: "bigint", defaultValue : "-1")
            column(name: "CREATION_DATE", type: "datetime", defaultValueComputed : "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATED_BY", type: "bigint", defaultValue : "-1")
            column(name: "LAST_UPDATE_DATE", type: "datetime", defaultValueComputed : "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATE_LOGIN", type: "bigint", defaultValue : "-1")
            column(name:"ATTRIBUTE_CATEGORY",type:"varchar(30)")
            column(name:"ATTRIBUTE1",type:"varchar(240)")
            column(name:"ATTRIBUTE2",type:"varchar(240)")
            column(name:"ATTRIBUTE3",type:"varchar(240)")
            column(name:"ATTRIBUTE4",type:"varchar(240)")
            column(name:"ATTRIBUTE5",type:"varchar(240)")
            column(name:"ATTRIBUTE6",type:"varchar(240)")
            column(name:"ATTRIBUTE7",type:"varchar(240)")
            column(name:"ATTRIBUTE8",type:"varchar(240)")
            column(name:"ATTRIBUTE9",type:"varchar(240)")
            column(name:"ATTRIBUTE10",type:"varchar(240)")
            column(name:"ATTRIBUTE11",type:"varchar(240)")
            column(name:"ATTRIBUTE12",type:"varchar(240)")
            column(name:"ATTRIBUTE13",type:"varchar(240)")
            column(name:"ATTRIBUTE14",type:"varchar(240)")
            column(name:"ATTRIBUTE15",type:"varchar(240)")
        }
    }

    changeSet(author: "jessen", id: "20160611-sys-message-transaction-1") {
        // sqlFile(path: MigrationHelper.getInstance().dataPath("com/jingrui/jrap/db/data/"+dbType+"/tables/sys_message_transaction.sql"), encoding: "UTF-8")
        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'SYS_MESSAGE_TRANSACTION_S', startValue:"10001")
        }
        createTable(tableName: "SYS_MESSAGE_TRANSACTION"){
            if (mhi.getDbType().isSuppportAutoIncrement()){
                column(name:"TRANSACTION_ID",type:"bigint",autoIncrement: "true", startWith:"10001",remarks: "表ID，主键"){
                    constraints(nullable: "false", primaryKey: "true",primaryKeyName: "SYS_MESSAGE_TRANSACTION_PK")
                }
            } else {
                column(name:"TRANSACTION_ID",type:"bigint",remarks: "表ID，主键"){
                    constraints(nullable: "false", primaryKey: "true",primaryKeyName: "SYS_MESSAGE_TRANSACTION_PK")
                }
            }

            column(name:"MESSAGE_ID",type:"bigint",remarks: "消息id")
            column(name:"TRANSACTION_STATUS",type:"varchar(25)",remarks: "发送状态")
            column(name:"TRANSACTION_MESSAGE",type:"clob",remarks: "返回信息")
            column(name:"MESSAGE_ADDRESS",type:"varchar(255)",remarks: "消息地址,邮箱/手机号/会员/用户")
            column(name:"OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "bigint", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "bigint", defaultValue : "-1")
            column(name: "CREATED_BY", type: "bigint", defaultValue : "-1")
            column(name: "CREATION_DATE", type: "datetime", defaultValueComputed : "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATED_BY", type: "bigint", defaultValue : "-1")
            column(name: "LAST_UPDATE_DATE", type: "datetime", defaultValueComputed : "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATE_LOGIN", type: "bigint", defaultValue : "-1")
            column(name:"ATTRIBUTE_CATEGORY",type:"varchar(30)")
            column(name:"ATTRIBUTE1",type:"varchar(240)")
            column(name:"ATTRIBUTE2",type:"varchar(240)")
            column(name:"ATTRIBUTE3",type:"varchar(240)")
            column(name:"ATTRIBUTE4",type:"varchar(240)")
            column(name:"ATTRIBUTE5",type:"varchar(240)")
            column(name:"ATTRIBUTE6",type:"varchar(240)")
            column(name:"ATTRIBUTE7",type:"varchar(240)")
            column(name:"ATTRIBUTE8",type:"varchar(240)")
            column(name:"ATTRIBUTE9",type:"varchar(240)")
            column(name:"ATTRIBUTE10",type:"varchar(240)")
            column(name:"ATTRIBUTE11",type:"varchar(240)")
            column(name:"ATTRIBUTE12",type:"varchar(240)")
            column(name:"ATTRIBUTE13",type:"varchar(240)")
            column(name:"ATTRIBUTE14",type:"varchar(240)")
            column(name:"ATTRIBUTE15",type:"varchar(240)")
        }
        createIndex(tableName:"SYS_MESSAGE_TRANSACTION",indexName:"SYS_MESSAGE_TRANSACTION_N1"){column(name: "MESSAGE_ID",type: "bigint")}
        createIndex(tableName:"SYS_MESSAGE_TRANSACTION",indexName:"SYS_MESSAGE_TRANSACTION_N2"){column(name: "TRANSACTION_STATUS",type: "varchar(25)")}

    }

    changeSet(author: "jessen", id: "20160611-sys-message-attachment-1") {
        //    sqlFile(path: MigrationHelper.getInstance().dataPath("com/jingrui/jrap/db/data/"+dbType+"/tables/sys_message_attachment.sql"), encoding: "UTF-8")
        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'SYS_MESSAGE_ATTACHMENT_S', startValue:"10001")
        }
        createTable(tableName: "SYS_MESSAGE_ATTACHMENT"){
            if (mhi.getDbType().isSuppportAutoIncrement()) {
                column(name:"ATTACHMENT_ID",type:"bigint",autoIncrement: "true", startWith:"10001",remarks: "表ID，主键"){
                    constraints(nullable: "false", primaryKey: "true",primaryKeyName: "SYS_MESSAGE_ATTACHMENT_PK")
                }
            } else {
                column(name:"ATTACHMENT_ID",type:"bigint",remarks: "表ID，主键"){
                    constraints(nullable: "false", primaryKey: "true",primaryKeyName: "SYS_MESSAGE_ATTACHMENT_PK")
                }
            }

            column(name:"MESSAGE_ID",type:"bigint",remarks: "消息id")
            column(name:"FILE_ID",type:"bigint",remarks: "附件id")
            column(name:"OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "bigint", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "bigint", defaultValue : "-1")
            column(name: "CREATED_BY", type: "bigint", defaultValue : "-1")
            column(name: "CREATION_DATE", type: "datetime", defaultValueComputed : "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATED_BY", type: "bigint", defaultValue : "-1")
            column(name: "LAST_UPDATE_DATE", type: "datetime", defaultValueComputed : "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATE_LOGIN", type: "bigint", defaultValue : "-1")
            column(name:"ATTRIBUTE_CATEGORY",type:"varchar(30)")
            column(name:"ATTRIBUTE1",type:"varchar(240)")
            column(name:"ATTRIBUTE2",type:"varchar(240)")
            column(name:"ATTRIBUTE3",type:"varchar(240)")
            column(name:"ATTRIBUTE4",type:"varchar(240)")
            column(name:"ATTRIBUTE5",type:"varchar(240)")
            column(name:"ATTRIBUTE6",type:"varchar(240)")
            column(name:"ATTRIBUTE7",type:"varchar(240)")
            column(name:"ATTRIBUTE8",type:"varchar(240)")
            column(name:"ATTRIBUTE9",type:"varchar(240)")
            column(name:"ATTRIBUTE10",type:"varchar(240)")
            column(name:"ATTRIBUTE11",type:"varchar(240)")
            column(name:"ATTRIBUTE12",type:"varchar(240)")
            column(name:"ATTRIBUTE13",type:"varchar(240)")
            column(name:"ATTRIBUTE14",type:"varchar(240)")
            column(name:"ATTRIBUTE15",type:"varchar(240)")
        }
        createIndex(tableName:"SYS_MESSAGE_ATTACHMENT",indexName:"SYS_MESSAGE_ATTACHMENT_N1"){column(name: "FILE_ID",type: "bigint")}
        createIndex(tableName:"SYS_MESSAGE_ATTACHMENT",indexName:"SYS_MESSAGE_ATTACHMENT_N2"){column(name: "MESSAGE_ID",type: "bigint")}

    }

    changeSet(author: "jessen", id: "20160611-sys-message-email-account-1") {
        //     sqlFile(path: MigrationHelper.getInstance().dataPath("com/jingrui/jrap/db/data/"+dbType+"/tables/sys_message_email_account.sql"), encoding: "UTF-8")
        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'SYS_MESSAGE_EMAIL_ACCOUNT_S', startValue:"10001")
        }
        createTable(tableName: "SYS_MESSAGE_EMAIL_ACCOUNT"){
            if (mhi.getDbType().isSuppportAutoIncrement()){
                column(name:"ACCOUNT_ID",type:"bigint",autoIncrement: "true", startWith:"10001",remarks: "表ID，主键"){
                    constraints(nullable: "false", primaryKey: "true",primaryKeyName: "SYS_MESSAGE_EMAIL_ACCOUNT_PK")
                }
            } else {
                column(name:"ACCOUNT_ID",type:"bigint",remarks: "表ID，主键"){
                    constraints(nullable: "false", primaryKey: "true",primaryKeyName: "SYS_MESSAGE_EMAIL_ACCOUNT_PK")
                }
            }
            if(!mhi.isDbType('postgresql')){
                column(name:"ACCOUNT_CODE",type:"varchar(50)"){
                    constraints(nullable: "false",unique: "true", uniqueConstraintName: "SYS_MESSAGE_EMAIL_ACCOUNT_U1")
                }
            }else {
                column(name:"ACCOUNT_CODE",type:"varchar(50)"){
                    constraints(nullable: "false")
                }
            }

            column(name:"CONFIG_ID",type:"bigint")
            column(name:"USER_NAME",type:"varchar(240)")
            column(name:"PASSWORD",type:"varchar(240)")
            column(name:"DESCRIPTION",type:"varchar(240)")
            column(name:"OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "bigint", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "bigint", defaultValue : "-1")
            column(name: "CREATED_BY", type: "bigint", defaultValue : "-1")
            column(name: "CREATION_DATE", type: "datetime", defaultValueComputed : "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATED_BY", type: "bigint", defaultValue : "-1")
            column(name: "LAST_UPDATE_DATE", type: "datetime", defaultValueComputed : "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATE_LOGIN", type: "bigint", defaultValue : "-1")
            column(name:"ATTRIBUTE_CATEGORY",type:"varchar(30)")
            column(name:"ATTRIBUTE1",type:"varchar(240)")
            column(name:"ATTRIBUTE2",type:"varchar(240)")
            column(name:"ATTRIBUTE3",type:"varchar(240)")
            column(name:"ATTRIBUTE4",type:"varchar(240)")
            column(name:"ATTRIBUTE5",type:"varchar(240)")
            column(name:"ATTRIBUTE6",type:"varchar(240)")
            column(name:"ATTRIBUTE7",type:"varchar(240)")
            column(name:"ATTRIBUTE8",type:"varchar(240)")
            column(name:"ATTRIBUTE9",type:"varchar(240)")
            column(name:"ATTRIBUTE10",type:"varchar(240)")
            column(name:"ATTRIBUTE11",type:"varchar(240)")
            column(name:"ATTRIBUTE12",type:"varchar(240)")
            column(name:"ATTRIBUTE13",type:"varchar(240)")
            column(name:"ATTRIBUTE14",type:"varchar(240)")
            column(name:"ATTRIBUTE15",type:"varchar(240)")
        }
        if(mhi.isDbType('postgresql')){
            addUniqueConstraint(columnNames: "ACCOUNT_ID,ACCOUNT_CODE", tableName: "SYS_MESSAGE_EMAIL_ACCOUNT", constraintName: "SYS_MESSAGE_EMAIL_ACCOUNT_U1")
        }

    }

    changeSet(author: "jessen", id: "20160611-sys-message-email-config-1") {
        //    sqlFile(path: MigrationHelper.getInstance().dataPath("com/jingrui/jrap/db/data/"+dbType+"/tables/sys_message_email_config.sql"), encoding: "UTF-8")
        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'SYS_MESSAGE_EMAIL_CONFIG_S', startValue:"10001")
        }
        createTable(tableName: "SYS_MESSAGE_EMAIL_CONFIG"){
            if (mhi.getDbType().isSuppportAutoIncrement()){
                column(name:"CONFIG_ID",type:"bigint",autoIncrement: "true", startWith:"10001",remarks: "表ID，主键"){
                    constraints(nullable: "false", primaryKey: "true",primaryKeyName: "SYS_MESSAGE_EMAIL_CONFIG_PK")
                }
            } else {
                column(name:"CONFIG_ID",type:"bigint",remarks: "表ID，主键"){
                    constraints(nullable: "false", primaryKey: "true",primaryKeyName: "SYS_MESSAGE_EMAIL_CONFIG_PK")
                }
            }
            if(!mhi.isDbType('postgresql')){
                column(name:"CONFIG_CODE",type:"varchar(50)",remarks: "编号"){
                    constraints(nullable: "false",unique: "true", uniqueConstraintName: "SYS_MESSAGE_EMAIL_CONFIG_U1")
                }
            }else {
                column(name:"CONFIG_CODE",type:"varchar(50)",remarks: "编号"){
                    constraints(nullable: "false")
                }
            }

            column(name:"HOST",type:"varchar(50)",remarks: "服务器")
            column(name:"PORT",type:"varchar(10)",remarks: "端口")
            column(name:"TRY_TIMES",type:"decimal(20,0)",defaultValue: "3", remarks: "重试次数")
            column(name:"DESCRIPTION",type:"varchar(20)",remarks: "描述")
            column(name:"USE_WHITE_LIST",type:"varchar(1)",defaultValue: "N", remarks: "是否使用白名单")
            column(name:"USER_NAME",type:"varchar(240)",remarks: "用户名")
            column(name:"PASSWORD",type:"varchar(240)",remarks: "密码")
            column(name:"ENABLE",type:"varchar(1)",defaultValue: "Y")
            column(name:"OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "bigint", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "bigint", defaultValue : "-1")
            column(name: "CREATED_BY", type: "bigint", defaultValue : "-1")
            column(name: "CREATION_DATE", type: "datetime", defaultValueComputed : "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATED_BY", type: "bigint", defaultValue : "-1")
            column(name: "LAST_UPDATE_DATE", type: "datetime", defaultValueComputed : "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATE_LOGIN", type: "bigint", defaultValue : "-1")
            column(name:"ATTRIBUTE_CATEGORY",type:"varchar(30)")
            column(name:"ATTRIBUTE1",type:"varchar(240)")
            column(name:"ATTRIBUTE2",type:"varchar(240)")
            column(name:"ATTRIBUTE3",type:"varchar(240)")
            column(name:"ATTRIBUTE4",type:"varchar(240)")
            column(name:"ATTRIBUTE5",type:"varchar(240)")
            column(name:"ATTRIBUTE6",type:"varchar(240)")
            column(name:"ATTRIBUTE7",type:"varchar(240)")
            column(name:"ATTRIBUTE8",type:"varchar(240)")
            column(name:"ATTRIBUTE9",type:"varchar(240)")
            column(name:"ATTRIBUTE10",type:"varchar(240)")
            column(name:"ATTRIBUTE11",type:"varchar(240)")
            column(name:"ATTRIBUTE12",type:"varchar(240)")
            column(name:"ATTRIBUTE13",type:"varchar(240)")
            column(name:"ATTRIBUTE14",type:"varchar(240)")
            column(name:"ATTRIBUTE15",type:"varchar(240)")
        }
        if(mhi.isDbType('postgresql')){
            addUniqueConstraint(columnNames: "CONFIG_ID,CONFIG_CODE", tableName: "SYS_MESSAGE_EMAIL_CONFIG", constraintName: "SYS_MESSAGE_EMAIL_CONFIG_U1")
        }
    }

    changeSet(author: "jessen", id: "20160613-sys-message-email-white-lt-1") {
        //    sqlFile(path: MigrationHelper.getInstance().dataPath("com/jingrui/jrap/db/data/"+dbType+"/tables/sys_message_email_white_lt.sql"), encoding: "UTF-8")
        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'SYS_MESSAGE_EMAIL_WHITE_LT_S', startValue:"10001")
        }
        createTable(tableName: "SYS_MESSAGE_EMAIL_WHITE_LT"){
            if (mhi.getDbType().isSuppportAutoIncrement()) {
                column(name:"ID",type:"bigint",autoIncrement: "true", startWith:"10001",remarks: "表ID，主键"){
                    constraints(nullable: "false", primaryKey: "true",primaryKeyName: "SYS_MESSAGE_EMAIL_WHITE_LT_PK")
                }
            } else {
                column(name:"ID",type:"bigint",remarks: "表ID，主键"){
                    constraints(nullable: "false", primaryKey: "true",primaryKeyName: "SYS_MESSAGE_EMAIL_WHITE_LT_PK")
                }
            }

            column(name:"ADDRESS",type:"varchar(240)",remarks: "白名单地址")
            column(name:"CONFIG_ID",type:"bigint",remarks: "邮箱配置id")
            column(name:"DESCRIPTION",type:"varchar(240)",remarks: "描述")
            column(name:"OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "bigint", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "bigint", defaultValue : "-1")
            column(name: "CREATED_BY", type: "bigint", defaultValue : "-1")
            column(name: "CREATION_DATE", type: "datetime", defaultValueComputed : "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATED_BY", type: "bigint", defaultValue : "-1")
            column(name: "LAST_UPDATE_DATE", type: "datetime", defaultValueComputed : "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATE_LOGIN", type: "bigint", defaultValue : "-1")
            column(name:"ATTRIBUTE_CATEGORY",type:"varchar(30)")
            column(name:"ATTRIBUTE1",type:"varchar(240)")
            column(name:"ATTRIBUTE2",type:"varchar(240)")
            column(name:"ATTRIBUTE3",type:"varchar(240)")
            column(name:"ATTRIBUTE4",type:"varchar(240)")
            column(name:"ATTRIBUTE5",type:"varchar(240)")
            column(name:"ATTRIBUTE6",type:"varchar(240)")
            column(name:"ATTRIBUTE7",type:"varchar(240)")
            column(name:"ATTRIBUTE8",type:"varchar(240)")
            column(name:"ATTRIBUTE9",type:"varchar(240)")
            column(name:"ATTRIBUTE10",type:"varchar(240)")
            column(name:"ATTRIBUTE11",type:"varchar(240)")
            column(name:"ATTRIBUTE12",type:"varchar(240)")
            column(name:"ATTRIBUTE13",type:"varchar(240)")
            column(name:"ATTRIBUTE14",type:"varchar(240)")
            column(name:"ATTRIBUTE15",type:"varchar(240)")
        }
    }

    changeSet(author: "jessen", id: "20160613-sys-message-receiver-1") {
        //     sqlFile(path: MigrationHelper.getInstance().dataPath("com/jingrui/jrap/db/data/"+dbType+"/tables/sys_message_receiver.sql"), encoding: "UTF-8")
        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'SYS_MESSAGE_RECEIVER_S', startValue:"10001")
        }
        createTable(tableName: "SYS_MESSAGE_RECEIVER"){
            if (mhi.getDbType().isSuppportAutoIncrement()) {
                column(name:"RECEIVER_ID",type:"bigint",autoIncrement: "true", startWith:"10001",remarks: "表ID，主键"){
                    constraints(nullable: "false", primaryKey: "true",primaryKeyName: "SYS_MESSAGE_RECEIVER_PK")
                }
            } else {
                column(name:"RECEIVER_ID",type:"bigint",remarks: "表ID，主键"){
                    constraints(nullable: "false", primaryKey: "true",primaryKeyName: "SYS_MESSAGE_RECEIVER_PK")
                }
            }

            column(name:"MESSAGE_ID",type:"bigint",remarks: "消息id")
            column(name:"MESSAGE_TYPE",type:"varchar(25)",remarks: "消息类型,抄送/普通")
            column(name:"MESSAGE_ADDRESS",type:"varchar(255)",remarks: "消息地址,邮箱/手机号")
            column(name:"OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "bigint", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "bigint", defaultValue : "-1")
            column(name: "CREATED_BY", type: "bigint", defaultValue : "-1")
            column(name: "CREATION_DATE", type: "datetime", defaultValueComputed : "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATED_BY", type: "bigint", defaultValue : "-1")
            column(name: "LAST_UPDATE_DATE", type: "datetime", defaultValueComputed : "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATE_LOGIN", type: "bigint", defaultValue : "-1")
            column(name:"ATTRIBUTE_CATEGORY",type:"varchar(30)")
            column(name:"ATTRIBUTE1",type:"varchar(240)")
            column(name:"ATTRIBUTE2",type:"varchar(240)")
            column(name:"ATTRIBUTE3",type:"varchar(240)")
            column(name:"ATTRIBUTE4",type:"varchar(240)")
            column(name:"ATTRIBUTE5",type:"varchar(240)")
            column(name:"ATTRIBUTE6",type:"varchar(240)")
            column(name:"ATTRIBUTE7",type:"varchar(240)")
            column(name:"ATTRIBUTE8",type:"varchar(240)")
            column(name:"ATTRIBUTE9",type:"varchar(240)")
            column(name:"ATTRIBUTE10",type:"varchar(240)")
            column(name:"ATTRIBUTE11",type:"varchar(240)")
            column(name:"ATTRIBUTE12",type:"varchar(240)")
            column(name:"ATTRIBUTE13",type:"varchar(240)")
            column(name:"ATTRIBUTE14",type:"varchar(240)")
            column(name:"ATTRIBUTE15",type:"varchar(240)")
        }
        createIndex(tableName:"SYS_MESSAGE_RECEIVER",indexName:"SYS_MESSAGE_RECEIVER_N1"){column(name: "MESSAGE_ID",type: "bigint")}

    }

    changeSet(author: "jessen", id: "20160613-sys-message-template-1") {
        //sqlFile(path: MigrationHelper.getInstance().dataPath("com/jingrui/jrap/db/data/"+dbType+"/tables/sys_message_template.sql"), encoding: "UTF-8")
        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'SYS_MESSAGE_TEMPLATE_S', startValue:"10001")
        }
        createTable(tableName: "SYS_MESSAGE_TEMPLATE"){
            if (mhi.getDbType().isSuppportAutoIncrement()){
                column(name:"TEMPLATE_ID",type:"bigint",autoIncrement: "true", startWith:"10001",remarks: "表ID，主键"){
                    constraints(nullable: "false", primaryKey: "true",primaryKeyName: "SYS_MESSAGE_TEMPLATE_PK")
                }
            } else {
                column(name:"TEMPLATE_ID",type:"bigint",remarks: "表ID，主键"){
                    constraints(nullable: "false", primaryKey: "true",primaryKeyName: "SYS_MESSAGE_TEMPLATE_PK")
                }
            }

            column(name:"SUBJECT",type:"clob",remarks: "模板标题")
            column(name:"CONTENT",type:"clob",remarks: "模板内容")
            column(name:"ACCOUNT_ID",type:"bigint",remarks: "账号id")
            if(!mhi.isDbType('postgresql')){
                column(name:"TEMPLATE_CODE",type:"varchar(50)",remarks: "模板编号"){
                    constraints(nullable: "false",unique: "true",uniqueConstraintName: "SYS_MESSAGE_TEMPLATE_U1")
                }
            }else {
                column(name:"TEMPLATE_CODE",type:"varchar(50)",remarks: "模板编号"){
                    constraints(nullable: "false")
                }
            }

            column(name:"TEMPLATE_TYPE",type:"varchar(50)",remarks: "模板类型,邮件/SMS")
            column(name:"PRIORITY_LEVEL",type:"varchar(50)",remarks: "优先级")
            column(name:"DESCRIPTION",type:"varchar(240)",remarks: "描述")
            column(name:"OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "bigint", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "bigint", defaultValue : "-1")
            column(name: "CREATED_BY", type: "bigint", defaultValue : "-1")
            column(name: "CREATION_DATE", type: "datetime", defaultValueComputed : "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATED_BY", type: "bigint", defaultValue : "-1")
            column(name: "LAST_UPDATE_DATE", type: "datetime", defaultValueComputed : "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATE_LOGIN", type: "bigint", defaultValue : "-1")
            column(name:"ATTRIBUTE_CATEGORY",type:"varchar(30)")
            column(name:"ATTRIBUTE1",type:"varchar(240)")
            column(name:"ATTRIBUTE2",type:"varchar(240)")
            column(name:"ATTRIBUTE3",type:"varchar(240)")
            column(name:"ATTRIBUTE4",type:"varchar(240)")
            column(name:"ATTRIBUTE5",type:"varchar(240)")
            column(name:"ATTRIBUTE6",type:"varchar(240)")
            column(name:"ATTRIBUTE7",type:"varchar(240)")
            column(name:"ATTRIBUTE8",type:"varchar(240)")
            column(name:"ATTRIBUTE9",type:"varchar(240)")
            column(name:"ATTRIBUTE10",type:"varchar(240)")
            column(name:"ATTRIBUTE11",type:"varchar(240)")
            column(name:"ATTRIBUTE12",type:"varchar(240)")
            column(name:"ATTRIBUTE13",type:"varchar(240)")
            column(name:"ATTRIBUTE14",type:"varchar(240)")
            column(name:"ATTRIBUTE15",type:"varchar(240)")
        }
        if(mhi.isDbType('postgresql')){
            addUniqueConstraint(columnNames: "TEMPLATE_ID,TEMPLATE_CODE", tableName: "SYS_MESSAGE_TEMPLATE", constraintName: "SYS_MESSAGE_TEMPLATE_U1")
        }
    }

}
