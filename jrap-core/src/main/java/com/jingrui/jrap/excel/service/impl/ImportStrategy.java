package com.jingrui.jrap.excel.service.impl;

import com.jingrui.jrap.excel.ExcelException;
import com.jingrui.jrap.excel.util.TableUtils;
import com.jingrui.jrap.generator.service.impl.FileUtil;
import com.jingrui.jrap.mybatis.common.BaseMapper;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author jialong.zuo@jingrui.com on 2017/11/16.
 */
public class ImportStrategy extends ExcelSheetStrategy {

    protected BaseMapper baseMapper;

    private Logger logger = LoggerFactory.getLogger(getClass());


    public ImportStrategy(Connection connection, String tableName) throws SQLException, ClassNotFoundException {
        super(connection,tableName);
        baseMapper = TableUtils.getBaseMapperByType(TableUtils.getTableClass(tableName), MapperType.Insert);
    }


    /** 在获取晚excel每行数据后执行
     * @param curRow  当前的行数
     * @param rowList excel每行数据
     * @throws ExcelException
     */
    public void optRow(int curRow, List<String> rowList) throws ExcelException {
        if (curRow == 1) {
            columnName = new ArrayList<>(rowList);
        } else if(curRow>1) {
            try {
                Object dto = dtoClass.newInstance();
                Map<String, Map<String, String>> tls = new HashMap<>();

                for (int i = 1; i < rowList.size(); i++) {
                    String fieldName = columnName.get(i);
                    String fieldValue = rowList.get(i);
                    //处理多语言
                    if(null==languages || !translateLanguageCell(fieldName,fieldValue,tls)){
                        //处理ExcelJoinColumn如果列是多语言列则忽略
                        Object value1 = translateCellValue(fieldName, fieldValue, TranslateType.alterColumnTojoinColumn,null);
                        if(null == value1){
                            continue;
                        }
                        if(typeMapping.get(fieldName).getSimpleName().equalsIgnoreCase("DATE")){
                            value1= HSSFDateUtil.getJavaCalendar(Double.valueOf(value1.toString())).getTime();
                        }
                            BeanUtils.setProperty(dto, fieldName, value1);
                    }
                }
                if(tls.size()!=0){
                    BeanUtils.setProperty(dto,"__tls",tls);
                }
                baseMapper.insertSelective(dto);
            } catch (Exception e) {
                logger.error(e.getMessage(),e);
                throw new ExcelException("插入数据失败");
            }

        }

    }

    @Override
    protected void cleanData() {
        super.cleanData();
        baseMapper=null;
    }
}
