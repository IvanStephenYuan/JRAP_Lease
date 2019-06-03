package com.jingrui.jrap.item.controllers;

import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.item.dto.PurchaseAssign;
import com.jingrui.jrap.item.mapper.PurchaseAssignMapper;
import com.jingrui.jrap.item.service.IPurchaseAssignService;
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
public class PurchaseAssignController extends BaseController {

    @Autowired
    private IPurchaseAssignService service;

    @Autowired
    private PurchaseAssignMapper purchaseAssignMapper;


    @RequestMapping(value = "/afd/purchase/assign/query")
    @ResponseBody
    public ResponseData query(PurchaseAssign dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/afd/purchase/assign/selectOptions")
    @ResponseBody
    public ResponseData queryOptions(PurchaseAssign dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                     @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        return new ResponseData(service.selectOptions(requestContext, dto, criteria, page, pageSize));
    }

    @RequestMapping(value = "/afd/purchase/assign/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<PurchaseAssign> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/afd/purchase/assign/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<PurchaseAssign> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    /**
     * 采购分配明细查询
     *
     * @param dto
     * @param page
     * @param pageSize
     * @param request
     * @return
     */
    @RequestMapping(value = "/afd/purchase/assign/queryPurAssign")
    @ResponseBody
    public ResponseData queryPurAssign(PurchaseAssign dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                       @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        //IRequest requestContext = createRequestContext(request);
        List<PurchaseAssign> list = purchaseAssignMapper.queryPurAssign(dto);

        return new ResponseData(list);
    }


    @RequestMapping(value = "/afd/purchase/assign/modify")
    @ResponseBody
    public ResponseData modify(HttpServletRequest request, @RequestBody PurchaseAssign dto) {
        PurchaseAssign purchaseAssign = null;
        if (dto.getAssignId()!= null) {
            purchaseAssign = purchaseAssignMapper.selectByPrimaryKey(dto.getAssignId());
        }
        if(purchaseAssign!=null){
            Long  unregisterNum =  purchaseAssign.getUnregisterNum()==null?0L:purchaseAssign.getUnregisterNum();
            if(unregisterNum>0L){
                purchaseAssign.setUnregisterNum(unregisterNum-1L);
                purchaseAssignMapper.updateByPrimaryKeySelective(purchaseAssign);
            }
        }
        return new ResponseData();
    }
}