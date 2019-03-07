/*
 * Copyright ZheJiang JingRui Co.,Ltd.
 */

package com.jingrui.jrap.message;

import com.jingrui.jrap.core.AppContextInitListener;
import com.jingrui.jrap.message.components.ChannelAndQueuePrefix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author shengyang.zhou@jingrui.com
 */
public class TopicListenerContainer extends RedisMessageListenerContainer implements AppContextInitListener {

    private Logger logger = LoggerFactory.getLogger(TopicListenerContainer.class);

    @Override
    public boolean isAutoStartup(){
        return false;
    }

    @Override
    public void contextInitialized(ApplicationContext applicationContext) {
        Map<String, Object> monitors = applicationContext.getBeansWithAnnotation(TopicMonitor.class);
        Map<MessageListener, Collection<? extends Topic>> listeners = new HashMap<>();
        monitors.forEach((k, v) -> {
            Class<?> clazz = v.getClass();
            TopicMonitor tm = clazz.getAnnotation(TopicMonitor.class);
            String mn = MethodReflectUtils.getTopicMethodName(tm.method(), v);
            List<Method> avaMethods = MethodReflectUtils.findMethod(clazz, new MethodReflectUtils.FindDesc(mn, 2));

            if (avaMethods.isEmpty()) {
                if (logger.isErrorEnabled()) {
                    logger.error("can not find proper method of name '{}' for bean {}", mn, v);
                }
                return;
            }

            SimpleMessageListener adaptor = new SimpleMessageListener(v, avaMethods.get(0));
            List<Topic> topics = new ArrayList<>();
            for (String t : tm.channel()) {
                //添加前缀
                t = ChannelAndQueuePrefix.addPrefix(t);
                Topic topic = new PatternTopic(t);
                topics.add(topic);
            }

            listeners.put(adaptor, topics);
        });
        setMessageListeners(listeners);
        // start(); // auto call
//        if (listeners != null) {
//            for (ITopicMessageListener receiver : listeners) {
//                MessageListenerAdapter messageListener = new MessageListenerAdapter(receiver, "onTopicMessage");
//                if (receiver.getRedisSerializer() != null) {
//                    messageListener.setSerializer(receiver.getRedisSerializer());
//                }
//                messageListener.afterPropertiesSet();
//                List<Topic> topics = new ArrayList<>();
//                for (String t : receiver.getTopic()) {
//                    Topic topic = new PatternTopic(t);
//                    topics.add(topic);
//                }
//                listeners.put(messageListener, topics);
//            }
//        }
    }

    private static class SimpleMessageListener implements MessageListener {
        private RedisSerializer redisSerializer;

        private Object target;
        private Method method;

        private Logger logger;

        SimpleMessageListener(Object target, Method method) {
            this.target = target;
            this.method = method;
            Class p0 = method.getParameterTypes()[0];
            redisSerializer = MethodReflectUtils.getProperRedisSerializer(p0);
            logger = LoggerFactory.getLogger(target.getClass());
        }

        @Override
        public void onMessage(Message message, byte[] pattern) {
            try {
                Object obj = redisSerializer.deserialize(message.getBody());
                String p = new String(pattern, "UTF-8");
                //去掉前缀
                p = ChannelAndQueuePrefix.removePrefix(p);
                method.invoke(target, obj, p);
            } catch (Exception e) {
                Throwable thr = e;
                while (thr.getCause() != null) {
                    thr = thr.getCause();
                }
                if (logger.isErrorEnabled()) {
                    logger.error(thr.getMessage(), thr);
                }
            }
        }
    }
}
