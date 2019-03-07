package com.jingrui.jrap.system.mapper;

import com.jingrui.jrap.mybatis.common.Mapper;
import com.jingrui.jrap.system.dto.SysConfig;

/**
 * @author hailin.xu@jingrui.com
 */
public interface SysConfigMapper extends Mapper<SysConfig> {

    SysConfig selectByCode(String configCode);
}
