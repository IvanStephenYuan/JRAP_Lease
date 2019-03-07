package com.jingrui.jrap.intergration.service;

import com.jingrui.jrap.core.ProxySelf;
import com.jingrui.jrap.intergration.dto.JrapInterfaceOutbound;
import com.jingrui.jrap.system.service.IBaseService;

public interface IJrapInterfaceOutboundService
        extends IBaseService<JrapInterfaceOutbound>, ProxySelf<IJrapInterfaceOutboundService> {

    int outboundInvoke(JrapInterfaceOutbound outbound);
}