package com.jingrui.jrap.excel.service.impl;

import com.jingrui.jrap.excel.ExcelException;
import com.jingrui.jrap.excel.annotation.ExcelJoinColumn;
import com.jingrui.jrap.excel.util.TableUtils;
import com.jingrui.jrap.mybatis.entity.EntityTable;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

/**
 * @author jialong.zuo@jingrui.com on 2017/11/20.
 */
public class JrapExcelExportImpl {

    private CellStyle dateCellStyle;

    private CellStyle titleCellStyle;
    private CellStyle columnNameCellStyle;

    private SXSSFWorkbook wb;

    Connection connection;

    private List<String> containColumns;

    private Logger logger = LoggerFactory.getLogger(getClass());

    private Map<String,List<String>> columnMapping=new HashMap<>();

    SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);


    public JrapExcelExportImpl(SXSSFWorkbook workbook,DataSource dataSource,List<String> columns) throws SQLException {
        CellStyle dateFormat = workbook.createCellStyle();
        dateFormat.setDataFormat(workbook.createDataFormat().getFormat("yyyy-MM-dd HH:mm:ss"));
        dateCellStyle=dateFormat;
        titleCellStyle=workbook.createCellStyle();
        columnNameCellStyle=workbook.createCellStyle();
        short GREY_40_PERCENT=23;
        titleCellStyle.setFillForegroundColor(GREY_40_PERCENT);
        titleCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);//填暗红色
        Font font=workbook.createFont();
        font.setColor(font.getIndex());
        titleCellStyle.setFont(font);

        columnNameCellStyle.setFillForegroundColor(GREY_40_PERCENT);
        columnNameCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);//填暗红色
        columnNameCellStyle.setFont(font);
        this.wb=workbook;
        this.containColumns=columns;
        connection = dataSource.getConnection();

    }


    /**
     * @param lists
     * @param dto
     */
    public void fillSheet(List<Object> lists,Class dto) throws ClassNotFoundException, ExcelException {
        if(lists.size()==0){
            return;
        }

        String tableName=TableUtils.getTable(dto).getName();
        SXSSFSheet sheet=wb.getSheet(tableName);
        ExportStrategy exportStrategy=new ExportStrategy(connection,tableName);
        sheet.setColumnWidth(0,7*256);
        List<String> columns=Optional.ofNullable(columnMapping.get(tableName)).orElseGet(()->{
            List<String> column=new ArrayList<>();
            SXSSFRow columnName=sheet.getRow(1);
            for(int i=0;i<columnName.getLastCellNum();i++){
                column.add(columnName.getCell(i).getStringCellValue());
                String type=exportStrategy.getCellType(columnName.getCell(i).getStringCellValue());
                setCellWidth(sheet,type,i);
            }
            columnMapping.put(tableName,column);
            return column;
        });
        int rowNum=sheet.getLastRowNum();
        //处理每行数据
        for(int i=0;i<lists.size();i++){
            SXSSFRow row=sheet.createRow(rowNum+i+1);
            createRow(row,lists.get(i),columns,exportStrategy);
        }

    }


    /** 生成行数据
     * @param row
     * @param object
     * @param columns
     * @param exportStrategy
     * @throws SQLException
     */
    public void createRow(SXSSFRow row, Object object, List<String> columns, ExportStrategy exportStrategy) throws ExcelException {

        createCell(row.createCell(0),"*","String");
        for(int i=1;i<columns.size();i++){
           String column=columns.get(i);
            String cell=null;
            try {
                cell = BeanUtils.getProperty(object,column);
            } catch (Exception ev) {
            }
            String cellValue=null;
            try {
               Object v = exportStrategy.translateCellValue(column, cell, ExcelSheetStrategy.TranslateType.joinColumnToAlterCloumn,object);
                if(null != v){
                    cellValue=v.toString();
                }
            } catch (Exception e) {
                logger.error(e.getMessage(),e);
                throw new ExcelException("cell内容替换失败");
            }

            createCell(row.createCell(i),cellValue,exportStrategy.getCellType(column));
        }
        getChildrenData(object);
    }

    public void getChildrenData(Object object) throws ExcelException {
        try {
        Class parentClass=object.getClass();
        List<Class>childClasses=TableUtils.getChildren(parentClass);
        for(Class childClass:childClasses){
            Object child=childClass.newInstance();

            List<Field> fields= TableUtils.getExcelJoinColumn(childClass);
            for(Field field:fields){
                ExcelJoinColumn ann=field.getAnnotationsByType(ExcelJoinColumn.class)[0];
                if(ann.JoinTable() == parentClass){
                    String value=BeanUtils.getProperty(object,ann.JoinColumn());
                    BeanUtils.setProperty(child,field.getName(),value);
                }
            }
            List<Object>responseData=TableUtils.getBaseMapperByType(childClass, ExcelSheetStrategy.MapperType.Select).select(child);
            fillSheet(responseData,childClass);
        }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            throw new ExcelException("获取子表数据失败");
        }
    }


    /** 根据dto生产对应sheet导出模板
     * @param dto
     * @throws IOException
     */
    private void createSheetTemplate(Class dto, boolean containStdWho) throws IOException {
        EntityTable tableEntity= TableUtils.getTable(dto);
        SXSSFSheet sheet = createSheet(tableEntity.getName());
        sheet.setDefaultColumnWidth(20);
        List<String> column=TableUtils.getColumn(tableEntity,containColumns,containStdWho);
        List<String> title=TableUtils.getTitle(column,dto);
        //设置title
        SXSSFRow titleRow=sheet.createRow(0);
        SXSSFRow columnRow=sheet.createRow(1);
        createTemplateRow(title,titleRow);
        //设置column
        createTemplateRow(column,columnRow);
        for(int i=0;i<titleRow.getLastCellNum();i++){
            SXSSFCell cell=titleRow.getCell(i);
            SXSSFCell columnCell=columnRow.getCell(i);
            columnCell.setCellStyle(columnNameCellStyle);
            cell.setCellStyle(titleCellStyle);
        }

    }

    /**
     * @param tableName 解析dto包含子表创建sheet
     * @throws IOException
     */
    public void createExcelTemplate(String tableName,boolean containStdWho) throws IOException {
        List<Class>allTables=new ArrayList<>();
        TableUtils.getAllChildrenTable(TableUtils.getTableClass(tableName),allTables);
        for(Class tableDto:allTables){
            createSheetTemplate(tableDto,containStdWho);
        }
    }

    /**
     * @param tableName 解析dto包含子表创建sheet
     * @throws IOException
     */
    public void createExcelTemplate(String tableName) throws IOException {
        createExcelTemplate(tableName,true);
    }



    /**设置http请求报文为下载文件
     * @param httpServletResponse
     * @param httpServletRequest
     * @param fileName
     * @throws UnsupportedEncodingException
     *
     * **/
    public static void setExcelHeader(HttpServletResponse httpServletResponse , HttpServletRequest httpServletRequest,String fileName) throws UnsupportedEncodingException {

        String name=fileName+".xlsx";
        String userAgent = httpServletRequest.getHeader("User-Agent");
        if(userAgent.contains("Firefox")){
            name=new String(name.getBytes("UTF-8"), "ISO8859-1");
        }else{
            name=URLEncoder.encode(name, "UTF-8");
        }
        httpServletResponse.addHeader("Content-Disposition",
                "attachment; filename=\""+name+"\"");
        httpServletResponse.setContentType("application/vnd.ms-excel" + ";charset=" + "UTF-8");
        httpServletResponse.setHeader("Accept-Ranges", "bytes");

    }


    /**
     * @param cell 创建cell
     * @param fieldObject
     * @param type
     */
    public void createCell(SXSSFCell cell,String fieldObject,String type){
        if(null == type){
            type=fieldObject.getClass().getSimpleName();
        }
        if (null == fieldObject) {
            cell.setCellType(CellType.STRING);
            cell.setCellValue((String) null);
        } else {
            switch (type.toUpperCase()) {
                case "NUMBER":
                case "FLOAT":
                    cell.setCellType(CellType.NUMERIC);
                    cell.setCellValue(Float.valueOf(fieldObject));
                    break;
                case "DOUBLE":
                    cell.setCellType(CellType.NUMERIC);
                    cell.setCellValue(Double.valueOf(fieldObject));
                    break;
                case "INT":
                case "INTEGER":
                case "LONG":
                    cell.setCellType(CellType.NUMERIC);
                    cell.setCellValue(Long.valueOf(fieldObject));
                    break;
                case "DATE":
                    Date date;
                    try {
                        date = sdf.parse(fieldObject);
                    } catch (ParseException e) {
                        logger.error("can not parse date value,skip...");
                        break;
                        //date=new Date(fieldObject);
                    }
                    cell.setCellStyle(dateCellStyle);
                    cell.setCellValue(date);
                    break;
                case "BOOLEAN":
                    cell.setCellType(CellType.BOOLEAN);
                    cell.setCellValue(Boolean.valueOf(fieldObject));
                    break;
                default:
                    cell.setCellType(CellType.STRING);
                    cell.setCellValue( fieldObject);
                    break;
            }
        }

    }

    public void setCellWidth(SXSSFSheet sheet,String type,int columnIndex){
        if(null == type){
            return;
        }
        switch (type.toUpperCase()) {
            case "STRING":
                sheet.setColumnWidth(columnIndex,22*256);
                break;
            case "NUMBER":
            case "FLOAT":
            case "DOUBLE":
            case "INT":
            case "INTEGER":
            case "LONG":
            case "BOOLEAN":
                sheet.setColumnWidth(columnIndex,15*256);
                break;
            case "DATE":
                sheet.setColumnWidth(columnIndex,25*256);
                break;
            default:
                sheet.setColumnWidth(columnIndex,22*256);
                break;
        }

    }

    /** 生产excel title和column
     * @param title
     * @param titleRow
     */
    public void createTemplateRow(List<String> title, SXSSFRow titleRow){
        createCell(titleRow.createCell(0),"*","String");

        for(int i=0;i<title.size();i++){

            createCell(titleRow.createCell(i+1),title.get(i),"String");
        }
    }


    public SXSSFSheet createSheet(String sheetName){
        return wb.createSheet(sheetName);
    }

    public void write(OutputStream outputStream) throws IOException {
        wb.write(outputStream);
    }
    public void close() throws IOException {
        wb.close();
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException("关闭数据库连接失败");
        }

    }

}
