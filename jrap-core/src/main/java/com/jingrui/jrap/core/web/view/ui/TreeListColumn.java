package com.jingrui.jrap.core.web.view.ui;

import java.util.Map;

import com.jingrui.jrap.core.web.view.XMap;

public class TreeListColumn extends Column {

    private Object filterable;

    public static TreeListColumn parse(XMap view) {
        TreeListColumn column = new TreeListColumn();
        column.parseColumn(view);
        if (view.getChild(PROPERTITY_FILTERABLE) != null) {
            column.setFilterable(view.getChild(PROPERTITY_FILTERABLE));
        } else {
            column.setFilterable(view.getBoolean(PROPERTITY_FILTERABLE));
        }
        return column;
    }

    public XMap toXMap() {
        XMap map = new XMap(ViewTag.DEFAULT_TAG_PREFIX, ViewTag.DEFAULT_NAME_SPACE, "item");
        toXMap(map);
        if (getFilterable() instanceof Boolean) {
            map.put(PROPERTITY_FILTERABLE, getFilterable());
        } else {
            XMap filterable = new XMap(ViewTag.DEFAULT_TAG_PREFIX, ViewTag.DEFAULT_NAME_SPACE, PROPERTITY_FILTERABLE);
            Map<String, Object> f = (Map<String, Object>) getFilterable();
            if(f!=null){
                for (String key : f.keySet()) {
                    filterable.put(key, f.get(key));
                }  
            }
            map.addChild(filterable);
        }
        return map;
    }

    public Object getFilterable() {
        return filterable;
    }

    public void setFilterable(Boolean filterable) {
        this.filterable = filterable;
    }

    public void setFilterable(Map<Object, Object> filterable) {
        this.filterable = filterable;
    }
}
