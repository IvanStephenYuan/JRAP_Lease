package com.jingrui.jrap.customer.controllers;

import com.jingrui.jrap.finance.dto.Bank;
import com.jingrui.jrap.hr.dto.Position;
import com.jingrui.jrap.mybatis.common.Criteria;
import com.jingrui.jrap.mybatis.common.query.Comparison;
import com.jingrui.jrap.mybatis.common.query.WhereField;
import org.springframework.stereotype.Controller;
import com.jingrui.jrap.system.controllers.BaseController;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.system.dto.ResponseData;
import com.jingrui.jrap.customer.dto.Account;
import com.jingrui.jrap.customer.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import org.springframework.validation.BindingResult;
import java.util.List;

    @Controller
    public class AccountController extends BaseController{

    @Autowired
    private IAccountService service;


    @RequestMapping(value = "/afd/account/query")
    @ResponseBody
    public ResponseData query(Account dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        //return new ResponseData(service.select(requestContext,dto,page,pageSize));
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField(Bank.FIELD_BANK_ID));
        return new ResponseData(service.selectOptions(requestContext,dto, criteria, page, pageSize));
    }

    @RequestMapping(value = "/afd/account/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<Account> dto, BindingResult result, HttpServletRequest request){
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
        ResponseData responseData = new ResponseData(false);
        responseData.setMessage(getErrorMessage(result, request));
        return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/afd/account/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request,@RequestBody List<Account> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }
    }