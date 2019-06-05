package com.jingrui.jrap.config.controllers;

import com.jingrui.jrap.system.dto.DTOStatus;
import org.springframework.stereotype.Controller;
import com.jingrui.jrap.system.controllers.BaseController;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.system.dto.ResponseData;
import com.jingrui.jrap.config.dto.Channel;
import com.jingrui.jrap.config.service.IChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.BindingResult;
import com.jingrui.jrap.mybatis.common.Criteria;

import java.util.List;

@Controller
public class ChannelController extends BaseController {

    @Autowired
    private IChannelService service;


    @RequestMapping(value = "/acc/channel/query")
    @ResponseBody
    public ResponseData query(Channel dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/acc/channel/selectOptions")
    @ResponseBody
    public ResponseData queryOptions(Channel dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                     @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        return new ResponseData(service.selectOptions(requestContext, dto, criteria, page, pageSize));
    }

    @RequestMapping(value = "/acc/channel/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<Channel> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        // 如果是添加的话 填充公司id
        for (Channel channel :
                dto) {
            if (DTOStatus.ADD.equals(channel.get__status())) {
                channel.setCompanyId(requestCtx.getCompanyId());
            }
        }
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/acc/channel/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<Channel> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }
}