/*
 * #{copyright}#
 */
package com.jingrui.jrap.core.components;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

import com.jingrui.jrap.cache.CacheManager;
import com.jingrui.jrap.cache.impl.HashStringRedisCache;
import com.jingrui.jrap.core.ILanguageProvider;
import com.jingrui.jrap.message.IMessageConsumer;
import com.jingrui.jrap.system.dto.Language;

/**
 * @author shengyang.zhou@jingrui.com
 */
//////已经停用该类!
//@Component
//@TopicMonitor(channel = { "cache.language", "topic:cache:reloaded" })
public class CacheBasedLanguageProvider implements ILanguageProvider, IMessageConsumer<String>, BeanFactoryAware {

    private BeanFactory beanFactory;

    private HashStringRedisCache<Language> languageCache;

    private String cacheName = "language";

    private boolean enableSecondaryCache = false;

    private List<Language> tempList;
    private Logger logger = LoggerFactory.getLogger(CacheBasedLanguageProvider.class);

    public String getCacheName() {
        return cacheName;
    }

    public void setCacheName(String cacheName) {
        this.cacheName = cacheName;
    }

    public boolean isEnableSecondaryCache() {
        return enableSecondaryCache;
    }

    public void setEnableSecondaryCache(boolean enableSecondaryCache) {
        this.enableSecondaryCache = enableSecondaryCache;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Language> getSupportedLanguages() {
        if (tempList == null) {
            tempList = getFromCache();
        }
        if (tempList == null) {
            return Collections.EMPTY_LIST;
        }
        return tempList;
    }

    @SuppressWarnings("unchecked")
    private List<Language> getFromCache() {
        if (languageCache == null) {
            CacheManager cacheManager = beanFactory.getBean(CacheManager.class);
            languageCache = (HashStringRedisCache) cacheManager.getCache(getCacheName());
        }
        if (logger.isDebugEnabled()) {
            logger.debug("load languages from cache.");
        }
        return languageCache.getAll();
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void onMessage(String message, String pattern) {
        if ("cache.language".equals(pattern)) {
            if (logger.isDebugEnabled()) {
                logger.debug("language cache changed, now reload secondary cache.", message);
            }
            tempList = getFromCache();
        } else if ("topic:cache:reloaded".equals(pattern) && cacheName.equals(message)) {
            if (logger.isDebugEnabled()) {
                logger.debug("language cache reloaded, now reload secondary cache.");
            }
            tempList = getFromCache();
        }
    }

}
