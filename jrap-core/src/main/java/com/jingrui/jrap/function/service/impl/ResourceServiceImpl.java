package com.jingrui.jrap.function.service.impl;

import com.jingrui.jrap.cache.Cache;
import com.jingrui.jrap.cache.CacheManager;
import com.jingrui.jrap.core.BaseConstants;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.core.annotation.StdWho;
import com.jingrui.jrap.function.dto.Function;
import com.jingrui.jrap.function.dto.Resource;
import com.jingrui.jrap.function.mapper.FunctionResourceMapper;
import com.jingrui.jrap.function.mapper.ResourceMapper;
import com.jingrui.jrap.function.service.IResourceCustomizationService;
import com.jingrui.jrap.function.service.IResourceItemService;
import com.jingrui.jrap.function.service.IResourceService;
import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 资源服务接口实现.
 *
 * @author wuyichu
 * @author njq.niu@jingrui.com
 */
@Service
public class ResourceServiceImpl extends BaseServiceImpl<Resource> implements IResourceService {

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private ResourceMapper resourceMapper;

    @Autowired
    private FunctionResourceMapper functionResourceMapper;

    @Autowired
    private IResourceItemService resourceItemService;

    @Autowired
    private IResourceCustomizationService resourceCustomizationService;


    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Resource selectResourceByUrl(String url) {
        if (StringUtils.isEmpty(url)) {
            return null;
        }
        Cache<Resource> cache = getResourceByURL();
        Resource resource = cache.getValue(url);
        if (resource == null) {
            resource = resourceMapper.selectResourceByUrl(url);
            flushCache(resource);
        }
        return resource;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Resource selectResourceById(Long id) {
        if (id == null) {
            return null;
        }
        Cache<Resource> cache = getResourceById();
        Resource resource = cache.getValue(id.toString());
        if (resource == null) {
            resource = resourceMapper.selectByPrimaryKey(id);
            flushCache(resource);
        }
        return resource;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Resource insertSelective(IRequest request, Resource resource) {
        String url = resource.getUrl();
        if (url.startsWith(BaseConstants.FORWARD_SLASH)) {
            resource.setUrl(url.substring(1));
        }
        resourceMapper.insertSelective(resource);
        flushCache(resource);
        return resource;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Resource> batchUpdate(IRequest requestContext, List<Resource> resources) {
        for (Resource resource : resources) {
            if (resource.getResourceId() != null) {
                self().updateByPrimaryKeySelective(requestContext, resource);
            } else {
                self().insertSelective(requestContext, resource);
            }
        }
        return resources;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Resource updateByPrimaryKeySelective(IRequest request, @StdWho Resource record) {
        String url = record.getUrl();
        if (url.startsWith(BaseConstants.FORWARD_SLASH)) {
            record.setUrl(url.substring(1));
        }
        record = super.updateByPrimaryKeySelective(request, record);
        flushCache(record);
        reloadFunction();
        return record;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteByPrimaryKey(Resource resource) {
        if (resource == null || resource.getResourceId() == null || StringUtils.isEmpty(resource.getUrl())) {
            return 0;
        }
        int updateCount = resourceMapper.deleteByPrimaryKey(resource);
        checkOvn(updateCount,resource);
        functionResourceMapper.deleteByResource(resource);
        resourceItemService.deleteByResourceId(resource);
        resourceCustomizationService.deleteByResourceId(resource.getResourceId());
        removeCache(resource);
        return 1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchDelete(IRequest requestContext, List<Resource> resources) {
        int result = 0;
        if (CollectionUtils.isEmpty(resources)) {
            return result;
        }
        for (Resource resource : resources) {
            self().deleteByPrimaryKey(resource);
            result++;
        }
        return result;
    }

    /**
     * 获取资源URL缓存
     *
     * @return 资源URL缓存
     */
    private Cache<Resource> getResourceByURL() {
        return cacheManager.getCache(BaseConstants.CACHE_RESOURCE_URL);
    }

    /**
     * 获取资源ID缓存
     *
     * @return 资源ID缓存
     */
    private Cache<Resource> getResourceById() {
        return cacheManager.getCache(BaseConstants.CACHE_RESOURCE_ID);
    }

    /**
     *  获取功能的缓存
     * @return
     */
    private Cache<Function> getFunction() {
        return  cacheManager.getCache(BaseConstants.CACHE_FUNCTION);
    }

    /**
     * 更新资源URL缓存和资源ID缓存
     *
     * @param resource 资源
     */
    private void flushCache(Resource resource) {
        if (resource == null) {
            return;
        }
        Cache<Resource> resourceCache = getResourceByURL();
        resourceCache.setValue(resource.getUrl(), resource);
        Cache<Resource> resourceCache2 = getResourceById();
        resourceCache2.setValue(resource.getResourceId().toString(), resource);
    }

    /**
     * 删除资源URL缓存和资源ID缓存
     *
     * @param resource 资源
     */
    private void removeCache(Resource resource) {
        if (resource == null) {
            return;
        }
        Cache<Resource> resourceCache = getResourceByURL();
        resourceCache.remove(resource.getUrl());
        Cache<Resource> resourceCache2 = getResourceById();
        resourceCache2.remove(resource.getResourceId().toString());

        reloadFunction();
    }

    /**
     * 重新加载 Function 的缓存
     */
    private void reloadFunction() {
        Cache<Function> functionCache = getFunction();
        functionCache.reload();
    }

}
