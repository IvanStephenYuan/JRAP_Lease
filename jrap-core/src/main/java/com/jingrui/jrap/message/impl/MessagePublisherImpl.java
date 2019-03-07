/*
 * #{copyright}#
 */

package com.jingrui.jrap.message.impl;

import com.jingrui.jrap.message.components.ChannelAndQueuePrefix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jingrui.jrap.message.IMessagePublisher;

/**
 * @author shengyang.zhou@jingrui.com
 * @author njq.niu@jingrui.com
 */
@Component
public class MessagePublisherImpl implements IMessagePublisher {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private Logger logger = LoggerFactory.getLogger(MessagePublisherImpl.class);

    @Override
    public void publish(String channel, Object message) {
        //添加前缀
        channel = ChannelAndQueuePrefix.addPrefix(channel);
        if (message instanceof String || message instanceof Number) {
            redisTemplate.convertAndSend(channel, message.toString());
        } else {
            try {
                redisTemplate.convertAndSend(channel, objectMapper.writeValueAsString(message));
            } catch (JsonProcessingException e) {
                if (logger.isErrorEnabled()) {
                    logger.error("publish message failed.", e);
                }
            }
        }
    }

    @Override
    public void rPush(String list, Object message) {
        message(list, message);
    }

    @Override
    public void message(String name, Object message) {
        if (message instanceof String || message instanceof Number) {
            redisTemplate.opsForList().rightPush(name, message.toString());
        } else {
            try {
                redisTemplate.opsForList().rightPush(name, objectMapper.writeValueAsString(message));
            } catch (JsonProcessingException e) {
                if (logger.isErrorEnabled()) {
                    logger.error("push data failed.", e);
                }
            }
        }
    }


}
