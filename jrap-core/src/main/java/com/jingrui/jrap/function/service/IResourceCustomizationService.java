package com.jingrui.jrap.function.service;

import com.jingrui.jrap.core.ProxySelf;
import com.jingrui.jrap.function.dto.ResourceCustomization;
import com.jingrui.jrap.system.service.IBaseService;

import java.util.List;

/**
 * 资源合并配置服务接口.
 *
 * @author zhizheng.yang@jingrui.com
 */
public interface IResourceCustomizationService extends IBaseService<ResourceCustomization>, ProxySelf<IResourceCustomizationService> {

    /**
     * 根据资源Id查询资源合并配置项.
     *
     * @param resourceId 资源Id
     * @return 资源合并配置项集合
     */
    List<ResourceCustomization> selectResourceCustomizationsByResourceId(Long resourceId);

    /**
     * 根据资源Id删除资源合并配置项.
     *
     * @param resourceId 资源Id
     * @return int
     */
    int deleteByResourceId(Long resourceId);
}
