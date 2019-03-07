#!/bin/bash

# <Resource auth="Container" 
# driverClassName="com.mysql.jdbc.Driver" 
# url="jdbc:mysql://localhost:3306/hap_dev" 
# name="jdbc/hap_dev" 
# type="javax.sql.DataSource" 
# username="hap_dev" 
# password="hap_dev"/>
add_mysql(){
    if [ -z ${MYSQL_HOST} ]; then
        error "MYSQL_HOST 数据库链接地址未设置！"
    fi
    if [ -z ${MYSQL_PORT} ]; then
        ignore "MYSQL_PORT 数据库链接地址端口号未设置，使用默认值3306。"
    fi
    if [ -z ${MYSQL_DB} ]; then
        error "MYSQL_DB 数据库名未设置！"
    fi
    if [ -z ${MYSQL_USER} ]; then
        error "MYSQL_USER 数据库用户名未设置！"
    fi
    if [ -z ${MYSQL_PASS} ]; then
        error "MYSQL_PASS 数据库用户密码未设置！"
    fi
    xmlstarlet ed -L -i '/Context/Resource' -t attr -n "driverClassName" -v "com.mysql.jdbc.Driver" conf/context.xml
    xmlstarlet ed -L -i '/Context/Resource' -t attr -n "url" -v "jdbc:mysql://${MYSQL_HOST}:${MYSQL_PORT:-"3306"}/${MYSQL_DB}" conf/context.xml
    xmlstarlet ed -L -i '/Context/Resource' -t attr -n "username" -v "${MYSQL_USER}" conf/context.xml
    xmlstarlet ed -L -i '/Context/Resource' -t attr -n "password" -v "${MYSQL_PASS}" conf/context.xml
}

# <Resource auth="Container" 
# driverClassName="oracle.jdbc.driver.OracleDriver" 
# url="jdbc:oracle:thin:@192.168.115.136:1521:HAP" 
# name="jdbc/hap_dev" 
# type="javax.sql.DataSource"  
# username="hap_dev" 
# password="hap_dev"/>
add_oracle(){
    if [ -z ${ORACLE_HOST} ]; then
        error "ORACLE_HOST 数据库链接地址未设置！"
    fi
    if [ -z ${ORACLE_PORT} ]; then
        ignore "ORACLE_PORT 数据库链接地址端口号未设置，使用默认值1521。"
    fi
    if [ -z ${ORACLE_SID} ]; then
        error "ORACLE_SID 数据库SID未设置！"
    fi
    if [ -z ${ORACLE_USER} ]; then
        error "ORACLE_USER 数据库用户名未设置！"
    fi
    if [ -z ${ORACLE_PASS} ]; then
        error "ORACLE_PASS 数据库用户密码未设置！"
    fi
    xmlstarlet ed -L -i '/Context/Resource' -t attr -n "driverClassName" -v "oracle.jdbc.driver.OracleDriver" conf/context.xml
    xmlstarlet ed -L -i '/Context/Resource' -t attr -n "url" -v "jdbc:oracle:thin:@${ORACLE_HOST}:${ORACLE_PORT:-"1521"}:${ORACLE_SID}" conf/context.xml
    xmlstarlet ed -L -i '/Context/Resource' -t attr -n "username" -v "${ORACLE_USER}" conf/context.xml
    xmlstarlet ed -L -i '/Context/Resource' -t attr -n "password" -v "${ORACLE_PASS}" conf/context.xml
}

# <Resource auth="Container" 
# driverClassName="com.microsoft.sqlserver.jdbc.SQLServerDriver" 
# url="jdbc:sqlserver://10.211.55.6:1433; 
# name="jdbc/hap_dev" 
# type="javax.sql.DataSource"  
# DatabaseName=hap_dev"  
# username="hap" 
# password="handhapdev"/>
add_sqlserver(){
    if [ -z ${SQLSERVER_HOST} ]; then
        error "SQLSERVER_HOST 数据库链接地址未设置！"
    fi
    if [ -z ${SQLSERVER_PORT} ]; then
        ignore "SQLSERVER_PORT 数据库链接地址端口号未设置，使用默认值1433。"
    fi
    if [ -z ${SQLSERVER_DB} ]; then
        error "SQLSERVER_DB 数据库名未设置！"
    fi
    if [ -z ${SQLSERVER_USER} ]; then
        error "SQLSERVER_USER 数据库用户名未设置！"
    fi
    if [ -z ${SQLSERVER_PASS} ]; then
        error "SQLSERVER_PASS 数据库用户密码未设置！"
    fi
    xmlstarlet ed -L -i '/Context/Resource' -t attr -n "driverClassName" -v "com.microsoft.sqlserver.jdbc.SQLServerDriver" conf/context.xml
    xmlstarlet ed -L -i '/Context/Resource' -t attr -n "url" -v "jdbc:sqlserver://${SQLSERVER_HOST}:${SQLSERVER_PORT:-"1433"}" conf/context.xml
    xmlstarlet ed -L -i '/Context/Resource' -t attr -n "DatabaseName" -v "${SQLSERVER_DB}" conf/context.xml
    xmlstarlet ed -L -i '/Context/Resource' -t attr -n "username" -v "${SQLSERVER_USER}" conf/context.xml
    xmlstarlet ed -L -i '/Context/Resource' -t attr -n "password" -v "${SQLSERVER_PASS}" conf/context.xml
}

# <Resources cachingAllowed="true" cacheMaxSize="100000" />
add_resources(){
    xmlstarlet ed -L -a '/Context/*[2]' -t elem -n "Resources" conf/context.xml
    xmlstarlet ed -L -i '/Context/Resources' -t attr -n "cachingAllowed" -v ${cachingAllowe:-"true"} conf/context.xml
    xmlstarlet ed -L -i '/Context/Resources' -t attr -n "cacheMaxSize" -v ${cacheMaxSize:-"100000"} conf/context.xml
    xmlstarlet ed -L -a '/Context/*[3]' -t elem -n "Resource" conf/context.xml
    xmlstarlet ed -L -i '/Context/Resource' -t attr -n "auth" -v "Container" conf/context.xml
    xmlstarlet ed -L -i '/Context/Resource' -t attr -n "type" -v "javax.sql.DataSource"  conf/context.xml
    xmlstarlet ed -L -i '/Context/Resource' -t attr -n "name" -v `prop "^db.jndiName" | awk '{print substr($0,15)}'` conf/context.xml
}

# 取出properties文件信息
prop(){
    grep "${1}" webapps/ROOT/WEB-INF/classes/config.properties | cut -d'=' -f2 | sed 's/\r//'
}

# 报错公共方法
error(){
    echo "Error" "$*"
    exit 1
}

# 警告公共方法
ignore(){
    echo "Ignore" "$*"
}
#------------------------------------------------------------------------------------------------------------------#
#                                                      Begin                                                       #
#------------------------------------------------------------------------------------------------------------------#

if [ -z ${REDIS_IP} ]; then
    error "REDIS_IP 数据库链接地址未设置！"
fi
if [ -z ${REDIS_PORT} ]; then
    ignore "REDIS_PORT 数据库链接地址端口号未设置，使用默认值6379。"
fi
if [ -z ${REDIS_DB} ]; then
    ignore "REDIS_DB 数据库INDEX未设置！使用默认值8。"
fi

# 替换Redis数据库链接数据
sed -i 's/^redis.ip=.*$/redis.ip='${REDIS_IP}'/g' webapps/ROOT/WEB-INF/classes/config.properties
sed -i 's/^redis.port=.*$/redis.port='${REDIS_PORT:-"6379"}'/g' webapps/ROOT/WEB-INF/classes/config.properties
sed -i 's/^redis.db=.*$/redis.db='${REDIS_DB:-"8"}'/g' webapps/ROOT/WEB-INF/classes/config.properties

# 添加数据库基本配置
add_resources

if [ -z ${DB_TYPE} ]; then
    ignore "DB_TYPE 数据库类型未设置！使用config.properties中设置的$(echo `prop "^db.type"` | tr '[A-Z]' '[a-z]')。"
    DB_TYPE=$(echo `prop "^db.type"`| tr '[A-Z]' '[a-z]')
fi
case ${DB_TYPE} in
    'mysql') add_mysql
    ;;
    'oracle') add_oracle
    ;;
    'sqlserver') add_sqlserver
    ;;
    *) error "未知的数据库类型，请检查后重试！"
    ;;
esac

# 启动Tomcat容器
catalina.sh run