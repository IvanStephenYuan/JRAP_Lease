package com.jingrui.jrap.message.components;

import com.jingrui.jrap.intergration.InvokeLogStrategy;
import com.jingrui.jrap.intergration.beans.JrapinterfaceBound;
import com.jingrui.jrap.intergration.components.DefaultInvokeLogStrategy;
import com.jingrui.jrap.message.IMessageConsumer;
import com.jingrui.jrap.message.QueueMonitor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @author xiangyu.qi@jingrui.com on 2017/9/23.
 */
@Component
@QueueMonitor(queue = InvokeLogManager.CHANNEL_INBOUND)
public class InvokeLogManager implements IMessageConsumer<JrapinterfaceBound> , InitializingBean {

    public static final String CHANNEL_OUTBOUND = "invoke.outbound";

    public static final String CHANNEL_INBOUND = "invoke.inbound";

    @Autowired(required = false)
    private InvokeLogStrategy invokeLogStrategy;

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void onMessage(JrapinterfaceBound message, String pattern) {
        if(InvokeLogManager.CHANNEL_INBOUND.equalsIgnoreCase(pattern)){
            invokeLogStrategy.logInbound(message.getInbound());
        }else if(InvokeLogManager.CHANNEL_OUTBOUND.equalsIgnoreCase(pattern)){
            invokeLogStrategy.logOutbound(message.getOutbound());
        }
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        if(invokeLogStrategy == null){
            invokeLogStrategy = new DefaultInvokeLogStrategy();
            applicationContext.getAutowireCapableBeanFactory().autowireBean(invokeLogStrategy);
        }
    }

    public InvokeLogStrategy getInvokeLogStrategy(){
        return invokeLogStrategy;
    }
}
