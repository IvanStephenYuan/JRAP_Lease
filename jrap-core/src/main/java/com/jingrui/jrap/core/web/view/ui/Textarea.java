package com.jingrui.jrap.core.web.view.ui;

import com.jingrui.jrap.core.web.view.UITag;
import com.jingrui.jrap.core.web.view.ViewContext;
import com.jingrui.jrap.core.web.view.XMap;

/**
 * Created by ivan on 2017/1/9.
 */
@UITag
public class Textarea extends InputField {
    private static final String PROPERTITY_WIDTH = "width";
    private static final String PROPERTITY_HEIGHT = "height";

    public static Textarea createInstance() {
        XMap view = new XMap(DEFAULT_TAG_PREFIX, DEFAULT_NAME_SPACE, "textarea");
        Textarea textarea = new Textarea();
        textarea.initPrototype(view);
        return textarea;
    }
    public Object getHeight() {
        String height = getPrototype().getString(PROPERTITY_HEIGHT);
        if (height != null) {
            try {
                Integer.parseInt(height);
                return height;
            } catch (Exception e) {
                return height;
            }
        }
        return null;
    }

    public void setHeight(Integer height) {
        setPropertity(PROPERTITY_HEIGHT, height);
    }

    public void setHeight(String height) {
        setPropertity(PROPERTITY_HEIGHT, height);
    }


    public Object getWidth() {
        String width = getPrototype().getString(PROPERTITY_WIDTH);
        if (width != null) {
            try {
                Integer.parseInt(width);
                return width;
            } catch (Exception e) {
                return width;
            }
        }
        return null;
    }

    public void setWidth(Integer width) {
        setPropertity(PROPERTITY_WIDTH, width);
    }

    public void setWidth(String width) {
        setPropertity(PROPERTITY_WIDTH, width);
    }

    public void init(XMap view, ViewContext context) throws Exception {
        super.init(view, context);
        context.addJsonConfig(PROPERTITY_WIDTH, getWidth());
        context.addJsonConfig(PROPERTITY_HEIGHT, getHeight());
    }
}
