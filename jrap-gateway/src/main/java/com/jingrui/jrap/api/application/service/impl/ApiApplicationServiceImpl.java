package com.jingrui.jrap.api.application.service.impl;

import com.github.pagehelper.PageHelper;
import com.jingrui.jrap.api.application.dto.ApiApplication;
import com.jingrui.jrap.api.application.mapper.ApiApplicationMapper;
import com.jingrui.jrap.api.application.service.IApiAccessLimitService;
import com.jingrui.jrap.api.application.service.IApiApplicationService;
import com.jingrui.jrap.api.gateway.dto.ApiServer;
import com.jingrui.jrap.api.gateway.service.IApiServerService;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.core.annotation.StdWho;
import com.jingrui.jrap.security.oauth.dto.Oauth2ClientDetails;
import com.jingrui.jrap.security.oauth.service.IOauth2ClientDetailsService;
import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 应用service - 实现类
 * @author lijian.yin@jingrui.com
 **/

@Service
public class ApiApplicationServiceImpl extends BaseServiceImpl<ApiApplication> implements IApiApplicationService {

    @Autowired
    private ApiApplicationMapper applicationMapper;

    @Autowired
    private IOauth2ClientDetailsService clientService;

    @Autowired
    private IApiServerService serverService;

    @Autowired
    private IApiAccessLimitService accessLimitService;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public ApiApplication getById(IRequest request, Long applicationId) {
        ApiApplication application = applicationMapper.getById(applicationId);
        String[] ids = StringUtils.commaDelimitedListToStringArray(application.getClient().getScope());
        if(ids != null && ids.length >0) {
            application.setServers(serverService.selectByCodes(request, Arrays.asList(ids)));
        }
        return application;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public List<ApiApplication> selectApplications(IRequest request, ApiApplication apiApplication, int page, int pageSize) {
        PageHelper.startPage(page,pageSize);
        return applicationMapper.selectApplications(apiApplication);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public List<ApiServer> selectNotExistsServerByApp(IRequest request, String exitsCodes, ApiServer server, int page, int pageSize) {
        Map<String, Object> params = new HashMap<>(2);
        if(!StringUtils.isEmpty(exitsCodes)) {
            params.put("codeList", StringUtils.commaDelimitedListToStringArray(exitsCodes));
        }
        params.put("server",server);
        PageHelper.startPage(page,pageSize);
        return serverService.selectNotExistsServerByApp(params);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<ApiApplication> batchUpdate(IRequest request, List<ApiApplication> list) {
        for(ApiApplication application:list){
            if(application.getApplicationId() != null){
                self().updateApplication(request,application);
            }else{
                self().insertApplication(request,application);
            }
        }
        return list;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ApiApplication insertApplication(IRequest request, ApiApplication application) {
        //处理scope
        //application = processServers(application);
//        application.getClient().setApplicationCode(application.getCode());
        Oauth2ClientDetails client = clientService.insertSelective(request,application.getClient());
        application.setCliId(client.getId());
        applicationMapper.insertSelective(application);
        // 绑定解绑server时，interface级联
        accessLimitService.updateByApplication("", application);
        return application;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ApiApplication updateApplication(IRequest request, @StdWho ApiApplication application) {
        // 修改application
        updateByPrimaryKey(request, application);
        // 级联操作
        Oauth2ClientDetails oauth2ClientDetails =
                clientService.selectByClientId(application.getClient().getClientId());
//        application.getClient().setApplicationCode(application.getCode());
        clientService.updateClient(request,application.getClient());
        String before = oauth2ClientDetails.getScope();
        // 解绑时 删除;
        accessLimitService.updateByApplication(before, application);
        //级联修改AccessLimit
        accessLimitService.updateAccessLimit(request, application);
        return application;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteByPrimaryKey(ApiApplication record) {
        Long cliId = record.getCliId();
        Oauth2ClientDetails clientDetails = new Oauth2ClientDetails();
        clientDetails.setId(cliId);
        // 删除application时，删除对应访问权限记录
        accessLimitService.removeByClientId(cliId);
        clientService.deleteByPrimaryKey(clientDetails);
        return applicationMapper.deleteByPrimaryKey(record);
    }

}