package com.jingrui.jrap.api.gateway.service;

import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.core.ProxySelf;
import com.jingrui.jrap.api.gateway.dto.ApiInterface;
import com.jingrui.jrap.system.service.IBaseService;

import java.util.List;

/**
 * server-interface 接口.
 *
 * @author xiangyu.qi@jingrui.com
 * @date 2017/9/20.
 */

public interface IApiInterfaceService extends IBaseService<ApiInterface>, ProxySelf<IApiInterfaceService>{

    /**
     * 接口查询.
     *
     * @param request
     * @param srInterface
     * @return
     */
    List<ApiInterface> selectByServerId(IRequest request, ApiInterface srInterface);

    /**
     * 查询服务接口（包括接口限制信息）.
     *
     * @param request
     * @param clientId
     * @param serverId
     * @return
     */
    List<ApiInterface> selectByServerIdWithLimit(IRequest request, String clientId, Long serverId);

    /**
     * 根据服务代码获取接口.
     *
     * @param requestContext
     * @param clientId
     * @param serverCode
     * @return
     */
    List<ApiInterface> selectInterfacesByServerCode(IRequest requestContext, String clientId, String serverCode);
}