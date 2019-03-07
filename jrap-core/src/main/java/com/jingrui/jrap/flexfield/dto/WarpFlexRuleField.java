package com.jingrui.jrap.flexfield.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jingrui.jrap.cache.Cache;
import com.jingrui.jrap.cache.impl.LovCache;
import com.jingrui.jrap.cache.impl.SysCodeCache;
import com.jingrui.jrap.core.components.ApplicationContextHelper;
import com.jingrui.jrap.core.impl.RequestHelper;
import com.jingrui.jrap.system.dto.Code;
import com.jingrui.jrap.system.dto.Lov;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/8.
 */
public class WarpFlexRuleField {

    private List<FlexRuleField> fields;

    private int totoal;

    private Long rowNumber;


    private static ObjectMapper mapper = new ObjectMapper();

    public List<FlexRuleField> getFields() {
        return fields;
    }

    public void setFields(List<FlexRuleField> fields) {
        this.fields = fields;
    }

    public int getTotoal() {
        return totoal;
    }

    public void setTotoal(int totoal) {
        this.totoal = totoal;
    }

    public Long getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(Long rowNumber) {
        this.rowNumber = rowNumber;
    }

    public WarpFlexRuleField warpField(List<FlexRuleField> flexRuleFields) {
        setTotoal(flexRuleFields.size());
        setFields(flexRuleFields);
        setRowNumber(flexRuleFields.get(0).getFieldColumnNumber());
        return this;
    }


}
