package com.jingrui.jrap.activiti.demo.controllers;

import com.jingrui.jrap.activiti.demo.dto.DemoVacation;
import com.jingrui.jrap.activiti.demo.service.IDemoVacationService;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.system.controllers.BaseController;
import com.jingrui.jrap.system.dto.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class DemoVacationController extends BaseController {

    @Autowired
    private IDemoVacationService vacationService;

    /*
    * 请假流程demo
    * */
    @RequestMapping(value = "/wfl/runtime/process-instances/vacation", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ResponseData createVacationProcessInstance(@RequestBody DemoVacation demoVacation,
                                                      HttpServletRequest httpRequest, HttpServletResponse response) {
        IRequest iRequest = createRequestContext(httpRequest);
        vacationService.createVacationInstance(iRequest, demoVacation);
        return new ResponseData();
    }

    /*
    请假流程通过businessKey获取流程表单
    * */
    @RequestMapping("/wfl/history/form/details/vacation/{businessKey}")
    @ResponseBody
    public DemoVacation getProcessFrom(HttpServletRequest request, @PathVariable String businessKey) {
        DemoVacation demoVacation = new DemoVacation();
        demoVacation.setId(Long.parseLong(businessKey));
        DemoVacation demo = vacationService.selectByPrimaryKey(createRequestContext(request), demoVacation);
        return demo;
    }

    /*
    * 获取当前用户历史请假记录
    * */
    @ResponseBody
    @RequestMapping("/wfl/vacation/query")
    public ResponseData getVacationHistory(HttpServletRequest request, HttpServletResponse response, DemoVacation demoVacation, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                           @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
        IRequest iRequest = createRequestContext(request);
        List<DemoVacation> lists = vacationService.selectVacationHistory(iRequest);
        return new ResponseData(lists);
    }

    /*
   * 获取当前用户历史请假记录
   * */
    @ResponseBody
    @RequestMapping("/wfl/vacation/save")
    public ResponseData saveVacationHistory(HttpServletRequest request, HttpServletResponse response, @RequestBody DemoVacation demoVacation, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
        IRequest iRequest = createRequestContext(request);
        vacationService.updateByPrimaryKeySelective(iRequest, demoVacation);
        return new ResponseData();
    }
}