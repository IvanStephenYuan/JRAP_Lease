package com.jingrui.jrap.activiti.approval.dto;

/**
 * @author xiangyu.qi@jingrui.com
 */

import com.jingrui.jrap.mybatis.annotation.Condition;
import com.jingrui.jrap.mybatis.annotation.ExtensionAttribute;
import com.jingrui.jrap.system.dto.BaseDTO;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@ExtensionAttribute(disable = true)
@Table(name = "wfl_approve_strategy")
public class ApproveStrategy extends BaseDTO {
    @Id
    @GeneratedValue
    private Long approveStrategyId;

    @NotEmpty
    @Length(max = 50)
    //@Condition(operator = LIKE)
    private String code;

    @NotEmpty
    @Length(max = 255)
    @Condition(operator = LIKE)
    private String description;

    @Column(name = "conditions")
    @Length(max = 500)
    @NotEmpty
    private String condition;

    private String enableFlag;

    public void setApproveStrategyId(Long approveStrategyId) {
        this.approveStrategyId = approveStrategyId;
    }

    public Long getApproveStrategyId() {
        return approveStrategyId;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getCondition() {
        return condition;
    }

    public void setEnableFlag(String enableFlag) {
        this.enableFlag = enableFlag;
    }

    public String getEnableFlag() {
        return enableFlag;
    }

}
