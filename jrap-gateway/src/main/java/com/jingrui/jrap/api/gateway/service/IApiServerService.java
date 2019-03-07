package com.jingrui.jrap.api.gateway.service;

import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.core.ProxySelf;
import com.jingrui.jrap.api.gateway.dto.ApiServer;
import com.jingrui.jrap.core.annotation.StdWho;
import com.jingrui.jrap.system.service.IBaseService;

import java.util.List;
import java.util.Map;

/**
 * 透传服务接口.
 *
 * @author peng.jiang@jingrui.com
 * @date 2017/9/15.
 */

public interface IApiServerService extends IBaseService<ApiServer>, ProxySelf<IApiServerService>{

    /**
     * 通过代码查询服务.
     *
     * @param request
     * @param codeList
     * @return
     */
    List<ApiServer> selectByCodes(IRequest request, List<String> codeList);

    /**
     * 导入并解析服务.
     *
     * @param request
     * @param dto
     * @return
     */
    ApiServer importServer(IRequest request, ApiServer dto);

    /**
     * 添加服务.
     *
     * @param server
     * @return
     */
    ApiServer insertServer(ApiServer server);

    /**
     * 修改服务.
     *
     * @param request
     * @param server
     * @return
     */
    ApiServer updateServer(IRequest request , @StdWho ApiServer server);

    /**
     * 根据服务路径、接口路径获取服务(缓存).
     *
     * @param serverUrl
     * @param interfaceUrl
     * @return
     */
    ApiServer getByMappingUrl(String serverUrl, String interfaceUrl);


    /**
     * 查询应用没关联的服务.
     *
     * @param params
     * @return
     */
    List<ApiServer> selectNotExistsServerByApp(Map<String, Object> params);

}