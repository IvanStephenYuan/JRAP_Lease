/*
 * *
 *  @file com.maddyhome.idea.copyright.pattern.JavaCopyrightVariablesProvider$1@5cf29a54$
 *  @CopyRight (C) 2018 ZheJiangJingRui Co. Ltd.
 *  @brief JingRui Application Platform
 *  @author $name$
 *  @email yulong.yuan@jr-info.cn
 *  @date $date$
 * /
 */

package com.jingrui.jrap.customer.entity;

import java.io.Serializable;

import com.jingrui.jrap.customer.entity.PreloanQueryResponse;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class PreloanSubmitResponse implements Serializable {
    private static final long serialVersionUID = 4152462611121573434L;
    private Boolean success = false;
    private String report_id;
    private String reason_code;
    private String reason_desc;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getReport_id() {
        return report_id;
    }

    public void setReport_id(String report_id) {
        this.report_id = report_id;
    }

    public String getReason_code() {
        return reason_code;
    }


    public void setReason_code(String reason_code) {
        this.reason_code = reason_code;
    }

    public String getReason_desc() {
        return reason_desc;
    }

    public void setReason_desc(String reason_desc) {
        this.reason_desc = reason_desc;
    }

    @Override
    public String toString(){
        ToStringBuilder stringBuilder = new ToStringBuilder(this);
        stringBuilder.append("success", success);
        stringBuilder.append("report_id", report_id);
        stringBuilder.append("reason_code", reason_code);
        stringBuilder.append("reason_desc", reason_desc);
        return stringBuilder.toString();
    }
}
