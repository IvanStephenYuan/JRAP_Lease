package com.jingrui.jrap.cashflow.controllers;

import org.springframework.stereotype.Controller;
import com.jingrui.jrap.system.controllers.BaseController;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.system.dto.ResponseData;
import com.jingrui.jrap.cashflow.dto.Writeoff;
import com.jingrui.jrap.cashflow.service.IWriteoffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import org.springframework.validation.BindingResult;
import java.util.List;

    @Controller
    public class WriteoffController extends BaseController{

    @Autowired
    private IWriteoffService service;


    @RequestMapping(value = "/csh/writeoff/query")
    @ResponseBody
    public ResponseData query(Writeoff dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext,dto,page,pageSize));
    }

    @RequestMapping(value = "/csh/writeoff/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<Writeoff> dto, BindingResult result, HttpServletRequest request){
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
        ResponseData responseData = new ResponseData(false);
        responseData.setMessage(getErrorMessage(result, request));
        return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/csh/writeoff/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request,@RequestBody List<Writeoff> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }
    }