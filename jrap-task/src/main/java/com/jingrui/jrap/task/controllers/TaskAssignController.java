package com.jingrui.jrap.task.controllers;

import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.system.controllers.BaseController;
import com.jingrui.jrap.system.dto.ResponseData;
import com.jingrui.jrap.task.dto.TaskAssign;
import com.jingrui.jrap.task.service.ITaskAssignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 任务权限控制器.
 *
 * @author lijian.yin@jingrui.com
 * @date 2017/11/15.
 **/

@RestController
@RequestMapping({"/sys/task/assign", "/api/sys/task/assign"})
public class TaskAssignController extends BaseController {

    @Autowired
    private ITaskAssignService service;

    @RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseData query(TaskAssign dto, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.query(requestContext, dto));
    }

    @PostMapping(value = "/submit")
    public ResponseData update(@RequestBody List<TaskAssign> dto, BindingResult result, HttpServletRequest request) {
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
    public ResponseData remove(HttpServletRequest request, @RequestBody List<TaskAssign> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    @PostMapping(value = "/selectUnbound")
    public ResponseData queryUnbound(@RequestParam(required = false) String ids, HttpServletRequest request) {
        IRequest iRequest = createRequestContext(request);
        List<String> idList = new ArrayList<>();
        if (ids != null && !ids.isEmpty()) {
            idList = Arrays.asList(ids.split(","));
        }
        return new ResponseData(service.queryUnbound(iRequest, idList));
    }
}