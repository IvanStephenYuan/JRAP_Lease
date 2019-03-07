package com.jingrui.jrap.api.gateway.service.impl;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import com.jingrui.jrap.api.ApiConstants;
import com.jingrui.jrap.api.gateway.dto.ApiInterface;
import com.jingrui.jrap.api.gateway.dto.ApiServer;
import com.jingrui.jrap.api.gateway.mapper.ApiInterfaceMapper;
import com.jingrui.jrap.api.gateway.mapper.ApiServerMapper;
import com.jingrui.jrap.api.gateway.service.IApiImportService;
import com.jingrui.jrap.api.gateway.service.IApiServerService;
import com.jingrui.jrap.cache.impl.ApiServerCache;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.core.annotation.StdWho;
import com.jingrui.jrap.intergration.service.IJrapAuthenticationService;
import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 透传服务 Service - 实现类.
 *
 * @author peng.jiang@jingrui.com
 * @date 2017/9/15.
 */
@Service
public class ApiServerServiceImpl extends BaseServiceImpl<ApiServer> implements IApiServerService {

    @Autowired
    private ApiServerMapper serverMapper;

    @Autowired
    private ApiInterfaceMapper interfaceMapper;

    @Resource(name = "restImportServer")
    private IApiImportService restImportServer;

    @Resource(name = "soapImportServer")
    private IApiImportService soapImportServer;

    @Autowired
    private ApiServerCache serverCache;

    @Autowired
    private IJrapAuthenticationService authenticationService;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public List<ApiServer> selectByCodes(IRequest request, List<String> codeList) {
        return serverMapper.selectByCodes(codeList);
    }

    @Override
    public ApiServer importServer(IRequest request, ApiServer srServer) {

        String importUrl = srServer.getImportUrl();
        String[] s = importUrl.split("\\?");
        if (s.length == ApiConstants.NUMBER_2 && ApiConstants.SOAP_WSDL.equals(s[1].toLowerCase())) {
            srServer = soapImportServer.importServer(srServer);
        } else {
            srServer = restImportServer.importServer(srServer);
        }
        return srServer;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<ApiServer> batchUpdate(IRequest request, List<ApiServer> servers) {
        for (ApiServer server : servers) {
            if (server.getServerId() == null) {
                self().insertServer(server);
            } else {
                self().updateServer(request, server);
            }
        }
        return servers;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ApiServer insertServer(ApiServer server) {
        serverMapper.insertSelective(server);
        if (server.getInterfaces() != null) {
            processInterfaces(server);
        }
        serverCache.reload(server);
        return server;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ApiServer updateServer(IRequest request, @StdWho ApiServer server) {
        ApiServer preServer = selectByPrimaryKey(request, server);
        int count = serverMapper.updateByPrimaryKey(server);
        checkOvn(count, server);
        if (server.getInterfaces() != null) {
            processInterfaces(server);
        }
        serverCache.remove(preServer);
        serverCache.reload(server);
        if (ApiConstants.AUTH_TYPE_OAUTH2.equalsIgnoreCase(server.getAuthType())) {
            authenticationService.removeToken(server);
        }
        return server;
    }

    private void processInterfaces(ApiServer server) {
        for (ApiInterface srInterface : server.getInterfaces()) {
            if (srInterface.getInterfaceId() == null) {
                // 设置头ID跟行ID一致
                srInterface.setServerId(server.getServerId());
                interfaceMapper.insertSelective(srInterface);
            } else {
                int count = interfaceMapper.updateByPrimaryKey(srInterface);
                checkOvn(count, srInterface);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchDelete(List<ApiServer> servers) {
        for (ApiServer server : servers) {
            interfaceMapper.removeByServerId(server.getServerId());
            int count = serverMapper.deleteByPrimaryKey(server);
            checkOvn(count, server);
            serverCache.remove(server);
        }
        return servers.size();
    }

    @Override
    public ApiServer getByMappingUrl(String serverUrl, String interfaceUrl) {
        return serverCache.getValue(serverUrl, interfaceUrl);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public List<ApiServer> selectNotExistsServerByApp(Map<String, Object> params) {
        return serverMapper.selectNotExistsServerByApp(params);
    }
}