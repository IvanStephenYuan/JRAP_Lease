package com.jingrui.jrap.activiti.demo.service;

import com.jingrui.jrap.activiti.demo.dto.DemoVacation;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.core.ProxySelf;
import com.jingrui.jrap.system.service.IBaseService;

import java.util.List;

public interface IDemoVacationService extends IBaseService<DemoVacation>, ProxySelf<IDemoVacationService> {

    void createVacationInstance(IRequest iRequest, DemoVacation demoVacation);

    List<DemoVacation> selectVacationHistory(IRequest iRequest);

}