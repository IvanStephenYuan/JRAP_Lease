package com.jingrui.jrap.system.controllers.sys;

import com.jingrui.jrap.cache.impl.HotkeyCache;
import com.jingrui.jrap.core.web.view.ui.Model;
import com.jingrui.jrap.system.controllers.CommonController;
import org.springframework.stereotype.Controller;
import com.jingrui.jrap.system.controllers.BaseController;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.system.dto.ResponseData;
import com.jingrui.jrap.system.dto.Hotkey;
import com.jingrui.jrap.system.service.IHotkeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller
    public class HotkeyController extends BaseController{

    @Autowired
    private IHotkeyService service;


    @RequestMapping(value = "/sys/hotkey/query")
    @ResponseBody
    public ResponseData query(Hotkey dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        dto.setHotkeyLevel("system");
        dto.setHotkeyLevelId((long)0);
        return new ResponseData(service.selectOptions(requestContext,dto,null));
    }

        @RequestMapping(value = "/sys/preference/hotkey/query")
        public ModelAndView preferenceQuery(HttpServletRequest request) {
            IRequest requestContext = createRequestContext(request);
            ModelAndView view = new ModelAndView();
            view.setViewName("/sys/um/sys_preference_hotkey");
            List<Hotkey> hotkeys = service.preferenceQuery(requestContext);
            view.addObject("hotkeys",hotkeys);
            return view;
        }

    @RequestMapping(value = "/sys/hotkey/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<Hotkey> dto, BindingResult result, HttpServletRequest request){
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
        ResponseData responseData = new ResponseData(false);
        responseData.setMessage(getErrorMessage(result, request));
        return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/sys/hotkey/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request,@RequestBody List<Hotkey> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }
    }