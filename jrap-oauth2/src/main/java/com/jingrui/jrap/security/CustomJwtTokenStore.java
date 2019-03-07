package com.jingrui.jrap.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jingrui.jrap.core.impl.RequestHelper;
import com.jingrui.jrap.security.oauth.dto.TokenLogs;
import com.jingrui.jrap.security.oauth.service.ITokenLogsService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.Calendar;

import java.util.concurrent.TimeUnit;

/**
 * @author Qixiangyu
 * @date 2017/5/3.
 */
public class CustomJwtTokenStore extends JwtTokenStore {

    public final static String REDIS_CATALOG = "jrap:cache:oauth_token:";

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Autowired
    ITokenLogsService tokenLogsService;

    @Autowired
    ObjectMapper objectMapper;

    private Logger logger = LoggerFactory.getLogger(CustomJwtTokenStore.class);

    public CustomJwtTokenStore(JwtAccessTokenConverter jwtTokenEnhancer) {
        super(jwtTokenEnhancer);
    }

    @Override
    public OAuth2AccessToken readAccessToken(String tokenValue) {
        String tokenState = redisTemplate.opsForValue().get(REDIS_CATALOG + tokenValue);
        if (StringUtils.isNotEmpty(tokenState)) {
            OAuth2AccessToken accessToken = super.readAccessToken(tokenValue);
            return accessToken;
        }
        return null;
    }

    @Override
    public void removeAccessToken(OAuth2AccessToken token) {
        redisTemplate.delete(REDIS_CATALOG + token.getValue());
        tokenLogsService.revokeToken(token.getValue());
        super.removeAccessToken(token);
    }

    @Override
    public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
        String clientId = authentication.getOAuth2Request().getClientId();
        if(!StringUtils.equals(clientId, CustomJdbcClientDetailsService.DEFAULT_CLIENT_ID)) {
            TokenLogs tokenLogs = new TokenLogs();
            Object principal = authentication.getPrincipal();
            if (principal instanceof CustomUserDetails) {
                CustomUserDetails details = (CustomUserDetails) principal;
                tokenLogs.setUserId(details.getUserId());
            }
            tokenLogs.setToken(token.getValue());
            tokenLogs.setClientId(authentication.getOAuth2Request().getClientId());
            tokenLogs.setTokenAccessType(authentication.getOAuth2Request().getGrantType());
            //token获取时间和失效时间
            Calendar expiration = Calendar.getInstance();
            expiration.setTime(token.getExpiration());
            expiration.add(Calendar.SECOND,-token.getExpiresIn());
            tokenLogs.setTokenAccessTime(expiration.getTime());
            tokenLogs.setTokenExpiresTime(token.getExpiration());
            tokenLogsService.insertSelective(RequestHelper.getCurrentRequest(true), tokenLogs);
            String tokenString = "";
            try {
                tokenString = objectMapper.writeValueAsString(tokenLogs);
            } catch (JsonProcessingException e) {
                logger.error(e.getMessage(), e);
            }

            redisTemplate.opsForValue().set(REDIS_CATALOG + token.getValue(), tokenString, token.getExpiresIn(),
                    TimeUnit.SECONDS);
        }
    }

}
