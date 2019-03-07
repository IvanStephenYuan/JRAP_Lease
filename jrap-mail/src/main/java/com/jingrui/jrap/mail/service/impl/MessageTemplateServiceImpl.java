package com.jingrui.jrap.mail.service.impl;

import com.github.pagehelper.PageHelper;
import com.jingrui.jrap.cache.impl.MessageTemplateCache;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.core.exception.EmailException;
import com.jingrui.jrap.mail.dto.MessageTemplate;
import com.jingrui.jrap.mail.mapper.MessageTemplateMapper;
import com.jingrui.jrap.mail.service.IMessageTemplateService;
import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 消息模板服务接口实现.
 *
 * @author qiang.zeng@jingrui.com
 */
@Service
public class MessageTemplateServiceImpl extends BaseServiceImpl<MessageTemplate> implements IMessageTemplateService {

    @Autowired
    private MessageTemplateMapper templateMapper;
    @Autowired
    private MessageTemplateCache templateCache;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MessageTemplate createMessageTemplate(IRequest request, MessageTemplate obj) throws EmailException {
        if (obj == null) {
            return null;
        }
        templateMapper.insertSelective(obj);
        templateCache.reload(obj.getTemplateCode());
        return obj;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MessageTemplate updateMessageTemplate(IRequest request, MessageTemplate obj) throws EmailException {
        if (obj == null) {
            return null;
        }
        int updateCount = templateMapper.updateByPrimaryKeySelective(obj);
        checkOvn(updateCount, obj);
        templateCache.reload(obj.getTemplateCode());
        return obj;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public MessageTemplate selectMessageTemplateById(IRequest request, Long objId) {
        if (objId == null) {
            return null;
        }
        return templateMapper.selectByPrimaryKey(objId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteMessageTemplate(IRequest request, MessageTemplate obj) {
        if (obj.getTemplateId() == null) {
            return 0;
        }
        int result = templateMapper.deleteByPrimaryKey(obj);
        checkOvn(result, obj);
        templateCache.reload(obj.getTemplateCode());
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchDelete(IRequest request, List<MessageTemplate> objs) {
        int result = 0;
        if (CollectionUtils.isEmpty(objs)) {
            return result;
        }

        for (MessageTemplate obj : objs) {
            self().deleteMessageTemplate(request, obj);
            result++;
        }
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<MessageTemplate> selectMessageTemplates(IRequest request, MessageTemplate example, int page,
                                                        int pageSize) {
        PageHelper.startPage(page, pageSize);
        return templateMapper.selectMessageTemplates(example);
    }
}
