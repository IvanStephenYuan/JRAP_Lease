package com.jingrui.jrap.intergration.service;

import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.core.ProxySelf;
import com.jingrui.jrap.intergration.dto.JrapInterfaceHeader;
import com.jingrui.jrap.system.service.IBaseService;
import com.jingrui.jrap.intergration.dto.JrapInterfaceLine;

import java.util.List;

/**
 * Created by user on 2016/7/26.
 */
public interface IJrapInterfaceLineService extends IBaseService<JrapInterfaceLine>,ProxySelf<IJrapInterfaceLineService> {

    List<JrapInterfaceLine> getLineAndLineTl(IRequest request, JrapInterfaceLine hapInterfaceLine);

    List<JrapInterfaceLine> getLinesByHeaderId(IRequest request,JrapInterfaceLine lineAndLineTlDTO,int page,int pagesize);

    int insertLine(IRequest request, JrapInterfaceLine hapInterfaceLine);

    int updateLine(IRequest request, JrapInterfaceLine hapInterfaceLine);

    int batchDeleteByHeaders( IRequest request,List<JrapInterfaceHeader> lists);
}
