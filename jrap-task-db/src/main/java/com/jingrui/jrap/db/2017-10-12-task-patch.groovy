package com.jingrui.jrap.db

import com.jingrui.jrap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = MigrationHelper.getInstance().dbType()
databaseChangeLog(logicalFilePath:"patch.groovy"){

    changeSet(author: "jiangpeng", id: "20171205-sys_task_detail") {
        addColumn(tableName: "SYS_TASK_DETAIL"){
            column(name:"START_DATE",type:"DATE",remarks: "开始日期")
        }
        addColumn(tableName: "SYS_TASK_DETAIL"){
            column(name:"END_DATE",type:"DATE",remarks: "结束日期")
        }
    }

    changeSet(author: "jiangpeng", id: "20180104-sys-task-assign") {
        if(!mhi.isDbType('postgresql')){
            addUniqueConstraint(columnNames:"TASK_ID, ASSIGN_ID, ASSIGN_TYPE",tableName:"SYS_TASK_ASSIGN",constraintName: "TASK_ASSIGN_U1")
        }else {
            addUniqueConstraint(columnNames:"TASK_ASSIGN_ID,TASK_ID, ASSIGN_ID, ASSIGN_TYPE",tableName:"SYS_TASK_ASSIGN",constraintName: "TASK_ASSIGN_U1")
        }

    }

    changeSet(author: "jiangpeng", id: "20180110-sys-task-execution") {
        addColumn(tableName: "SYS_TASK_EXECUTION") {
            column(name: "LAST_EXECUTE_DATE", type: "DATETIME", remarks: "上次执行时间", afterColumn:"EXECUTION_ORDER")
        }
        addColumn(tableName: "SYS_TASK_EXECUTION") {
            column(name: "EXECUTE_RESULT_PATH", type: "varchar(250)", remarks: "执行结果路径", afterColumn:"LAST_EXECUTE_DATE")
        }
    }

}