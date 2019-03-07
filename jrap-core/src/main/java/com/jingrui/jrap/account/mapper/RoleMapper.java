package com.jingrui.jrap.account.mapper;

import java.util.List;

import com.jingrui.jrap.account.dto.Role;
import com.jingrui.jrap.account.dto.RoleExt;
import com.jingrui.jrap.account.dto.User;
import com.jingrui.jrap.mybatis.common.Mapper;

/**
 * 角色Mapper.
 *
 * @author shengyang.zhou@jingrui.com
 */
public interface RoleMapper extends Mapper<Role> {

    /**
     * 根据用户Id查询角色.
     *
     * @param userId 用户ID
     * @return 角色集合
     */
    List<Role> selectUserRolesByUserId(Long userId);

    /**
     * 根据用户Id获取角色.
     *
     * @param userId 用户ID
     * @return 角色集合
     */
    List<Role> selectUserRoles(Long userId);

    /**
     * 查询用户不具有的角色.
     *
     * @param roleExt 角色扩展对象
     * @return 角色集合
     */
    List<Role> selectRoleNotUserRoles(RoleExt roleExt);

    /**
     * 查询用户拥有的角色.
     *
     * @param user 用户
     * @return 角色集合
     */
    List<Role> selectByUser(User user);

    /**
     * 根据用户查询角色.
     *
     * @param user 用户
     * @return 角色集合
     */
    List<Role> selectRolesByUserWithoutLang(User user);

    /**
     * 根据用户ID和角色ID查询用户角色分配数量.
     *
     * @param userId 用户ID
     * @param roleId 角色ID
     * @return 用户角色分配数量
     */
    int selectUserRoleCount(Long userId, Long roleId);

    /**
     * 根据用户ID查询用户启用状态的角色.
     *
     * @param userId 用户ID
     * @return 角色集合
     */
    List<Role> selectUserActiveRoles(Long userId);

    /**
     * 查询所有启用状态的角色.
     *
     * @return 角色集合
     */
    List<Role> selectActiveRoles();
}
