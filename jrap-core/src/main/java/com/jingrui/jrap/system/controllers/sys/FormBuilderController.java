package com.jingrui.jrap.system.controllers.sys;

import org.springframework.stereotype.Controller;
import com.jingrui.jrap.system.controllers.BaseController;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.system.dto.ResponseData;
import com.jingrui.jrap.system.dto.Form;
import com.jingrui.jrap.system.service.IFormBuilderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class FormBuilderController extends BaseController {

    @Autowired
    private IFormBuilderService service;

    @RequestMapping(value = "sys/form/builder/query")
    @ResponseBody
    public ResponseData queryAll(Form builder, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                 @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext, builder, page, pageSize));
    }

    @RequestMapping(value = "sys/form/builder/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<Form> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "sys/form/builder/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<Form> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }


    @RequestMapping(value = { "/form/{name}"})
    public ModelAndView renderView(@PathVariable String name, Model model) {
        return new ModelAndView(name+".form");
    }

    @RequestMapping(value = { "/form_preview"})
    public ModelAndView previewView(@RequestParam String code, Model model) {
        return new ModelAndView("preview/"+code+".form");
    }



}