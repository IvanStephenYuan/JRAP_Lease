/*
 * *
 *  @file com.maddyhome.idea.copyright.pattern.JavaCopyrightVariablesProvider$1@d082332$
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
import com.jingrui.jrap.fnd.dto.LimitDetail;
import com.jingrui.jrap.fnd.service.ILimitDetailService;
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
public class LimitDetailController extends BaseController{

@Autowired
private ILimitDetailService service;


@RequestMapping(value = "/fnd/limit/detail/query")
@ResponseBody
public ResponseData query(LimitDetail dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                          @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
    IRequest requestContext = createRequestContext(request);
    return new ResponseData(service.select(requestContext,dto,page,pageSize));
}

@RequestMapping(value = "/fnd/limit/detail/submit")
@ResponseBody
public ResponseData update(@RequestBody List<LimitDetail> dto, BindingResult result, HttpServletRequest request){
    getValidator().validate(dto, result);
    if (result.hasErrors()) {
    ResponseData responseData = new ResponseData(false);
    responseData.setMessage(getErrorMessage(result, request));
    return responseData;
    }
    IRequest requestCtx = createRequestContext(request);
    return new ResponseData(service.batchUpdate(requestCtx, dto));
}

@RequestMapping(value = "/fnd/limit/detail/remove")
@ResponseBody
public ResponseData delete(HttpServletRequest request,@RequestBody List<LimitDetail> dto){
    service.batchDelete(dto);
    return new ResponseData();
}
}