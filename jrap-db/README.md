# Liquibase功能实现及测试说明

1. 在主项目hap的中添加了com/jingrui/jrap/db/liquibase.groovy这个入口脚本
2. 不同的数据库类型需求可以在com/jingrui/jrap/db/liquibase.groovy中修改
3. 所有数据库相关的内容位于db子项目中
4. db子项目的liquibase脚本位于com/jingrui/jrap/db目录下,脚本分为两类
    a. 建表 以table-migration.groovy结尾
    b. 初始化数据脚本,以data-migration.groovy结尾
5. db下的data可以存放各数据库的建表及初始化数据SQL,各类型数据库的脚本文件数量,及名称必须相同.


## 测试

### Oracle

修改com/jingrui/jrap/db/liquibase.groovy的类型为oracle,然后执行(db的各参数请根据实际情况修改)
mvn process-resources -D skipLiquibaseRun=false -D db.driver=oracle.jdbc.driver.OracleDriver -D db.url=jdbc:oracle:thin:@localhost:1521:xe -Ddb.user=jrap -Ddb.password=handhand

### Mysql

修改com/jingrui/jrap/db/liquibase.groovy的类型为mysql,然后执行(db的各参数请根据实际情况修改)
mvn process-resources -D skipLiquibaseRun=false -D db.driver=com.mysql.jdbc.Driver -D db.url=jdbc:mysql://127.0.0.1:3306/hap2 -Ddb.user=root -Ddb.password=root

### Hana
mvn process-resources -D skipLiquibaseRun=false -D db.driver=com.sap.db.jdbc.Driver -D db.url=jdbc:sap://192.168.11.28:35215/HAPTRP3 -Ddb.user=HAPTRP3 -Ddb.password=Hap123456
