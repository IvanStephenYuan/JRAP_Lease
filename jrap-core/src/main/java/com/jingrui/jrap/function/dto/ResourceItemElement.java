package com.jingrui.jrap.function.dto;

import com.jingrui.jrap.mybatis.annotation.ExtensionAttribute;
import com.jingrui.jrap.system.dto.BaseDTO;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 权限组件元素DTO.
 *
 * @author qiang.zeng
 */
@ExtensionAttribute(disable = true)
@Table(name = "sys_resource_item_element")
public class ResourceItemElement extends BaseDTO {

    public static final String FIELD_ELEMENT_ID = "elementId";
    public static final String FIELD_TYPE = "type";
    public static final String FIELD_PROPERTY = "property";
    public static final String FIELD_PROPERTY_VALUE = "propertyValue";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_RESOURCE_ITEM_ID = "resourceItemId";

    public static final String TYPE_GRID_BUTTONS = "grid-buttons";
    public static final String TYPE_COLUMN_BUTTONS = "column-buttons";
    public static final String TYPE_COLUMN = "column";
    public static final String TYPE_FORM_BUTTONS = "form-buttons";
    public static final String TYPE_FORM_FIELD = "form-field";

    public static final String DATA_OLD = "oldData";
    public static final String DATA_NEW = "newData";

    @Id
    @GeneratedValue
    @Column
    private Long elementId;

    @Length(max = 20)
    @Column
    @NotEmpty
    private String type;

    @Length(max = 20)
    @Column
    @NotEmpty
    private String property;

    @Length(max = 20)
    @Column
    @NotEmpty
    private String propertyValue;

    @Length(max = 20)
    @Column
    private String name;
    @Column
    private Long resourceItemId;

    public Long getElementId() {
        return elementId;
    }

    public void setElementId(Long elementId) {
        this.elementId = elementId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getPropertyValue() {
        return propertyValue;
    }

    public void setPropertyValue(String propertyValue) {
        this.propertyValue = propertyValue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getResourceItemId() {
        return resourceItemId;
    }

    public void setResourceItemId(Long resourceItemId) {
        this.resourceItemId = resourceItemId;
    }

}
