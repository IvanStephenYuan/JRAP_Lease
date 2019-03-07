package com.jingrui.jrap.mail.mapper;

import com.jingrui.jrap.mail.dto.MessageTemplate;
import com.jingrui.jrap.mybatis.common.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 邮件模板Mapper.
 *
 * @author qiang.zeng@jingrui.com
 */
public interface MessageTemplateMapper extends Mapper<MessageTemplate> {
    /**
     * 根据模板编码查询消息模板.
     *
     * @param templateCode 模板编码
     * @return 消息模板
     */
    MessageTemplate selectByCode(String templateCode);

    /**
     * 根据模板Id和模板编码查询消息模板.
     *
     * @param templateId   模板Id
     * @param templateCode 模板编码
     * @return 消息模板
     */
    MessageTemplate getMsgTemByCode(@Param("templateId") Long templateId, @Param("templateCode") String templateCode);

    /**
     * 条件查询消息模板.
     *
     * @param messageTemplate 消息模板
     * @return 消息模板列表
     */
    List<MessageTemplate> selectMessageTemplates(MessageTemplate messageTemplate);
}