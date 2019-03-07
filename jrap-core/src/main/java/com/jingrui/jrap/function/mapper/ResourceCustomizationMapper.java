package com.jingrui.jrap.function.mapper;

import com.jingrui.jrap.function.dto.ResourceCustomization;
import com.jingrui.jrap.mybatis.common.Mapper;

import java.util.List;

/**
 * 资源合并配置项Mapper.
 *
 * @author zhizheng.yang@jingrui.com
 */
public interface ResourceCustomizationMapper extends Mapper<ResourceCustomization> {
    /**
     * 根据资源Id查询资源合并配置项.
     *
     * @param resourceId 资源Id
     * @return 资源合并配置项集合
     */
    List<ResourceCustomization> selectResourceCustomizationsByResourceId(Long resourceId);

}
