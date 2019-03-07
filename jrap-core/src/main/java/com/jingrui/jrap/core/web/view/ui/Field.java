package com.jingrui.jrap.core.web.view.ui;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jingrui.jrap.core.web.view.ReferenceType;
import com.jingrui.jrap.core.web.view.XMap;

/**
 * DataSource-Schema-Model-Field
 * 
 * @author hailin.xu@jingrui.com
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Field {

    private static final String PROPERTITY_DEFAULT_VALUE = "defaultValue";
    private static final String PROPERTITY_TYPE = "type";
    private static final String PROPERTITY_EDITABLE = "editable";
    private static final String PROPERTITY_NULLABLE = "nullable";
    private static final String PROPERTITY_PARSE = "parse";
    private static final String PROPERTITY_FROM = "from";
    private static final String PROPERTITY_VALIDATION = "validation";
    private static final String PROPERTITY_NAME = "name";
    private static final String PROPERTITY_CHECKED_VALUE = "checkedValue";
    private static final String PROPERTITY_UNCHECKED_VALUE = "uncheckedValue";

    private Boolean editable;
    private Boolean nullable;
    private Object defaultValue;
    private Map<Object, Object> validation;
    private ReferenceType parse;
    private String from;
    private String type;
    private String name;
    private String checkedValue;
    private String uncheckedValue;
    
    public Field() {
    }

    public static Field parseField(XMap view) {
        Field f = new Field();
        if (view != null) {
            f.setDefaultValue(view.getPropertity(PROPERTITY_DEFAULT_VALUE));
            f.setEditable(view.getBoolean(PROPERTITY_EDITABLE));
            f.setFrom(view.getString(PROPERTITY_FROM));
            f.setNullable(view.getBoolean(PROPERTITY_NULLABLE));
            if (view.getString(PROPERTITY_PARSE) != null) {
                f.setParse(new ReferenceType(view.getString(PROPERTITY_PARSE)));
            }
            f.setType(view.getString(PROPERTITY_TYPE));
            f.setValidation(parseValidation(view.getChild(PROPERTITY_VALIDATION)));
            f.setCheckedValue(view.getString(PROPERTITY_CHECKED_VALUE));
            f.setUncheckedValue(view.getString(PROPERTITY_UNCHECKED_VALUE));
        }
        return f;
    }

    public static Map<Object, Object> parseValidation(XMap map) {
        if (map != null) {
            Map<Object, Object> validation = new HashMap<>();
            for (Map.Entry<Object, Object> entry : map.entrySet()) {
                if ("required".equals(entry.getKey())) {
                    validation.put("required", entry.getValue().equals("true"));
                }
                if ("max".equals(entry.getKey())) {
                    validation.put("max", Integer.parseInt(entry.getValue().toString()));
                }
                if ("min".equals(entry.getKey())) {
                    validation.put("min", Integer.parseInt(entry.getValue().toString()));
                } else {
                    if (entry.getValue().toString() != null) {
                        validation.put(entry.getKey(), new ReferenceType(entry.getValue().toString()));
                    }
                }
            }
            return validation;
        }
        return null;
    }

    public XMap toXMap() {
        XMap map = new XMap(ViewTag.DEFAULT_TAG_PREFIX, ViewTag.DEFAULT_NAME_SPACE, "item");
        map.put(PROPERTITY_DEFAULT_VALUE, getDefaultValue());
        map.put(PROPERTITY_EDITABLE, getEditable());
        map.put(PROPERTITY_FROM, getFrom());
        map.put(PROPERTITY_NAME, getName());
        map.put(PROPERTITY_NULLABLE, getNullable());
        map.put(PROPERTITY_PARSE, getParse());
        map.put(PROPERTITY_TYPE, getType());
        XMap mapValidation = new XMap(ViewTag.DEFAULT_TAG_PREFIX, ViewTag.DEFAULT_NAME_SPACE, "validation");
        if(getValidation()!=null){
            for (Map.Entry<Object, Object> entry : getValidation().entrySet()) {
                mapValidation.put(entry.getKey(), entry.getValue());
            }  
        }
        map.addChild(mapValidation);
        map.put(PROPERTITY_CHECKED_VALUE, getCheckedValue());
        map.put(PROPERTITY_UNCHECKED_VALUE, getUncheckedValue());
        return map;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getEditable() {
        return editable;
    }

    public void setEditable(Boolean editable) {
        this.editable = editable;
    }

    public Boolean getNullable() {
        return nullable;
    }

    public void setNullable(Boolean nullable) {
        this.nullable = nullable;
    }

    public Object getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(Object defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Map<Object, Object> getValidation() {
        return validation;
    }

    public void setValidation(Map<Object, Object> validation) {
        this.validation = validation;
    }

    public ReferenceType getParse() {
        return parse;
    }

    public void setParse(ReferenceType parse) {
        this.parse = parse;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCheckedValue() {
        return checkedValue;
    }

    public void setCheckedValue(String checkedValue) {
        this.checkedValue = checkedValue;
    }

    public String getUncheckedValue() {
        return uncheckedValue;
    }

    public void setUncheckedValue(String uncheckedValue) {
        this.uncheckedValue = uncheckedValue;
    }
    
}
