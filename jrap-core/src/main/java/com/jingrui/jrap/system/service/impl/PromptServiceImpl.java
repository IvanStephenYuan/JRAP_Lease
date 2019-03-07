package com.jingrui.jrap.system.service.impl;

import com.jingrui.jrap.cache.CacheDelete;
import com.jingrui.jrap.cache.CacheSet;
import com.jingrui.jrap.cache.impl.HashStringRedisCacheGroup;
import com.jingrui.jrap.core.BaseConstants;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.core.annotation.StdWho;
import com.jingrui.jrap.core.i18n.CacheMessageSource;
import com.jingrui.jrap.message.IMessagePublisher;
import com.jingrui.jrap.message.components.DefaultPromptListener;
import com.jingrui.jrap.mybatis.common.Criteria;
import com.jingrui.jrap.system.dto.Prompt;
import com.jingrui.jrap.system.service.IPromptService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 描述维护.
 *
 * @author njq.niu@jingrui.com
 * @date 2016/6/9.
 */
@Service
public class PromptServiceImpl extends BaseServiceImpl<Prompt> implements IPromptService {

    @Autowired
    private IMessagePublisher messagePublisher;

    @Autowired
    @Qualifier("promptCache")
    private HashStringRedisCacheGroup<String> promptCache;

    @Override
    public List<Prompt> submit(IRequest request, List<Prompt> list) {
        self().batchUpdate(request, list);
        for (Prompt prompt : list) {
            notifyCache(prompt);
            notifyPromptCache(prompt);
        }
        return list;
    }

    @Override
    public String getPromptDescription(String locale, String promptCode) {
        String description = promptCache.getGroup(locale).getValue(promptCode);
        if (description == null) {
            Prompt prompt = new Prompt();
            prompt.setLang(locale);
            prompt.setPromptCode(promptCode);
            Criteria criteria = new Criteria(prompt);
            criteria.where(Prompt.FIELD_LANG, Prompt.FIELD_PROMPT_CODE);
            criteria.select(Prompt.FIELD_DESCRIPTION);
            List<Prompt> prompts = super.selectOptions(null,prompt,criteria);
            if(CollectionUtils.isNotEmpty(prompts)){
                description = prompts.get(0).getDescription();
                promptCache.getGroup(locale).setValue(promptCode,description);
            }
        }
        return description;
    }

    @Override
    @CacheSet(cache = BaseConstants.CACHE_PROMPT)
    public Prompt insertSelective(IRequest request, @StdWho Prompt prompt) {
        super.insertSelective(request, prompt);
        return prompt;
    }

    @Override
    @CacheDelete(cache = BaseConstants.CACHE_PROMPT)
    public int deleteByPrimaryKey(Prompt prompt) {
        return super.deleteByPrimaryKey(prompt);
    }

    @Override
    @CacheSet(cache = BaseConstants.CACHE_PROMPT)
    public Prompt updateByPrimaryKeySelective(IRequest request, @StdWho Prompt prompt) {
        return super.updateByPrimaryKeySelective(request, prompt);
    }

    @Override
    @CacheSet(cache = BaseConstants.CACHE_PROMPT)
    public Prompt updateByPrimaryKey(IRequest request, Prompt prompt) {
        return super.updateByPrimaryKey(request, prompt);
    }

    private void notifyCache(Prompt prompt) {
        messagePublisher.publish(DefaultPromptListener.CACHE_PROMPT, prompt.getPromptCode());
    }

    private void notifyPromptCache(Prompt prompt) {
        messagePublisher.publish(CacheMessageSource.CACHE_PROMPT_ALL, prompt);
    }
}
