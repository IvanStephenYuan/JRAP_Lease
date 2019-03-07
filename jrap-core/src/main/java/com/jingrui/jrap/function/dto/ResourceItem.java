package com.jingrui.jrap.function.dto;

import com.jingrui.jrap.core.annotation.MultiLanguage;
import com.jingrui.jrap.system.dto.BaseDTO;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

/**
 * 权限组件DTO.
 *
 * @author njq.niu@jingrui.com
 * @date 2016年4月7日
 */
@MultiLanguage
@Table(name = "sys_resource_item_b")
public class ResourceItem extends BaseDTO {

    private static final long serialVersionUID = -2814559439360957338L;
    public static final String FIELD_RESOURCE_ITEM_ID = "resourceItemId";
    public static final String FIELD_OWNER_RESOURCE_ID = "ownerResourceId";
    public static final String FIELD_TARGET_RESOURCE_ID = "targetResourceId";
    public static final String FIELD_TARGET_RESOURCE_NAME = "targetResourceName";
    public static final String FIELD_ITEM_ID = "itemId";
    public static final String FIELD_ITEM_NAME = "itemName";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_ITEM_TYPE = "itemType";

    public static final String TYPE_FORM = "form";
    public static final String TYPE_GRID = "grid";
    public static final String TYPE_BUTTON = "buttons";
    public static final String TYPE_VARIABLE = "variable";
    public static final String TYPE_NOT_VARIABLE = "notVariable";

    @Id
    @Column
    @GeneratedValue(generator = GENERATOR_TYPE)
    private Long resourceItemId;

    @Column
    private Long ownerResourceId;

    @Column
    private Long targetResourceId;

    @Transient
    private String targetResourceName;

    @NotEmpty
    @Column
    private String itemId;

    @com.jingrui.jrap.core.annotation.MultiLanguageField
    @Column
    @Length(max = 50)
    @NotEmpty
    private String itemName;

    @com.jingrui.jrap.core.annotation.MultiLanguageField
    @Column
    @Length(max = 240)
    private String description;

    @Column
    @Length(max = 10)
    @OrderBy("DESC")
    private String itemType;

    public String getTargetResourceName() {
        return targetResourceName;
    }

    public void setTargetResourceName(String targetResourceName) {
        this.targetResourceName = targetResourceName;
    }

    public Long getResourceItemId() {
        return resourceItemId;
    }

    public void setResourceItemId(Long resourceItemId) {
        this.resourceItemId = resourceItemId;
    }

    public Long getOwnerResourceId() {
        return ownerResourceId;
    }

    public void setOwnerResourceId(Long ownerResourceId) {
        this.ownerResourceId = ownerResourceId;
    }

    public Long getTargetResourceId() {
        return targetResourceId;
    }

    public void setTargetResourceId(Long targetResourceId) {
        this.targetResourceId = targetResourceId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

}