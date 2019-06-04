package com.jingrui.jrap.order.controllers;

import com.jingrui.jrap.change.dto.Change;
import com.jingrui.jrap.change.service.IChangeService;
import com.jingrui.jrap.code.rule.exception.CodeRuleException;
import com.jingrui.jrap.code.rule.service.ISysCodeRuleProcessService;
import com.jingrui.jrap.customer.dto.Customer;
import com.jingrui.jrap.customer.service.ICustomerService;
import com.jingrui.jrap.item.dto.Item;
import com.jingrui.jrap.item.service.IItemService;
import com.jingrui.jrap.mybatis.util.StringUtil;
import com.jingrui.jrap.order.mapper.OrderMapper;
import com.jingrui.jrap.product.service.IDocumentTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import com.alibaba.fastjson.JSON;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import com.jingrui.jrap.system.controllers.BaseController;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.system.dto.ResponseData;
import com.jingrui.jrap.order.dto.Order;
import com.jingrui.jrap.order.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
    private static final String ORDER_DOCUMENT_CATEGORY = "ORDER";
    private static final String ORDER_DOCUMENT_TYPE = "ORDER";


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
    @Autowired
    private IChangeService changeService;
    @Autowired
    private OrderMapper orderMapper;


    @RequestMapping(value = "/con/order/query")
    @ResponseBody
    public ResponseData query(Order dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(orderMapper.selectOrder(dto));
    }

    @RequestMapping(value = "/con/order/submit")
    @ResponseBody
    @ApiOperation(value = "录入订单信息", notes = "通用订单接口", httpMethod = "POST", response = Order.class)
    public ResponseData update(@RequestBody Map tolmapparam, HttpServletRequest request) {
        IRequest requestCtx = createRequestContext(request);
        //订单信息的集合
        Map order = (Map) tolmapparam.get("order");
        //客户信息的集合
        Map cm = (Map) tolmapparam.get("customer");
        //租赁物信息的集合
        Map item = (Map) tolmapparam.get("item");
        //定义客户对象的集合
        List<Customer> listcustomer = new ArrayList<>();
        //定义租赁物对象的集合
        List<Item> listitem = new ArrayList<>();
        //定义订单的集合
        List<Order> listorder = new ArrayList<>();
        //先进行客户信息的插入
        Object customerId = cm.get("customerId");
        Object customerCode = cm.get("customerCode");
        Object employeeId = cm.get("employeeId");
        Object companyId = cm.get("companyId");
        Object customerCategory = cm.get("customerCategory");
        Object customerType = cm.get("customerType");
        Object customerSource = cm.get("customerSource");
        //将状态接收
        String cstatus = cm.get("__status").toString();
        String ostatus = order.get("__status").toString();
        String istatus = item.get("__status").toString();
        Customer customer = null;
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
        if (customerId == null) {
            if (customerCode == null) {
                try {
                    String ruleCode = null;
                    if (customerCategory != null && customerType != null) {
                        ruleCode = documentTypeService
                                .getDocumentCodeRule(customerCategory.toString(), customerType.toString());
                    }
                    if (ruleCode == null || "ERROR".equalsIgnoreCase(ruleCode)) {
                        ruleCode = OrderController.CUSTOMER_RULE_CODE;
                    }
                    customerCode = codeRuleProcessService.getRuleCode(ruleCode);
                    cm.put("customerCode", customerCode);

                } catch (CodeRuleException e) {
                    e.printStackTrace();
                }
            }
            // 将map转为Customer对象
            customer = JSON.parseObject(JSON.toJSONString(cm), Customer.class);
            customer.set__status(cstatus);
        } else {
            // 将map转为Customer对象
            customer = JSON.parseObject(JSON.toJSONString(cm), Customer.class);
            customer.set__status("update");
        }
        //将对象放在集合中
        listcustomer.add(customer);
        //客户插入返回结果
        List<Customer> resultcustomer = customerService.batchUpdate(requestCtx, listcustomer);
        //租赁物的保存
        Object icompanyId = item.get("companyId");
        Object itemCode = item.get("itemCode");
        Object documentCategory = item.get("documentCategory");
        Object documentType = item.get("documentType");
        //设置租赁物编码
        if (itemCode == null) {
            try {
                String ruleCode = null;
                if (documentCategory != null && documentType != null) {
                    ruleCode = documentTypeService
                            .getDocumentCodeRule(documentCategory.toString(), documentType.toString());
                }
                if (ruleCode == null || "ERROR".equalsIgnoreCase(ruleCode)) {
                    ruleCode = OrderController.CAR_RULE_CODE;
                }
                String carCode = codeRuleProcessService.getRuleCode(ruleCode);
                item.put("itemCode", carCode);
            } catch (CodeRuleException e) {
                e.printStackTrace();
            }
        }
        if (icompanyId != null) {
            item.put("companyId", requestCtx.getCompanyId());
        }
        //将map转换成item对象
        Item ritem = JSON.parseObject(JSON.toJSONString(item), Item.class);
        ritem.set__status(istatus);
        listitem.add(ritem);
        //返回租赁物插入结果
        List<Item> resultitem = itemService.batchUpdate(requestCtx, listitem);
        for (Customer csm : resultcustomer) {
            //循环遍历订单的集合
            for (Item im : resultitem) {
                order.put("customerId", csm.getCustomerId());
                order.put("itemId", im.getItemId());
                order.put("documentCategory", ORDER_DOCUMENT_CATEGORY);
                order.put("documentType", ORDER_DOCUMENT_TYPE);
                Object orderCode = order.get("orderCode");
                Object oemployeeId = order.get("employeeId");
                Object ocompanyId = order.get("companyId");
                Object ochangeId = order.get("changeId");
                Object ounitId = order.get("unitId");
                if (orderCode == null) {
                    try {
                        String ruleCode = documentTypeService
                                .getDocumentCodeRule(ORDER_DOCUMENT_CATEGORY, ORDER_DOCUMENT_TYPE);
                        if (ruleCode == null || "ERROR".equalsIgnoreCase(ruleCode)) {
                            ruleCode = OrderController.ORDER_RULE_CODE;
                        }
                        orderCode = codeRuleProcessService.getRuleCode(ruleCode);
                        order.put("orderCode", orderCode);
                    } catch (CodeRuleException e) {
                        e.printStackTrace();
                    }
                }
                //设置默认商户
                if (ocompanyId == null) {
                    order.put("companyId", requestCtx.getCompanyId());
                }
                //设置默认业务员
                if (oemployeeId == null) {
                    order.put("employeeId", requestCtx.getEmployeeId());
                }
                //如果商机id有值，则将其状态改变
                if (ochangeId != null && ostatus.equals("add")) {
                    Change querychange = new Change();
                    querychange.setChangeId(Long.parseLong(ochangeId.toString()));
                    Change change = changeService.selectByPrimaryKey(requestCtx, querychange);
                    change.setStatus("SUBMIT");
                    change.set__status("update");
                    List<Change> lchange = new ArrayList<>();
                    lchange.add(change);
                    changeService.batchUpdate(requestCtx, lchange);
                    if (ounitId == null) {
                        order.put("unitId", change.getUnitId());
                    }
                }
                // 将map转为order对象
                Order rorder = JSON.parseObject(JSON.toJSONString(order), Order.class);
                rorder.set__status(ostatus);
                //将对象放在集合中
                listorder.add(rorder);
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

    /*
     * 自然人订单审核启动工作流
     * */
    @RequestMapping(value = "/wfl/runtime/processInstances/natuorder/check", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ResponseData createVacationProcessInstance(@RequestBody Order order,
                                                      HttpServletRequest httpRequest, HttpServletResponse response) {
        IRequest iRequest = createRequestContext(httpRequest);
        service.createVacationInstance(iRequest, order);
        return new ResponseData();
    }

    /*
     * 法人订单审核启动工作流
     * */
    @RequestMapping(value = "/wfl/runtime/processInstances/laworder/check", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ResponseData createVacationLawProcessInstance(@RequestBody Order order,
                                                         HttpServletRequest httpRequest, HttpServletResponse response) {
        IRequest iRequest = createRequestContext(httpRequest);
        service.createVacationLawInstance(iRequest, order);
        return new ResponseData();
    }

    /*
     *订单签约启动工作流
     * */
    @RequestMapping(value = "/wfl/runtime/processInstances/ordersign/check", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ResponseData createVacationSignProcessInstance(@RequestBody Order order,
                                                          HttpServletRequest httpRequest, HttpServletResponse response) {
        IRequest iRequest = createRequestContext(httpRequest);
        service.createVacationLawInstance(iRequest, order);
        return new ResponseData();
    }

    /*产品机构分配接口*/
    @RequestMapping(value = "/con/order/selectcar")
    @ResponseBody
    public ResponseData batchassign(@RequestBody List<String[]> orderItem,
                                    HttpServletRequest request) {
        String documentCategory = "ITEM";
        String documentType = "ITEM";
        //定义一个订单的数组
        String[] order = orderItem.get(0);
        //定义一个租赁物的数组
        String[] item = orderItem.get(1);
        //定义一个Order类型的list
        List<Order> lorder = new ArrayList<>();
        List<Item> litem = new ArrayList<>();
        Order uporder;
        Item upitem;
        for (int i = 0; i < order.length; i++) {
            for (int j = 0; j < item.length; j++) {
                uporder = new Order();
                uporder.setOrderId(Long.parseLong(order[i]));
                uporder.setItemId(Long.parseLong(item[j]));
                uporder.set__status("update");
                try {
                    String ruleCode = null;
                    if (documentCategory != null && documentType != null) {
                        ruleCode = documentTypeService
                                .getDocumentCodeRule(documentCategory.toString(), documentType.toString());
                    }
                    if (ruleCode == null || "ERROR".equalsIgnoreCase(ruleCode)) {
                        ruleCode = OrderController.CAR_RULE_CODE;
                    }
                    String carCode = codeRuleProcessService.getRuleCode(ruleCode);
                    uporder.setItemCode(carCode);
                } catch (CodeRuleException e) {
                    e.printStackTrace();
                }
                lorder.add(uporder);
                //更新车辆状态
                upitem = new Item();
                upitem.set__status("update");
                upitem.setItemId(Long.parseLong(item[j]));
                upitem.setStatus("SALING");
                litem.add(upitem);
            }
        }
        IRequest requestCtx = createRequestContext(request);
        itemService.batchUpdate(requestCtx, litem);
        return new ResponseData(service.batchUpdate(requestCtx, lorder));
    }

    @RequestMapping(value = "/con/order/rankOrder")
    @ResponseBody
    @ApiOperation(value = "员工放款排行信息", notes = "通用订单接口", httpMethod = "POST", response = Order.class)
    public ResponseData rankOrder(@RequestBody String[] timepara, HttpServletRequest request) {
        IRequest requestCtx = createRequestContext(request);
        String employeecode = requestCtx.getEmployeeCode();
        String begintime = timepara[0];
        String endtime = timepara[1];
        return new ResponseData(orderMapper.selectleaseAmount(begintime, endtime, employeecode));
    }

    @RequestMapping(value = "/con/order/leaseamount")
    @ResponseBody
    @ApiOperation(value = "员工放款数额", notes = "通用订单接口", httpMethod = "POST", response = Order.class)
    public ResponseData leaseShow(@RequestBody String[] timepara, HttpServletRequest request) {
        IRequest requestCtx = createRequestContext(request);
        String employeecode = requestCtx.getEmployeeCode();
        String begintime = timepara[0];
        String endtime = timepara[1];
        return new ResponseData(orderMapper.selectleaseShow(begintime, endtime, employeecode));
    }
}