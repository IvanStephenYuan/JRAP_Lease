package com.jingrui.jrap.system.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.jingrui.jrap.mybatis.common.query.Where;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.jingrui.jrap.mybatis.annotation.Condition;

/**
 * 描述维护.
 *
 * @author wuyichu
 * @date 2016/6/9.
 */
@Table(name = "sys_prompts")
public class Prompt extends BaseDTO {

    private static final long serialVersionUID = 2856108923186548186L;

    public static final String FIELD_PROMPT_ID = "promptId";
    public static final String FIELD_PROMPT_CODE = "promptCode";
    public static final String FIELD_LANG = "lang";
    public static final String FIELD_DESCRIPTION = "description";

    /**
     * 表ID，主键，供其他表做外键.
     */
    @Id
    @GeneratedValue
    private Long promptId;

    /**
     * 文本编码.
     */

    @NotEmpty
    @Condition(operator = LIKE)
    @Length(max = 255)
    @OrderBy("ASC")
    @Where
    private String promptCode;

    @NotEmpty
    @Length(max = 10)
    @Where
    private String lang;

    @NotEmpty
    @Condition(operator = LIKE)
    @Length(max = 240)
    @Where
    private String description;

    public Long getPromptId() {
        return promptId;
    }

    public void setPromptId(Long promptId) {
        this.promptId = promptId;
    }

    public String getPromptCode() {
        return promptCode;
    }

    public void setPromptCode(String promptCode) {
        this.promptCode = StringUtils.trim(promptCode);
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = StringUtils.trim(lang);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}