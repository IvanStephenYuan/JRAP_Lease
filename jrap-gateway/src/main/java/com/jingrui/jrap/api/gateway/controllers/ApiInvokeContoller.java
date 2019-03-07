package com.jingrui.jrap.api.gateway.controllers;

import com.jingrui.jrap.api.gateway.dto.ApiServer;
import com.jingrui.jrap.api.gateway.service.IApiInvokeService;
import com.jingrui.jrap.api.gateway.service.IApiServerService;
import com.jingrui.jrap.api.gateway.service.impl.ApiInvokeServiceManager;
import com.jingrui.jrap.api.logs.dto.ApiResponseData;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

/**
 * 访问映射控制器.
 *
 * @author lijian.yin@jingrui.com
 **/

@RestController
public class ApiInvokeContoller {

    @Autowired
    ApiInvokeServiceManager srInvokeServiceManager;

    @Autowired
    private IApiServerService serverService;

    @RequestMapping(value = "/api/rest/{serverUrl}/{interfaceUrl}" , method= { RequestMethod.GET , RequestMethod.POST })
    public ApiResponseData sentRequest(HttpServletRequest request, @PathVariable String serverUrl, @PathVariable String interfaceUrl,
                                       @RequestBody(required = false)JSONObject inbound)throws Exception{
        ApiServer server = serverService.getByMappingUrl(serverUrl,interfaceUrl);

        ApiResponseData apiResponseData = new ApiResponseData();
        List<IApiInvokeService> invokeServices = srInvokeServiceManager.getInvokeServices();
        for(IApiInvokeService service: invokeServices){
            if(service.matchServerType(server.getServiceType())){
                Object result =service.invoke(server,inbound);
                apiResponseData.setRows(Arrays.asList(result));
                break;
            }
        }
        return apiResponseData;
    }
}
