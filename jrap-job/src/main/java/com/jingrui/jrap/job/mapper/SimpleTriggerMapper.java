/*
 * #{copyright}#
 */
package com.jingrui.jrap.job.mapper;

import com.jingrui.jrap.job.dto.SimpleTriggerDto;

/**
 *
 * @author shengyang.zhou@jingrui.com
 */
public interface SimpleTriggerMapper {

    SimpleTriggerDto selectByPrimaryKey(SimpleTriggerDto key);
}