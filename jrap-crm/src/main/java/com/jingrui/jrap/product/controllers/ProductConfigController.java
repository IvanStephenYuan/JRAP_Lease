package com.jingrui.jrap.product.controllers;

import com.jingrui.jrap.generator.dto.DBColumn;
import com.jingrui.jrap.generator.dto.DBTable;
import com.jingrui.jrap.generator.service.IJrapGeneratorService;
import com.jingrui.jrap.system.dto.UserDashboard;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import com.jingrui.jrap.system.controllers.BaseController;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.system.dto.ResponseData;
import com.jingrui.jrap.product.dto.ProductConfig;
import com.jingrui.jrap.product.service.IProductConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@ComponentScan(basePackages = "com.jingrui.jrap.generator.service.impl")
public class ProductConfigController extends BaseController {

    @Autowired
    private IProductConfigService service;

    @Autowired
    private IJrapGeneratorService jrapGeneratorService;

    @RequestMapping(value = "/pro/product/config/query")
    @ResponseBody
    public ResponseData query(ProductConfig dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/pro/product/config/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<ProductConfig> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/pro/product/config/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<ProductConfig> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value = "/pro/product/getColumns")
    public List<DBColumn> getTableColumns(@RequestParam String tableName){
        DBTable table = jrapGeneratorService.getTableInfo(tableName);
        if(table != null){
            return table.getColumns();
        }else{
            return null;
        }
    }
    // 产品测算freemaker
    @RequestMapping(value = "/product/product_calculate.html")
    public ModelAndView productCalc(HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        ModelAndView view = new ModelAndView(getViewPath() + "/product/product_calculate");
        ProductConfig productConfig = new ProductConfig();
        productConfig.setProductCode(request.getParameter("productCode"));
        productConfig.setConfigType("H");
        List<ProductConfig>  productConfigs =  service.select(requestContext, productConfig, 1, 10);
        view.addObject("heads", productConfigs);
        return view;
    }
}