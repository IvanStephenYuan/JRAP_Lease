package com.jingrui.jrap.system.dto;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.jingrui.jrap.core.annotation.MultiLanguage;
import com.jingrui.jrap.core.annotation.MultiLanguageField;
import com.jingrui.jrap.mybatis.annotation.ExtensionAttribute;
import com.jingrui.jrap.system.dto.BaseDTO;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * DashBoard配置的DTO.
 *
 * @author zhizheng.yang@jingrui.com
 */

@MultiLanguage
@Table(name = "sys_dashboard_b")
@ExtensionAttribute(disable = true)
public class DashBoard extends BaseDTO {

    private static final long serialVersionUID = 1L;

    /**
     * 表ID，主键，供其他表做外键.
     */
    @Id
    @Column
    @GeneratedValue(generator = GENERATOR_TYPE)
    private Long dashboardId;

    /**
     * 配置编码.
     */
    @NotEmpty
    @Column
    @Length(max = 100)
    private String dashboardCode;

    /**
     * 描述.
     */
    @Column
    @MultiLanguageField
    @Length(max = 255)
    private String description;

    /**
     * 标题.
     */
    @Column
    @MultiLanguageField
    @Length(max = 100)
    private String title;

    /**
     * 资源ID.
     */
    @Column
    private Long resourceId;

    @Transient
    private String resourceName;
    
    @Transient
    private String url;

    private String enabledFlag;

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public Long getDashboardId() {
        return dashboardId;
    }

    public void setDashboardId(Long dashboardId) {
        this.dashboardId = dashboardId;
    }

    public String getDashboardCode() {
        return dashboardCode;
    }

    public void setDashboardCode(String dashboardCode) {
        this.dashboardCode = StringUtils.trim(dashboardCode);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getEnabledFlag() {
        return enabledFlag;
    }

    public void setEnabledFlag(String enabledFlag) {
        this.enabledFlag = enabledFlag;
    }
}
