/*
 * *
 *  @file org.jetbrains.plugins.groovy.copyright.GroovyCopyrightVariablesProvider$collectVariables$1@6d18ada9$
 *  @CopyRight (C) 2018 ZheJiangJingRui Co. Ltd.
 *  @brief JingRui Application Platform
 *  @author $name$
 *  @email yulong.yuan@jr-info.cn
 *  @date $date$
 * /
 */

package com.jingrui.jrap.db

import com.jingrui.jrap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()

databaseChangeLog(logicalFilePath:"2019-03-18-init-table-migration.groovy"){

 changeSet(author:"Admin", id: "2019-05-05_FND_LIMIT_CHANGE"){
  if(mhi.isDbType('oracle') || mhi.isDbType('hana')){
   createSequence(sequenceName:'FND_LIMIT_CHANGE_S', startValue:"10001")
  }
  createTable(tableName:"FND_LIMIT_CHANGE"){
   column(name:"CHANGE_ID",type:"bigint",autoIncrement:"true",startWith:"10001",remarks:"表ID，主键，供其他表做外键"){
    constraints(nullable:"false",primaryKey: "true",primaryKeyName: "FND_LIMIT_CHANGE_PK")
   }
   column(name:"LIMIT_TYPE",type:"varchar(60)",remarks:"授信类型")
   column(name:"COMPANY_ID",type:"bigint",remarks:"公司FND_COMPANY_B.COMPANY_ID")
   column(name:"LIMIT_COMPANY_ID",type:"bigint",remarks:"资方ID")
   column(name:"STATUS",type:"varchar(60)",remarks:"申请状态")
   column(name:"GOOD_ID",type:"bigint",remarks:"商品ID")
   column(name:"ORDER_ID",type:"bigint",remarks:"订单ID")
   column(name:"LIMIT_AMOUNT",type:"decimal",remarks:"授信额度")
   column(name:"SUBMIT_DATE",type:"datetime",remarks:"提交日期")
   column(name:"APPROVED_DATE",type:"datetime",remarks:"审批日期")
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
  createIndex(tableName:"FND_LIMIT_CHANGE",indexName:"FND_LIMIT_CHANGE_N1"){
   column(name: "COMPANY_ID")
   column(name: "LIMIT_COMPANY_ID")
  }
  createIndex(tableName:"FND_LIMIT_CHANGE",indexName:"FND_LIMIT_CHANGE_N2"){
   column(name: "STATUS")
  }

 }



 changeSet(author:"Admin", id: "2019-05-05_FIN_PERIOD_SETS"){
  createTable(tableName:"FIN_PERIOD_SETS"){
   column(name:"SET_CODE",type:"varchar(30)",remarks:"表ID，主键，供其他表做外键"){
    constraints(nullable:"false",primaryKey: "true",primaryKeyName: "FIN_PERIOD_SETS_PK")
   }
   column(name:"COMPANY_ID",type:"bigint",remarks:"公司FND_COMPANY_B.COMPANY_ID")
   column(name:"SET_NAME",type:"varchar(200)",remarks:"期间集名称")
   column(name:"TOTAL_PERIOD",type:"Int",remarks:"期间总数")
   column(name:"ADDITIONAL_FLAG",type:"varchar(50)",remarks:"名称附加")
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
 }

 changeSet(author:"Admin", id: "2019-05-05_FIN_PERIOD_RULES"){
  if(mhi.isDbType('oracle') || mhi.isDbType('hana')){
   createSequence(sequenceName:'FIN_PERIOD_RULES_S', startValue:"10001")
  }
  createTable(tableName:"FIN_PERIOD_RULES"){
   column(name:"SET_CODE",type:"varchar(30)",remarks:"表ID，主键，供其他表做外键"){
    constraints(nullable:"false")
   }
   column(name:"PERIOD_NUM",type:"Int",remarks:"期间"){
    constraints(nullable:"false")
   }
   column(name:"ADDITIONAL_NAME",type:"varchar(30)",remarks:"期间名")
   column(name:"MONTH_FROM",type:"Int",remarks:"月份从")
   column(name:"MONTH_TO",type:"Int(50)",remarks:"月份至")
   column(name:"DATE_FROM",type:"Int(150)",remarks:"日期从")
   column(name:"DATE_TO",type:"Int(1)",remarks:"日期至")
   column(name:"QUARTER_NUM",type:"Int",remarks:"季度")
   column(name:"ADJUSTMENT_FLAG",type:"varchar(1)",remarks:"调整标志")
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
  createIndex(tableName:"FIN_PERIOD_RULES",indexName:"FIN_PERIOD_RULES_N1"){
   column(name: "SET_CODE")
  }
  addPrimaryKey(columnNames:"SET_CODE,PERIOD_NUM", constraintName:"FIN_PERIOD_RULES_PK", tableName:"FIN_PERIOD_RULES")
 }


 changeSet(author:"Admin", id: "2019-05-05_FIN_PERIODS"){
  if(mhi.isDbType('oracle') || mhi.isDbType('hana')){
   createSequence(sequenceName:'FIN_PERIODS_S', startValue:"10001")
  }
  createTable(tableName:"FIN_PERIODS"){
   column(name:"SET_CODE",type:"varchar(30)",remarks:"期间集"){
    constraints(nullable:"false")
   }
   column(name:"COMPANY_ID",type:"bigint",remarks:"公司FND_COMPANY_B.COMPANY_ID")
   column(name:"PERIOD_YEAR",type:"Int",remarks:"年")
   column(name:"PERIOD_NUM",type:"Int",remarks:"序号")
   column(name:"PERIOD_NAME",type:"varchar(60)",remarks:"期间")
   column(name:"ADJUSTMENT_FLAG",type:"varchar(1)",remarks:"调整标志"){
    constraints(nullable:"false")
   }
   column(name:"INTERNAL_PERIOD_NUM",type:"Int",remarks:"内部期间")
   column(name:"START_DATE",type:"datetime",remarks:"日期从")
   column(name:"END_DATE",type:"datetime",remarks:"日期至")
   column(name:"QUARTER_NUM",type:"Int",remarks:"季度")
   column(name:"STATUS",type:"varchar(30)",remarks:"期间状态")
   column(name:"MONTH_FLAG",type:"varchar(1)",remarks:"月结标志")
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
  addPrimaryKey(columnNames:"SET_CODE,INTERNAL_PERIOD_NUM,COMPANY_ID",tableName:"FIN_PERIODS",constraintName: "FIN_PERIODS_PK")
  addUniqueConstraint(columnNames:"SET_CODE,PERIOD_NAME,COMPANY_ID",tableName:"FIN_PERIODS",constraintName: "FIN_PERIODS_U2")
 }
 
 changeSet(author:"Admin", id: "2019-05-13_FND_NOTICE"){
	if(mhi.isDbType('oracle') || mhi.isDbType('hana')){
	 createSequence(sequenceName:'FND_NOTICE_S', startValue:"10001")
	}
	 createTable(tableName:"FND_NOTICE"){
	 column(name:"NOTICE_ID",type:"bigint",autoIncrement:"true",startWith:"10001",remarks:"表ID，主键，供其他表做外键"){
	   constraints(nullable:"false",primaryKey: "true",primaryKeyName: "FND_NOTICE_PK")
	}
	 column(name:"NOTICE_TYPE",type:"varchar(60)",remarks:"公告类型")
	 column(name:"STATUS",type:"varchar(30)",remarks:"公告状态")
	 column(name:"COMPANY_ID",type:"bigint",remarks:"公司FND_COMPANY_B.COMPANY_ID")
	 column(name:"ROLE_ID",type:"bigint",remarks:"角色ID")
	 column(name:"USER_ID",type:"bigint",remarks:"用户ID")
	 column(name:"NOTICE_DATE",type:"datetime",remarks:"公告日期")
	 column(name:"NOTICE_TITLE",type:"varchar(200)",remarks:"公告标题")
	 column(name:"NOTICE_DIGEST",type:"varchar(200)",remarks:"公告摘要")
	 column(name:"CONTENT",type:"clob",remarks:"公告内容")
	 column(name:"START_DATE",type:"datetime",remarks:"有效期从")
	 column(name:"END_DATE",type:"datetime",remarks:"有效期至")
	 column(name:"AUTHOR",type:"bigint",remarks:"发布者ID")
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
	createIndex(tableName:"FND_NOTICE",indexName:"FND_NOTICE_N1"){
	 column(name: "NOTICE_TYPE")
	 column(name: "STATUS")
	}
	createIndex(tableName:"FND_NOTICE",indexName:"FND_NOTICE_N2"){
	 column(name: "COMPANY_ID")
	}

}

 changeSet(author:"Admin", id: "2019-05-05_SYS_CODE_VALUE_V"){
    createView(viewName:"SYS_CODE_VALUE_V", replaceIfExists:"true"){
     "SELECT c.code_id, c.`CODE`, c.DESCRIPTION, v.CODE_VALUE_ID, v.MEANING, v.`VALUE` FROM sys_code_b c, sys_code_value_b v WHERE v.CODE_ID = c.code_id AND c.ENABLED_FLAG = 'Y' AND v.ENABLED_FLAG = 'Y'"
    }
 }

    changeSet(author:"Admin", id: "2019-05-16_AFD_WAREHOUSE"){
        if(mhi.isDbType('oracle') || mhi.isDbType('hana')){
            createSequence(sequenceName:'AFD_WAREHOUSE_S', startValue:"10001")
        }
        createTable(tableName:"AFD_WAREHOUSE", remarks: "仓库表"){
            column(name:"WAREHOUSE_ID",type:"bigint",autoIncrement:"true",startWith:"10001",remarks:"表ID，主键，供其他表做外键"){
                constraints(nullable:"false",primaryKey: "true",primaryKeyName: "AFD_WAREHOUSE_PK")
            }
            column(name:"COMPANY_ID",type:"bigint",remarks:"公司FND_COMPANY_B.COMPANY_ID")
            column(name:"WAREHOUSE_NAME",type:"varchar(200)",remarks:"仓库名称")
            column(name:"WAREHOUSE_LOCATION",type:"varchar(200)",remarks:"仓库地址")
            column(name:"REMARK",type:"varchar(200)",remarks:"备注")
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
        createIndex(tableName:"AFD_WAREHOUSE",indexName:"AFD_WAREHOUSE_N1"){
            column(name: "COMPANY_ID")
        }

    }



    changeSet(author:"Admin", id: "2019-05-16_AFD_PURCHASE"){
        if(mhi.isDbType('oracle') || mhi.isDbType('hana')){
            createSequence(sequenceName:'AFD_PURCHASE_S', startValue:"10001")
        }
        createTable(tableName:"AFD_PURCHASE", remarks: "采购单"){
            column(name:"PURCHASE_ID",type:"bigint",autoIncrement:"true",startWith:"10001",remarks:"表ID，主键，供其他表做外键"){
                constraints(nullable:"false",primaryKey: "true",primaryKeyName: "AFD_PURCHASE_PK")
            }
            column(name:"PURCHASE_CODE",type:"varchar(60)",remarks:"采购单号"){
                constraints(nullable:"false")
            }
            column(name:"PURCHASE_DATE",type:"datetime",remarks:"采购日期")
            column(name:"STATUS",type:"varchar(60)",defaultValue:"NEW",remarks:"状态"){
                constraints(nullable:"false")
            }
            column(name:"COMPANY_ID",type:"bigint",remarks:"公司FND_COMPANY_B.COMPANY_ID")
            column(name:"UNIT_ID",type:"bigint",remarks:"分公司")
            column(name:"EMPLOYEE_ID",type:"bigint",remarks:"采购员")
            column(name:"VENDOR_ID",type:"bigint",remarks:"供应商ID")
            column(name:"TOTAL_AMOUNT",type:"decimal",remarks:"采购总额")
            column(name:"TOTAL_NUMBER",type:"Int",remarks:"采购数量")
            column(name:"REMARK",type:"varchar(200)",remarks:"备注")
            column(name:"SUBMIT_DATE",type:"datetime",remarks:"提交日期")
            column(name:"APPROVED_DATE",type:"datetime",remarks:"审批通过日期")
            column(name:"REFUSE_DATE",type:"datetime",remarks:"审批拒绝日期")
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
        addUniqueConstraint(columnNames:"COMPANY_ID,PURCHASE_CODE",tableName:"AFD_PURCHASE",constraintName: "AFD_PURCHASE_U1")

    }



    changeSet(author:"Admin", id: "2019-05-16_AFD_PURCHASE_DETAIL"){
        if(mhi.isDbType('oracle') || mhi.isDbType('hana')){
            createSequence(sequenceName:'AFD_PURCHASE_DETAIL_S', startValue:"10001")
        }
        createTable(tableName:"AFD_PURCHASE_DETAIL", remarks: "采购明细"){
            column(name:"DETAIL_ID",type:"bigint",autoIncrement:"true",startWith:"10001",remarks:"表ID，主键，供其他表做外键"){
                constraints(nullable:"false",primaryKey: "true",primaryKeyName: "AFD_PURCHASE_DETAIL_PK")
            }
            column(name:"PURCHASE_ID",type:"bigint",remarks:"采购ID")
            column(name:"MODEL_ID",type:"bigint",remarks:"租赁物模型ID")
            column(name:"GUIDE_PRICE",type:"decimal",remarks:"指导价")
            column(name:"INVOICE_PRICE",type:"decimal",remarks:"开票价")
            column(name:"UNIT_PRICE",type:"decimal",remarks:"单价")
            column(name:"ITEM_NUMBER",type:"Int",remarks:"数量")
            column(name:"PURCHASE_COST",type:"decimal",remarks:"购置税")
            column(name:"TRAFFIC_INSURANCE",type:"decimal",remarks:"交强险")
            column(name:"COMMERCIAL_INSURANCE",type:"decimal",remarks:"商业险")
            column(name:"DEED_FAX",type:"decimal",remarks:"契税")
            column(name:"REMARK",type:"varchar(200)",remarks:"备注")
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
        createIndex(tableName:"AFD_PURCHASE_DETAIL",indexName:"AFD_PURCHASE_DETAIL_N1"){
            column(name: "PURCHASE_ID")
        }

    }



    changeSet(author:"Admin", id: "2019-05-16_AFD_PURCHASE_ASSIGN"){
        if(mhi.isDbType('oracle') || mhi.isDbType('hana')){
            createSequence(sequenceName:'AFD_PURCHASE_ASSIGN_S', startValue:"10001")
        }
        createTable(tableName:"AFD_PURCHASE_ASSIGN", remarks: "采购分配"){
            column(name:"ASSIGN_ID",type:"bigint",autoIncrement:"true",startWith:"10001",remarks:"表ID，主键，供其他表做外键"){
                constraints(nullable:"false",primaryKey: "true",primaryKeyName: "AFD_PURCHASE_ASSIGN_PK")
            }
            column(name:"PURCHASE_ID",type:"bigint",remarks:"采购ID")
            column(name:"DETAIL_ID",type:"bigint",remarks:"采购明细ID")
            column(name:"UNIT_ID",type:"bigint",remarks:"分公司ID")
            column(name:"WAREHOUSE_ID",type:"bigint",remarks:"仓库ID")
            column(name:"ASSIGN_NUMBER",type:"Int",remarks:"分配数量")
            column(name:"UNREGISTER_NUM",type:"Int",remarks:"未登记数量")
            column(name:"ASSIGN_DATE",type:"datetime(50)",remarks:"分配日期")
            column(name:"REMARK",type:"varchar(200)",remarks:"备注说明")
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
        createIndex(tableName:"AFD_PURCHASE_ASSIGN",indexName:"AFD_PURCHASE_ASSIGN_N1"){
            column(name: "ASSIGN_ID")
        }

    }



    changeSet(author:"Admin", id: "2019-05-16_AFD_ITEM_ALLOCATE"){
        if(mhi.isDbType('oracle') || mhi.isDbType('hana')){
            createSequence(sequenceName:'AFD_ITEM_ALLOCATE_S', startValue:"10001")
        }
        createTable(tableName:"AFD_ITEM_ALLOCATE", remarks: "调拨单"){
            column(name:"ALLOCATE_ID",type:"bigint",autoIncrement:"true",startWith:"10001",remarks:"表ID，主键，供其他表做外键"){
                constraints(nullable:"false",primaryKey: "true",primaryKeyName: "AFD_ITEM_ALLOCATE_PK")
            }
            column(name:"ALLOCATE_CODE",type:"varchar(60)",remarks:"调拨单")
            column(name:"STATUS",type:"varchar(60)",remarks:"状态")
            column(name:"COMPANY_ID",type:"bigint",remarks:"公司FND_COMPANY_B.COMPANY_ID")
            column(name:"ITEM_ID",type:"bigint",remarks:"租赁物ID")
            column(name:"FROM_WAREHOUSE_ID",type:"bigint",remarks:"出库ID")
            column(name:"FROM_UNIT_ID",type:"bigint",remarks:"出分公司ID")
            column(name:"FROM_EMPLOYEE_ID",type:"bigint",remarks:"出库人员ID")
            column(name:"FROM_DATE",type:"datetime",remarks:"出库日期")
            column(name:"TO_WAREHOUSE_ID",type:"bigint",remarks:"入库ID")
            column(name:"TO_UNIT_ID",type:"bigint",remarks:"入分公司ID")
            column(name:"TO_EMPLOYEE_ID",type:"bigint",remarks:"入库人员ID")
            column(name:"TO_DATE",type:"datetime",remarks:"入库日期")
            column(name:"REMARK",type:"varchar(200)",remarks:"备注说明")
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
        addUniqueConstraint(columnNames:"COMPANY_ID,ALLOCATE_CODE",tableName:"AFD_ITEM_ALLOCATE",constraintName: "AFD_ITEM_ALLOCATE_U1")
        createIndex(tableName:"AFD_ITEM_ALLOCATE",indexName:"AFD_ITEM_ALLOCATE_N1"){
            column(name: "STATUS")
        }

    }


    changeSet(author:"Admin", id: "2019-05-05_AFD_PURCHASE_DETAIL_V"){
        createView(viewName:"AFD_PURCHASE_DETAIL_V", replaceIfExists:"true"){
            "select `a`.`DETAIL_ID` AS `DETAIL_ID`,`a`.`PURCHASE_ID` AS `PURCHASE_ID`,`a`.`MODEL_ID` AS `MODEL_ID`,`a`.`GUIDE_PRICE` AS `GUIDE_PRICE`,`a`.`INVOICE_PRICE` AS `INVOICE_PRICE`,`a`.`UNIT_PRICE` AS `UNIT_PRICE`,`a`.`ITEM_NUMBER` AS `ITEM_NUMBER`,`a`.`PURCHASE_COST` AS `PURCHASE_COST`,`a`.`TRAFFIC_INSURANCE` AS `TRAFFIC_INSURANCE`,`a`.`COMMERCIAL_INSURANCE` AS `COMMERCIAL_INSURANCE`,`a`.`DEED_FAX` AS `DEED_FAX`,`a`.`REMARK` AS `REMARK`,`a`.`OBJECT_VERSION_NUMBER` AS `OBJECT_VERSION_NUMBER`,`a`.`CREATED_BY` AS `CREATED_BY`,`a`.`CREATION_DATE` AS `CREATION_DATE`,`a`.`LAST_UPDATED_BY` AS `LAST_UPDATED_BY`,`a`.`LAST_UPDATE_DATE` AS `LAST_UPDATE_DATE`,`a`.`LAST_UPDATE_LOGIN` AS `LAST_UPDATE_LOGIN`,`a`.`PROGRAM_APPLICATION_ID` AS `PROGRAM_APPLICATION_ID`,`a`.`PROGRAM_ID` AS `PROGRAM_ID`,`a`.`PROGRAM_UPDATE_DATE` AS `PROGRAM_UPDATE_DATE`,`a`.`REQUEST_ID` AS `REQUEST_ID`,`a`.`ATTRIBUTE_CATEGORY` AS `ATTRIBUTE_CATEGORY`,`a`.`ATTRIBUTE1` AS `ATTRIBUTE1`,`a`.`ATTRIBUTE2` AS `ATTRIBUTE2`,`a`.`ATTRIBUTE3` AS `ATTRIBUTE3`,`a`.`ATTRIBUTE4` AS `ATTRIBUTE4`,`a`.`ATTRIBUTE5` AS `ATTRIBUTE5`,`a`.`ATTRIBUTE6` AS `ATTRIBUTE6`,`a`.`ATTRIBUTE7` AS `ATTRIBUTE7`,`a`.`ATTRIBUTE8` AS `ATTRIBUTE8`,`a`.`ATTRIBUTE9` AS `ATTRIBUTE9`,`a`.`ATTRIBUTE10` AS `ATTRIBUTE10`,`a`.`ATTRIBUTE11` AS `ATTRIBUTE11`,`a`.`ATTRIBUTE12` AS `ATTRIBUTE12`,`a`.`ATTRIBUTE13` AS `ATTRIBUTE13`,`a`.`ATTRIBUTE14` AS `ATTRIBUTE14`,`a`.`ATTRIBUTE15` AS `ATTRIBUTE15`,`b`.`MODEL` AS `MODEL`,(`a`.`ITEM_NUMBER` - sum(`c`.`ASSIGN_NUMBER`)) AS `remain_number` from ((`afd_purchase_detail` `a` left join `pro_item_model` `b` on((`b`.`MODEL_ID` = `a`.`MODEL_ID`))) left join `afd_purchase_assign` `c` on((`a`.`DETAIL_ID` = `c`.`DETAIL_ID`))) group by `c`.`DETAIL_ID`"
        }
    }


    changeSet(author:"Admin", id: "2019-05-29_CON_GUARANTOR"){
        if(mhi.isDbType('oracle') || mhi.isDbType('hana')){
            createSequence(sequenceName:'CON_GUARANTOR_S', startValue:"10001")
        }
        createTable(tableName:"CON_GUARANTOR", remarks: "担保人"){
            column(name:"GUARANTOR_ID",type:"bigint",autoIncrement:"true",startWith:"10001",remarks:"表ID，主键，供其他表做外键"){
                constraints(nullable:"false",primaryKey: "true",primaryKeyName: "CON_GUARANTOR_PK")
            }
            column(name:"ORDER_ID",type:"bigint",remarks:"订单ID")
            column(name:"CUSTOMER_ID",type:"bigint",remarks:"客户ID")
            column(name:"GUARANTOR_SEQUENCE",type:"bigint",remarks:"担保序列")
            column(name:"REMARK",type:"varchar(200)",remarks:"备注说明")
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
        createIndex(tableName:"CON_GUARANTOR",indexName:"CON_GUARANTOR_N1"){
            column(name: "GUARANTOR_ID")
        }
        createIndex(tableName:"CON_GUARANTOR",indexName:"CON_GUARANTOR_N2"){
            column(name: "ORDER_ID")
            column(name: "CUSTOMER_ID")
        }

    }

    changeSet(author:"Admin", id: "2019-05-30_SYS_HOMEPAGE"){
        if(mhi.isDbType('oracle') || mhi.isDbType('hana')){
            createSequence(sequenceName:'SYS_HOMEPAGE_S', startValue:"10001")
        }
        createTable(tableName:"SYS_HOMEPAGE"){
            column(name:"HOMEPAGE",type:"varchar(60)",remarks:"表ID，主键，供其他表做外键"){
                constraints(nullable:"false",primaryKey: "true",primaryKeyName: "SYS_HOMEPAGE_PK")
            }
            column(name:"COMPANY_ID",type:"bigint",remarks:"商户ID")
            column(name:"ROLE_ID",type:"bigint",remarks:"角色ID")
            column(name:"USER_ID",type:"bigint",remarks:"用户ID")
            column(name:"HOME_PATH",type:"varchar(200)",remarks:"URL")
            column(name:"REMAKR",type:"varchar(200)",remarks:"备注")
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
    }

}
