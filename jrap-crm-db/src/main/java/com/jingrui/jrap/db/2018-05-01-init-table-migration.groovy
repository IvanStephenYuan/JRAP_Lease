import com.jingrui.jrap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()

databaseChangeLog(logicalFilePath:"2018-05-01-init-table-migration.groovy"){

    changeSet(author:"Admin", id: "2018-04-08_AFD_CUSTOMER"){
        if(mhi.isDbType('oracle')){
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
            column(name:"ENABLE_FLAG",type:"varchar(1)",defaultValue:"Y",remarks:"是否启用"){
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
}
