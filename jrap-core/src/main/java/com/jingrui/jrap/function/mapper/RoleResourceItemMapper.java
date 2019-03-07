package com.jingrui.jrap.function.mapper;

import com.jingrui.jrap.function.dto.RoleResourceItem;
import com.jingrui.jrap.mybatis.common.Mapper;

import java.util.List;

/**
 * 角色权限组件Mapper.
 *
 * @author njq.niu@jingrui.com
 * @date 2016年4月8日
 */
public interface RoleResourceItemMapper extends Mapper<RoleResourceItem> {
    /**
     * 根据权限组件Id删除角色权限组件.
     *
     * @param itemId 权限组件Id
     * @return int
     */
    int deleteByResourceItemId(Long itemId);

    /**
     * 根据角色Id和功能Id删除角色权限组件.
     *
     * @param roleId     角色Id
     * @param functionId 功能Id
     * @return int
     */
    int deleteByRoleIdAndFunctionId(Long roleId, Long functionId);

    /**
     * 插入角色权限组件.
     *
     * @param roleResourceItem
     * @return int
     */
    @Override
    int insert(RoleResourceItem roleResourceItem);

    /**
     * 根据角色Id查询角色权限组件.
     *
     * @param roleId 角色Id
     * @return 角色权限组件集合
     */
    List<RoleResourceItem> selectResourceItemsByRole(Long roleId);

    /**
     * 根据角色Id和权限组件Id查询角色权限组件.
     *
     * @param roleId         角色Id
     * @param resourceItemId 权限组件Id
     * @return 角色权限组件
     */
    RoleResourceItem selectByRoleIdAndResourceItemId(Long roleId, Long resourceItemId);

    /**
     * 缓存查询全部角色权限组件.
     *
     * @return 角色权限组件集合
     */
    List<RoleResourceItem> selectForCache();
}