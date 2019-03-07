package com.jingrui.jrap.db

import com.jingrui.jrap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = MigrationHelper.getInstance().dbType()
databaseChangeLog(logicalFilePath:"patch.groovy"){

        changeSet(author: "jiangpeng", id: "api_config_server-alter-namespace-length-1") {
            modifyDataType(tableName: "API_CONFIG_SERVER", columnName: "NAMESPACE", newDataType: "VARCHAR(500)")
        }

}