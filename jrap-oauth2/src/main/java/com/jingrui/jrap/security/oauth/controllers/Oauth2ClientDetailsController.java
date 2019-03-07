package com.jingrui.jrap.security.oauth.controllers;

import org.springframework.stereotype.Controller;
import com.jingrui.jrap.system.controllers.BaseController;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.system.dto.ResponseData;
import com.jingrui.jrap.security.oauth.dto.Oauth2ClientDetails;
import com.jingrui.jrap.security.oauth.service.IOauth2ClientDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author qixiangyu
 */
@Controller
@RequestMapping(value = "/sys/client/details")
public class Oauth2ClientDetailsController extends BaseController {

    @Autowired
    private IOauth2ClientDetailsService service;

    @RequestMapping(value = "/query")
    @ResponseBody
    public ResponseData query(Oauth2ClientDetails dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.selectOptions(requestContext, dto,null, page, pageSize));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(HttpServletRequest request, @RequestBody List<Oauth2ClientDetails> dto,BindingResult result) {
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
    public ResponseData delete(HttpServletRequest request, @RequestBody List<Oauth2ClientDetails> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value = "/updatePassword")
    @ResponseBody
    public ResponseData updatePassword(HttpServletRequest request, Long id) {
        String pwd =  service.updatePassword(id);
        Map<String,String> result = new HashMap<>(1);
        result.put("clientSecret",pwd);
        return new ResponseData(Arrays.asList(result));
    }
}