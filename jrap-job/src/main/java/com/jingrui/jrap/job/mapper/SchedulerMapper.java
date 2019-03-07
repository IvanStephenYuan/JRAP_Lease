/*
 * #{copyright}#
 */
package com.jingrui.jrap.job.mapper;

import java.util.List;

import com.jingrui.jrap.job.dto.SchedulerDto;

/**
 *
 * @author shengyang.zhou@jingrui.com
 */
public interface SchedulerMapper {

    SchedulerDto selectByPrimaryKey(SchedulerDto key);

    List<SchedulerDto> selectSchedulers(SchedulerDto example);

}