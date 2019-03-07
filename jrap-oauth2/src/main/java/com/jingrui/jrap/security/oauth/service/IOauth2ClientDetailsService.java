package com.jingrui.jrap.security.oauth.service;

import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.core.ProxySelf;
import com.jingrui.jrap.security.oauth.dto.Oauth2ClientDetails;
import com.jingrui.jrap.system.service.IBaseService;

/**
 * oauth2客户端服务 - 实现类.
 *
 * @author qixiangyu
 */
public interface IOauth2ClientDetailsService extends IBaseService<Oauth2ClientDetails>, ProxySelf<IOauth2ClientDetailsService> {

    /**
     * 更新客户端.
     *
     * @param request       IRequest
     * @param clientDetails
     * @return
     */
    Oauth2ClientDetails updateClient(IRequest request, Oauth2ClientDetails clientDetails);

    /**
     * 根据客户端ID获取clientDetails.
     *
     * @param clientID 客户端ID
     * @return
     */
    Oauth2ClientDetails selectByClientId(String clientID);

    /**
     * 生成新的client Secret.
     *
     * @param id 客户端主键ID
     * @return 新密码
     */
    String updatePassword(Long id);

    /**
     * 通过ID获取Oauth2客户端信息.
     *
     * @param id 主键ID
     * @return Oauth2客户端信息
     */
    Oauth2ClientDetails selectById(Long id);
}