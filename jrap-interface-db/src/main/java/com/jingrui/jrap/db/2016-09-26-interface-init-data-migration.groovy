package com.jingrui.jrap.db

import com.jingrui.jrap.liquibase.MigrationHelper
import com.jingrui.jrap.db.excel.ExcelDataLoader

dbType = MigrationHelper.getInstance().dbType()
databaseChangeLog(logicalFilePath:"2016-09-26-interface-init-data-migration.groovy"){

    changeSet(author: "jiangpeng", id: "interface-init-data-xlsx", runAlways:"true"){
        customChange(class:ExcelDataLoader.class.name){
            param(name:"filePath",value:MigrationHelper.getInstance().dataPath("com/jingrui/jrap/db/data/interface-init-data.xlsx"))
        }
    }

}
