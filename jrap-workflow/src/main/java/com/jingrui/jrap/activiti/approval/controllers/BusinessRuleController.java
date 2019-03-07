package com.jingrui.jrap.activiti.approval.controllers;

/**
 * @author xiangyu.qi@jingrui.com
 */

import com.jingrui.jrap.activiti.approval.dto.BusinessRuleHeader;
import com.jingrui.jrap.activiti.approval.dto.BusinessRuleLine;
import com.jingrui.jrap.activiti.approval.service.IBusinessRuleHeaderService;
import com.jingrui.jrap.activiti.approval.service.IBusinessRuleLineService;
import com.jingrui.jrap.core.IRequest;
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
@RequestMapping(value = {"/wfl/business/rule", "/api/wfl/business/rule"})
public class BusinessRuleController extends BaseController {

    @Autowired
    private IBusinessRuleHeaderService service;

    @Autowired
    private IBusinessRuleLineService lineService;

    @RequestMapping(value = "/header/query")
    @ResponseBody
    public ResponseData query(BusinessRuleHeader dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/header/queryAll")
    @ResponseBody
    public ResponseData queryAll(HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        BusinessRuleHeader dto = new BusinessRuleHeader();
        dto.setEnableFlag("Y");
        return new ResponseData(service.selectAll(requestContext, dto));
    }

    @RequestMapping(value = "/header/submit")
    @ResponseBody
    public ResponseData update(HttpServletRequest request, @RequestBody List<BusinessRuleHeader> dto, BindingResult result) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/header/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<BusinessRuleHeader> dto) {
        IRequest requestCtx = createRequestContext(request);
        service.batchDelete(requestCtx, dto);
        return new ResponseData();
    }

    @RequestMapping(value = "/line/query")
    @ResponseBody
    public ResponseData queryLine(BusinessRuleLine dto, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(lineService.selectOptions(requestContext, dto, null));
    }

    @RequestMapping(value = "/line/remove")
    @ResponseBody
    public ResponseData deleteLine(HttpServletRequest request, @RequestBody List<BusinessRuleLine> dto) {
        lineService.batchDelete(dto);
        return new ResponseData();
    }
}