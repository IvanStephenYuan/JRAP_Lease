package com.jingrui.jrap.intergration.controllers;

import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.intergration.dto.JrapInterfaceInbound;
import com.jingrui.jrap.intergration.dto.JrapInterfaceOutbound;
import com.jingrui.jrap.message.components.InvokeLogManager;
import com.jingrui.jrap.system.controllers.BaseController;
import com.jingrui.jrap.system.dto.ResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


/**
 * xiangyu.qi@jingrui.com 2016/11/01
 * @version 2016/11/22
 */

@Controller
@RequestMapping(value={"/sys/invoke","/api/sys/invoke"})
public class JrapInterfaceInvokeController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(JrapInterfaceInvokeController.class);

    @Autowired
    private InvokeLogManager logManager;


    @RequestMapping(value = "/querryInbound" ,produces = "application/json")
    @ResponseBody
    public ResponseData queryInbound(@RequestBody  JrapInterfaceInbound inbound,  HttpServletRequest request){
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(logManager.getInvokeLogStrategy().queryInbound(requestContext, inbound, inbound.getPage(), inbound.getPagesize()));
    }

    @RequestMapping(value = "/querryOutbound")
    @ResponseBody
    public ResponseData queryOutbound(@RequestBody  JrapInterfaceOutbound outbound,  HttpServletRequest request){
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(logManager.getInvokeLogStrategy().queryOutbound(requestContext, outbound, outbound.getPage(), outbound.getPagesize()));
    }

   /* @RequestMapping(value = "/removeInbound")
    @ResponseBody
    public ResponseData deleteInbound(HttpServletRequest request,@RequestBody List<JrapInterfaceInbound> inbounds){
        inboundService.batchDelete(inbounds);
        return new ResponseData();
    }

    @RequestMapping(value = "/removeOutbound")
    @ResponseBody
    public ResponseData deleteOutbound(HttpServletRequest request,@RequestBody List<JrapInterfaceOutbound> outbounds){
        outboundService.batchDelete(outbounds);
        return new ResponseData();
    }*/

}
