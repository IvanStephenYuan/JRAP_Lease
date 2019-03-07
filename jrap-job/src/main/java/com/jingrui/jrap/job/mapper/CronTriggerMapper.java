/*
 * #{copyright}#
 */
package com.jingrui.jrap.job.mapper;

import com.jingrui.jrap.job.dto.CronTriggerDto;

/**
 *
 * @author shengyang.zhou@jingrui.com
 */
public interface CronTriggerMapper {
    CronTriggerDto selectByPrimaryKey(CronTriggerDto key);
}