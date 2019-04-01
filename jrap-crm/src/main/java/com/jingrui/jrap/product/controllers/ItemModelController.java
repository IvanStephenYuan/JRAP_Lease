package com.jingrui.jrap.product.controllers;

import org.springframework.stereotype.Controller;
import com.jingrui.jrap.system.controllers.BaseController;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.system.dto.ResponseData;
import com.jingrui.jrap.product.dto.ItemModel;
import com.jingrui.jrap.product.service.IItemModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import org.springframework.validation.BindingResult;
import java.util.List;

    @Controller
    public class ItemModelController extends BaseController{

    @Autowired
    private IItemModelService service;


    @RequestMapping(value = "/pro/item/model/query")
    @ResponseBody
    public ResponseData query(ItemModel dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        //设置查询条件
        dto.setCompanyId(requestContext.getCompanyId());
        return new ResponseData(service.select(requestContext,dto,page,pageSize));
    }

    @RequestMapping(value = "/pro/item/model/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<ItemModel> dto, BindingResult result, HttpServletRequest request){
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
        ResponseData responseData = new ResponseData(false);
        responseData.setMessage(getErrorMessage(result, request));
        return responseData;
        }
        IRequest requestCtx = createRequestContext(request);

        //设置默认值
        for(ItemModel record : dto){
            Long companyId = record.getCompanyId();
            //设置默认商户
            if(companyId == null){
                record.setCompanyId(requestCtx.getCompanyId());
            }
        }

        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/pro/item/model/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request,@RequestBody List<ItemModel> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }
    }