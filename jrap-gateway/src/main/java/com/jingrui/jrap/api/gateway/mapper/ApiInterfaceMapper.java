package com.jingrui.jrap.api.gateway.mapper;

import com.jingrui.jrap.mybatis.common.Mapper;
import com.jingrui.jrap.api.gateway.dto.ApiInterface;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 接口 mapper.
 * 
 * @author peng.jiang@jingrui.com
 * @date: 2017/9/21.
 **/

public interface ApiInterfaceMapper extends Mapper<ApiInterface>{

    /**
     * 根据服务Id删除服务.
     * 
     * @param serverId
     * @return
     */
    int removeByServerId(@Param("serverId") Long serverId);

    /**
     * 根据服务Id获取接口.
     * 
     * @param srInterface
     * @return
     */
    List<ApiInterface> selectByServerId(ApiInterface srInterface);

    /**
     * 根据客户端Id和服务Id查询接口.
     * 
     * @param clientId
     * @param serverId
     * @return
     */
    List<ApiInterface> selectByServerIdWithLimit(@Param("clientId") String clientId,
                                                 @Param("serverId") Long serverId);

    /**
     * 根据服务代码查询接口.
     * 
     * @param serverCode
     * @return
     */
    List<ApiInterface> selectInterfacesByServerCode(String serverCode);
}