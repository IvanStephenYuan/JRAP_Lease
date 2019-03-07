package com.jingrui.jrap.function.service;

import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.core.ProxySelf;
import com.jingrui.jrap.core.annotation.StdWho;
import com.jingrui.jrap.function.dto.*;
import com.jingrui.jrap.system.service.IBaseService;

import java.util.List;

/**
 * 角色权限组件服务接口.
 *
 * @author njq.niu@jingrui.com
 * @author qiang.zeng@jingrui.com
 * @date 2016年4月7日
 */
public interface IRoleResourceItemService extends IBaseService<RoleResourceItem>, ProxySelf<IRoleResourceItemService> {
    /**
     * 查询角色拥有的权限组件项.
     *
     * @param requestContext IRequest
     * @param roleId         角色Id
     * @param functionId     功能Id
     * @return 权限组件项集合
     */
    List<MenuItem> queryResourceItems(IRequest requestContext, Long roleId, Long functionId);

    /**
     * 查询功能下的HTML资源.
     *
     * @param requestContext IRequest
     * @param function       功能
     * @return HTML资源集合
     */
    List<Resource> queryHtmlResources(IRequest requestContext, Function function);

    /**
     * 构建HTML资源菜单.
     *
     * @param resourceList HTML资源集合
     * @return HTML资源菜单集合
     */
    List<MenuItem> createResources(List<Resource> resourceList);

    /**
     * 保存角色拥有的权限组件分配.
     *
     * @param requestContext         IRequest
     * @param resourceItemAssignList 分配的权限组件集合
     * @param roleId                 角色Id
     * @param functionId             功能Id
     * @return 分配的权限组件集合
     */
    List<ResourceItemAssign> updateResourceItemAssign(IRequest requestContext, @StdWho List<ResourceItemAssign> resourceItemAssignList,
                                                      Long roleId, Long functionId);

    /**
     * 判断角色是否拥有权限项.
     *
     * @param roleId         角色Id
     * @param resourceItemId 权限组件Id
     * @return 返回true:有  返回false:没有
     */
    boolean hasResourceItem(Long roleId, Long resourceItemId);
}
