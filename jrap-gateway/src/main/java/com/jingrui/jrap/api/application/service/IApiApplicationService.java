package com.jingrui.jrap.api.application.service;

import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.core.ProxySelf;
import com.jingrui.jrap.api.gateway.dto.ApiServer;
import com.jingrui.jrap.core.annotation.StdWho;
import com.jingrui.jrap.system.service.IBaseService;
import com.jingrui.jrap.api.application.dto.ApiApplication;

import java.util.List;

/**
 * 访问限制service - 实现类.
 *
 * @author lijian.yin@jingrui.com
 * @date 2017/11/15.
 **/

public interface IApiApplicationService extends IBaseService<ApiApplication>, ProxySelf<IApiApplicationService>{

    /**
     * 根据应用ID获取应用.
     *
     * @param request
     * @param applicationId
     * @return
     */
    ApiApplication getById(IRequest request, Long applicationId);

    /**
     * 添加应用.
     *
     * @param request
     * @param application
     * @return
     */
    ApiApplication insertApplication(IRequest request, ApiApplication application);

    /**
     * 修改应用.
     *
     * @param request
     * @param application
     * @return
     */
    ApiApplication updateApplication(IRequest request, @StdWho ApiApplication application);

    /**
     * 查询应用未关联的服务.
     *
     * @param request
     * @param exitsCodes
     * @param srServer
     * @param page
     * @param pageSize
     * @return
     */
    List<ApiServer> selectNotExistsServerByApp(IRequest request, String exitsCodes, ApiServer srServer , int page, int pageSize);

    /**
     * 条件查询应用.
     *
     * @param request
     * @param apiApplication
     * @param page
     * @param pageSize
     * @return
     */
    List<ApiApplication> selectApplications(IRequest request, ApiApplication apiApplication, int page, int pageSize);
}