package com.jingrui.jrap.function.controllers;

import com.jingrui.jrap.core.exception.BaseException;
import com.jingrui.jrap.function.dto.ResourceCustomization;
import com.jingrui.jrap.function.service.IResourceCustomizationService;
import com.jingrui.jrap.system.controllers.BaseController;
import com.jingrui.jrap.system.dto.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 对资源合并配置项的操作.
 *
 * @author zhizheng.yang@jingrui.com
 */
@RestController
@RequestMapping(value = {"/sys/resourceCustomization", "/api/sys/resourceCustomization"})
public class ResourceCustomizationController extends BaseController {

    @Autowired
    private IResourceCustomizationService resourceCustomizationService;


    @RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseData query(HttpServletRequest request, Long resourceId) {
        return new ResponseData(resourceCustomizationService.selectResourceCustomizationsByResourceId(resourceId));
    }

    @PostMapping(value = "/submit")
    public ResponseData submit(HttpServletRequest request, @RequestBody List<ResourceCustomization> resourceCustomizations,
                               BindingResult result) throws BaseException {
        getValidator().validate(resourceCustomizations, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        return new ResponseData(resourceCustomizationService.batchUpdate(createRequestContext(request), resourceCustomizations));
    }

    @PostMapping(value = "/remove")
    public ResponseData remove(HttpServletRequest request, @RequestBody List<ResourceCustomization> resourceCustomizations)
            throws BaseException {
        resourceCustomizationService.batchDelete(resourceCustomizations);
        return new ResponseData();
    }

}
