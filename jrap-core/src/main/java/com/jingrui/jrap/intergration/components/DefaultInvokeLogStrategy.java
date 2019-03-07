package com.jingrui.jrap.intergration.components;

import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.intergration.InvokeLogStrategy;
import com.jingrui.jrap.intergration.dto.JrapInterfaceInbound;
import com.jingrui.jrap.intergration.dto.JrapInterfaceOutbound;
import com.jingrui.jrap.intergration.service.IJrapInterfaceInboundService;
import com.jingrui.jrap.intergration.service.IJrapInterfaceOutboundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author xiangyu.qi@jingrui.com on 2017/9/23.
 */
@Component
public class DefaultInvokeLogStrategy implements InvokeLogStrategy {

    @Autowired
    private IJrapInterfaceInboundService inboundService;

    @Autowired
    private IJrapInterfaceOutboundService outboundService;

    @Override
    public void logInbound(JrapInterfaceInbound inbound) {
        //JrapInvokeLogUtils.processExceptionInfo(inbound,throwable);
        inboundService.inboundInvoke(inbound);
    }

    @Override
    public void logOutbound(JrapInterfaceOutbound outbound) {
        //JrapInvokeLogUtils.processExceptionInfo(outbound,throwable);
        outboundService.outboundInvoke(outbound);
    }

    @Override
    public List<JrapInterfaceInbound> queryInbound(IRequest request, JrapInterfaceInbound condition, int pageNum, int pageSize) {
        return inboundService.select(request, condition, pageNum, pageSize);
    }

    @Override
    public List<JrapInterfaceOutbound> queryOutbound(IRequest request, JrapInterfaceOutbound condition, int pageNum, int pageSize) {
        return outboundService.select(request,condition,pageNum,pageSize);
    }
}
