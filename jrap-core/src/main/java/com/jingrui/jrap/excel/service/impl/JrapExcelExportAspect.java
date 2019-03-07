package com.jingrui.jrap.excel.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.jingrui.jrap.core.components.ApplicationContextHelper;
import com.jingrui.jrap.excel.ExcelException;
import com.jingrui.jrap.excel.annotation.ExcelExport;
import com.jingrui.jrap.excel.service.IJrapExcelExportService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jialong.zuo@jingrui.com on 2017/11/20.
 */

@Aspect
public class JrapExcelExportAspect {

    @AfterReturning(value = "@annotation(excelExport)",returning = "responseData")
    public void afterController(JoinPoint joinpoint, ExcelExport excelExport,Object responseData) throws IOException, SQLException, ExcelException {

        HttpServletRequest httpServletRequest=null;
        HttpServletResponse httpServletResponse=null;

        for(int i=0;i<joinpoint.getArgs().length;i++){
            if(joinpoint.getArgs()[i] instanceof HttpServletRequest){
                httpServletRequest= (HttpServletRequest) joinpoint.getArgs()[i];
            }else if(joinpoint.getArgs()[i] instanceof HttpServletResponse){
                httpServletResponse=(HttpServletResponse) joinpoint.getArgs()[i];
            }
        }
        if(httpServletRequest.getParameterMap().containsKey("_HAP_EXCEL_EXPORT_COLUMNS")) {
            String columnsJson= httpServletRequest.getParameterMap().get("_HAP_EXCEL_EXPORT_COLUMNS")[0];
            ObjectMapper mapper = new ObjectMapper();
            ArrayNode node=mapper.readValue(columnsJson,ArrayNode.class);
            List<String> columns=new ArrayList<>();
            node.forEach(v->{
                columns.add(v.get("column").asText());
            });
            ApplicationContextHelper.getApplicationContext().getBean(IJrapExcelExportService.class)
                    .exportAndDownloadExcel(responseData, httpServletRequest, httpServletResponse,columns);
        }
    }


}
