package com.jingrui.jrap.message.components;

import com.jingrui.jrap.intergration.beans.JrapinterfaceBound;
import com.jingrui.jrap.message.IMessageConsumer;
import com.jingrui.jrap.message.QueueMonitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author xiangyu.qi@jingrui.com on 2017/11/27.
 */
@Component
@QueueMonitor(queue = InvokeLogManager.CHANNEL_OUTBOUND)
public class OutInvokeLogMessageConsumer  implements IMessageConsumer<JrapinterfaceBound>{

    @Autowired
    private InvokeLogManager invokeLogManager;

    @Override
    public void onMessage(JrapinterfaceBound message, String pattern) {
        invokeLogManager.getInvokeLogStrategy().logOutbound(message.getOutbound());
    }
}
