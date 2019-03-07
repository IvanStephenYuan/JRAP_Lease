package com.jingrui.jrap.api.gateway.service;

import com.jingrui.jrap.api.gateway.dto.ApiServer;
import net.sf.json.JSONObject;

/**
 * 映射接口.
 *
 * @author xiangyu.qi@jingrui.com
 * @date 2017/9/25.
 */

public interface IApiInvokeService {

    /**
     * 当前实现类处理的服务类型.
     *
     * @return
     */
    String serverType();

    /**
     * 判断服务类型（REST，SOAP...）是否匹配当前服务.
     *
     * @param serverType 服务类型
     * @return
     */
    default boolean matchServerType(String serverType){
        return serverType().equalsIgnoreCase(serverType);
    }

    /**
     * 透传调用具体第三方服务.
     *
     * @param server 服务配置信息
     * @param inbound 传入参数
     * @return
     * @throws Exception
     */
    Object invoke(ApiServer server, JSONObject inbound) throws Exception;

    /**
     * 请求第三方服务.
     *
     * @param server
     * @param parameter
     * @return
     * @throws Exception
     */
    String apiInvoke(ApiServer server,String parameter)throws Exception;

}
