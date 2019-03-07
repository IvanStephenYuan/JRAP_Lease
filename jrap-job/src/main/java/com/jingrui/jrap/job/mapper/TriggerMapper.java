/*
 * #{copyright}#
 */
package com.jingrui.jrap.job.mapper;

import java.util.List;

import com.jingrui.jrap.job.dto.TriggerDto;

/**
 *
 * @author shengyang.zhou@jingrui.com
 */
public interface TriggerMapper {
    TriggerDto selectByPrimaryKey(TriggerDto key);

    List<TriggerDto> selectTriggers(TriggerDto example);

}