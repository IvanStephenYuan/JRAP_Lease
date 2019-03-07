package com.jingrui.jrap.excel.util;

import com.jingrui.jrap.core.annotation.Children;
import com.jingrui.jrap.core.annotation.MultiLanguage;
import com.jingrui.jrap.core.annotation.MultiLanguageField;
import com.jingrui.jrap.core.components.ApplicationContextHelper;
import com.jingrui.jrap.core.impl.RequestHelper;
import com.jingrui.jrap.excel.annotation.ExcelJoinColumn;
import com.jingrui.jrap.excel.service.impl.ExcelSheetStrategy;
import com.jingrui.jrap.generator.service.impl.FileUtil;
import com.jingrui.jrap.mybatis.common.BaseMapper;
import com.jingrui.jrap.mybatis.entity.EntityColumn;
import com.jingrui.jrap.mybatis.entity.EntityTable;
import com.jingrui.jrap.mybatis.mapperhelper.EntityHelper;
import com.jingrui.jrap.mybatis.mapperhelper.MapperHelper;
import com.jingrui.jrap.mybatis.mapperhelper.MapperTemplate;
import com.jingrui.jrap.mybatis.spring.MapperScannerConfigurer;
import com.jingrui.jrap.system.dto.BaseDTO;
import com.jingrui.jrap.system.dto.Language;
import com.jingrui.jrap.system.dto.Prompt;
import com.jingrui.jrap.system.mapper.LanguageMapper;
import com.jingrui.jrap.system.mapper.PromptMapper;
import org.apache.commons.lang.LocaleUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.context.MessageSource;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author jialong.zuo@jingrui.com on 2017/11/15.
 */
public class TableUtils {

    public static List<String> STDWHO_COLUMN = new ArrayList<String>();

    static {
        STDWHO_COLUMN.add(BaseDTO.FIELD_CREATED_BY);
        STDWHO_COLUMN.add(BaseDTO.FIELD_OBJECT_VERSION_NUMBER);
        STDWHO_COLUMN.add(BaseDTO.FIELD_PROGRAM_ID);
        STDWHO_COLUMN.add(BaseDTO.FIELD_REQUEST_ID);
        STDWHO_COLUMN.add(BaseDTO.FIELD_CREATION_DATE);
        STDWHO_COLUMN.add(BaseDTO.FIELD_LAST_UPDATE_DATE);
        STDWHO_COLUMN.add(BaseDTO.FIELD_LAST_UPDATE_LOGIN);
        STDWHO_COLUMN.add(BaseDTO.FIELD_LAST_UPDATED_BY);
    }


    /**
     * @param tableName
     * @return 通过表名获取表的映射类
     */
    public static Class getTableClass(String tableName){
        return EntityHelper.getEntityTable(tableName).getEntityClass();
    }

    public static EntityTable getTable(Class dto){
        return EntityHelper.getEntityTable(dto);
    }

    /**
     * @param tableName
     * @return 获取ExcelJoinColumn标注的列
     */
    public static List<Field> getExcelJoinColumns(String tableName){
        Class tableClass= getTableClass(tableName);
        return getExcelJoinColumn(tableClass);
    }

    /**
     * @param tableDto
     * @return 获取ExcelJoinColumn标注的列
     */
    public static List<Field> getExcelJoinColumn(Class tableDto){
        return Arrays.stream(tableDto.getDeclaredFields()).filter(v->{
            return v.isAnnotationPresent(ExcelJoinColumn.class);
        }).collect(Collectors.toList());
    }

    /**
     * @param tableDto
     * @return 获取ExcelJoinColumn标注的列
     */
    public static List<Field> getMultiLanguageColumn(Class tableDto){
        if(!tableDto.isAnnotationPresent(MultiLanguage.class)){
            return new ArrayList<>();
        }
        return Arrays.stream(tableDto.getDeclaredFields()).filter(v->{
            return v.isAnnotationPresent(MultiLanguageField.class);
        }).collect(Collectors.toList());
    }

    /**
     * @param dto
     * @return 获取Children标注的列
     */
    public static List<Field> getChildrenColumn(Class dto){
        return Arrays.stream(dto.getDeclaredFields()).filter(v->{
            return v.isAnnotationPresent(Children.class);
        }).collect(Collectors.toList());
    }

    /**
     * @param dtoClass
     * @return 通过dto获取其InsertMapper
     * @throws ClassNotFoundException
     */
    public static BaseMapper getBaseMapperByType(Class dtoClass, ExcelSheetStrategy.MapperType mapperType) throws ClassNotFoundException {
       String uri;
        if(mapperType == ExcelSheetStrategy.MapperType.Insert){
            uri="com.jingrui.jrap.mybatis.common.base.insert.InsertMapper";
        }else{
            uri="com.jingrui.jrap.mybatis.common.base.select.SelectAllMapper";
        }
        List<MapperHelper> lists=new ArrayList<>();
         ApplicationContextHelper.getApplicationContext()
                .getBeansOfType(MapperScannerConfigurer.class).forEach((k,v)->{lists.add(v.getMapperHelper());});
        MapperTemplate template=null;

        for(MapperHelper helper:lists){
            template=helper.getRegisterMapper(Class.forName(uri));
            if(template !=null){
                break;
            }
        }

        Map map=template.getEntityClassMap();
        if(!map.containsValue(dtoClass)){
            throw new ClassNotFoundException();
        }
        String key="";
        Set<String> kset=map.keySet();
        for(String ks:kset){
            if(map.get(ks).equals(dtoClass)){
                key=ks;
                break;
            }
        }
        if(""==key) {
            throw new ClassNotFoundException();
        }
        return (BaseMapper) ApplicationContextHelper.getApplicationContext().getBean(Class.forName(key.substring(0,key.lastIndexOf("."))));
    }

    public static Set getAllColumns(String tableName){
        return EntityHelper.getEntityTable(tableName).getAllColumns();
    }


    /**
     * @param column
     * @return 根据column 获取title
     */
    public static List<String> getTitle(List<String> column,Class dto){
        String lang= RequestHelper.getCurrentRequest(true).getLocale();
//        List<String> languageColumns=TableUtils.getMultiLanguageColumn(dto).stream().map(v->v.getName()).collect(Collectors.toList());
//        Map<String,String> languagePrompt=new HashMap<>();
//        if(languageColumns.size()!=0) {
//            List<Language> languages = ApplicationContextHelper.getApplicationContext().getBean(LanguageMapper.class).selectAll();
//            languages.stream().forEach(v->{
//                languagePrompt.put(v.getLangCode(),v.getDescription());
//            });
//        }
//
        MessageSource messageSource=ApplicationContextHelper.getApplicationContext()
                .getBean(MessageSource.class);
       return column.stream().map(v->{
            Prompt prompt=new Prompt();
            String title=dto.getSimpleName().toLowerCase()+"."+ v.toLowerCase();
            prompt.setPromptCode(title);
            prompt.setLang(lang);
            title=messageSource.getMessage(title,null, LocaleUtils.toLocale(lang));
            return title;
        }).collect(Collectors.toList());

    }

    /**
     * @param tableEntity
     * @return 获取column
     */
    public static List<String> getColumn(EntityTable tableEntity,List<String> containColumns){
        return  getColumn(tableEntity,containColumns,true);
    }

    /**
     * @param tableEntity
     * @return 获取column
     */
    public static List<String> getColumn(EntityTable tableEntity,List<String> containColumns , boolean containStdWho) {
        Class dto=tableEntity.getEntityClass();
        String dtoName=dto.getSimpleName();
        List<Field> joinColumn=TableUtils.getExcelJoinColumn(dto);
        List<Field> languageColumns=TableUtils.getMultiLanguageColumn(dto);
        List<String> column=new ArrayList<>();
        Set<EntityColumn>  allColumns =  tableEntity.getAllColumns();
        if(null!=containColumns) {
            allColumns.stream().map(m -> FileUtil.columnToCamel(m.getColumn())).filter(u -> containColumns.contains(dtoName + "." + u)).forEach(v -> column.add(v));
        }else {
            allColumns.stream().map(m -> FileUtil.columnToCamel(m.getColumn())).forEach(v -> column.add(v));
        }
//        joinColumn.forEach(v->{
//            ExcelJoinColumn excelJoinColumn = v.getAnnotationsByType(ExcelJoinColumn.class)[0];
//            if (column.contains(v.getName())) {
//                Collections.replaceAll(column,v.getName(),excelJoinColumn.AlternateColumn());
//            }
//        });

        if(languageColumns.size()!=0){
            if(null == containColumns) {
                List<Language> languages = ApplicationContextHelper.getApplicationContext().getBean(LanguageMapper.class).selectAll();
                languageColumns.forEach(v -> {
                    if (column.contains(v.getName())) {
                        languages.stream().forEach(u -> column.add(v.getName() + ":" + u.getLangCode()));
                    }
                });
            }
        }
        if(!containStdWho){
            column.removeAll(STDWHO_COLUMN);
        }

        return column;
    }



    /**通过tableName查询所有关联表
     * @param parentTableName 基表表明
     * @param tables 递归查询表名并将其放入tables集合中
     */
    public static void getAllChildrenTable(Class parentTableName, List<Class>tables) {
        tables.add(parentTableName);
        getChildren(parentTableName).forEach(v->getAllChildrenTable(v,tables));

      //  Class entityTable = TableUtils.getTableClass(parentTableName);
//        List<Field> childrenField = TableUtils.getChildrenColumn(entityTable);
//        if (childrenField.size() != 0) {
//            for (int i = 0; i < childrenField.size(); i++) {
//                Type type = childrenField.get(i).getGenericType();
//                Class entityClass;
//                if (type instanceof ParameterizedType) {
//                    ParameterizedType parameterizedType = (ParameterizedType) type;
//                    entityClass = (Class<T>) parameterizedType.getActualTypeArguments()[0];
//                } else {
//                    entityClass = (Class) type;
//                }
//                String childrenTableName = EntityHelper.getEntityTable(entityClass).getName();
//                getAllChildrenTable(childrenTableName,tables);
//            }
//        }
    }

    public static List<Class> getChildren(Class entityTable) {
        List<Class>tables=new ArrayList<>();
        List<Field> childrenField = TableUtils.getChildrenColumn(entityTable);
        if (childrenField.size() != 0) {
            for (int i = 0; i < childrenField.size(); i++) {
                Type type = childrenField.get(i).getGenericType();
                Class entityClass;
                if (type instanceof ParameterizedType) {
                    ParameterizedType parameterizedType = (ParameterizedType) type;
                    entityClass = (Class<T>) parameterizedType.getActualTypeArguments()[0];
                } else {
                    entityClass = (Class) type;
                }
                tables.add(entityClass);
            }
        }
        return tables;
    }

}
