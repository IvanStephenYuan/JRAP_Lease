package com.jingrui.jrap.intergration.service;

/**
 * Created by  peng.jiang@jingrui.com on 2017/9/29.
 */
public interface AuthenticationAdapter {

    String getGrantType();

    String getClientId();

    String getClientSecret();

    String getScope();

    String getAuthUsername();

    String getAuthPassword();

    String getAccessTokenUrl();

    String getAccessTokenKey();

    String getRefreshTokenKey();

}
