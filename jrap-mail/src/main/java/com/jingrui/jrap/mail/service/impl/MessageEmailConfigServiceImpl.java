package com.jingrui.jrap.mail.service.impl;

import com.github.pagehelper.PageHelper;
import com.jingrui.jrap.cache.impl.MessageEmailConfigCache;
import com.jingrui.jrap.core.BaseConstants;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.core.exception.EmailException;
import com.jingrui.jrap.mail.dto.MessageEmailAccount;
import com.jingrui.jrap.mail.dto.MessageEmailConfig;
import com.jingrui.jrap.mail.dto.MessageEmailProperty;
import com.jingrui.jrap.mail.dto.MessageEmailWhiteList;
import com.jingrui.jrap.mail.mapper.MessageEmailAccountMapper;
import com.jingrui.jrap.mail.mapper.MessageEmailConfigMapper;
import com.jingrui.jrap.mail.mapper.MessageEmailPropertyMapper;
import com.jingrui.jrap.mail.mapper.MessageEmailWhiteListMapper;
import com.jingrui.jrap.mail.service.IMessageEmailConfigService;
import com.jingrui.jrap.security.service.IAESClientService;
import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 邮件配置服务接口实现.
 *
 * @author qiang.zeng@jingrui.com
 */
@Service
public class MessageEmailConfigServiceImpl extends BaseServiceImpl<MessageEmailConfig> implements IMessageEmailConfigService, BeanFactoryAware {

    /**
     * 选择白名单没有设置名单
     */
    public static final String MSG_MESSAGE_NO_WHITE_LIST = "msg.warning.system.email_message_no_whitelist";


    /**
     * 账号列表为空
     */
    public static final String MSG_MESSAGE_NO_ACCOUNT_LIST = "msg.warning.system.email_message_on_accountlist";

    private BeanFactory beanFactory;

    @Autowired
    private MessageEmailConfigMapper mapper;
    @Autowired
    private MessageEmailAccountMapper accountMapper;
    @Autowired
    private MessageEmailWhiteListMapper addressMapper;
    @Autowired
    private MessageEmailPropertyMapper messageEmailPropertyMapper;
    @Autowired
    private IAESClientService aceClientService;
    @Autowired
    private MessageEmailConfigCache configCache;

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MessageEmailConfig createMessageEmailConfig(IRequest request, MessageEmailConfig obj) {
        if (obj == null) {
            return null;
        }
        obj.setPassword(aceClientService.encrypt(obj.getPassword()));
        mapper.insertSelective(obj);
        return obj;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MessageEmailConfig updateMessageEmailConfig(IRequest request, MessageEmailConfig obj) {
        if (obj == null) {
            return null;
        }
        MessageEmailConfig config = mapper.selectByPrimaryKey(obj.getConfigId());
        if (config != null) {
            if (config.getPassword() != null && config.getPassword().equals(obj.getPassword())) {
                // 没有修改密码
                obj.setPassword(null);
            } else {
                // aes加密
                obj.setPassword(aceClientService.encrypt(obj.getPassword()));
            }
        }
        int updateCount = mapper.updateByPrimaryKeySelective(obj);
        checkOvn(updateCount, obj);
        return obj;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public MessageEmailConfig selectMessageEmailConfigById(IRequest request, Long objId) {
        if (objId == null) {
            return null;
        }
        return mapper.selectByPrimaryKey(objId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteMessageEmailConfig(IRequest request, MessageEmailConfig obj) {
        if (obj.getConfigId() == null) {
            return 0;
        }
        accountMapper.deleteByConfigId(obj.getConfigId());
        addressMapper.deleteByConfigId(obj.getConfigId());
        messageEmailPropertyMapper.deleteByConfigId(obj.getConfigId());
        int updateCount = mapper.deleteByPrimaryKey(obj);
        checkOvn(updateCount, obj);
        configCache.remove(obj.getConfigId());
        return updateCount;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchDelete(IRequest request, List<MessageEmailConfig> objs) {
        int result = 0;
        if (CollectionUtils.isEmpty(objs)) {
            return result;
        }

        for (MessageEmailConfig obj : objs) {
            self().deleteMessageEmailConfig(request, obj);
            result++;
        }
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<MessageEmailConfig> selectMessageEmailConfigs(IRequest request, MessageEmailConfig example, int page,
                                                              int pageSize) {
        PageHelper.startPage(page, pageSize);
        return mapper.selectMessageEmailConfigs(example);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchUpdate(IRequest requestContext, MessageEmailConfig obj) throws EmailException {
        if (obj != null) {
            if (CollectionUtils.isEmpty(obj.getEmailAccounts())) {
                // 账号列表为空
                throw new EmailException(MSG_MESSAGE_NO_ACCOUNT_LIST);
            }
            if (BaseConstants.YES.equalsIgnoreCase(obj.getUseWhiteList()) && (CollectionUtils.isEmpty(obj.getWhiteLists()))) {
                // 选择白名单没有设置名单
                throw new EmailException(MSG_MESSAGE_NO_WHITE_LIST);
            }
            if (obj.getConfigId() == null) {
                createMessageEmailConfig(requestContext, obj);
            } else {
                updateMessageEmailConfig(requestContext, obj);
            }

            if (obj.getEmailAccounts() != null) {
                for (MessageEmailAccount current : obj.getEmailAccounts()) {
                    if (current.getAccountId() == null) {
                        current.setObjectVersionNumber(0L);
                        current.setConfigId(obj.getConfigId());
                        createEmailAccount(requestContext, current);
                    } else {
                        updateEmailAccount(requestContext, current);
                    }
                }
            }
            if (obj.getWhiteLists() != null) {
                for (MessageEmailWhiteList current : obj.getWhiteLists()) {
                    if (current.getId() == null) {
                        current.setObjectVersionNumber(0L);
                        current.setConfigId(obj.getConfigId());
                        createAddress(requestContext, current);
                    } else {
                        updateAddress(requestContext, current);
                    }
                }
            }
            if (obj.getPropertyLists() != null) {
                for (MessageEmailProperty current : obj.getPropertyLists()) {
                    if (current.getPropertyId() == null) {
                        current.setObjectVersionNumber(0L);
                        current.setConfigId(obj.getConfigId());
                        createProperty(requestContext, current);
                    } else {
                        updateProperty(requestContext, current);
                    }
                }
            }
        }
        configCache.reload(obj.getConfigId());
    }

    private MessageEmailAccount createEmailAccount(IRequest request, MessageEmailAccount obj) {
        if (obj == null) {
            return null;
        }
        accountMapper.insertSelective(obj);
        return obj;
    }

    private MessageEmailWhiteList createAddress(IRequest request, MessageEmailWhiteList obj) {
        if (obj == null) {
            return null;
        }
        addressMapper.insertSelective(obj);
        return obj;
    }

    private MessageEmailAccount updateEmailAccount(IRequest request, MessageEmailAccount obj) {
        if (obj == null) {
            return null;
        }
        int updateCount = accountMapper.updateByPrimaryKeySelective(obj);
        checkOvn(updateCount, obj);
        return obj;
    }

    private MessageEmailWhiteList updateAddress(IRequest request, MessageEmailWhiteList obj) {
        if (obj == null) {
            return null;
        }
        int updateCount = addressMapper.updateByPrimaryKeySelective(obj);
        checkOvn(updateCount, obj);
        return obj;
    }

    private MessageEmailProperty createProperty(IRequest request, MessageEmailProperty obj) {
        if (obj == null) {
            return null;
        }
        messageEmailPropertyMapper.insertSelective(obj);
        return obj;
    }

    private MessageEmailProperty updateProperty(IRequest request, MessageEmailProperty obj) {
        if (obj == null) {
            return null;
        }
        int updateCount = messageEmailPropertyMapper.updateByPrimaryKeySelective(obj);
        checkOvn(updateCount, obj);
        return obj;
    }

}
