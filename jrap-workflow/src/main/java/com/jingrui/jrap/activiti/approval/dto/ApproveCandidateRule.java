package com.jingrui.jrap.activiti.approval.dto;

/**
 * @author xiangyu.qi@jingrui.com
 */

import com.jingrui.jrap.mybatis.annotation.Condition;
import com.jingrui.jrap.mybatis.annotation.ExtensionAttribute;
import com.jingrui.jrap.system.dto.BaseDTO;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@ExtensionAttribute(disable = true)
@Table(name = "wfl_approve_candidate_rule")
public class ApproveCandidateRule extends BaseDTO {
    @Id
    @GeneratedValue
    private Long candidateRuleId;

    @NotEmpty
    //@Condition(operator = LIKE)
    @Length(max = 50)
    private String code;

    @NotEmpty
    @Length(max = 255)
    @Condition(operator = LIKE)
    private String description;

    @NotEmpty
    @Length(max = 100)
    private String expression;

    private String enableFlag;

    public void setCandidateRuleId(Long candidateRuleId) {
        this.candidateRuleId = candidateRuleId;
    }

    public Long getCandidateRuleId() {
        return candidateRuleId;
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

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getExpression() {
        return expression;
    }

    public void setEnableFlag(String enableFlag) {
        this.enableFlag = enableFlag;
    }

    public String getEnableFlag() {
        return enableFlag;
    }

}
