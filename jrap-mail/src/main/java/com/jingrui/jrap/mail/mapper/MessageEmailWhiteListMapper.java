package com.jingrui.jrap.mail.mapper;

import com.jingrui.jrap.mail.dto.MessageEmailWhiteList;
import com.jingrui.jrap.mybatis.common.Mapper;

import java.util.List;

/**
 * 邮件白名单Mapper.
 *
 * @author Clerifen Li
 */
public interface MessageEmailWhiteListMapper extends Mapper<MessageEmailWhiteList> {
    /**
     * 根据邮件配置Id删除邮件白名单.
     *
     * @param configId 邮件配置Id
     * @return int
     */
    int deleteByConfigId(Long configId);

    /**
     * 根据邮件配置Id删除邮件白名单.
     *
     * @param configId 邮件配置Id
     * @return 邮件白名单列表
     */
    List<MessageEmailWhiteList> selectByConfigId(Long configId);

}