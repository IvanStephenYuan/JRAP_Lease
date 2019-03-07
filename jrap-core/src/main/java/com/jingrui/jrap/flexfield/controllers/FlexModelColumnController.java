package com.jingrui.jrap.flexfield.controllers;

import com.jingrui.jrap.system.controllers.BaseController;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.system.dto.ResponseData;
import com.jingrui.jrap.flexfield.dto.FlexModelColumn;
import com.jingrui.jrap.flexfield.service.IFlexModelColumnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = {"/fnd/flex", "/api/fnd/flex"})
public class FlexModelColumnController extends BaseController {

    @Autowired
    private IFlexModelColumnService service;


    @RequestMapping(value = "/model/column/query",method = {RequestMethod.GET,RequestMethod.POST})
    public ResponseData query(FlexModelColumn dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.selectOptions(requestContext, dto, null,page, pageSize));
    }

    @PostMapping(value = "/model/column/submit")
    public ResponseData update(@RequestBody List<FlexModelColumn> dto, HttpServletRequest request, BindingResult result) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @PostMapping(value = "/model/column/remove")
    public ResponseData delete(HttpServletRequest request, @RequestBody List<FlexModelColumn> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value = "/column/queryAll",method = {RequestMethod.GET,RequestMethod.POST})
    public ResponseData getTableColumn(HttpServletRequest request, String tableName) {
        return new ResponseData(service.getTableColumn(tableName));
    }

}