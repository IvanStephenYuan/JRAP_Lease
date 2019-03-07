package com.jingrui.jrap.mail.service.impl;

import com.github.pagehelper.PageHelper;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.mail.dto.MessageEmailAccount;
import com.jingrui.jrap.mail.mapper.MessageEmailAccountMapper;
import com.jingrui.jrap.mail.service.IMessageEmailAccountService;
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
 * 邮件账号服务接口实现.
 *
 * @author Clerifen Li
 */

@Service
public class MessageEmailAccountServiceImpl extends BaseServiceImpl<MessageEmailAccount> implements IMessageEmailAccountService, BeanFactoryAware {

    private BeanFactory beanFactory;

    @Autowired
    private MessageEmailAccountMapper mapper;

    @Autowired
    private IAESClientService aceClientService;

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MessageEmailAccount createMessageEmailAccount(IRequest request, MessageEmailAccount obj) {
        if (obj == null) {
            return null;
        }
        obj.setPassword(aceClientService.encrypt(obj.getPassword()));
        mapper.insertSelective(obj);
        return obj;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MessageEmailAccount updateMessageEmailAccount(IRequest request, MessageEmailAccount obj) {
        if (obj == null) {
            return null;
        }
        // 不处理password
        obj.setPassword(null);
        int updateCount = mapper.updateByPrimaryKeySelective(obj);
        checkOvn(updateCount, obj);
        return obj;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MessageEmailAccount updateMessageEmailAccountPasswordOnly(IRequest request, MessageEmailAccount obj) {
        if (obj == null) {
            return null;
        }
        obj.setPassword(aceClientService.encrypt(obj.getPassword()));
        int updateCount = mapper.updateByPrimaryKeySelective(obj);
        checkOvn(updateCount, obj);
        return obj;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public MessageEmailAccount selectMessageEmailAccountById(IRequest request, Long objId) {
        if (objId == null) {
            return null;
        }
        return mapper.selectByPrimaryKey(objId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteMessageEmailAccount(IRequest request, MessageEmailAccount obj) {
        if (obj.getAccountId() == null) {
            return 0;
        }
        int updateCount = mapper.deleteByPrimaryKey(obj);
        checkOvn(updateCount, obj);
        return updateCount;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchDelete(IRequest request, List<MessageEmailAccount> objs) {
        int result = 0;
        if (CollectionUtils.isEmpty(objs)) {
            return result;
        }

        for (MessageEmailAccount obj : objs) {
            self().deleteMessageEmailAccount(request, obj);
            result++;
        }
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<MessageEmailAccount> selectMessageEmailAccounts(IRequest request, MessageEmailAccount example, int page,
                                                                int pageSize) {
        PageHelper.startPage(page, pageSize);
        return mapper.selectMessageEmailAccounts(example);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<MessageEmailAccount> selectMessageEmailAccountWithPassword(IRequest request, MessageEmailAccount example, int page,
                                                                           int pageSize) {
        PageHelper.startPage(page, pageSize);
        return mapper.selectMessageEmailAccountPassword(example);
    }

}
