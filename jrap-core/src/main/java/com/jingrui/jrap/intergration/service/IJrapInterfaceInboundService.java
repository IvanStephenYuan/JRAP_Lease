package com.jingrui.jrap.intergration.service;

import com.jingrui.jrap.core.ProxySelf;
import com.jingrui.jrap.system.service.IBaseService;
import com.jingrui.jrap.intergration.dto.JrapInterfaceInbound;

import javax.servlet.http.HttpServletRequest;

public interface IJrapInterfaceInboundService
        extends IBaseService<JrapInterfaceInbound>, ProxySelf<IJrapInterfaceInboundService> {

    @Deprecated
    int inboundInvoke(HttpServletRequest request, JrapInterfaceInbound inbound, Throwable throwable);

    int inboundInvoke(JrapInterfaceInbound inbound);
}