package com.jingrui.jrap.function.controllers;

import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.function.dto.ResourceItemElement;
import com.jingrui.jrap.function.service.IResourceItemElementService;
import com.jingrui.jrap.system.controllers.BaseController;
import com.jingrui.jrap.system.dto.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 对权限组件元素的操作.
 *
 * @author qiang.zeng@jingrui.com
 */
@RestController
@RequestMapping(value = {"/sys/resourceItemElement", "/api/sys/resourceItemElement"})
public class ResourceItemElementController extends BaseController {

    @Autowired
    private IResourceItemElementService elementService;

    @RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseData query(HttpServletRequest request, ResourceItemElement element) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(elementService.selectByResourceItemId(requestContext, element));
    }

    @PostMapping(value = "/submit")
    public ResponseData submit(HttpServletRequest request, @RequestBody List<ResourceItemElement> elements, BindingResult result) {
        getValidator().validate(elements, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(elementService.batchUpdate(requestCtx, elements));
    }

    @PostMapping(value = "/remove")
    public ResponseData remove(HttpServletRequest request, @RequestBody List<ResourceItemElement> elements) {
        IRequest requestContext = createRequestContext(request);
        elementService.batchDelete(requestContext, elements);
        return new ResponseData();
    }
}