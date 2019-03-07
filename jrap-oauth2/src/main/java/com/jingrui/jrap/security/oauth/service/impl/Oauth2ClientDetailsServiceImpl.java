package com.jingrui.jrap.security.oauth.service.impl;

import com.jingrui.jrap.cache.Cache;
import com.jingrui.jrap.cache.CacheManager;
import com.jingrui.jrap.core.BaseConstants;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.mybatis.common.Criteria;
import com.jingrui.jrap.security.oauth.dto.Oauth2ClientDetails;
import com.jingrui.jrap.security.oauth.mapper.Oauth2ClientDetailsMapper;
import com.jingrui.jrap.security.oauth.service.IOauth2ClientDetailsService;
import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * @author xiangyu.qi@jingrui.com
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class Oauth2ClientDetailsServiceImpl extends BaseServiceImpl<Oauth2ClientDetails>
        implements IOauth2ClientDetailsService {

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private Oauth2ClientDetailsMapper detailsMapper;


    @Override
    public Oauth2ClientDetails insertSelective(IRequest request, Oauth2ClientDetails record) {
        detailsMapper.insertSelective(record);
        getClientCache().setValue(record.getClientId(), record);
        return record;
    }

    @Override
    public Oauth2ClientDetails updateClient(IRequest request, Oauth2ClientDetails clientDetails) {
        Criteria criteria = new Criteria(clientDetails);
        criteria.update(Oauth2ClientDetails.FIELD_ACCESS_TOKEN_VALIDITY,Oauth2ClientDetails.FIELD_CLIENT_SECRET,
                Oauth2ClientDetails.FIELD_ADDITIONAL_INFORMATION, Oauth2ClientDetails.FIELD_AUTHORITIES,
                Oauth2ClientDetails.FIELD_AUTO_APPROVE, Oauth2ClientDetails.FIELD_REDIRECTURI,
                Oauth2ClientDetails.FIELD_REFRESH_TOKEN_VALIDITY, Oauth2ClientDetails.FIELD_SCOPE,
                Oauth2ClientDetails.FIELD_AUTHORIZED_GRANTTYPES, Oauth2ClientDetails.FIELD_RESOURCE_IDS);
        int count = detailsMapper.updateByPrimaryKeyOptions(clientDetails, criteria);
        checkOvn(count, clientDetails);
        getClientCache().remove(clientDetails.getClientId());
        getClientCache().setValue(clientDetails.getClientId(),clientDetails);
        return clientDetails;
    }

    @Override
    public List<Oauth2ClientDetails> batchUpdate(IRequest requestContext, List<Oauth2ClientDetails> clientDetailses) {
        for (Oauth2ClientDetails details : clientDetailses) {
            if (details.getId() == null) {
                details.setClientSecret(UUID.randomUUID().toString());
                self().insertSelective(requestContext, details);
            } else {
                self().updateClient(requestContext,details);
            }
        }
        return clientDetailses;
    }


    @Override
    public int deleteByPrimaryKey(Oauth2ClientDetails details) {
        //TODO: 删除对应应用或者不允许删除
        if(StringUtils.isEmpty(details.getClientId())){
            details = detailsMapper.selectByPrimaryKey(details);
        }
        int result = detailsMapper.deleteByPrimaryKey(details);
        //checkOvn(result, details);
        getClientCache().remove(details.getClientId());
        return result;
    }

    @Override
    public Oauth2ClientDetails selectByClientId(String clientID) {
        Oauth2ClientDetails details = getClientCache().getValue(clientID);
        if (details == null) {
            details = detailsMapper.selectByClientId(clientID);
            if (details != null) {
                getClientCache().setValue(clientID, details);
            }
        }
        return details;
    }

    @Override
    public String updatePassword(Long id) {
        String uuid = UUID.randomUUID().toString();
        detailsMapper.updatePassword(id, uuid);
        return uuid;
    }

    Cache<Oauth2ClientDetails> getClientCache() {
        return cacheManager.getCache(BaseConstants.CACHE_OAUTH_CLIENT);
    }

    @Override
    public Oauth2ClientDetails selectById(Long id) {
        return detailsMapper.selectByPrimaryKey(id);
    }
}