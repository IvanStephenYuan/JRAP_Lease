package com.jingrui.jrap.intergration.mapper;

import com.jingrui.jrap.mybatis.common.Mapper;
import com.jingrui.jrap.intergration.dto.JrapInterfaceOutbound;

import java.util.List;

public interface JrapInterfaceOutboundMapper extends Mapper<JrapInterfaceOutbound>{

    List<JrapInterfaceOutbound> select (JrapInterfaceOutbound outbound);
}