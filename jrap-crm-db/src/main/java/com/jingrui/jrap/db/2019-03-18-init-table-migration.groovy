import com.jingrui.jrap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()

databaseChangeLog(logicalFilePath:"2019-03-18-init-table-migration.groovy"){

 changeSet(author:"Admin", id: "2019-03-18_PRO_CASHFLOW_TYPE"){
  if(mhi.isDbType('oracle') || mhi.isDbType('hana')){
   createSequence(sequenceName:'PRO_CASHFLOW_TYPE_S', startValue:"10001")
  }
  createTable(tableName:"PRO_CASHFLOW_TYPE"){
   column(name:"CF_TYPE",type:"varchar(60)",remarks:"主键，供其他表做外键"){
    constraints(nullable:"false",primaryKey: "true",primaryKeyName: "PRO_CASHFLOW_TYPE_PK")
   }
   column(name:"DESCRIPTION",type:"varchar(100)",remarks:"描述")
   column(name:"CF_DIRECTION",type:"varchar(60)",remarks:"现金流方向")
   column(name:"COMPANY_ID",type:"bigint",remarks:"公司FND_COMPANY_B.COMPANY_ID")
   column(name:"SYS_FLAG",type:"varchar(1)",remarks:"系统标识")
   column(name:"WRITE_OFF_ORDER",type:"Int",remarks:"核销顺序")
   column(name:"CALC_PENALTY",type:"varchar(1)",defaultValue:"N",remarks:"计算罚息")
   column(name:"BILLING_DESC",type:"varchar(100)",remarks:"开票描述")
   column(name:"VAT_RATE",type:"decimal",remarks:"税率")
   column(name:"ENABLED_FLAG",type:"varchar(1)",defaultValue:"Y",remarks:"启用标识")
   column(name:"REMARK",type:"clob",remarks:"备注说明")
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
  addUniqueConstraint(columnNames:"CF_TYPE",tableName:"PRO_CASHFLOW_TYPE",constraintName: "PRO_CASHFLOW_TYPE_U1")
  createIndex(tableName:"PRO_CASHFLOW_TYPE",indexName:"PRO_CASHFLOW_TYPE_N1"){
   column(name: "COMPANY_ID")
  }
 }



 changeSet(author:"Admin", id: "2019-03-18_PRO_BUSINESS_TYPE"){
  if(mhi.isDbType('oracle') || mhi.isDbType('hana')){
   createSequence(sequenceName:'PRO_BUSINESS_TYPE_S', startValue:"10001")
  }
  createTable(tableName:"PRO_BUSINESS_TYPE"){
   column(name:"BUSINESS_TYPE",type:"varchar(60)",remarks:"主键，供其他表做外键"){
    constraints(nullable:"false",primaryKey: "true",primaryKeyName: "PRO_BUSINESS_TYPE_PK")
   }
   column(name:"DESCRIPTION",type:"varchar(200)",remarks:"描述")
   column(name:"CATEGORY",type:"varchar(60)",remarks:"种类")
   column(name:"DEAL_TYPE",type:"varchar(60)",remarks:"经营类型")
   column(name:"DEAL_USAGE",type:"varchar(60)",remarks:"经营用途")
   column(name:"CYCLE",type:"varchar(60)",remarks:"周期")
   column(name:"ENABLED_FLAG",type:"varchar(1)",defaultValue:"Y",remarks:"启用标识")
   column(name:"REMARK",type:"clob",remarks:"备注说明")
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
  addUniqueConstraint(columnNames:"BUSINESS_TYPE",tableName:"PRO_BUSINESS_TYPE",constraintName: "PRO_BUSINESS_TYPE_U1")
 }



 changeSet(author:"Admin", id: "2019-03-18_PRO_PRODUCT_LINE"){
  if(mhi.isDbType('oracle') || mhi.isDbType('hana')){
   createSequence(sequenceName:'PRO_PRODUCT_LINE_S', startValue:"10001")
  }
  createTable(tableName:"PRO_PRODUCT_LINE"){
   column(name:"LINE_CODE",type:"varchar(60)",remarks:"主键，供其他表做外键"){
    constraints(nullable:"false",primaryKey: "true",primaryKeyName: "PRO_PRODUCT_LINE_PK")
   }
   column(name:"DESCRIPTION",type:"varchar(200)",remarks:"描述")
   column(name:"COMPANY_ID",type:"bigint",remarks:"公司FND_COMPANY_B.COMPANY_ID")
   column(name:"ENABLED_FLAG",type:"varchar(1)",defaultValue:"Y",remarks:"启用标识")
   column(name:"REMARK",type:"clob",remarks:"备注说明")
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
  addUniqueConstraint(columnNames:"LINE_CODE",tableName:"PRO_PRODUCT_LINE",constraintName: "PRO_PRODUCT_LINE_U1")

 }



 changeSet(author:"Admin", id: "2019-03-18_PRO_PRODUCT"){
  if(mhi.isDbType('oracle') || mhi.isDbType('hana')){
   createSequence(sequenceName:'PRO_PRODUCT_S', startValue:"10001")
  }
  createTable(tableName:"PRO_PRODUCT"){
   column(name:"PRODUCT_CODE",type:"varchar(60)",remarks:"产品编码"){
    constraints(nullable:"false",primaryKey: "true",primaryKeyName: "PRO_PRODUCT_PK")
   }
   column(name:"PRODUCT_NAME",type:"varchar(200)",remarks:"产品名称")
   column(name:"PRODUCT_TYPE",type:"varchar(60)",remarks:"产品类型")
   column(name:"PRODUCT_CLASS",type:"varchar(60)",remarks:"产品属性")
   column(name:"DECRIPTION",type:"varchar(200)",remarks:"产品描述")
   column(name:"BUSINESS_TYPE",type:"varchar(60)",remarks:"业务类型")
   column(name:"LINE_CODE",type:"varchar(60)",remarks:"产品线")
   column(name:"MODEL_ID",type:"bigint",remarks:"租赁物模型ID")
   column(name:"COMPANY_ID",type:"bigint",remarks:"公司FND_COMPANY_B.COMPANY_ID")
   column(name:"CALCULATE",type:"varchar(60)",remarks:"计算器")
   column(name:"RATE_TYPE",type:"varchar(60)",remarks:"利率类型")
   column(name:"VERSION",type:"int", defaultValue:"1", remarks:"版本号")
   column(name:"ENABLED_FLAG",type:"varchar(1)",defaultValue:"Y",remarks:"启用标识")
   column(name:"START_DATE",type:"datetime",remarks:"有效期从")
   column(name:"END_DATE",type:"datetime",remarks:"有效期至")
   column(name:"REMARK",type:"clob",remarks:"备注说明")
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
  addUniqueConstraint(columnNames:"PRODUCT_CODE",tableName:"PRO_PRODUCT",constraintName: "PRO_PRODUCT_U1")
  createIndex(tableName:"PRO_PRODUCT",indexName:"PRO_PRODUCT_N1"){
   column(name: "COMPANY_ID")
  }
  createIndex(tableName:"PRO_PRODUCT",indexName:"PRO_PRODUCT_N2"){
   column(name: "PRODUCT_TYPE")
  }
  createIndex(tableName:"PRO_PRODUCT",indexName:"PRO_PRODUCT_N3"){
   column(name: "PRODUCT_CLASS")
  }
 }



 changeSet(author:"Admin", id: "2019-03-18_PRO_PRODUCT_CONFIG"){
  if(mhi.isDbType('oracle') || mhi.isDbType('hana')){
   createSequence(sequenceName:'PRO_PRODUCT_CONFIG_S', startValue:"10001")
  }
  createTable(tableName:"PRO_PRODUCT_CONFIG"){
   column(name:"CONFIG_ID",type:"bigint",autoIncrement:"true",startWith:"10001",remarks:"表ID，主键，供其他表做外键"){
    constraints(nullable:"false",primaryKey: "true",primaryKeyName: "PRO_PRODUCT_CONFIG_PK")
   }
   column(name:"PRODUCT_CODE",type:"varchar(100)",remarks:"产品表Code")
   column(name:"CONFIG_TYPE",type:"varchar(1)",remarks:"字段类型")
   column(name:"COLUMN_NAME",type:"varchar(100)",remarks:"案件表字段")
   column(name:"COLUMN_CODE",type:"varchar(60)",remarks:"字典编码")
   column(name:"CF_TYPE",type:"varchar(60)",remarks:"现金流类型")
   column(name:"DATA_TYPE",type:"varchar(60)",remarks:"数据类型")
   column(name:"SYSTEM_FLAG",type:"varchar(1)",defaultValue:"N",remarks:"系统标识")
   column(name:"DISPLAY_FLAG",type:"varchar(1)",defaultValue:"Y",remarks:"展示标识")
   column(name:"DISPLAY_ORDER",type:"Int",remarks:"展示顺序")
   column(name:"PROMPT",type:"varchar(100)",remarks:"描述")
   column(name:"SYSCODE",type:"varchar(60)",remarks:"值列表")
   column(name:"REQUIRE_FLAG",type:"varchar(1)",remarks:"必输标识")
   column(name:"READONLY_FLAG",type:"varchar(1)",remarks:"只读标识")
   column(name:"DATA_PRECISION",type:"Int",remarks:"精度")
   column(name:"DEFAULT_VALUE",type:"varchar(100)",remarks:"默认值")
   column(name:"DATA_STATUS",type:"varchar(1)",remarks:"状态")
   column(name:"ENABLED_FLAG",type:"varchar(1)",defaultValue:"Y",remarks:"启用标识")
   column(name:"REMARK",type:"clob",remarks:"备注说明")
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
  createIndex(tableName:"PRO_PRODUCT_CONFIG",indexName:"PRO_PRODUCT_CONFIG_N1"){
   column(name: "PRODUCT_CODE")
  }
  createIndex(tableName:"PRO_PRODUCT_CONFIG",indexName:"PRO_PRODUCT_CONFIG_N2"){
   column(name: "COLUMN_CODE")
  }

 }



 changeSet(author:"Admin", id: "2019-03-18_PRO_PRODUCT_FORMULA"){
  if(mhi.isDbType('oracle') || mhi.isDbType('hana')){
   createSequence(sequenceName:'PRO_PRODUCT_FORMULA_S', startValue:"10001")
  }
  createTable(tableName:"PRO_PRODUCT_FORMULA"){
   column(name:"FORMULA_ID",type:"bigint",autoIncrement:"true",startWith:"10001",remarks:"表ID，主键，供其他表做外键"){
    constraints(nullable:"false",primaryKey: "true",primaryKeyName: "PRO_PRODUCT_FORMULA_PK")
   }
   column(name:"CONFIG_ID",type:"bigint",remarks:"配置ID")
   column(name:"MATRIX_TYPE",type:"varchar(60)",remarks:"行类型(固定/循环)")
   column(name:"MATRIX_INITIAL",type:"varchar(100)",remarks:"行初始值")
   column(name:"MATRIX_INTERVAL",type:"varchar(100)",remarks:"行步长")
   column(name:"CALC_SEQ",type:"Int",remarks:"执行顺序")
   column(name:"CALC_FORMULA",type:"varchar(200)",remarks:"计算公式")
   column(name:"ENABLED_FLAG",type:"varchar(1)",defaultValue:"Y",remarks:"启用标识")
   column(name:"REMARK",type:"clob",remarks:"备注说明")
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
  createIndex(tableName:"PRO_PRODUCT_FORMULA",indexName:"PRO_PRODUCT_FORMULA_N1"){
   column(name: "CONFIG_ID")
  }
 }



 changeSet(author:"Admin", id: "2019-03-18_PRO_ITEM_MODEL"){
  if(mhi.isDbType('oracle') || mhi.isDbType('hana')){
   createSequence(sequenceName:'PRO_ITEM_MODEL_S', startValue:"10001")
  }
  createTable(tableName:"PRO_ITEM_MODEL"){
   column(name:"MODEL_ID",type:"bigint",autoIncrement:"true",startWith:"10001",remarks:"表ID，主键，供其他表做外键"){
    constraints(nullable:"false",primaryKey: "true",primaryKeyName: "PRO_ITEM_MODEL_PK")
   }
   column(name:"COMPANY_ID",type:"bigint",remarks:"公司ID")
   column(name:"BRAND",type:"varchar(200)",remarks:"品牌")
   column(name:"SERIES",type:"varchar(200)",remarks:"系列")
   column(name:"MODEL",type:"varchar(200)",remarks:"型号")
   column(name:"COLOR",type:"varchar(100)",remarks:"颜色")
   column(name:"AREA",type:"varchar(100)",remarks:"面积")
   column(name:"LOCATION",type:"varchar(200)",remarks:"位置")
   column(name:"ENABLED_FLAG",type:"varchar(1)",defaultValue:"Y",remarks:"启用标识")
   column(name:"REMARK",type:"clob",remarks:"备注说明")
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
  createIndex(tableName:"PRO_ITEM_MODEL",indexName:"PRO_ITEM_MODEL_N1"){
   column(name: "COMPANY_ID")
  }
  createIndex(tableName:"PRO_ITEM_MODEL",indexName:"PRO_ITEM_MODEL_N2"){
   column(name: "BRAND")
   column(name: "SERIES")
   column(name: "MODEL")
  }

 }



 changeSet(author:"Admin", id: "2019-03-18_PRO_UNIT_ASSIGN"){
  if(mhi.isDbType('oracle') || mhi.isDbType('hana')){
   createSequence(sequenceName:'PRO_UNIT_ASSIGN_S', startValue:"10001")
  }
  createTable(tableName:"PRO_UNIT_ASSIGN"){
   column(name:"ASSIGN_ID",type:"bigint",autoIncrement:"true",startWith:"10001",remarks:"表ID，主键，供其他表做外键"){
    constraints(nullable:"false",primaryKey: "true",primaryKeyName: "PRO_UNIT_ASSIGN_PK")
   }
   column(name:"UNIT_ID",type:"bigint",remarks:"组织部门HR_ORG_UNIT_B.UNIT_ID")
   column(name:"PRODUCT_CODE",type:"varchar(60)",remarks:"产品Code")
   column(name:"ENABLED_FLAG",type:"varchar(1)",defaultValue:"Y",remarks:"启用标识")
   column(name:"START_DATE",type:"datetime",remarks:"有效期从")
   column(name:"END_DATE",type:"datetime",remarks:"有效期至")
   column(name:"REMARK",type:"clob",remarks:"备注说明")
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
  createIndex(tableName:"PRO_UNIT_ASSIGN",indexName:"PRO_UNIT_ASSIGN_N1"){
   column(name: "UNIT_ID")
   column(name: "PRODUCT_CODE")
  }

 }

 changeSet(author:"Admin", id: "2019-03-18_PRO_DOCUMENT_CATEGORY"){
  if(mhi.isDbType('oracle') || mhi.isDbType('hana')){
   createSequence(sequenceName:'PRO_DOCUMENT_CATEGORY_S', startValue:"10001")
  }
  createTable(tableName:"PRO_DOCUMENT_CATEGORY"){
   column(name:"DOCUMENT_CATEGORY",type:"varchar(60)",remarks:"主键，供其他表做外键"){
    constraints(nullable:"false",primaryKey: "true",primaryKeyName: "PRO_DOCUMENT_CATEGORY_PK")
   }
   column(name:"DESCRIPTION",type:"varchar(200)",remarks:"描述")
   column(name:"ENABLED_FLAG",type:"varchar(1)",defaultValue:"Y",remarks:"启用标识")
   column(name:"REMARK",type:"clob",remarks:"备注说明")
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
  addUniqueConstraint(columnNames:"DOCUMENT_CATEGORY",tableName:"PRO_DOCUMENT_CATEGORY",constraintName: "PRO_DOCUMENT_CATEGORY_U1")
 }

 changeSet(author:"Admin", id: "2019-03-18_PRO_DOCUMENT_TYPE"){
  if(mhi.isDbType('oracle') || mhi.isDbType('hana')){
   createSequence(sequenceName:'PRO_DOCUMENT_TYPE_S', startValue:"10001")
  }
  createTable(tableName:"PRO_DOCUMENT_TYPE"){
   column(name:"DOCUMENT_TYPE",type:"varchar(60)",remarks:"主键，供其他表做外键"){
    constraints(nullable:"false",primaryKey: "true",primaryKeyName: "PRO_DOCUMENT_TYPE_PK")
   }
   column(name:"DESCRIPTION",type:"varchar(200)",remarks:"描述")
   column(name:"DOCUMENT_CATEGORY",type:"varchar(60)",remarks:"单据类别")
   column(name:"BUSINESS_TYPE",type:"varchar(60)",remarks:"业务类型")
   column(name:"CODING_RULE",type:"varchar(60)",remarks:"编码规则")
   column(name:"WORKFLOW_CODE",type:"varchar(60)",remarks:"工作流编码")
   column(name:"ENABLED_FLAG",type:"varchar(1)",defaultValue:"Y",remarks:"启用标识")
   column(name:"REMARK",type:"clob",remarks:"备注说明")
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
  addUniqueConstraint(columnNames:"DOCUMENT_TYPE",tableName:"PRO_DOCUMENT_TYPE",constraintName: "PRO_DOCUMENT_TYPE_U1")
  createIndex(tableName:"PRO_DOCUMENT_TYPE",indexName:"PRO_DOCUMENT_TYPE_N1"){
   column(name: "DOCUMENT_CATEGORY")
  }

 }



 changeSet(author:"Admin", id: "2019-03-18_AFD_CUSTOMER"){
  if(mhi.isDbType('oracle') || mhi.isDbType('hana')){
   createSequence(sequenceName:'AFD_CUSTOMER_S', startValue:"10001")
  }
  createTable(tableName:"AFD_CUSTOMER"){
   column(name:"CUSTOMER_ID",type:"bigint",autoIncrement:"true",startWith:"10001",remarks:"表ID，主键，供其他表做外键"){
    constraints(nullable:"false",primaryKey: "true",primaryKeyName: "AFD_CUSTOMER_PK")
   }
   column(name:"CUSTOMER_CODE",type:"varchar(60)",remarks:"客户编码"){
    constraints(nullable:"false")
   }
   column(name:"CUSTOMER_NAME",type:"varchar(255)",remarks:"客户名称"){
    constraints(nullable:"false")
   }
   column(name:"CUSTOMER_CLASS",type:"varchar(60)",remarks:"分类（自然人、法人）")
   column(name:"CUSTOMER_CATEGORY",type:"varchar(60)",remarks:"类别")
   column(name:"CUSTOMER_TYPE",type:"varchar(60)",remarks:"类型")
   column(name:"CUSTOMER_SOURCE",type:"varchar(60)",remarks:"客户来源")
   column(name:"COMPANY_ID",type:"bigint",remarks:"所属公司ID")
   column(name:"ORGANIZATION_ID",type:"bigint",remarks:"所属机构ID")
   column(name:"EMPLOYEE_ID",type:"bigint",remarks:"所属员工ID")
   column(name:"ID_TYPE",type:"varchar(60)",remarks:"证件类型")
   column(name:"ID_NO",type:"varchar(60)",remarks:"证件号码")
   column(name:"ID_END_DATE",type:"datetime",remarks:"证件有效期至")
   column(name:"SEX",type:"varchar(60)",remarks:"性别")
   column(name:"AGE",type:"Int",remarks:"年龄")
   column(name:"DRIVER_LICENSE",type:"varchar(60)",remarks:"驾驶证档案编号")
   column(name:"LEASE_PURPOSE",type:"varchar(60)",remarks:"租车目的")
   column(name:"TELPHONE",type:"varchar(60)",remarks:"主手机号")
   column(name:"CELLPHONE",type:"varchar(60)",remarks:"座机号")
   column(name:"TELPHONE02",type:"varchar(60)",remarks:"备用手机号2")
   column(name:"TELPHONE03",type:"varchar(60)",remarks:"备用手机号3")
   column(name:"EMAIL",type:"varchar(60)",remarks:"邮箱")
   column(name:"QQ",type:"varchar(60)",remarks:"QQ")
   column(name:"WEIXIN",type:"varchar(60)",remarks:"微信号")
   column(name:"LOCAL_FAMILY_REGISTER",type:"varchar(60)",remarks:"本地户籍")
   column(name:"LOCAL_SOCIAL_SECURITY",type:"varchar(60)",remarks:"当地社保")
   column(name:"LOCAL_HOUSE_PROPERTY",type:"varchar(60)",remarks:"本地房产")
   column(name:"RESIDE_CONDITION",type:"varchar(60)",remarks:"居住状况")
   column(name:"RESIDE_PERIODS",type:"varchar(60)",remarks:"租期")
   column(name:"EDUCATIONAL_BACKGROUND",type:"varchar(60)",remarks:"学历")
   column(name:"MARITAL_STATUS",type:"varchar(60)",remarks:"婚姻状况")
   column(name:"FERTILITY_STATUS",type:"varchar(60)",remarks:"生育状况")
   column(name:"DEBT_FLAG",type:"varchar(1)",remarks:"有无债务")
   column(name:"MORTGAGE",type:"decimal",remarks:"每月还款额")
   column(name:"ANNUAL_INCOME",type:"decimal",remarks:"年收入")
   column(name:"EXPENSE",type:"decimal",remarks:"每月家庭支出")
   column(name:"QUALIFIED_SCORE",type:"decimal",remarks:"客户资质评分")
   column(name:"HOME_ADDRESS",type:"varchar(200)",remarks:"户籍住址")
   column(name:"HOME_ZIP",type:"varchar(60)",remarks:"户籍邮编")
   column(name:"RESIDE_ADDRESS",type:"varchar(200)",remarks:"居住地址")
   column(name:"RESIDE_ZIP",type:"varchar(60)",remarks:"居住邮编")
   column(name:"INDUSTRY",type:"varchar(60)",remarks:"行业")
   column(name:"POSITION",type:"varchar(60)",remarks:"职位")
   column(name:"UNIT_NAME",type:"varchar(200)",remarks:"现职公司全称")
   column(name:"UNIT_ADDRESS",type:"varchar(200)",remarks:"单位地址")
   column(name:"UNIT_ZIP",type:"varchar(60)",remarks:"单位邮编")
   column(name:"CONTACT_ADDRESS",type:"varchar(200)",remarks:"联系地址")
   column(name:"SPOUSE_TYPE",type:"varchar(60)",remarks:"关系")
   column(name:"SPOUSE_NAME",type:"varchar(60)",remarks:"配偶姓名")
   column(name:"SPOUSE_ID_TYPE",type:"varchar(60)",remarks:"配偶身份证件类型")
   column(name:"SPOUSE_ID_NO",type:"varchar(60)",remarks:"配偶身份证件编号")
   column(name:"SPOUSE_TELPHONE",type:"varchar(60)",remarks:"配偶主手机号")
   column(name:"SPOUSE_CELLPHONE",type:"varchar(60)",remarks:"配偶座机")
   column(name:"SPOUSE_DRIVER_LICENSE",type:"varchar(60)",remarks:"配偶驾照")
   column(name:"SPOUSE_HOME_ADDRESS",type:"varchar(200)",remarks:"配偶户籍住址")
   column(name:"SPOUSE_UNIT_ADDRESS",type:"varchar(200)",remarks:"配偶工作单位名称及地址")
   column(name:"COMPOSITE_SCORE",type:"decimal",remarks:"综合评分")
   column(name:"ENABLED_FLAG",type:"varchar(1)",defaultValue:"Y",remarks:"是否启用"){
    constraints(nullable:"false")
   }
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
   column(name:"ATTRIBUTE1",type:"varchar(150)",remarks:"法人身份证")
   column(name:"ATTRIBUTE2",type:"varchar(150)",remarks:"法人姓名")
   column(name:"ATTRIBUTE3",type:"varchar(150)",remarks:"法人手机号")
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
  addUniqueConstraint(columnNames:"CUSTOMER_CODE",tableName:"AFD_CUSTOMER",constraintName: "AFD_CUSTOMER_U1")
  createIndex(tableName:"AFD_CUSTOMER",indexName:"AFD_CUSTOMER_N1"){
   column(name: "CUSTOMER_NAME")
  }
  createIndex(tableName:"AFD_CUSTOMER",indexName:"AFD_CUSTOMER_N2"){
   column(name: "ID_NO")
  }
  createIndex(tableName:"AFD_CUSTOMER",indexName:"AFD_CUSTOMER_N3"){
   column(name: "CUSTOMER_TYPE")
  }
  createIndex(tableName:"AFD_CUSTOMER",indexName:"AFD_CUSTOMER_N4"){
   column(name: "TELPHONE")
  }

 }



 changeSet(author:"Admin", id: "2019-03-18_AFD_CUSTOMER_EVALUATE"){
  if(mhi.isDbType('oracle') || mhi.isDbType('hana')){
   createSequence(sequenceName:'AFD_CUSTOMER_EVALUATE_S', startValue:"10001")
  }
  createTable(tableName:"AFD_CUSTOMER_EVALUATE"){
   column(name:"EVALUATE_ID",type:"bigint",autoIncrement:"true",startWith:"10001",remarks:"表ID，主键，供其他表做外键"){
    constraints(nullable:"false",primaryKey: "true",primaryKeyName: "AFD_CUSTOMER_EVALUATE_PK")
   }
   column(name:"CUSTOMER_ID",type:"bigint",remarks:"客户ID")
   column(name:"EVALUATE_TYPE",type:"varchar(60)",remarks:"评估类型")
   column(name:"EVALUATOR",type:"varchar(60)",remarks:"评估人")
   column(name:"COMPOSITE_SCORE",type:"decimal",remarks:"综合评分")
   column(name:"EVALUATE_DATE",type:"datetime",remarks:"评估时间")
   column(name:"INDATE_DATE",type:"datetime",remarks:"有效日期")
   column(name:"BRIEF",type:"clob",remarks:"评估简要")
   column(name:"REMARK",type:"clob",remarks:"备注说明")
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
  createIndex(tableName:"AFD_CUSTOMER_EVALUATE",indexName:"AFD_CUSTOMER_EVALUATE_N1"){
   column(name: "CUSTOMER_ID")
  }

 }



 changeSet(author:"Admin", id: "2019-03-18_AFD_BANK"){
  if(mhi.isDbType('oracle') || mhi.isDbType('hana')){
   createSequence(sequenceName:'AFD_BANK_S', startValue:"10001")
  }
  createTable(tableName:"AFD_BANK"){
   column(name:"BANK_ID",type:"bigint",autoIncrement:"true",startWith:"10001",remarks:"表ID，主键，供其他表做外键"){
    constraints(nullable:"false",primaryKey: "true",primaryKeyName: "AFD_BANK_PK")
   }
   column(name:"BANK_CODE",type:"varchar(30)",remarks:"银行CODE")
   column(name:"SHORT_NAME",type:"varchar(60)",remarks:"银行简称")
   column(name:"FULL_NAME",type:"varchar(200)",remarks:"银行全称")
   column(name:"PAY_BANK_NUMBER",type:"varchar(50)",remarks:"支付联行号"){
    constraints(nullable:"false")
   }
   column(name:"PROVINCE",type:"varchar(150)",remarks:"省份")
   column(name:"CITY",type:"varchar(150)",remarks:"城市")
   column(name:"REMARK",type:"clob",remarks:"备注说明")
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
  addUniqueConstraint(columnNames:"PAY_BANK_NUMBER",tableName:"AFD_BANK",constraintName: "AFD_BANK_U1")
  createIndex(tableName:"AFD_BANK",indexName:"AFD_BANK_N1"){
   column(name: "SHORT_NAME")
  }
  createIndex(tableName:"AFD_BANK",indexName:"AFD_BANK_N2"){
   column(name: "FULL_NAME")
  }

 }



 changeSet(author:"Admin", id: "2019-03-18_AFD_ACCOUNT"){
  if(mhi.isDbType('oracle') || mhi.isDbType('hana')){
   createSequence(sequenceName:'AFD_ACCOUNT_S', startValue:"10001")
  }
  createTable(tableName:"AFD_ACCOUNT"){
   column(name:"ACCOUNT_ID",type:"bigint",autoIncrement:"true",startWith:"10001",remarks:"表ID，主键，供其他表做外键"){
    constraints(nullable:"false",primaryKey: "true",primaryKeyName: "AFD_ACCOUNT_PK")
   }
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
   column(name:"REMARK",type:"clob",remarks:"备注说明")
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
  addUniqueConstraint(columnNames:"ACCOUNT_NUMBER",tableName:"AFD_ACCOUNT",constraintName: "AFD_ACCOUNT_U1")
  createIndex(tableName:"AFD_ACCOUNT",indexName:"AFD_ACCOUNT_N1"){
   column(name: "ACCOUNT_NAME")
  }
  createIndex(tableName:"AFD_ACCOUNT",indexName:"AFD_ACCOUNT_N2"){
   column(name: "OWNER_TYPE")
   column(name: "OWNER_ID")
  }
  createIndex(tableName:"AFD_ACCOUNT",indexName:"AFD_ACCOUNT_N3"){
   column(name: "ENABLED_FLAG")
  }
  createIndex(tableName:"AFD_ACCOUNT",indexName:"AFD_ACCOUNT_N4"){
   column(name: "BANK_ID")
  }
 }



 changeSet(author:"Admin", id: "2019-03-18_AFD_ITEM"){
  if(mhi.isDbType('oracle') || mhi.isDbType('hana')){
   createSequence(sequenceName:'AFD_ITEM_S', startValue:"10001")
  }
  createTable(tableName:"AFD_ITEM"){
   column(name:"ITEM_ID",type:"bigint",autoIncrement:"true",startWith:"10001",remarks:"表ID，主键，供其他表做外键"){
    constraints(nullable:"false",primaryKey: "true",primaryKeyName: "AFD_ITEM_PK")
   }
   column(name:"COMPANY_ID",type:"bigint",remarks:"所属公司ID"){
    constraints(nullable:"false")
   }
   column(name:"ITEM_CODE",type:"varchar(200)",remarks:"租赁物编码"){
    constraints(nullable:"false")
   }
   
   column(name:"STATUS",type:"varchar(60)",remarks:"状态")
   
   column(name:"DOCUMENT_CATEGORY",type:"varchar(60)",remarks:"单据类别"){
    constraints(nullable:"false")
   }
   column(name:"DOCUMENT_TYPE",type:"varchar(60)",remarks:"单据类型"){
    constraints(nullable:"false")
   }
   column(name:"LINE_CODE",type:"bigint",remarks:"产品线"){
    constraints(nullable:"false")
   }
   column(name:"PURCHASE_ID",type:"bigint",remarks:"采购单ID")
   column(name:"ASSIGN_ID",type:"bigint",remarks:"采购分配ID")
   column(name:"MODEL_ID",type:"bigint",remarks:"模型ID")
   column(name:"MODEL",type:"varchar(200)",remarks:"型号")
   column(name:"OUTSIDE_COLOR",type:"varchar(60)",remarks:"车体颜色")
   column(name:"INSIDE_COLOR",type:"varchar(60)",remarks:"车内颜色")
   column(name:"VIN",type:"varchar(30)",remarks:"车架号")
   column(name:"ENGINE_NUMBER",type:"varchar(30)",remarks:"发动机号")
   column(name:"LICENSE",type:"varchar(50)",remarks:"车牌号")
   column(name:"MILEAGE",type:"varchar(30)",remarks:"里程数")
   column(name:"CAR_KEY",type:"varchar(30)",remarks:"车辆钥匙")
   column(name:"GUIDE_PRICE",type:"decimal",remarks:"指导价")
   column(name:"INVOICE_PRICE",type:"decimal",remarks:"发票价格")
   column(name:"CAR_TYPE",type:"varchar(30)",remarks:"车辆抵押状况")
   column(name:"LOAN_TYPE",type:"varchar(30)",remarks:"借款情况")
   column(name:"LOAN_REMARK",type:"varchar(200)",remarks:"抵押详情")
   column(name:"RELEASE_DATE",type:"datetime",remarks:"出厂日期")
   column(name:"ANNUAL_DATE",type:"datetime",remarks:"年检日期")
   column(name:"INSURANCE",type:"datetime",remarks:"保险日期")
   column(name:"DRIVING_DATE",type:"datetime",remarks:"行驶证登记日")
   column(name:"ENABLED_FLAG",type:"varchar(1)",defaultValue:"Y",remarks:"启用标识")
   column(name:"REMARK",type:"clob",remarks:"备注说明")
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
  createIndex(tableName:"AFD_ITEM",indexName:"AFD_ITEM_N1"){
   column(name: "COMPANY_ID")
   column(name: "ITEM_CODE")
   column(name: "DOCUMENT_CATEGORY")
   column(name: "DOCUMENT_TYPE")
  }
  createIndex(tableName:"AFD_ITEM",indexName:"AFD_ITEM_N2"){
   column(name: "LINE_CODE")
  }
  createIndex(tableName:"AFD_ITEM",indexName:"AFD_ITEM_N3"){
   column(name: "VIN")
  }
  createIndex(tableName:"AFD_ITEM",indexName:"AFD_ITEM_N4"){
   column(name: "LICENSE")
  }

 }



 changeSet(author:"Admin", id: "2019-03-18_AFD_ITEM_EVALUATE"){
  if(mhi.isDbType('oracle') || mhi.isDbType('hana')){
   createSequence(sequenceName:'AFD_ITEM_EVALUATE_S', startValue:"10001")
  }
  createTable(tableName:"AFD_ITEM_EVALUATE"){
   column(name:"EVALUATE_ID",type:"bigint",autoIncrement:"true",startWith:"10001",remarks:"表ID，主键，供其他表做外键"){
    constraints(nullable:"false",primaryKey: "true",primaryKeyName: "AFD_ITEM_EVALUATE_PK")
   }
   column(name:"ITEM_ID",type:"bigint",remarks:"抵押物ID")
   column(name:"EVALUATE_TYPE",type:"varchar(60)",remarks:"评估类型")
   column(name:"EVALUATION_DATE",type:"datetime",remarks:"评估时间")
   column(name:"EVALUATOR",type:"varchar(200)",remarks:"评估人")
   column(name:"OUTSIDE_SCORE",type:"decimal",remarks:"车体外部评分")
   column(name:"INSIDE_SCORE",type:"decimal",remarks:"车体内部评分")
   column(name:"SKELETON_SCORE",type:"decimal",remarks:"车骨架评分")
   column(name:"CONFIG_SCORE",type:"decimal",remarks:"车配置评分")
   column(name:"COMPOSITE_SCORE",type:"decimal",remarks:"综合评分")
   column(name:"EVALUATE_PRICE",type:"decimal",remarks:"评估价")
   column(name:"TRADE_PRICE",type:"decimal",remarks:"同行交易价")
   column(name:"REMARK",type:"clob",remarks:"备注说明")
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
  createIndex(tableName:"AFD_ITEM_EVALUATE",indexName:"AFD_ITEM_EVALUATE_N1"){
   column(name: "ITEM_ID")
  }
  createIndex(tableName:"AFD_ITEM_EVALUATE",indexName:"AFD_ITEM_EVALUATE_N2"){
   column(name: "EVALUATE_TYPE")
  }

 }



 changeSet(author:"Admin", id: "2019-03-18_AFD_ITEM_STOCK"){
  if(mhi.isDbType('oracle') || mhi.isDbType('hana')){
   createSequence(sequenceName:'AFD_ITEM_STOCK_S', startValue:"10001")
  }
  createTable(tableName:"AFD_ITEM_STOCK"){
   column(name:"STOCK_ID",type:"bigint",autoIncrement:"true",startWith:"10001",remarks:"表ID，主键，供其他表做外键"){
    constraints(nullable:"false",primaryKey: "true",primaryKeyName: "AFD_ITEM_STOCK_PK")
   }
   column(name:"ITEM_ID",type:"bigint",remarks:"抵押物ID")
   column(name:"STOCK_TYPE",type:"varchar(60)",remarks:"出入库类型")
   column(name:"STATUS",type:"varchar(60)",remarks:"状态")
   column(name:"STOCK_DATE",type:"datetime",remarks:"出入库日期")
   column(name:"UNIT_ID",type:"bigint",remarks:"部门ID")
   column(name:"WAREHOUSE_ID",type:"bigint",remarks:"仓库ID")
   column(name:"STOCKMAN",type:"varchar(200)",remarks:"仓库人员")
   column(name:"EMPLOYEE_ID",type:"bigint",remarks:"员工ID")
   column(name:"REMARK",type:"clob",remarks:"备注说明")
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
  createIndex(tableName:"AFD_ITEM_STOCK",indexName:"AFD_ITEM_STOCK_N1"){
   column(name: "ITEM_ID")
  }

 }



 changeSet(author:"Admin", id: "2019-03-18_AFD_GOOD"){
  if(mhi.isDbType('oracle') || mhi.isDbType('hana')){
   createSequence(sequenceName:'AFD_GOOD_S', startValue:"10001")
  }
  createTable(tableName:"AFD_GOOD"){
   column(name:"GOOD_ID",type:"bigint",autoIncrement:"true",startWith:"10001",remarks:"表ID，主键，供其他表做外键"){
    constraints(nullable:"false",primaryKey: "true",primaryKeyName: "AFD_GOOD_PK")
   }
   column(name:"GOOD_NAME",type:"varchar(200)",remarks:"商品名称")
   column(name:"COMPANY_ID",type:"bigint",remarks:"公司FND_COMPANY_B.COMPANY_ID")
   column(name:"DOCUMENT_CATEGORY",type:"varchar(60)",remarks:"单据类别"){
    constraints(nullable:"false")
   }
   column(name:"DOCUMENT_TYPE",type:"varchar(60)",remarks:"单据类型"){
    constraints(nullable:"false")
   }
   column(name:"PRODUCT_CODE",type:"varchar(60)",remarks:"产品编码")
   column(name:"DESCRIPTION",type:"clob",remarks:"商品描述")
   column(name:"UNIT_PRICE",type:"decimal",remarks:"商品单价")
   column(name:"TOTAL_NUMBER",type:"Int",remarks:"商品数量")
   column(name:"TOTAL_AMOUNT",type:"decimal",remarks:"商品总额度")
   column(name:"SURPLUS_AMOUNT",type:"decimal",remarks:"剩余额度")
   column(name:"STATUS", type:"varchar(60)",remarks:"状态")
   column(name:"ENABLED_FLAG",type:"varchar(1)",defaultValue:"Y",remarks:"启用标识")
   column(name:"START_DATE",type:"datetime",remarks:"有效期从")
   column(name:"END_DATE",type:"datetime",remarks:"有效期至")
   column(name:"REMARK",type:"clob",remarks:"备注说明")
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
  createIndex(tableName:"AFD_GOOD",indexName:"AFD_GOOD_N1"){
   column(name: "PRODUCT_CODE")
   column(name: "DOCUMENT_CATEGORY")
   column(name: "DOCUMENT_TYPE")
  }
  createIndex(tableName:"AFD_GOOD",indexName:"AFD_GOOD_N2"){
   column(name: "COMPANY_ID")
   column(name: "ENABLED_FLAG")
  }
  createIndex(tableName:"AFD_GOOD",indexName:"AFD_GOOD_N3"){
   column(name: "COMPANY_ID")
   column(name: "START_DATE")
   column(name: "END_DATE")
  }
  createIndex(tableName:"AFD_GOOD",indexName:"AFD_GOOD_N4"){
   column(name: "STATUS")
  }
 }



 changeSet(author:"Admin", id: "2019-03-18_AFD_GOOD_CONDITION"){
  if(mhi.isDbType('oracle') || mhi.isDbType('hana')){
   createSequence(sequenceName:'AFD_GOOD_CONDITION_S', startValue:"10001")
  }
  createTable(tableName:"AFD_GOOD_CONDITION"){
   column(name:"CONDITION_ID",type:"bigint",autoIncrement:"true",startWith:"10001",remarks:"表ID，主键，供其他表做外键"){
    constraints(nullable:"false",primaryKey: "true",primaryKeyName: "AFD_GOOD_CONDITION_PK")
   }
   column(name:"GOOD_ID",type:"bigint",remarks:"商品表ID")
   column(name:"CONDITION_TYPE",type:"varchar(60)",remarks:"条件类型")
   column(name:"CALC_TYPE",type:"varchar(60)",remarks:"运算符")
   column(name:"MIN_VALUE",type:"decimal",remarks:"最小值")
   column(name:"MAX_VALUE",type:"decimal",remarks:"最大值")
   column(name:"DESCRIPTION",type:"varchar(200)",remarks:"备注")
   column(name:"CALC_SEQ",type:"Int",remarks:"顺序")
   column(name:"ENABLED_FLAG",type:"varchar(1)",defaultValue:"Y",remarks:"启用标识")
   column(name:"REMARK",type:"clob",remarks:"备注说明")
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
  createIndex(tableName:"AFD_GOOD_CONDITION",indexName:"AFD_GOOD_CONDITION_N1"){
   column(name: "GOOD_ID")
  }

 }


 changeSet(author:"Admin", id: "2019-04-22_CON_CHANGE"){
  if(mhi.isDbType('oracle') || mhi.isDbType('hana')){
   createSequence(sequenceName:'CON_CHANGE_S', startValue:"10001")
  }
  createTable(tableName:"CON_CHANGE"){
   column(name:"CHANGE_ID",type:"bigint",autoIncrement:"true",startWith:"10001",remarks:"表ID，主键，供其他表做外键"){
    constraints(nullable:"false",primaryKey: "true",primaryKeyName: "CON_CHANGE_PK")
   }
   column(name:"CHANGE_CODE",type:"varchar(60)",remarks:"销售机会编号"){
    constraints(nullable:"false")
   }
   column(name:"DOCUMENT_CATEGORY",type:"varchar(60)",remarks:"单据类别"){
    constraints(nullable:"false")
   }
   column(name:"DOCUMENT_TYPE",type:"varchar(60)",remarks:"单据类型"){
    constraints(nullable:"false")
   }
   column(name:"GOOD_ID",type:"bigint",remarks:"商品ID")
   column(name:"PRODUCT_CODE",type:"varchar(60)",remarks:"产品编码")
   column(name:"INDICATIVE_AMOUNT",type:"decimal",remarks:"意向价格")
   column(name:"STATUS",type:"varchar(60)",defaultValue:"NEW",remarks:"状态")
   column(name:"COMPANY_ID",type:"bigint",remarks:"公司ID")
   column(name:"UNIT_ID",type:"bigint",remarks:"机构ID")
   column(name:"EMPLOYEE_ID",type:"bigint",remarks:"业务员ID")
   column(name:"SUBMIT_DATE",type:"datetime",remarks:"申请日期")
   column(name:"APPROVED_DATE",type:"datetime",remarks:"通过日期")
   column(name:"REFUSE_DATE",type:"datetime",remarks:"拒绝日期")
   column(name:"CUSTOMER_ID",type:"bigint",remarks:"客户ID")
   column(name:"MODEL_ID",type:"bigint",remarks:"新车ID")
   column(name:"CUSTOMER_NAME",type:"varchar(200)",remarks:"客户姓名")
   column(name:"CELLPHONE",type:"varchar(30)",remarks:"手机号")
   column(name:"CELLPHONE02",type:"varchar(30)",remarks:"手机号02")
   column(name:"CELLPHONE03",type:"varchar(30)",remarks:"手机号03")
   column(name:"ID_TYPE",type:"varchar(60)",defaultValue:"ID",remarks:"证件类型")
   column(name:"ID_NUMBER",type:"varchar(60)",remarks:"证件号")
   column(name:"CAR_LICENSE",type:"varchar(60)",remarks:"车牌号")
   column(name:"CAR_MODEL",type:"varchar(200)",remarks:"车型")
   column(name:"REMARK",type:"clob",remarks:"备注说明")
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
  addUniqueConstraint(columnNames:"COMPANY_ID,CHANGE_CODE",tableName:"CON_CHANGE",constraintName: "CON_CHANGE_U1")
  createIndex(tableName:"CON_CHANGE",indexName:"CON_CHANGE_N1"){
   column(name: "GOOD_ID")
  }
  createIndex(tableName:"CON_CHANGE",indexName:"CON_CHANGE_N2"){
   column(name: "PRODUCT_CODE")
  }
  createIndex(tableName:"CON_CHANGE",indexName:"CON_CHANGE_N3"){
   column(name: "EMPLOYEE_ID")
  }
  createIndex(tableName:"CON_CHANGE",indexName:"CON_CHANGE_N4"){
   column(name: "CUSTOMER_NAME")
  }
  createIndex(tableName:"CON_CHANGE",indexName:"CON_CHANGE_N5"){
   column(name: "CAR_LICENSE")
  }
  createIndex(tableName:"CON_CHANGE",indexName:"CON_CHANGE_N6"){
   column(name: "CELLPHONE")
  }
  createIndex(tableName:"CON_CHANGE",indexName:"CON_CHANGE_N7"){
   column(name: "DOCUMENT_CATEGORY")
   column(name: "DOCUMENT_TYPE")
   column(name: "COMPANY_ID")
  }
 }


 changeSet(author:"Admin", id: "2019-04-22_CON_CONTACT"){
  if(mhi.isDbType('oracle') || mhi.isDbType('hana')){
   createSequence(sequenceName:'CON_CONTACT_S', startValue:"10001")
  }
  createTable(tableName:"CON_CONTACT"){
   column(name:"CONTACT_ID",type:"bigint",autoIncrement:"true",startWith:"10001",remarks:"表ID，主键，供其他表做外键"){
    constraints(nullable:"false",primaryKey: "true",primaryKeyName: "CON_CONTACT_PK")
   }
   column(name:"COMPANY_ID",type:"bigint",remarks:"公司FND_COMPANY_B.COMPANY_ID")
   column(name:"STATUS",type:"varchar(60)",remarks:"状态")
   column(name:"GOOD_ID",type:"bigint",remarks:"商品ID")
   column(name:"CUSTOMER_NAME",type:"varchar(200)",remarks:"客户姓名")
   column(name:"CELLPHONE",type:"varchar(30)",remarks:"手机号")
   column(name:"CELLPHONE02",type:"varchar(30)",remarks:"手机号02")
   column(name:"CELLPHONE03",type:"varchar(30)",remarks:"手机号03")
   column(name:"ID_TYPE",type:"varchar(60)",defaultValue:"ID",remarks:"证件类型")
   column(name:"ID_NUMBER",type:"varchar(60)",remarks:"证件号")
   column(name:"SOURCE_TYPE",type:"varchar(60)",remarks:"来源")
   column(name:"ENABLED_FLAG",type:"varchar(1)",defaultValue:"Y",remarks:"启用标识")
   column(name:"REMARK",type:"clob",remarks:"备注说明")
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
  createIndex(tableName:"CON_CONTACT",indexName:"CON_CONTACT_N1"){
   column(name: "COMPANY_ID")
   column(name: "GOOD_ID")
  }
  createIndex(tableName:"CON_CONTACT",indexName:"CON_CONTACT_N2"){
   column(name: "COMPANY_ID")
   column(name: "CELLPHONE")
  }
 }


 changeSet(author:"Admin", id: "2019-04-22_CON_CHANGE_ASSIGN"){
  if(mhi.isDbType('oracle') || mhi.isDbType('hana')){
   createSequence(sequenceName:'CON_CHANGE_ASSIGN_S', startValue:"10001")
  }
  createTable(tableName:"CON_CHANGE_ASSIGN"){
   column(name:"ASSIGN_ID",type:"bigint",autoIncrement:"true",startWith:"10001",remarks:"表ID，主键，供其他表做外键"){
    constraints(nullable:"false",primaryKey: "true",primaryKeyName: "CON_CHANGE_ASSIGN_PK")
   }
   column(name:"CONTACT_ID",type:"bigint",remarks:"联系人ID")
   column(name:"CHANGE_ID",type:"bigint",remarks:"商机ID")
   column(name:"UNIT_ID",type:"bigint",remarks:"组织部门HR_ORG_UNIT_B.UNIT_ID")
   column(name:"EMPLOYEE_ID",type:"bigint",remarks:"员工HR_EMPLOYEE T.EMPLOYEE_ID")
   column(name:"ASSIGN_DATE",type:"datetime",remarks:"分配时间")
   column(name:"INVALID_DATE",type:"datetime",remarks:"失效时间")
   column(name:"ASSIGN_NOTE",type:"varchar(200)",remarks:"备注")
   column(name:"ENABLED_FLAG",type:"varchar(1)",defaultValue:"Y",remarks:"启用标识")
   column(name:"REMARK",type:"clob",remarks:"备注说明")
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
  createIndex(tableName:"CON_CHANGE_ASSIGN",indexName:"CON_CHANGE_ASSIGN_N1"){
   column(name: "CHANGE_ID")
  }
  createIndex(tableName:"CON_CHANGE_ASSIGN",indexName:"CON_CHANGE_ASSIGN_N2"){
   column(name: "UNIT_ID")
  }
  createIndex(tableName:"CON_CHANGE_ASSIGN",indexName:"CON_CHANGE_ASSIGN_N3"){
   column(name: "EMPLOYEE_ID")
  }
 }


 changeSet(author:"Admin", id: "2019-04-22_CON_CHANGE_TRACK"){
  if(mhi.isDbType('oracle') || mhi.isDbType('hana')){
   createSequence(sequenceName:'CON_CHANGE_TRACK_S', startValue:"10001")
  }
  createTable(tableName:"CON_CHANGE_TRACK"){
   column(name:"TRACK_ID",type:"bigint",autoIncrement:"true",startWith:"10001",remarks:"表ID，主键，供其他表做外键"){
    constraints(nullable:"false",primaryKey: "true",primaryKeyName: "CON_CHANGE_TRACK_PK")
   }
   column(name:"CHANGE_ID",type:"bigint",remarks:"商机ID")
   column(name:"TRACK_DATE",type:"datetime",remarks:"跟踪日期")
   column(name:"TRACK_TYPE",type:"varchar(60)",remarks:"跟踪方式")
   column(name:"TRACK_NOTE",type:"varchar(200)",remarks:"跟踪日志")
   column(name:"REMARK",type:"clob",remarks:"备注说明")
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
  createIndex(tableName:"CON_CHANGE_TRACK",indexName:"CON_CHANGE_TRACK_N1"){
   column(name: "CHANGE_ID")
  }
 }



 changeSet(author:"Admin", id: "2019-03-18_CON_ORDER"){
  if(mhi.isDbType('oracle') || mhi.isDbType('hana')){
   createSequence(sequenceName:'CON_ORDER_S', startValue:"10001")
  }
  createTable(tableName:"CON_ORDER"){
   column(name:"ORDER_ID",type:"bigint",autoIncrement:"true",startWith:"10001",remarks:"表ID，主键，供其他表做外键"){
    constraints(nullable:"false",primaryKey: "true",primaryKeyName: "CON_ORDER_PK")
   }
   column(name:"ORDER_CODE",type:"varchar(60)",remarks:"订单编号"){
    constraints(nullable:"false")
   }
   column(name:"DOCUMENT_CATEGORY",type:"varchar(60)",remarks:"单据类别"){
    constraints(nullable:"false")
   }
   column(name:"DOCUMENT_TYPE",type:"varchar(60)",remarks:"单据类型"){
    constraints(nullable:"false")
   }
   column(name:"ORDER_STATUS",type:"varchar(60)",defaultValue:"NEW",remarks:"订单状态"){
    constraints(nullable:"false")
   }
   column(name:"PRODUCT_CODE",type:"varchar(60)",remarks:"产品编号")
   column(name:"BUSINESS_TYPE",type:"varchar(60)",remarks:"业务类型")
   column(name:"CHANGE_ID",type:"bigint",remarks:"商机ID")
   column(name:"CUSTOMER_ID",type:"bigint",remarks:"客户ID")
   column(name:"ITEM_ID",type:"bigint",remarks:"租赁物ID")
   column(name:"AGENT_ID",type:"bigint",remarks:"供应商ID")
   column(name:"COMPANY_ID",type:"bigint",remarks:"公司ID")
   column(name:"UNIT_ID",type:"bigint",remarks:"机构ID")
   column(name:"EMPLOYEE_ID",type:"bigint",remarks:"业务员ID")
   column(name:"RISK_EMPLOYEE_ID",type:"bigint",remarks:"风控员ID")
   column(name:"GUIDE_PRICE",type:"decimal",remarks:"建议价")
   column(name:"EVALUATION_AMOUNT",type:"decimal",remarks:"评估价")
   column(name:"LEASE_AMOUNT",type:"decimal",remarks:"租赁物价款")
   column(name:"FINANCE_AMOUNT",type:"decimal",remarks:"融资金额")
   column(name:"CONTRACT_AMOUNT",type:"decimal",remarks:"合同总额（首付+各项费用+租金+尾款+留购价款)")
   column(name:"NET_FINANCE_AMOUNT",type:"decimal",remarks:"不含税融资额")
   column(name:"VAT_FINANCE_AMOUNT",type:"decimal",remarks:"融资额增值税")
   column(name:"DOWN_PAYMENT",type:"decimal",remarks:"首付款")
   column(name:"NET_DOWN_PAYMENT",type:"decimal",remarks:"不含税首付金额")
   column(name:"VAT_DOWN_PAYMENT",type:"decimal",remarks:"首付款税额")
   column(name:"FINAL_PAYMENT",type:"decimal",remarks:"尾款")
   column(name:"RESIDUAL_VALUE",type:"decimal",remarks:"留购价款")
   column(name:"VAT_INPUT",type:"decimal",remarks:"进项增值税额")
   column(name:"LEASE_TIMES",type:"Int",remarks:"租赁期数")
   column(name:"PAY_TYPE",type:"varchar(1)",defaultValue:"0",remarks:"支付类型（1/先付 0/后付）")
   column(name:"PAY_TIMES",type:"Int",remarks:"支付期数")
   column(name:"ANNUAL_PAY_TIMES",type:"Int",remarks:"支付频率（年）")
   column(name:"LEASE_TERM",type:"Int",remarks:"租赁期限")
   column(name:"BASE_RATE",type:"decimal",remarks:"基准利率")
   column(name:"INT_RATE",type:"decimal",remarks:"租赁利率")
   column(name:"INT_RATE_IMPLICIT",type:"decimal",remarks:"租赁利率（实际）")
   column(name:"INT_RATE_REAL",type:"decimal",remarks:"税后实际利率")
   column(name:"INT_RATE_TYPE",type:"varchar(60)",defaultValue:"FIXED",remarks:"利率类型：浮动/FLOATING 固定/FIXED")
   column(name:"PLATE_PRICE",type:"decimal",remarks:"牌照费")
   column(name:"INSURANCE_AMOUNT",type:"decimal",remarks:"保险费")
   column(name:"MORTGAGE_FEE",type:"decimal",remarks:"抵押费")
   column(name:"CHARGE",type:"decimal",remarks:"手续费")
   column(name:"GPS_FEE",type:"decimal",remarks:"GPS费")
   column(name:"PARKING_FEE",type:"decimal",remarks:"停车费")
   column(name:"PROPERTY_FEE",type:"decimal",remarks:"产调费")
   column(name:"DOCUMENT_FEE",type:"decimal",remarks:"查档费")
   column(name:"PURCHASE_TAX",type:"decimal",remarks:"购置税")
   column(name:"NOTARIAL_FEE",type:"decimal",remarks:"公证费")
   column(name:"CREDIT_FEE",type:"decimal",remarks:"征信费")
   column(name:"LICENSE_FEE",type:"decimal",remarks:"上牌费")
   column(name:"OTHER_FEE_01",type:"decimal",remarks:"其他费用01")
   column(name:"OTHER_FEE_02",type:"decimal",remarks:"其他费用02")
   column(name:"OTHER_FEE_03",type:"decimal",remarks:"其他费用03")
   column(name:"OTHER_FEE_04",type:"decimal",remarks:"其他费用04")
   column(name:"OTHER_FEE_05",type:"decimal",remarks:"其他费用05")
   column(name:"TOTAL_FEE",type:"decimal",remarks:"总费用")
   column(name:"NET_TOTAL_FEE",type:"decimal",remarks:"不含税费用金额")
   column(name:"VAT_TOTAL_FEE",type:"decimal",remarks:"费用增值税额")
   column(name:"VIOLATION_DEPOSIT",type:"decimal",remarks:"违章押金")
   column(name:"INSURANCE_DEPOSIT",type:"decimal",remarks:"保险押金")
   column(name:"ANNUAL_SURVEY_DEPOSIT",type:"decimal",remarks:"年检押金")
   column(name:"OTHER_DEPOSIT_01",type:"decimal",remarks:"其他押金01")
   column(name:"OTHER_DEPOSIT_02",type:"decimal",remarks:"其他押金02")
   column(name:"OTHER_DEPOSIT_03",type:"decimal",remarks:"其他押金03")
   column(name:"OTHER_DEPOSIT_04",type:"decimal",remarks:"其他押金04")
   column(name:"OTHER_DEPOSIT_05",type:"decimal",remarks:"其他押金05")
   column(name:"TOTAL_DEPOSIT",type:"decimal",remarks:"总押金")
   column(name:"TOTAL_RENTAL",type:"decimal",remarks:"总租金")
   column(name:"NET_TOTAL_RENTAL",type:"decimal",remarks:"不含税租金")
   column(name:"VAT_TOTAL_RENTAL",type:"decimal",remarks:"租金增值税额")
   column(name:"TOTAL_INTEREST",type:"decimal",remarks:"利息总额")
   column(name:"NET_TOTAL_INTEREST",type:"decimal",remarks:"不含税利息")
   column(name:"VAT_TOTAL_INTEREST",type:"decimal",remarks:"利息增值税额")
   column(name:"FINANCE_INCOME",type:"decimal",remarks:"租赁收入")
   column(name:"NET_FINANCE_INCOME",type:"decimal",remarks:"不含税租赁收入")
   column(name:"VAT_FINANCE_INCOME",type:"decimal",remarks:"租赁收入税额")
   column(name:"RECEIVE_RENTAL",type:"decimal",remarks:"已收租金")
   column(name:"RECEIVE_PRINCIPAL",type:"decimal",remarks:"已收本金")
   column(name:"RECEIVE_INTEREST",type:"decimal",remarks:"已收利息")
   column(name:"DEPOSIT_BALANCE",type:"decimal",remarks:"押金余额")
   column(name:"SUBMIT_DATE",type:"datetime",remarks:"申请日期")
   column(name:"APPROVED_DATE",type:"datetime",remarks:"通过日期")
   column(name:"REFUSE_DATE",type:"datetime",remarks:"拒绝日期")
   column(name:"SIGNING_DATE",type:"datetime",remarks:"签约申请日期")
   column(name:"SIGNED_DATE",type:"datetime",remarks:"签约日期")
   column(name:"PAYMENT_DATE",type:"datetime",remarks:"放款日期")
   column(name:"DELIVERY_DATE",type:"datetime",remarks:"提车日期")
   column(name:"INCEPT_DATE",type:"datetime",remarks:"起租日期")
   column(name:"FINISHED_DATE",type:"datetime",remarks:"过户日期")
   column(name:"START_DATE",type:"datetime",remarks:"开始日期")
   column(name:"END_DATE",type:"datetime",remarks:"结束日期")
   column(name:"OVERDUE_FLAG",type:"varchar(1)", defaultValue:"N",remarks:"逾期状态")
   column(name:"TOTAL_OVERDUE",type:"Int", defaultValue:"0",remarks:"逾期天数合计")
   column(name:"BILL_STATUS",type:"varchar(60)", defaultValue:"NOT",remarks:"开票状态（NOT/PARTIAL/FULL）")
   column(name:"DELIVERY_STATUS",type:"varchar(1)", defaultValue:"N",remarks:"提前状态")
   column(name:"INT_RATE_PRECISION",type:"Int", defaultValue:"14", remarks:"利率精度")
   column(name:"IRR",type:"decimal", remarks:"内部收益率")
   column(name:"IRR_AFTER_TAX",type:"decimal", remarks:"税后内部收益率")
   column(name:"PMT",type:"decimal", remarks:"等额本息租金")
   column(name:"PMT_FIRST",type:"decimal", remarks:"等额本息租金（首期）")
   column(name:"CALC_PROMPT",type:"varchar(100)", remarks:"租金计算结果提示")
   column(name:"CALC_PROMPT_MSG",type:"varchar(500)",remarks:"租金计算结果提示信息")
   column(name:"SOURCE_TYPE",type:"varchar(60)",remarks:"订单来源")
   column(name:"SOURCE_ID",type:"bigint",remarks:"源ID")
   column(name:"USER_STATUS_1",type:"varchar(60)",remarks:"订单状态01")
   column(name:"USER_STATUS_2",type:"varchar(60)",remarks:"订单状态01")
   column(name:"DATA_CLASS",type:"varchar(60)", defaultValue:"NORMAL",remarks:"数据种类（NORMAL/HISTORY/CHANGE）")
   column(name:"REMARK",type:"clob",remarks:"备注说明")
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
  addUniqueConstraint(columnNames:"COMPANY_ID,ORDER_CODE",tableName:"CON_ORDER",constraintName: "CON_ORDER_U1")
  createIndex(tableName:"CON_ORDER",indexName:"CON_ORDER_N1"){
   column(name: "SOURCE_TYPE")
   column(name: "SOURCE_ID")
  }
  createIndex(tableName:"CON_ORDER",indexName:"CON_ORDER_N2"){
   column(name: "ORDER_STATUS")
  }
  createIndex(tableName:"CON_ORDER",indexName:"CON_ORDER_N3"){
   column(name: "PRODUCT_CODE")
  }
  createIndex(tableName:"CON_ORDER",indexName:"CON_ORDER_N4"){
   column(name: "DOCUMENT_CATEGORY")
   column(name: "DOCUMENT_TYPE")
  }
  createIndex(tableName:"CON_ORDER",indexName:"CON_ORDER_N5"){
   column(name: "CHANGE_ID")
  }
  createIndex(tableName:"CON_ORDER",indexName:"CON_ORDER_N6"){
   column(name: "CUSTOMER_ID")
  }
  createIndex(tableName:"CON_ORDER",indexName:"CON_ORDER_N7"){
   column(name: "ITEM_ID")
  }
  createIndex(tableName:"CON_ORDER",indexName:"CON_ORDER_N8"){
   column(name: "AGENT_ID")
  }
  createIndex(tableName:"CON_ORDER",indexName:"CON_ORDER_N9"){
   column(name: "UNIT_ID")
  }
  createIndex(tableName:"CON_ORDER",indexName:"CON_ORDER_N10"){
   column(name: "EMPLOYEE_ID")
  }

 }



 changeSet(author:"Admin", id: "2019-03-18_CON_ORDER_CYCLE"){
  if(mhi.isDbType('oracle') || mhi.isDbType('hana')){
   createSequence(sequenceName:'CON_ORDER_CYCLE_S', startValue:"10001")
  }
  createTable(tableName:"CON_ORDER_CYCLE"){
   column(name:"CYCLE_ID",type:"bigint",autoIncrement:"true",startWith:"10001",remarks:"表ID，主键，供其他表做外键"){
    constraints(nullable:"false",primaryKey: "true",primaryKeyName: "CON_ORDER_CYCLE_PK")
   }
   column(name:"ORDER_ID",type:"bigint",remarks:"订单ID")
   column(name:"STATUS",type:"varchar(60)",remarks:"订单状态")
   column(name:"STATUS_DATE",type:"datetime",remarks:"状态时间")
   column(name:"CHANGER",type:"bigint",remarks:"修改人")
   column(name:"PARENT_CYCLE_ID",type:"bigint",remarks:"上一节点ID")
   column(name:"CONTENT",type:"varchar(200)",remarks:"备注")
   column(name:"REMARK",type:"clob",remarks:"备注说明")
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
  createIndex(tableName:"CON_ORDER_CYCLE",indexName:"CON_ORDER_CYCLE_N1"){
   column(name: "ORDER_ID")
  }

 }



 changeSet(author:"Admin", id: "2019-03-18_CON_CASHFLOW"){
  if(mhi.isDbType('oracle') || mhi.isDbType('hana')){
   createSequence(sequenceName:'CON_CASHFLOW_S', startValue:"10001")
  }
  createTable(tableName:"CON_CASHFLOW"){
   column(name:"CASHFLOW_ID",type:"bigint",autoIncrement:"true",startWith:"10001",remarks:"表ID，主键，供其他表做外键"){
    constraints(nullable:"false",primaryKey: "true",primaryKeyName: "CON_CASHFLOW_PK")
   }
   column(name:"ORDER_ID",type:"bigint",remarks:"订单ID")
   column(name:"CF_TYPE",type:"varchar(60)",remarks:"现金流类型")
   column(name:"CF_DIRECTION",type:"varchar(60)",remarks:"现金流方向（INFLOW/OUTFLOW/NONCASH）")
   column(name:"CF_STATUS",type:"varchar(60)",remarks:"现金流状态（BLOCK/冻结 、RELEASE/下达、CANCEL/取消）")
   column(name:"TIMES",type:"Int",remarks:"期数")
   column(name:"CALC_DATE",type:"datetime",remarks:"计算日")
   column(name:"DUE_DATE",type:"datetime",remarks:"支付日/到期日")
   column(name:"DUE_AMOUNT",type:"decimal",remarks:"应收金额")
   column(name:"NET_DUE_AMOUNT",type:"decimal",remarks:"不含税应收金额")
   column(name:"VAT_DUE_AMOUNT",type:"decimal",remarks:"增值税额")
   column(name:"PRINCIPAL",type:"decimal",remarks:"应收本金")
   column(name:"NET_PRINCIPAL",type:"decimal",remarks:"不含税本金")
   column(name:"VAT_PRINCIPAL",type:"decimal",remarks:"本金增值税额")
   column(name:"INTEREST",type:"decimal",remarks:"应收利息")
   column(name:"NET_INTEREST",type:"decimal",remarks:"不含税利息")
   column(name:"VAT_INTEREST",type:"decimal",remarks:"利息增值税额")
   column(name:"PRINCIPAL_IMPLICIT_RATE",type:"decimal",remarks:"实际利率法本金")
   column(name:"NET_PRINCIPAL_IMPLICIT",type:"decimal",remarks:"实际利率法不含税本金")
   column(name:"VAT_PRINCIPAL_IMPLICIT",type:"decimal",remarks:"实际利率法本金增值税额")
   column(name:"INTEREST_IMPLICIT_RATE",type:"decimal",remarks:"实际利率法利息")
   column(name:"NET_INTEREST_IMPLICIT",type:"decimal",remarks:"实际利率法不含税利息")
   column(name:"VAT_INTEREST_IMPLICIT",type:"decimal",remarks:"实际利率法利息增值税额")
   column(name:"OUTSTANDING_RENTAL",type:"decimal",remarks:"当期剩余租金")
   column(name:"OUTSTANDING_PRINCIPAL",type:"decimal",remarks:"当期剩余本金")
   column(name:"OUTSTANDING_INTEREST",type:"decimal",remarks:"当期剩余利息")
   column(name:"INTEREST_PERIOD_DAYS",type:"Int",remarks:"计息天数（利息期天数）")
   column(name:"SOURCE_TYPE",type:"varchar(60)",remarks:"来源类型（CALCULATOR/计算器ET/结清MANUAL/手工DAY_END/日结）")
   column(name:"SOURCE_ID",type:"bigint",remarks:"来源ID")
   column(name:"COLOUR_SCHEME",type:"varchar(60)",remarks:"颜色方案")
   column(name:"CALC_LINE_ID",type:"bigint",remarks:"计算器行ID")
   column(name:"OVERDUE_STATUS",type:"varchar(60)",defaultValue:"N",remarks:"逾期状态（N 未逾期、Y 已逾期）")
   column(name:"OVERDUE_DATE",type:"datetime",remarks:"逾期计算日")
   column(name:"OVERDUE_MAX_DAYS",type:"Int",remarks:"最大逾期天数")
   column(name:"OVERDUE_AMOUNT",type:"decimal",remarks:"逾期金额")
   column(name:"OVERDUE_PRINCIPAL",type:"decimal",remarks:"逾期本金")
   column(name:"OVERDUE_INTEREST",type:"decimal",remarks:"逾期利息")
   column(name:"RECEIVED_AMOUNT",type:"decimal",remarks:"已收金额")
   column(name:"RECEIVED_PRINCIPAL",type:"decimal",remarks:"已收本金")
   column(name:"RECEIVED_INTEREST",type:"decimal",remarks:"已收利息")
   column(name:"WRITEOFF_FLAG",type:"varchar(60)",defaultValue:"NOT",remarks:"核销标志（NOT/PARTIAL/FULL）")
   column(name:"LAST_RECEIVED_DATE",type:"datetime",remarks:"最后收款日")
   column(name:"FULL_WRITEOFF_DATE",type:"datetime",remarks:"完全核销日")
   column(name:"PENALTY_PROCESS_STATUS",type:"varchar(60)",defaultValue:"NORMAL",remarks:"罚金处理状态（NORMAL正常/SUSPEND暂停计算/EXEMPT豁免）")
   column(name:"BILLING_STATUS",type:"varchar(60)",defaultValue:"NOT",remarks:"开票状态（NOT/PARTIAL/FULL）")
   column(name:"BILLING_AMOUNT",type:"decimal",remarks:"开票金额")
   column(name:"BILLING_PRINCIPAL",type:"decimal",remarks:"本金开票金额")
   column(name:"BILLING_INTEREST",type:"decimal",remarks:"利息开票金额")
   column(name:"REMARK",type:"clob",remarks:"备注说明")
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
  createIndex(tableName:"CON_CASHFLOW",indexName:"CON_CASHFLOW_N1"){
   column(name: "ORDER_ID")
  }
  createIndex(tableName:"CON_CASHFLOW",indexName:"CON_CASHFLOW_N2"){
   column(name: "CF_TYPE")
   column(name: "TIMES")
  }
  createIndex(tableName:"CON_CASHFLOW",indexName:"CON_CASHFLOW_N3"){
   column(name: "SOURCE_TYPE")
   column(name: "SOURCE_ID")
  }
  createIndex(tableName:"CON_CASHFLOW",indexName:"CON_CASHFLOW_N4"){
   column(name: "CALC_LINE_ID")
  }

 }



 changeSet(author:"Admin", id: "2019-03-18_CON_CONTENT"){
  if(mhi.isDbType('oracle') || mhi.isDbType('hana')){
   createSequence(sequenceName:'CON_CONTENT_S', startValue:"10001")
  }
  createTable(tableName:"CON_CONTENT"){
   column(name:"CONTENT_ID",type:"bigint",autoIncrement:"true",startWith:"10001",remarks:"表ID，主键，供其他表做外键"){
    constraints(nullable:"false",primaryKey: "true",primaryKeyName: "CON_CONTENT_PK")
   }
   column(name:"ORDER_ID",type:"bigint",remarks:"订单ID")
   column(name:"CONTENT_NAME",type:"varchar(200)",remarks:"文本名称")
   column(name:"CLAUSE_USAGE",type:"varchar(60)",remarks:"模板用途")
   column(name:"TEMPLET_ID",type:"varchar(50)",remarks:"模板ID")
   column(name:"PRINT_FLAG",type:"varchar(1)",remarks:"打印标志")
   column(name:"TIMES",type:"Int",remarks:"期数")
   column(name:"TRANSACTION_ID",type:"bigint",remarks:"现金事务ID")
   column(name:"PAYMENT_ID",type:"bigint",remarks:"付款ID")
   column(name:"PRINT_COUNT",type:"Int",remarks:"打印次数")
   column(name:"AVAILABLE_FLAG",type:"varchar(1)",defaultValue:"N",remarks:"生效标志")
   column(name:"REMARK",type:"clob",remarks:"备注说明")
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
  createIndex(tableName:"CON_CONTENT",indexName:"CON_CONTENT_N1"){
   column(name: "ORDER_ID")
   column(name: "TIMES")
  }
  createIndex(tableName:"CON_CONTENT",indexName:"CON_CONTENT_N2"){
   column(name: "CLAUSE_USAGE")
   column(name: "TEMPLET_ID")
  }

 }



 changeSet(author:"Admin", id: "2019-03-18_CSH_TRANSACTION"){
  if(mhi.isDbType('oracle') || mhi.isDbType('hana')){CSH_TRANSACTION
   createSequence(sequenceName:'CSH_TRANSACTION_S', startValue:"10001")
  }
  createTable(tableName:"CSH_TRANSACTION"){
   column(name:"TRANSACTION_ID",type:"bigint",autoIncrement:"true",startWith:"10001",remarks:"表ID，主键，供其他表做外键"){
    constraints(nullable:"false",primaryKey: "true",primaryKeyName: "CSH_TRANSACTION_PK")
   }
   column(name:"TRANSACTION_NUM",type:"bigint",remarks:"交易编号")
   column(name:"TRANSACTION_CATEGORY",type:"varchar(60)",remarks:"交易类别（BUSINESS-经营类，LOAN_REPAYMENT-银行还款，LOAN_WITHDRAW-银行提款）")
   column(name:"TRANSACTION_TYPE",type:"varchar(60)",remarks:"交易类型（RECEIPT-收款，PAYMENT-付款，ADVANCE_RECEIPT-预收款，预付款-PREPAYMENT，TRANSFER_IN-转入，TRANSFER_OUT-转出）")
   column(name:"TRANSACTION_DATE",type:"datetime",remarks:"交易日期")
   column(name:"COMPANY_ID",type:"bigint",remarks:"公司ID")
   column(name:"TRANSACTION_AMOUNT",type:"decimal",remarks:"事务金额")
   column(name:"CASHFLOW_AMOUNT",type:"decimal",remarks:"现金流金额")
   column(name:"INTERNAL_PERIOD_NUM",type:"Int",remarks:"内部期间")
   column(name:"PERIOD_NAME",type:"varchar(60)",remarks:"期间")
   column(name:"BANK_ACCOUNT_ID",type:"bigint",remarks:"本方账户ID")
   column(name:"ORDER_ID",type:"bigint",remarks:"订单ID")
   column(name:"CUSTOMER_ID",type:"bigint",remarks:"客户ID")
   column(name:"OPT_ACCOUNT_ID",type:"bigint",remarks:"对方账户ID")
   column(name:"OPT_ACCOUNT_NUM",type:"varchar(60)",remarks:"对方账号")
   column(name:"OPT_ACCOUNT_NAME",type:"varchar(200)",remarks:"对方账户")
   column(name:"OPT_BANK_NAME",type:"varchar(200)",remarks:"对方开户行")
   column(name:"BANK_SLIP_NUM",type:"varchar(200)",remarks:"银行回单")
   column(name:"DESCRIPTION",type:"varchar(200)",remarks:"备注")
   column(name:"POSTED_FLAG",type:"varchar(1)",defaultValue:"N",remarks:"过账标志（N/Y）")
   column(name:"WRITEOFF_FLAG",type:"varchar(60)",defaultValue:"NOT",remarks:"核销标志（ NOT/未核销 ，PARTIAL/部分核销 ， FULL/全部核销 ）")
   column(name:"WRITEOFF_AMOUNT",type:"decimal",remarks:"核销金额")
   column(name:"FULL_WRITEOFF_DATE",type:"datetime",remarks:"完全核销日")
   column(name:"REVERSED_FLAG",type:"varchar(1)",defaultValue:"N",remarks:"反冲标志（ N/未反冲 ，W/被反冲，R/反冲事物 ）")
   column(name:"REVERSED_DATE",type:"datetime",remarks:"反冲日期")
   column(name:"REVERSED_TRANS_ID",type:"bigint",remarks:"反冲事务ID")
   column(name:"RETURNED_FLAG",type:"varchar(60)",defaultValue:"NOT",remarks:"退款标志（NOT/未退款 ，PARTIAL/部分退款 ， FULL/全部退款 ， RETURN/退款事务）")
   column(name:"RETURNED_AMOUNT",type:"decimal",remarks:"已退款金额")
   column(name:"RETURNED_DATE",type:"datetime",remarks:"最新退款日期")
   column(name:"RETURNED_TRANS_ID",type:"bigint",remarks:"被退款事务ID")
   column(name:"SOURCE_TYPE",type:"varchar(60)",remarks:"来源类型")
   column(name:"SOURCE_ID",type:"bigint",remarks:"来源ID")
   column(name:"SOURCE_LINE_ID",type:"bigint",remarks:"来源行ID")
   column(name:"JE_METHOD",type:"varchar(60)",remarks:"凭证生成方式（CLEARING/CONSOLIDATION）")
   column(name:"JE_FLAG",type:"varchar(1)",defaultValue:"N",remarks:"凭证标志")
   column(name:"ENABLED_FLAG",type:"varchar(1)",defaultValue:"Y",remarks:"启用标志")
   column(name:"REMARK",type:"clob",remarks:"备注说明")
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
  addUniqueConstraint(columnNames:"TRANSACTION_NUM,COMPANY_ID",tableName:"CSH_TRANSACTION",constraintName: "CSH_TRANSACTION_U1")
  createIndex(tableName:"CSH_TRANSACTION",indexName:"CSH_TRANSACTION_N1"){
   column(name: "TRANSACTION_CATEGORY")
   column(name: "TRANSACTION_TYPE")
  }
  createIndex(tableName:"CSH_TRANSACTION",indexName:"CSH_TRANSACTION_N2"){
   column(name: "INTERNAL_PERIOD_NUM")
  }
  createIndex(tableName:"CSH_TRANSACTION",indexName:"CSH_TRANSACTION_N3"){
   column(name: "BANK_ACCOUNT_ID")
  }
  createIndex(tableName:"CSH_TRANSACTION",indexName:"CSH_TRANSACTION_N4"){
   column(name: "CUSTOMER_ID")
   column(name: "OPT_ACCOUNT_ID")
  }
  createIndex(tableName:"CSH_TRANSACTION",indexName:"CSH_TRANSACTION_N5"){
   column(name: "OPT_ACCOUNT_NUM")
   column(name: "OPT_ACCOUNT_NAME")
  }
  createIndex(tableName:"CSH_TRANSACTION",indexName:"CSH_TRANSACTION_N6"){
   column(name: "WRITEOFF_FLAG")
  }
  createIndex(tableName:"CSH_TRANSACTION",indexName:"CSH_TRANSACTION_N7"){
   column(name: "REVERSED_FLAG")
  }
  createIndex(tableName:"CSH_TRANSACTION",indexName:"CSH_TRANSACTION_N8"){
   column(name: "RETURNED_FLAG")
  }
  createIndex(tableName:"CSH_TRANSACTION",indexName:"CSH_TRANSACTION_N9"){
   column(name: "SOURCE_TYPE")
   column(name: "SOURCE_ID")
   column(name: "SOURCE_LINE_ID")
  }

 }



 changeSet(author:"Admin", id: "2019-03-18_CSH_WRITEOFF"){
  if(mhi.isDbType('oracle') || mhi.isDbType('hana')){
   createSequence(sequenceName:'CSH_WRITEOFF_S', startValue:"10001")
  }
  createTable(tableName:"CSH_WRITEOFF"){
   column(name:"WRITEOFF_ID",type:"bigint",autoIncrement:"true",startWith:"10001",remarks:"表ID，主键，供其他表做外键"){
    constraints(nullable:"false",primaryKey: "true",primaryKeyName: "CSH_WRITEOFF_PK")
   }
   column(name:"WRITEOFF_TYPE",type:"varchar(60)",remarks:"核销类型")
   column(name:"WRITEOFF_DATE",type:"datetime",remarks:"核销日期")
   column(name:"INTERNAL_PERIOD_NUM",type:"Int",remarks:"内部期间")
   column(name:"PERIOD_NAME",type:"varchar(60)",remarks:"期间")
   column(name:"TRANSACTION_ID",type:"bigint",remarks:"现金事务ID")
   column(name:"BILL_AMOUNT",type:"decimal",remarks:"账单金额")
   column(name:"REVERSED_FLAG",type:"varchar(1)",defaultValue:"N",remarks:"反冲标志（N 未反冲/W 被反冲/R 反冲交易）")
   column(name:"REVERSED_WRITEOFF_ID",type:"bigint",remarks:"反冲核销ID")
   column(name:"REVERSED_DATE",type:"datetime",remarks:"反冲日期")
   column(name:"ORDER_ID",type:"bigint",remarks:"订单ID")
   column(name:"CASHFLOW_ID",type:"bigint",remarks:"现金流ID")
   column(name:"TIMES",type:"Int",remarks:"期数")
   column(name:"CF_TYPE",type:"varchar(60)",remarks:"现金流类型")
   column(name:"WRITEOFF_AMOUNT",type:"decimal",remarks:"核销金额")
   column(name:"WRITEOFF_PRINCIPAL",type:"decimal",remarks:"核销本金")
   column(name:"WRITEOFF_INTEREST",type:"decimal",remarks:"核销利息")
   column(name:"DESCRIPTION",type:"varchar(200)",remarks:"摘要")
   column(name:"SOURCE_TYPE",type:"varchar(60)",remarks:"来源类型")
   column(name:"SOURCE_ID",type:"bigint",remarks:"来源ID")
   column(name:"SOURCE_LINE_ID",type:"bigint",remarks:"来源行ID")
   column(name:"JE_MOTHED",type:"varchar(60)",remarks:"凭证生成方式（CLEARING/CONSOLIDATION）")
   column(name:"JE_FLAG",type:"varchar(1)",defaultValue:"N",remarks:"凭证标志")
   column(name:"REMARK",type:"clob",remarks:"备注说明")
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
  createIndex(tableName:"CSH_WRITEOFF",indexName:"CSH_WRITEOFF_N1"){
   column(name: "TRANSACTION_ID")
  }
  createIndex(tableName:"CSH_WRITEOFF",indexName:"CSH_WRITEOFF_N2"){
   column(name: "ORDER_ID")
   column(name: "CASHFLOW_ID")
   column(name: "TIMES")
  }
  createIndex(tableName:"CSH_WRITEOFF",indexName:"CSH_WRITEOFF_N3"){
   column(name: "SOURCE_TYPE")
   column(name: "SOURCE_ID")
   column(name: "SOURCE_LINE_ID")
  }
  createIndex(tableName:"CSH_WRITEOFF",indexName:"CSH_WRITEOFF_N4"){
   column(name: "WRITEOFF_TYPE")
   column(name: "WRITEOFF_DATE")
  }
  createIndex(tableName:"CSH_WRITEOFF",indexName:"CSH_WRITEOFF_N5"){
   column(name: "INTERNAL_PERIOD_NUM")
  }

 }



 changeSet(author:"Admin", id: "2019-03-18_CSH_PAYMENT"){
  if(mhi.isDbType('oracle') || mhi.isDbType('hana')){
   createSequence(sequenceName:'CSH_PAYMENT_S', startValue:"10001")
  }
  createTable(tableName:"CSH_PAYMENT"){
   column(name:"PAYMENT_ID",type:"bigint",autoIncrement:"true",startWith:"10001",remarks:"表ID，主键，供其他表做外键"){
    constraints(nullable:"false",primaryKey: "true",primaryKeyName: "CSH_PAYMENT_PK")
   }
   column(name:"PAYMENT_CODE",type:"varchar(60)",remarks:"付款编号")
   column(name:"COMPANY_ID",type:"bigint",remarks:"公司FND_COMPANY_B.COMPANY_ID")
   column(name:"DOCUMENT_CATEGORY",type:"varchar(60)",remarks:"单据类别")
   column(name:"DOCUMENT_TYPE",type:"varchar(60)",remarks:"单据类型")
   column(name:"STATUS",type:"varchar(60)",defaultValue:"NEW",remarks:"付款状态")
   column(name:"AMOUNT",type:"decimal",remarks:"申请金额")
   column(name:"AMOUNT_PAID",type:"decimal",remarks:"已付金额")
   column(name:"DESCRIPTION",type:"varchar(200)",remarks:"备注说明")
   column(name:"REQUEST_DATE",type:"datetime",remarks:"申请日期")
   column(name:"PAYMENT_DATE",type:"datetime",remarks:"付款日期")
   column(name:"LAST_PAY_DATE",type:"datetime",remarks:"最后付款日期")
   column(name:"TRANSACTION_CATEGORY",type:"varchar(60)",remarks:"事务类别")
   column(name:"PAYMENT_METHOD",type:"varchar(60)",remarks:"付款方式")
   column(name:"ORDER_ID",type:"bigint",remarks:"订单ID")
   column(name:"CUSTOMER_ID",type:"bigint",remarks:"客户ID")
   column(name:"APPROVED_DATE",type:"datetime",remarks:"审批日期")
   column(name:"CLOSED_DATE",type:"datetime",remarks:"关闭日期")
   column(name:"PRINT_FLAG",type:"varchar(1)",defaultValue:"N",remarks:"打印标志")
   column(name:"PRINT_DATE",type:"datetime",remarks:"打印日期")
   column(name:"REMARK",type:"clob",remarks:"备注说明")
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
  addUniqueConstraint(columnNames:"PAYMENT_CODE,COMPANY_ID",tableName:"CSH_PAYMENT",constraintName: "CSH_PAYMENT_U1")
  createIndex(tableName:"CSH_PAYMENT",indexName:"CSH_PAYMENT_N1"){
   column(name: "DOCUMENT_CATEGORY")
   column(name: "DOCUMENT_TYPE")
  }
  createIndex(tableName:"CSH_PAYMENT",indexName:"CSH_PAYMENT_N2"){
   column(name: "STATUS")
  }
  createIndex(tableName:"CSH_PAYMENT",indexName:"CSH_PAYMENT_N3"){
   column(name: "CUSTOMER_ID")
  }
  createIndex(tableName:"CSH_PAYMENT",indexName:"CSH_PAYMENT_N4"){
   column(name: "ORDER_ID")
  }

 }



 changeSet(author:"Admin", id: "2019-03-18_CSH_PAYMENT_DETAIL"){
  if(mhi.isDbType('oracle') || mhi.isDbType('hana')){
   createSequence(sequenceName:'CSH_PAYMENT_DETAIL_S', startValue:"10001")
  }
  createTable(tableName:"CSH_PAYMENT_DETAIL"){
   column(name:"DETAIL_ID",type:"bigint",autoIncrement:"true",startWith:"10001",remarks:"表ID，主键，供其他表做外键"){
    constraints(nullable:"false",primaryKey: "true",primaryKeyName: "CSH_PAYMENT_DETAIL_PK")
   }
   column(name:"PAYMENT_ID",type:"bigint",remarks:"付款ID")
   column(name:"PAYMENT_TYPE",type:"varchar(60)",remarks:"行类型（PREPAYMENT/预付款、DEBT/债务、EXP_REPORT/报销单、OTHERS/其他）")
   column(name:"PAYMENT_STATUS",type:"varchar(60)",defaultValue:"NOT",remarks:"付款状态（ NOT/未付款 ，PARTIAL/部分付款 ， FULL/全部付款 ）")
   column(name:"AMOUNT",type:"decimal",remarks:"申请金额")
   column(name:"AMOUNT_PAID",type:"decimal",remarks:"已付金额")
   column(name:"PAYMENT_DATE",type:"datetime",remarks:"付款日期")
   column(name:"COMPLETED_DATE",type:"datetime",remarks:"完成日期")
   column(name:"PAYMENT_METHOD",type:"varchar(60)",remarks:"付款方式")
   column(name:"DESCRIPTION",type:"varchar(200)",remarks:"付款摘要")
   column(name:"BANK_ACCOUNT_ID",type:"bigint",remarks:"本方账户ID")
   column(name:"OPT_ACCOUNT_ID",type:"bigint",remarks:"对方账户ID")
   column(name:"OPT_ACCOUNT_NUM",type:"varchar(60)",remarks:"对方账号")
   column(name:"OPT_ACCOUNT_NAME",type:"varchar(200)",remarks:"对方账户")
   column(name:"OPT_BANK_NAME",type:"varchar(200)",remarks:"对方开户行")
   column(name:"SOURCE_TYPE",type:"varchar(60)",remarks:"来源类型")
   column(name:"SOURCE_ID",type:"bigint",remarks:"来源ID")
   column(name:"SOURCE_LINE_ID",type:"bigint",remarks:"来源行ID")
   column(name:"REMARK",type:"clob",remarks:"备注说明")
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
  createIndex(tableName:"CSH_PAYMENT_DETAIL",indexName:"CSH_PAYMENT_DETAIL_N1"){
   column(name: "PAYMENT_ID")
  }
  createIndex(tableName:"CSH_PAYMENT_DETAIL",indexName:"CSH_PAYMENT_DETAIL_N2"){
   column(name: "PAYMENT_TYPE")
  }
  createIndex(tableName:"CSH_PAYMENT_DETAIL",indexName:"CSH_PAYMENT_DETAIL_N3"){
   column(name: "PAYMENT_STATUS")
  }
  createIndex(tableName:"CSH_PAYMENT_DETAIL",indexName:"CSH_PAYMENT_DETAIL_N4"){
   column(name: "SOURCE_TYPE")
   column(name: "SOURCE_ID")
   column(name: "SOURCE_LINE_ID")
  }

 }



 changeSet(author:"Admin", id: "2019-03-18_PLM_SETTLE"){
  if(mhi.isDbType('oracle') || mhi.isDbType('hana')){
   createSequence(sequenceName:'PLM_SETTLE_S', startValue:"10001")
  }
  createTable(tableName:"PLM_SETTLE"){
   column(name:"SETTLE_ID",type:"bigint",autoIncrement:"true",startWith:"10001",remarks:"表ID，主键，供其他表做外键"){
    constraints(nullable:"false",primaryKey: "true",primaryKeyName: "PLM_SETTLE_PK")
   }
   column(name:"SETTLE_CODE",type:"varchar(60)",remarks:"结清编号")
   column(name:"DOCUMENT_CATEGORY",type:"varchar(60)",remarks:"单据类别")
   column(name:"DOCUMENT_TYPE",type:"varchar(60)",remarks:"单据类型")
   column(name:"COMPANY_ID",type:"bigint",remarks:"公司ID")
   column(name:"ORDER_ID",type:"bigint",remarks:"订单ID")
   column(name:"SETTLE_TYPE",type:"varchar(60)",remarks:"结清类型")
   column(name:"STATUS",type:"varchar(60)",defaultValue:"NEW",remarks:"状态")
   column(name:"SETTLE_DATE",type:"datetime",remarks:"结清日期")
   column(name:"LAST_PAYDUE_DATE",type:"datetime",remarks:"最近支付期数日期")
   column(name:"LAST_PAYDUE_TIMES",type:"Int",remarks:"最近支付期数")
   column(name:"UNDUE_PRINCIPAL",type:"decimal",remarks:"未收本金")
   column(name:"UNDUE_INTEREST",type:"decimal",remarks:"未收利息")
   column(name:"UNDUE_SERVICE_FEE",type:"decimal",remarks:"未收服务费")
   column(name:"UNDUE_PARKING_FEE",type:"decimal",remarks:"未收停车费")
   column(name:"UNDUE_GPS_FEE",type:"decimal",remarks:"未收GPS费")
   column(name:"UNDUE_DEPOSIT",type:"decimal",remarks:"未退押金")
   column(name:"UNDUE_RECEIVE",type:"decimal",remarks:"预收款")
   column(name:"RESIDUAL_VALUE",type:"decimal",remarks:"留购金")
   column(name:"OVERDUE_FEE",type:"decimal",remarks:"逾期费")
   column(name:"PENALTY",type:"decimal",remarks:"违约金")
   column(name:"CHARGE_FEE",type:"decimal",remarks:"结清手续费")
   column(name:"DEDUCT_FEE",type:"decimal",remarks:"减免金额")
   column(name:"SETTLE_AMOUNT",type:"decimal",remarks:"结清金额")
   column(name:"CASHFLOW_ID",type:"bigint",remarks:"结清现金流ID")
   column(name:"DESCRIPTION",type:"varchar(200)",remarks:"摘要")
   column(name:"SUBMIT_DATE",type:"datetime",remarks:"提交日期")
   column(name:"APPROVED_DATE",type:"datetime",remarks:"审批日期")
   column(name:"FINISH_DATE",type:"datetime",remarks:"完成日期")
   column(name:"PRINT_FLAG",type:"varchar(1)",defaultValue:"N",remarks:"打印标志")
   column(name:"PRINT_DATE",type:"datetime",remarks:"打印日期")
   column(name:"REMARK",type:"clob",remarks:"备注说明")
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
  addUniqueConstraint(columnNames:"SETTLE_CODE,COMPANY_ID",tableName:"PLM_SETTLE",constraintName: "PLM_SETTLE_U1")
  createIndex(tableName:"PLM_SETTLE",indexName:"PLM_SETTLE_N1"){
   column(name: "DOCUMENT_CATEGORY")
   column(name: "DOCUMENT_TYPE")
   column(name: "COMPANY_ID")
  }
  createIndex(tableName:"PLM_SETTLE",indexName:"PLM_SETTLE_N2"){
   column(name: "ORDER_ID")
  }
  createIndex(tableName:"PLM_SETTLE",indexName:"PLM_SETTLE_N3"){
   column(name: "SETTLE_TYPE")
   column(name: "STATUS")
  }
  createIndex(tableName:"PLM_SETTLE",indexName:"PLM_SETTLE_N4"){
   column(name: "SETTLE_DATE")
  }

 }



 changeSet(author:"Admin", id: "2019-03-18_PLM_SETTLE_DETAIL"){
  if(mhi.isDbType('oracle') || mhi.isDbType('hana')){
   createSequence(sequenceName:'PLM_SETTLE_DETAIL_S', startValue:"10001")
  }
  createTable(tableName:"PLM_SETTLE_DETAIL"){
   column(name:"DETAIL_ID",type:"bigint",autoIncrement:"true",startWith:"10001",remarks:"表ID，主键，供其他表做外键"){
    constraints(nullable:"false",primaryKey: "true",primaryKeyName: "PLM_SETTLE_DETAIL_PK")
   }
   column(name:"SETTLE_ID",type:"bigint",remarks:"结清ID")
   column(name:"AMOUNT",type:"decimal",remarks:"金额")
   column(name:"CF_TYPE",type:"varchar(60)",remarks:"现金流类型")
   column(name:"DIRECTION",type:"varchar(60)",remarks:"方向")
   column(name:"DESCRIPTION",type:"varchar(200)",remarks:"摘要")
   column(name:"REMARK",type:"clob",remarks:"备注说明")
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
  createIndex(tableName:"PLM_SETTLE_DETAIL",indexName:"PLM_SETTLE_DETAIL_N1"){
   column(name: "SETTLE_ID")
  }

 }



 changeSet(author:"Admin", id: "2019-03-18_PLM_RETRIEVE"){
  if(mhi.isDbType('oracle') || mhi.isDbType('hana')){
   createSequence(sequenceName:'PLM_RETRIEVE_S', startValue:"10001")
  }
  createTable(tableName:"PLM_RETRIEVE"){
   column(name:"RETRIEVE_ID",type:"bigint",autoIncrement:"true",startWith:"10001",remarks:"表ID，主键，供其他表做外键"){
    constraints(nullable:"false",primaryKey: "true",primaryKeyName: "PLM_RETRIEVE_PK")
   }
   column(name:"RETRIEVE_CODE",type:"varchar(60)",remarks:"拖收编号")
   column(name:"DOCUMENT_CATEGORY",type:"varchar(60)",remarks:"单据类别")
   column(name:"DOCUMENT_TYPE",type:"varchar(60)",remarks:"单据类型")
   column(name:"COMPANY_ID",type:"bigint",remarks:"公司FND_COMPANY_B.COMPANY_ID")
   column(name:"ORDER_ID",type:"bigint",remarks:"订单ID")
   column(name:"STATUS",type:"varchar(60)",defaultValue:"NEW",remarks:"状态")
   column(name:"RETRIEVE_DATE",type:"datetime",remarks:"拖收日期")
   column(name:"AMOUNT",type:"decimal",remarks:"总金额")
   column(name:"CHARGE_FEE",type:"decimal",remarks:"拖收费用")
   column(name:"OIL_FEE",type:"decimal",remarks:"油费")
   column(name:"DEPOSIT",type:"decimal",remarks:"押金")
   column(name:"CASHFLOW_ID",type:"bigint",remarks:"现金流ID")
   column(name:"DESCRIPTION",type:"varchar(200)",remarks:"摘要")
   column(name:"SUBMIT_DATE",type:"datetime",remarks:"申请日期")
   column(name:"APPROVED_DATE",type:"datetime",remarks:"通过日期")
   column(name:"RECEIVE_DATE",type:"datetime",remarks:"收回日期")
   column(name:"RELEASE_DATE",type:"datetime",remarks:"释放日期")
   column(name:"REMARK",type:"clob",remarks:"备注说明")
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
  addUniqueConstraint(columnNames:"COMPANY_ID,RETRIEVE_CODE",tableName:"PLM_RETRIEVE",constraintName: "PLM_RETRIEVE_U1")
  createIndex(tableName:"PLM_RETRIEVE",indexName:"PLM_RETRIEVE_N1"){
   column(name: "ORDER_ID")
  }
  createIndex(tableName:"PLM_RETRIEVE",indexName:"PLM_RETRIEVE_N2"){
   column(name: "DOCUMENT_CATEGORY")
   column(name: "DOCUMENT_TYPE")
  }
  createIndex(tableName:"PLM_RETRIEVE",indexName:"PLM_RETRIEVE_N3"){
   column(name: "RETRIEVE_DATE")
  }
  createIndex(tableName:"PLM_RETRIEVE",indexName:"PLM_RETRIEVE_N4"){
   column(name: "STATUS")
  }

 }

 changeSet(author:"Admin", id: "2019-03-18_PLM_DISPOSE"){
  if(mhi.isDbType('oracle') || mhi.isDbType('hana')){
   createSequence(sequenceName:'PLM_DISPOSE_S', startValue:"10001")
  }
  createTable(tableName:"PLM_DISPOSE"){
   column(name:"DISPOSE_ID",type:"bigint",autoIncrement:"true",startWith:"10001",remarks:"表ID，主键，供其他表做外键"){
    constraints(nullable:"false",primaryKey: "true",primaryKeyName: "PLM_DISPOSE_PK")
   }
   column(name:"DISPOSE_CODE",type:"varchar(60)",remarks:"处置编号")
   column(name:"DOCUMENT_CATEGORY",type:"varchar(60)",remarks:"单据类别")
   column(name:"DOCUMENT_TYPE",type:"varchar(60)",remarks:"单据类型")
   column(name:"COMPANY_ID",type:"bigint",remarks:"公司FND_COMPANY_B.COMPANY_ID")
   column(name:"ORDER_ID",type:"bigint",remarks:"订单ID")
   column(name:"STATUS",type:"varchar(60)",defaultValue:"NEW",remarks:"状态")
   column(name:"DISPOSE_DATE",type:"datetime",remarks:"处置日期")
   column(name:"AMOUNT",type:"decimal",remarks:"处置金额")
   column(name:"UNDUE_PRINCIPAL",type:"decimal",remarks:"未收本金")
   column(name:"UNDUE_INTEREST",type:"decimal",remarks:"未收利息")
   column(name:"UNDUE_SERVICE_FEE",type:"decimal",remarks:"未收服务费")
   column(name:"UNDUE_PARKING_FEE",type:"decimal",remarks:"未收停车费")
   column(name:"UNDUE_GPS_FEE",type:"decimal",remarks:"未收GPS费")
   column(name:"UNDUE_DEPOSIT",type:"decimal",remarks:"未退押金")
   column(name:"UNDUE_RECEIVE",type:"decimal",remarks:"预收款")
   column(name:"DEBT_AMOUNT",type:"decimal",remarks:"亏损金额")
   column(name:"DEBT_RATE",type:"decimal",remarks:"坏账率")
   column(name:"CASHFLOW_ID",type:"bigint",remarks:"现金流ID")
   column(name:"DESCRIPTION",type:"varchar(200)",remarks:"摘要")
   column(name:"SUBMIT_DATE",type:"datetime",remarks:"申请日期")
   column(name:"APPROVED_DATE",type:"datetime",remarks:"通过日期")
   column(name:"FINISHED_DATE",type:"datetime",remarks:"完成日期")
   column(name:"REMARK",type:"clob",remarks:"备注说明")
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
  addUniqueConstraint(columnNames:"COMPANY_ID,DISPOSE_CODE",tableName:"PLM_DISPOSE",constraintName: "PLM_DISPOSE_U1")
  createIndex(tableName:"PLM_DISPOSE",indexName:"PLM_DISPOSE_N1"){
   column(name: "ORDER_ID")
  }
  createIndex(tableName:"PLM_DISPOSE",indexName:"PLM_DISPOSE_N2"){
   column(name: "DOCUMENT_CATEGORY")
   column(name: "DOCUMENT_TYPE")
  }
  createIndex(tableName:"PLM_DISPOSE",indexName:"PLM_DISPOSE_N3"){
   column(name: "DISPOSE_DATE")
  }
  createIndex(tableName:"PLM_DISPOSE",indexName:"PLM_DISPOSE_N4"){
   column(name: "STATUS")
  }

 }

    changeSet(author:"Admin", id: "2019-04-23_FND_COMPANY_LIMIT"){
        if(mhi.isDbType('oracle') || mhi.isDbType('hana')){
            createSequence(sequenceName:'FND_COMPANY_LIMIT_S', startValue:"10001")
        }
        createTable(tableName:"FND_COMPANY_LIMIT"){
            column(name:"LIMIT_ID",type:"bigint",autoIncrement:"true",startWith:"10001",remarks:"表ID，主键，供其他表做外键"){
                constraints(nullable:"false",primaryKey: "true",primaryKeyName: "FND_COMPANY_LIMIT_PK")
            }
            column(name:"LIMIT_TYPE",type:"varchar(60)",remarks:"授信类型")
            column(name:"COMPANY_ID",type:"bigint",remarks:"商户ID")
            column(name:"LIMIT_COMPANY_ID",type:"bigint",remarks:"资方ID")
            column(name:"LIMIT_DATE",type:"datetime",remarks:"授信日期")
            column(name:"GOOD_ID",type:"bigint",remarks:"商品ID")
            column(name:"ORDER_ID",type:"bigint",remarks:"订单ID")
            column(name:"LIMIT_AMOUNT",type:"decimal",remarks:"授信额度")
            column(name:"BALANCE",type:"decimal",remarks:"可用额度")
            column(name:"LIMIT_NOTE",type:"varchar(200)",remarks:"备注")
            column(name:"ENABLED_FLAG",type:"varchar(1)",remarks:"启用标识")
            column(name:"START_DATE",type:"datetime",remarks:"有效期从")
            column(name:"END_DATE",type:"datetime",remarks:"有效期至")
            column(name:"REMARK",type:"clob",remarks:"备注说明")
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
        createIndex(tableName:"FND_COMPANY_LIMIT",indexName:"FND_COMPANY_LIMIT_N1"){
            column(name: "COMPANY_ID")
        }
        createIndex(tableName:"FND_COMPANY_LIMIT",indexName:"FND_COMPANY_LIMIT_N2"){
            column(name: "LIMIT_COMPANY_ID")
        }

    }


    changeSet(author:"Admin", id: "2019-04-23_FND_LIMIT_DETAIL"){
        if(mhi.isDbType('oracle') || mhi.isDbType('hana')){
            createSequence(sequenceName:'FND_LIMIT_DETAIL_S', startValue:"10001")
        }
        createTable(tableName:"FND_LIMIT_DETAIL"){
            column(name:"DETAIL_ID",type:"bigint",autoIncrement:"true",startWith:"10001",remarks:"表ID，主键，供其他表做外键"){
                constraints(nullable:"false",primaryKey: "true",primaryKeyName: "FND_LIMIT_DETAIL_PK")
            }
            column(name:"COMPANY_ID",type:"bigint",remarks:"商户ID")
            column(name:"LIMIT_ID",type:"bigint",remarks:"授信ID")
            column(name:"DEAL_TYPE",type:"varchar(60)",remarks:"交易类型")
            column(name:"ASSIGN_ID",type:"bigint",remarks:"采购分配ID")
            column(name:"ORDER_ID",type:"bigint",remarks:"订单ID")
            column(name:"CASHFLOW_ID",type:"bigint",remarks:"现金流ID")
            column(name:"DR_AMOUNT",type:"decimal",remarks:"借方金额")
            column(name:"CR_AMOUNT",type:"decimal",remarks:"贷方金额")
            column(name:"DEAL_DATE",type:"datetime",remarks:"交易日期")
            column(name:"BALANCE",type:"decimal",remarks:"余额")
            column(name:"DEAL_NOTE",type:"varchar(200)",remarks:"摘要")
            column(name:"REMARK",type:"clob",remarks:"备注说明")
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
        createIndex(tableName:"FND_LIMIT_DETAIL",indexName:"FND_LIMIT_DETAIL_N1"){
            column(name: "LIMIT_ID")
        }
        createIndex(tableName:"FND_LIMIT_DETAIL",indexName:"FND_LIMIT_DETAIL_N2"){
            column(name: "ORDER_ID")
            column(name: "CASHFLOW_ID")
        }

    }

}
