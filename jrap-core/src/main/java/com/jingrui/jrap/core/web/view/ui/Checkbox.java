package com.jingrui.jrap.core.web.view.ui;

import com.jingrui.jrap.core.web.view.UITag;
import com.jingrui.jrap.core.web.view.ViewContext;
import com.jingrui.jrap.core.web.view.XMap;

/**
 * 
 * 复选框.
 * 
 * @author hailin.xu@jingrui.com
 * @author njq.niu@jingrui.com
 * 
 */
@UITag
public class Checkbox extends InputField {
//    private static final String PROPERTITY_NAME = "name";
    private static final String PROPERTITY_CHECKED_VALUE = "checkedValue";
    private static final String PROPERTITY_UNCHECKED_VALUE = "uncheckedValue";
//    private static final String PROPERTITY_CHECKED = "checked";
    private static final String PROPERTITY_READONLY = "readonly";
    private static final String PROPERTITY_LABEL = "label";
    
    public static Checkbox createInstance() {
        XMap view = new XMap(DEFAULT_TAG_PREFIX, DEFAULT_NAME_SPACE, "checkbox");
        Checkbox checkbox = new Checkbox();
        checkbox.initPrototype(view);
        return checkbox;
    }
    
//    public String getName() {
//        return getPrototype().getString(PROPERTITY_NAME);
//    }
//    
//    public void setName(String name) {
//        setPropertity(PROPERTITY_NAME, name);
//    }
    
    public String getCheckedValue() {
        return getPrototype().getString(PROPERTITY_CHECKED_VALUE);
    }
    
    public void setCheckedValue(String checkedValue) {
        setPropertity(PROPERTITY_CHECKED_VALUE, checkedValue);
    }
    
    public String getUncheckedValue() {
        return getPrototype().getString(PROPERTITY_UNCHECKED_VALUE);
    }

    public void setUncheckedValue(String UncheckedValue) {
        setPropertity(PROPERTITY_UNCHECKED_VALUE, UncheckedValue);
    }
    
//    public String getChecked() {
//        return getPrototype().getString(PROPERTITY_CHECKED);
//    }
//    
//    public void setChecked(String checked) {
//        setPropertity(PROPERTITY_CHECKED, checked);
//    }
    
    public Boolean getReadonly() {
        return getPrototype().getBoolean(PROPERTITY_READONLY);
    }
    
    public void setReadonly(Boolean readonly) {
        setPropertity(PROPERTITY_READONLY, readonly);
    }
    
    public String getLabel() {
        return getPrototype().getString(PROPERTITY_LABEL);
    }
    
    public void setLabel(String label) {
        setPropertity(PROPERTITY_LABEL, label);
    }
    
    public void init(XMap view, ViewContext context) throws Exception {
        super.init(view, context);
//        context.put(PROPERTITY_CHECKED, getChecked());
//        context.addJsonConfig(PROPERTITY_NAME, getName());
        context.addJsonConfig(PROPERTITY_CHECKED_VALUE, getCheckedValue());
        context.addJsonConfig(PROPERTITY_UNCHECKED_VALUE, getUncheckedValue());
        context.addJsonConfig(PROPERTITY_READONLY, getReadonly());
        context.addJsonConfig(PROPERTITY_LABEL, getLabel());
    }

}
