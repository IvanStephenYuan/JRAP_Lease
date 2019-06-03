package com.jingrui.jrap.item.controllers;

import com.jingrui.jrap.code.rule.exception.CodeRuleException;
import com.jingrui.jrap.code.rule.service.ISysCodeRuleProcessService;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.item.dto.Purchase;
import com.jingrui.jrap.item.mapper.PurchaseMapper;
import com.jingrui.jrap.item.service.IPurchaseService;
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
public class PurchaseController extends BaseController{

    public final static String PURCHASE_RULE_CODE = "PURCHASE";

    @Autowired
    private IPurchaseService service;
    @Autowired
    private ISysCodeRuleProcessService codeRuleProcessService;
    @Autowired
    private PurchaseMapper purchaseMapper;


    @RequestMapping(value = "/afd/purchase/query")
    @ResponseBody
    public ResponseData query(Purchase dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext,dto,page,pageSize));
    }

    @RequestMapping(value = "/afd/purchase/selectOptions")
    @ResponseBody
    public ResponseData queryOptions(Purchase dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        return new ResponseData(service.selectOptions(requestContext, dto, criteria, page, pageSize));
    }

    @RequestMapping(value = "/afd/purchase/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<Purchase> dto, BindingResult result, HttpServletRequest request){
        IRequest requestCtx = createRequestContext(request);
        for (Purchase purchase : dto) {
            String purchaseCode = purchase.getPurchaseCode();
            if (purchaseCode == null||"".equals(purchaseCode)) {
                try {
                    purchaseCode = codeRuleProcessService.getRuleCode(PurchaseController.PURCHASE_RULE_CODE);
                    purchase.setPurchaseCode(purchaseCode);
                } catch (CodeRuleException e) {
                    e.printStackTrace();
                }
            }
        }
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
        ResponseData responseData = new ResponseData(false);
        responseData.setMessage(getErrorMessage(result, request));
        return responseData;
        }
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/afd/purchase/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request,@RequestBody List<Purchase> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }

    /**
     * 采购申请
     * @param dto
     * @param result
     * @param request
     * @return
     */
    @RequestMapping(value = "/afd/purchase/apply/workflow")
    @ResponseBody
    public ResponseData purchaseApply(@RequestBody Purchase dto, BindingResult result, HttpServletRequest request){
        IRequest requestCtx = createRequestContext(request);
        return service.purchaseApply(requestCtx, dto);
    }

    /**
     * 查询采购信息
     * @param dto
     * @param page
     * @param pageSize
     * @param request
     * @return
     */
    @RequestMapping(value = "/afd/purchase/queryPurchase")
    @ResponseBody
    public ResponseData queryPurchase(Purchase dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        //IRequest requestContext = createRequestContext(request);
        return new ResponseData(purchaseMapper.queryPurchase(dto));
    }
}