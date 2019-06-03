package com.jingrui.jrap.item.controllers;

import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.item.dto.PurchaseDetail;
import com.jingrui.jrap.item.mapper.PurchaseDetailMapper;
import com.jingrui.jrap.item.service.IPurchaseDetailService;
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
public class PurchaseDetailController extends BaseController{

    @Autowired
    private IPurchaseDetailService service;

    @Autowired
    private PurchaseDetailMapper purchaseDetailMapper;


    @RequestMapping(value = "/afd/purchase/detail/query")
    @ResponseBody
    public ResponseData query(PurchaseDetail dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext,dto,page,pageSize));
    }

    @RequestMapping(value = "/afd/purchase/detail/selectOptions")
    @ResponseBody
    public ResponseData queryOptions(PurchaseDetail dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        return new ResponseData(service.selectOptions(requestContext, dto, criteria, page, pageSize));
    }

    @RequestMapping(value = "/afd/purchase/detail/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<PurchaseDetail> dto, BindingResult result, HttpServletRequest request){
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
        ResponseData responseData = new ResponseData(false);
        responseData.setMessage(getErrorMessage(result, request));
        return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/afd/purchase/detail/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request,@RequestBody List<PurchaseDetail> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }

    /**
     * 查询采购明细（从视图查询）
     * @param dto
     * @param page
     * @param pageSize
     * @param request
     * @return
     */
    @RequestMapping(value = "/afd/purchase/detail/queryPurchaseDetail")
    @ResponseBody
    public ResponseData queryPurchaseDetail(PurchaseDetail dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        //IRequest requestContext = createRequestContext(request);
        return new ResponseData(purchaseDetailMapper.queryPurchaseDetail(dto));
    }
}