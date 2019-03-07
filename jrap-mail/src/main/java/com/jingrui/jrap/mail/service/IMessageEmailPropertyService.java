package com.jingrui.jrap.mail.service;

import com.jingrui.jrap.core.ProxySelf;
import com.jingrui.jrap.mail.dto.MessageEmailProperty;
import com.jingrui.jrap.system.service.IBaseService;

/**
 * 邮件服务器属性服务接口.
 *
 * @author qiang.zeng@jingrui.com
 */
public interface IMessageEmailPropertyService extends IBaseService<MessageEmailProperty>, ProxySelf<IMessageEmailPropertyService> {
    /**
     * 根据邮件配置Id删除邮件服务器属性.
     *
     * @param obj 邮件服务器属性
     * @return int
     */
    int deleteByConfigId(MessageEmailProperty obj);
}