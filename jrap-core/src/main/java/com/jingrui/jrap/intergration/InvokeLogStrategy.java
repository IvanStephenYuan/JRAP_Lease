package com.jingrui.jrap.intergration;

import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.intergration.dto.JrapInterfaceInbound;
import com.jingrui.jrap.intergration.dto.JrapInterfaceOutbound;

import java.util.List;

/**
 * @author xiangyu.qi@jingrui.com on 2017/9/23.
 */
public interface InvokeLogStrategy {


    /**
     *  记录入站请求记录
     * @param inbound 入站请求相关信息
     * @return
     */
    void logInbound(JrapInterfaceInbound inbound);

    /**
     *  记录出产请求记录
     * @param outbound 出站请求相关信息
     * @return
     */
    void logOutbound(JrapInterfaceOutbound outbound);

    /**
     *  查询入站请求记录
     * @param request Irequest
     * @param condition 查询条件
     * @param pageNum  分页信息
     * @param pageSize
     * @return
     */
    List<JrapInterfaceInbound> queryInbound(IRequest request, JrapInterfaceInbound condition, int pageNum, int pageSize);


    /**
     *  查询出站请求记录
     * @param request
     * @param condition
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<JrapInterfaceOutbound> queryOutbound(IRequest request,JrapInterfaceOutbound condition, int pageNum, int pageSize);
}
