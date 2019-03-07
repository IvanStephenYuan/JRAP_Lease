package com.jingrui.jrap.core.web.view.ui;

/**
 * Created by ivan on 2016/12/20.
 */

import com.google.common.hash.Hashing;
import com.jingrui.jrap.core.web.view.ReferenceType;
import com.jingrui.jrap.core.web.view.UITag;
import com.jingrui.jrap.core.web.view.ViewContext;
import com.jingrui.jrap.core.web.view.XMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 单选框
 */
@UITag
public class Radio extends InputField {
    private Object items;
    private static final String PROPERTITY_LAYOUT = "layout";
    private static final String PROPERTITY_READONLY = "readonly";
    public static final String PROPERTITY_ITEMS = "items";
    public static final String PROPERTITY_ITEM = "item";

    public static Radio createInstance() {
        XMap view = new XMap(DEFAULT_TAG_PREFIX, DEFAULT_NAME_SPACE, "radio");
        Radio radio = new Radio();
        radio.initPrototype(view);
        return radio;
    }

    public String getLayout() {
        return getPrototype().getString(PROPERTITY_LAYOUT);
    }

    public void setLayout(String layout) {
        setPropertity(PROPERTITY_LAYOUT, layout);
    }

    public Boolean getReadonly() {
        return getPrototype().getBoolean(PROPERTITY_READONLY);
    }

    public void setReadonly(String readonly) {
        setPropertity(PROPERTITY_READONLY, readonly);
    }

    public Object getItems() {
        XMap items = getPrototype().getChild(PROPERTITY_ITEMS);
        if (items != null) {
            List<Map<String, String>> list = new ArrayList<>();
            List<XMap> child = items.getChildren();
            for (XMap item : child) {
                Map<String, String> map = new HashMap<>();
                String value = item.getString("value");
                String label = item.getString("label");
                String checked = item.getString("checked");
                if (value != null) {
                    map.put("value", value);
                }
                if (label != null) {
                    map.put("label", label);
                }
                if (checked != null) {
                    map.put("checked", checked);
                }
                list.add(map);
            }
            return list;
        }
        return null;
    }

    public void setItems(List<Map<String, String>> items) {
        XMap map = getPrototype().getChild(PROPERTITY_ITEMS);
        if (map == null) {
            map = getPrototype().createChild(DEFAULT_TAG_PREFIX, DEFAULT_NAME_SPACE, PROPERTITY_ITEMS);
        }
        List<XMap> list = new ArrayList<>();
        for (Map<String, String> m : items) {
            XMap x = new XMap(map.createChild(DEFAULT_TAG_PREFIX, DEFAULT_NAME_SPACE, PROPERTITY_ITEM));
            for (Map.Entry<String, String> entry : m.entrySet()) {
                x.put(entry.getKey(), entry.getValue());
            }
            list.add(x);
        }
        map.addChild(list);
    }

    public void init(XMap view, ViewContext context) throws Exception {
        super.init(view, context);
        context.addJsonConfig(PROPERTITY_LAYOUT, getLayout());
        context.addJsonConfig(PROPERTITY_READONLY, getReadonly());
        context.addJsonConfig(PROPERTITY_ITEMS, getItems());
    }
}
