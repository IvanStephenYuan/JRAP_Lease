package com.jingrui.jrap.excel.service.impl;

import com.jingrui.jrap.core.annotation.MultiLanguage;
import com.jingrui.jrap.core.components.ApplicationContextHelper;
import com.jingrui.jrap.excel.annotation.ExcelJoinColumn;
import com.jingrui.jrap.excel.util.TableUtils;
import com.jingrui.jrap.generator.service.impl.FileUtil;
import com.jingrui.jrap.mybatis.common.BaseMapper;
import com.jingrui.jrap.mybatis.entity.EntityColumn;
import com.jingrui.jrap.system.dto.Language;
import com.jingrui.jrap.system.mapper.LanguageMapper;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * @author jialong.zuo@jingrui.com on 2017/11/21.
 */
public class ExcelSheetStrategy {

    protected Connection connection;

    protected String tableName;

   // protected BaseMapper baseMapper;

    protected List<String> columnName;

    protected Class dtoClass;

    protected List<Field> excelJoinColumns;

    //字段类型mapping表
    protected Map<String, Class> typeMapping = new HashMap<>();

    //替换表Mapping 减少查询数据库次数
    protected Map<String, Object> translateCellMap =new HashMap<>();

    protected List<Language> languages;

    public  enum TranslateType {alterColumnTojoinColumn, joinColumnToAlterCloumn}

    public  enum MapperType {Select, Insert}



    public ExcelSheetStrategy( Connection connection,String tableName) throws ClassNotFoundException {
        this.tableName=tableName;
        this.connection = connection;
        dtoClass = TableUtils.getTableClass(tableName);
        excelJoinColumns = TableUtils.getExcelJoinColumns(tableName);
        Set<EntityColumn> entityColumns = TableUtils.getAllColumns(tableName);
        entityColumns.stream().forEach(v -> {
            typeMapping.put(FileUtil.columnToCamel(v.getColumn()), v.getJavaType());
        });
        if(dtoClass.isAnnotationPresent(MultiLanguage.class)){
            languages= ApplicationContextHelper.getApplicationContext().getBean(LanguageMapper.class).selectAll();
        }
    }

    /**
     * @param column
     * @param cell
     * @param tls
     * @return 是否处理了多语言字段
     */
    protected boolean translateLanguageCell(String column, String cell, Map tls) throws InvocationTargetException, IllegalAccessException {
        for(Language language:languages){
            if(column.endsWith(":"+language.getLangCode())){
                //String col= FileUtil.columnToCamel(column.substring(0,column.length()-language.getLangCode().length()-1));
                String col = column.substring(0,column.length()-language.getLangCode().length()-1);
                Map<String,String> map= (Map<String, String>) Optional.ofNullable(tls.get(col)).orElseGet(()->{
                    Map<String,String> map2=new HashMap<>();
                    tls.put(col,map2);
                    return map2;
                });
                map.put(language.getLangCode(),cell);
                //BeanUtils.setProperty(dto,col,cell);
                return true;
            }
        }
        return false;
    }

    /** 将dto中包含@ExcelJoinColumn注解的行替换为其所在头表中对应的数据
     * @param column
     * @param cell
     * @return
     * @throws SQLException
     */
    protected Object translateCellValue(String column, String cell,TranslateType translateType,Object dto) throws SQLException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, NoSuchFieldException {
        Object rs=cell;
        if(null == cell || "" == cell){
            return null;
        }
        for (Field field : excelJoinColumns) {
            ExcelJoinColumn ann = field.getAnnotationsByType(ExcelJoinColumn.class)[0];

            if (field.getName().equalsIgnoreCase(column)) {
                if(null!=dto) {
                    cell = BeanUtils.getProperty(dto, column);
                }

                if (translateCellMap.containsKey(column+"-"+cell)) {
                    return translateCellMap.get(column+"-"+cell);
                }
                String joinColumn=ann.JoinColumn();
                String alternateColumn=ann.AlternateColumn();
                String joinTable = TableUtils.getTable(ann.JoinTable()).getName();
                String sql;
                if(translateType == TranslateType.alterColumnTojoinColumn){
                    sql = "select " + FileUtil.camelToColumn(joinColumn) + " from " + joinTable + " where " + FileUtil.camelToColumn(alternateColumn) + " = '" + cell + "'";

                }else{
                    sql = "select " + FileUtil.camelToColumn(alternateColumn) + " from " + joinTable + " where " + FileUtil.camelToColumn(joinColumn) + " = '" + cell + "'";
                }
                PreparedStatement stmt1 = connection.prepareStatement(sql);
                ResultSet set = stmt1.executeQuery();
                if(set.next()){
                    rs = set.getObject(1);
                    translateCellMap.put(column+"-"+cell, rs);
                    changeTypeMapping(column,ann.AlternateColumn(),ann.JoinTable());
                }
                stmt1.close();
                break;
            }
        }
        return rs;
    }

    /**
     * @param column 讲exceljoin字段的类型映射修改为替换字段的类型
     * @param cls
     * @throws NoSuchFieldException
     */
    public void changeTypeMapping(String column,String alternateColumn,Class cls) throws NoSuchFieldException {

        typeMapping.replace(column,cls.getDeclaredField(alternateColumn).getType());
    }


    /**
     * 删除缓存数据（应在每个表结束后执行）
     */
    protected void cleanData() {
        tableName = null;
        //baseMapper = null;
        columnName = null;
        dtoClass = null;
        excelJoinColumns = null;
        typeMapping = new HashMap<>();
        translateCellMap =new HashMap<>();
    }
}
