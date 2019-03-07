package com.jingrui.jrap.api.application.service;

import com.jingrui.jrap.audit.service.IAuditRecordService;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.core.ProxySelf;
import com.jingrui.jrap.api.application.dto.ApiAccessLimit;
import com.jingrui.jrap.api.application.dto.ApiApplication;
import com.jingrui.jrap.core.annotation.StdWho;
import com.jingrui.jrap.system.service.IBaseService;

/**
 * 服务访问限制.
 *
 * @author lijian.yin@jingrui.com
 **/
public interface IApiAccessLimitService extends IBaseService<ApiAccessLimit>, ProxySelf<IApiAccessLimitService>{

    /**
     * 应用绑定 解绑服务 时 同步访问限制记录.
     *
     * @param before
     * @param apiApplication
     */
    void updateByApplication(String before, @StdWho ApiApplication apiApplication);

    /**
     * 根据客户端ID删除记录.
     *
     * @param id
     * @return
     */
    int removeByClientId(Long id);

    /**
     * 修改访问限制.
     *
     * @param iRequest
     * @param apiApplication
     * @return
     */
    int updateAccessLimit(IRequest iRequest, ApiApplication apiApplication);
}