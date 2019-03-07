package com.jingrui.jrap.system.dto;

import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.jingrui.jrap.mybatis.annotation.Condition;
import com.jingrui.jrap.core.annotation.MultiLanguageField;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.jingrui.jrap.core.annotation.Children;

/**
 * 配置文件对象.
 * 
 * @author frank.li
 * @date 2016/6/9.
 */
@Table(name = "sys_profile")
public class Profile extends BaseDTO {

    private static final long serialVersionUID = -3284239490993804271L;

    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_PROFILE_ID = "profileId";
    public static final String FIELD_PROFILE_NAME = "profileName";
    public static final String FIELD_PROFILE_VALUES = "profileValues";

    /**
     * 配置文件描述.
     */
    @MultiLanguageField
    @NotEmpty
    @Length(max = 240)
    @Condition(operator = LIKE)
    private String description;

    /**
     * 表ID，主键，供其他表做外键.
     */
    @Id
    @GeneratedValue(generator = GENERATOR_TYPE)
    private Long profileId;

    /**
     * 配置文件.
     */
    @Condition(operator = LIKE)
    @NotEmpty
    @Length(max = 40)
    private String profileName;

    @Children
    @Transient
    private List<ProfileValue> profileValues;

    public String getDescription() {
        return description;
    }

    public Long getProfileId() {
        return profileId;
    }

    public String getProfileName() {
        return profileName;
    }

    public List<ProfileValue> getProfileValues() {
        return profileValues;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public void setProfileValues(List<ProfileValue> profileValues) {
        this.profileValues = profileValues;
    }
}