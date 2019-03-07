/*
 * #{copyright}#
 */
package com.jingrui.jrap.job.mapper;

import com.jingrui.jrap.job.dto.JobRunningInfoDto;
import com.jingrui.jrap.mybatis.common.Mapper;

/**
 *
 * @author liyan.shi@jingrui.com
 */
public interface JobRunningInfoDtoMapper extends Mapper<JobRunningInfoDto> {

    void deleteByNameGroup(JobRunningInfoDto example);

}