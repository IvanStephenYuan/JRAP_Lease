package com.jingrui.jrap.core.web.view.ui;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jingrui.jrap.core.web.view.XMap;

/**
 * 动画效果
 * 
 * @author hailin.xu@jingrui.com
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Animation {

    private static final String PROPERTITY_COLLAPSE = "collapse";
    private static final String PROPERTITY_EXPAND = "expand";
    private static final String PROPERTITY_DURATION = "duration";
    private static final String PROPERTITY_EFFECTS = "effects";

    private Object collapse;
    private Object expand;

    public static Animation parseAnimation(XMap view) {
        Animation animation = new Animation();
        if (view != null) {
            if (view.getChild(PROPERTITY_COLLAPSE) != null) {
                animation.setCollapse(toMap(view.getChild(PROPERTITY_COLLAPSE)));
            } else {
                animation.setCollapse(view.getBoolean(PROPERTITY_COLLAPSE));
            }
            if (view.getChild(PROPERTITY_EXPAND) != null) {
                animation.setExpand(toMap(view.getChild(PROPERTITY_EXPAND)));
            } else {
                animation.setExpand(view.getBoolean(PROPERTITY_EXPAND));
            }
        }
        return animation;
    }

    public static Map<String, Object> toMap(XMap view) {
        if (view != null) {
            Map<String, Object> map = new HashMap<>();
            if (view.getInteger(PROPERTITY_DURATION) != null) {
                map.put(PROPERTITY_DURATION, view.getInteger(PROPERTITY_DURATION));
            }
            if (view.getString(PROPERTITY_EFFECTS) != null) {
                map.put(PROPERTITY_EFFECTS, view.getString(PROPERTITY_EFFECTS));
            }
            return map;
        }
        return null;
    }

    public XMap toXMap() {
        XMap map = new XMap(ViewTag.DEFAULT_TAG_PREFIX, ViewTag.DEFAULT_NAME_SPACE, "animation");
        if (getCollapse() instanceof Boolean) {
            map.put(PROPERTITY_COLLAPSE, getCollapse());
        } else {
            XMap collapse = new XMap(ViewTag.DEFAULT_TAG_PREFIX, ViewTag.DEFAULT_NAME_SPACE, PROPERTITY_COLLAPSE);
            if (getCollapse() != null) {
                for (Map.Entry<String, Object> entry : ((Map<String, Object>) getCollapse()).entrySet()) {
                    collapse.put(entry.getKey(), entry.getValue());
                }
            }
            map.addChild(collapse);
        }
        if (getExpand() instanceof Boolean) {
            map.put(PROPERTITY_EXPAND, getExpand());
        } else {
            XMap expand = new XMap(ViewTag.DEFAULT_TAG_PREFIX, ViewTag.DEFAULT_NAME_SPACE, PROPERTITY_EXPAND);
            if (getExpand() != null) {
                for (Map.Entry<String, Object> entry : ((Map<String, Object>) getExpand()).entrySet()) {
                    expand.put(entry.getKey(), entry.getValue());
                }
            }
            map.addChild(expand);
        }
        return map;
    }

    public Object getCollapse() {
        return collapse;
    }

    public void setCollapse(Map<String, Object> collapse) {
        this.collapse = collapse;
    }

    public void setCollapse(Boolean collapse) {
        this.collapse = collapse;
    }

    public Object getExpand() {
        return expand;
    }

    public void setExpand(Map<String, Object> expand) {
        this.expand = expand;
    }

    public void setExpand(Boolean expand) {
        this.expand = expand;
    }
}
