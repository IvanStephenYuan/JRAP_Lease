package com.jingrui.jrap.function.mapper;

import com.jingrui.jrap.function.dto.ResourceItemElement;
import com.jingrui.jrap.mybatis.common.Mapper;

import java.util.List;

/**
 * 权限组件元素Mapper.
 *
 * @author qiang.zeng
 */
public interface ResourceItemElementMapper extends Mapper<ResourceItemElement> {
    /**
     * 根据权限组件Id查询权限组件元素.
     *
     * @param element 权限组件元素
     * @return 权限组件元素集合
     */
    List<ResourceItemElement> selectByResourceItemId(ResourceItemElement element);

    /**
     * 根据权限组件Id删除权限组件元素.
     *
     * @param resourceItemId 权限组件Id
     * @return int
     */
    int deleteByResourceItemId(Long resourceItemId);
}