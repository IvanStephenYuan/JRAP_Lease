package com.jingrui.jrap.activiti.approval.controllers;

import com.jingrui.jrap.activiti.approval.dto.ApproveStrategy;
import com.jingrui.jrap.activiti.approval.service.IApproveStrategyService;
import com.jingrui.jrap.core.BaseConstants;
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
@RequestMapping(value = {"/wfl/approve/strategy", "/api/wfl/approve/strategy"})
public class ApproveStrategyController extends BaseController {

    @Autowired
    private IApproveStrategyService service;


    @RequestMapping(value = "/query")
    @ResponseBody
    public ResponseData query(ApproveStrategy dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/queryAll")
    @ResponseBody
    public ResponseData queryAll(HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        ApproveStrategy dto = new ApproveStrategy();
        dto.setEnableFlag(BaseConstants.YES);
        return new ResponseData(service.selectAll(requestContext, dto));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(HttpServletRequest request, @RequestBody List<ApproveStrategy> dto, BindingResult result) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<ApproveStrategy> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }
}