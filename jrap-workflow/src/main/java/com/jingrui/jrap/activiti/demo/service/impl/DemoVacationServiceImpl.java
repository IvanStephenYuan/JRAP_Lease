package com.jingrui.jrap.activiti.demo.service.impl;

import com.jingrui.jrap.activiti.demo.dto.DemoVacation;
import com.jingrui.jrap.activiti.demo.mapper.DemoVacationMapper;
import com.jingrui.jrap.activiti.demo.service.IDemoVacationService;
import com.jingrui.jrap.activiti.service.IActivitiService;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import org.activiti.rest.service.api.engine.variable.RestVariable;
import org.activiti.rest.service.api.runtime.process.ProcessInstanceCreateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class DemoVacationServiceImpl extends BaseServiceImpl<DemoVacation> implements IDemoVacationService {

    @Autowired
    private IActivitiService activitiService;

    @Autowired
    private DemoVacationMapper demoVacationMapper;

    private final String processDefinitionKey = "VACATION_REQUEST";

    //    @Autowired
    // private IDemoVacationService vacationService;
    /**
    * 创建请假流程将表单存放在act_demo_vacation 表
    *
    */
    @Override
    public void createVacationInstance(IRequest iRequest, DemoVacation demoVacation) {
        demoVacation.setUserCode(iRequest.getEmployeeCode());
        DemoVacation demo = this.insertSelective(iRequest, demoVacation);
        ProcessInstanceCreateRequest instanceCreateRequest = new ProcessInstanceCreateRequest();
        instanceCreateRequest.setBusinessKey(String.valueOf(demo.getId()));
        instanceCreateRequest.setProcessDefinitionKey(processDefinitionKey);
        //添加流程变量
        List<RestVariable> variables = new ArrayList<>();
        RestVariable variable = new RestVariable();
        variable.setName("needDays");
        variable.setType("long");
        variable.setValue(demo.getNeedDays());

        variables.add(variable);
        instanceCreateRequest.setVariables(variables);

        activitiService.startProcess(iRequest, instanceCreateRequest);
    }

    /*
    * 查看历史请假记录
    * c*/
    @Override
    public List<DemoVacation> selectVacationHistory(IRequest iRequest) {
        DemoVacation demoVacation = new DemoVacation();
        demoVacation.setUserCode(iRequest.getEmployeeCode());
        List<DemoVacation> demoVacations = demoVacationMapper.select(demoVacation);
        return demoVacations;
    }
}