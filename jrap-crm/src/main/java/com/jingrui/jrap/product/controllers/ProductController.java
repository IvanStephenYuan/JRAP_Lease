package com.jingrui.jrap.product.controllers;

import com.jingrui.jrap.code.rule.exception.CodeRuleException;
import com.jingrui.jrap.code.rule.service.ISysCodeRuleProcessService;
import com.jingrui.jrap.product.dto.ItemModel;
import com.jingrui.jrap.product.dto.ProductConfig;
import com.jingrui.jrap.fnd.dto.Company;
import com.jingrui.jrap.fnd.service.ICompanyService;
import com.jingrui.jrap.product.service.IProductConfigService;
import com.jingrui.jrap.product.calculate.IProductCalculate;
import com.jingrui.jrap.product.calculate.impl.ProductECICalculateImp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import com.jingrui.jrap.system.controllers.BaseController;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.system.dto.ResponseData;
import com.jingrui.jrap.product.dto.Product;
import com.jingrui.jrap.product.dto.ProductCalculateLine;
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

@RequestMapping(value = "/pro/product")
@Controller
@Api(value="ProductController", tags = {"产品接口"})
public class ProductController extends BaseController {
    public final static String PRODUCT_RULE_CODE = "PRODUCT";
    public final static String EFIXED_INSTALLMENT = "ECI"; //等额本息
    public final static String FIXED_DPRINCIPAL = "EC"; //等额本金
    public final static String FIXED_PRINCIPAL_INTEREST = "EPEI"; //等本等息
    public final static String AINTEREST_PRINCIPAL = "EIAP"; //先息后本
    public final static String DINTEREST_PRINCIPAL = "EPPI"; //阶梯降本

    static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private IProductService service;

    @Autowired
    private IProductConfigService configService;

    @Autowired
    private ICompanyService companyService;

    @Autowired
    private ISysCodeRuleProcessService codeRuleProcessService;

    @RequestMapping(value = "/query")
    @ResponseBody
    @ApiOperation(value="获取产品信息", notes = "通用产品接口", httpMethod="GET", response=Product.class)
    public ResponseData query(Product dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        //设置查询条件
        dto.setCompanyId(requestContext.getCompanyId());
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField(ItemModel.FIELD_MODEL_ID));
        return new ResponseData(service.selectOptions(requestContext, dto, criteria, page, pageSize));
    }

    @RequestMapping(value = "/submit")
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

    @RequestMapping(value = "/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<Product> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value = "/calculate")
    @ResponseBody
    @ApiOperation(value="产品计算", notes = "产品计算器", httpMethod="POST", response=ProductCalculateLine.class)
    public ResponseData calculate(@RequestParam(value = "productCode") String productCode,
                                  @RequestBody List<ProductConfig> dto,
                                  HttpServletRequest request){
        System.out.println(productCode);
        System.out.println(dto.size());

        IRequest requestContext = createRequestContext(request);
        Product queryPara = new Product();
        queryPara.setProductCode(productCode);
        Product result = service.selectByPrimaryKey(requestContext, queryPara);
        IProductCalculate calculate;


        switch (result.getCalculate()){
            //等额本息
            case ProductController.EFIXED_INSTALLMENT : {
                logger.info("等额本息");
                calculate = new ProductECICalculateImp();
                //calculate.calculate(null);
                break;
            }
            //等额本金
            case ProductController.FIXED_DPRINCIPAL : {
                logger.info("等额本金");
                break;
            }
            //等本等息
            case ProductController.FIXED_PRINCIPAL_INTEREST : {
                logger.info("等本等息");
                break;
            }
            //先息后本
            case ProductController.AINTEREST_PRINCIPAL : {
                logger.info("等额本息");
                break;
            }
            //阶梯降本
            case ProductController.DINTEREST_PRINCIPAL : {
                logger.info("阶梯降本");
                break;
            }
            default:{
                logger.info("等本等息");
            }
        }

        return new ResponseData();
    }
}