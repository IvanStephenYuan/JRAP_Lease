package com.jingrui.jrap.core.web.view.ui;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jingrui.jrap.core.web.view.ReferenceType;
import com.jingrui.jrap.core.web.view.XMap;

/**
 * DataSource - Schema
 * 
 * @author hailin.xu@jingrui.com
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Schema {

    private static final String PROPERTITY_AGGREGATES = "aggregates";
    private static final String PROPERTITY_DATA = "data";
    private static final String PROPERTITY_ERRORS = "errors";
    private static final String PROPERTITY_GROUPS = "groups";
    private static final String PROPERTITY_MODEL = "model";
    private static final String PROPERTITY_PARSE = "parse";
    private static final String PROPERTITY_TOTAL = "total";
    private static final String PROPERTITY_TYPE = "type";

    private Object aggregates;
    private Object data;
    private Object errors;
    private Object groups;
    private Model model;
    private ReferenceType parse;
    private Object total;
    private String type;

    public static Schema parseXMap(XMap view) {
        if (view != null) {
            Schema schema = new Schema();
            schema.setAggregates(view.getPropertity(PROPERTITY_AGGREGATES));
            schema.setData(view.getPropertity(PROPERTITY_DATA));
            schema.setErrors(view.getPropertity(PROPERTITY_ERRORS));
            schema.setGroups(view.getPropertity(PROPERTITY_GROUPS));
            schema.setModel(Model.parseModel(view.getChild(PROPERTITY_MODEL)));
            if (view.getString(PROPERTITY_PARSE) != null) {
                schema.setParse(new ReferenceType(view.getString(PROPERTITY_PARSE)));
            }
            schema.setTotal(view.getPropertity(PROPERTITY_TOTAL));
            schema.setType(view.getString(PROPERTITY_TYPE));
            return schema;
        }
        return null;
    }

    public XMap toXMap() {
        XMap map = new XMap(ViewTag.DEFAULT_TAG_PREFIX, ViewTag.DEFAULT_NAME_SPACE, "schema");
        map.put(PROPERTITY_AGGREGATES, getAggregates());
        map.put(PROPERTITY_DATA, getData());
        map.put(PROPERTITY_ERRORS, getErrors());
        map.put(PROPERTITY_GROUPS, getGroups());
        map.addChild(getModel().toXMap());
        map.put(PROPERTITY_PARSE, getParse());
        map.put(PROPERTITY_TOTAL, getTotal());
        map.put(PROPERTITY_TYPE, getType());
        return map;
    }

    public Object getAggregates() {
        return aggregates;
    }

    public void setAggregates(Object aggregates) {
        this.aggregates = aggregates;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Object getErrors() {
        return errors;
    }

    public void setErrors(Object errors) {
        this.errors = errors;
    }

    public Object getGroups() {
        return groups;
    }

    public void setGroups(Object groups) {
        this.groups = groups;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public ReferenceType getParse() {
        return parse;
    }

    public void setParse(ReferenceType parse) {
        this.parse = parse;
    }

    public Object getTotal() {
        return total;
    }

    public void setTotal(Object total) {
        this.total = total;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
