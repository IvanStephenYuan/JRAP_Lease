package com.jingrui.jrap.flexfield.controllers;

import com.jingrui.jrap.flexfield.dto.WarpFlexRuleField;
import com.jingrui.jrap.flexfield.service.impl.FlexRuleServiceImpl;
import net.sf.json.JSONObject;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import com.jingrui.jrap.system.controllers.BaseController;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.system.dto.ResponseData;
import com.jingrui.jrap.flexfield.dto.FlexRule;
import com.jingrui.jrap.flexfield.service.IFlexRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping(value = {"/fnd/flex/rule", "/api/fnd/flex/rule"})
public class FlexRuleController extends BaseController {

    @Autowired
    private IFlexRuleService service;

    @Autowired
    private MessageSource messageSource;

    @RequestMapping(value = "/query",method = {RequestMethod.GET,RequestMethod.POST})
    public ResponseData query(FlexRule dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/matching",method = {RequestMethod.GET,RequestMethod.POST})
    public ResponseData matchingRule(String ruleSetCode, @RequestBody JSONObject viewModel, HttpServletRequest request) {
        Set<Map.Entry<String, String>> entrySet = viewModel.entrySet();
        IRequest requestContext = createRequestContext(request);
        ResponseData responseData = service.matchingRule(ruleSetCode, entrySet,requestContext);
        if(null ==responseData.getRows()) {
            return responseData;
        }

        List<WarpFlexRuleField> warpFlexRuleFields = (List<WarpFlexRuleField>) responseData.getRows();
        Locale locale = RequestContextUtils.getLocale(request);

        warpFlexRuleFields.forEach(v -> {
            FlexRuleServiceImpl.setPrompt(v, locale, messageSource);
        });
        return responseData;
    }

    @PostMapping(value = "/submit")
    public ResponseData update(@RequestBody List<FlexRule> dto, HttpServletRequest request, BindingResult result) {
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
    public ResponseData delete(HttpServletRequest request, @RequestBody List<FlexRule> dto) {
        service.deleteRule(dto);
        return new ResponseData();
    }
}