package com.jingrui.jrap.mail.controllers;

import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.core.exception.BaseException;
import com.jingrui.jrap.mail.dto.MessageTemplate;
import com.jingrui.jrap.mail.service.IMessageTemplateService;
import com.jingrui.jrap.system.controllers.BaseController;
import com.jingrui.jrap.system.dto.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 对消息模板的操作.
 *
 * @author Clerifen Li
 */
@RestController
@RequestMapping(value = {"/sys/messageTemplate", "/api/sys/messageTemplate"})
public class MessageTemplateController extends BaseController {

    @Autowired
    private IMessageTemplateService service;

    @RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseData getMessageTemplate(HttpServletRequest request, MessageTemplate example,
                                           @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                           @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.selectMessageTemplates(requestContext, example, page, pagesize));
    }

    @PostMapping(value = "/add")
    public ResponseData addMessageTemplate(HttpServletRequest request, @RequestBody MessageTemplate obj, BindingResult result) throws BaseException {

        getValidator().validate(obj, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestContext = createRequestContext(request);
        service.createMessageTemplate(requestContext, obj);
        return new ResponseData();
    }

    @PostMapping(value = "/update")
    public ResponseData updateMessageTemplate(HttpServletRequest request, @RequestBody MessageTemplate obj, BindingResult result) throws BaseException {

        getValidator().validate(obj, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestContext = createRequestContext(request);
        service.updateMessageTemplate(requestContext, obj);
        return new ResponseData();
    }

    @PostMapping(value = "/remove")
    public ResponseData deleteMessageTemplate(HttpServletRequest request, @RequestBody List<MessageTemplate> objs) throws BaseException {
        IRequest requestContext = createRequestContext(request);
        service.batchDelete(requestContext, objs);
        return new ResponseData();
    }

}
