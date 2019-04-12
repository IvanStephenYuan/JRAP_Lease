#!/bin/bash

# <Resource auth="Container" 
# driverClassName="com.mysql.jdbc.Driver" 
# url="jdbc:mysql://localhost:3306/jrap_dev"
# name="jdbc/jrap_dev"
# type="javax.sql.DataSource" 
# username="jrap_dev"
# password="jrap_dev"/>
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