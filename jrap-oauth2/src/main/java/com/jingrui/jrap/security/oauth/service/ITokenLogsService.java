package com.jingrui.jrap.security.oauth.service;

import com.jingrui.jrap.core.ProxySelf;
import com.jingrui.jrap.system.service.IBaseService;
import com.jingrui.jrap.security.oauth.dto.TokenLogs;

/**
 * @author qixiangyu
 */
public interface ITokenLogsService extends IBaseService<TokenLogs>, ProxySelf<ITokenLogsService> {

    /**
     * 失效Token.
     *
     * @param token token
     * @return 影响行数
     */
    int revokeToken(String token);
}