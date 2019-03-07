package com.jingrui.jrap.function.service.impl;

import com.jingrui.jrap.cache.impl.ResourceCustomizationCache;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.function.dto.ResourceCustomization;
import com.jingrui.jrap.function.mapper.ResourceCustomizationMapper;
import com.jingrui.jrap.function.service.IResourceCustomizationService;
import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 资源合并配置服务接口实现.
 *
 * @author zhizheng.yang@jingrui.com
 */
@Service
public class ResourceCustomizationServiceImpl extends BaseServiceImpl<ResourceCustomization> implements IResourceCustomizationService {

    @Autowired
    private ResourceCustomizationMapper resourceCustomizationMapper;

    @Autowired
    private ResourceCustomizationCache resourceCustomizationCache;


    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<ResourceCustomization> selectResourceCustomizationsByResourceId(Long resourceId) {
        return resourceCustomizationMapper.selectResourceCustomizationsByResourceId(resourceId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteByResourceId(Long resourceId) {
        int result = 0;
        List<ResourceCustomization> resourceCustomizations = resourceCustomizationMapper.selectResourceCustomizationsByResourceId(resourceId);
        if (CollectionUtils.isNotEmpty(resourceCustomizations)) {
            result = super.batchDelete(resourceCustomizations);
            resourceCustomizationCache.remove(resourceCustomizations.get(0).getResourceId().toString());
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<ResourceCustomization> batchUpdate(IRequest request, List<ResourceCustomization> list) {
        List<ResourceCustomization> result = super.batchUpdate(request, list);
        if (list.size() > 0) {
            resourceCustomizationCache.load(list.get(0).getResourceId().toString());
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchDelete(List<ResourceCustomization> list) {
        int size = super.batchDelete(list);
        if (list.size() > 0) {
            resourceCustomizationCache.load(list.get(0).getResourceId().toString());
        }
        return size;
    }
}
