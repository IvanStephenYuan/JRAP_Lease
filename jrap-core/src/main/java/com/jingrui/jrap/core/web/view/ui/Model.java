package com.jingrui.jrap.core.web.view.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jingrui.jrap.core.web.view.ReferenceType;
import com.jingrui.jrap.core.web.view.XMap;

/**
 * DataSource-Schema-Model
 * 
 * @author hailin.xu@jingrui.com
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Model {

    private static final String PROPERTITY_ID = "id";
    private static final String PROPERTITY_FIELDS = "fields";
    private static final String PROPERTITY_EDITABLE = "editable";
    private static final String PROPERTITY_NAME = "name";
    private static final String PROPERTITY_MODEL = "model";

    private String id;
    private Map<String, Field> fields;
    private ReferenceType editable;

    public static Model parseModel(XMap view) {
        Model m = null;
        if (view != null) {
            m = new Model();
            m.setId(view.getString(PROPERTITY_ID));
            m.addFields(parseField(view.getChild(PROPERTITY_FIELDS)));
            if (view.getString(PROPERTITY_EDITABLE) != null) {
                m.setEditable(new ReferenceType(view.getString(PROPERTITY_EDITABLE)));
            }
            return m;
        }
        return m;
    }

    public static Map<String, Field> parseField(XMap fields) {
        if (fields != null) {
            List<XMap> list = fields.getChildren();
            Map<String, Field> map = new HashMap<>();
            if(list!=null){
                for (XMap m : list) {
                    if (m.getString(PROPERTITY_NAME) != null) {
                        map.put(m.getString(PROPERTITY_NAME), Field.parseField(m));
                    }
                }  
            }
            return map;
        }
        return null;
    }

    public XMap toXMap() {
        XMap map = new XMap(ViewTag.DEFAULT_TAG_PREFIX, ViewTag.DEFAULT_NAME_SPACE, PROPERTITY_MODEL);
        map.put(PROPERTITY_ID, getId());
        map.put(PROPERTITY_EDITABLE, getEditable());
        XMap fields = map.getChild(PROPERTITY_FIELDS);
        if (fields == null) {
            fields = new XMap(ViewTag.DEFAULT_TAG_PREFIX, ViewTag.DEFAULT_NAME_SPACE, PROPERTITY_FIELDS);
        }
        if(getFields() !=null){
            for (Map.Entry<String, Field> entry : getFields().entrySet()) {
                XMap m = entry.getValue().toXMap();
                m.put(PROPERTITY_NAME, entry.getKey());
                fields.addChild(m);
            } 
        }
        map.addChild(fields);
        return map;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ReferenceType getEditable() {
        return editable;
    }

    public void setEditable(ReferenceType editable) {
        this.editable = editable;
    }

    public void addFields(Map<String, Field> fields) {
        this.fields = fields;
    }

    public Map<String, Field> getFields() {
        return fields;
    }

}
