package com.jingrui.jrap.excel.service.impl;

import com.jingrui.jrap.excel.util.TableUtils;
import com.jingrui.jrap.mybatis.common.BaseMapper;

import java.sql.Connection;
import java.util.List;

/**
 * @author jialong.zuo@jingrui.com on 2017/11/21.
 */
public class ExportStrategy extends ExcelSheetStrategy {

    BaseMapper queryMapper;

    public ExportStrategy(Connection connection, String tableName) throws ClassNotFoundException {
        super(connection,tableName);
        queryMapper = TableUtils.getBaseMapperByType(TableUtils.getTableClass(tableName), MapperType.Select);

    }

    public List<Object> query(Object dto){
        return queryMapper.select(dto);
    }

    public String getCellType(String column){

        if(typeMapping.containsKey(column)) {
            return typeMapping.get(column).getSimpleName();
        }

        return null;
    }

    @Override
    protected void cleanData() {
        super.cleanData();
        queryMapper=null;
    }
}
