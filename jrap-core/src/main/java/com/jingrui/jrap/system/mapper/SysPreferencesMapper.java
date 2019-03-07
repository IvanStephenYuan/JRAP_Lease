/*
 * #{copyright}#
 */
package com.jingrui.jrap.system.mapper;

import com.jingrui.jrap.mybatis.common.Mapper;
import com.jingrui.jrap.system.dto.SysPreferences;

/**
 * 系统首选项Mapper.
 * 
 * @author zhangYang
 * @author njq.niu@jingrui.com
 */
public interface SysPreferencesMapper extends Mapper<SysPreferences> {

    /**
     * 查询用户单个首选项
     * @param record preferences值
     * @return 首选项
     */
    SysPreferences selectUserPreference(SysPreferences record);

    /**
     * 根据preferences或userId更新SysPreferences
     * @param record record
     * @return
     */
    int updatePreferLine(SysPreferences record);
}