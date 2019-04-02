package com.jingrui.jrap.product.controllers;

import com.jingrui.jrap.code.rule.exception.CodeRuleException;
import com.jingrui.jrap.code.rule.service.ISysCodeRuleProcessService;
import com.jingrui.jrap.fnd.dto.Company;
import com.jingrui.jrap.fnd.service.ICompanyService;
import com.jingrui.jrap.product.dto.ItemModel;
import net.bytebuddy.implementation.auxiliary.AuxiliaryType;
import org.springframework.stereotype.Controller;
import com.jingrui.jrap.system.controllers.BaseController;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.system.dto.ResponseData;
import com.jingrui.jrap.product.dto.Product;
import com.jingrui.jrap.product.service.IProductService;
import com.jingrui.jrap.mybatis.common.Criteria;
import com.jingrui.jrap.mybatis.common.query.WhereField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ProductController extends BaseController {
    public final static String PRODUCT_RULE_CODE = "PRODUCT";

    @Autowired
    private IProductService service;

    @Autowired
    private ICompanyService companyService;

    @Autowired
    private ISysCodeRuleProcessService codeRuleProcessService;

    @RequestMapping(value = "/pro/product/query")
    @ResponseBody
    public ResponseData query(Product dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        //设置查询条件
        dto.setCompanyId(requestContext.getCompanyId());
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField(ItemModel.FIELD_MODEL_ID));
        return new ResponseData(service.selectOptions(requestContext, dto, criteria, page, pageSize));
    }

    @RequestMapping(value = "/pro/product/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<Product> dto, BindingResult result, HttpServletRequest request) {
        IRequest requestCtx = createRequestContext(request);

        //设置默认值
        for(Product record : dto){
            String productCode = record.getProductCode();
            //设置默认商户
            if(record.getCompanyId() == null){
                record.setCompanyId(requestCtx.getCompanyId());
            }

            if(productCode == null || "".equalsIgnoreCase(productCode)){
                try {
                    Long companyId = record.getCompanyId();
                    Company company = new Company();
                    company.setCompanyId(companyId);
                    company = companyService.selectByPrimaryKey(requestCtx, company);

                    Map<String, String> map = new HashMap<String, String>();
                    map.put("company", company.getCompanyCode());
                    productCode = codeRuleProcessService.getRuleCode(ProductController.PRODUCT_RULE_CODE, map);
                    record.setProductCode(productCode);
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

    @RequestMapping(value = "/pro/product/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<Product> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }
}