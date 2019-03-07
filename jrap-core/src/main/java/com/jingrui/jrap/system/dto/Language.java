/*
 * #{copyright}#
 */
package com.jingrui.jrap.system.dto;

import com.jingrui.jrap.mybatis.annotation.ExtensionAttribute;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.jingrui.jrap.mybatis.annotation.Condition;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 语言DTO.
 * 
 * @author shengyang.zhou@jingrui.com
 */
@Table(name = "sys_lang_b")
@ExtensionAttribute(disable=true)
public class Language extends BaseDTO {

    private static final long serialVersionUID = -2978619661646886631L;

    public static final String FIELD_LANG_CODE = "langCode";
    public static final String FIELD_BASE_LANG = "baseLang";
    public static final String FIELD_DESCRIPTION = "description";

    @Id
    @NotEmpty
    @Condition(operator = LIKE)
    private String langCode;

    @Condition(exclude = true)
    @Length(max = 10)
    private String baseLang = "zh_CN";

    @Condition(operator = LIKE)
    @Length(max = 240)
    private String description;

    public String getBaseLang() {
        return baseLang;
    }

    public String getDescription() {
        return description;
    }

    public String getLangCode() {
        return langCode;
    }

    public void setBaseLang(String baseLang) {
        this.baseLang = StringUtils.trim(baseLang);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLangCode(String langCode) {
        this.langCode = StringUtils.trim(langCode);
    }

}