package com.jingrui.jrap.message.components;

import com.jingrui.jrap.message.IMessageConsumer;
import com.jingrui.jrap.message.TopicMonitor;
import com.jingrui.jrap.system.dto.Prompt;
import com.jingrui.jrap.system.mapper.PromptMapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author shengyang.zhou@jingrui.com
 */
@Component
@TopicMonitor(channel = DefaultPromptListener.CACHE_PROMPT)
public class DefaultPromptListener implements IMessageConsumer<String>, InitializingBean {

    public static final String CACHE_PROMPT = "cache.prompt";

    public static final String SYS_PROMPT = "jrap.";

    @Autowired
    private PromptMapper promptMapper;

    private Map<String, List<Prompt>> promptsMap = new HashMap<>();

    @Override
    public void onMessage(String message, String pattern) {
        if (message.toLowerCase().startsWith(SYS_PROMPT)) {
            reload();
        }
    }

    private void reload() {
        Prompt p = new Prompt();
        p.setPromptCode(SYS_PROMPT);
        List<Prompt> prompts = promptMapper.select(p);
        Map<String, List<Prompt>> promptsMapNew = new HashMap<>(16);
        for (Prompt prompt : prompts) {
            List<Prompt> list = promptsMapNew.get(prompt.getLang());
            if (list == null) {
                list = new ArrayList<>();
                promptsMapNew.put(prompt.getLang(), list);
            }
            list.add(prompt);
        }

        promptsMap = promptsMapNew;
    }

    public List<Prompt> getDefaultPrompt(String lang) {
        List<Prompt> prompts = promptsMap.get(lang);
        if (prompts == null || prompts.isEmpty()){
            reload();
            prompts = promptsMap.get(lang);
        }
        return prompts;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        reload();
    }
}
