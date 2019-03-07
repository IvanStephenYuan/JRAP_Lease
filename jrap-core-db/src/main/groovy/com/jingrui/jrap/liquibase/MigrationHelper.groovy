package com.jingrui.jrap.liquibase

import com.jingrui.jrap.db.util.DbType
import org.springframework.core.io.support.PathMatchingResourcePatternResolver

/**
 * Created by hailor on 16/4/11.
 */
class MigrationHelper {

    private static final INSTANCE = new MigrationHelper()

    static getInstance() { return INSTANCE }

    private MigrationHelper() {}

    //数据文件存储路径
    def copyToFolderPath = ""

    // 当前数据库的类型是 DbType
    def currentDbType = null


    def printClassPath(classLoader) {
        println "$classLoader"
        classLoader.getURLs().each { url ->
            println "- ${url.toString()}"
        }
        if (classLoader.parent) {
            printClassPath(classLoader.parent)
        }
    }

    def dbmigrate = { dbType="mysql",loadPlugins= ["com/jingrui/jrap"], migrateType = ["table"] ->
        def projectName = "jrapdbmigration"
        def dataMigrations = [:]
        def tableMigrations = []
        def dataScriptMigrations = []
        currentDbType = DbType.getDbType(dbType)
        copyToFolderPath = System.getProperty("java.io.tmpdir") + File.separator + projectName
        if (new File(copyToFolderPath).exists()) {
            new File(copyToFolderPath).deleteDir()
        }
        new File(copyToFolderPath).mkdirs()

        printClassPath this.class.classLoader

        System.properties.each { k, v ->
            println "$k = $v"
        }

        loadPlugins.each {
            //加载主项目Migration
            HashMap<String, ArrayList> result = prepare(it, dbType, migrateType)
            tableMigrations = tableMigrations + result.tableMigrations
            dataMigrations = dataMigrations + result.dataMigrations
            dataScriptMigrations = dataScriptMigrations+result.dataScriptMigrations
        }

        def dataMigrationsStr = dataMigrations.collect{key,value-> "${key}@${value}"}


        (tableMigrations+dataScriptMigrations).each {
            println("Loading file:" + it)
            include(file: it)
        }


    }


    private void copyFile(def fromStream, String to) {
        File toFile = new File(to);
        File parent = toFile.getParentFile();
        if (!parent.exists() && !parent.mkdirs()) {
            throw new IllegalStateException("Couldn't create dir: " + parent);
        }
        toFile << fromStream
        if (!toFile.exists()) {
            throw new IllegalStateException("Couldn't copy file to: " + to);
        }
    }

    private String getTargetPath(String origin) {
        def temp = origin.split(".jar!")
        if(temp.length>1){
            return temp[1]
        }else{
            return origin.split(".tmp!")[1]
        }
    }


    private HashMap<String, ArrayList> prepare(String plugin, String dbType, ArrayList<String> migrateType) {
        def dataMigrations = [:]
        def dataScriptMigrations = []
        def tableMigrations = []

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        if ((migrateType.contains("table") || migrateType.contains("all"))) {
            resolver.getResources("classpath*:" + plugin + "/**/db/" + "/*-table-migration.groovy").sort{a,b->
                return a.getFilename().compareTo(b.getFilename())
            }.each {
                def targetPath = copyToFolderPath + getTargetPath(it.getURL().getPath())
                println("Copy file:" + it+" to:"+targetPath)
                copyFile(it.getInputStream(), targetPath)
                tableMigrations << targetPath
            }
        }

        if ((migrateType.contains("patch") || migrateType.contains("all"))) {

            resolver.getResources("classpath*:" + plugin + "/**/db/patch/**/*.sql").sort{a,b->
                return a.getFilename().compareTo(b.getFilename())
            }.each {
                def targetPath = copyToFolderPath + getTargetPath(it.getURL().getPath())
                println("Copy SQL file:" + it+" to:"+targetPath)
                copyFile(it.getInputStream(), targetPath)
                dataMigrations[targetPath] = plugin
            }

            resolver.getResources("classpath*:" + plugin + "/**/db/" + "/*-patch.groovy").sort{a,b->
                return a.getFilename().compareTo(b.getFilename())
            }.each {
                def targetPath = copyToFolderPath + getTargetPath(it.getURL().getPath())
                println("Copy file:" + it+" to:"+targetPath)
                copyFile(it.getInputStream(), targetPath)
                dataScriptMigrations << targetPath
            }
        }

        if ((migrateType.contains("data") || migrateType.contains("all"))) {

            resolver.getResources("classpath*:" + plugin + "/**/db/data/**/*.sql").sort{a,b->
                return  a.getFilename().compareTo(b.getFilename())
            }.each {
                def targetPath = copyToFolderPath + getTargetPath(it.getURL().getPath())
                println("Copy SQL file:" + it+" to:"+targetPath)
                copyFile(it.getInputStream(), targetPath)
                dataMigrations[targetPath] = plugin
            }

            resolver.getResources("classpath*:" + plugin + "/**/db/data/*.xlsx").sort{a,b->
                return a.getFilename().compareTo(b.getFilename())
            }.each {
                def targetPath = copyToFolderPath + getTargetPath(it.getURL().getPath())
                println("Copy Excel file:" + it+" to:"+targetPath)
                copyFile(it.getInputStream(), targetPath)
                dataMigrations[targetPath] = plugin
            }

            resolver.getResources("classpath*:" + plugin + "/**/db/" + "/*-data-migration.groovy").sort{a,b->
                return a.getFilename().compareTo(b.getFilename())
            }.each {
                def targetPath = copyToFolderPath + getTargetPath(it.getURL().getPath())
                println("Copy file:" + it+" to:"+targetPath)
                copyFile(it.getInputStream(), targetPath)
                dataScriptMigrations << targetPath
            }
        }

        return [dataScriptMigrations:dataScriptMigrations,tableMigrations: tableMigrations, dataMigrations: dataMigrations]
    }

    //工具函数
    String dataPath(String path) {
        (new File(copyToFolderPath + File.separator + path)).path
    }

    String dataPathWithType(String path) {
        (new File(copyToFolderPath + File.separator+currentDbType+ File.separator + path)).path
    }

    Boolean isDbType(String dbType) {
        currentDbType.getValue().equalsIgnoreCase(dbType);
    }

    String dbType() {
        currentDbType.getValue()
    }

    DbType getDbType() {
        currentDbType
    }
}
