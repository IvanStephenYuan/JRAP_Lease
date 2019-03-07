package com.jingrui.jrap.mail.controllers;

import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.core.exception.BaseException;
import com.jingrui.jrap.mail.dto.MessageEmailAccount;
import com.jingrui.jrap.mail.service.IMessageEmailAccountService;
import com.jingrui.jrap.system.controllers.BaseController;
import com.jingrui.jrap.system.dto.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 对邮件账号的操作.
 *
 * @author Clerifen Li
 */
@RestController
@RequestMapping(value = {"/sys/messageEmailAccount", "/api/sys/messageEmailAccount"})
public class MessageEmailAccountController extends BaseController {

    @Autowired
    private IMessageEmailAccountService service;

    @RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseData getMessageEmailAccount(HttpServletRequest request, MessageEmailAccount example,
                                               @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                               @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.selectMessageEmailAccounts(requestContext, example, page, pagesize));
    }

    @RequestMapping(value = "/queryAccount", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseData getMessageEmailAccountPassword(HttpServletRequest request, MessageEmailAccount example) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.selectMessageEmailAccountWithPassword(requestContext, example, 1, 1));
    }

    @PostMapping(value = "/remove")
    public ResponseData deleteMessageEmailAccount(HttpServletRequest request, @RequestBody List<MessageEmailAccount> objs) throws BaseException {
        IRequest requestContext = createRequestContext(request);
        service.batchDelete(requestContext, objs);
        return new ResponseData();
    }

}
