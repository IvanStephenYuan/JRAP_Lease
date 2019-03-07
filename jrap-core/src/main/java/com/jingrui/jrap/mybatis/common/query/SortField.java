/**
 * Copyright 2016 www.extdo.com 
 */
package com.jingrui.jrap.mybatis.common.query;


/**
 * @author njq.niu@jingrui.com
 */
public class SortField extends SQLField {

    private SortType sortType;

    public SortField(String field, SortType SortType) {
        super(field);
        this.sortType = SortType;
    }

    public SortType getSortType() {
        return sortType;
    }
}
