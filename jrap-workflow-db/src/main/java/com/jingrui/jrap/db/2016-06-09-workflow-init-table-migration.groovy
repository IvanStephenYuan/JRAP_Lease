package com.jingrui.jrap.db.data

import com.jingrui.jrap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()

databaseChangeLog(logicalFilePath:"2016-06-09-init-table-migration.groovy"){

    changeSet(author: "jessen", id: "20160926-activiti.create.engine-1") {
        sqlFile(path: MigrationHelper.getInstance().dataPath("com/jingrui/jrap/db/data/"+dbType+"/tables/activiti.create.engine.sql"), encoding: "UTF-8")
    }

    changeSet(author: "jessen", id: "20160926-activiti.create.history-1") {
        sqlFile(path: MigrationHelper.getInstance().dataPath("com/jingrui/jrap/db/data/"+dbType+"/tables/activiti.create.history.sql"), encoding: "UTF-8")
    }

    changeSet(author: "jessen", id: "20160926-activiti.create.identity-1") {
        sqlFile(path: MigrationHelper.getInstance().dataPath("com/jingrui/jrap/db/data/"+dbType+"/tables/activiti.create.identity.sql"), encoding: "UTF-8")
    }

}
