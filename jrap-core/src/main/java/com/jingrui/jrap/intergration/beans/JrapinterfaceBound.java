package com.jingrui.jrap.intergration.beans;

import com.jingrui.jrap.intergration.dto.JrapInterfaceInbound;
import com.jingrui.jrap.intergration.dto.JrapInterfaceOutbound;

import java.io.Serializable;

/**
 * @author xiangyu.qi@jingrui.com on 2017/9/23.
 */
public class JrapinterfaceBound implements Serializable {

    private JrapInterfaceInbound inbound;

    private JrapInterfaceOutbound outbound;


    public JrapinterfaceBound(){

    }

    public JrapinterfaceBound(JrapInterfaceInbound inbound){
        this.inbound = inbound;
    }

    public JrapinterfaceBound(JrapInterfaceOutbound outbound){
        this.outbound = outbound;
    }

    public JrapInterfaceInbound getInbound() {
        return inbound;
    }

    public void setInbound(JrapInterfaceInbound inbound) {
        this.inbound = inbound;
    }

    public JrapInterfaceOutbound getOutbound() {
        return outbound;
    }

    public void setOutbound(JrapInterfaceOutbound outbound) {
        this.outbound = outbound;
    }
}
