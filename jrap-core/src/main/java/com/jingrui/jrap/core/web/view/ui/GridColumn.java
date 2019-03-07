package com.jingrui.jrap.core.web.view.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jingrui.jrap.core.web.view.ReferenceType;
import com.jingrui.jrap.core.web.view.XMap;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GridColumn extends Column {

    private String[] aggregates;
    private Object columns;
    private Object filterable;
    private Boolean groupable;
    private Object groupHeaderTemplate;
    private Object groupFooterTemplate;
    private Object values;
    private Object footerAttributes;

    private static final String PROPERTITY_AGGREGATES = "aggregates";
    private static final String PROPERTITY_VALUES = "values";
    private static final String PROPERTITY_GROUPABLE = "groupable";
    private static final String PROPERTITY_FOOTER_ATTRIBUTES = "footerAttributes";
    private static final String PROPERTITY_GROUP_HEADER_TEMPLATE = "groupHeaderTemplate";
    private static final String PROPERTITY_GROUP_FOOTER_TEMPLATE = "groupFooterTemplate";
    private static final String PROPERTITY_COLUMNS = "columns";
    private static final String PROPERTITY_VALUE = "value";
    private static final String PROPERTITY_TEXT = "text";
    private static final String PROPERTITY_ITEM = "item";

    public static GridColumn parse(XMap view) {
        GridColumn gridColumn = new GridColumn();
        if (view != null) {
            gridColumn.parseColumn(view);
            if (view.getChild(PROPERTITY_FILTERABLE) != null) {
                gridColumn.setFilterable(GridColumnFilterable.parseFilterable(view.getChild(PROPERTITY_FILTERABLE)));
            } else {
                gridColumn.setFilterable(view.getBoolean(PROPERTITY_FILTERABLE));
            }
            gridColumn.setAggregates(view.getString(PROPERTITY_AGGREGATES));
            gridColumn.setFooterAttributes(view.getChild(PROPERTITY_FOOTER_ATTRIBUTES));
            gridColumn.setGroupable(view.getBoolean(PROPERTITY_GROUPABLE));
            gridColumn.setGroupHeaderTemplate(view.getPropertity(PROPERTITY_GROUP_HEADER_TEMPLATE));
            gridColumn.setGroupFooterTemplate(view.getPropertity(PROPERTITY_GROUP_FOOTER_TEMPLATE));
            gridColumn.setValues(ParseValues(view.getChild(PROPERTITY_VALUES)));
        }
        return gridColumn;
    }

    /**
     * TODO: filterable
     * 
     * @return
     */
    public XMap toXMap() {
        XMap map = new XMap(ViewTag.DEFAULT_TAG_PREFIX, ViewTag.DEFAULT_NAME_SPACE, PROPERTITY_ITEM);
        toXMap(map);
        if(getAggregates()!=null){
            map.put(PROPERTITY_AGGREGATES, String.join(",", getAggregates()));
        }
        map.put(PROPERTITY_FOOTER_ATTRIBUTES, getFooterAttributes());
        map.put(PROPERTITY_GROUP_FOOTER_TEMPLATE, getGroupFooterTemplate());
        map.put(PROPERTITY_GROUP_HEADER_TEMPLATE, getGroupHeaderTemplate());
        map.put(PROPERTITY_GROUPABLE, getGroupable());
        map.put(PROPERTITY_VALUES, getValues());
        return map;
    }

    public String[] getAggregates() {
        return aggregates;
    }

    public void setAggregates(String aggregates) {
        if (aggregates != null) {
            String[] datas = aggregates.split(",");
            for (int i = 0; i < datas.length; i++) {
                datas[i] = datas[i].trim();
            }
            this.aggregates = datas;
        }
    }

    public void setAggregates(String[] aggregates) {
        this.aggregates = aggregates;
    }

    public Object getColumns() {
        return columns;
    }

    /*
     * TODO column下
     */
    public void setColumns(XMap map) {
        XMap columns = map.getChild(PROPERTITY_COLUMNS);
        List<XMap> cList = new ArrayList<>();
        if (columns != null) {
            List<XMap> list = columns.getChildren();
            if (list != null) {
                for (XMap column : list) {
                    GridColumn col = parse(column);
                    cList.add(col.toXMap());
                }
                this.columns = cList;
            }
        }

    }

    public Object getFilterable() {
        return filterable;
    }

    public void setFilterable(Boolean filterable) {
        this.filterable = filterable;
    }

    public void setFilterable(GridColumnFilterable filterable) {
        this.filterable = filterable;
    }

    public Boolean getGroupable() {
        return groupable;
    }

    public void setGroupable(Boolean groupable) {
        this.groupable = groupable;
    }

    public Object getGroupHeaderTemplate() {
        return groupHeaderTemplate;
    }

    public void setGroupHeaderTemplate(Object groupHeaderTemplate) {
        this.groupHeaderTemplate = groupHeaderTemplate;
    }

    public Object getGroupFooterTemplate() {
        return groupFooterTemplate;
    }

    public void setGroupFooterTemplate(Object groupFooterTemplate) {
        this.groupFooterTemplate = groupFooterTemplate;
    }

    public static List<Map<String, Object>> ParseValues(XMap view) {
        List<Map<String, Object>> cList = new ArrayList<>();
        if (view != null) {
            List<XMap> list = view.getChildren();
            if (list != null) {
                for (XMap value : list) {
                    if(value!=null){
                        Map<String, Object> map = new HashMap<>();
                        String text = value.getString(PROPERTITY_TEXT);
                        Integer val = value.getInteger(PROPERTITY_VALUE);
                        if (text != null) {
                            map.put(PROPERTITY_TEXT, text);
                        }
                        if (val != null) {
                            map.put(PROPERTITY_VALUE, val);
                        }
                        cList.add(map); 
                    }
                }
                return cList;
            }
        }
        return null;
    }

    public Object getValues() {
        return values;
    }

    public void setValues(List<Map<String, Object>> values) {
        this.values = values;
    }

    public Object getFooterAttributes() {
        return footerAttributes;
    }

    public void setFooterAttributes(Object footerAttributes) {
        this.footerAttributes = footerAttributes;
    }
}
