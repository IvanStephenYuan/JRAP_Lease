package com.jingrui.jrap.db

import com.jingrui.jrap.liquibase.MigrationHelper
def mhi = MigrationHelper.getInstance()
dbType = MigrationHelper.getInstance().dbType()
databaseChangeLog(logicalFilePath:"patch.groovy"){

    changeSet(author: "xiangyuqi", id: "20161009-xiangyuqi-1") {
        addColumn(tableName:"SYS_USER"){
            column(name:"LAST_LOGIN_DATE",type:"datetime",remarks:"最后一次登录时间")
        }
        addColumn(tableName:"SYS_USER"){
            column(name:"LAST_PASSWORD_UPDATE_DATE",type:"datetime",remarks:"最后一次修改密码时间")
        }
    }
    changeSet(author: "zhizheng.yang", id: "20161025-zhizheng.yang-1") {
        addColumn(tableName:"SYS_LOV"){
            column(name:"CUSTOM_SQL",type:"clob",remarks:"自定义sql")
        }
    }
    changeSet(author: "zhizheng.yang", id: "20161104-zhizheng.yang") {
        addColumn(tableName:"SYS_LOV"){
            column(name:"QUERY_COLUMNS",type:"int",defaultValue:"1",remarks:"查询框列数")
        }
    }


    changeSet(author: "jialong.zuo",id:"20161110-jialongzuo-1"){
        if(!mhi.isDbType('postgresql')){
            addUniqueConstraint(tableName:"HR_ORG_UNIT_B", columnNames:"UNIT_CODE", constraintName:"HR_ORG_UNIT_U1")
        }else {
            addUniqueConstraint(tableName:"HR_ORG_UNIT_B", columnNames:"UNIT_ID,UNIT_CODE", constraintName:"HR_ORG_UNIT_U1")
        }

    }
    changeSet(author: "jialong.zuo",id:"20161129-jialongzuo-1"){
        addColumn(tableName: "SYS_CODE_VALUE_B"){
            column(name:"ORDER_SEQ",type:"int",defaultValue:"10")
        }
    }

    changeSet(author: "xiangyuqi", id: "20161221-xiangyuqi-1") {
        addColumn(tableName:"SYS_USER"){
            column(name:"FIRST_LOGIN",type:"varchar(1)",remarks:"是否第一次登录")
        }
        addDefaultValue(tableName: "SYS_USER",columnName:"FIRST_LOGIN",columnDataType:"varchar", defaultValue:"Y")
    }
    changeSet(author: "zhizheng.yang",id:"20161221-yangzhizheng-1"){
        addColumn(tableName: "SYS_LOV_ITEM"){
            column(name:"CONDITION_FIELD_LABEL_WIDTH",type:"decimal")
        }
    }
    changeSet(author: "zhizhengyang",id:"20170106-yangzhizheng-1"){
        addColumn(tableName: "SYS_LOV"){
            column(name:"CUSTOM_URL",type:"varchar(255)",remarks:"自定义URL")
        }
    }
    changeSet(author: "qiangzeng",id:"20170317-qiangzeng-1"){
        addColumn(tableName: "SYS_CODE_B"){
            column(name:"TYPE",type:"varchar(10)",remarks:"代码类型",defaultValue: "USER")
        }
        addColumn(tableName: "SYS_CODE_B"){
            column(name:"ENABLED_FLAG",type:"varchar(1)",remarks: "是否启用",defaultValue: "Y")
        }
        addColumn(tableName: "SYS_CODE_VALUE_B"){
            column(name:"TAG",type:"varchar(255)",remarks:"标记")
        }
        addColumn(tableName: "SYS_CODE_VALUE_B"){
            column(name:"ENABLED_FLAG",type:"varchar(1)",remarks: "是否启用",defaultValue: "Y")
        }
    }
    changeSet(author: "qiangzeng",id:"20170320-qiangzeng-1"){
        addColumn(tableName: "SYS_USER"){
            column(name:"DESCRIPTION",type:"varchar(255)",remarks:"说明")
        }
        addColumn(tableName: "SYS_USER"){
            column(name: "EMPLOYEE_ID",type: "BIGINT",remarks: "员工ID")
        }
        addColumn(tableName: "SYS_USER"){
            column(name: "CUSTOMER_ID",type: "BIGINT", remarks: "客户ID")
        }
        addColumn(tableName: "SYS_USER"){
            column(name: "SUPPLIER_ID ",type: "BIGINT",remarks: "供应商ID")
        }
        addColumn(tableName: "HR_EMPLOYEE"){
            column(name:"CERTIFICATE_TYPE",type:"varchar(240)",remarks: "证件类型",defaultValue: "ID")
        }
        addColumn(tableName: "HR_EMPLOYEE"){
            column(name:"EFFECTIVE_START_DATE",type:"DATE",remarks: "有效日期从")
        }
        addColumn(tableName: "HR_EMPLOYEE"){
            column(name:"EFFECTIVE_END_DATE",type:"DATE",remarks: "有效日期至")
        }
        addColumn(tableName: "HR_EMPLOYEE"){
            column(name:"ATTRIBUTE1",type:"varchar(240)")
        }
        addColumn(tableName: "HR_EMPLOYEE"){
            column(name:"ATTRIBUTE2",type:"varchar(240)")
        }
        addColumn(tableName: "HR_EMPLOYEE"){
            column(name:"ATTRIBUTE3",type:"varchar(240)")
        }
        addColumn(tableName: "HR_EMPLOYEE"){
            column(name:"ATTRIBUTE4",type:"varchar(240)")
        }
        addColumn(tableName: "HR_EMPLOYEE"){
            column(name:"ATTRIBUTE5",type:"varchar(240)")
        }
        addColumn(tableName: "HR_EMPLOYEE"){
            column(name:"ATTRIBUTE6",type:"varchar(240)")
        }
        addColumn(tableName: "HR_EMPLOYEE"){
            column(name:"ATTRIBUTE7",type:"varchar(240)")
        }
        addColumn(tableName: "HR_EMPLOYEE"){
            column(name:"ATTRIBUTE8",type:"varchar(240)")
        }
        addColumn(tableName: "HR_EMPLOYEE"){
            column(name:"ATTRIBUTE9",type:"varchar(240)")
        }
        addColumn(tableName: "HR_EMPLOYEE"){
            column(name:"ATTRIBUTE10",type:"varchar(240)")
        }
        addColumn(tableName: "HR_EMPLOYEE"){
            column(name:"ATTRIBUTE11",type:"varchar(240)")
        }
        addColumn(tableName: "HR_EMPLOYEE"){
            column(name:"ATTRIBUTE12",type:"varchar(240)")
        }
        addColumn(tableName: "HR_EMPLOYEE"){
            column(name:"ATTRIBUTE13",type:"varchar(240)")
        }
        addColumn(tableName: "HR_EMPLOYEE"){
            column(name:"ATTRIBUTE14",type:"varchar(240)")
        }
        addColumn(tableName: "HR_EMPLOYEE"){
            column(name:"ATTRIBUTE15",type:"varchar(240)")
        }

    }

    changeSet(author: "qiangzeng",id:"20170426-qiangzeng-1") {
        addColumn(tableName: "HR_ORG_UNIT_B") {
            column(name:"UNIT_CATEGORY",type:"varchar(50)",remarks:"组织类别",defaultValue: "G")
        }
        addColumn(tableName: "HR_ORG_UNIT_B") {
            column(name:"UNIT_TYPE",type:"varchar(50)",remarks: "组织分类",defaultValue: "GROUP")
        }
    }
    changeSet(author: "qiangzeng",id:"20170426-qiangzeng-2") {
        addColumn(tableName: "SYS_CODE_B") {
            column(name:"PARENT_CODE_ID",type:"bigint",remarks:"父级快码")
        }
    }

    changeSet(author: "qiangzeng",id:"20170426-qiangzeng-3") {
        addColumn(tableName: "SYS_CODE_VALUE_B") {
            column(name:"PARENT_CODE_VALUE_ID",type:"bigint",remarks:"父级快码值ID")
        }
    }


    changeSet(author: "niujiaqing", id:"20170527-sys_user-index") {
        createIndex(tableName:"SYS_USER",indexName:"SYS_USER_N1"){column(name: "STATUS", type: "varchar(30)")}
    }

    changeSet(author: "niujiaqing", id:"20170530-sys-resource-b-index") {
        createIndex(tableName:"SYS_RESOURCE_B",  indexName:"SYS_RESOURCE_B_N1"){column(name: "TYPE", type: "varchar(15)")}
    }
    changeSet(author: "niujiaqing", id:"20170530-sys-resource-tl-index") {
//        createIndex(tableName:"SYS_RESOURCE_TL",  indexName:"SYS_RESOURCE_TL_N1"){column(name: "NAME", type: "varchar(40)")}
    }


    changeSet(author: "niujiaqing", id:"20170602-sys-function-b-index") {
        createIndex(tableName:"SYS_FUNCTION_B",  indexName:"SYS_RESOURCE_B_N3"){column(name: "MODULE_CODE", type: "varchar(30)")}
        createIndex(tableName:"SYS_FUNCTION_B",  indexName:"SYS_RESOURCE_B_N4"){column(name: "RESOURCE_ID", type: "bigint")}
    }
    changeSet(author: "niujiaqing", id:"20170602-sys-function-tl-index") {
        createIndex(tableName:"SYS_FUNCTION_TL",  indexName:"SYS_FUNCTION_TL_N1"){column(name: "FUNCTION_NAME", type: "varchar(150)")}
    }

    changeSet(author: "jialongzuo", id: "20170605-hr-org-unit-b-index") {
        createIndex(tableName: "HR_ORG_UNIT_B", indexName: "HR_ORG_UNIT_B_N2") {
            column(name: "PARENT_ID", type: "bigint")
        }
    }

    changeSet(author: "jialongzuo", id: "20170605-hr-org-unit-tl-index") {
        createIndex(tableName: "HR_ORG_UNIT_TL", indexName: "HR_ORG_UNIT_TL_N1") {
            column(name: "NAME", type: "varchar(100)")
        }
    }

    changeSet(author: "jialongzuo", id: "20170606-position-b-index") {
        createIndex(tableName: "HR_ORG_POSITION_B", indexName: "HR_ORG_POSITION_B_N1") {
            column(name: "position_code", type: "varcher(50)")
        }
        createIndex(tableName: "HR_ORG_POSITION_B", indexName: "HR_ORG_POSITION_B_N2") {
            column(name: "PARENT_POSITION_ID", type: "bigint")
        }
    }

    changeSet(author: "jialongzuo", id: "20170606-position-tl-index") {
        createIndex(tableName: "HR_ORG_POSITION_TL", indexName: "HR_ORG_POSITION_TL_N1") {
            column(name: "NAME", type: "varchar(100)")
        }
    }

    changeSet(author: "jialongzuo", id: "20170606-company-b-change-name-long") {
        modifyDataType(tableName: "FND_COMPANY_B", columnName: "COMPANY_FULL_NAME", newDataType: "varchar(250)")
        modifyDataType(tableName: "FND_COMPANY_B", columnName: "COMPANY_SHORT_NAME", newDataType: "varchar(250)")
        modifyDataType(tableName: "FND_COMPANY_B", columnName: "ADDRESS", newDataType: "varchar(250)")
    }

    changeSet(author: "jialongzuo", id: "20170606-fnd-company-b-index") {
        createIndex(tableName: "FND_COMPANY_B", indexName: "FND_COMPANY_B_N2") {
            column(name: "COMPANY_TYPE")
        }
        createIndex(tableName: "FND_COMPANY_B", indexName: "FND_COMPANY_B_N3") {
            column(name: "COMPANY_FULL_NAME")
        }
        createIndex(tableName: "FND_COMPANY_B", indexName: "FND_COMPANY_B_N4") {
            column(name: "PARENT_COMPANY_ID")
        }
    }

    changeSet(author: "jialongzuo", id: "20170606-company-tl-change-name-long") {
        modifyDataType(tableName: "FND_COMPANY_TL", columnName: "COMPANY_SHORT_NAME", newDataType: "varchar(250)")
        modifyDataType(tableName: "FND_COMPANY_TL", columnName: "COMPANY_FULL_NAME", newDataType: "varchar(250)")
    }

    changeSet(author: "jialongzuo", id: "20170606-fnd-company-tl-index") {
        createIndex(tableName: "FND_COMPANY_TL", indexName: "FND_COMPANY_TL_N1") {
            column(name: "COMPANY_FULL_NAME")
        }
    }

    changeSet(author: "niujiaqing", id: "20170607-fnd-company-b") {
        if(mhi.isDbType('mysql')){
            renameColumn (tableName:"FND_COMPANY_B",oldColumnName:"company_id",newColumnName:"COMPANY_ID", columnDataType:"BIGINT", remarks:"公司ID")
            addAutoIncrement(columnDataType:"BIGINT", columnName:"COMPANY_ID",tableName:"FND_COMPANY_B")
            renameColumn (tableName:"FND_COMPANY_B",oldColumnName:"company_code",newColumnName:"COMPANY_CODE", columnDataType:"VARCHAR(30)", remarks: "公司编码")
            addNotNullConstraint(tableName:"FND_COMPANY_B", columnName:"COMPANY_CODE",columnDataType:"VARCHAR(30)")
            renameColumn (tableName:"FND_COMPANY_B",oldColumnName:"company_type",newColumnName:"COMPANY_TYPE", columnDataType:"VARCHAR(30)", remarks: "公司类型")
            renameColumn (tableName:"FND_COMPANY_B",oldColumnName:"address",newColumnName:"ADDRESS",  columnDataType:"VARCHAR(250)",remarks: "地址")
            renameColumn (tableName:"FND_COMPANY_B",oldColumnName:"company_level_id",newColumnName:"COMPANY_LEVEL_ID", columnDataType:"BIGINT",remarks: "公司级别")
            renameColumn (tableName:"FND_COMPANY_B",oldColumnName:"parent_company_id",newColumnName:"PARENT_COMPANY_ID", columnDataType:"BIGINT",remarks: "母公司")
            renameColumn (tableName:"FND_COMPANY_B",oldColumnName:"chief_position_id",newColumnName:"CHIEF_POSITION_ID", columnDataType:"BIGINT",remarks: "主岗位")
            renameColumn (tableName:"FND_COMPANY_B",oldColumnName:"start_date_active",newColumnName:"START_DATE_ACTIVE", columnDataType:"DATETIME",remarks: "有效时间开始")
            renameColumn (tableName:"FND_COMPANY_B",oldColumnName:"end_date_active",newColumnName:"END_DATE_ACTIVE", columnDataType:"DATETIME",remarks: "有效时间截止")
            renameColumn (tableName:"FND_COMPANY_B",oldColumnName:"company_short_name",newColumnName:"COMPANY_SHORT_NAME", columnDataType:"VARCHAR(250)",remarks: "公司简称")
            renameColumn (tableName:"FND_COMPANY_B",oldColumnName:"company_full_name",newColumnName:"COMPANY_FULL_NAME", columnDataType:"VARCHAR(250)",remarks: "公司全称")
            renameColumn (tableName:"FND_COMPANY_B",oldColumnName:"zipcode",newColumnName:"ZIPCODE", columnDataType:"VARCHAR(100)",remarks: "邮编")
            renameColumn (tableName:"FND_COMPANY_B",oldColumnName:"fax",newColumnName:"FAX", columnDataType:"VARCHAR(100)",remarks: "传真")
            renameColumn (tableName:"FND_COMPANY_B",oldColumnName:"phone",newColumnName:"PHONE", columnDataType:"VARCHAR(100)",remarks: "联系电话")
            renameColumn (tableName:"FND_COMPANY_B",oldColumnName:"contact_person",newColumnName:"CONTACT_PERSON", columnDataType:"VARCHAR(100)",remarks: "联系人")
        }
    }


    changeSet(author: "niujiaqing", id: "20170607-fnd-employee-index") {
        createIndex(tableName: "HR_EMPLOYEE", indexName: "HR_EMPLOYEE_N1") {
            column(name: "NAME")
        }
    }

    changeSet(author: "niujiaqing", id: "20170607-sys-code-b") {
        if(mhi.isDbType('mysql')) {
            renameColumn(tableName: "SYS_CODE_B", oldColumnName: "CODE", newColumnName: "CODE", columnDataType: "varchar(30)", remarks: "编码")
            addNotNullConstraint(tableName: "SYS_CODE_B", columnName: "CODE", columnDataType: "VARCHAR(30)")
            renameColumn(tableName: "SYS_CODE_B", oldColumnName: "DESCRIPTION", newColumnName: "DESCRIPTION", columnDataType: "varchar(250)", remarks: "编码描述")
        }
    }

    changeSet(author: "niujiaqing", id: "20170607-sys-code-tl-index") {
        createIndex(tableName: "SYS_CODE_TL", indexName: "SYS_CODE_TL_N1") {
            column(name: "DESCRIPTION")
        }
    }

    changeSet(author: "niujiaqing", id: "20170607-sys-code-value-b") {
        if(mhi.isDbType('mysql')) {
            renameColumn(tableName: "SYS_CODE_VALUE_B", oldColumnName: "CODE_ID", newColumnName: "CODE_ID", columnDataType: "BIGINT", remarks: "编码ID")
            addNotNullConstraint(tableName: "SYS_CODE_VALUE_B", columnName: "CODE_ID", columnDataType: "BIGINT")
            renameColumn(tableName: "SYS_CODE_VALUE_B", oldColumnName: "VALUE", newColumnName: "VALUE", columnDataType: "varchar(150)", remarks: "编码值")
            renameColumn(tableName: "SYS_CODE_VALUE_B", oldColumnName: "MEANING", newColumnName: "MEANING", columnDataType: "varchar(150)", remarks: "编码值名")
            renameColumn(tableName: "SYS_CODE_VALUE_B", oldColumnName: "DESCRIPTION", newColumnName: "DESCRIPTION", columnDataType: "varchar(250)", remarks: "编码值描述")
        }
    }

    changeSet(author: "niujiaqing", id: "20170607-sys-function-resource") {
        if(mhi.isDbType('mysql')) {
            renameColumn(tableName: "SYS_FUNCTION_RESOURCE", oldColumnName: "FUNCTION_ID", newColumnName: "FUNCTION_ID", columnDataType: "BIGINT", remarks: "功能ID")
            renameColumn(tableName: "SYS_FUNCTION_RESOURCE", oldColumnName: "RESOURCE_ID", newColumnName: "RESOURCE_ID", columnDataType: "BIGINT", remarks: "资源ID")
        }
    }


    changeSet(author: "niujiaqing", id: "20170607-sys-lang-b") {
        dropColumn(tableName:"SYS_LANG_B",columnName:"JOB_RESULT")
        dropColumn(tableName:"SYS_LANG_B",columnName:"JOB_STATUS")
        dropColumn(tableName:"SYS_LANG_B",columnName:"JOB_STATUS_MESSAGE")
        dropColumn(tableName:"SYS_LANG_B",columnName:"TRIGGER_NAME")
        dropColumn(tableName:"SYS_LANG_B",columnName:"TRIGGER_GROUP")
        dropColumn(tableName:"SYS_LANG_B",columnName:"PREVIOUS_FIRE_TIME")
        dropColumn(tableName:"SYS_LANG_B",columnName:"FIRE_TIME")
        dropColumn(tableName:"SYS_LANG_B",columnName:"NEXT_FIRE_TIME")
        dropDefaultValue(tableName:"SYS_LANG_B",columnName:"REFIRE_COUNT")
        dropColumn(tableName:"SYS_LANG_B",columnName:"REFIRE_COUNT")
        dropColumn(tableName:"SYS_LANG_B",columnName:"FIRE_INSTANCE_ID")
        dropColumn(tableName:"SYS_LANG_B",columnName:"SCHEDULER_INSTANCE_ID")
        dropColumn(tableName:"SYS_LANG_B",columnName:"SCHEDULED_FIRE_TIME")
        dropColumn(tableName:"SYS_LANG_B",columnName:"EXECUTION_SUMMARY")
        dropColumn(tableName:"SYS_LANG_B",columnName:"ATTRIBUTE_CATEGORY")
        dropColumn(tableName:"SYS_LANG_B",columnName:"ATTRIBUTE1")
        dropColumn(tableName:"SYS_LANG_B",columnName:"ATTRIBUTE2")
        dropColumn(tableName:"SYS_LANG_B",columnName:"ATTRIBUTE3")
        dropColumn(tableName:"SYS_LANG_B",columnName:"ATTRIBUTE4")
        dropColumn(tableName:"SYS_LANG_B",columnName:"ATTRIBUTE5")
        dropColumn(tableName:"SYS_LANG_B",columnName:"ATTRIBUTE6")
        dropColumn(tableName:"SYS_LANG_B",columnName:"ATTRIBUTE7")
        dropColumn(tableName:"SYS_LANG_B",columnName:"ATTRIBUTE8")
        dropColumn(tableName:"SYS_LANG_B",columnName:"ATTRIBUTE9")
        dropColumn(tableName:"SYS_LANG_B",columnName:"ATTRIBUTE10")
        dropColumn(tableName:"SYS_LANG_B",columnName:"ATTRIBUTE11")
        dropColumn(tableName:"SYS_LANG_B",columnName:"ATTRIBUTE12")
        dropColumn(tableName:"SYS_LANG_B",columnName:"ATTRIBUTE13")
        dropColumn(tableName:"SYS_LANG_B",columnName:"ATTRIBUTE14")
        dropColumn(tableName:"SYS_LANG_B",columnName:"ATTRIBUTE15")
    }


    changeSet(author: "niujiaqing", id: "20170607-sys-lov-index") {
        createIndex(tableName: "SYS_LOV", indexName: "SYS_LOV_N1") {
            column(name: "DESCRIPTION")
        }
    }


    changeSet(author: "niujiaqing", id: "20170607-sys-prompts-index") {
        createIndex(tableName: "SYS_PROMPTS", indexName: "SYS_PROMPTS_N1") {
            column(name: "DESCRIPTION")
        }
    }



    changeSet(author: "niujiaqing", id: "20170607-sys-role-tl-index") {
        createIndex(tableName: "SYS_ROLE_TL", indexName: "SYS_ROLE_TL_N1") {
            column(name: "ROLE_NAME")
        }
        createIndex(tableName: "SYS_ROLE_TL", indexName: "SYS_ROLE_TL_N2") {
            column(name: "ROLE_DESCRIPTION")
        }
    }

    changeSet(author: "niujiaqing", id: "20170607-sys-user-shortcut-index") {
        if(!mhi.isDbType('postgresql')){
            addUniqueConstraint(tableName:"SYS_USER_SHORTCUT", columnNames:"USER_ID,FUNCTION_CODE", constraintName:"SYS_USER_SHORTCUT_U1")
        }else {
            addUniqueConstraint(tableName:"SYS_USER_SHORTCUT", columnNames:"SHORTCUT_ID,USER_ID,FUNCTION_CODE", constraintName:"SYS_USER_SHORTCUT_U1")
        }

    }

    changeSet(author: "niujiaqing", id:"20170621-sys-function-b-index-fix") {
//        dropIndex(tableName:"SYS_FUNCTION_B",  indexName:"SYS_RESOURCE_B_N2")
        dropIndex(tableName:"SYS_FUNCTION_B",  indexName:"SYS_RESOURCE_B_N3")
        dropIndex(tableName:"SYS_FUNCTION_B",  indexName:"SYS_RESOURCE_B_N4")

        createIndex(tableName:"SYS_FUNCTION_B",  indexName:"SYS_FUNCTION_B_N2"){column(name: "MODULE_CODE", type: "varchar(30)")}
        createIndex(tableName:"SYS_FUNCTION_B",  indexName:"SYS_FUNCTION_B_N3"){column(name: "RESOURCE_ID", type: "bigint")}
    }




    changeSet(author: "jialong.zuo", id: "20170703-sys_company_b-add-required") {
        dropIndex(tableName: "FND_COMPANY_B", indexName: "FND_COMPANY_B_N3")
        addNotNullConstraint(tableName: "FND_COMPANY_B", columnName: "COMPANY_FULL_NAME", columnDataType: "varchar(250)")
        createIndex(tableName: "FND_COMPANY_B", indexName: "FND_COMPANY_B_N3") {
            column(name: "COMPANY_FULL_NAME")
        }
        addNotNullConstraint(tableName: "FND_COMPANY_B", columnName: "COMPANY_SHORT_NAME", columnDataType: "varchar(250)")
    }

    changeSet(author: "jialong.zuo", id: "20170705-hr_unit_b-add-required") {
        addNotNullConstraint(tableName: "HR_ORG_UNIT_B", columnName: "NAME", columnDataType: "varchar(100)")
    }
    changeSet(author: "jialong.zuo", id: "20170705-hr_position_b-add-required") {
        dropIndex(tableName:"HR_ORG_POSITION_B",  indexName:"HR_ORG_POSITION_B_N1")
        if(!mhi.isDbType('postgresql')){
            addUniqueConstraint(columnNames: "POSITION_CODE", tableName: "HR_ORG_POSITION_B", constraintName: "HR_ORG_POSITION_B_U1")
        }else {
            addUniqueConstraint(columnNames: "POSITION_ID,POSITION_CODE", tableName: "HR_ORG_POSITION_B", constraintName: "HR_ORG_POSITION_B_U1")
        }

        addNotNullConstraint(tableName: "HR_ORG_POSITION_B", columnName: "NAME", columnDataType: "varchar(100)")
    }

    changeSet(author: "jialong.zuo", id: "20170705-sys_attach_category_b-add-required") {
        addNotNullConstraint(tableName: "SYS_ATTACH_CATEGORY_B", columnName: "CATEGORY_NAME", columnDataType: "varchar(40)")
    }



    changeSet(author: "jialong.zuo", id: "20170705-sys_code_b-add-required") {
	addNotNullConstraint(tableName: "SYS_CODE_B", columnName: "DESCRIPTION", columnDataType: "varchar(250)")
    }

    changeSet(author: "jialong.zuo", id: "20170705-sys_code_value_b-add-required") {

            dropUniqueConstraint(tableName: "SYS_CODE_VALUE_B", constraintName: "SYS_CODE_VALUE_B_U1")
            addNotNullConstraint(tableName: "SYS_CODE_VALUE_B", columnName: "CODE_ID", columnDataType: "bigint")
            addNotNullConstraint(tableName: "SYS_CODE_VALUE_B", columnName: "VALUE", columnDataType: "varchar(150)")
            if(!mhi.isDbType('postgresql')){
                addUniqueConstraint(columnNames: "CODE_ID,VALUE", tableName: "SYS_CODE_VALUE_B", constraintName: "SYS_CODE_VALUE_B_U1")
            }else {
                addUniqueConstraint(columnNames: "CODE_VALUE_ID,CODE_ID,VALUE", tableName: "SYS_CODE_VALUE_B", constraintName: "SYS_CODE_VALUE_B_U1")
            }

            addNotNullConstraint(tableName: "SYS_CODE_VALUE_B", columnName: "MEANING", columnDataType: "varchar(150)")
    }

    changeSet(author: "niujiaqing", id: "20170706-sys_function_resource") {
        if(mhi.isDbType('mysql')){
            addAutoIncrement(columnDataType:"BIGINT", columnName:"FUNC_SRC_ID",tableName:"SYS_FUNCTION_RESOURCE")
        }
    }

    changeSet(author: "qiangzeng", id: "20170817-sys_job_running_info") {
        addColumn(tableName: "SYS_JOB_RUNNING_INFO") {
            column(name: "IP_ADDRESS", type: "VARCHAR(50)", remarks: "IP地址",afterColumn:"EXECUTION_SUMMARY")
        }
    }

    changeSet(author: "qixiangyu",id:"20170901-qixiangyu-add_attribute-for-org-1"){
        addColumn(tableName: "HR_ORG_UNIT_B"){
            column(name:"ATTRIBUTE_CATEGORY",type:"varchar(240)")
        }
        addColumn(tableName: "HR_ORG_UNIT_B"){
            column(name:"ATTRIBUTE1",type:"varchar(240)")
        }
        addColumn(tableName: "HR_ORG_UNIT_B"){
            column(name:"ATTRIBUTE2",type:"varchar(240)")
        }
        addColumn(tableName: "HR_ORG_UNIT_B"){
            column(name:"ATTRIBUTE3",type:"varchar(240)")
        }
        addColumn(tableName: "HR_ORG_UNIT_B"){
            column(name:"ATTRIBUTE4",type:"varchar(240)")
        }
        addColumn(tableName: "HR_ORG_UNIT_B"){
            column(name:"ATTRIBUTE5",type:"varchar(240)")
        }
        addColumn(tableName: "HR_ORG_UNIT_B"){
            column(name:"ATTRIBUTE6",type:"varchar(240)")
        }
        addColumn(tableName: "HR_ORG_UNIT_B"){
            column(name:"ATTRIBUTE7",type:"varchar(240)")
        }
        addColumn(tableName: "HR_ORG_UNIT_B"){
            column(name:"ATTRIBUTE8",type:"varchar(240)")
        }
        addColumn(tableName: "HR_ORG_UNIT_B"){
            column(name:"ATTRIBUTE9",type:"varchar(240)")
        }
        addColumn(tableName: "HR_ORG_UNIT_B"){
            column(name:"ATTRIBUTE10",type:"varchar(240)")
        }
        addColumn(tableName: "HR_ORG_UNIT_B"){
            column(name:"ATTRIBUTE11",type:"varchar(240)")
        }
        addColumn(tableName: "HR_ORG_UNIT_B"){
            column(name:"ATTRIBUTE12",type:"varchar(240)")
        }
        addColumn(tableName: "HR_ORG_UNIT_B"){
            column(name:"ATTRIBUTE13",type:"varchar(240)")
        }
        addColumn(tableName: "HR_ORG_UNIT_B"){
            column(name:"ATTRIBUTE14",type:"varchar(240)")
        }
        addColumn(tableName: "HR_ORG_UNIT_B"){
            column(name:"ATTRIBUTE15",type:"varchar(240)")
        }

        addColumn(tableName: "HR_ORG_POSITION_B"){
            column(name:"ATTRIBUTE_CATEGORY",type:"varchar(240)")
        }
        addColumn(tableName: "HR_ORG_POSITION_B"){
            column(name:"ATTRIBUTE1",type:"varchar(240)")
        }
        addColumn(tableName: "HR_ORG_POSITION_B"){
            column(name:"ATTRIBUTE2",type:"varchar(240)")
        }
        addColumn(tableName: "HR_ORG_POSITION_B"){
            column(name:"ATTRIBUTE3",type:"varchar(240)")
        }
        addColumn(tableName: "HR_ORG_POSITION_B"){
            column(name:"ATTRIBUTE4",type:"varchar(240)")
        }
        addColumn(tableName: "HR_ORG_POSITION_B"){
            column(name:"ATTRIBUTE5",type:"varchar(240)")
        }
        addColumn(tableName: "HR_ORG_POSITION_B"){
            column(name:"ATTRIBUTE6",type:"varchar(240)")
        }
        addColumn(tableName: "HR_ORG_POSITION_B"){
            column(name:"ATTRIBUTE7",type:"varchar(240)")
        }
        addColumn(tableName: "HR_ORG_POSITION_B"){
            column(name:"ATTRIBUTE8",type:"varchar(240)")
        }
        addColumn(tableName: "HR_ORG_POSITION_B"){
            column(name:"ATTRIBUTE9",type:"varchar(240)")
        }
        addColumn(tableName: "HR_ORG_POSITION_B"){
            column(name:"ATTRIBUTE10",type:"varchar(240)")
        }
        addColumn(tableName: "HR_ORG_POSITION_B"){
            column(name:"ATTRIBUTE11",type:"varchar(240)")
        }
        addColumn(tableName: "HR_ORG_POSITION_B"){
            column(name:"ATTRIBUTE12",type:"varchar(240)")
        }
        addColumn(tableName: "HR_ORG_POSITION_B"){
            column(name:"ATTRIBUTE13",type:"varchar(240)")
        }
        addColumn(tableName: "HR_ORG_POSITION_B"){
            column(name:"ATTRIBUTE14",type:"varchar(240)")
        }
        addColumn(tableName: "HR_ORG_POSITION_B"){
            column(name:"ATTRIBUTE15",type:"varchar(240)")
        }

        addColumn(tableName: "FND_COMPANY_B"){
            column(name:"ATTRIBUTE_CATEGORY",type:"varchar(240)")
        }
        addColumn(tableName: "FND_COMPANY_B"){
            column(name:"ATTRIBUTE1",type:"varchar(240)")
        }
        addColumn(tableName: "FND_COMPANY_B"){
            column(name:"ATTRIBUTE2",type:"varchar(240)")
        }
        addColumn(tableName: "FND_COMPANY_B"){
            column(name:"ATTRIBUTE3",type:"varchar(240)")
        }
        addColumn(tableName: "FND_COMPANY_B"){
            column(name:"ATTRIBUTE4",type:"varchar(240)")
        }
        addColumn(tableName: "FND_COMPANY_B"){
            column(name:"ATTRIBUTE5",type:"varchar(240)")
        }
        addColumn(tableName: "FND_COMPANY_B"){
            column(name:"ATTRIBUTE6",type:"varchar(240)")
        }
        addColumn(tableName: "FND_COMPANY_B"){
            column(name:"ATTRIBUTE7",type:"varchar(240)")
        }
        addColumn(tableName: "FND_COMPANY_B"){
            column(name:"ATTRIBUTE8",type:"varchar(240)")
        }
        addColumn(tableName: "FND_COMPANY_B"){
            column(name:"ATTRIBUTE9",type:"varchar(240)")
        }
        addColumn(tableName: "FND_COMPANY_B"){
            column(name:"ATTRIBUTE10",type:"varchar(240)")
        }
        addColumn(tableName: "FND_COMPANY_B"){
            column(name:"ATTRIBUTE11",type:"varchar(240)")
        }
        addColumn(tableName: "FND_COMPANY_B"){
            column(name:"ATTRIBUTE12",type:"varchar(240)")
        }
        addColumn(tableName: "FND_COMPANY_B"){
            column(name:"ATTRIBUTE13",type:"varchar(240)")
        }
        addColumn(tableName: "FND_COMPANY_B"){
            column(name:"ATTRIBUTE14",type:"varchar(240)")
        }
        addColumn(tableName: "FND_COMPANY_B"){
            column(name:"ATTRIBUTE15",type:"varchar(240)")
        }

    }

        changeSet(author: "qixiangyu", id: "20170918-alter-url-length") {
            modifyDataType(tableName: "SYS_LOV", columnName: "CUSTOM_URL", newDataType: "VARCHAR(2000)")
            modifyDataType(tableName: "SYS_RESOURCE_CUSTOMIZATION", columnName: "URL", newDataType: "VARCHAR(2000)")
            modifyDataType(tableName: "SYS_LOV_ITEM", columnName: "CONDITION_FIELD_SELECT_URL", newDataType: "VARCHAR(2000)")
        }



        changeSet(author: "niujiaqing", id: "20171012-category-type-length") {
            modifyDataType(tableName: "SYS_ATTACH_CATEGORY_B", columnName: "ALLOWED_FILE_TYPE", newDataType: "VARCHAR(300)")
        }


    changeSet(author: "qiangzeng",id:"20171018-add_attribute-for-employee-1") {
        addColumn(tableName: "HR_EMPLOYEE") {
            column(name: "ATTRIBUTE_CATEGORY", type: "varchar(240)",afterColumn:"EFFECTIVE_END_DATE")
        }
    }

    changeSet(author: "yangzhizheng", id: "20170824-sys_forms") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'SYS_FORMS_S', startValue:"10001")
        }
        createTable(tableName: "SYS_FORMS"){
            if (mhi.getDbType().isSuppportAutoIncrement()){
                column(name:"FORM_ID",type:"bigint",autoIncrement: "true", startWith:"10001",remarks:"表单ID"){
                    constraints(nullable: "false", primaryKey: "true",primaryKeyName: "FORM_BUILDER_PK")
                }
            } else {
                column(name:"FORM_ID",type:"bigint",remarks:"表单ID"){
                    constraints(nullable: "false", primaryKey: "true",primaryKeyName: "FORM_BUILDER_PK")
                }
            }

            if(!mhi.isDbType('postgresql')){
                column(name:"CODE",type:"varchar(30)",remarks:"表单code"){
                    constraints(nullable: "false",unique:"true",uniqueConstraintName:"FORM_BUILDER_U1")
                }
            }else {
                column(name:"CODE",type:"varchar(30)",remarks:"表单code"){
                    constraints(nullable: "false")
                }
            }

            column(name:"DESCRIPTION",type:"varchar(220)",remarks:"表单描述")
            column(name:"CONTENT",type:'clob',remarks: "表单代码")
            column(name:"IS_PUBLISH",type:'varchar(1)',defaultValue : "N",remarks: "是否发布")
            column(name:"OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "bigint", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "bigint", defaultValue : "-1")
            column(name: "CREATED_BY", type: "bigint", defaultValue : "-1")
            column(name: "CREATION_DATE", type: "datetime", defaultValueComputed : "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATED_BY", type: "bigint", defaultValue : "-1")
            column(name: "LAST_UPDATE_DATE", type: "datetime", defaultValueComputed : "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATE_LOGIN", type: "bigint", defaultValue : "-1")
            column(name:"ATTRIBUTE_CATEGORY",type:"varchar(30)")
        }
        if(mhi.isDbType('postgresql')){
            addUniqueConstraint(columnNames: "FORM_ID,CODE", tableName: "SYS_FORMS", constraintName: "FORM_BUILDER_U1")
        }
    }

    changeSet(author: "yangzhizheng", id: "20171030-sys_lov") {
        addColumn(tableName: "SYS_LOV") {
            column(name: "LOV_PAGE_SIZE", type: "VARCHAR(3)", remarks: "每页页数", defaultValue: "10",afterColumn:"HEIGHT")
        }
    }

    changeSet(author: "jialongzuo", id: "20171103-fnd_flex_rule_field_add1") {
        addColumn(tableName: "FND_FLEX_RULE_FIELD") {
            column(name: "DESCRIPTION", type: "VARCHAR(200)", remarks: "描述",afterColumn:"MODEL_COLUMN_ID")
        }
    }
    changeSet(author: "yangzhizheng", id: "20171013-sys-hotkey-1") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'SYS_HOTKEY_B_S',startValue:"10001")
        }
        createTable (tableName: "SYS_HOTKEY_B", remarks: "系统热键") {
            if (mhi.getDbType().isSuppportAutoIncrement()){
                column(name: "HOTKEY_ID", type: "BIGINT", autoIncrement: "true", startWith: "10001", remarks: "PK") {
                    constraints(nullable: "false", primaryKey: "true",primaryKeyName:"SYS_HOTKEY_PK")
                }
            } else {
                column(name: "HOTKEY_ID", type: "BIGINT", remarks: "PK") {
                    constraints(nullable: "false", primaryKey: "true",primaryKeyName:"SYS_HOTKEY_PK")
                }
            }

            column(name: "CODE", type:"VARCHAR(50)",remarks: "热键编码") {
                constraints(nullable: "false")
            }
            column(name: "HOTKEY_LEVEL", type:"VARCHAR(50)",defaultValue : "system",remarks: "热键级别"){
                constraints(nullable: "false")
            }
            column(name: "HOTKEY_LEVEL_ID", type:"BIGINT",defaultValue : "0",remarks: "热键级别ID"){
                constraints(nullable: "false")
            }
            column(name: "HOTKEY", type:"VARCHAR(50)",remarks: "热键")
            column(name: "DESCRIPTION", type:"varchar(250)",remarks: "热键描述")

            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue : "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "CREATED_BY", type: "BIGINT", defaultValue : "-1")
            column(name: "CREATION_DATE", type: "DATETIME", defaultValueComputed : "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", defaultValueComputed : "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")
        }
        if(!mhi.isDbType('postgresql')){
            addUniqueConstraint(columnNames:"CODE,HOTKEY_LEVEL,HOTKEY_LEVEL_ID",tableName:"SYS_HOTKEY_B",constraintName: "SYS_HOTKEY_U1")
        }else {
            addUniqueConstraint(columnNames:"HOTKEY_ID,CODE,HOTKEY_LEVEL,HOTKEY_LEVEL_ID",tableName:"SYS_HOTKEY_B",constraintName: "SYS_HOTKEY_U1")
        }



        createTable (tableName: "SYS_HOTKEY_TL") {
            column(name: "HOTKEY_ID", type: "BIGINT", remarks: "PK") {
                constraints(nullable: "false", primaryKey: "true")
            }
            column(name:"LANG",type:"varchar(10)",remarks: "语言"){
                constraints(nullable: "false", primaryKey: "true")
            }
            column(name: "DESCRIPTION", type:"varchar(250)",remarks: "热键描述")

            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue : "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "CREATED_BY", type: "BIGINT", defaultValue : "-1")
            column(name: "CREATION_DATE", type: "DATETIME", defaultValueComputed : "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", defaultValueComputed : "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")
        }
    }


        changeSet(author: "qixiangyu",id:"201701113-qixiangyu-alter-sys_profile_value"){
            modifyDataType(tableName: "SYS_PROFILE_VALUE", columnName: "PROFILE_VALUE", newDataType: "VARCHAR(255)")
        }

    changeSet(author: "jialongzuo", id: "201701113-jialongzuo-alter-data-permission") {
        dropUniqueConstraint(tableName: "SYS_PERMISSION_RULE_DETAIL", constraintName: "SYS_PERMISSION_RULE_DETAIL_U1")
        renameColumn(tableName: "SYS_PERMISSION_RULE_DETAIL", oldColumnName: "PERMISSION_FIELD_VALUE", newColumnName: "PERMISSION_FIELD_VALUE_TEMP", columnDataType: "BIGINT")
        addColumn(tableName: "SYS_PERMISSION_RULE_DETAIL") {
            column(name: "PERMISSION_FIELD_VALUE", type: "VARCHAR(200)")
        }
        dropColumn(tableName: "SYS_PERMISSION_RULE_DETAIL", columnName: "PERMISSION_FIELD_VALUE_TEMP")
        createIndex(tableName: "SYS_PERMISSION_RULE_DETAIL", indexName: "SYS_PERMISSION_RULE_DETAIL_U1") {
            column(name: "PERMISSION_FIELD_VALUE")
            column(name: "RULE_ID")
        }

    }

    changeSet(author: "qiangzeng", id: "20171115-sys_sys_parameter_config") {
        addColumn(tableName: "SYS_PARAMETER_CONFIG") {
            column(name: "LABEL_WIDTH", type: "INT", remarks: "label宽度",afterColumn:"DISPLAY_LENGTH")
        }
    }

    changeSet(author: "qiangzeng",id:"20180110-sys-parameter-config-1") {
        addColumn(tableName: "SYS_PARAMETER_CONFIG") {
            column(name: "SOURCE_TYPE", type: "varchar(20)",remarks: "数据类型",afterColumn:"ENABLED",defaultValue: 'LOV')
        }
    }
    changeSet(author: "yangzhizheng", id: "20180131-sys-lov") {
        addColumn(tableName: "SYS_LOV"){
            column(name:"TREE_FLAG",type: "varchar(1)",defaultValue: "N",remarks: "是否为树形结构")
        }
        addColumn(tableName: "SYS_LOV"){
            column(name:"ID_FIELD",type: "varchar(80)",remarks: "树形结构ID字段")
        }
        addColumn(tableName: "SYS_LOV"){
            column(name:"PARENT_ID_FIELD",type: "varchar(80)",remarks: "树形结构parentID字段")
        }
    }
    if (mhi.isDbType('postgresql') || mhi.isDbType('hana')) {
        changeSet(author: "qixiangyu", id: "20180208-sys-attachment-1-fix") {
            dropIndex(tableName: "SYS_ATTACHMENT", indexName: "SYS_ATTACHMENT_N2")
        }
    }

    changeSet(author: "qiangzeng", id: "20180208-sys-attachment-1") {
        if(!mhi.isDbType('postgresql')){
            addUniqueConstraint(tableName:"SYS_ATTACHMENT", columnNames:"SOURCE_TYPE,SOURCE_KEY", constraintName:"SYS_ATTACHMENT_U1")
        }else {
            addUniqueConstraint(tableName:"SYS_ATTACHMENT", columnNames:"ATTACHMENT_ID,SOURCE_TYPE,SOURCE_KEY", constraintName:"SYS_ATTACHMENT_U1")
        }
    }

    if (mhi.isDbType('mysql')) {
        changeSet(author: "qixiangyu", id: "interface-alter-url-length-drop-index") {
            preConditions(onFail:"MARK_RAN"){
                indexExists(indexName:"SYS_IF_INVOKE_INBOUND_N2")
            }
            dropIndex(tableName: "SYS_IF_INVOKE_INBOUND", indexName: "SYS_IF_INVOKE_INBOUND_N2")
        }
    }

    if (mhi.isDbType('mysql')) {
        changeSet(author: "qixiangyu", id: "interface-alter-url-length-drop-index2") {
            preConditions(onFail:"MARK_RAN"){
                indexExists(indexName:"SYS_IF_INVOKE_OUTBOUND_N2")
            }
            dropIndex(tableName: "SYS_IF_INVOKE_OUTBOUND", indexName: "SYS_IF_INVOKE_OUTBOUND_N2")
        }
    }

        changeSet(author: "qixiangyu", id: "interface-alter-url-length") {
            modifyDataType(tableName: "SYS_IF_INVOKE_INBOUND", columnName: "INTERFACE_URL", newDataType: "VARCHAR(2000)")
            modifyDataType(tableName: "SYS_IF_INVOKE_OUTBOUND", columnName: "INTERFACE_URL", newDataType: "VARCHAR(2000)")
        }

    changeSet(author: "yinlijian",id:"20180411-sys-code-rules-line") {
        addColumn(tableName: "SYS_CODE_RULES_LINE") {
            column(name: "STEP_NUMBER", type: "bigint",remarks: "更新数据库步长",afterColumn:"RESET_DATE")
        }
    }

    changeSet(author: "qiang.zeng",id:"20180817-sys-code-value-b") {
        addColumn(tableName: "SYS_CODE_VALUE_B") {
            column(name: "PARENT_CODE_VALUE", type: "varchar(150)",remarks: "父级快码值")
        }
    }
}
