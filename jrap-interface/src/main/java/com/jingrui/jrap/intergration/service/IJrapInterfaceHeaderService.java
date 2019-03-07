package com.jingrui.jrap.intergration.service;

import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.core.ProxySelf;
import com.jingrui.jrap.intergration.dto.JrapInterfaceHeader;
import com.jingrui.jrap.system.service.IBaseService;

import java.util.List;

/**
 * @author jiguang.sun@jingrui.com
 * @version 2016/7/21.
 */
public interface IJrapInterfaceHeaderService extends IBaseService<JrapInterfaceHeader>, ProxySelf<IJrapInterfaceHeaderService> {


    List<JrapInterfaceHeader> getAllHeader(IRequest requestContext ,JrapInterfaceHeader interfaceHeader,int page, int pagesize);

    List<JrapInterfaceHeader> getHeaderAndLineList(IRequest requestContext ,JrapInterfaceHeader interfaceHeader);

    JrapInterfaceHeader getHeaderAndLine(String sysName, String apiName);

    List<JrapInterfaceHeader> getAllHeaderAndLine();

    List<JrapInterfaceHeader> getAllHeaderAndLine(int page, int pagesize);

    List<JrapInterfaceHeader> getHeaderByHeaderId(IRequest requestContext,JrapInterfaceHeader JrapInterfaceHeader);

    JrapInterfaceHeader getHeaderAndLineByLineId(JrapInterfaceHeader JrapInterfaceHeader);

    int updateHeader(IRequest request, JrapInterfaceHeader hmsInterfaceHeader);

    void createInterface(IRequest iRequest, JrapInterfaceHeader interfaceHeader);

    void updateInterface(IRequest iRequest, JrapInterfaceHeader interfaceHeader);

}
