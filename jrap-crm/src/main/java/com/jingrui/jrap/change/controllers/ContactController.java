/*
 * *
 *  @file com.maddyhome.idea.copyright.pattern.JavaCopyrightVariablesProvider$1@616b7c95$
 *  @CopyRight (C) 2018 ZheJiangJingRui Co. Ltd.
 *  @brief JingRui Application Platform
 *  @author $name$
 *  @email yulong.yuan@jr-info.cn
 *  @date $date$
 * /
 */

package com.jingrui.jrap.change.controllers;

import com.jingrui.jrap.code.rule.exception.CodeRuleException;
import com.jingrui.jrap.code.rule.service.ISysCodeRuleProcessService;
import com.jingrui.jrap.customer.dto.Customer;
import com.jingrui.jrap.customer.mapper.CustomerMapper;
import com.jingrui.jrap.customer.service.ICustomerService;
import com.jingrui.jrap.product.service.IDocumentTypeService;
import com.jingrui.jrap.change.service.IContactService;
import com.jingrui.jrap.change.service.IChangeService;
import com.jingrui.jrap.change.service.IChangeAssignService;
import com.jingrui.jrap.change.dto.Contact;
import com.jingrui.jrap.change.dto.Change;
import com.jingrui.jrap.change.dto.ChangeAssign;
import com.jingrui.jrap.change.controllers.ContactController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import com.jingrui.jrap.system.controllers.BaseController;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.system.dto.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.BindingResult;

import java.util.List;

@Controller
public class ContactController extends BaseController {

  private static final String CHANGE_RULE_CODE = "CHANGE";
  private static final String CHANGE_DOCUMENT_CATEGORY = "CHANGE";
  private static final String CHANGE_DOCUMENT_TYPE = "CHANGE";
  private static final String CUSTOMER_RULE_CODE = "CUSTOMER";


  @Autowired
  private IContactService service;

  @Autowired
  private IChangeService changeService;
  @Autowired
  private ISysCodeRuleProcessService codeRuleProcessService;

  @Autowired
  private IDocumentTypeService documentTypeService;

  @Autowired
  private IChangeAssignService changeAssignService;

  @Autowired
  private CustomerMapper customerMapper;
  @Autowired
  private ICustomerService customerService;


  @RequestMapping(value = "/con/contact/query")
  @ResponseBody
  public ResponseData query(Contact dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
      @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
    IRequest requestContext = createRequestContext(request);
    return new ResponseData(service.select(requestContext, dto, page, pageSize));
  }

  @RequestMapping(value = "/con/contact/submit")
  @ResponseBody
  public ResponseData update(@RequestBody List<Contact> dto, BindingResult result,
      HttpServletRequest request) {
    getValidator().validate(dto, result);
    if (result.hasErrors()) {
      ResponseData responseData = new ResponseData(false);
      responseData.setMessage(getErrorMessage(result, request));
      return responseData;
    }
    IRequest requestCtx = createRequestContext(request);
    for (Contact concat : dto) {
      concat.setCompanyId(requestCtx.getCompanyId());
      String enablefg = concat.getEnabledFlag();
      if (StringUtils.isNotEmpty(enablefg)) {
        if ("N".equals(enablefg)) {
          concat.setStatus("REFUSE");
        } else {
          concat.setStatus("NEW");
        }
      }
    }
    return new ResponseData(service.batchUpdate(requestCtx, dto));
  }

  @RequestMapping(value = "/con/contact/remove")
  @ResponseBody
  public ResponseData delete(HttpServletRequest request, @RequestBody List<Contact> dto) {
    service.batchDelete(dto);
    return new ResponseData();
  }

  //商机分配接口
  @RequestMapping(value = "/con/contact/batchassign")
  @ResponseBody
  public ResponseData batchassign(@RequestBody Map conAssmap, BindingResult result,
      HttpServletRequest request) {
    IRequest requestCtx = createRequestContext(request);
    List<ArrayList<String>> lcontact = (List<ArrayList<String>>) conAssmap.get("contact");
    Map mchangeAssign = (Map) conAssmap.get("changeassign");
    //定义一个联系人的数组
    List<String> concat = new ArrayList<>();
    concat = lcontact.get(0);
    Contact querycontact;
    Contact contact;
    List<Change> lChange = new ArrayList<>();
    //定义一个联系人的集合
    List<Contact> ulcontact = new ArrayList<>();
    Contact ucontact;
    //第一步，将联系人信息插到商机表中
    for (String contactid : concat) {
      querycontact = new Contact();
      querycontact.setContactId(Long.parseLong(contactid));
      contact = service.selectByPrimaryKey(requestCtx, querycontact);
      //如果查出来联系人，则将其放入商机对象中
      if (contact != null) {
        ucontact = contact;
        ucontact.set__status("update");
        ucontact.setStatus("SUBMIT");
        ulcontact.add(ucontact);
        String customerName = contact.getCustomerName();
        String cellphone = contact.getCellphone();
        String cellphon02 = contact.getCellphone02();
        String cellphone03 = contact.getCellphone03();
        Long goodid = contact.getGoodId();
        String idType = contact.getIdType();
        String idNumber = contact.getIdNumber();
        long customerid = -1;
        //通过手机号码和身份证号码查询是否customer表中有记录
        if (StringUtils.isNotEmpty(cellphone)) {
          Customer qc = new Customer();
          qc.setTelphone(cellphone);
          if (StringUtils.isNotEmpty(idNumber)) {
            qc.setIdNo(idNumber);
          }
          List<Customer> lcus = customerMapper.selectCustomer(qc);
          if (lcus.size() <= 0) {
            //如果为空则将联系人插入客户表中，返回customerid
            String customerCode;
            Customer iscustomer = new Customer();
            iscustomer.setCustomerName(customerName);
            iscustomer.setCustomerClass("NP");
            iscustomer.setCustomerCategory("TENANT");
            iscustomer.setCustomerType("TENANT");
            iscustomer.setTelphone(cellphone);
            iscustomer.setIdType(idType);
            iscustomer.setIdNo(idNumber);
            iscustomer.set__status("add");
            //设置默认商户
            iscustomer.setCompanyId(requestCtx.getCompanyId());
            //设置默认业务员
            iscustomer.setEmployeeId(requestCtx.getEmployeeId());
            //设置客户来源
            iscustomer.setCustomerSource("MANUAL");
            try {
              String ruleCode = documentTypeService
                  .getDocumentCodeRule(iscustomer.getCustomerCategory(),
                      iscustomer.getCustomerType());
              if (ruleCode == null || "ERROR".equalsIgnoreCase(ruleCode)) {
                ruleCode = ContactController.CUSTOMER_RULE_CODE;
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
            }
          } else {
            for (Customer cus : lcus) {
              customerid = cus.getCustomerId();
            }

          }
        }
        Change change = new Change();
        change.setDocumentCategory(ContactController.CHANGE_DOCUMENT_CATEGORY);
        change.setDocumentType(ContactController.CHANGE_DOCUMENT_TYPE);
        change.setCustomerId(customerid);
        try {
          String ruleCode = documentTypeService
              .getDocumentCodeRule(change.getDocumentCategory(), change.getDocumentType());
          if (ruleCode == null || "ERROR".equalsIgnoreCase(ruleCode)) {
            ruleCode = ContactController.CHANGE_RULE_CODE;
          }
          String changeCode = codeRuleProcessService.getRuleCode(ruleCode);
          change.setChangeCode(changeCode);
          change.set__status("add");
          change.setCompanyId(requestCtx.getCompanyId());
          if (StringUtils.isNotEmpty(customerName)) {
            change.setCustomerName(customerName);
          }
          if (StringUtils.isNotEmpty(cellphone)) {
            change.setCellphone(cellphone);
          }
          if (StringUtils.isNotEmpty(cellphon02)) {
            change.setCellphone02(cellphon02);
          }
          if (StringUtils.isNotEmpty(cellphone03)) {
            change.setCellphone03(cellphone03);
          }
          if (StringUtils.isNotEmpty(idType)) {
            change.setIdType(idType);
          }
          if (StringUtils.isNotEmpty(idNumber)) {
            change.setIdNumber(idNumber);
          }
          if (goodid != null) {
            change.setGoodId(goodid);
          }
          lChange.add(change);
        } catch (CodeRuleException e) {
          e.printStackTrace();
        }
      }
    }
    List<Change> ficg = changeService.batchUpdate(requestCtx, lChange);
    //第二步，将信息插入分配表中
    //定义一个分配对象
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    ChangeAssign changeAssign = null;
    List<ChangeAssign> lChangeAssign = new ArrayList<>();
    for (Change fchange : ficg) {
      changeAssign = new ChangeAssign();
      for (String contacid : concat) {
        changeAssign.setContactId(Long.parseLong(contacid));
        changeAssign.setChangeId(fchange.getChangeId());
        changeAssign.set__status("add");
        if (mchangeAssign.get("assignNote") != null) {
          String assignnote = String.valueOf(mchangeAssign.get("assignNote"));
          changeAssign.setAssignNote(assignnote);
        }
        if (mchangeAssign.get("assignDate") != null) {
          String assignDate = String.valueOf(mchangeAssign.get("assignDate"));
          try {
            Date dassignDate = sdf.parse(assignDate);
            changeAssign.setAssignDate(dassignDate);
          } catch (ParseException e) {
            e.printStackTrace();
          }
        }
        if (mchangeAssign.get("invalidDate") != null) {
          String invalidDate = String.valueOf(mchangeAssign.get("invalidDate"));
          try {
            Date dinvalidDate = sdf.parse(invalidDate);
            changeAssign.setInvalidDate(dinvalidDate);
          } catch (ParseException e) {
            e.printStackTrace();
          }
        }
        if (mchangeAssign.get("employeeId") != null) {
          Long employeeId = Long.valueOf(String.valueOf(mchangeAssign.get("employeeId")));
          changeAssign.setEmployeeId(employeeId);
        }
        if (mchangeAssign.get("unitId") != null) {
          Long unitId = Long.valueOf(String.valueOf(mchangeAssign.get("unitId")));
          changeAssign.setUnitId(unitId);
        }
        lChangeAssign.add(changeAssign);
      }
    }
    changeAssignService.batchUpdate(requestCtx, lChangeAssign);
    //第三步，将联系人状态改掉
    return new ResponseData(service.batchUpdate(requestCtx, ulcontact));
  }

}