package com.jingrui.jrap.cache.impl;

import com.jingrui.jrap.mail.dto.MessageTemplate;
import com.jingrui.jrap.mail.mapper.MessageTemplateMapper;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author qiang.zeng@jingrui.com
 */
public class MessageTemplateCache extends HashStringRedisCache<MessageTemplate> {
    private String messageTemplateQueryAllSqlId = MessageTemplateMapper.class.getName() + ".selectAll";
    private String messageTemplateQuerySqlId = MessageTemplateMapper.class.getName() + ".selectByCode";

    private final Logger logger = LoggerFactory.getLogger(MessageTemplateCache.class);

    {
        setLoadOnStartUp(true);
        setType(MessageTemplate.class);
    }


    /**
     * @param key templateId
     * @return value MessageTemplate
     */
    @Override
    public MessageTemplate getValue(String key) {
        return super.getValue(key);
    }

    /**
     * @param key   templateId
     * @param value MessageTemplate
     */
    @Override
    public void setValue(String key, MessageTemplate value) {
        super.setValue(key, value);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void initLoad() {
        Map<String, MessageTemplate> messageTemplateMap = new HashMap<>();
        try (SqlSession sqlSession = getSqlSessionFactory().openSession()) {
            sqlSession.select(messageTemplateQueryAllSqlId, (resultContext) -> {
                MessageTemplate messageTemplate = (MessageTemplate) resultContext.getResultObject();
                messageTemplateMap.put(messageTemplate.getTemplateCode(), messageTemplate);
            });
            messageTemplateMap.forEach((k, v) -> {
                setValue(k, v);
            });
            if (logger.isDebugEnabled()) {
                logger.debug("successfully loaded all message template cache");
            }
        } catch (Throwable e) {
            if (logger.isErrorEnabled()) {
                logger.error("init message template cache exception: ", e);
            }
        }
    }

    public void reload(String templateCode) {
        super.remove(templateCode);
        try (SqlSession sqlSession = getSqlSessionFactory().openSession()) {
            sqlSession.select(messageTemplateQuerySqlId, templateCode, (resultContext) -> {
                MessageTemplate messageTemplate = (MessageTemplate) resultContext.getResultObject();
                setValue(messageTemplate.getTemplateCode(), messageTemplate);
            });
        } catch (Throwable e) {
            if (logger.isErrorEnabled()) {
                logger.error("load message template cache exception: ", e);
            }
        }
    }
}
