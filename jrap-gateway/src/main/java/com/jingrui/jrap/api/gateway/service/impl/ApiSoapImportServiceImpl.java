package com.jingrui.jrap.api.gateway.service.impl;

import com.jingrui.jrap.api.ApiConstants;
import com.jingrui.jrap.api.gateway.dto.ApiServer;
import com.jingrui.jrap.api.gateway.service.IApiImportService;
import com.jingrui.jrap.api.gateway.WsdlParser;
import org.springframework.stereotype.Service;

/**
 * soap服务导入 service - 实现类.
 *
 * @author peng.jiang@jingrui.com
 * @date 2017/9/20.
 */
@Service("soapImportServer")
public class ApiSoapImportServiceImpl implements IApiImportService {


    @Override
    public ApiServer importServer(ApiServer srServer) {

        ApiServer result = new ApiServer();
        result.setCode(srServer.getCode());
        result.setServiceType(ApiConstants.SERVER_TYPE_SOAP);
        try {
            WsdlParser wsdlParse = new WsdlParser();
            result = wsdlParse.parseWSDL( result , srServer.getImportUrl());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}