package com.jingrui.jrap.function.controllers;

import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.core.exception.BaseException;
import com.jingrui.jrap.function.dto.Resource;
import com.jingrui.jrap.function.dto.ResourceItem;
import com.jingrui.jrap.function.service.IResourceItemService;
import com.jingrui.jrap.system.controllers.BaseController;
import com.jingrui.jrap.system.dto.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 对权限组件的操作.
 *
 * @author qiang.zeng@jingrui.com
 * @date 2017/6/30.
 */
@RestController
@RequestMapping(value = {"/sys/resourceItem", "/api/sys/resourceItem"})
public class ResourceItemController extends BaseController {

    @Autowired
    private IResourceItemService resourceItemService;

    @RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseData queryResourceItems(HttpServletRequest request, Resource resource) {
        return new ResponseData(resourceItemService.selectResourceItems(createRequestContext(request), resource));
    }

    @PostMapping(value = "/submit")
    public ResponseData submitResourceItems(HttpServletRequest request, @RequestBody List<ResourceItem> resourceItems,
                                            BindingResult result) throws BaseException {
        getValidator().validate(resourceItems, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        return new ResponseData(resourceItemService.batchUpdate(createRequestContext(request), resourceItems));
    }

    @PostMapping(value = "/remove")
    public ResponseData removeResourceItems(HttpServletRequest request, @RequestBody List<ResourceItem> resourceItems)
            throws BaseException {
        IRequest requestContext = createRequestContext(request);
        resourceItemService.batchDelete(requestContext, resourceItems);
        return new ResponseData();
    }

}
