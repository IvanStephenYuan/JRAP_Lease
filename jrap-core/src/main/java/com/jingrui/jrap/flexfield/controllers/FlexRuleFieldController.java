package com.jingrui.jrap.flexfield.controllers;

import org.springframework.stereotype.Controller;
import com.jingrui.jrap.system.controllers.BaseController;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.system.dto.ResponseData;
import com.jingrui.jrap.flexfield.dto.FlexRuleField;
import com.jingrui.jrap.flexfield.service.IFlexRuleFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author jialongzuo@jingrui.com on 2017-12-8
 */
@RestController
@RequestMapping(value = {"/fnd/flex/rule/field", "/api/fnd/flex/rule/field"})
public class FlexRuleFieldController extends BaseController {

    @Autowired
    private IFlexRuleFieldService service;


    @RequestMapping(value = "/query",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData query(FlexRuleField dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryFlexRuleField(dto));
    }

    @PostMapping(value = "/submit")
    public ResponseData update(@RequestBody List<FlexRuleField> dto, HttpServletRequest request, BindingResult result) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @PostMapping(value = "/remove")
    public ResponseData delete(HttpServletRequest request, @RequestBody List<FlexRuleField> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }
}