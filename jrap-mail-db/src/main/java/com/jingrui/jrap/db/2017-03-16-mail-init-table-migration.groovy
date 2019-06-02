package com.jingrui.jrap.db

import com.jingrui.jrap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()

databaseChangeLog(logicalFilePath:"2017-03-16-init-table-migration.groovy"){

    changeSet(author: "qiangzeng", id: "20170721-sys-message-email-property-1") {
        if (mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'SYS_MESSAGE_EMAIL_PROPERTY_S',startValue:"10001")
        }
        createTable (tableName: "SYS_MESSAGE_EMAIL_PROPERTY", remarks: "邮件消息属性") {
            if (mhi.getDbType().isSupportAutoIncrement()){
                column(name: "PROPERTY_ID", type: "BIGINT", autoIncrement: "true", startWith: "10001", remarks: "PK") {
                    constraints(nullable: "false", primaryKey: "true", primaryKeyName: "SYS_MESSAGE_EMAIL_PROPERTY_PK")
                }
            } else {
                column(name: "PROPERTY_ID", type: "BIGINT", remarks: "PK") {
                    constraints(nullable: "false", primaryKey: "true", primaryKeyName: "SYS_MESSAGE_EMAIL_PROPERTY_PK")
                }
            }

            column(name: "PROPERTY_NAME", type:"VARCHAR(50)",remarks: "属性名称")
            column(name: "PROPERTY_CODE", type:"VARCHAR(50)",remarks: "属性值")
            column(name: "CONFIG_ID", type:"BIGINT",remarks: "邮件账户ID")
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
        createIndex(tableName:"SYS_MESSAGE_EMAIL_PROPERTY",indexName:"SYS_MESSAGE_EMAIL_PROPERTY_N1"){
            column(name: "PROPERTY_NAME",type: "varchar(50)")
        }
    }

}