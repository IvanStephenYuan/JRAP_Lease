/*
 * #{copyright}#
 */

package com.jingrui.jrap.core.impl;

import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.connection.RedisNode;

/**
 * auto create RedisNode list, using a config list.
 * <p>
 * invalid sentinel config will be ignore
 * 
 * @author shengyang.zhou@jingrui.com
 */
public class RedisNodeAutoConfig extends ArrayList<RedisNode> {

    public void setSentinels(String[] sentinels) {
        for (String s : sentinels) {
            if (StringUtils.isBlank(s) || s.contains("$")) {
                continue;
            }
            String[] ss = s.split(":");
            RedisNode redisNode = new RedisNode(ss[0].trim(), Integer.parseInt(ss[1].trim()));
            add(redisNode);
        }
    }
}
