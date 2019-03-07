package com.jingrui.jrap.core.web.view.ui;

import java.util.Map;
import java.util.Map.Entry;

import com.jingrui.jrap.core.web.view.ReferenceType;
import com.jingrui.jrap.core.web.view.ViewContext;
import com.jingrui.jrap.core.web.view.XMap;

/**
 * Combobox,DropdownList 抽象类.
 * 
 * @author njq.niu@jingrui.com
 *
 */
public class Select extends InputField {

    private static final String PROPERTITY_ANIMATION = "animation";
    private static final String PROPERTITY_AUTO_BIND = "autoBind";
    private static final String PROPERTITY_CASCADE_FROM = "cascadeFrom";
    private static final String PROPERTITY_CASCADE_FROM_FIELD = "cascadeFromField";
    private static final String PROPERTITY_DATA_TEXT_FIELD = "dataTextField";
    private static final String PROPERTITY_DATA_VALUE_FIELD = "dataValueField";
    private static final String PROPERTITY_DELAY = "delay";
    private static final String PROPERTITY_ENABLE = "enable";
    private static final String PROPERTITY_ENFORCE_MIN_LENGTH = "enforceMinLength";
    private static final String PROPERTITY_FILTER = "filter";
    private static final String PROPERTITY_FIXED_GROUP_TEMPLATE = "fixedGroupTemplate";
    private static final String PROPERTITY_FOOTER_TEMPLATE = "footerTemplate";
    private static final String PROPERTITY_GROUP_TEMPLATE = "groupTemplate";
    private static final String PROPERTITY_HEIGHT = "height";
    private static final String PROPERTITY_IGNORE_CASE = "ignoreCase";
    private static final String PROPERTITY_INDEX = "index";
    private static final String PROPERTITY_MIN_LENGHT = "minLength";
    private static final String PROPERTITY_NO_DATA_TEMPLATE = "noDataTemplate";
    private static final String PROPERTITY_HEADER_TEMPLATE = "headerTemplate";
    private static final String PROPERTITY_TEMPLATE = "template";
    private static final String PROPERTITY_TEXT = "text";
    private static final String PROPERTITY_VALUE_PRIMITIVE = "valuePrimitive";
    private static final String PROPERTITY_CLOSE = "close";
    private static final String PROPERTITY_DATA_BOUND = "dataBound";
    private static final String PROPERTITY_FILTERING = "filtering";
    private static final String PROPERTITY_OPEN = "open";
    private static final String PROPERTITY_SELECT = "select";
    private static final String PROPERTITY_CASCADE = "cascade";
    private static final String PROPERTITY_DATA_SOURCE = "dataSource";
    private static final String PROPERTITY_POPUP = "popup";
    private static final String PROPERTITY_VIRTUAL = "virtual";

    public Object getAnimation() {
        XMap animation = getPrototype().getChild(PROPERTITY_ANIMATION);
        if (animation != null) {
            return Animation.parseAnimation(animation);
        }
        if (getPrototype().getBoolean(PROPERTITY_ANIMATION) != null) {
            return getPrototype().getBoolean(PROPERTITY_ANIMATION);
        }
        return null;
    }

    public void setAnimation(Animation animation) {
        getPrototype().addChild(animation.toXMap());
    }

    public Boolean getAutoBind() {
        return getPrototype().getBoolean(PROPERTITY_AUTO_BIND);
    }

    public void setAutoBind(Boolean autoBind) {
        setPropertity(PROPERTITY_AUTO_BIND, autoBind);
    }

    public String getCascadeFrom() {
        return getPrototype().getString(PROPERTITY_CASCADE_FROM);
    }

    public void setCascadeFrom(String cf) {
        setPropertity(PROPERTITY_CASCADE_FROM, cf);
    }

    public String getCascadeFromField() {
        return getPrototype().getString(PROPERTITY_CASCADE_FROM_FIELD);
    }

    public void setCascadeFromField(String cff) {
        setPropertity(PROPERTITY_CASCADE_FROM_FIELD, cff);
    }

    public String getDataTextField() {
        return getPrototype().getString(PROPERTITY_DATA_TEXT_FIELD);
    }

    public void setDataTextField(String textField) {
        setPropertity(PROPERTITY_DATA_TEXT_FIELD, textField);
    }

    public String getDataValueField() {
        return getPrototype().getString(PROPERTITY_DATA_VALUE_FIELD);
    }

    public void setDataValueField(String valueField) {
        setPropertity(PROPERTITY_DATA_VALUE_FIELD, valueField);
    }

    public Integer getDelay() {
        return getPrototype().getInteger(PROPERTITY_DELAY);
    }

    public void setDelay(Integer delay) {
        setPropertity(PROPERTITY_DELAY, delay);
    }

    public Boolean getEnable() {
        return getPrototype().getBoolean(PROPERTITY_ENABLE);
    }

    public void setEnable(Boolean enable) {
        setPropertity(PROPERTITY_ENABLE, enable);
    }

    public Boolean getEnforceMinLength() {
        return getPrototype().getBoolean(PROPERTITY_ENFORCE_MIN_LENGTH);
    }

    public void setEnforceMinLength(Boolean enforceMinLength) {
        setPropertity(PROPERTITY_ENFORCE_MIN_LENGTH, enforceMinLength);
    }

    public String getFilter() {
        return getPrototype().getString(PROPERTITY_FILTER);
    }

    public void setFilter(String filter) {
        setPropertity(PROPERTITY_FILTER, filter);
    }

    public Object getFixedGroupTemplate() {
        return getPropertity(PROPERTITY_FIXED_GROUP_TEMPLATE);
    }

    public void setFixedGroupTemplate(String template) {
        setPropertity(PROPERTITY_FIXED_GROUP_TEMPLATE, template);
    }

    public void setFixedGroupTemplate(ReferenceType templateFunction) {
        setPropertity(PROPERTITY_FIXED_GROUP_TEMPLATE, templateFunction);
    }

    public Object getFooterTemplate() {
        return getPropertity(PROPERTITY_FOOTER_TEMPLATE);
    }

    public void setFooterTemplate(String template) {
        setPropertity(PROPERTITY_FOOTER_TEMPLATE, template);
    }

    public void setFooterTemplate(ReferenceType templateFunction) {
        setPropertity(PROPERTITY_FOOTER_TEMPLATE, templateFunction);
    }

    public Object getGroupTemplate() {
        return getPropertity(PROPERTITY_GROUP_TEMPLATE);
    }

    public void setGroupTempalte(String template) {
        setPropertity(PROPERTITY_GROUP_TEMPLATE, template);
    }

    public void setGroupTempalte(ReferenceType templateFunction) {
        setPropertity(PROPERTITY_GROUP_TEMPLATE, templateFunction);
    }

    public Integer getHeight() {
        return getPrototype().getInteger(PROPERTITY_HEIGHT);
    }

    public void setHeight(Integer height) {
        setPropertity(PROPERTITY_HEIGHT, height);
    }

    public Boolean getIgnoreCase() {
        return getPrototype().getBoolean(PROPERTITY_IGNORE_CASE);
    }

    public void setIgnoreCase(Boolean ignoreCase) {
        setPropertity(PROPERTITY_IGNORE_CASE, ignoreCase);
    }

    public Integer getIndex() {
        return getPrototype().getInteger(PROPERTITY_INDEX);
    }

    public void setIndex(Integer index) {
        setPropertity(PROPERTITY_INDEX, index);
    }

    public Integer getMinLength() {
        return getPrototype().getInteger(PROPERTITY_MIN_LENGHT);
    }

    public void setMinLength(Integer minLength) {
        setPropertity(PROPERTITY_MIN_LENGHT, minLength);
    }

    public Object getNoDataTemplate() {
        return getPropertity(PROPERTITY_NO_DATA_TEMPLATE);
    }

    public void setNoDataTemplate(String template) {
        setPropertity(PROPERTITY_NO_DATA_TEMPLATE, template);
    }

    public void setNoDataTemplate(ReferenceType templateFunction) {
        setPropertity(PROPERTITY_NO_DATA_TEMPLATE, templateFunction);
    }

    public Object getHeaderTemplate() {
        return getPropertity(PROPERTITY_HEADER_TEMPLATE);
    }

    public void setHeaderTemplate(String template) {
        setPropertity(PROPERTITY_HEADER_TEMPLATE, template);
    }

    public void setHeaderTemplate(ReferenceType templateFunction) {
        setPropertity(PROPERTITY_HEADER_TEMPLATE, templateFunction);
    }

    public Object getTemplate() {
        return getPropertity(PROPERTITY_TEMPLATE);
    }

    public void setTemplate(String template) {
        setPropertity(PROPERTITY_TEMPLATE, template);
    }

    public void setTemplate(ReferenceType templateFunction) {
        setPropertity(PROPERTITY_TEMPLATE, templateFunction);
    }

    public String getText() {
        return getPrototype().getString(PROPERTITY_TEXT);
    }

    public void setText(String text) {
        setPropertity(PROPERTITY_TEXT, text);
    }

    public Boolean getValuePrimitive() {
        return getPrototype().getBoolean(PROPERTITY_VALUE_PRIMITIVE);
    }

    public void setValuePrimitive(Boolean valuePrimitive) {
        setPropertity(PROPERTITY_VALUE_PRIMITIVE, valuePrimitive);
    }

    public ReferenceType getDataSource() {
        String dataSource = getPrototype().getString(PROPERTITY_DATA_SOURCE);
        if (dataSource != null) {
            return new ReferenceType(dataSource);
        }
        return null;
    }

    public void setDataSource(ReferenceType dataSource) {
        setPropertity(PROPERTITY_DATA_SOURCE, dataSource);
    }

    public Object getPopup() {
        XMap map = getPrototype().getChild(PROPERTITY_POPUP);
        return map;
    }

    public void setPopup(Map<String, Object> popup) {
        XMap map = getPrototype().getChild(PROPERTITY_POPUP);
        if (map == null) {
            map = getPrototype().createChild(DEFAULT_TAG_PREFIX, DEFAULT_NAME_SPACE, PROPERTITY_POPUP);
        }
        if (popup != null) {
            for (String key : popup.keySet()) {
                map.put(key, popup.get(key));
            }
        }
    }

    public Object getVirtual() {
        XMap virtual = getPrototype().getChild(PROPERTITY_VIRTUAL);
        if (virtual != null) {
            Integer itemHeight = virtual.getInteger("itemHeight");
            if (itemHeight != null) {
                virtual.put("itemHeight", itemHeight);
            }
            if (virtual.getString("valueMapper") != null) {
                virtual.put("valueMapper", new ReferenceType(virtual.getString("valueMapper")));
            }
            return virtual;
        }
        if (getPrototype().getBoolean(PROPERTITY_VIRTUAL) != null) {
            return getPrototype().getBoolean(PROPERTITY_VIRTUAL);
        }
        return null;
    }

    public void setVirtual(Map<String, Object> virtual) {
        XMap map = getPrototype().getChild(PROPERTITY_VIRTUAL);
        if (map == null) {
            map = getPrototype().createChild(DEFAULT_TAG_PREFIX, DEFAULT_NAME_SPACE, PROPERTITY_VIRTUAL);
        }
        if (virtual != null) {
            for (String key : virtual.keySet()) {
                map.put(key, virtual.get(key));
            }
        }
    }

    protected void initPrototype(XMap view) {
        super.initPrototype(view);
        addEvents(PROPERTITY_CLOSE, PROPERTITY_DATA_BOUND, PROPERTITY_FILTERING, PROPERTITY_OPEN, PROPERTITY_SELECT,
                PROPERTITY_CASCADE);
    }

    public void init(XMap view, ViewContext context) throws Exception {
        super.init(view, context);
        context.addJsonConfig(PROPERTITY_ANIMATION, getAnimation());
        context.addJsonConfig(PROPERTITY_AUTO_BIND, getAutoBind());
        context.addJsonConfig(PROPERTITY_CASCADE_FROM, getCascadeFrom());
        context.addJsonConfig(PROPERTITY_CASCADE_FROM_FIELD, getCascadeFromField());
        context.addJsonConfig(PROPERTITY_DATA_TEXT_FIELD, getDataTextField());
        context.addJsonConfig(PROPERTITY_DATA_VALUE_FIELD, getDataValueField());
        context.addJsonConfig(PROPERTITY_DELAY, getDelay());
        context.addJsonConfig(PROPERTITY_ENABLE, getEnable());
        context.addJsonConfig(PROPERTITY_ENFORCE_MIN_LENGTH, getEnforceMinLength());
        context.addJsonConfig(PROPERTITY_FILTER, getFilter());
        context.addJsonConfig(PROPERTITY_FIXED_GROUP_TEMPLATE, getFixedGroupTemplate());
        context.addJsonConfig(PROPERTITY_FOOTER_TEMPLATE, getFooterTemplate());
        context.addJsonConfig(PROPERTITY_GROUP_TEMPLATE, getGroupTemplate());
        context.addJsonConfig(PROPERTITY_HEIGHT, getHeight());
        context.addJsonConfig(PROPERTITY_IGNORE_CASE, getIgnoreCase());
        context.addJsonConfig(PROPERTITY_INDEX, getIndex());
        context.addJsonConfig(PROPERTITY_MIN_LENGHT, getMinLength());
        context.addJsonConfig(PROPERTITY_NO_DATA_TEMPLATE, getNoDataTemplate());
        context.addJsonConfig(PROPERTITY_HEADER_TEMPLATE, getHeaderTemplate());
        context.addJsonConfig(PROPERTITY_TEMPLATE, getTemplate());
        context.addJsonConfig(PROPERTITY_TEXT, getText());
        context.addJsonConfig(PROPERTITY_VALUE_PRIMITIVE, getValuePrimitive());
        context.addJsonConfig(PROPERTITY_DATA_SOURCE, getDataSource());
        context.addJsonConfig(PROPERTITY_POPUP, getPopup());
        context.addJsonConfig(PROPERTITY_VIRTUAL, getVirtual());
    }
}
