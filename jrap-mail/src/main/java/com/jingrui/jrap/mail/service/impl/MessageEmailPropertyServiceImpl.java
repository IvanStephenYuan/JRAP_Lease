package com.jingrui.jrap.mail.service.impl;

import com.jingrui.jrap.mail.dto.MessageEmailProperty;
import com.jingrui.jrap.mail.mapper.MessageEmailPropertyMapper;
import com.jingrui.jrap.mail.service.IMessageEmailPropertyService;
import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 邮件服务器属性服务接口实现.
 *
 * @author qiang.zeng@jingrui.com
 */
@Service
public class MessageEmailPropertyServiceImpl extends BaseServiceImpl<MessageEmailProperty> implements IMessageEmailPropertyService {

    @Autowired
    private MessageEmailPropertyMapper messageEmailPropertyMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteByConfigId(MessageEmailProperty obj) {
        if (obj.getPropertyId() == null) {
            return 0;
        }
        int result = messageEmailPropertyMapper.deleteByPrimaryKey(obj);
        checkOvn(result, obj);
        return result;
    }


}