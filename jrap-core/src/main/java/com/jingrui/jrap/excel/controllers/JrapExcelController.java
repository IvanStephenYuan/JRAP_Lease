package com.jingrui.jrap.excel.controllers;

import java.io.*;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.jingrui.jrap.excel.ExcelException;
import com.jingrui.jrap.excel.service.IExportService;
import com.jingrui.jrap.excel.service.impl.JrapExcelExportService;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.excel.dto.ColumnInfo;
import com.jingrui.jrap.excel.dto.ExportConfig;
import com.jingrui.jrap.excel.service.IJrapExcelImportService;
import com.jingrui.jrap.function.dto.Function;
import com.jingrui.jrap.system.controllers.BaseController;

/**
 * Created by jialong.zuo@jingrui.com on 2016/11/30.
 */
@Controller
@RequestMapping(value = {"/sys", "/api/sys"})
public class JrapExcelController extends BaseController {
    @Autowired
    IExportService excelService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    IJrapExcelImportService iImportService;

    @Autowired
    JrapExcelExportService newExportService;

    @RequestMapping(value = "/function/export")
    public void createXLS(HttpServletRequest request, @RequestParam String config,
                          HttpServletResponse httpServletResponse) throws IOException {
        IRequest requestContext = createRequestContext(request);
        JavaType type = objectMapper.getTypeFactory().constructParametrizedType(ExportConfig.class,
                ExportConfig.class, Function.class, ColumnInfo.class);
        ExportConfig<Function, ColumnInfo> exportConfig = objectMapper.readValue(config, type);
        excelService.exportAndDownloadExcel("com.jingrui.jrap.function.mapper.FunctionMapper.selectAll",
                exportConfig, request, httpServletResponse, requestContext);

    }

    @RequestMapping(value = "/export/template/{tableName}")
    public void exportImportTemplate(@PathVariable String tableName,HttpServletRequest request, HttpServletResponse response ) throws IOException, SQLException, ExcelException {
        iImportService.exportExcelTemplate(tableName,response,request);
    }

    @RequestMapping(value = "/import/template/{tableName}")
    public ArrayNode chooseExportColumn(@PathVariable String tableName, HttpServletRequest request, HttpServletResponse response ) throws IOException, SQLException, ExcelException {
        return newExportService.getAllExportColumn(tableName,request);
    }

    @RequestMapping(value = "/import/{tableName}")
    public void importXLS(HttpServletRequest request, HttpServletResponse response, @PathVariable String tableName) throws IOException, ExcelException, SQLException, FileUploadException {
//
        // 文件上传处理工厂
        FileItemFactory factory = new DiskFileItemFactory();

        // 创建文件上传处理器
        ServletFileUpload upload = new ServletFileUpload(factory);

        List items;
        items = upload.parseRequest(request);
        // 对所有请求信息进行判断
        if (!items.isEmpty()) {
            Iterator iterator = items.iterator();
            while (iterator.hasNext()) {
                FileItem item = (FileItem) iterator.next();
                // 信息为文件的格式
                if (!item.isFormField()) {
                    InputStream fs = item.getInputStream();
                    iImportService.loadExcel(fs, tableName);
                }
            }
        }

        // EntityTable table = EntityHelper.getEntityTable(tableName);

    }

}
