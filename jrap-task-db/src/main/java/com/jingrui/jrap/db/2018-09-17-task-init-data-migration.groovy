package com.jingrui.jrap.db

import com.jingrui.jrap.liquibase.MigrationHelper
import com.jingrui.jrap.db.excel.ExcelDataLoader

dbType = MigrationHelper.getInstance().dbType()
databaseChangeLog(logicalFilePath:"2017-10-12-task-init-data-migration.groovy"){
    changeSet(author: "vista yih", id: "20180917-task-detail-b") {
        sqlFile(path: MigrationHelper.getInstance().dataPath("com/jingrui/jrap/db/data/"+dbType+"/init/sys_task_detail_tl.sql"), encoding: "UTF-8")
    }
}