package com.jingrui.jrap.item.controllers;

import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.item.dto.ItemStock;
import com.jingrui.jrap.item.service.IItemStockService;
import com.jingrui.jrap.mybatis.common.Criteria;
import com.jingrui.jrap.system.controllers.BaseController;
import com.jingrui.jrap.system.dto.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ItemStockController extends BaseController {

    @Autowired
    private IItemStockService service;


    @RequestMapping(value = "/afd/item/stock/query")
    @ResponseBody
    public ResponseData query(ItemStock dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/afd/item/stock/selectOptions")
    @ResponseBody
    public ResponseData queryOptions(ItemStock dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                     @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        return new ResponseData(service.selectOptions(requestContext, dto, criteria, page, pageSize));
    }


    @RequestMapping(value = "/afd/item/stock/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<ItemStock> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/afd/item/stock/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<ItemStock> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }
}