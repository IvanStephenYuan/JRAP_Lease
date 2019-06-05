import com.jingrui.jrap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()

databaseChangeLog(logicalFilePath:"2019-05-01-init-table-migration.groovy"){
    changeSet(author:"Admin", id: "2019-05-31_ACC_DOCUMENT"){
        if(mhi.isDbType('oracle') || mhi.isDbType('hana')){
            createSequence(sequenceName:'ACC_DOCUMENT_S', startValue:"10001")
        }
        createTable(tableName:"ACC_DOCUMENT"){
            column(name:"DOCUMENT_ID",type:"bigint",remarks:"主键，供其他表做外键"){
                constraints(nullable:"false",primaryKey: "true",primaryKeyName: "ACC_DOCUMENT_PK")
            }
            column(name:"DOCUMENT_CODE",type:"varchar(100)",remarks:"结算单号")
            column(name:"COMPANY_ID",type:"bigint",remarks:"公司FND_COMPANY_B.COMPANY_ID")
            column(name:"TRANSACTION_ID",type:"bigint",remarks:"结算事务ID")
            column(name:"STATUS",type:"varchar(60)",defaultValue:"NEW",remarks:"状态")
            column(name:"SETTLE_TYPE",type:"varchar(60)",remarks:"结算类型")
            column(name:"SETTLE_AMOUNT",type:"decimal",remarks:"结算金额")
            column(name:"SETTLE_DATE",type:"datetime",remarks:"结算日期")
            column(name:"SETTLE_CHANNEL",type:"varchar(60)",remarks:"结算通道")
            column(name:"SETTLE_ITEM",type:"varchar(60)",remarks:"结算项目")
            column(name:"DIGEST",type:"varchar(200)",remarks:"摘要")
            column(name:"ACCOUNT_ID",type:"bigint",remarks:"本方账户ID")
            column(name:"OPTACCT_ID",type:"bigint",remarks:"对方账户ID")
            column(name:"OPTACCT_NAME",type:"varchar(200)",remarks:"对方账户")
            column(name:"OPTACCT_NUMBER",type:"varchar(60)",remarks:"对方账号")
            column(name:"OPTACCT_BANK",type:"varchar(200)",remarks:"对方银行")
            column(name:"OPTACCT_BANK_PN",type:"varchar(60)",remarks:"对方银行PN")
            column(name:"OPTACCT_BANK_CODE",type:"varchar(60)",remarks:"对方银行编码")
            column(name:"OPTACCT_BANK_CITY",type:"varchar(60)",remarks:"对方银行城市")
            column(name:"SN",type:"varchar(60)",remarks:"流水号")
            column(name:"AUX01",type:"varchar(60)",remarks:"辅助核算01")
            column(name:"AUX02",type:"varchar(60)",remarks:"辅助核算02")
            column(name:"AUX03",type:"varchar(60)",remarks:"辅助核算03")
            column(name:"AUX04",type:"varchar(60)",remarks:"辅助核算04")
            column(name:"AUX05",type:"varchar(60)",remarks:"辅助核算05")
            column(name:"POST_DATE",type:"datetime",remarks:"过账日期")
            column(name:"DEAL_DATE",type:"datetime",remarks:"交易日期")
            column(name:"FINISH_DATE",type:"datetime",remarks:"结束日期")
            column(name:"OBJECT_VERSION_NUMBER",type:"bigint",defaultValue:"1",remarks:"行版本号，用来处理锁"){
                constraints(nullable:"false")
            }
            column(name:"CREATED_BY",type:"bigint", defaultValue : "-1")
            column(name:"CREATION_DATE",type:"datetime", defaultValueComputed : "CURRENT_TIMESTAMP")
            column(name:"LAST_UPDATED_BY",type:"bigint", defaultValue : "-1")
            column(name:"LAST_UPDATE_DATE",type:"datetime", defaultValueComputed : "CURRENT_TIMESTAMP")
            column(name:"LAST_UPDATE_LOGIN",type:"bigint", defaultValue : "-1")
            column(name:"ATTRIBUTE_CATEGORY",type:"varchar(30)")
            column(name:"ATTRIBUTE1",type:"varchar(150)")
            column(name:"ATTRIBUTE2",type:"varchar(150)")
            column(name:"ATTRIBUTE3",type:"varchar(150)")
            column(name:"ATTRIBUTE4",type:"varchar(150)")
            column(name:"ATTRIBUTE5",type:"varchar(150)")
            column(name:"ATTRIBUTE6",type:"varchar(150)")
            column(name:"ATTRIBUTE7",type:"varchar(150)")
            column(name:"ATTRIBUTE8",type:"varchar(150)")
            column(name:"ATTRIBUTE9",type:"varchar(150)")
            column(name:"ATTRIBUTE10",type:"varchar(150)")
            column(name:"ATTRIBUTE11",type:"varchar(150)")
            column(name:"ATTRIBUTE12",type:"varchar(150)")
            column(name:"ATTRIBUTE13",type:"varchar(150)")
            column(name:"ATTRIBUTE14",type:"varchar(150)")
            column(name:"ATTRIBUTE15",type:"varchar(150)")
        }
        addUniqueConstraint(columnNames:"DOCUMENT_CODE,COMPANY_ID",tableName:"ACC_DOCUMENT",constraintName: "ACC_DOCUMENT_U1")
        createIndex(tableName:"ACC_DOCUMENT",indexName:"ACC_DOCUMENT_N1"){
            column(name: "COMPANY_ID")
            column(name: "OPTACCT_NUMBER")
        }

    }



    changeSet(author:"Admin", id: "2019-05-31_ACC_DOCUMENT_DETAIL"){
        if(mhi.isDbType('oracle') || mhi.isDbType('hana')){
            createSequence(sequenceName:'ACC_DOCUMENT_DETAIL_S', startValue:"10001")
        }
        createTable(tableName:"ACC_DOCUMENT_DETAIL"){
            column(name:"DETAIL_ID",type:"bigint",remarks:"主键，供其他表做外键"){
                constraints(nullable:"false",primaryKey: "true",primaryKeyName: "ACC_DOCUMENT_DETAIL_PK")
            }
            column(name:"DOCUMENT_ID",type:"bigint",remarks:"结算单ID"){
                constraints(nullable:"false")
            }
            column(name:"AMOUNT",type:"decimal(60)",remarks:"金额")
            column(name:"AUX01",type:"varchar(60)",remarks:"辅助核算01")
            column(name:"AUX02",type:"varchar(60)",remarks:"辅助核算02")
            column(name:"AUX03",type:"varchar(60)",remarks:"辅助核算03")
            column(name:"DIGEST",type:"varchar(200)",remarks:"摘要")
            column(name:"OBJECT_VERSION_NUMBER",type:"bigint",defaultValue:"1",remarks:"行版本号，用来处理锁"){
                constraints(nullable:"false")
            }
            column(name:"CREATED_BY",type:"bigint", defaultValue : "-1")
            column(name:"CREATION_DATE",type:"datetime", defaultValueComputed : "CURRENT_TIMESTAMP")
            column(name:"LAST_UPDATED_BY",type:"bigint", defaultValue : "-1")
            column(name:"LAST_UPDATE_DATE",type:"datetime", defaultValueComputed : "CURRENT_TIMESTAMP")
            column(name:"LAST_UPDATE_LOGIN",type:"bigint", defaultValue : "-1")
            column(name:"ATTRIBUTE_CATEGORY",type:"varchar(30)")
            column(name:"ATTRIBUTE1",type:"varchar(150)")
            column(name:"ATTRIBUTE2",type:"varchar(150)")
            column(name:"ATTRIBUTE3",type:"varchar(150)")
            column(name:"ATTRIBUTE4",type:"varchar(150)")
            column(name:"ATTRIBUTE5",type:"varchar(150)")
            column(name:"ATTRIBUTE6",type:"varchar(150)")
            column(name:"ATTRIBUTE7",type:"varchar(150)")
            column(name:"ATTRIBUTE8",type:"varchar(150)")
            column(name:"ATTRIBUTE9",type:"varchar(150)")
            column(name:"ATTRIBUTE10",type:"varchar(150)")
            column(name:"ATTRIBUTE11",type:"varchar(150)")
            column(name:"ATTRIBUTE12",type:"varchar(150)")
            column(name:"ATTRIBUTE13",type:"varchar(150)")
            column(name:"ATTRIBUTE14",type:"varchar(150)")
            column(name:"ATTRIBUTE15",type:"varchar(150)")
        }
        createIndex(tableName:"ACC_DOCUMENT_DETAIL",indexName:"ACC_DOCUMENT_DETAIL_N1"){
            column(name: "DOCUMENT_ID")
        }

    }



    changeSet(author:"Admin", id: "2019-05-31_ACC_TRANSACTION"){
        if(mhi.isDbType('oracle') || mhi.isDbType('hana')){
            createSequence(sequenceName:'ACC_TRANSACTION_S', startValue:"10001")
        }
        createTable(tableName:"ACC_TRANSACTION"){
            column(name:"TRANSACTION_ID",type:"bigint",remarks:"主键，供其他表做外键"){
                constraints(nullable:"false",primaryKey: "true",primaryKeyName: "ACC_TRANSACTION_PK")
            }
            column(name:"TRANSACTION_CODE",type:"varchar(60)",remarks:"事务编号")
            column(name:"TRANSACTION_TYPE",type:"varchar(60)",remarks:"事务类型")
            column(name:"COMPANY_ID",type:"bigint",remarks:"公司FND_COMPANY_B.COMPANY_ID")
            column(name:"STATUS",type:"varchar(60)",remarks:"事务状态")
            column(name:"TRANSACTION_DATE",type:"datetime",remarks:"事务日期")
            column(name:"TRANSACTION_AMOUNT",type:"decimal",remarks:"交易金额")
            column(name:"TRANSACTION_NUMBER",type:"decimal",remarks:"交易笔数")
            column(name:"PAY_AMOUNT",type:"decimal",remarks:"付款金额")
            column(name:"PAY_NUMBER",type:"decimal",remarks:"付款笔数")
            column(name:"RECEIPT_AMOUNT",type:"decimal",remarks:"收款金额")
            column(name:"RECEIPT_NUMBER",type:"decimal",remarks:"收款笔数")
            column(name:"DIGEST",type:"varchar(60)",remarks:"摘要")
            column(name:"SERIAL_NUMBER",type:"varchar(60)",remarks:"流水号")
            column(name:"EMPLOYEE_ID",type:"bigint",remarks:"操作员")
            column(name:"DEAL_DATE",type:"datetime",remarks:"交易日期")
            column(name:"SUCCESS_DATE",type:"datetime",remarks:"成功日期")
            column(name:"EXCEPTION_DATE",type:"datetime",remarks:"异常日期")
            column(name:"REVERSE_DATE",type:"datetime",remarks:"反冲日期")
            column(name:"AUX01",type:"varchar(200)",remarks:"辅助核算01")
            column(name:"AUX02",type:"varchar(200)",remarks:"辅助核算02")
            column(name:"AUX03",type:"varchar(200)",remarks:"辅助核算03")
            column(name:"AUX04",type:"varchar(200)",remarks:"辅助核算04")
            column(name:"AUX05",type:"varchar(200)",remarks:"辅助核算05")
            column(name:"OBJECT_VERSION_NUMBER",type:"bigint",defaultValue:"1",remarks:"行版本号，用来处理锁"){
                constraints(nullable:"false")
            }
            column(name:"CREATED_BY",type:"bigint", defaultValue : "-1")
            column(name:"CREATION_DATE",type:"datetime", defaultValueComputed : "CURRENT_TIMESTAMP")
            column(name:"LAST_UPDATED_BY",type:"bigint", defaultValue : "-1")
            column(name:"LAST_UPDATE_DATE",type:"datetime", defaultValueComputed : "CURRENT_TIMESTAMP")
            column(name:"LAST_UPDATE_LOGIN",type:"bigint", defaultValue : "-1")
            column(name:"ATTRIBUTE_CATEGORY",type:"varchar(30)")
            column(name:"ATTRIBUTE1",type:"varchar(150)")
            column(name:"ATTRIBUTE2",type:"varchar(150)")
            column(name:"ATTRIBUTE3",type:"varchar(150)")
            column(name:"ATTRIBUTE4",type:"varchar(150)")
            column(name:"ATTRIBUTE5",type:"varchar(150)")
            column(name:"ATTRIBUTE6",type:"varchar(150)")
            column(name:"ATTRIBUTE7",type:"varchar(150)")
            column(name:"ATTRIBUTE8",type:"varchar(150)")
            column(name:"ATTRIBUTE9",type:"varchar(150)")
            column(name:"ATTRIBUTE10",type:"varchar(150)")
            column(name:"ATTRIBUTE11",type:"varchar(150)")
            column(name:"ATTRIBUTE12",type:"varchar(150)")
            column(name:"ATTRIBUTE13",type:"varchar(150)")
            column(name:"ATTRIBUTE14",type:"varchar(150)")
            column(name:"ATTRIBUTE15",type:"varchar(150)")
        }
        addUniqueConstraint(columnNames:"TRANSACTION_CODE,COMPANY_ID",tableName:"ACC_TRANSACTION",constraintName: "ACC_TRANSACTION_U1")
    }



    changeSet(author:"Admin", id: "2019-05-31_ACC_OPERATE"){
        if(mhi.isDbType('oracle') || mhi.isDbType('hana')){
            createSequence(sequenceName:'ACC_OPERATE_S', startValue:"10001")
        }
        createTable(tableName:"ACC_OPERATE"){
            column(name:"OPERATE_ID",type:"bigint",autoIncrement:"true",startWith:"10001",remarks:"表ID，主键，供其他表做外键"){
                constraints(nullable:"false",primaryKey: "true",primaryKeyName: "ACC_OPERATE_PK")
            }
            column(name:"OPERATE_TYPE",type:"varchar(60)",remarks:"操作类型")
            column(name:"OPERATE_DATE",type:"datetime",remarks:"操作日期")
            column(name:"TRANSACTION_ID",type:"bigint",remarks:"结算事务ID")
            column(name:"BATCH_NO",type:"varchar(60)",remarks:"批次号")
            column(name:"OPERATE_STATUS",type:"varchar(60)",remarks:"操作状态")
            column(name:"DEAL_STATUS",type:"varchar(60)",remarks:"交易状态")
            column(name:"USER_ID",type:"bigint",remarks:"用户ID")
            column(name:"TOTAL_AMOUNT",type:"decimal",remarks:"交易总额")
            column(name:"TOTAL_COUNT",type:"decimal",remarks:"交易笔数")
            column(name:"SUCCESS_AMOUNT",type:"decimal",remarks:"成功金额")
            column(name:"SUCCESS_COUNT",type:"decimal",remarks:"成功笔数")
            column(name:"FAILURE_AMOUNT",type:"decimal",remarks:"失败金额")
            column(name:"FAILURE_COUNT",type:"decimal",remarks:"失败笔数")
            column(name:"ACCOUNT_ID",type:"decimal",remarks:"本方账号")
            column(name:"CUST_CODE",type:"varchar(200)",remarks:"客户号")
            column(name:"USER_NAME",type:"varchar(200)",remarks:"用户名")
            column(name:"PASSWORD",type:"varchar(200)",remarks:"密码")
            column(name:"TX_CODE",type:"varchar(60)",remarks:"交易码")
            column(name:"USEAGE",type:"varchar(200)",remarks:"用途代码")
            column(name:"REQUEST_MESSAGE",type:"blob",remarks:"请求报文")
            column(name:"RESPONSE_MESSAGE",type:"blob",remarks:"响应报文")
            column(name:"RETURN_CODE",type:"varchar(70)",remarks:"返回编码")
            column(name:"RETURN_MSG",type:"varchar(200)",remarks:"返回详情")
            column(name:"DEAL_SN",type:"varchar(60)",remarks:"交易唯一码")
            column(name:"REQUEST01",type:"varchar(200)",remarks:"请求字段01")
            column(name:"REQUEST02",type:"varchar(200)",remarks:"请求字段02")
            column(name:"RESPONSE01",type:"varchar(200)",remarks:"响应字段01")
            column(name:"RESPONSE02",type:"varchar(200)",remarks:"响应字段02")
            column(name:"OBJECT_VERSION_NUMBER",type:"bigint",defaultValue:"1",remarks:"行版本号，用来处理锁"){
                constraints(nullable:"false")
            }
            column(name:"CREATED_BY",type:"bigint", defaultValue : "-1")
            column(name:"CREATION_DATE",type:"datetime", defaultValueComputed : "CURRENT_TIMESTAMP")
            column(name:"LAST_UPDATED_BY",type:"bigint", defaultValue : "-1")
            column(name:"LAST_UPDATE_DATE",type:"datetime", defaultValueComputed : "CURRENT_TIMESTAMP")
            column(name:"LAST_UPDATE_LOGIN",type:"bigint", defaultValue : "-1")
            column(name:"PROGRAM_APPLICATION_ID",type:"bigint")
            column(name:"PROGRAM_ID",type:"bigint")
            column(name:"PROGRAM_UPDATE_DATE",type:"datetime")
            column(name:"REQUEST_ID",type:"bigint")
            column(name:"ATTRIBUTE_CATEGORY",type:"varchar(30)")
            column(name:"ATTRIBUTE1",type:"varchar(150)")
            column(name:"ATTRIBUTE2",type:"varchar(150)")
            column(name:"ATTRIBUTE3",type:"varchar(150)")
            column(name:"ATTRIBUTE4",type:"varchar(150)")
            column(name:"ATTRIBUTE5",type:"varchar(150)")
            column(name:"ATTRIBUTE6",type:"varchar(150)")
            column(name:"ATTRIBUTE7",type:"varchar(150)")
            column(name:"ATTRIBUTE8",type:"varchar(150)")
            column(name:"ATTRIBUTE9",type:"varchar(150)")
            column(name:"ATTRIBUTE10",type:"varchar(150)")
            column(name:"ATTRIBUTE11",type:"varchar(150)")
            column(name:"ATTRIBUTE12",type:"varchar(150)")
            column(name:"ATTRIBUTE13",type:"varchar(150)")
            column(name:"ATTRIBUTE14",type:"varchar(150)")
            column(name:"ATTRIBUTE15",type:"varchar(150)")
        }
        createIndex(tableName:"ACC_OPERATE",indexName:"ACC_OPERATE_N1"){
            column(name: "TRANSACTION_ID")
            column(name: "OPERATE_TYPE")
        }
        createIndex(tableName:"ACC_OPERATE",indexName:"ACC_OPERATE_N2"){
            column(name: "TRANSACTION_ID")
            column(name: "BATCH_NO")
        }

    }



    changeSet(author:"Admin", id: "2019-05-31_ACC_SETTLE_ITEM"){
        if(mhi.isDbType('oracle') || mhi.isDbType('hana')){
            createSequence(sequenceName:'ACC_SETTLE_ITEM_S', startValue:"10001")
        }
        createTable(tableName:"ACC_SETTLE_ITEM"){
            column(name:"ITEM_CODE",type:"varchar(60)",remarks:"结算项目(主键)"){
                constraints(nullable:"false")
            }
            column(name:"COMPANY_ID",type:"bigint",remarks:"公司FND_COMPANY_B.COMPANY_ID")
            column(name:"DESCRIPTION",type:"varchar(200)",remarks:"描述")
            column(name:"ACCOUNT_ID",type:"bigint",remarks:"本方账号")
            column(name:"REMARK",type:"varchar(200)",remarks:"备注")
            column(name:"ENABLED_FLAG",type:"varchar(1)",defaultValue:"Y",remarks:"启用标识")
            column(name:"OBJECT_VERSION_NUMBER",type:"bigint",defaultValue:"1",remarks:"行版本号，用来处理锁"){
                constraints(nullable:"false")
            }
            column(name:"CREATED_BY",type:"bigint", defaultValue : "-1")
            column(name:"CREATION_DATE",type:"datetime", defaultValueComputed : "CURRENT_TIMESTAMP")
            column(name:"LAST_UPDATED_BY",type:"bigint", defaultValue : "-1")
            column(name:"LAST_UPDATE_DATE",type:"datetime", defaultValueComputed : "CURRENT_TIMESTAMP")
            column(name:"LAST_UPDATE_LOGIN",type:"bigint", defaultValue : "-1")
            column(name:"PROGRAM_APPLICATION_ID",type:"bigint")
            column(name:"PROGRAM_ID",type:"bigint")
            column(name:"PROGRAM_UPDATE_DATE",type:"datetime")
            column(name:"REQUEST_ID",type:"bigint")
            column(name:"ATTRIBUTE_CATEGORY",type:"varchar(30)")
            column(name:"ATTRIBUTE1",type:"varchar(150)")
            column(name:"ATTRIBUTE2",type:"varchar(150)")
            column(name:"ATTRIBUTE3",type:"varchar(150)")
            column(name:"ATTRIBUTE4",type:"varchar(150)")
            column(name:"ATTRIBUTE5",type:"varchar(150)")
            column(name:"ATTRIBUTE6",type:"varchar(150)")
            column(name:"ATTRIBUTE7",type:"varchar(150)")
            column(name:"ATTRIBUTE8",type:"varchar(150)")
            column(name:"ATTRIBUTE9",type:"varchar(150)")
            column(name:"ATTRIBUTE10",type:"varchar(150)")
            column(name:"ATTRIBUTE11",type:"varchar(150)")
            column(name:"ATTRIBUTE12",type:"varchar(150)")
            column(name:"ATTRIBUTE13",type:"varchar(150)")
            column(name:"ATTRIBUTE14",type:"varchar(150)")
            column(name:"ATTRIBUTE15",type:"varchar(150)")
        }
        addPrimaryKey(columnNames:"COMPANY_ID,ITEM_CODE", constraintName:"ACC_SETTLE_ITEM_PK", tableName:"ACC_SETTLE_ITEM")
    }



    changeSet(author:"Admin", id: "2019-05-31_ACC_CHANNEL"){
        if(mhi.isDbType('oracle') || mhi.isDbType('hana')){
            createSequence(sequenceName:'ACC_CHANNEL_S', startValue:"10001")
        }
        createTable(tableName:"ACC_CHANNEL"){
            column(name:"CHANNEL",type:"varchar(60)",remarks:"通道(主键)"){
                constraints(nullable:"false")
            }
            column(name:"COMPANY_ID",type:"bigint",remarks:"公司FND_COMPANY_B.COMPANY_ID")
            column(name:"DESCRIPTION",type:"varchar(200)",remarks:"描述")
            column(name:"CUSTOMER_CODE",type:"varchar(200)",remarks:"客户号")
            column(name:"USER_NAME",type:"varchar(50)",remarks:"用户名")
            column(name:"PASSWORD",type:"varchar(200)",remarks:"密码")
            column(name:"ACCOUNT_ID",type:"bigint",remarks:"本方账号")
            column(name:"CLIENT_URL",type:"varchar(200)",remarks:"前置机地址")
            column(name:"CERT_PATH",type:"varchar(200)",remarks:"证书路径")
            column(name:"PFX_PATH",type:"varchar(200)",remarks:"私钥路径")
            column(name:"CERT_PASSWORD",type:"varchar(200)",remarks:"证书密码")
            column(name:"REFER01",type:"varchar(200)",remarks:"参考字段01")
            column(name:"REFER02",type:"varchar(200)",remarks:"参考字段02")
            column(name:"REFER03",type:"varchar(200)",remarks:"参考字段03")
            column(name:"REFER04",type:"varchar(200)",remarks:"参考字段04")
            column(name:"REFER05",type:"varchar(200)",remarks:"参考字段05")
            column(name:"ENABLED_FLAG",type:"varchar(1)",remarks:"启用标识")
            column(name:"OBJECT_VERSION_NUMBER",type:"bigint",defaultValue:"1",remarks:"行版本号，用来处理锁"){
                constraints(nullable:"false")
            }
            column(name:"CREATED_BY",type:"bigint", defaultValue : "-1")
            column(name:"CREATION_DATE",type:"datetime", defaultValueComputed : "CURRENT_TIMESTAMP")
            column(name:"LAST_UPDATED_BY",type:"bigint", defaultValue : "-1")
            column(name:"LAST_UPDATE_DATE",type:"datetime", defaultValueComputed : "CURRENT_TIMESTAMP")
            column(name:"LAST_UPDATE_LOGIN",type:"bigint", defaultValue : "-1")
            column(name:"PROGRAM_APPLICATION_ID",type:"bigint")
            column(name:"PROGRAM_ID",type:"bigint")
            column(name:"PROGRAM_UPDATE_DATE",type:"datetime")
            column(name:"REQUEST_ID",type:"bigint")
            column(name:"ATTRIBUTE_CATEGORY",type:"varchar(30)")
            column(name:"ATTRIBUTE1",type:"varchar(150)")
            column(name:"ATTRIBUTE2",type:"varchar(150)")
            column(name:"ATTRIBUTE3",type:"varchar(150)")
            column(name:"ATTRIBUTE4",type:"varchar(150)")
            column(name:"ATTRIBUTE5",type:"varchar(150)")
            column(name:"ATTRIBUTE6",type:"varchar(150)")
            column(name:"ATTRIBUTE7",type:"varchar(150)")
            column(name:"ATTRIBUTE8",type:"varchar(150)")
            column(name:"ATTRIBUTE9",type:"varchar(150)")
            column(name:"ATTRIBUTE10",type:"varchar(150)")
            column(name:"ATTRIBUTE11",type:"varchar(150)")
            column(name:"ATTRIBUTE12",type:"varchar(150)")
            column(name:"ATTRIBUTE13",type:"varchar(150)")
            column(name:"ATTRIBUTE14",type:"varchar(150)")
            column(name:"ATTRIBUTE15",type:"varchar(150)")
        }
        addPrimaryKey(columnNames:"CHANNEL,COMPANY_ID", constraintName:"ACC_CHANNEL_PK", tableName:"ACC_CHANNEL")
    }



    changeSet(author:"Admin", id: "2019-05-31_ACC_SETTLE_DEFAULT"){
        if(mhi.isDbType('oracle') || mhi.isDbType('hana')){
            createSequence(sequenceName:'ACC_SETTLE_DEFAULT_S', startValue:"10001")
        }
        createTable(tableName:"ACC_SETTLE_DEFAULT"){
            column(name:"DEFAULT_ID",type:"bigint",autoIncrement:"true",startWith:"10001",remarks:"表ID，主键，供其他表做外键"){
                constraints(nullable:"false",primaryKey: "true",primaryKeyName: "ACC_SETTLE_DEFAULT_PK")
            }
            column(name:"DEFAULT_CODE",type:"varchar(60)",remarks:"默认编号")
            column(name:"COMPANY_ID",type:"bigint",remarks:"公司FND_COMPANY_B.COMPANY_ID")
            column(name:"EMPLOYEE_ID",type:"bigint",remarks:"操作员ID")
            column(name:"ACCOUNT_ID",type:"bigint",remarks:"本方账号ID")
            column(name:"ITEM_CODE",type:"varchar(60)",remarks:"费用项")
            column(name:"CHANNEL",type:"varchar(60)",remarks:"通道")
            column(name:"DIGEST",type:"varchar(200)",remarks:"摘要")
            column(name:"AUX01",type:"varchar(200)",remarks:"辅助核算01")
            column(name:"AUX02",type:"varchar(200)",remarks:"辅助核算02")
            column(name:"AUX03",type:"varchar(200)",remarks:"辅助核算03")
            column(name:"AUX04",type:"varchar(200)",remarks:"辅助核算04")
            column(name:"AUX05",type:"varchar(200)",remarks:"辅助核算05")
            column(name:"ENABLED_FLAG",type:"varchar(1)",defaultValue:"Y",remarks:"启用标识")
            column(name:"OBJECT_VERSION_NUMBER",type:"bigint",defaultValue:"1",remarks:"行版本号，用来处理锁"){
                constraints(nullable:"false")
            }
            column(name:"CREATED_BY",type:"bigint", defaultValue : "-1")
            column(name:"CREATION_DATE",type:"datetime", defaultValueComputed : "CURRENT_TIMESTAMP")
            column(name:"LAST_UPDATED_BY",type:"bigint", defaultValue : "-1")
            column(name:"LAST_UPDATE_DATE",type:"datetime", defaultValueComputed : "CURRENT_TIMESTAMP")
            column(name:"LAST_UPDATE_LOGIN",type:"bigint", defaultValue : "-1")
            column(name:"PROGRAM_APPLICATION_ID",type:"bigint")
            column(name:"PROGRAM_ID",type:"bigint")
            column(name:"PROGRAM_UPDATE_DATE",type:"datetime")
            column(name:"REQUEST_ID",type:"bigint")
            column(name:"ATTRIBUTE_CATEGORY",type:"varchar(30)")
            column(name:"ATTRIBUTE1",type:"varchar(150)")
            column(name:"ATTRIBUTE2",type:"varchar(150)")
            column(name:"ATTRIBUTE3",type:"varchar(150)")
            column(name:"ATTRIBUTE4",type:"varchar(150)")
            column(name:"ATTRIBUTE5",type:"varchar(150)")
            column(name:"ATTRIBUTE6",type:"varchar(150)")
            column(name:"ATTRIBUTE7",type:"varchar(150)")
            column(name:"ATTRIBUTE8",type:"varchar(150)")
            column(name:"ATTRIBUTE9",type:"varchar(150)")
            column(name:"ATTRIBUTE10",type:"varchar(150)")
            column(name:"ATTRIBUTE11",type:"varchar(150)")
            column(name:"ATTRIBUTE12",type:"varchar(150)")
            column(name:"ATTRIBUTE13",type:"varchar(150)")
            column(name:"ATTRIBUTE14",type:"varchar(150)")
            column(name:"ATTRIBUTE15",type:"varchar(150)")
        }
        addUniqueConstraint(columnNames:"COMPANY_ID,DEFAULT_CODE",tableName:"ACC_SETTLE_DEFAULT",constraintName: "ACC_SETTLE_DEFAULT_U1")

    }



    changeSet(author:"Admin", id: "2019-05-31_ACC_CONFIG"){
        if(mhi.isDbType('oracle') || mhi.isDbType('hana')){
            createSequence(sequenceName:'ACC_CONFIG_S', startValue:"10001")
        }
        createTable(tableName:"ACC_CONFIG"){
            column(name:"CONFIG_ID",type:"bigint",autoIncrement:"true",startWith:"10001",remarks:"表ID，主键，供其他表做外键"){
                constraints(nullable:"false",primaryKey: "true",primaryKeyName: "ACC_CONFIG_PK")
            }
            column(name:"COMPANY_ID",type:"bigint",remarks:"公司FND_COMPANY_B.COMPANY_ID")
            column(name:"TRX_CODE",type:"varchar(60)",remarks:"交易代码")
            column(name:"DESCIRPTION",type:"varchar(200)",remarks:"描述")
            column(name:"CHANNEL",type:"varchar(60)",remarks:"通道")
            column(name:"BUSINESS_CODE",type:"varchar(200)",remarks:"业务代码")
            column(name:"ACTION_URL",type:"varchar(200)",remarks:"交易地址")
            column(name:"USEAGE",type:"varchar(200)",remarks:"用途")
            column(name:"REFER01",type:"varchar(200)",remarks:"参考字段01")
            column(name:"REFER02",type:"varchar(200)",remarks:"参考字段02")
            column(name:"REFER03",type:"varchar(200)",remarks:"参考字段03")
            column(name:"ENABLED_FLAG",type:"varchar(1)",remarks:"启用标识")
            column(name:"OBJECT_VERSION_NUMBER",type:"bigint",defaultValue:"1",remarks:"行版本号，用来处理锁"){
                constraints(nullable:"false")
            }
            column(name:"CREATED_BY",type:"bigint", defaultValue : "-1")
            column(name:"CREATION_DATE",type:"datetime", defaultValueComputed : "CURRENT_TIMESTAMP")
            column(name:"LAST_UPDATED_BY",type:"bigint", defaultValue : "-1")
            column(name:"LAST_UPDATE_DATE",type:"datetime", defaultValueComputed : "CURRENT_TIMESTAMP")
            column(name:"LAST_UPDATE_LOGIN",type:"bigint", defaultValue : "-1")
            column(name:"PROGRAM_APPLICATION_ID",type:"bigint")
            column(name:"PROGRAM_ID",type:"bigint")
            column(name:"PROGRAM_UPDATE_DATE",type:"datetime")
            column(name:"REQUEST_ID",type:"bigint")
            column(name:"ATTRIBUTE_CATEGORY",type:"varchar(30)")
            column(name:"ATTRIBUTE1",type:"varchar(150)")
            column(name:"ATTRIBUTE2",type:"varchar(150)")
            column(name:"ATTRIBUTE3",type:"varchar(150)")
            column(name:"ATTRIBUTE4",type:"varchar(150)")
            column(name:"ATTRIBUTE5",type:"varchar(150)")
            column(name:"ATTRIBUTE6",type:"varchar(150)")
            column(name:"ATTRIBUTE7",type:"varchar(150)")
            column(name:"ATTRIBUTE8",type:"varchar(150)")
            column(name:"ATTRIBUTE9",type:"varchar(150)")
            column(name:"ATTRIBUTE10",type:"varchar(150)")
            column(name:"ATTRIBUTE11",type:"varchar(150)")
            column(name:"ATTRIBUTE12",type:"varchar(150)")
            column(name:"ATTRIBUTE13",type:"varchar(150)")
            column(name:"ATTRIBUTE14",type:"varchar(150)")
            column(name:"ATTRIBUTE15",type:"varchar(150)")
        }
        addUniqueConstraint(columnNames:"COMPANY_ID,TRX_CODE",tableName:"ACC_CONFIG",constraintName: "ACC_CONFIG_U1")
    }



    changeSet(author:"Admin", id: "2019-05-31_ACC_ACCOUNT"){
        if(mhi.isDbType('oracle') || mhi.isDbType('hana')){
            createSequence(sequenceName:'ACC_ACCOUNT_S', startValue:"10001")
        }
        createTable(tableName:"ACC_ACCOUNT"){
            column(name:"ACCOUNT_ID",type:"bigint",autoIncrement:"true",startWith:"10001",remarks:"表ID，主键，供其他表做外键"){
                constraints(nullable:"false",primaryKey: "true",primaryKeyName: "ACC_ACCOUNT_PK")
            }
            column(name:"COMPANY_ID",type:"bigint",remarks:"公司FND_COMPANY_B.COMPANY_ID")
            column(name:"ACCOUNT_NUMBER",type:"varchar(60)",remarks:"账号")
            column(name:"ACCOUNT_NAME",type:"varchar(200)",remarks:"账户")
            column(name:"ACCOUNT_TYPE",type:"varchar(60)",remarks:"账户类型")
            column(name:"ACCOUNT_CLASS",type:"varchar(60)",remarks:"账户属性")
            column(name:"ACCOUNT_USAGE",type:"varchar(60)",remarks:"账户用途")
            column(name:"BANK_ID",type:"bigint",remarks:"银行表ID")
            column(name:"OWNER_TYPE",type:"varchar(50)",remarks:"所有者类型"){
                constraints(nullable:"false")
            }
            column(name:"OWNER_ID",type:"bigint",remarks:"所有者ID"){
                constraints(nullable:"false")
            }
            column(name:"ENABLED_FLAG",type:"varchar(1)",defaultValue:"Y",remarks:"启用标识")
            column(name:"OBJECT_VERSION_NUMBER",type:"bigint",defaultValue:"1",remarks:"行版本号，用来处理锁"){
                constraints(nullable:"false")
            }
            column(name:"CREATED_BY",type:"bigint", defaultValue : "-1")
            column(name:"CREATION_DATE",type:"datetime", defaultValueComputed : "CURRENT_TIMESTAMP")
            column(name:"LAST_UPDATED_BY",type:"bigint", defaultValue : "-1")
            column(name:"LAST_UPDATE_DATE",type:"datetime", defaultValueComputed : "CURRENT_TIMESTAMP")
            column(name:"LAST_UPDATE_LOGIN",type:"bigint", defaultValue : "-1")
            column(name:"PROGRAM_APPLICATION_ID",type:"bigint")
            column(name:"PROGRAM_ID",type:"bigint")
            column(name:"PROGRAM_UPDATE_DATE",type:"datetime")
            column(name:"REQUEST_ID",type:"bigint")
            column(name:"ATTRIBUTE_CATEGORY",type:"varchar(30)")
            column(name:"ATTRIBUTE1",type:"varchar(150)")
            column(name:"ATTRIBUTE2",type:"varchar(150)")
            column(name:"ATTRIBUTE3",type:"varchar(150)")
            column(name:"ATTRIBUTE4",type:"varchar(150)")
            column(name:"ATTRIBUTE5",type:"varchar(150)")
            column(name:"ATTRIBUTE6",type:"varchar(150)")
            column(name:"ATTRIBUTE7",type:"varchar(150)")
            column(name:"ATTRIBUTE8",type:"varchar(150)")
            column(name:"ATTRIBUTE9",type:"varchar(150)")
            column(name:"ATTRIBUTE10",type:"varchar(150)")
            column(name:"ATTRIBUTE11",type:"varchar(150)")
            column(name:"ATTRIBUTE12",type:"varchar(150)")
            column(name:"ATTRIBUTE13",type:"varchar(150)")
            column(name:"ATTRIBUTE14",type:"varchar(150)")
            column(name:"ATTRIBUTE15",type:"varchar(150)")
        }
        createIndex(tableName:"ACC_ACCOUNT",indexName:"ACC_ACCOUNT_N1"){
            column(name: "ACCOUNT_NUMBER")
        }
        createIndex(tableName:"ACC_ACCOUNT",indexName:"ACC_ACCOUNT_N2"){
            column(name: "COMPANY_ID")
        }

    }



    changeSet(author:"Admin", id: "2019-05-31_ACC_CHARGE_RULE"){
        if(mhi.isDbType('oracle') || mhi.isDbType('hana')){
            createSequence(sequenceName:'ACC_CHARGE_RULE_S', startValue:"10001")
        }
        createTable(tableName:"ACC_CHARGE_RULE"){
            column(name:"RULE_ID",type:"bigint",autoIncrement:"true",startWith:"10001",remarks:"表ID，主键，供其他表做外键"){
                constraints(nullable:"false",primaryKey: "true",primaryKeyName: "ACC_CHARGE_RULE_PK")
            }
            column(name:"RULE_CODE",type:"varchar(60)",remarks:"规则代码")
            column(name:"COMPANY_ID",type:"bigint",remarks:"公司FND_COMPANY_B.COMPANY_ID")
            column(name:"CHANNEL",type:"varchar(60)",remarks:"通道")
            column(name:"INTERVAL_MIN",type:"decimal",remarks:"分段下限")
            column(name:"INTERVAL_MAX",type:"decimal",remarks:"分段上限")
            column(name:"CHARGE_TYPE",type:"varchar(60)",remarks:"费用类型")
            column(name:"SINGLE_FEE",type:"decimal",remarks:"费用/费率")
            column(name:"MIN_FEE",type:"decimal",remarks:"最低费用")
            column(name:"MAX_FEE",type:"decimal",remarks:"最高费用")
            column(name:"CALC_SEQ",type:"decimal",remarks:"计算顺序")
            column(name:"ENABLED_FLAG",type:"varchar(1)",remarks:"启用标识")
            column(name:"OBJECT_VERSION_NUMBER",type:"bigint",defaultValue:"1",remarks:"行版本号，用来处理锁"){
                constraints(nullable:"false")
            }
            column(name:"CREATED_BY",type:"bigint", defaultValue : "-1")
            column(name:"CREATION_DATE",type:"datetime", defaultValueComputed : "CURRENT_TIMESTAMP")
            column(name:"LAST_UPDATED_BY",type:"bigint", defaultValue : "-1")
            column(name:"LAST_UPDATE_DATE",type:"datetime", defaultValueComputed : "CURRENT_TIMESTAMP")
            column(name:"LAST_UPDATE_LOGIN",type:"bigint", defaultValue : "-1")
            column(name:"PROGRAM_APPLICATION_ID",type:"bigint")
            column(name:"PROGRAM_ID",type:"bigint")
            column(name:"PROGRAM_UPDATE_DATE",type:"datetime")
            column(name:"REQUEST_ID",type:"bigint")
            column(name:"ATTRIBUTE_CATEGORY",type:"varchar(30)")
            column(name:"ATTRIBUTE1",type:"varchar(150)")
            column(name:"ATTRIBUTE2",type:"varchar(150)")
            column(name:"ATTRIBUTE3",type:"varchar(150)")
            column(name:"ATTRIBUTE4",type:"varchar(150)")
            column(name:"ATTRIBUTE5",type:"varchar(150)")
            column(name:"ATTRIBUTE6",type:"varchar(150)")
            column(name:"ATTRIBUTE7",type:"varchar(150)")
            column(name:"ATTRIBUTE8",type:"varchar(150)")
            column(name:"ATTRIBUTE9",type:"varchar(150)")
            column(name:"ATTRIBUTE10",type:"varchar(150)")
            column(name:"ATTRIBUTE11",type:"varchar(150)")
            column(name:"ATTRIBUTE12",type:"varchar(150)")
            column(name:"ATTRIBUTE13",type:"varchar(150)")
            column(name:"ATTRIBUTE14",type:"varchar(150)")
            column(name:"ATTRIBUTE15",type:"varchar(150)")
        }
        addUniqueConstraint(columnNames:"COMPANY_ID,RULE_CODE,CHANNEL",tableName:"ACC_CHARGE_RULE",constraintName: "ACC_CHARGE_RULE_U1")

    }



    changeSet(author:"Admin", id: "2019-05-31_ACC_CHARGE"){
        if(mhi.isDbType('oracle') || mhi.isDbType('hana')){
            createSequence(sequenceName:'ACC_CHARGE_S', startValue:"10001")
        }
        createTable(tableName:"ACC_CHARGE"){
            column(name:"CHARGE_ID",type:"bigint",autoIncrement:"true",startWith:"10001",remarks:"表ID，主键，供其他表做外键"){
                constraints(nullable:"false",primaryKey: "true",primaryKeyName: "ACC_CHARGE_PK")
            }
            column(name:"RULE_ID",type:"bigint",remarks:"规则")
            column(name:"TRANSACTION_ID",type:"bigint",remarks:"结算事务ID")
            column(name:"DOCUMENT_ID",type:"bigint",remarks:"结算单ID")
            column(name:"COMPANY_ID",type:"bigint",remarks:"商户ID")
            column(name:"AMOUNT",type:"decimal",remarks:"金额")
            column(name:"CHARGE_DATE",type:"datetime",remarks:"计费日期")
            column(name:"DIGEST",type:"varchar(200)",remarks:"摘要")
            column(name:"DEAL_SN",type:"varchar(200)",remarks:"交易唯一码")
            column(name:"OBJECT_VERSION_NUMBER",type:"bigint",defaultValue:"1",remarks:"行版本号，用来处理锁"){
                constraints(nullable:"false")
            }
            column(name:"CREATED_BY",type:"bigint", defaultValue : "-1")
            column(name:"CREATION_DATE",type:"datetime", defaultValueComputed : "CURRENT_TIMESTAMP")
            column(name:"LAST_UPDATED_BY",type:"bigint", defaultValue : "-1")
            column(name:"LAST_UPDATE_DATE",type:"datetime", defaultValueComputed : "CURRENT_TIMESTAMP")
            column(name:"LAST_UPDATE_LOGIN",type:"bigint", defaultValue : "-1")
            column(name:"PROGRAM_APPLICATION_ID",type:"bigint")
            column(name:"PROGRAM_ID",type:"bigint")
            column(name:"PROGRAM_UPDATE_DATE",type:"datetime")
            column(name:"REQUEST_ID",type:"bigint")
            column(name:"ATTRIBUTE_CATEGORY",type:"varchar(30)")
            column(name:"ATTRIBUTE1",type:"varchar(150)")
            column(name:"ATTRIBUTE2",type:"varchar(150)")
            column(name:"ATTRIBUTE3",type:"varchar(150)")
            column(name:"ATTRIBUTE4",type:"varchar(150)")
            column(name:"ATTRIBUTE5",type:"varchar(150)")
            column(name:"ATTRIBUTE6",type:"varchar(150)")
            column(name:"ATTRIBUTE7",type:"varchar(150)")
            column(name:"ATTRIBUTE8",type:"varchar(150)")
            column(name:"ATTRIBUTE9",type:"varchar(150)")
            column(name:"ATTRIBUTE10",type:"varchar(150)")
            column(name:"ATTRIBUTE11",type:"varchar(150)")
            column(name:"ATTRIBUTE12",type:"varchar(150)")
            column(name:"ATTRIBUTE13",type:"varchar(150)")
            column(name:"ATTRIBUTE14",type:"varchar(150)")
            column(name:"ATTRIBUTE15",type:"varchar(150)")
        }
        createIndex(tableName:"ACC_CHARGE",indexName:"ACC_CHARGE_N1"){
            column(name: "COMPANY_ID")
        }
        createIndex(tableName:"ACC_CHARGE",indexName:"ACC_CHARGE_N2"){
            column(name: "TRANSACTION_ID")
            column(name: "DOCUMENT_ID")
        }
    }
}
