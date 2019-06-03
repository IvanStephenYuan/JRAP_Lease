/*
 * *
 *  @file com.maddyhome.idea.copyright.pattern.JavaCopyrightVariablesProvider$1@40eeabdc$
 *  @CopyRight (C) 2018 ZheJiangJingRui Co. Ltd.
 *  @brief JingRui Application Platform
 *  @author $name$
 *  @email yulong.yuan@jr-info.cn
 *  @date $date$
 * /
 */

package com.jingrui.jrap.finance.controllers;

import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.finance.dto.PeriodSets;
import com.jingrui.jrap.finance.dto.Periods;
import com.jingrui.jrap.finance.service.IPeriodsService;
import com.jingrui.jrap.mybatis.common.Criteria;
import com.jingrui.jrap.system.controllers.BaseController;
import com.jingrui.jrap.system.dto.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class PeriodsController extends BaseController {

    @Autowired
    private IPeriodsService service;


    @RequestMapping(value = "/fin/periods/query")
    @ResponseBody
    public ResponseData query(Periods dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/fin/periods/selectOptions")
    @ResponseBody
    public ResponseData queryOptions(Periods dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                     @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        return new ResponseData(service.selectOptions(requestContext, dto, criteria, page, pageSize));
    }

    @RequestMapping(value = "/fin/periods/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<Periods> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/fin/periods/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<Periods> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    /**
     * 创建期间
     * @param dto
     * @param result
     * @param request
     * @return
     */
    @RequestMapping(value = "/fin/periods/creatPeriods")
    @ResponseBody
    public ResponseData creatPeriods(@RequestBody PeriodSets dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.creatPeriods(requestCtx, dto));
    }

    /**
     *  期间变更
     * @param request
     * @param dto
     * @return
     */
    @RequestMapping(value = "/fin/periods/modify")
    @ResponseBody
    public ResponseData modify(HttpServletRequest request, @RequestBody Periods dto) {
        IRequest requestCtx = createRequestContext(request);
        service.modify(requestCtx,dto);
        return new ResponseData();
    }
}