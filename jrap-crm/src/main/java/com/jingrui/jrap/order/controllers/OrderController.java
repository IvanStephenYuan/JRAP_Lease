package com.jingrui.jrap.order.controllers;

import com.jingrui.jrap.code.rule.exception.CodeRuleException;
import com.jingrui.jrap.code.rule.service.ISysCodeRuleProcessService;
import com.jingrui.jrap.customer.dto.Customer;
import com.jingrui.jrap.customer.service.ICustomerService;
import com.jingrui.jrap.item.dto.Item;
import com.jingrui.jrap.item.service.IItemService;
import com.jingrui.jrap.mybatis.util.StringUtil;
import com.jingrui.jrap.product.service.IDocumentTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.Map;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Controller;
import com.jingrui.jrap.system.controllers.BaseController;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.system.dto.ResponseData;
import com.jingrui.jrap.order.dto.Order;
import com.jingrui.jrap.order.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import org.springframework.validation.BindingResult;
import java.util.List;

@Controller
@Api(value = "OrderController", tags = {"订单接口"})
public class OrderController extends BaseController {

  private static final String CUSTOMER_RULE_CODE = "CUSTOMER";
  private static final String CAR_RULE_CODE = "CAR";
  private static final String ORDER_RULE_CODE = "ORDER";


  @Autowired
  private IOrderService service;

  @Autowired
  private ISysCodeRuleProcessService codeRuleProcessService;

  @Autowired
  private ICustomerService customerService;

  @Autowired
  private IDocumentTypeService documentTypeService;

  @Autowired
  private IItemService itemService;


  @RequestMapping(value = "/con/order/query")
  @ResponseBody
  public ResponseData query(Order dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
      @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
    IRequest requestContext = createRequestContext(request);
    return new ResponseData(service.select(requestContext, dto, page, pageSize));
  }

  @RequestMapping(value = "/con/order/submit")
  @ResponseBody
  @ApiOperation(value = "录入订单信息", notes = "通用订单接口", httpMethod = "POST", response = Order.class)
  public ResponseData update(@RequestBody Map tolmapparam, BindingResult result,
      HttpServletRequest request) {
    IRequest requestCtx = createRequestContext(request);
    //订单信息的集合
    List<Map> orederMap = (List<Map>) tolmapparam.get("order");
    //客户信息的集合
    List<Map> customerMap = (List<Map>) tolmapparam.get("customer");
    //租赁物信息的集合
    List<Map> itemMap = (List<Map>) tolmapparam.get("item");
    //定义客户对象的集合
    List<Customer> listcustomer = new ArrayList<>();
    //定义租赁物对象的集合
    List<Item> listitem = new ArrayList<>();
    //定义订单的集合
    List<Order> listorder = new ArrayList<>();
    //先进行客户信息的插入
    for (Map cm : customerMap) {
      String customerCode = cm.get("customerCode").toString();
      Long employeeId = Long.parseLong(cm.get("employeeId").toString());
      String customerCategory = cm.get("customerCategory").toString();
      String customerType = cm.get("customerType").toString();
      String customerSource = cm.get("customerSource").toString();
      Long companyId = Long.parseLong(cm.get("companyId").toString());
      if (customerCode == null || "".equalsIgnoreCase(customerCode)) {
        try {
          if (StringUtil.isNotEmpty(customerCategory) && StringUtil.isNotEmpty(customerType)) {
            String ruleCode = documentTypeService
                .getDocumentCodeRule(customerCategory, customerType);
            if (ruleCode == null || "ERROR".equalsIgnoreCase(ruleCode)) {
              ruleCode = OrderController.CUSTOMER_RULE_CODE;
            }
            customerCode = codeRuleProcessService.getRuleCode(ruleCode);
            cm.put("customerCode", customerCode);
          }
        } catch (CodeRuleException e) {
          e.printStackTrace();
        }
      }
      //设置默认商户
      if (companyId == null) {
        companyId = requestCtx.getCompanyId();
        cm.put("companyId", companyId);
      }
      //设置默认业务员
      if (employeeId == null) {
        employeeId = requestCtx.getEmployeeId();
        cm.put("employeeId", employeeId);
      }
      //设置客户来源
      if (customerSource == null) {
        customerSource = "MANUAL";
        cm.put("customerSource", customerSource);
      }
      // 将map转为Customer对象
      Customer Customer = JSON.parseObject(JSON.toJSONString(cm), Customer.class);
      //将对象放在集合中
      listcustomer.add(Customer);
    }
    //客户插入返回结果
    List<Customer> resultcustomer = customerService.batchUpdate(requestCtx, listcustomer);
    //租赁物的保存
    for (Map item : itemMap) {
      String companyId = item.get("companyId").toString();
      String itemCode = item.get("itemCode").toString();
      String documentCategory = item.get("documentCategory").toString();
      String documentType = item.get("documentType").toString();
      //设置租赁物编码
      if (itemCode == null || "".equalsIgnoreCase(itemCode)) {
        try {
          String ruleCode = documentTypeService.getDocumentCodeRule(documentCategory, documentType);
          if (ruleCode == null || "ERROR".equalsIgnoreCase(ruleCode)) {
            ruleCode = OrderController.CAR_RULE_CODE;
          }
          String carCode = codeRuleProcessService.getRuleCode(ruleCode);
          item.put("itemCode", carCode);
        } catch (CodeRuleException e) {
          e.printStackTrace();
        }
      }
      if (StringUtil.isNotEmpty(companyId)) {
        item.put("companyId", requestCtx.getCompanyId());
      }
      //将map转换成item对象
      Item ritem = JSON.parseObject(JSON.toJSONString(item), Item.class);
      listitem.add(ritem);
    }
    //返回租赁物插入结果
    List<Item> resultitem = itemService.batchUpdate(requestCtx, listitem);
    for (Customer csm : resultcustomer) {
      //循环遍历订单的集合
      for (Map order : orederMap) {
        for (Map imap : itemMap) {
          order.put("customerId", imap.get("customerId"));
          order.put("itemId", imap.get("itemId"));
          String orderCode = order.get("orderCode").toString();
          Long employeeId = Long.parseLong(order.get("employeeId").toString());
          String documentCategory = order.get("documentCategory").toString();
          String documentType = order.get("documentType").toString();
          Long companyId = Long.parseLong(order.get("companyId").toString());
          if (orderCode == null || "".equalsIgnoreCase(orderCode)) {
            try {
              if (StringUtil.isNotEmpty(documentCategory) && StringUtil.isNotEmpty(documentType)) {
                String ruleCode = documentTypeService
                    .getDocumentCodeRule(documentCategory, documentType);
                if (ruleCode == null || "ERROR".equalsIgnoreCase(ruleCode)) {
                  ruleCode = OrderController.ORDER_RULE_CODE;
                }
                orderCode = codeRuleProcessService.getRuleCode(ruleCode);
                order.put("orderCode", orderCode);
              }
            } catch (CodeRuleException e) {
              e.printStackTrace();
            }
          }
          //设置默认商户
          if (companyId == null) {
            companyId = requestCtx.getCompanyId();
            order.put("companyId", companyId);
          }
          //设置默认业务员
          if (employeeId == null) {
            employeeId = requestCtx.getEmployeeId();
            order.put("employeeId", employeeId);
          }
          order.put("customerId", csm.getCustomerId());
          // 将map转为order对象
          Order rorder = JSON.parseObject(JSON.toJSONString(order), Order.class);
          //将对象放在集合中
          listorder.add(rorder);
        }
      }
    }
    return new ResponseData(service.batchUpdate(requestCtx, listorder));
  }

  @RequestMapping(value = "/con/order/remove")
  @ResponseBody
  public ResponseData delete(HttpServletRequest request, @RequestBody List<Order> dto) {
    service.batchDelete(dto);
    return new ResponseData();
  }
}