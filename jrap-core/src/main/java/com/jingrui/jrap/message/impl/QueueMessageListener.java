/*
 * #{copyright}#
 */

package com.jingrui.jrap.message.impl;

import com.jingrui.jrap.message.IQueueMessageListener;
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
public class QueueMessageListener<T> implements IQueueMessageListener<T>, BeanNameAware {
    private String queue;
    private RedisSerializer<T> redisSerializer;

    private IMessageConsumer<T>[] consumers = new IMessageConsumer[0];

    private Logger logger = LoggerFactory.getLogger(getClass());

    public void setQueue(String queue) {
        this.queue = queue;
    }

    @Override
    public String getQueue() {
        return queue;
    }

    public void setRedisSerializer(RedisSerializer<T> redisSerializer) {
        this.redisSerializer = redisSerializer;
    }

    @Override
    public RedisSerializer<T> getRedisSerializer() {
        return redisSerializer;
    }

    public IMessageConsumer<T>[] getConsumers() {
        return consumers;
    }

    public void setConsumers(IMessageConsumer<T>[] consumers) {
        this.consumers = consumers;
    }

    @Override
    public void onQueueMessage(T message, String queue) {
        if (logger.isDebugEnabled()) {
            logger.debug("receive message: {}, from: {}, consumers: {}", message, this.queue, consumers.length);
        }
        for (IMessageConsumer<T> c : consumers) {
            try {
                c.onMessage(message, queue);
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
        if (queue == null) {
            setQueue(name);
        }
    }
}
