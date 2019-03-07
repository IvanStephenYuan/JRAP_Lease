package com.jingrui.jrap.db

import com.jingrui.jrap.liquibase.MigrationHelper
import com.jingrui.jrap.db.excel.ExcelDataLoader
dbType = MigrationHelper.getInstance().dbType()
databaseChangeLog(logicalFilePath:"2016-06-09-init-data-migration.groovy"){

    changeSet(author: "jessen", id: "20160613-data-sys-message-email-config-1") {
        sqlFile(path: MigrationHelper.getInstance().dataPath("com/jingrui/jrap/db/data/"+dbType+"/init/sys_message_email_config.sql"), encoding: "UTF-8")
    }

    changeSet(author: "jessen", id: "20160613-data-sys-message-email-account-1") {
        sqlFile(path: MigrationHelper.getInstance().dataPath("com/jingrui/jrap/db/data/"+dbType+"/init/sys_message_email_account.sql"), encoding: "UTF-8")
    }

    changeSet(author: "jessen", id: "20160613-data-sys-message-email-white-lt-1") {
        sqlFile(path: MigrationHelper.getInstance().dataPath("com/jingrui/jrap/db/data/"+dbType+"/init/sys_message_email_white_lt.sql"), encoding: "UTF-8")
    }

    changeSet(author: "jessen", id: "20160613-data-sys-message-template-1") {
        sqlFile(path: MigrationHelper.getInstance().dataPath("com/jingrui/jrap/db/data/"+dbType+"/init/sys_message_template.sql"), encoding: "UTF-8")
    }

    changeSet(author: "jessen", id: "mail-init-data-xlsx", runAlways:"true"){
        customChange(class:ExcelDataLoader.class.name){
            param(name:"filePath",value:MigrationHelper.getInstance().dataPath("com/jingrui/jrap/db/data/mail-init-data.xlsx"))
        }
    }

    changeSet(author: "qiangzeng", id: "20170717-data-sys-message-email-config-1") {
        sqlFile(path: MigrationHelper.getInstance().dataPath("com/jingrui/jrap/db/data/"+dbType+"/init/sys_message_email_config_update.sql"), encoding: "UTF-8")
    }

}
