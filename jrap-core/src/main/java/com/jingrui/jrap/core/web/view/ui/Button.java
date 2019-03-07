package com.jingrui.jrap.core.web.view.ui;

import com.jingrui.jrap.core.web.view.UITag;
import com.jingrui.jrap.core.web.view.ViewContext;
import com.jingrui.jrap.core.web.view.XMap;

/**
 * 
 * 按钮标签.
 * 
 * @author njq.niu@jingrui.com
 *
 */
@UITag
public class Button extends Component {

    private static final String PROPERTITY_TEXT = "text";
    private static final String PROPERTITY_ENABLE = "enable";
    private static final String PROPERTITY_CLICK = "click";
    private static final String PROPERTITY_ICON = "icon";
    private static final String PROPERTITY_IMAGE_URL = "imageUrl";
    private static final String PROPERTITY_SPRITE_CSS_CLASS = "spriteCssClass";

    public static Button createInstance() {
        XMap view = new XMap(DEFAULT_TAG_PREFIX, DEFAULT_NAME_SPACE, "button");
        Button button = new Button();
        button.initPrototype(view);
        return button;
    }

    public Boolean getEnable() {
        return getPrototype().getBoolean(PROPERTITY_ENABLE);
    }

    public void setEnable(Boolean enable) {
        setPropertity(PROPERTITY_ENABLE, enable);
    }

    public String getText() {
        return getPrototype().getString(PROPERTITY_TEXT);
    }

    public void setText(String text) {
        setPropertity(PROPERTITY_TEXT, text);
    }

    public String getIcon() {
        return getPrototype().getString(PROPERTITY_ICON);
    }

    public void setIcon(String icon) {
        setPropertity(PROPERTITY_ICON, icon);
    }

    public String getImageUrl() {
        return getPrototype().getString(PROPERTITY_IMAGE_URL);
    }

    public void setImageUrl(String imageUrl) {
        setPropertity(PROPERTITY_IMAGE_URL, imageUrl);
    }

    public String getSpriteCssClass() {
        return getPrototype().getString(PROPERTITY_SPRITE_CSS_CLASS);
    }

    public void setSpriteCssClass(String clazz) {
        setPropertity(PROPERTITY_SPRITE_CSS_CLASS, clazz);
    }
    
    public String getClick(){
        return getPrototype().getString(PROPERTITY_CLICK);
    }
    
    public void setClick(String click){
        setPropertity(PROPERTITY_CLICK, click);
    }
    

    protected void initPrototype(XMap view) {
        super.initPrototype(view);
        addEvent(PROPERTITY_CLICK);
    }

    public void init(XMap view, ViewContext context) throws Exception {
        super.init(view, context);
        context.put(PROPERTITY_TEXT, getText());
        context.addJsonConfig(PROPERTITY_ENABLE, getEnable());
        context.addJsonConfig(PROPERTITY_ICON, getIcon());
        context.addJsonConfig(PROPERTITY_IMAGE_URL, getImageUrl());
        context.addJsonConfig(PROPERTITY_SPRITE_CSS_CLASS, getSpriteCssClass());
    }
}
