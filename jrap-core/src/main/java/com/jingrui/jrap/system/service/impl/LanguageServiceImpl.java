/*
 * #{copyright}#
 */
package com.jingrui.jrap.system.service.impl;

import com.jingrui.jrap.core.annotation.StdWho;
import com.jingrui.jrap.core.impl.LanguageProviderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.message.IMessagePublisher;
import com.jingrui.jrap.system.dto.Language;
import com.jingrui.jrap.system.service.ILanguageService;

import java.util.List;

/**
 * @author shengyang.zhou@jingrui.com
 */
@Service
public class LanguageServiceImpl extends BaseServiceImpl<Language> implements ILanguageService {

    @Autowired
    private IMessagePublisher messagePublisher;

    @Override
    public List<Language> submit(IRequest request, @StdWho List<Language> list){
        self().batchUpdate(request, list);
        notifyCache();
        return  list;
    }

    @Override
    public int remove(List<Language> list) {
        int result = self().batchDelete(list);
        notifyCache();
        return result;
    }

    /**
     * 更新缓存数据
     */
    private void notifyCache() {
        messagePublisher.publish(LanguageProviderImpl.CACHE_LANGUAGE, "language");
    }
}
