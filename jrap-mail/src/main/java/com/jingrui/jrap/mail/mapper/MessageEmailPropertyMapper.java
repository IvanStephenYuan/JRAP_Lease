package com.jingrui.jrap.mail.mapper;

import com.jingrui.jrap.mail.dto.MessageEmailProperty;
import com.jingrui.jrap.mybatis.common.Mapper;

import java.util.List;

/**
 * 邮件服务器属性Mapper.
 *
 * @author qiang.zeng@jingrui.com
 */
public interface MessageEmailPropertyMapper extends Mapper<MessageEmailProperty> {
    /**
     * 根据邮件配置Id删除邮件服务器属性.
     *
     * @param configId 邮件配置Id
     * @return int
     */
    int deleteByConfigId(long configId);

    /**
     * 根据邮件配置Id查询邮件服务器属性.
     *
     * @param configId 邮件配置Id
     * @return 邮件服务器属性
     */
    List<MessageEmailProperty> selectByConfigId(Long configId);

}