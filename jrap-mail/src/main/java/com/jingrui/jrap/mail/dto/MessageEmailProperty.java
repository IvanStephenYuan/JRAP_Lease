package com.jingrui.jrap.mail.dto;

import com.jingrui.jrap.mybatis.annotation.ExtensionAttribute;
import com.jingrui.jrap.system.dto.BaseDTO;
import org.hibernate.validator.constraints.Length;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 邮件服务器属性.
 *
 * @author qiang.zeng@jingrui.com
 */
@ExtensionAttribute(disable = true)
@Table(name = "sys_message_email_property")
public class MessageEmailProperty extends BaseDTO {

    public static final String FIELD_PROPERTY_ID = "propertyId";
    public static final String FIELD_PROPERTY_NAME = "propertyName";
    public static final String FIELD_PROPERTY_CODE = "propertyCode";
    public static final String FIELD_CONFIG_ID = "configId";


    @Id
    @GeneratedValue
    private Long propertyId;

    @Length(max = 50)
    private String propertyName;

    @Length(max = 50)
    private String propertyCode;

    private Long configId;


    public void setPropertyId(Long propertyId) {
        this.propertyId = propertyId;
    }

    public Long getPropertyId() {
        return propertyId;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyCode(String propertyCode) {
        this.propertyCode = propertyCode;
    }

    public String getPropertyCode() {
        return propertyCode;
    }

    public void setConfigId(Long configId) {
        this.configId = configId;
    }

    public Long getConfigId() {
        return configId;
    }

}
