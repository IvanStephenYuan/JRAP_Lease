package com.jingrui.jrap.mail.controllers;

import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.mail.dto.MessageEmailProperty;
import com.jingrui.jrap.mail.service.IMessageEmailPropertyService;
import com.jingrui.jrap.system.controllers.BaseController;
import com.jingrui.jrap.system.dto.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 对邮件服务器属性的操作.
 *
 * @author qiang.zeng@jingrui.com
 */
@RestController
@RequestMapping(value = {"/sys/message/email/property", "/api/sys/message/email/property"})
public class MessageEmailPropertyController extends BaseController {

    @Autowired
    private IMessageEmailPropertyService service;


    @RequestMapping(value = "query", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseData query(MessageEmailProperty dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "remove")
    public ResponseData delete(HttpServletRequest request, @RequestBody List<MessageEmailProperty> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }
}