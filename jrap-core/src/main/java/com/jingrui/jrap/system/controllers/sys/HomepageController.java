/*
 * *
 *  @file com.maddyhome.idea.copyright.pattern.JavaCopyrightVariablesProvider$1@589be62a$
 *  @CopyRight (C) 2018 ZheJiangJingRui Co. Ltd.
 *  @brief JingRui Application Platform
 *  @author $name$
 *  @email yulong.yuan@jr-info.cn
 *  @date $date$
 * /
 */

package com.jingrui.jrap.system.controllers.sys;

import com.jingrui.jrap.code.rule.exception.CodeRuleException;
import com.jingrui.jrap.code.rule.service.ISysCodeRuleProcessService;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.mybatis.common.Criteria;
import com.jingrui.jrap.system.controllers.BaseController;
import com.jingrui.jrap.system.dto.Homepage;
import com.jingrui.jrap.system.dto.ResponseData;
import com.jingrui.jrap.system.service.IHomepageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(value="HomepageController", tags = {"首页配置接口"})
public class HomepageController extends BaseController{
    public final static String HOMEPAGE_RULE_CODE = "HOMEPAGE";

    @Autowired
    private ISysCodeRuleProcessService codeRuleProcessService;

    @Autowired
    private IHomepageService service;


    @RequestMapping(value = "/sys/homepage/query")
    @ResponseBody
    @ApiOperation(value="查询首页配置", notes = "首页配置查询接口", httpMethod="POST", response=Homepage.class)
    public ResponseData query(Homepage dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext,dto,page,pageSize));
    }

    @RequestMapping(value = "/sys/homepage/selectOptions")
    @ResponseBody
    @ApiOperation(value="查询首页配置（联表查询）", notes = "首页配置查询接口（联表查询）", httpMethod="POST", response=Homepage.class)
    public ResponseData queryOptions(Homepage dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        return new ResponseData(service.selectOptions(requestContext, dto, criteria, page, pageSize));
    }

    @RequestMapping(value = "/sys/homepage/submit")
    @ResponseBody
    @ApiOperation(value="修改首页配置", notes = "首页配置修改接口", httpMethod="POST", response=Homepage.class)
    public ResponseData update(@RequestBody List<Homepage> dto, BindingResult result, HttpServletRequest request){
        IRequest requestCtx = createRequestContext(request);
        for (Homepage homepage : dto) {
            String code = homepage.getHomepage();
            if (code == null||"".equals(code)) {
                try {
                    code = codeRuleProcessService.getRuleCode(HOMEPAGE_RULE_CODE);
                    homepage.setHomepage(code);
                } catch (CodeRuleException e) {
                    e.printStackTrace();
                }
            }
        }
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
        ResponseData responseData = new ResponseData(false);
        responseData.setMessage(getErrorMessage(result, request));
        return responseData;
        }
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/sys/homepage/remove")
    @ResponseBody
    @ApiOperation(value="删除首页配置", notes = "首页配置删除接口", httpMethod="POST", response=Homepage.class)
    public ResponseData delete(HttpServletRequest request,@RequestBody List<Homepage> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value = "/sys/homepage/queryPath")
    @ResponseBody
    @ApiOperation(value="查询首页路径", notes = "首页路径查询接口", httpMethod="GET")
    public Homepage queryPath(HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);

        return service.queryPath(requestContext);
    }
}