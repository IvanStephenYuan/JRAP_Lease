package com.jingrui.jrap.db

import com.jingrui.jrap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = MigrationHelper.getInstance().dbType()
databaseChangeLog(logicalFilePath:"patch.groovy"){

    changeSet(author: "jialong.zuo", id: "20170705-sys_message_email_config-add-required") {

            addNotNullConstraint(tableName: "SYS_MESSAGE_EMAIL_CONFIG", columnName: "HOST", columnDataType: "varchar(50)")
            addNotNullConstraint(tableName: "SYS_MESSAGE_EMAIL_CONFIG", columnName: "PORT", columnDataType: "varchar(10)")
            addNotNullConstraint(tableName: "SYS_MESSAGE_EMAIL_CONFIG", columnName: "USE_WHITE_LIST", columnDataType: "varchar(1)")
            addNotNullConstraint(tableName: "SYS_MESSAGE_EMAIL_CONFIG", columnName: "USER_NAME", columnDataType: "varchar(240)")
            addNotNullConstraint(tableName: "SYS_MESSAGE_EMAIL_CONFIG", columnName: "PASSWORD", columnDataType: "varchar(240)")

    }

    changeSet(author: "jialong.zuo", id: "20170705-sys_message_email_account-add-required") {

            addNotNullConstraint(tableName: "SYS_MESSAGE_EMAIL_ACCOUNT", columnName: "USER_NAME", columnDataType: "varchar(240)")

    }

    changeSet(author: "jialong.zuo", id: "20170705-sys_message_template-add-required") {

            addNotNullConstraint(tableName: "SYS_MESSAGE_TEMPLATE", columnName: "SUBJECT", columnDataType: "longtext")
            addNotNullConstraint(tableName: "SYS_MESSAGE_TEMPLATE", columnName: "CONTENT", columnDataType: "longtext")

    }

    changeSet(author: "qiangzeng", id: "20170717-sys_message_template") {
        addColumn(tableName: "SYS_MESSAGE_TEMPLATE") {
            column(name: "SEND_TYPE", type: "VARCHAR(50)", remarks: "发送类型", defaultValue: "DIRECT",afterColumn:"DESCRIPTION")
        }
    }

    changeSet(author: "qiangzeng", id: "20170717-sys_message_email_config") {
        dropNotNullConstraint(tableName: "SYS_MESSAGE_EMAIL_CONFIG", columnName: "USER_NAME", columnDataType: "varchar(240)")
        dropNotNullConstraint(tableName: "SYS_MESSAGE_EMAIL_CONFIG", columnName: "PASSWORD", columnDataType: "varchar(240)")
    }

    changeSet(author: "qiangzeng", id: "20170718-sys_message_email_config") {
        addColumn(tableName: "SYS_MESSAGE_EMAIL_CONFIG") {
            column(name: "SERVER_ENABLE", type: "VARCHAR(1)", remarks: "是否启用服务器配置", defaultValue: "Y",afterColumn:"ENABLE")
        }
    }


}