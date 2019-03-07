/*
 * #{copyright}#
 */

package com.jingrui.jrap.job.controllers;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.core.exception.FieldRequiredException;
import com.jingrui.jrap.job.dto.JobCreateDto;
import com.jingrui.jrap.job.dto.JobDetailDto;
import com.jingrui.jrap.job.dto.SchedulerDto;
import com.jingrui.jrap.job.dto.TriggerDto;
import com.jingrui.jrap.job.exception.JobException;
import com.jingrui.jrap.job.service.IQuartzService;
import com.jingrui.jrap.system.controllers.BaseController;
import com.jingrui.jrap.system.dto.ResponseData;

/**
 * @author shengyang.zhou@jingrui.com
 */
@Controller
@RequestMapping(value = {"/job", "/api/job"})
public class JobController extends BaseController {

    @Autowired
    private IQuartzService quartzService;

    /**
     * 新建一个JOB.
     * 
     * @param jobCreateDto
     *            dto
     * @param result
     *            result
     * @param request
     *            HttpServletRequest
     * @return 新建结果
     * @throws SchedulerException
     *             Base class for exceptions thrown by the Quartz Scheduler.
     * @throws JobException
     *             JobException
     * @throws ClassNotFoundException
     *             ClassNotFoundException
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseData createJob(@RequestBody JobCreateDto jobCreateDto, BindingResult result,
                                  HttpServletRequest request) throws SchedulerException, JobException, ClassNotFoundException, FieldRequiredException {
        jobCreateDto.setTriggerGroup(jobCreateDto.getJobGroup());
        jobCreateDto.setTriggerName(jobCreateDto.getJobName() + "_trigger");
        getValidator().validate(jobCreateDto, result);
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }
        quartzService.createJob(jobCreateDto);
        return new ResponseData();
    }

    /**
     * 暂停job.
     * 
     * @param list
     *            job列表
     * @return 结果
     * @throws SchedulerException
     *             Base class for exceptions thrown by the Quartz Scheduler.
     */
    @RequestMapping("/pause")
    public ResponseData pauseJobs(@RequestBody List<JobDetailDto> list) throws SchedulerException {
        quartzService.pauseJobs(list);
        return new ResponseData();
    }

    /**
     * 继续执行job.
     * 
     * @param list
     *            job 列表
     * @return 结果
     * @throws SchedulerException
     *             Base class for exceptions thrown by the Quartz Scheduler.
     */
    @RequestMapping("/resume")
    public ResponseData resumeJobs(@RequestBody List<JobDetailDto> list) throws SchedulerException {
        quartzService.resumeJobs(list);
        return new ResponseData();
    }

    /**
     * 删除job.
     * 
     * @param list
     *            job列表
     * @return 结果
     * @throws SchedulerException
     *             Base class for exceptions thrown by the Quartz Scheduler.
     */
    @RequestMapping("/deletejob")
    public ResponseData deleteJobs(@RequestBody List<JobDetailDto> list) throws SchedulerException {
        quartzService.deleteJobs(list);
        return new ResponseData();
    }

    /**
     * 暂停触发器
     * 
     * @param list
     *            触发器列表
     * @return 结果
     * @throws SchedulerException
     *             Base class for exceptions thrown by the Quartz Scheduler.
     */
    @RequestMapping("/pausetrigger")
    public ResponseData pauseTrigger(@RequestBody List<TriggerDto> list) throws SchedulerException {
        quartzService.pauseTriggers(list);
        return new ResponseData();
    }

    /**
     * 
     * 继续执行触发器.
     * 
     * @param list
     *            触发器列表
     * @return 结果
     * @throws SchedulerException
     *             Base class for exceptions thrown by the Quartz Scheduler.
     */
    @RequestMapping("/resumetrigger")
    public ResponseData resumeTrigger(@RequestBody List<TriggerDto> list) throws SchedulerException {
        quartzService.resumeTriggers(list);
        return new ResponseData();
    }

    /**
     * 
     * 查询job列表.
     * 
     * @param example
     *            查询参数
     * @param page
     *            页码
     * @param pagesize
     *            每页数量
     * @param request
     *            HttpServletRequest
     * @return job结果列表
     * @throws SchedulerException
     *             Base class for exceptions thrown by the Quartz Scheduler.
     */
    @RequestMapping("/query")
    public ResponseData queryJobs(@ModelAttribute JobDetailDto example,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize, HttpServletRequest request)
                    throws SchedulerException {
        return qj(example, page, pagesize, request);
    }

    /**
     * 
     * 查询job列表.
     * 
     * @param example
     *            查询参数
     * @param page
     *            页码
     * @param pagesize
     *            每页数量
     * @param request
     *            HttpServletRequest
     * @return job结果列表
     * @throws SchedulerException
     *             Base class for exceptions thrown by the Quartz Scheduler.
     */
    @RequestMapping("/queryInfo")
    public ResponseData query(@RequestBody JobDetailDto example, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize, HttpServletRequest request)
                    throws SchedulerException {
        return qj(example, page, pagesize, request);
    }

    private ResponseData qj(JobDetailDto example, int page, int pagesize, HttpServletRequest request) {
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(quartzService.getJobInfoDetails(requestCtx, example, page, pagesize));
    }

    /**
     * 查询触发器.
     * 
     * @param triggerName
     *            name
     * @param triggerGroup
     *            group
     * @param triggerType
     *            触发器类型
     * @return 触发器列表
     * @throws SchedulerException
     *             SchedulerException
     */
    @RequestMapping("/trigger")
    public ResponseData queryTrigger(@RequestParam(required = true) String triggerName,
            @RequestParam(required = true) String triggerGroup, @RequestParam(required = true) String triggerType)
                    throws SchedulerException {
        if ("CRON".equalsIgnoreCase(triggerType)) {
            return new ResponseData(Arrays.asList(quartzService.getCronTrigger(triggerName, triggerGroup)));
        }
        if ("SIMPLE".equalsIgnoreCase(triggerType)) {
            return new ResponseData(Arrays.asList(quartzService.getSimpleTrigger(triggerName, triggerGroup)));
        }
        return new ResponseData();
    }

    /**
     * 查询触发器.
     * 
     * @param example
     *            参数
     * @param page
     *            页码
     * @param pagesize
     *            每页数量
     * @param request
     *            HttpServletRequest
     * @return 触发器列表
     * @throws SchedulerException
     *             SchedulerException
     */
    @RequestMapping("/trigger/query")
    public ResponseData queryTriggers(@ModelAttribute TriggerDto example,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize, HttpServletRequest request)
                    throws SchedulerException {
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(quartzService.getTriggers(requestCtx, example, page, pagesize));
    }

    /**
     * 
     * 查询Scheduler.
     * 
     * @param example
     *            查询参数
     * @param page
     *            页码
     * @param pagesize
     *            每页数量
     * @return Scheduler信息
     * @throws SchedulerException
     *             SchedulerException
     * @deprecated
     */
    @RequestMapping("/scheduler/query")
    public ResponseData querySchedulers(@ModelAttribute SchedulerDto example,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) throws SchedulerException {
        return new ResponseData(quartzService.selectSchedulers(example, page, pagesize));
    }

    /**
     * 
     * .
     * 
     * @return scheduler信息
     * @throws SchedulerException
     *             Base class for exceptions thrown by the Quartz Scheduler.
     * @deprecated
     */
    @RequestMapping("/scheduler/info")
    public ResponseData schedulerInformation() throws SchedulerException {
        Map<String, Object> infoMap = quartzService.schedulerInformation();
        ResponseData responseData = new ResponseData();
        responseData.setRows(Arrays.asList(infoMap));
        return responseData;
    }

    /**
     * 
     * 启动当前的Scheduler.
     * 
     * @return Scheduler信息
     * @throws SchedulerException
     *             Scheduler异常
     * @deprecated
     */

    @RequestMapping("/scheduler/start")
    public ResponseData startScheduler() throws SchedulerException {
        return new ResponseData(Arrays.asList(quartzService.start()));
    }

    /**
     * 
     * standby当前的Scheduler.
     * 
     * @return Scheduler信息
     * @throws SchedulerException
     *             Scheduler异常
     * @deprecated
     */

    @RequestMapping("/scheduler/standby")
    public ResponseData standbyScheduler() throws SchedulerException {
        return new ResponseData(Arrays.asList(quartzService.standby()));
    }

    /**
     * 
     * 暂停所有job.
     * 
     * @return Scheduler信息
     * @throws SchedulerException
     *             Scheduler异常
     * @deprecated
     */

    @RequestMapping("/scheduler/pauseall")
    public ResponseData schedulerPauseAll() throws SchedulerException {
        return new ResponseData(Arrays.asList(quartzService.pauseAll()));
    }

    /**
     * 
     * 继续所有job.
     * 
     * @return Scheduler信息
     * @throws SchedulerException
     *             Scheduler异常
     * @deprecated
     */

    @RequestMapping("/scheduler/resumeall")
    public ResponseData schedulerResumeAll() throws SchedulerException {
        return new ResponseData(Arrays.asList(quartzService.resumeAll()));
    }
}
