package com.jingrui.jrap.api.gateway.controllers;

import com.jingrui.jrap.api.gateway.dto.ApiServer;
import org.springframework.stereotype.Controller;
import com.jingrui.jrap.system.controllers.BaseController;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.system.dto.ResponseData;
import com.jingrui.jrap.api.gateway.service.IApiServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import org.springframework.validation.BindingResult;

import java.util.Arrays;
import java.util.List;

/**
 * 服务控制器.
 *
 * @author lijian.yin@jingrui.com
 **/

@Controller
@RequestMapping(value = { "/sys/gateway/server" , "/api/sys/gateway/server" })
public class ApiServerController extends BaseController {

    @Autowired
    private IApiServerService service;

    @RequestMapping(value = "/query")
    @ResponseBody
    public ResponseData query(ApiServer dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/remove")
    @ResponseBody
    public ResponseData remove(HttpServletRequest request, @RequestBody List<ApiServer> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value = "/import")
    @ResponseBody
    public ResponseData importServer(HttpServletRequest request , @RequestBody ApiServer dto){
        IRequest iRequest = createRequestContext(request);
        return new ResponseData(Arrays.asList(service.importServer(iRequest,dto)));
    }

    @RequestMapping(value = "/getById")
    @ResponseBody
    public ResponseData queryById(HttpServletRequest request , @RequestBody ApiServer dto) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(Arrays.asList(service.selectByPrimaryKey(requestContext , dto)));
    }

    @RequestMapping(value = "/submit", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseData submit(@RequestBody List<ApiServer> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }


}