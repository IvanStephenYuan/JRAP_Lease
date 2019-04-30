/*
 * *
 *  @file com.maddyhome.idea.copyright.pattern.JavaCopyrightVariablesProvider$1@34dd9247$
 *  @CopyRight (C) 2018 ZheJiangJingRui Co. Ltd.
 *  @brief JingRui Application Platform
 *  @author $name$
 *  @email yulong.yuan@jr-info.cn
 *  @date $date$
 * /
 */

package com.jingrui.jrap.fnd.controllers;

import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.system.controllers.BaseController;
import com.jingrui.jrap.system.dto.ResponseData;
import com.jingrui.jrap.fnd.dto.CompanyLimit;
import com.jingrui.jrap.fnd.service.ICompanyLimitService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@Api(value="CompanyLimitController", tags = {"额度接口"})
public class CompanyLimitController extends BaseController {

    @Autowired
    private ICompanyLimitService service;


    @RequestMapping(value = "/fnd/company/limit/query")
    @ResponseBody
    @ApiOperation(value="获取额度信息", notes = "通用额度接口", httpMethod="GET", response=CompanyLimit.class)
    public ResponseData query(CompanyLimit dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/fnd/company/limit/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<CompanyLimit> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/fnd/company/limit/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<CompanyLimit> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }
    /*
     * 商户入网工作流启动
     * */
    @RequestMapping(value = "/wfl/runtime/processInstances/company/limit/apply")
    @ResponseBody
    @ApiOperation(value="额度申请", notes = "额度申请工作流接口", httpMethod="GET")
    public ResponseData createVacationInstance(String companyId,
                                                      HttpServletRequest httpRequest, HttpServletResponse response) {
        IRequest iRequest = createRequestContext(httpRequest);
        service.createVacationInstance(iRequest, companyId);
        return new ResponseData();
    }

    /**
     * 商户信息（基础信息和额度信息）变更
     * @param dto
     * @param result
     * @param request
     * @return
     */
    @RequestMapping(value = "/fnd/company/limit/modify")
    @ResponseBody
    @ApiOperation(value="商户信息变更", notes = "商户信息变更接口", httpMethod="GET")
    public ResponseData modify(@RequestBody CompanyLimit dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        service.modify(requestCtx, dto);
        return new ResponseData();
    }


    /**
     * 额度调整
     * @param dto
     * @param result
     * @param request
     * @return
     */
    @RequestMapping(value = "/fnd/company/limit/adjust")
    @ResponseBody
    @ApiOperation(value="额度调整", notes = "额度调整接口", httpMethod="GET")
    public ResponseData adjust(@RequestBody List<CompanyLimit> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        service.adjust(requestCtx, dto);
        return new ResponseData();
    }


}