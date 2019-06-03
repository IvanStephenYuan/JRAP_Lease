package com.jingrui.jrap.item.controllers;

import com.jingrui.jrap.code.rule.exception.CodeRuleException;
import com.jingrui.jrap.code.rule.service.ISysCodeRuleProcessService;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.item.dto.ItemAllocate;
import com.jingrui.jrap.item.service.IItemAllocateService;
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
public class ItemAllocateController extends BaseController{
    public final static String ALLOCATE_RULE_CODE = "ALLOCATE";
    @Autowired
    private IItemAllocateService service;

    @Autowired
    private ISysCodeRuleProcessService codeRuleProcessService;


    @RequestMapping(value = "/afd/item/allocate/query")
    @ResponseBody
    public ResponseData query(ItemAllocate dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext,dto,page,pageSize));
    }

    @RequestMapping(value = "/afd/item/allocate/selectOptions")
    @ResponseBody
    public ResponseData queryOptions(ItemAllocate dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        return new ResponseData(service.selectOptions(requestContext, dto, criteria, page, pageSize));
    }

    @RequestMapping(value = "/afd/item/allocate/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<ItemAllocate> dto, BindingResult result, HttpServletRequest request){
        IRequest requestCtx = createRequestContext(request);
        for (ItemAllocate itemAllocate : dto) {
            String allocateCode = itemAllocate.getAllocateCode();
            if (allocateCode == null||"".equals(allocateCode)) {
                try {
                    allocateCode = codeRuleProcessService.getRuleCode(ALLOCATE_RULE_CODE);
                    itemAllocate.setAllocateCode(allocateCode);
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

    @RequestMapping(value = "/afd/item/allocate/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request,@RequestBody List<ItemAllocate> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }
}