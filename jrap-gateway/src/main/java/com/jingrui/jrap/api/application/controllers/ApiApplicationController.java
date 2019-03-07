package com.jingrui.jrap.api.application.controllers;

import com.jingrui.jrap.api.application.dto.ApiApplication;
import com.jingrui.jrap.api.application.service.IApiApplicationService;
import com.jingrui.jrap.api.gateway.dto.ApiServer;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.system.controllers.BaseController;
import com.jingrui.jrap.system.dto.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 服务控制器.
 *
 * @author lijian.yin@jingrui.com
 * @date 2017/11/15.
 **/

@RestController
@RequestMapping(value = {"/sys/application/app","/api/sys/application/app"})
public class ApiApplicationController extends BaseController {

    @Autowired
    private IApiApplicationService service;

    @RequestMapping(value = "/query" ,method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseData query(ApiApplication dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.selectApplications(requestContext, dto, page, pageSize));
    }

    @PostMapping(value = "/{applicationId}/detail")
    public ResponseData queryDetail(HttpServletRequest request,@PathVariable Long applicationId) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(Arrays.asList(service.getById(requestContext,applicationId)));
    }


    @PostMapping(value = "/fetchNotServer")
    public ResponseData fetch(final HttpServletRequest request, final String exitsCodes, final ApiServer server,
            @RequestParam(defaultValue = DEFAULT_PAGE) final int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) final int pagesize) {
        IRequest requestContext = createRequestContext(request);
        List<ApiServer> apiServers = service.selectNotExistsServerByApp(requestContext, exitsCodes, server, page, pagesize);
        return new ResponseData(apiServers);
    }

    @PostMapping(value = "/submit")
    public ResponseData update(@RequestBody List<ApiApplication> dto, BindingResult result, HttpServletRequest request) {
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
    public ResponseData remove(HttpServletRequest request, @RequestBody List<ApiApplication> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    @PostMapping(value = "/generatorClientInfo")
    public Map generatorClientInfo(){
        Map<String,String> clientInfo = new HashMap<>(1);
        clientInfo.put("clientId", UUID.randomUUID().toString().replaceAll("\\-",""));
        clientInfo.put("clientSecret",UUID.randomUUID().toString());
        return clientInfo;
    }
}