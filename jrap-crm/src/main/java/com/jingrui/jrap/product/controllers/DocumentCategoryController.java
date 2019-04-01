package com.jingrui.jrap.product.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jingrui.jrap.system.dto.CodeValue;
import org.springframework.stereotype.Controller;
import com.jingrui.jrap.system.controllers.BaseController;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.system.dto.ResponseData;
import com.jingrui.jrap.product.dto.DocumentCategory;
import com.jingrui.jrap.product.service.IDocumentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.BindingResult;

import java.util.List;

@Controller
public class DocumentCategoryController extends BaseController {

    @Autowired
    private IDocumentCategoryService service;


    @RequestMapping(value = "/pro/document/category/query")
    @ResponseBody
    public ResponseData query(DocumentCategory dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/pro/document/category/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<DocumentCategory> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/pro/document/category/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<DocumentCategory> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }
}