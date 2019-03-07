package com.jingrui.jrap.system.mapper;

import com.jingrui.jrap.mybatis.common.Mapper;
import com.jingrui.jrap.system.dto.Profile;

/**
 * 配置维护Mapper
 *
 * @author Frank.li
 * @date 2016/6/9.
 */
public interface ProfileMapper extends Mapper<Profile> {

    /**
     * 查询配置
     *
     * @param profileName 配置名称
     * @return 配置
     */
    Profile selectByName(String profileName);
}