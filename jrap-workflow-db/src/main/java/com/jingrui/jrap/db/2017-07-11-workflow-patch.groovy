package com.jingrui.jrap.db.data

import com.jingrui.jrap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = MigrationHelper.getInstance().dbType()
databaseChangeLog(logicalFilePath:"patch.groovy"){


    changeSet(author: "qixiangyu", id: "201707011-act_exception") {
        if(mhi.isDbType('oracle')) {
            modifyDataType(tableName: "ACT_EXCEPTION", columnName: "PROC_ID", newDataType: "NVARCHAR(255)")
        }
    }

    changeSet(author: "qixiangyu", id: "20170808-act_hi_identitylink") {
        addColumn(tableName: "ACT_HI_IDENTITYLINK") {
            column(name: "READ_FLAG_", type: "VARCHAR(1)", remarks: "读取状态", defaultValue: "N")
        }
    }

}