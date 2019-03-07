package com.jingrui.jrap.api.application.mapper;

import com.jingrui.jrap.mybatis.common.Mapper;
import com.jingrui.jrap.api.application.dto.ApiApplication;
import com.jingrui.jrap.api.gateway.dto.ApiServer;
import com.jingrui.jrap.security.oauth.dto.Oauth2ClientDetails;

import java.util.List;
import java.util.Map;

/**
 * 应用 mapper.
 *
 * @author lijian.yin@jingrui.com
 * @date 2017/11/15.
 **/

public interface ApiApplicationMapper extends Mapper<ApiApplication>{

    /**
     * 获取应用.
     *
     * @param applicationId
     * @return
     */
    ApiApplication getById(Long applicationId);

    /**
     * 查询应用.
     *
     * @param record
     * @return
     */
    List<ApiApplication> selectApplications(ApiApplication record);


}