package com.jingrui.jrap.db

import com.jingrui.jrap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = MigrationHelper.getInstance().dbType()
databaseChangeLog(logicalFilePath:"patch.groovy"){

    changeSet(author: "xiangyu.qi",id:"20161109-xiangyuqi-1"){
        renameColumn (tableName:"SYS_IF_CONFIG_LINE_TL",columnDataType:"VARCHAR(255)",oldColumnName:"HEADER_ID",newColumnName:"LINE_ID")
        if(!mhi.isDbType('postgresql')){
            addUniqueConstraint(tableName:"SYS_IF_CONFIG_HEADER_B", columnNames:"INTERFACE_CODE", constraintName:"SYS_IF_CONFIG_HEADER_U1")
        }else {
            addUniqueConstraint(tableName:"SYS_IF_CONFIG_HEADER_B", columnNames:"HEADER_ID,INTERFACE_CODE", constraintName:"SYS_IF_CONFIG_HEADER_U1")
        }
    }

    changeSet(author: "qixiangyu",id:"20170327-qixiangyu-1") {
        addColumn(tableName: "SYS_IF_CONFIG_HEADER_B") {
            column(name: "AUTH_TYPE", type: "VARCHAR(50)", remarks: "校验模式",defaultValue: "BASIC_AUTH")
        }
        addColumn(tableName: "SYS_IF_CONFIG_HEADER_B"){
            column(name: "AUTH_URL", type: "VARCHAR(255)", remarks: "取授权码的url")
        }
        addColumn(tableName: "SYS_IF_CONFIG_HEADER_B"){
            column(name: "ACCESS_TOKEN_URL", type: "VARCHAR(255)", remarks: "获取token的url")
        }
        addColumn(tableName: "SYS_IF_CONFIG_HEADER_B"){
            column(name: "CLIENT_ID", type: "VARCHAR(255)", remarks: "应用id")
        }
        addColumn(tableName: "SYS_IF_CONFIG_HEADER_B"){
            column(name: "CLIENT_SECRET", type: "VARCHAR(255)", remarks: "应用secret")
        }
        addColumn(tableName: "SYS_IF_CONFIG_HEADER_B"){
            column(name: "GRANT_TYPE", type: "VARCHAR(50)", remarks: "授权模式")
        }
        addColumn(tableName: "SYS_IF_CONFIG_HEADER_B"){
            column(name: "SCOPE" ,type: "VARCHAR(100)", remarks: "权限范围")
        }
    }

    changeSet(author: "jialong.zuo", id: "20170616-sys-if-config-header-b-addColumn-action") {
        addColumn(tableName: "SYS_IF_CONFIG_HEADER_B") {
            column(name: "SOAP_ACTION", type: "VARCHAR(100)", remarks: "SOAPACTION")
        }
    }


        changeSet(author: "qixiangyu", id: "interface-alter-url-length-2") {
            modifyDataType(tableName: "SYS_IF_CONFIG_HEADER_B", columnName: "AUTH_URL", newDataType: "VARCHAR(2000)")
            modifyDataType(tableName: "SYS_IF_CONFIG_HEADER_B", columnName: "ACCESS_TOKEN_URL", newDataType: "VARCHAR(2000)")
            modifyDataType(tableName: "SYS_IF_CONFIG_HEADER_B", columnName: "DOMAIN_URL", newDataType: "VARCHAR(2000)")
            modifyDataType(tableName: "SYS_IF_CONFIG_LINE_B", columnName: "IFT_URL", newDataType: "VARCHAR(2000)")
        }
}