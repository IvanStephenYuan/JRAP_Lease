import com.jingrui.jrap.liquibase.MigrationHelper

//表结构
databaseChangeLog(logicalFilePath: "com/jingrui/jrap/db/liquibase.groovy") {
    def migrationHelper = MigrationHelper.getInstance()
    migrationHelper.dbmigrate.delegate = delegate

    // 默认数据库类型是 mysql
    def dbType = "mysql"
    // 现在支持自动根据 db.url 参数检测数据库类型
    def dburl = System.properties.getProperty("db.url")
    dburl = dburl == null ? "" : dburl
    /*if (dburl.startsWith("jdbc:oracle")) {
        dbType = DbType.ORACLE
    } else if (dburl.startsWith("jdbc:sqlserver")) {
        dbType = DbType.SQL_SERVER
    }else if(dburl.startsWith("jdbc:postgresql")){
        dbType = DbType.POSTGRE_SQL
    }*/

    // 根据 db url 设置数据库类型
    if (dburl != "") {
        String[] sub = dburl.split(":")
        dbType = sub[1]
    }
    //"dbType"表示数据库类型
    //["com/jingrui/jrap"] 表示本次要执行脚本的扫描路径,可以添加多个
    //["table", "data"] 表示在建表的同时初始化数据
    migrationHelper.dbmigrate(dbType, ["com/jingrui/jrap"], ["table", "data", "patch"])


}
