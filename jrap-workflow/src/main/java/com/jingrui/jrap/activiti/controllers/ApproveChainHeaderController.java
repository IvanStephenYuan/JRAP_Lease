package com.jingrui.jrap.activiti.controllers;

import com.jingrui.jrap.activiti.dto.ApproveChainHeader;
import com.jingrui.jrap.activiti.service.IApproveChainHeaderService;
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
@RequestMapping(value = {"/wfl", "/api/wfl"})
public class ApproveChainHeaderController extends BaseController {

    @Autowired
    private IApproveChainHeaderService service;

    @RequestMapping(value = "/approve/chain/header/query")
    @ResponseBody
    public ResponseData query(ApproveChainHeader dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/approve/chain/header/submit")
    @ResponseBody
    public ResponseData update(HttpServletRequest request, @RequestBody List<ApproveChainHeader> dto) {
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/approve/chain/submit")
    @ResponseBody
    public ResponseData submitHeadLine(HttpServletRequest request, @RequestBody List<ApproveChainHeader> dto, final BindingResult result) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.updateHeadLine(requestCtx, dto));
    }

    @RequestMapping(value = "/approve/chain/header/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<ApproveChainHeader> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }
}