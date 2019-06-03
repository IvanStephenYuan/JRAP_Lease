package com.jingrui.jrap.fnd.controllers;

import com.jingrui.jrap.fnd.mapper.NoticeMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import com.jingrui.jrap.system.controllers.BaseController;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.system.dto.ResponseData;
import com.jingrui.jrap.fnd.dto.Notice;
import com.jingrui.jrap.fnd.service.INoticeService;
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
@Api(value="NoticeController", tags = {"公告接口"})
public class NoticeController extends BaseController{

    @Autowired
    private INoticeService service;

    @Autowired
    private NoticeMapper noticeMapper;

    @RequestMapping(value = "/fnd/notice/query")
    @ResponseBody
    @ApiOperation(value="查询公告信息", notes = "通用公告查询接口", httpMethod="GET", response=Notice.class)
    public ResponseData query(Notice dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext,dto,page,pageSize));
    }

    @RequestMapping(value = "/fnd/notice/queryAll")
    @ResponseBody
    @ApiOperation(value="查询全部公告信息", notes = "查询全部公告信息接口", httpMethod="POST", response=Notice.class)
    public ResponseData queryAll(Notice dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(noticeMapper.select(dto));
    }

    @RequestMapping(value = "/fnd/notice/selectOptions")
    @ResponseBody
    public ResponseData queryOptions(Notice dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        return new ResponseData(service.selectOptions(requestContext, dto, criteria, page, pageSize));
    }

    @RequestMapping(value = "/fnd/notice/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<Notice> dto, BindingResult result, HttpServletRequest request){
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
        ResponseData responseData = new ResponseData(false);
        responseData.setMessage(getErrorMessage(result, request));
        return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/fnd/notice/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request,@RequestBody List<Notice> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }

    /**
     *  公告限制查询
     * @param dto
     * @param page
     * @param pageSize
     * @param request
     * @return
     */
    @RequestMapping(value = "/fnd/notice/limit/query")
    @ResponseBody
    @ApiOperation(value="公告限制查询", notes = "公告限制查询接口", httpMethod="POST", response=Notice.class)
    public ResponseData limitQuery(Notice dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.limitQuery(requestContext,dto,page,pageSize));
    }
}