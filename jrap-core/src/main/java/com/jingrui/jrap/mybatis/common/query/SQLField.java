/**
 * Copyright 2016 www.extdo.com 
 */
package com.jingrui.jrap.mybatis.common.query;

import org.apache.commons.lang.StringUtils;

import java.util.Map;

/**
 * @author njq.niu@jingrui.com
 */
public class SQLField {

    private String field;
    
    public SQLField(String field){
        setField(field);
    }
    

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }


    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (!(obj instanceof SQLField))
            return false;

        SQLField sqlField = (SQLField) obj;
        return StringUtils.equals(field, sqlField.getField());
    }
}
