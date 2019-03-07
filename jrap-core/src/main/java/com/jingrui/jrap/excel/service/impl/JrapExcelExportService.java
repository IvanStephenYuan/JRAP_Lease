package com.jingrui.jrap.excel.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jingrui.jrap.excel.ExcelException;
import com.jingrui.jrap.excel.service.IJrapExcelExportService;
import com.jingrui.jrap.excel.util.TableUtils;
import com.jingrui.jrap.generator.service.impl.FileUtil;
import com.jingrui.jrap.mybatis.entity.EntityColumn;
import com.jingrui.jrap.mybatis.entity.EntityTable;
import com.jingrui.jrap.system.dto.ResponseData;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * @author jialong.zuo@jingrui.com on 2017/11/20.
 */
@Component
public class JrapExcelExportService implements IJrapExcelExportService {

    @Autowired
    DataSource dataSource;

    private Logger logger = LoggerFactory.getLogger(getClass());

    ThreadLocal<Long> index=ThreadLocal.withInitial(()->new Long(1));


    @Autowired
    MessageSource messageSource;

    @Override
    public void exportAndDownloadExcel(Object responseData, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,List<String> columns) throws ExcelException, IOException {
        ResponseData rs=(ResponseData) responseData;

        if(rs.getRows().size()==0){
            return;
        }
        SXSSFWorkbook wb = new SXSSFWorkbook();

        JrapExcelExportImpl hapExcelExport=null;

        try {
            hapExcelExport=new JrapExcelExportImpl(wb,dataSource,columns);
            Object r=rs.getRows().get(0);
            JrapExcelExportImpl.setExcelHeader(httpServletResponse,httpServletRequest,"exportFile");
            hapExcelExport.createExcelTemplate(TableUtils.getTable(r.getClass()).getName());
            hapExcelExport.fillSheet((List<Object>) rs.getRows(),r.getClass());
            hapExcelExport.write(httpServletResponse.getOutputStream());
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            throw new ExcelException("导出失败");
        }finally {
            hapExcelExport.close();
        }

    }

    public ArrayNode getAllExportColumn(String tableName,HttpServletRequest request){
        Class parentDto= TableUtils.getTableClass(tableName);
        ObjectMapper columnTree=new ObjectMapper();
        ArrayNode jsonNodes=columnTree.createArrayNode();

        parseClass(parentDto,columnTree,request,jsonNodes,index.get());
        parentDto.getSimpleName();
        index.set(new Long(1));
        return jsonNodes;
    }

    private void parseClass(Class dto,ObjectMapper columnTree,HttpServletRequest request,ArrayNode rootNode,Long parentId){

        Long id=index.get();

        EntityTable entityTable=TableUtils.getTable(dto);
        String dtoName=dto.getSimpleName();
        Set<EntityColumn> columns=entityTable.getAllColumns();
        ObjectNode objectNode=columnTree.createObjectNode();


        objectNode.put("text",dtoName);
        objectNode.put("value",dtoName);
        objectNode.put("ischecked",false);
        objectNode.put("hasChildren",false);
        objectNode.put("id",id);
        objectNode.put("parentId",id-1==0?null:parentId);
        rootNode.add(objectNode);

        int j=1;
        for(EntityColumn column:columns){
            String columnName=FileUtil.columnToCamel(column.getColumn());
            String description=messageSource.getMessage((dtoName+"."+columnName).toLowerCase(),null, RequestContextUtils.getLocale(request));
            if(description.equals((dtoName+"."+columnName).toLowerCase())){
                continue;
            }
            ObjectNode node=columnTree.createObjectNode();
            node.put("text",description);
            node.put("value",dtoName+"."+columnName);
            node.put("ischecked",false);
            node.put("id",id+j);
            node.put("parentId",id);
            rootNode.add(node);
            j+=1;
        }
        index.set(id+j);
        for(Class clz:TableUtils.getChildren(dto)){
            parseClass(clz,columnTree,request,rootNode,id);
        }
    }



}
