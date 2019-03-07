package com.jingrui.jrap.system.controllers.sys;

import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.system.controllers.BaseController;
import com.jingrui.jrap.system.dto.Lov;
import com.jingrui.jrap.system.dto.LovItem;
import com.jingrui.jrap.system.dto.ResponseData;
import com.jingrui.jrap.system.service.IKendoLovService;
import com.jingrui.jrap.system.service.ILovService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * 通用lov的控制器.
 * 
 * @author njq.niu@jingrui.com
 * @date 2016/2/1
 */

@RestController
@RequestMapping(value = {"/sys", "/api/sys"})
public class LovController extends BaseController {

    @Autowired
    private ILovService lovService;
    
    @Autowired
    private IKendoLovService kendoLovService;

    /**
     * 通用lov配置项查询.
     * 
     * @param item LovItem
     * @param request HttpServletRequest
     * @return ResponseData
     */
    @RequestMapping(value = "/lovitem/query", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseData getLovItems(LovItem item, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(lovService.selectLovItems(requestContext, item));
    }
    
    /**
     * 通用lov查询.
     * 
     * @param lov Lov
     * @param page 起始页
     * @param pagesize 分页大小
     * @param request HttpServletRequest
     * @return ResponseData
     */
    @RequestMapping(value = "/lov/query", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseData queryLov(Lov lov, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(lovService.selectLovs(requestContext, lov, page, pagesize));
    }
    
    /**
     * 根据动态的lovCode获取LOV配置.
     *
     * @param contextPath
     * @param locale
     * @param lovCode
     * @return
     */
    @RequestMapping(value = "/lov/getLovByCustomCode", method = {RequestMethod.GET, RequestMethod.POST})
    public String getLovByCustomCode(@RequestParam("contextPath")String contextPath, @RequestParam("locale")Locale locale, @RequestParam("lovCode")String lovCode){
        return kendoLovService.getLov(contextPath, locale, lovCode);
    }
    
    
    /**
     * 加载通用lov.
     * 
     * @param lovId id
     * @param request HttpServletRequest
     * @return ResponseData
     */
    @RequestMapping(value = "/lov/load", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseData loadLov(@RequestParam Long lovId, HttpServletRequest request) {
        List<Lov> list = new ArrayList<>();
        list.add(lovService.loadLov(lovId));
        return new ResponseData(list);
    }
    
    /**
     * 删除通用lov.
     * 
     * @param items items
     * @return ResponseData
     */
    @PostMapping(value = "/lov/remove")
    public ResponseData removeLov(@RequestBody List<Lov> items) {
        lovService.batchDeleteLov(items);
        return new ResponseData();
    }
    
    /**
     * 删除通用lov配置项.
     * 
     * @param items LovItem
     * @return ResponseData
     */
    @PostMapping(value = "/lovitem/remove")
    public ResponseData removeLovItems(@RequestBody List<LovItem> items) {
        lovService.batchDeleteItems(items);
        return new ResponseData();
    }
    
    /**
     * 保存通用lov. 
     * @param lovs lovs
     * @param result BindingResult
     * @param request HttpServletRequest
     * @return ResponseData
     */
    @PostMapping(value = "/lov/submit")
    public ResponseData submitLov(HttpServletRequest request,@RequestBody List<Lov> lovs,final BindingResult result) {
        getValidator().validate(lovs, result);
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(lovService.batchUpdate(requestContext, lovs));
    }
}
