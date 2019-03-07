package com.jingrui.jrap.security.oauth.controllers;

import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.security.oauth.dto.TokenLogs;
import com.jingrui.jrap.security.oauth.service.ITokenLogsService;
import com.jingrui.jrap.system.controllers.BaseController;
import com.jingrui.jrap.system.dto.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author qixiangyu
 */
@Controller
@RequestMapping(value = "/sys/token/logs")
public class TokenLogsController extends BaseController {

    @Autowired
    private ITokenLogsService service;

    @Autowired
    @Qualifier("tokenServices")
    private ConsumerTokenServices tokenServices;

    @RequestMapping(value = "/query")
    @ResponseBody
    public ResponseData query(TokenLogs dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/revoke")
    @ResponseBody
    public ResponseData revoke(HttpServletRequest request, @RequestParam String token) {
        tokenServices.revokeToken(token);
        return new ResponseData();
    }
}