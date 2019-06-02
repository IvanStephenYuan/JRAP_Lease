
import com.jingrui.jrap.liquibase.MigrationHelper
import liquibase.structure.core.UniqueConstraint

def mhi = MigrationHelper.getInstance()

databaseChangeLog(logicalFilePath:"2017-10-12-init-migration.groovy"){

    changeSet(author: "qiangzeng", id: "20171106-sys-parameter-config-1") {
        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'SYS_PARAMETER_CONFIG_S', startValue: "10001")
        }
        createTable(tableName: "SYS_PARAMETER_CONFIG", remarks: "参数配置") {
            if (mhi.getDbType().isSupportAutoIncrement()){
                column(name: "PARAMETER_ID", type: "BIGINT", autoIncrement: "true", startWith: "10001", remarks: "参数ID") {
                    constraints(nullable: "false", primaryKey: "true", primaryKeyName: "SYS_PARAMETER_CONFIG_PK")
                }
            } else {
                column(name: "PARAMETER_ID", type: "BIGINT", remarks: "参数ID") {
                    constraints(nullable: "false", primaryKey: "true", primaryKeyName: "SYS_PARAMETER_CONFIG_PK")
                }
            }

            column(name: "CODE", type: "VARCHAR(20)", remarks: "参数编码")
            column(name: "TARGET_ID", type: "BIGINT", remarks: "所属编码ID")
            column(name: "DISPLAY", type: "VARCHAR(20)", remarks: "表单控件"){
                constraints(nullable: "false")
            }
            column(name: "TABLE_FIELD_NAME", type: "VARCHAR(80)", remarks: "表字段名"){
                constraints(nullable: "false")
            }
            column(name: "TITLE", type: "VARCHAR(50)", remarks: "标题"){
                constraints(nullable: "false")
            }
            column(name: "DESCRIPTION", type: "VARCHAR(255)", remarks: "描述")
            column(name: "LINE_NUMBER", type: "INT", remarks: "行号")
            column(name: "COLUMN_NUMBER", type: "INT", remarks: "列号")
            column(name: "DISPLAY_LENGTH", type: "INT", remarks: "显示长度")
            column(name: "DATA_LENGTH", type: "INT", remarks: "数据长度")
            column(name: "REQUIRED", type: "VARCHAR(1)", remarks: "必输")
            column(name: "READ_ONLY", type: "VARCHAR(1)", remarks: "只读")
            column(name: "ENABLED", type: "VARCHAR(1)", remarks: "启用")
            column(name: "SOURCE_CODE", type: "VARCHAR(80)", remarks: "数据来源")
            column(name: "DEFAULT_TYPE", type:"VARCHAR(20)",remarks: "默认类型")
            column(name: "DEFAULT_VALUE", type:"CLOB",remarks: "默认值")
            column(name: "DEFAULT_TEXT", type:"VARCHAR(50)",remarks: "默认文本")
            column(name: "EXTRA_ATTRIBUTE", type:"CLOB",remarks: "扩展属性")
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "CREATED_BY", type: "BIGINT", defaultValue: "-1")
            column(name: "CREATION_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")
        }
        if(!mhi.isDbType('postgresql')){
            addUniqueConstraint(columnNames: "CODE,TARGET_ID,TABLE_FIELD_NAME", tableName: "SYS_PARAMETER_CONFIG", constraintName: "SYS_PARAMETER_CONFIG_U1")
        }else {
            addUniqueConstraint(columnNames: "PARAMETER_ID,CODE,TARGET_ID,TABLE_FIELD_NAME", tableName: "SYS_PARAMETER_CONFIG", constraintName: "SYS_PARAMETER_CONFIG_U1")
        }


    }


}