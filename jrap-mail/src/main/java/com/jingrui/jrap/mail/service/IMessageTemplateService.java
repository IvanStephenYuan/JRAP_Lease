package com.jingrui.jrap.mail.service;

import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.core.ProxySelf;
import com.jingrui.jrap.core.annotation.StdWho;
import com.jingrui.jrap.core.exception.EmailException;
import com.jingrui.jrap.mail.dto.MessageTemplate;

import java.util.List;

/**
 * 消息模板服务接口.
 *
 * @author Clerifen Li
 */
public interface IMessageTemplateService extends ProxySelf<IMessageTemplateService> {
    /**
     * 新建消息模板.
     *
     * @param request IRequest
     * @param obj     消息模板
     * @return 消息模板
     * @throws EmailException 邮件异常
     */
    MessageTemplate createMessageTemplate(IRequest request, @StdWho MessageTemplate obj) throws EmailException;

    /**
     * 更新消息模板.
     *
     * @param request IRequest
     * @param obj     消息模板
     * @return 消息模板
     * @throws EmailException 邮件异常
     */
    MessageTemplate updateMessageTemplate(IRequest request, @StdWho MessageTemplate obj) throws EmailException;

    /**
     * 根据模板Id查询消息模板.
     *
     * @param request IRequest
     * @param objId   模板Id
     * @return 消息模板
     */
    MessageTemplate selectMessageTemplateById(IRequest request, Long objId);

    /**
     * 条件查询消息模板.
     *
     * @param request         IRequest
     * @param messageTemplate 消息模板
     * @param page            页码
     * @param pageSize        页数
     * @return 消息模板列表
     */
    List<MessageTemplate> selectMessageTemplates(IRequest request, MessageTemplate messageTemplate, int page, int pageSize);

    /**
     * 删除单个消息模板.
     *
     * @param request  IRequest
     * @param template 消息模板
     * @return int
     */
    int deleteMessageTemplate(IRequest request, MessageTemplate template);

    /**
     * 批量删除消息模板.
     *
     * @param request      IRequest
     * @param templateList 消息模板 列表
     * @return int
     */
    int batchDelete(IRequest request, List<MessageTemplate> templateList);

}
