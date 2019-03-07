package com.jingrui.jrap.function.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jingrui.jrap.core.BaseConstants;
import com.jingrui.jrap.core.annotation.Children;
import com.jingrui.jrap.core.annotation.MultiLanguage;
import com.jingrui.jrap.core.annotation.MultiLanguageField;
import com.jingrui.jrap.mybatis.annotation.Condition;
import com.jingrui.jrap.mybatis.common.query.*;
import com.jingrui.jrap.mybatis.common.query.JoinColumn;
import com.jingrui.jrap.mybatis.common.query.JoinTable;
import com.jingrui.jrap.system.dto.BaseDTO;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.persistence.criteria.JoinType;
import java.util.List;

/**
 * 功能DTO.
 *
 * @author njq.niu@jingrui.com
 */
@MultiLanguage
@Table(name = "sys_function_b")
public class Function extends BaseDTO {

    public static final String FIELD_FUNCTION_ID = "functionId";
    public static final String FIELD_FUNCTION_CODE = "functionCode";
    public static final String FIELD_FUNCTION_DESCRIPTION = "functionDescription";
    public static final String FIELD_FUNCTION_ICON = "functionIcon";
    public static final String FIELD_FUNCTION_SEQUENCE = "functionSequence";
    public static final String FIELD_FUNCTION_NAME = "functionName";
    public static final String FIELD_LANG = "lang";
    public static final String FIELD_MODULE_CODE = "moduleCode";
    public static final String FIELD_PARENT_FUNCTION_ID = "parentFunctionId";
    public static final String FIELD_PARENT_FUNCTION_NAME = "parentFunctionName";
    public static final String FIELD_RESOURCE_NAME = "resourceName";
    public static final String FIELD_RESOURCE_ID = "resourceId";
    public static final String FIELD_RESOURCES = "resources";
    public static final String FIELD_TYPE = "type";

    @Id
    @Column
    @Where
    @GeneratedValue(generator = GENERATOR_TYPE)
    private Long functionId;

    @Column
    @OrderBy("ASC")
    @Where
    @NotEmpty
    @Length(max = 30)
    private String functionCode;

    @Column
    @MultiLanguageField
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Length(max = 240)
    private String functionDescription;

    @Column
    @Condition(exclude = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Length(max = 100)
    private String functionIcon;

    @Column
    @Condition(exclude = true)
    @OrderBy
    private Long functionSequence;

    @Column
    @MultiLanguageField
    @Where(comparison = Comparison.LIKE)
    @Length(max = 150)
    private String functionName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Transient
    @Length(max = 10)
    private String lang;

    @Column
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Where
    @Length(max = 30)
    @OrderBy
    private String moduleCode;

    @Column
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Where
    private Long parentFunctionId;

    @Transient
    @JoinCache(joinKey = FIELD_PARENT_FUNCTION_ID, joinColumn = FIELD_FUNCTION_NAME, cacheName = BaseConstants.CACHE_FUNCTION)
    @Length(max = 150)
    private String parentFunctionName;

    @Transient
    @JoinColumn(joinName = "resourceJoin", field = Resource.FIELD_NAME)
    @Length(max = 40)
    private String resourceName;

    @Transient
    private String resourceUrl;

    @Column
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JoinTable(name = "resourceJoin", joinMultiLanguageTable = true, target = Resource.class, type = JoinType.LEFT, on = {@JoinOn(joinField = Resource.FIELD_RESOURCE_ID), @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    @Where
    private Long resourceId;

    @Children
    @Transient
    private List<Resource> resources;

    @Column
    private String type = "PAGE";

    public String getFunctionCode() {
        return functionCode;
    }

    public String getFunctionDescription() {
        return functionDescription;
    }

    public String getFunctionIcon() {
        return functionIcon;
    }

    public Long getFunctionId() {
        return functionId;
    }

    public String getFunctionName() {
        return functionName;
    }

    public String getLang() {
        return lang;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public Long getParentFunctionId() {
        return parentFunctionId;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public List<Resource> getResources() {
        return resources;
    }

    public String getType() {
        return type;
    }

    public void setFunctionCode(String functionCode) {
        this.functionCode = StringUtils.trim(functionCode);
    }

    public void setFunctionDescription(String functionDescription) {
        this.functionDescription = functionDescription;
    }

    public void setFunctionIcon(String functionIcon) {
        this.functionIcon = functionIcon;
    }

    public void setFunctionId(Long functionId) {
        this.functionId = functionId;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public void setLang(String sourceLang) {
        this.lang = sourceLang;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = StringUtils.trim(moduleCode);
    }

    public void setParentFunctionId(Long parentFunctionId) {
        this.parentFunctionId = parentFunctionId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getFunctionSequence() {
        return functionSequence;
    }

    public void setFunctionSequence(Long functionSequence) {
        this.functionSequence = functionSequence;
    }

    public String getParentFunctionName() {
        return parentFunctionName;
    }

    public void setParentFunctionName(String parentFunctionName) {
        this.parentFunctionName = parentFunctionName;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }
}