/*
 * #{copyright}#
 */
package com.jingrui.jrap.job.dto;

import com.jingrui.jrap.system.dto.BaseDTO;

/**
 * @author shiliyan
 *
 */
public class JobData extends BaseDTO {
    /**
     * 
     */
    private static final long serialVersionUID = 200612977390984121L;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    private String value;
}
