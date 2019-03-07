package com.jingrui.jrap.core.web.view.ui;

import com.jingrui.jrap.core.web.view.ReferenceType;
import com.jingrui.jrap.core.web.view.UITag;
import com.jingrui.jrap.core.web.view.ViewContext;
import com.jingrui.jrap.core.web.view.XMap;

/**
 * LOV组件
 * 
 * @author zhizheng.yang@jingrui.com
 *
 */
@UITag
public class Lov extends InputField {
    /*
     * animation
     */

    private static final String PROPERTITY_CODE = "code";
    private static final String PROPERTITY_CONTEXT_PATH = "contextPath";
    private static final String PROPERTITY_LOCALE = "locale";
    private static final String PROPERTITY_NAME = "name";
    private static final String PROPERTITY_ENABLED = "enable";
    private static final String PROPERTITY_VALUE_PRIMITIVE = "valuePrimitive";
    private static final String PROPERTITY_TEXT = "text";
    private static final String PROPERTITY_DATA_TEXT_FIELD = "dataTextField";
    private static final String PROPERTITY_DATA_VALUE_FIELD = "dataValueField";

    private static final String PROPERTITY_OPEN = "open";
    private static final String PROPERTITY_CLOSE = "close";
    private static final String PROPERTITY_SELECT = "select";
    private static final String PROPERTITY_QUERY = "query";

    public static Lov createInstance(){
        XMap view = new XMap(DEFAULT_TAG_PREFIX, DEFAULT_NAME_SPACE, "lov");
        Lov lov = new Lov();
        lov.initPrototype(view);
        return lov;
    }

    public Object getCode() {
        return getPropertity(PROPERTITY_CODE);
    }

    public void setCode(String code) {
        setPropertity(PROPERTITY_CODE, code);
    }

    public void setCode(ReferenceType codeFuntion) {
        setPropertity(PROPERTITY_CODE, codeFuntion);
    }

    public String getContextPath() {
        return getPrototype().getString(PROPERTITY_CONTEXT_PATH);
    }

    public void setContextPath(String cp) {
        setPropertity(PROPERTITY_CONTEXT_PATH, cp);
    }

    public String getLocale() {
        return getPrototype().getString(PROPERTITY_LOCALE);
    }

    public void setLocale(String locale) {
        setPropertity(PROPERTITY_LOCALE, locale);
    }

    public String getName() {
        return getPrototype().getString(PROPERTITY_NAME);
    }

    public void setName(String name) {
        setPropertity(PROPERTITY_NAME, name);
    }

    public Boolean getEnable() {
        return getPrototype().getBoolean(PROPERTITY_ENABLED);
    }

    public void setEnable(Boolean enable) {
        setPropertity(PROPERTITY_ENABLED, enable);
    }

    public Boolean getValuePrimitive() {
        return getPrototype().getBoolean(PROPERTITY_VALUE_PRIMITIVE);
    }

    public void setValuePrimitive(Boolean vp) {
        setPropertity(PROPERTITY_VALUE_PRIMITIVE, vp);
    }

    public String getText() {
        return getPrototype().getString(PROPERTITY_TEXT);
    }

    public void setText(String text) {
        setPropertity(PROPERTITY_TEXT, text);
    }

    public String getDataTextField() {
        return getPrototype().getString(PROPERTITY_DATA_TEXT_FIELD);
    }

    public void setDataTextField(String dtf) {
        setPropertity(PROPERTITY_DATA_TEXT_FIELD, dtf);
    }

    public String getDataValueField() {
        return getPrototype().getString(PROPERTITY_DATA_VALUE_FIELD);
    }

    public void setDataValueField(String dvf) {
        setPropertity(PROPERTITY_DATA_VALUE_FIELD, dvf);
    }

    protected void initPrototype(XMap view) {
        super.initPrototype(view);
        addEvents(PROPERTITY_CLOSE, PROPERTITY_OPEN, PROPERTITY_SELECT, PROPERTITY_QUERY);
    }

    public void init(XMap view, ViewContext context) throws Exception {
        super.init(view, context);
        context.put(PROPERTITY_NAME,getName()) ;
        context.addJsonConfig(PROPERTITY_CODE, getCode());
        context.addJsonConfig(PROPERTITY_TEXT, getText());
        context.addJsonConfig(PROPERTITY_VALUE_PRIMITIVE, getValuePrimitive());
        context.addJsonConfig(PROPERTITY_CONTEXT_PATH, getContextPath());
        context.addJsonConfig(PROPERTITY_DATA_TEXT_FIELD, getDataTextField());
        context.addJsonConfig(PROPERTITY_DATA_VALUE_FIELD, getDataValueField());
        context.addJsonConfig(PROPERTITY_ENABLED, getEnable());
        context.addJsonConfig(PROPERTITY_LOCALE, getLocale());
    }

}
