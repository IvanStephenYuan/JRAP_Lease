package com.jingrui.jrap.intergration.mapper;

import com.jingrui.jrap.mybatis.common.Mapper;
import com.jingrui.jrap.intergration.dto.JrapInterfaceInbound;

import java.util.List;

public interface JrapInterfaceInboundMapper extends Mapper<JrapInterfaceInbound>{

    List<JrapInterfaceInbound> select (JrapInterfaceInbound inbound);
}