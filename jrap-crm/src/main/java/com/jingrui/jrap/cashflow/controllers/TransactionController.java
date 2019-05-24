package com.jingrui.jrap.cashflow.controllers;

import com.jingrui.jrap.code.rule.exception.CodeRuleException;
import com.jingrui.jrap.code.rule.service.ISysCodeRuleProcessService;
import com.jingrui.jrap.mybatis.common.Criteria;
import org.springframework.stereotype.Controller;
import com.jingrui.jrap.system.controllers.BaseController;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.system.dto.ResponseData;
import com.jingrui.jrap.cashflow.dto.Transaction;
import com.jingrui.jrap.cashflow.service.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.BindingResult;

import java.util.List;

@Controller
public class TransactionController extends BaseController {

    public final static String TRANS_RULE_CODE = "TRANSACTIONNUM";

    @Autowired
    private ITransactionService service;

    @Autowired
    private ISysCodeRuleProcessService codeRuleProcessService;


    @RequestMapping(value = "/csh/transaction/query")
    @ResponseBody
    public ResponseData query(Transaction dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/csh/transaction/selectOptions")
    @ResponseBody
    public ResponseData queryOptions(Transaction dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                     @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        return new ResponseData(service.selectOptions(requestContext, dto, criteria, page, pageSize));
    }

    @RequestMapping(value = "/csh/transaction/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<Transaction> dto, BindingResult result, HttpServletRequest request) {
        IRequest requestCtx = createRequestContext(request);
        for (Transaction transaction : dto) {
            Long transactionNum = transaction.getTransactionNum();
            if (transactionNum == null) {
                try {
                    String num = codeRuleProcessService.getRuleCode(TransactionController.TRANS_RULE_CODE);
                    transaction.setTransactionNum(Long.parseLong(num));
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

    @RequestMapping(value = "/csh/transaction/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<Transaction> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }
}