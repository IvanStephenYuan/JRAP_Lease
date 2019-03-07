/*
 * #{copyright}#
 */

package com.jingrui.jrap.core;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import redis.clients.jedis.JedisPoolConfig;

/**
 * @author shengyang.zhou@jingrui.com
 */
public class JedisConnectionFactoryBean implements FactoryBean<JedisConnectionFactory> {

    private boolean useSentinel = false;

    private boolean useCluster = false;

    private RedisClusterConfiguration clusterConfiguration;

    private RedisSentinelConfiguration sentinelConfiguration;

    private String hostName;

    private int port;

    private int database = 0;

    private String password;

    private JedisPoolConfig poolConfig;

    private volatile CustomJedisConnectionFactory cacheObject;

    @Override
    public JedisConnectionFactory getObject() throws Exception {
        if (cacheObject == null) {
            synchronized (this) {
                if (cacheObject == null) {
                    doCreate();
                }
            }
        }
        return cacheObject;
    }

    private void doCreate() {
        if(useCluster){
            cacheObject = new CustomJedisConnectionFactory((clusterConfiguration));
        }else if (useSentinel) {
            cacheObject = new CustomJedisConnectionFactory(sentinelConfiguration);
        } else {
            cacheObject = new CustomJedisConnectionFactory();
            cacheObject.setHostName(hostName);
            cacheObject.setPort(port);
        }
        if(StringUtils.isNotEmpty(password)) {
            cacheObject.setPassword(password);
        }
        cacheObject.setPoolDb(database);
        cacheObject.setUsePool(true);
        cacheObject.setPoolConfig(poolConfig);
        cacheObject.afterPropertiesSet();
    }

    @Override
    public Class<?> getObjectType() {
        return JedisConnectionFactory.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public boolean isUseSentinel() {
        return useSentinel;
    }

    public void setUseSentinel(boolean useSentinel) {
        this.useSentinel = useSentinel;
    }

    public RedisSentinelConfiguration getSentinelConfiguration() {
        return sentinelConfiguration;
    }

    public void setSentinelConfiguration(RedisSentinelConfiguration sentinelConfiguration) {
        this.sentinelConfiguration = sentinelConfiguration;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getDatabase() {
        return database;
    }

    public void setDatabase(int database) {
        this.database = database;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public JedisPoolConfig getPoolConfig() {
        return poolConfig;
    }

    public void setPoolConfig(JedisPoolConfig poolConfig) {
        this.poolConfig = poolConfig;
    }

    public boolean isUseCluster() {
        return useCluster;
    }

    public void setUseCluster(boolean useCluster) {
        this.useCluster = useCluster;
    }

    public RedisClusterConfiguration getClusterConfiguration() {
        return clusterConfiguration;
    }

    public void setClusterConfiguration(RedisClusterConfiguration clusterConfiguration) {
        this.clusterConfiguration = clusterConfiguration;
    }
}
