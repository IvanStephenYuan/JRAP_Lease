package com.jingrui.jrap.api.application.controllers;

import com.jingrui.jrap.api.application.dto.ApiAccessLimit;
import com.jingrui.jrap.api.application.service.IApiAccessLimitService;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.system.controllers.BaseController;
import com.jingrui.jrap.system.dto.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 访问限制控制器.
 *
 * @author lijian.yin@jingrui.com
 * @date 2017/11/15.
 **/

@RestController
@RequestMapping(value = {"/sys/application/accessLimit","/api/sys/application/accessLimit"})
public class ApiAccessLimitController extends BaseController{

    @Autowired
    private IApiAccessLimitService apiAccessLimitService;

    @PostMapping(value = "/submit")
    public ResponseData submit(@RequestBody List<ApiAccessLimit> apiAccessLimiteList, HttpServletRequest request) {
        IRequest iRequest = createRequestContext(request);
        apiAccessLimitService.batchUpdate(iRequest, apiAccessLimiteList);
        return new ResponseData(apiAccessLimiteList);
    }
}