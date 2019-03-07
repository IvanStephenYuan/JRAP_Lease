package com.jingrui.jrap.activiti.components;

import java.util.List;

import com.jingrui.jrap.activiti.core.IActivitiConstants;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.mybatis.common.Criteria;
import com.jingrui.jrap.mybatis.common.query.WhereField;
import com.jingrui.jrap.system.dto.Prompt;
import com.jingrui.jrap.system.mapper.PromptMapper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.LocaleUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

/**
 * 工作流多语言管理.
 *
 * @author qiang.zeng
 * @date 2018/9/12.
 */
@Component
public class ActivitiMultiLanguageManager implements InitializingBean {

    @Autowired
    private ApplicationContext applicationContext;

    private static MessageSource messageSource;

    private static PromptMapper promptMapper;

    /**
     * 工作流是否开启多语言.
     */
    public static boolean multiLanguageOpen;

    /**
     * 工作流多语言的描述前缀.
     */
    public static String multiLanguagePrefix;

    @Value("${activiti.multiLanguage.open:false}")
    public void setMultiLanguageOpen(boolean multiLanguageOpen) {
        ActivitiMultiLanguageManager.multiLanguageOpen = multiLanguageOpen;
    }

    @Value("${activiti.multiLanguage.prefix:act.}")
    public void setMultiLanguagePrefix(String multiLanguagePrefix) {
        ActivitiMultiLanguageManager.multiLanguagePrefix = multiLanguagePrefix;
    }


    /**
     * 如果工作流开启了多语言,根据编码和指定前缀获取多语言描述;否则返回原编码（供工作流查询返回数据 多语言字段替换使用）.
     *
     * @param code     编码
     * @param iRequest IRequest
     * @return 多语言描述或原编码
     */
    public static String getMultLanguageInfoByCode(String code, IRequest iRequest) {
        if (multiLanguageOpen) {
            boolean isQuery = StringUtils.isNotEmpty(code) && (code.startsWith(multiLanguagePrefix)
                    || IActivitiConstants.MULTI_INFO_START.equals(code) || IActivitiConstants.MULTI_INFO_END.equals(code));
            if (isQuery) {
                code = messageSource.getMessage(code, null, LocaleUtils.toLocale(iRequest.getLocale()));
            }
        }
        return code;
    }

    /**
     * 根据描述和编码指定前缀获取多语言编码;否则返回原描述（供工作流查询条件 多语言字段替换使用）.
     *
     * @param description 描述
     * @param iRequest    IRequest
     * @return 多语言编码或原描述
     */
    public static String getMultiLanguageInfoByDescription(String description, IRequest iRequest) {
        if (StringUtils.isNotEmpty(description)) {
            Prompt prompt = new Prompt();
            prompt.setLang(iRequest.getLocale());
            prompt.setDescription(description);
            Criteria criteria = new Criteria(prompt);
            criteria.select(Prompt.FIELD_PROMPT_CODE);
            criteria.where(new WhereField(Prompt.FIELD_DESCRIPTION));
            List<Prompt> prompts = promptMapper.selectOptions(prompt, criteria);
            if (CollectionUtils.isNotEmpty(prompts)) {
                for (Prompt prompt1 : prompts) {
                    String code = prompt1.getPromptCode();
                    if (StringUtils.isNotEmpty(code) && code.startsWith(multiLanguagePrefix)) {
                        description = code;
                        break;
                    }
                }

            }
        }
        return description;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        messageSource = applicationContext.getBean(MessageSource.class);
        promptMapper = applicationContext.getBean(PromptMapper.class);
    }
}
