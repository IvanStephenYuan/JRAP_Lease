package com.jingrui.jrap.api.application.service.impl;

import com.jingrui.jrap.api.application.service.IApiAccessLimitService;
import com.jingrui.jrap.cache.impl.ApiAccessLimitCache;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.api.application.dto.ApiAccessLimit;
import com.jingrui.jrap.api.application.dto.ApiApplication;
import com.jingrui.jrap.api.gateway.dto.ApiInterface;
import com.jingrui.jrap.api.gateway.dto.ApiServer;
import com.jingrui.jrap.api.application.mapper.ApiAccessLimitMapper;
import com.jingrui.jrap.core.annotation.StdWho;
import com.jingrui.jrap.security.oauth.dto.Oauth2ClientDetails;
import com.jingrui.jrap.security.oauth.service.IOauth2ClientDetailsService;
import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 访问限制server - 实现类.
 *
 * @author lijian.yin@jingrui.com
 * @date 2017/11/15.
 **/

@Service
public class ApiAccessLimitServiceImpl extends BaseServiceImpl<ApiAccessLimit> implements IApiAccessLimitService {

    @Autowired
    private ApiAccessLimitMapper accessLimitMapper;

    @Autowired
    private ApiAccessLimitCache apiAccessLimitCache;

    @Autowired
    private IOauth2ClientDetailsService oauth2ClientDetailsService;

    private static final Logger logger = LoggerFactory.getLogger(ApiAccessLimitServiceImpl.class);

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<ApiAccessLimit> batchUpdate(IRequest request, List<ApiAccessLimit> apiAccessLimitList) {
        for(ApiAccessLimit apiAccessLimit: apiAccessLimitList){
            if(apiAccessLimit.getId() == null){
                self().insertSelective(request,apiAccessLimit);
            }else{
                self().updateByPrimaryKey(request,apiAccessLimit);
            }
            apiAccessLimitCache.setValue(apiAccessLimit);
        }
        return apiAccessLimitList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateByApplication(String before, @StdWho ApiApplication apiApplication) {

        String after = apiApplication.getClient().getScope();
        String clientId = apiApplication.getClient().getClientId();
        logger.info("updateByApplication");

        List<String> befores = Arrays.asList(before.split(","));
        List<String> afters = Arrays.asList(after.split(","));
        List<String> changeCode = new ArrayList<>();
        // 解绑的serverCode
        befores.forEach(item -> {
            if(!afters.contains(item)) {
                changeCode.add(item);
            }
        });
        changeCode.forEach(code -> {
            String key = clientId + "_" + code;
            // 缓存
            apiAccessLimitCache.remove(key);
            ApiAccessLimit apiAccessLimit = new ApiAccessLimit();
            apiAccessLimit.setClientId(clientId);
            apiAccessLimit.setServerCode(code);
            // 数据库
            accessLimitMapper.removeByClientIdAndServerCode(apiAccessLimit);
        });

        //绑定
        changeCode.clear();
        afters.forEach(item -> {
            if(!StringUtils.isEmpty(item)) {
                if (!befores.contains(item)) {
                    changeCode.add(item);
                }
            }
        });
        // 遍历 change server code
        changeCode.forEach(code -> {
            ApiServer apiServer = null;
            // 绑定server时 修改accessLimit
            if(null != apiApplication.getServers()) {
                apiServer = apiApplication.getServers().stream().filter(server -> {
                    return server.getCode().equalsIgnoreCase(code);
                }).findFirst().get();
            }
            List<ApiAccessLimit> apiAccessLimits = new ArrayList<>();
            if(null != apiServer) {
                List<ApiInterface> apiInterfaces = apiServer.getInterfaces();
                if(null != apiInterfaces) {
                    for (ApiInterface apiInterface : apiInterfaces) {
                        ApiAccessLimit apiAccessLimit = apiInterface.getApiAccessLimit();
                        apiAccessLimit.setClientId(clientId);
                        apiAccessLimit.setServerCode(code);
                        apiAccessLimit.setInterfaceCode(apiInterface.getCode());
                        apiAccessLimits.add(apiAccessLimit);
                    }
                }
            }
            //绑定时，没有编辑限制次数，前台没有传进interface,需数据库获取
            if(apiAccessLimits.size() == 0) {
                apiAccessLimits.addAll(selectLimit(code, clientId));
            } else {
                //保存数据
                apiAccessLimits.forEach(apiAccessLimit -> {
                    accessLimitMapper.insertSelective(apiAccessLimit);
                });
            }
            // 存redis
            apiAccessLimits.forEach(accessLimit -> {
                apiAccessLimitCache.setValue(accessLimit);
            });
        });
    }

    /**
     * 绑定时，没有编辑限制次数，前台没有传进interface,需数据库获取.
     *
     * @param code
     * @param clientId
     */
    private List<ApiAccessLimit> selectLimit(String code, String clientId){
        List<ApiAccessLimit> apiAccessLimits = new ArrayList<>();
        ApiAccessLimit apiAccessLimit = new ApiAccessLimit();
        apiAccessLimit.setServerCode(code);
        apiAccessLimit.setClientId(clientId);
        // 保存数据
        List<ApiAccessLimit> temp = accessLimitMapper.selectByClientIdAndServerCode(apiAccessLimit);
        if(temp != null && temp.size()>0){
            temp.forEach( t->{
                accessLimitMapper.insertSelective(t);
            });
            // 查询
            apiAccessLimits = accessLimitMapper.selectList(apiAccessLimit);
        }
        return apiAccessLimits;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int removeByClientId(Long id) {
        logger.info("deleteByClientId : " + id);
        //根据cliId 获取clientId
        Oauth2ClientDetails oauth2ClientDetails = oauth2ClientDetailsService.selectById(id);
        String clientId = oauth2ClientDetails.getClientId();
        //删除缓存
        String[] scope = oauth2ClientDetails.getScope().split(",");
        for(String key : scope) {
            apiAccessLimitCache.remove(clientId + "_" + key);
        }
        ApiAccessLimit apiAccessLimit = new ApiAccessLimit();
        apiAccessLimit.setClientId(clientId);
        return accessLimitMapper.removeByClientIdAndServerCode(apiAccessLimit);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateAccessLimit(IRequest iRequest, ApiApplication apiApplication) {
        List<ApiServer> servers = apiApplication.getServers();
        String clientId = apiApplication.getClient().getClientId();
        if(null ==  servers){
            return 0;
        }
        servers.stream().forEach(server ->{
            String serverCode = server.getCode();
            List<ApiInterface> interfaces = server.getInterfaces();
            if(null != interfaces) {
                interfaces.stream().forEach(apiInterface -> {
                    ApiAccessLimit apiAccessLimit = apiInterface.getApiAccessLimit();
                    String interfaceCode = apiInterface.getCode();
                    apiAccessLimit.setServerCode(serverCode);
                    apiAccessLimit.setInterfaceCode(interfaceCode);
                    if(null == apiAccessLimit.getClientId()) {
                       apiAccessLimit.setClientId(clientId);
                        mapper.insert(apiAccessLimit);
                    }
                    updateRedisAccessLimit(apiAccessLimit);
                });
            }
        });

        return 0;
    }

    /**
     * 修改AccessLimit
     * @param apiAccessLimit
     */
    @Transactional(rollbackFor = Exception.class)
    private void updateRedisAccessLimit(ApiAccessLimit apiAccessLimit) {
        accessLimitMapper.updateByPrimaryKey(apiAccessLimit);
        String key = apiAccessLimit.getClientId() + "_" + apiAccessLimit.getServerCode();
        Map<String, ApiAccessLimit> apiAccessLimitMap = apiAccessLimitCache.getValue(key);
        if(null == apiAccessLimitMap) {
            apiAccessLimitMap = new HashMap<>(2);
        }
        apiAccessLimitMap.put(apiAccessLimit.getInterfaceCode(), apiAccessLimit);
        apiAccessLimitCache.setValue(key, apiAccessLimitMap);
    }
}