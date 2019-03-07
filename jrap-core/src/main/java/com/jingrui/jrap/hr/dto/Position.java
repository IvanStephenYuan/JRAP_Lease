package com.jingrui.jrap.hr.dto;

import javax.persistence.*;
import javax.persistence.criteria.JoinType;

import com.jingrui.jrap.core.BaseConstants;
import com.jingrui.jrap.core.annotation.MultiLanguage;
import com.jingrui.jrap.core.annotation.MultiLanguageField;
import com.jingrui.jrap.mybatis.common.query.*;
import com.jingrui.jrap.mybatis.common.query.JoinColumn;
import com.jingrui.jrap.mybatis.common.query.JoinTable;
import com.jingrui.jrap.system.dto.BaseDTO;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 岗位对象.
 *
 * @author hailin.xu@jingrui.com
 */
@MultiLanguage
@Table(name = "hr_org_position_b")
//@ExtensionAttribute(disable = true)
public class Position extends BaseDTO {

    public static final String FIELD_POSITION_ID = "positionId";
    public static final String FIELD_UNIT_ID = "unitId";
    public static final String FIELD_UNIT_NAME = "unitName";
    public static final String FIELD_POSITION_CODE = "positionCode";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_PARENT_POSITION_ID = "parentPositionId";
    public static final String FIELD_PARENT_POSITION_NAME = "parentPositionName";

    private static final long serialVersionUID = 1L;

    /**
     * 表ID，主键，供其他表做外键.
     */
    @Id
    @Where
    @GeneratedValue
    private Long positionId;

    /**
     * 部门id.
     */
    @JoinTable(name = "unitJoin", joinMultiLanguageTable = true, target = HrOrgUnit.class, type = JoinType.LEFT, on = {@JoinOn(joinField = HrOrgUnit.FIELD_UNIT_ID), @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    @OrderBy
    private Long unitId;

    /**
     * 部门名称
     */
    @Transient
    @JoinColumn(joinName = "unitJoin", field = HrOrgUnit.FIELD_NAME)
    @NotEmpty
    private String unitName;

    /**
     * 岗位编码.
     */
    @Where
    @Length(max = 50)
    @NotEmpty
    @OrderBy("ASC")
    private String positionCode;

    /**
     * 岗位名称.
     */
    @MultiLanguageField
    @Where(comparison = Comparison.LIKE)
    @Length(max = 100)
    @NotEmpty
    @OrderBy
    private String name;

    /**
     * 岗位描述
     */
    @MultiLanguageField
    @Length(max = 255)
    private String description;

    /**
     * 上级岗位id.
     */
    @Where
    @JoinTable(name = "positionJoin", joinMultiLanguageTable = true, target = Position.class, type = JoinType.LEFT, on = {@JoinOn(joinField = Position.FIELD_POSITION_ID), @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    @OrderBy
    private Long parentPositionId;
    /**
     * 上级岗位名称
     */
    @Transient
    @JoinColumn(joinName = "positionJoin", field = Position.FIELD_NAME)
    private String parentPositionName;

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getParentPositionName() {
        return parentPositionName;
    }

    public void setParentPositionName(String parentPositionName) {
        this.parentPositionName = parentPositionName;
    }

    public Long getPositionId() {
        return positionId;
    }

    public void setPositionId(Long positionId) {
        this.positionId = positionId;
    }

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    public String getPositionCode() {
        return positionCode;
    }

    public void setPositionCode(String positionCode) {
        this.positionCode = StringUtils.trim(positionCode);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getParentPositionId() {
        return parentPositionId;
    }

    public void setParentPositionId(Long parentPositionId) {
        this.parentPositionId = parentPositionId;
    }


}
