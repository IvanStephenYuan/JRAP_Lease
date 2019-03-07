package com.jingrui.jrap.mail.controllers;

import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.core.exception.BaseException;
import com.jingrui.jrap.mail.dto.MessageAccount;
import com.jingrui.jrap.mail.service.IMessageAccountService;
import com.jingrui.jrap.system.controllers.BaseController;
import com.jingrui.jrap.system.dto.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 对消息账号的操作.
 *
 * @author Clerifen Li
 */
@RestController
@RequestMapping(value = {"/sys/messageAccount", "/api/sys/messageAccount"})
public class MessageAccountController extends BaseController {

    @Autowired
    private IMessageAccountService service;

    @RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseData getMessageAccount(HttpServletRequest request, MessageAccount example,
                                          @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                          @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.selectMessageAccounts(requestContext, example, page, pagesize));
    }

    @RequestMapping(value = "/queryAccount", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseData getMessageAccountPassword(HttpServletRequest request, MessageAccount example) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.selectMessageAccountPassword(requestContext, example, 1, 1));
    }

    @PostMapping(value = "/add")
    public ResponseData addMessageAccount(HttpServletRequest request, @RequestBody MessageAccount obj, BindingResult result) throws BaseException {
        //没意义的值
        obj.setObjectVersionNumber(0L);

        getValidator().validate(obj, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestContext = createRequestContext(request);
        service.createMessageAccount(requestContext, obj);
        return new ResponseData();
    }

    @PostMapping(value = "/update")
    public ResponseData updateMessageAccount(HttpServletRequest request, @RequestBody MessageAccount obj, BindingResult result) throws BaseException {
        //没意义的值
        obj.setObjectVersionNumber(0L);

        getValidator().validate(obj, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestContext = createRequestContext(request);
        service.updateMessageAccount(requestContext, obj);
        return new ResponseData();
    }

    @PostMapping(value = "/updatePasswordOnly")
    public ResponseData updateMessageAccountPasswordOnly(HttpServletRequest request, @RequestBody MessageAccount obj, BindingResult result) throws BaseException {
        //没意义的值
        obj.setObjectVersionNumber(0L);

        getValidator().validate(obj, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestContext = createRequestContext(request);
        service.updateMessageAccountPasswordOnly(requestContext, obj);
        return new ResponseData();
    }

    @PostMapping(value = "/remove")
    public ResponseData deleteMessageAccount(HttpServletRequest request, @RequestBody List<MessageAccount> objs) throws BaseException {
        IRequest requestContext = createRequestContext(request);
        service.batchDelete(requestContext, objs);
        return new ResponseData();
    }

}
