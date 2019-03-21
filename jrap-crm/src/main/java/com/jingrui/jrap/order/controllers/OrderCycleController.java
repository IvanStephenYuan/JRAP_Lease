package com.jingrui.jrap.order.controllers;

import org.springframework.stereotype.Controller;
import com.jingrui.jrap.system.controllers.BaseController;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.system.dto.ResponseData;
import com.jingrui.jrap.order.dto.OrderCycle;
import com.jingrui.jrap.order.service.IOrderCycleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import org.springframework.validation.BindingResult;
import java.util.List;

    @Controller
    public class OrderCycleController extends BaseController{

    @Autowired
    private IOrderCycleService service;


    @RequestMapping(value = "/con/order/cycle/query")
    @ResponseBody
    public ResponseData query(OrderCycle dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext,dto,page,pageSize));
    }

    @RequestMapping(value = "/con/order/cycle/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<OrderCycle> dto, BindingResult result, HttpServletRequest request){
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
        ResponseData responseData = new ResponseData(false);
        responseData.setMessage(getErrorMessage(result, request));
        return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/con/order/cycle/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request,@RequestBody List<OrderCycle> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }
    }