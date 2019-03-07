/**
 * Copyright 2016 www.extdo.com 
 */
package com.jingrui.jrap.mybatis.common.query;

/**
 * @author njq.niu@jingrui.com
 */
public enum SortType {
    
    ASC("ASC"), DESC("DESC");
    
    private String sql;

    SortType(String sql) {
        this.sql = sql;
    }
    
    public String sql() {
        return " " + this.sql + " ";
    }

}
