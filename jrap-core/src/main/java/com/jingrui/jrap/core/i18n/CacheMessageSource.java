/*
 * #{copyright}#
 */
package com.jingrui.jrap.core.i18n;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.jingrui.jrap.message.IMessageConsumer;
import com.jingrui.jrap.message.TopicMonitor;
import com.jingrui.jrap.system.dto.Prompt;
import com.jingrui.jrap.system.service.IPromptService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.text.StrBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * CacheMessageSource.
 *
 * @author shengyang.zhou@jingrui.com
 */
@Component
@TopicMonitor(channel = CacheMessageSource.CACHE_PROMPT_ALL)
public class CacheMessageSource extends AbstractMessageSource implements IMessageConsumer<Prompt> {
    private static final String SINGLE_QUOTES_REPLACEMENT = "&#39;";
    private static final String DOUBLE_QUOTES_REPLACEMENT = "&#34;";

    public static final String CACHE_PROMPT_ALL = "cache.prompt.all";

    private Map<String, Map<String, String>> promptsMap = new ConcurrentHashMap<>();

    @Autowired
    private IPromptService promptService;

    public CacheMessageSource() {
        reload();
    }

    public void reload() {
    }


    @Override
    public void onMessage(Prompt message, String pattern) {
        Map<String, String> prompts = promptsMap.get(message.getLang());
        if (!CollectionUtils.isEmpty(prompts)) {
            prompts.remove(message.getPromptCode());
            String description = promptService.getPromptDescription(message.getLang(), message.getPromptCode());
            if (StringUtils.isNotEmpty(description)) {
                prompts.put(message.getPromptCode(), description);
            }
        }
    }

    @Override
    protected MessageFormat resolveCode(String code, Locale locale) {
        return createMessageFormat(resolveCodeWithoutArguments(code, locale), locale);
    }


    @Override
    protected String resolveCodeWithoutArguments(String code, Locale locale) {
        String code2 = StringUtils.lowerCase(code);
        String description = resolveCodeFromMap(code2, locale.toString());
        if (description == null) {
            return code;
        }
        return replaceQuote(description);
    }

    private String resolveCodeFromMap(String code, String locale) {
        Map<String, String> prompts = promptsMap.get(locale);
        if (CollectionUtils.isEmpty(prompts)) {
            //应该设置一个相对合适的size
            prompts = new ConcurrentHashMap<>(5000);
        }
        String description = prompts.get(code);
        if (description == null) {
            description = promptService.getPromptDescription(locale, code);
            if (description != null) {
                prompts.put(code, description);
                promptsMap.put(locale, prompts);
            }
        }
        return description;
    }

    private String replaceQuote(String str) {
        int idx = -1;
        char c = 0;
        for (int i = 0; i < str.length(); i++) {
            c = str.charAt(i);
            if (c == '\'' || c == '"') {
                idx = i;
                break;
            }
        }
        if (idx == -1) {
            return str;
        }
        StrBuilder sb = new StrBuilder(str.length() + 32);
        sb.append(str.substring(0, idx));
        if (c == '"') {
            sb.append(DOUBLE_QUOTES_REPLACEMENT);
        } else {
            sb.append(SINGLE_QUOTES_REPLACEMENT);
        }
        for (int i = idx + 1; i < str.length(); i++) {
            c = str.charAt(i);
            if (c == '"') {
                sb.append(DOUBLE_QUOTES_REPLACEMENT);
            } else if (c == '\'') {
                sb.append(SINGLE_QUOTES_REPLACEMENT);
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}