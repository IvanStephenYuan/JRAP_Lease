package com.jingrui.jrap.security.permission.controllers;

import org.springframework.stereotype.Controller;
import com.jingrui.jrap.system.controllers.BaseController;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.system.dto.ResponseData;
import com.jingrui.jrap.security.permission.dto.DataPermissionRuleDetail;
import com.jingrui.jrap.security.permission.service.IDataPermissionRuleDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.BindingResult;

import java.util.List;

/**
 * @author jialong.zuo@jingrui.com on 2017/12/8
 */
@RestController
@RequestMapping(value = {"/sys/data/permission/rule/detail","/api/sys/data/permission/rule/detail"})
public class DataPermissionRuleDetailController extends BaseController {

    @Autowired
    private IDataPermissionRuleDetailService service;


    @RequestMapping(value = "/query")
    public ResponseData query(DataPermissionRuleDetail dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) throws IllegalAccessException {
        IRequest requestContext = createRequestContext(request);
        List<DataPermissionRuleDetail> dataMaskRuleManageDetails = service.selectRuleManageDetail(dto, page, pageSize, requestContext);
        return new ResponseData(dataMaskRuleManageDetails);
    }

    @PostMapping(value = "/submit")
    public ResponseData update(@RequestBody List<DataPermissionRuleDetail> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.updateDataMaskRuleDetail(requestCtx, dto));
    }

    @PostMapping(value = "/remove")
    public ResponseData delete(HttpServletRequest request, @RequestBody List<DataPermissionRuleDetail> dto) {
        service.removeDataMaskRuleDetail(dto);
        return new ResponseData();
    }
}