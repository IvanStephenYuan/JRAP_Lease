package com.jingrui.jrap.config.controllers;

import org.springframework.stereotype.Controller;
import com.jingrui.jrap.system.controllers.BaseController;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.system.dto.ResponseData;
import com.jingrui.jrap.config.dto.SettleDefault;
import com.jingrui.jrap.config.service.ISettleDefaultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import org.springframework.validation.BindingResult;
import com.jingrui.jrap.mybatis.common.Criteria;
import java.util.List;

@Controller
public class SettleDefaultController extends BaseController{

    @Autowired
    private ISettleDefaultService service;


    @RequestMapping(value = "/acc/settle/default/query")
    @ResponseBody
    public ResponseData query(SettleDefault dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext,dto,page,pageSize));
    }

    @RequestMapping(value = "/acc/settle/default/selectOptions")
    @ResponseBody
    public ResponseData queryOptions(SettleDefault dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        return new ResponseData(service.selectOptions(requestContext, dto, criteria, page, pageSize));
    }

    @RequestMapping(value = "/acc/settle/default/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<SettleDefault> dto, BindingResult result, HttpServletRequest request){
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
        ResponseData responseData = new ResponseData(false);
        responseData.setMessage(getErrorMessage(result, request));
        return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/acc/settle/default/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request,@RequestBody List<SettleDefault> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }
}