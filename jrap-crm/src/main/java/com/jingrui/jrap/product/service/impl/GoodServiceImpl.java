package com.jingrui.jrap.product.service.impl;

import com.jingrui.jrap.activiti.service.IActivitiService;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.product.dto.Good;
import com.jingrui.jrap.product.mapper.GoodMapper;
import com.jingrui.jrap.product.service.IGoodService;
import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import org.activiti.rest.service.api.runtime.process.ProcessInstanceCreateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(rollbackFor = Exception.class)
public class GoodServiceImpl extends BaseServiceImpl<Good> implements IGoodService {

  @Autowired
  private IActivitiService activitiService;  //工作流相关Service
  @Autowired
  private GoodMapper goodMapper;
  //产品发布流程标识，通过其启动流程
  private final String processDefinitionKey = "PRODUCT_RELEASE";

  public void createVacationInstance(IRequest iRequest, Good good) {
    //设置流程状态为提交中
    good.setStatus("Submit");
    //设置companyid
    good.setCompanyId(iRequest.getCompanyId());
    goodMapper.insert(good);
    ProcessInstanceCreateRequest instanceCreateRequest = new ProcessInstanceCreateRequest();
    instanceCreateRequest.setBusinessKey(String.valueOf(good.getGoodId()));
    instanceCreateRequest.setProcessDefinitionKey(processDefinitionKey);
    //启动工作流
    activitiService.startProcess(iRequest, instanceCreateRequest);
  }

}