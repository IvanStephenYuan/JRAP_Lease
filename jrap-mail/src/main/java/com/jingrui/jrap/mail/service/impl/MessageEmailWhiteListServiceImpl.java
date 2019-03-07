package com.jingrui.jrap.mail.service.impl;

import com.github.pagehelper.PageHelper;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.mail.dto.MessageEmailWhiteList;
import com.jingrui.jrap.mail.mapper.MessageEmailWhiteListMapper;
import com.jingrui.jrap.mail.service.IMessageEmailWhiteListService;
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
 * 邮件白名单服务接口实现.
 *
 * @author Clerifen Li
 */
@Service
public class MessageEmailWhiteListServiceImpl extends BaseServiceImpl<MessageEmailWhiteList> implements IMessageEmailWhiteListService, BeanFactoryAware {

    private BeanFactory beanFactory;

    @Autowired
    private MessageEmailWhiteListMapper mapper;

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MessageEmailWhiteList createMessageEmailWhiteList(IRequest request, MessageEmailWhiteList obj) {
        if (obj == null) {
            return null;
        }
        mapper.insertSelective(obj);
        return obj;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MessageEmailWhiteList updateMessageEmailWhiteList(IRequest request, MessageEmailWhiteList obj) {
        if (obj == null) {
            return null;
        }
        int updateCount = mapper.updateByPrimaryKeySelective(obj);
        checkOvn(updateCount, obj);
        return obj;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public MessageEmailWhiteList selectMessageEmailWhiteListById(IRequest request, Long objId) {
        if (objId == null) {
            return null;
        }
        return mapper.selectByPrimaryKey(objId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteMessageEmailWhiteList(IRequest request, MessageEmailWhiteList obj) {
        if (obj.getId() == null) {
            return 0;
        }
        int updateCount = mapper.deleteByPrimaryKey(obj);
        checkOvn(updateCount, obj);
        return updateCount;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchDelete(IRequest request, List<MessageEmailWhiteList> objs) {
        int result = 0;
        if (CollectionUtils.isEmpty(objs)) {
            return result;
        }

        for (MessageEmailWhiteList obj : objs) {
            self().deleteMessageEmailWhiteList(request, obj);
            result++;
        }
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<MessageEmailWhiteList> selectMessageEmailWhiteLists(IRequest request, MessageEmailWhiteList example, int page,
                                                                    int pageSize) {
        PageHelper.startPage(page, pageSize);
        return mapper.select(example);
    }

}
