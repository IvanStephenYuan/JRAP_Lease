package com.jingrui.jrap.db

import com.jingrui.jrap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath:"2016-06-09-init-table-migration.groovy"){
    changeSet(author: "hailor", id: "20160609-hailor-1") {
        sqlFile(path: MigrationHelper.getInstance().dataPath("com/jingrui/jrap/db/data/"+dbType+"/tables/quartz_2.2.3.sql"), encoding: "UTF-8")
    }
}
