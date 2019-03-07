package com.jingrui.jrap.system.controllers.sys;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.jingrui.jrap.system.dto.ResponseData;
import com.jingrui.jrap.system.dto.Prompt;
import com.jingrui.jrap.core.exception.TokenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.jingrui.jrap.system.controllers.BaseController;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.system.service.IPromptService;

/**
 * 描述维护.
 * 
 * @author shengyang.zhou@jingrui.com
 * @date 2016/6/9.
 */
@RestController
@RequestMapping(value = {"/sys/prompt", "/api/sys/prompt"})
public class PromptController extends BaseController {

    @Autowired
    private IPromptService promptService;

    @RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST} )
    public ResponseData query(HttpServletRequest request,Prompt prompt, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                   @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
        IRequest iRequest = createRequestContext(request);
        return new ResponseData(promptService.select(iRequest, prompt, page, pagesize));
    }

    @PostMapping(value = "/submit")
    public ResponseData submit(@RequestBody List<Prompt> prompts, BindingResult result,
            HttpServletRequest request) throws TokenException {
        checkToken(request, prompts);
        getValidator().validate(prompts, result);
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(promptService.submit(requestCtx, prompts));
    }

    @PostMapping(value = "/remove")
    public ResponseData remove(HttpServletRequest request, @RequestBody List<Prompt> prompts) throws TokenException {
        checkToken(request, prompts);
        IRequest requestCtx = createRequestContext(request);
        promptService.submit(requestCtx, prompts);
        return new ResponseData();
    }
}
