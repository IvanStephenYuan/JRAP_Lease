package com.jingrui.jrap.function.dto;

import com.jingrui.jrap.core.annotation.MultiLanguage;
import com.jingrui.jrap.core.annotation.MultiLanguageField;
import com.jingrui.jrap.mybatis.common.query.Comparison;
import com.jingrui.jrap.mybatis.common.query.Where;
import com.jingrui.jrap.system.dto.BaseDTO;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OrderBy;
import javax.persistence.Table;

/**
 * 资源DTO.
 *
 * @author wuyichu
 * @author njq.niu@jingrui.com
 */
@MultiLanguage
@Table(name = "sys_resource_b")
public class Resource extends BaseDTO {

    public static final String FIELD_RESOURCE_ID = "resourceId";
    public static final String FIELD_ACCESS_CHECK = "accessCheck";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_LOGIN_REQUIRE = "loginRequire";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_TYPE = "type";
    public static final String FIELD_URL = "url";

    @Id
    @GeneratedValue(generator = GENERATOR_TYPE)
    private Long resourceId;

    @NotEmpty
    @Length(max = 1)
    private String accessCheck;

    @MultiLanguageField
    @Length(max = 240)
    private String description;

    @NotEmpty
    @Length(max = 1)
    private String loginRequire;

    @MultiLanguageField
    @NotEmpty
    @Where(comparison = Comparison.LIKE)
    @Length(max = 40)
    private String name;

    @NotEmpty
    @Where
    @Length(max = 15)
    private String type;

    @NotEmpty
    @Where(comparison = Comparison.LIKE)
    @OrderBy("ASC")
    @Length(max = 255)
    private String url;

    public String getAccessCheck() {
        return accessCheck;
    }

    public String getDescription() {
        return description;
    }

    public String getLoginRequire() {
        return loginRequire;
    }

    public String getName() {
        return name;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public String getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    public void setAccessCheck(String accessCheck) {
        this.accessCheck = accessCheck;
    }

    public void setDescription(String description) {
        this.description = StringUtils.trim(description);
    }

    public void setLoginRequire(String loginRequire) {
        this.loginRequire = StringUtils.trim(loginRequire);
    }

    public void setName(String name) {
        this.name = StringUtils.trim(name);
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public void setType(String type) {
        this.type = StringUtils.trim(type);
    }

    public void setUrl(String url) {
        this.url = StringUtils.trim(url);
    }


}