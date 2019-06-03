package com.jingrui.jrap.order.controllers;

import com.jingrui.jrap.code.rule.exception.CodeRuleException;
import com.jingrui.jrap.code.rule.service.ISysCodeRuleProcessService;
import com.jingrui.jrap.customer.dto.Customer;
import com.jingrui.jrap.customer.mapper.CustomerMapper;
import com.jingrui.jrap.customer.service.ICustomerService;
import com.jingrui.jrap.order.mapper.GuarantorMapper;
import com.jingrui.jrap.product.service.IDocumentTypeService;
import java.util.ArrayList;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import com.jingrui.jrap.system.controllers.BaseController;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.system.dto.ResponseData;
import com.jingrui.jrap.order.dto.Guarantor;
import com.jingrui.jrap.order.service.IGuarantorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import org.springframework.validation.BindingResult;
import com.jingrui.jrap.mybatis.common.Criteria;
import java.util.List;

@Controller
public class GuarantorController extends BaseController {

  private static final String CUSTOMER_RULE_CODE = "CUSTOMER";


  @Autowired
  private IGuarantorService service;
  @Autowired
  private CustomerMapper customerMapper;
  @Autowired
  private ICustomerService customerService;
  @Autowired
  private ISysCodeRuleProcessService codeRuleProcessService;

  @Autowired
  private IDocumentTypeService documentTypeService;

  @Autowired
  private GuarantorMapper guarantorMapper;


  @RequestMapping(value = "/con/guarantor/query")
  @ResponseBody
  public ResponseData query(Guarantor dto, HttpServletRequest request) {
    IRequest requestContext = createRequestContext(request);
    return new ResponseData(guarantorMapper.selectguaranter(dto));
  }

  @RequestMapping(value = "/con/guarantor/selectOptions")
  @ResponseBody
  public ResponseData queryOptions(Guarantor dto,
      @RequestParam(defaultValue = DEFAULT_PAGE) int page,
      @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
    IRequest requestContext = createRequestContext(request);
    Criteria criteria = new Criteria(dto);
    return new ResponseData(service.selectOptions(requestContext, dto, criteria, page, pageSize));
  }

  @RequestMapping(value = "/con/guarantor/submit")
  @ResponseBody
  public ResponseData update(@RequestBody List<Guarantor> dto, BindingResult result,
      HttpServletRequest request) {
    getValidator().validate(dto, result);
    if (result.hasErrors()) {
      ResponseData responseData = new ResponseData(false);
      responseData.setMessage(getErrorMessage(result, request));
      return responseData;
    }
    IRequest requestCtx = createRequestContext(request);
    for (Guarantor guarantor : dto) {
      Long customerid;
      Long id = guarantor.getGuarantorId();
      if (id == null) {
        guarantor.set__status("add");
      } else {
        guarantor.set__status("update");
      }
      //通过证件号和主手机号查询是否有此担保人
      String idType = guarantor.getIdType();
      String idNo = guarantor.getIdNo();
      String telphone = guarantor.getTelphone();
      String customerName = guarantor.getCustomerName();
      String enableFlag = guarantor.getEnabledFlag();
      String homeAddress = guarantor.getHomeAddress();
      //通过手机号码和身份证号码查询是否customer表中有记录
      if (StringUtils.isNotEmpty(telphone)) {
        Customer qc = new Customer();
        qc.setTelphone(telphone);
        if (StringUtils.isNotEmpty(idNo)) {
          qc.setIdNo(idNo);
        }
        qc.setCustomerCategory("GUARANTOR");
        List<Customer> lcus = customerMapper.selectCustomer(qc);
        if (lcus.size() <= 0) {
          //如果为空则将联系人插入客户表中，返回customerid
          String customerCode;
          Customer iscustomer = new Customer();
          iscustomer.setCustomerName(customerName);
          iscustomer.setCustomerCategory("GUARANTOR");
          if (idType.equals("BT")) {
            iscustomer.setCustomerClass("ORG");
            iscustomer.setCustomerType("GUTA_ORG");
          } else {
            iscustomer.setCustomerClass("NP");
            iscustomer.setCustomerType("GUTA_NP");
          }
          iscustomer.setTelphone(telphone);
          iscustomer.setIdType(idType);
          iscustomer.setIdNo(idNo);
          iscustomer.setEnabledFlag(enableFlag);
          iscustomer.setHomeAddress(homeAddress);
          iscustomer.set__status("add");
          //设置默认商户
          iscustomer.setCompanyId(requestCtx.getCompanyId());
          //设置默认业务员
          iscustomer.setEmployeeId(requestCtx.getEmployeeId());
          try {
            String ruleCode = documentTypeService
                .getDocumentCodeRule(iscustomer.getCustomerCategory(),
                    iscustomer.getCustomerType());
            if (ruleCode == null || "ERROR".equalsIgnoreCase(ruleCode)) {
              ruleCode = GuarantorController.CUSTOMER_RULE_CODE;
            }
            customerCode = codeRuleProcessService.getRuleCode(ruleCode);
            iscustomer.setCustomerCode(customerCode);
          } catch (CodeRuleException e) {
            e.printStackTrace();
          }
          //执行插入
          List<Customer> ilc = new ArrayList<>();
          ilc.add(iscustomer);
          List<Customer> rlc = new ArrayList<>();
          rlc = customerService.batchUpdate(requestCtx, ilc);

          for (Customer rc : rlc) {
            customerid = rc.getCustomerId();
            guarantor.setCustomerId(customerid);
          }
        } else {
          for (Customer cus : lcus) {
            customerid = cus.getCustomerId();
            guarantor.setCustomerId(customerid);
          }
        }
      }
    }
    return new ResponseData(service.batchUpdate(requestCtx, dto));
  }

  @RequestMapping(value = "/con/guarantor/remove")
  @ResponseBody
  public ResponseData delete(HttpServletRequest request, @RequestBody List<Guarantor> dto) {
    service.batchDelete(dto);
    return new ResponseData();
  }
}