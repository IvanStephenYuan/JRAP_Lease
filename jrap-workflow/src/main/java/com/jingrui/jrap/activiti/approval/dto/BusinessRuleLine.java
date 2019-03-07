package com.jingrui.jrap.activiti.approval.dto;

/**
 * @author xiangyu.qi@jingrui.com
 */

import com.jingrui.jrap.mybatis.annotation.ExtensionAttribute;
import com.jingrui.jrap.mybatis.common.query.Where;
import com.jingrui.jrap.system.dto.BaseDTO;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@ExtensionAttribute(disable = true)
@Table(name = "wfl_business_rule_line")
public class BusinessRuleLine extends BaseDTO {

    @Id
    @GeneratedValue
    private Long businessRuleLineId;

    @Where
    private Long businessRuleId;

    @NotEmpty
    @Length(max = 255)
    private String description;

    @Length(max = 500)
    @NotEmpty
    private String conditions;

    private String enableFlag;

    public void setBusinessRuleLineId(Long businessRuleLineId) {
        this.businessRuleLineId = businessRuleLineId;
    }

    public Long getBusinessRuleLineId() {
        return businessRuleLineId;
    }

    public void setBusinessRuleId(Long businessRuleId) {
        this.businessRuleId = businessRuleId;
    }

    public Long getBusinessRuleId() {
        return businessRuleId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public String getConditions() {
        return conditions;
    }

    public void setEnableFlag(String enableFlag) {
        this.enableFlag = enableFlag;
    }

    public String getEnableFlag() {
        return enableFlag;
    }

}
