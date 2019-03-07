package com.jingrui.jrap.function.service.impl;

import com.jingrui.jrap.cache.impl.ResourceItemCache;
import com.jingrui.jrap.cache.impl.ResourceItemElementCache;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.core.annotation.StdWho;
import com.jingrui.jrap.core.impl.RequestHelper;
import com.jingrui.jrap.function.dto.Resource;
import com.jingrui.jrap.function.dto.ResourceItem;
import com.jingrui.jrap.function.mapper.ResourceItemAssignMapper;
import com.jingrui.jrap.function.mapper.ResourceItemElementMapper;
import com.jingrui.jrap.function.mapper.ResourceItemMapper;
import com.jingrui.jrap.function.mapper.RoleResourceItemMapper;
import com.jingrui.jrap.function.service.IResourceItemService;
import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 权限组件服务接口实现.
 *
 * @author njq.niu@jingrui.com
 * @author qiang.zeng@jingrui.com
 * @date 2016年4月7日
 */
@Service
public class ResourceItemServiceImpl extends BaseServiceImpl<ResourceItem> implements IResourceItemService {

    @Autowired
    private ResourceItemMapper resourceItemMapper;
    @Autowired
    private RoleResourceItemMapper roleResourceItemMapper;
    @Autowired
    private ResourceItemCache resourceItemCache;
    @Autowired
    private ResourceItemElementMapper resourceItemElementMapper;
    @Autowired
    private ResourceItemAssignMapper resourceItemAssignMapper;
    @Autowired
    private ResourceItemElementCache resourceItemElementCache;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResourceItem insertSelective(IRequest request,@StdWho ResourceItem resourceItem) {
        if (null == resourceItem) {
            return null;
        }
        super.insertSelective(request, resourceItem);
        resourceItemCache.load(resourceItem.getOwnerResourceId().toString());
        return resourceItem;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResourceItem updateByPrimaryKey(IRequest request, ResourceItem resourceItem) {
        if (null == resourceItem) {
            return null;
        }
        super.updateByPrimaryKey(request, resourceItem);
        resourceItemCache.load(resourceItem.getOwnerResourceId().toString());
        return resourceItem;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<ResourceItem> selectResourceItems(IRequest request, Resource resource) {
        return resourceItemMapper.selectResourceItemsByResourceId(resource);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<ResourceItem> batchUpdate(IRequest request, List<ResourceItem> resourceItems) {
        if (CollectionUtils.isEmpty(resourceItems)) {
            return resourceItems;
        }
        for (ResourceItem resourceItem : resourceItems) {
            if (resourceItem.getResourceItemId() == null) {
                self().insertSelective(request, resourceItem);
            } else {
                self().updateByPrimaryKey(request, resourceItem);
            }
        }
        return resourceItems;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchDelete(IRequest requestContext, List<ResourceItem> resourceItems) {
        int result = 0;
        if (CollectionUtils.isEmpty(resourceItems)) {
            return result;
        }
        for (ResourceItem resourceItem : resourceItems) {
            int updateCount = resourceItemMapper.deleteByPrimaryKey(resourceItem);
            checkOvn(updateCount, resourceItem);
            roleResourceItemMapper.deleteByResourceItemId(resourceItem.getResourceItemId());
            resourceItemAssignMapper.deleteByResourceItemId(resourceItem.getResourceItemId());
            resourceItemElementMapper.deleteByResourceItemId(resourceItem.getResourceItemId());
            resourceItemCache.load(resourceItem.getOwnerResourceId().toString());
            resourceItemElementCache.remove(resourceItem.getResourceItemId().toString());
            result++;
        }
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public ResourceItem selectResourceItemByResourceIdAndItemId(ResourceItem resourceItem) {
        return resourceItemMapper.selectResourceItemByResourceIdAndItemId(resourceItem);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteByResourceId(Resource resource) {
        int result = 0;
        List<ResourceItem> resourceItems = resourceItemMapper.selectResourceItemsByResourceId(resource);
        if (CollectionUtils.isNotEmpty(resourceItems)) {
            IRequest request = RequestHelper.newEmptyRequest();
            result = self().batchDelete(request, resourceItems);
        }
        return result;
    }

}
