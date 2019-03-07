/*
 * #{copyright}#
 */

package com.jingrui.jrap.job.controllers;

import javax.servlet.http.HttpServletRequest;

import com.jingrui.jrap.job.service.IJobRunningInfoService;
import com.jingrui.jrap.system.dto.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jingrui.jrap.system.controllers.BaseController;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.job.dto.JobRunningInfoDto;
import com.jingrui.jrap.job.service.impl.JobRunningInfoService;

/**
 * @author liyan.shi@jingrui.com
 */
@Controller
@RequestMapping(value = {"/job/jobinfo", "/api/job/jobinfo"})
public class JobInfoController extends BaseController {
    @Autowired
    private IJobRunningInfoService jobRunningInfoService;

    /**
     * 查询Job运行记录.
     * 
     * @param dto
     *            参数
     * @param page
     *            页码
     * @param pagesize
     *            每页数量
     * @param request
     *            HttpServletRequest
     * @return 运行记录结果
     */
    @RequestMapping(value = "/query")
    @ResponseBody
    public ResponseData queryJobRunningInfo(JobRunningInfoDto dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize, HttpServletRequest request) {
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(jobRunningInfoService.queryJobRunningInfo(requestCtx, dto, page, pagesize));
    }

}
