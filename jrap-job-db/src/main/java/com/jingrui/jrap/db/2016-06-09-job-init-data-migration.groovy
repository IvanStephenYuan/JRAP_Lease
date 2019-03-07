package com.jingrui.jrap.db

import com.jingrui.jrap.liquibase.MigrationHelper
import com.jingrui.jrap.db.excel.ExcelDataLoader
dbType = MigrationHelper.getInstance().dbType()
databaseChangeLog(logicalFilePath:"2016-06-09-init-data-migration.groovy"){

    //Milestone , excel data, runAlways=true
    changeSet(author: "jessen", id: "job-init-data-xlsx", runAlways:"true"){
        customChange(class:ExcelDataLoader.class.name){
            param(name:"filePath",value:MigrationHelper.getInstance().dataPath("com/jingrui/jrap/db/data/job-init-data.xlsx"))
        }
    }

}
