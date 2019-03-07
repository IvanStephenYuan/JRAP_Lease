/*
 * #{copyright}#
 */

package com.jingrui.jrap.job.service;

import java.util.List;

import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.job.dto.JobRunningInfoDto;

/**
 * @author liyan.shi@jingrui.com
 */
public interface IJobRunningInfoService {

    /**
     * 查询Job运行记录.
     * 
     * @param request
     *            session信息
     * @param example
     *            参数
     * @param page
     *            页码
     * @param pagesize
     *            每页数量
     * @return 运行记录结果
     */
    List<JobRunningInfoDto> queryJobRunningInfo(IRequest request, JobRunningInfoDto example, int page, int pagesize);

    /**
     * 
     * 新建运行记录.
     * 
     * @param jobCreateDto
     *            运行记录
     */
    void createJobRunningInfo(JobRunningInfoDto jobCreateDto);

    /**
     * 删除运行记录
     * 
     * @param jobCreateDto
     *            记录
     */
    void delete(JobRunningInfoDto jobCreateDto);
}
