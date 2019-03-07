/*
 * #{copyright}#
 */

package com.jingrui.jrap.system.controllers.sys;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jingrui.jrap.core.exception.TokenException;
import com.jingrui.jrap.excel.annotation.ExcelExport;
import com.jingrui.jrap.system.controllers.BaseController;
import com.jingrui.jrap.system.dto.ResponseData;
import com.jingrui.jrap.system.dto.CodeValue;
import com.jingrui.jrap.system.service.ICodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.system.dto.Code;

/**
 * 快速编码Controller.
 * 
 * @author njq.niu@jingrui.com
 *
 *         2016年3月2日
 */
@Controller
@RequestMapping(value = {"/sys", "/api/sys"})
public class CodeController extends BaseController {

    @Autowired
    private ICodeService codeService;

    /**
     * 获取快速编码对象.
     * 
     * @param code
     *            Code
     * @param page
     *            起始页
     * @param pagesize
     *            分页大小
     * @param request
     *            HttpServletRequest
     * @return ResponseData
     */
    @RequestMapping(value = "/code/query")
    @ExcelExport(table = Code.class)
    @ResponseBody
    public ResponseData getCodes(Code code, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                 @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize, HttpServletRequest request, HttpServletResponse httpServletResponse) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(codeService.selectCodes(requestContext, code, page, pagesize));
    }

    /**
     * 查询快速编码值.
     * 
     * @param value
     *            CodeValue
     * @param request
     *            HttpServletRequest
     * @return ResponseData
     */
    @RequestMapping(value = "/codevalue/query")
    @ResponseBody
    public ResponseData getCodeValues(CodeValue value, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(codeService.selectCodeValues(requestContext, value));
    }

    /**
     * 删除快速编码.
     * 
     * @param codes
     *            codes
     * @param request
     *            HttpServletRequest
     * @return ResponseData
     */
    @RequestMapping(value = "/code/remove", method = RequestMethod.POST)
    public ResponseData removeCodes(@RequestBody List<Code> codes, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        codeService.batchDelete(requestContext, codes);
        return new ResponseData();
    }

    /**
     * 删除快速编码值.
     * 
     * @param values
     *            values
     * @param request
     *            HttpServletRequest
     * @return ResponseData
     */
    @RequestMapping(value = "/codevalue/remove", method = RequestMethod.POST)
    public ResponseData removeValues(@RequestBody List<CodeValue> values, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        codeService.batchDeleteValues(requestContext, values);
        return new ResponseData();
    }

    /**
     * 提交快速编码对象.
     * 
     * @param codes
     *            codes
     * @param result
     *            BindingResult
     * @param request
     *            HttpServletRequest
     * @return ResponseData
     */
    @RequestMapping(value = "/code/submit", method = RequestMethod.POST)
    public ResponseData submitCode(@RequestBody List<Code> codes, BindingResult result, HttpServletRequest request) throws TokenException {
        checkToken(request, codes);
        getValidator().validate(codes, result);
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(codeService.batchUpdate(requestContext, codes));
    }

    /**
     * 根据parentCode,value,获取子快码列表
     *
     * @param parentCode
     *            父快码的code
     * @param value
     *            父快码的某快码value
     * @param request
     *            HttpServletRequest
     * @return ResponseData
     */
    @RequestMapping(value = "/code/queryChildCodeValue")
    @ResponseBody
    public ResponseData getChildCodes(@RequestParam String parentCode,
                                 @RequestParam String value, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(codeService.getChildCodeValue(requestContext,parentCode,value));
    }


    /**
     * 根据parentCode,value,获取子快码列表
     *
     * @param codeValue
     *          子快码
     * @param request
     *            HttpServletRequest
     * @return ResponseData
     */
    @RequestMapping(value = "/code/getCodeValueById")
    @ResponseBody
    public ResponseData getCodeValueById(@RequestBody CodeValue codeValue, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(Arrays.asList(codeService.getCodeValueById(codeValue.getCodeValueId())));
    }
}
