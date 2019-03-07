/*
 * #{copyright}#
 */

package com.jingrui.jrap.job.mapper;

import java.util.List;

import com.jingrui.jrap.job.dto.JobDetailDto;
import com.jingrui.jrap.job.dto.JobInfoDetailDto;

/**
 *
 * @author shengyang.zhou@jingrui.com
 */
public interface JobDetailMapper {
    JobDetailDto selectByPrimaryKey(JobDetailDto key);

    List<JobDetailDto> selectJobDetails(JobDetailDto example);

    List<JobInfoDetailDto> selectJobInfoDetails(JobDetailDto example);
}