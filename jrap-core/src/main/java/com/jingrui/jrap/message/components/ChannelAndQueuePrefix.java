package com.jingrui.jrap.message.components;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Properties;

/**
 * @author xiangyu.qi@jingrui.com on 2017/9/25.
 */
@Component
public class ChannelAndQueuePrefix {



    private static String CHANNEL_PREFIX;

    public static String addPrefix(String str){
        if(StringUtils.isNotEmpty(CHANNEL_PREFIX)){
            return CHANNEL_PREFIX +"."+ str;
        }
        return str;
    }

    public static String removePrefix(String str){
        if(StringUtils.isNotEmpty(CHANNEL_PREFIX)){
            return str.replaceFirst(CHANNEL_PREFIX+".","");
        }
        return str;
    }

    @Value("${redis.topic.channel.prefix:}")
    public void setChannelPrefix(String prefix){
        CHANNEL_PREFIX = prefix;
    }

}
