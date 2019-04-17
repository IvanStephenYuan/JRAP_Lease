package com.jingrui.jrap.item.controllers;

import com.jingrui.jrap.code.rule.exception.CodeRuleException;
import com.jingrui.jrap.code.rule.service.ISysCodeRuleProcessService;
import com.jingrui.jrap.product.service.IDocumentTypeService;
import org.springframework.stereotype.Controller;
import com.jingrui.jrap.system.controllers.BaseController;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.system.dto.ResponseData;
import com.jingrui.jrap.item.dto.Item;
import com.jingrui.jrap.item.service.IItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.BindingResult;

import java.util.List;

@Controller
public class ItemController extends BaseController {
    private static final String CAR_RULE_CODE = "CAR";

    @Autowired
    private IItemService service;

    @Autowired
    private ISysCodeRuleProcessService codeRuleProcessService;

    @Autowired
    private IDocumentTypeService documentTypeService;

    @RequestMapping(value = "/afd/item/query")
    @ResponseBody
    public ResponseData query(Item dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/afd/item/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<Item> dto, BindingResult result,
                               HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }

        IRequest requestCtx = createRequestContext(request);
        for (int i = 0; i < dto.size(); i++) {
            dto.get(i).setCompanyId(requestCtx.getCompanyId());
            //设置租赁物编码
            String itecode = dto.get(i).getItemCode();
            if (itecode == null || "".equalsIgnoreCase(itecode)) {
                try {
                    String ruleCode = documentTypeService.getDocumentCodeRule(dto.get(i).getDocumentCategory(), dto.get(i).getDocumentType());
                    if (ruleCode == null || "ERROR".equalsIgnoreCase(ruleCode)) {
                        ruleCode = ItemController.CAR_RULE_CODE;
                    }

                    String carCode = codeRuleProcessService.getRuleCode(ruleCode);
                    dto.get(i).setItemCode(carCode);
                } catch (CodeRuleException e) {
                    e.printStackTrace();
                }
            }
        }
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/afd/item/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<Item> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }
}