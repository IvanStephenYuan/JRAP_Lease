package com.jingrui.jrap.system.dto;

import com.jingrui.jrap.core.annotation.MultiLanguage;
import com.jingrui.jrap.core.annotation.Children;
import com.jingrui.jrap.core.annotation.MultiLanguageField;
import com.jingrui.jrap.mybatis.annotation.Condition;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.List;

/**
 * 快速编码DTO.
 *
 * @author runbai.chen
 * @date 2016/6/9.
 */
@MultiLanguage
@Table(name = "sys_code_b")
public class Code extends BaseDTO {

    private static final long serialVersionUID = 2776430709705510697L;

    public static final String FIELD_CODE = "code";
    public static final String FIELD_CODE_ID = "codeId";
    public static final String FIELD_CODE_VALUES = "codeValues";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_TYPE = "type";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";
    public static final String FIELD_PARENT_CODE_ID = "parentCodeId";
    public static final String FIELD_PARENT_CODE_DESCRIPTION = "parentCodeDescription";

    @Column
    @NotEmpty
    @OrderBy("ASC")
    @Length(max = 30)
    @Condition(operator = LIKE)
    private String code;

    @Id
    @Column
    @GeneratedValue(generator = GENERATOR_TYPE)
    private Long codeId;

    @Children
    @Transient
    private List<CodeValue> codeValues;

    @Column
    @MultiLanguageField
    @NotEmpty
    @Length(max = 250)
    @Condition(operator = LIKE)
    private String description;

    @Length(max = 10)
    private String type;

    @Length(max = 1)
    private String enabledFlag;

    @Column
    private Long parentCodeId;

    @Transient
    private String parentCodeDescription;

    public String getParentCodeDescription() {
        return parentCodeDescription;
    }

    public void setParentCodeDescription(String parentCodeDescription) {
        this.parentCodeDescription = parentCodeDescription;
    }

    public Long getParentCodeId() {
        return parentCodeId;
    }

    public void setParentCodeId(Long parentCodeId) {
        this.parentCodeId = parentCodeId;
    }

    public String getCode() {
        return code;
    }

    public Long getCodeId() {
        return codeId;
    }

    public List<CodeValue> getCodeValues() {
        return codeValues;
    }

    public String getDescription() {
        return description;
    }

    public void setCode(String code) {
        this.code = StringUtils.trim(code);
    }

    public void setCodeId(Long codeId) {
        this.codeId = codeId;
    }

    public void setCodeValues(List<CodeValue> codeValues) {
        this.codeValues = codeValues;
    }

    public void setDescription(String description) {
        this.description = StringUtils.trim(description);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEnabledFlag() {
        return enabledFlag;
    }

    public void setEnabledFlag(String enabledFlag) {
        this.enabledFlag = enabledFlag;
    }
}