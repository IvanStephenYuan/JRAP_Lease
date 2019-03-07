package com.jingrui.jrap.code.rule.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.jingrui.jrap.mybatis.common.Criteria;
import com.jingrui.jrap.mybatis.common.query.Comparison;
import com.jingrui.jrap.mybatis.common.query.WhereField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jingrui.jrap.code.rule.dto.CodeRulesHeader;
import com.jingrui.jrap.code.rule.service.ICodeRulesHeaderService;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.system.controllers.BaseController;
import com.jingrui.jrap.system.dto.ResponseData;

@Controller
@RequestMapping(value = {"/sys/code/rules/header","/api/sys/code/rules/header"})
public class CodeRulesHeaderController extends BaseController {

    @Autowired
    private ICodeRulesHeaderService service;

    @RequestMapping(value = "/query")
    @ResponseBody
    public ResponseData query(CodeRulesHeader dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField(CodeRulesHeader.FIELD_HEADER_ID),new WhereField( CodeRulesHeader.FIELD_RULE_CODE,Comparison.LIKE),new WhereField(CodeRulesHeader.FIELD_RULE_NAME,Comparison.LIKE));
        return new ResponseData(service.selectOptions(requestContext, dto,criteria, page, pageSize));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<CodeRulesHeader> dto, BindingResult result,
            HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<CodeRulesHeader> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }
}