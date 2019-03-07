/*
 * #{copyright}#
 */

package com.jingrui.jrap.message.impl;

import com.jingrui.jrap.message.ITopicMessageListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.data.redis.serializer.RedisSerializer;

import com.jingrui.jrap.message.IMessageConsumer;

/**
 * @author shengyang.zhou@jingrui.com
 * @param <T>
 *            消息类型
 */
public class TopicMessageListener<T> implements ITopicMessageListener<T>, BeanNameAware {
    private String[] topic;

    private RedisSerializer<T> redisSerializer;

    private IMessageConsumer<T>[] consumers = new IMessageConsumer[0];

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public String[] getTopic() {
        return topic;
    }

    public void setTopic(String[] topic) {
        this.topic = topic;
    }

    @Override
    public RedisSerializer<T> getRedisSerializer() {
        return redisSerializer;
    }

    public void setRedisSerializer(RedisSerializer<T> redisSerializer) {
        this.redisSerializer = redisSerializer;
    }

    public IMessageConsumer<T>[] getConsumers() {
        return consumers;
    }

    public void setConsumers(IMessageConsumer<T>[] consumers) {
        this.consumers = consumers;
    }

    @Override
    public void onTopicMessage(T message, String pattern) {
        if (logger.isDebugEnabled()) {
            logger.debug("receive message: {}, pattern: {}, consumers: {}", message, pattern, consumers.length);
        }
        for (IMessageConsumer<T> c : consumers) {
            try {
                c.onMessage(message, pattern);
            } catch (Throwable throwable) {
                if (logger.isDebugEnabled()) {
                    logger.error(new StringBuilder("exception occurred while consumer ").append(c)
                            .append(" handle message:").append(message).toString(), throwable);
                }
            }
        }
    }

    @Override
    public void setBeanName(String name) {
        setTopic(new String[] { name });
    }
}
