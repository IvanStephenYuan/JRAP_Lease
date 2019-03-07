package com.jingrui.jrap.core.web.view.ui;

import java.util.Map;

import com.jingrui.jrap.core.web.view.ReferenceType;
import com.jingrui.jrap.core.web.view.UITag;
import com.jingrui.jrap.core.web.view.ViewContext;
import com.jingrui.jrap.core.web.view.XMap;

/**
 * 
 * 自动提示控件
 * 
 * @author hailin.xu@jingrui.com
 *
 */

@UITag
public class AutoComplete extends InputField {

    private static final String PROPERTITY_ANIMATION = "animation";
    private static final String PROPERTITY_AUTO_WIDTH = "autoWidth";
    private static final String PROPERTITY_DATA_SOURCE = "dataSource";
    private static final String PROPERTITY_DATA_TEXT_FIELD = "dataTextField";
    private static final String PROPERTITY_DELAY = "delay";
    private static final String PROPERTITY_ENABLE = "enable";
    private static final String PROPERTITY_ENFORCE_MIN_LENGTH = "enforceMinLength";
    private static final String PROPERTITY_FILTER = "filter";
    private static final String PROPERTITY_FIXED_GROUP_TEMPLATE = "fixedGroupTemplate";
    private static final String PROPERTITY_FOOTER_TEMPLATE = "footerTemplate";
    private static final String PROPERTITY_GROUP_TEMPLATE = "groupTemplate";
    private static final String PROPERTITY_HEIGHT = "height";
    private static final String PROPERTITY_HIGHLIGHT_FIRST = "highlightFirst";
    private static final String PROPERTITY_IGNORE_CASE = "ignoreCase";
    private static final String PROPERTITY_MIN_LENGTH = "minLength";
    private static final String PROPERTITY_NO_DATA_TEMPLATE = "noDataTemplate";
    private static final String PROPERTITY_POPUP = "popup";
    private static final String PROPERTITY_SEPARATOR = "separator";
    private static final String PROPERTITY_SUGGEST = "suggest";
    private static final String PROPERTITY_HEADER_TEMPLATE = "headerTemplate";
    private static final String PROPERTITY_TEMPLATE = "template";
    private static final String PROPERTITY_VALUE_PRIMITIVE = "valuePrimitive";
    private static final String PROPERTITY_VIRTUAL = "virtual";
    private static final String PROPERTITY_CLOSE = "close";
    private static final String PROPERTITY_DATA_BOUND = "dataBound";
    private static final String PROPERTITY_FILTERING = "filtering";
    private static final String PROPERTITY_OPEN = "open";
    private static final String PROPERTITY_SELECT = "select";

    public static AutoComplete createInstance() {
        XMap view = new XMap(DEFAULT_TAG_PREFIX, DEFAULT_NAME_SPACE, "autoComplete");
        AutoComplete autoComplete = new AutoComplete();
        autoComplete.initPrototype(view);
        return autoComplete;
    }

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

    public Boolean getAutoWidth() {
        return getPrototype().getBoolean(PROPERTITY_AUTO_WIDTH);
    }

    public void setAutoWidth(Boolean autoWidth) {
        setPropertity(PROPERTITY_AUTO_WIDTH, autoWidth);
    }

    public ReferenceType getDataSource() {
        return ReferenceType.create(getPrototype().getString(PROPERTITY_DATA_SOURCE));
    }

    public void setDataSource(ReferenceType dataSource) {
        setPropertity(PROPERTITY_DATA_SOURCE, dataSource);
    }

    public String getDataTextField() {
        return getPrototype().getString(PROPERTITY_DATA_TEXT_FIELD);
    }

    public void setDataTextField(String dataTextField) {
        setPropertity(PROPERTITY_DATA_TEXT_FIELD, dataTextField);
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
        XMap map = getPrototype().getChild(PROPERTITY_FIXED_GROUP_TEMPLATE);
        if (map != null) {
            return map.getText();
        }
        return getPrototype().getPropertity(PROPERTITY_FIXED_GROUP_TEMPLATE);
    }

    public void setFixedGroupTemplate(String fixedGroupTemplate) {
        XMap map = getPrototype().getChild(PROPERTITY_FIXED_GROUP_TEMPLATE);
        if (map == null) {
            map = getPrototype().createChild(DEFAULT_TAG_PREFIX, DEFAULT_NAME_SPACE, PROPERTITY_FIXED_GROUP_TEMPLATE);
        }
        map.setText(fixedGroupTemplate);
    }

    public void setFixedGroupTemplate(ReferenceType fixedGroupTemplate) {
        setPropertity(PROPERTITY_FIXED_GROUP_TEMPLATE, fixedGroupTemplate);
    }

    public Object getFooterTemplate() {
        XMap map = getPrototype().getChild(PROPERTITY_FOOTER_TEMPLATE);
        if (map != null) {
            return map.getText();
        }
        return getPrototype().getPropertity(PROPERTITY_FOOTER_TEMPLATE);
    }

    public void setFooterTemplate(String footerTemplate) {
        XMap map = getPrototype().getChild(PROPERTITY_FOOTER_TEMPLATE);
        if (map == null) {
            map = getPrototype().createChild(DEFAULT_TAG_PREFIX, DEFAULT_NAME_SPACE, PROPERTITY_FOOTER_TEMPLATE);
        }
        map.setText(footerTemplate);
    }

    public void setFooterTemplate(ReferenceType footerTemplate) {
        setPropertity(PROPERTITY_FOOTER_TEMPLATE, footerTemplate);
    }

    public Object getGroupTemplate() {
        XMap map = getPrototype().getChild(PROPERTITY_GROUP_TEMPLATE);
        if (map != null) {
            return map.getText();
        }
        return getPrototype().getPropertity(PROPERTITY_GROUP_TEMPLATE);
    }

    public void setGroupTemplate(String groupTemplate) {
        XMap map = getPrototype().getChild(PROPERTITY_GROUP_TEMPLATE);
        if (map == null) {
            map = getPrototype().createChild(DEFAULT_TAG_PREFIX, DEFAULT_NAME_SPACE, PROPERTITY_GROUP_TEMPLATE);
        }
        map.setText(groupTemplate);
    }

    public void setGroupTemplate(ReferenceType groupTemplate) {
        setPropertity(PROPERTITY_GROUP_TEMPLATE, groupTemplate);
    }

    public Integer getHeight() {
        return getPrototype().getInteger(PROPERTITY_HEIGHT);
    }

    public void setHeight(Integer height) {
        setPropertity(PROPERTITY_HEIGHT, height);
    }

    public Boolean getHighlightFirst() {
        return getPrototype().getBoolean(PROPERTITY_HIGHLIGHT_FIRST);
    }

    public void setHighlightFirst(Boolean highlightFirst) {
        setPropertity(PROPERTITY_HIGHLIGHT_FIRST, highlightFirst);
    }

    public Boolean getIgnoreCase() {
        return getPrototype().getBoolean(PROPERTITY_IGNORE_CASE);
    }

    public void setIgnoreCase(Boolean ignoreCase) {
        setPropertity(PROPERTITY_IGNORE_CASE, ignoreCase);
    }

    public Integer getMinLength() {
        return getPrototype().getInteger(PROPERTITY_MIN_LENGTH);
    }

    public void setMinLength(Integer minLength) {
        setPropertity(PROPERTITY_MIN_LENGTH, minLength);
    }

    public Object getNoDataTemplate() {
        XMap map = getPrototype().getChild(PROPERTITY_NO_DATA_TEMPLATE);
        if (map != null) {
            return map.getText();
        }
        return getPrototype().getPropertity(PROPERTITY_NO_DATA_TEMPLATE);
    }

    public void setNoDataTemplate(String noDataTemplate) {
        XMap map = getPrototype().getChild(PROPERTITY_NO_DATA_TEMPLATE);
        if (map == null) {
            map = getPrototype().createChild(DEFAULT_TAG_PREFIX, DEFAULT_NAME_SPACE, PROPERTITY_NO_DATA_TEMPLATE);
        }
        map.setText(noDataTemplate);
    }

    public void setNoDataTemplate(ReferenceType noDataTemplate) {
        setPropertity(PROPERTITY_NO_DATA_TEMPLATE, noDataTemplate);
    }

    public Object getPopup() {
        return getPrototype().getChild(PROPERTITY_POPUP);
    }

    public void setPopup(Map<String, Object> popup) {
        XMap map = getPrototype().getChild(PROPERTITY_POPUP);
        if (map == null) {
            map = getPrototype().createChild(DEFAULT_TAG_PREFIX, DEFAULT_NAME_SPACE, PROPERTITY_POPUP);
        }
        if(popup != null){
            for (Map.Entry<String, Object> entry : popup.entrySet()) {
                map.putPropertity(entry, entry.getValue());
            }  
        }
    }

    public String[] getSeparator() {
        String separator = getPrototype().getString(PROPERTITY_SEPARATOR);
        if (separator != null) {
            return separator.split(" ");
        }
        return null;
    }

    public void setSeparator(String separator) {
        setPropertity(PROPERTITY_SUGGEST, separator);
    }

    public void setSeparator(String[] separator) {
        setPropertity(PROPERTITY_SUGGEST, String.join(" ", separator));
    }

    public Boolean getSuggest() {
        return getPrototype().getBoolean(PROPERTITY_SUGGEST);
    }

    public void setSuggest(Boolean suggest) {
        setPropertity(PROPERTITY_SUGGEST, suggest);
    }

    public Object getHeaderTemplate() {
        XMap map = getPrototype().getChild(PROPERTITY_HEADER_TEMPLATE);
        if (map != null) {
            return map.getText();
        }
        return getPrototype().getPropertity(PROPERTITY_HEADER_TEMPLATE);
    }

    public void setHeaderTemplate(String headerTemplate) {
        XMap map = getPrototype().getChild(PROPERTITY_HEADER_TEMPLATE);
        if (map == null) {
            map = getPrototype().createChild(DEFAULT_TAG_PREFIX, DEFAULT_NAME_SPACE, PROPERTITY_HEADER_TEMPLATE);
        }
        map.setText(headerTemplate);
    }

    public void setHeaderTemplate(ReferenceType headerTemplate) {
        setPropertity(PROPERTITY_HEADER_TEMPLATE, headerTemplate);
    }

    public Object getTemplate() {
        XMap map = getPrototype().getChild(PROPERTITY_TEMPLATE);
        if (map != null) {
            return map.getText();
        }
        return getPrototype().getPropertity(PROPERTITY_TEMPLATE);
    }

    public void setTemplate(String template) {
        XMap map = getPrototype().getChild(PROPERTITY_TEMPLATE);
        if (map == null) {
            map = getPrototype().createChild(DEFAULT_TAG_PREFIX, DEFAULT_NAME_SPACE, PROPERTITY_TEMPLATE);
        }
        map.setText(template);
    }

    public void setTemplate(ReferenceType template) {
        setPropertity(PROPERTITY_TEMPLATE, template);
    }

    public Boolean getValuePrimitive() {
        return getPrototype().getBoolean(PROPERTITY_VALUE_PRIMITIVE);
    }

    public void setValuePrimitive(Boolean valuePrimitive) {
        setPropertity(PROPERTITY_VALUE_PRIMITIVE, valuePrimitive);
    }

    public Object getVirtual() {
        XMap virtual = getPrototype().getChild(PROPERTITY_VIRTUAL);
        if (virtual != null) {
            Integer itemHeight = virtual.getInteger("itemHeight");
            ReferenceType valueMapper = ReferenceType.create(virtual.getString("valueMapper"));
            virtual.put("itemHeight", itemHeight);
            virtual.put("valueMapper", valueMapper);
            return virtual;
        }
        return getPrototype().getBoolean(PROPERTITY_VIRTUAL);
    }

    public void setVirtual(Boolean virtual) {
        setPropertity(PROPERTITY_VIRTUAL, virtual);
    }

    public void setVirtual(Map<String, Object> virtual) {
        XMap map = getPrototype().getChild(PROPERTITY_VIRTUAL);
        if (map == null) {
            map = getPrototype().createChild(DEFAULT_TAG_PREFIX, DEFAULT_NAME_SPACE, PROPERTITY_VIRTUAL);
        }
        if(virtual!=null){
            for (Map.Entry<String, Object> entry : virtual.entrySet()) {
                map.putPropertity(entry, entry.getValue());
            } 
        }
    }

    protected void initPrototype(XMap view) {
        super.initPrototype(view);
        addEvents(PROPERTITY_CLOSE, PROPERTITY_DATA_BOUND, PROPERTITY_FILTERING, PROPERTITY_OPEN, PROPERTITY_SELECT);
    }

    public void init(XMap view, ViewContext context) throws Exception {
        super.init(view, context);
        context.addJsonConfig(PROPERTITY_ANIMATION, getAnimation());
        context.addJsonConfig(PROPERTITY_AUTO_WIDTH, getAutoWidth());
        context.addJsonConfig(PROPERTITY_DATA_SOURCE, getDataSource());
        context.addJsonConfig(PROPERTITY_DATA_TEXT_FIELD, getDataTextField());
        context.addJsonConfig(PROPERTITY_DELAY, getDelay());
        context.addJsonConfig(PROPERTITY_ENABLE, getEnable());
        context.addJsonConfig(PROPERTITY_ENFORCE_MIN_LENGTH, getEnforceMinLength());
        context.addJsonConfig(PROPERTITY_FILTER, getFilter());
        context.addJsonConfig(PROPERTITY_FIXED_GROUP_TEMPLATE, getFixedGroupTemplate());
        context.addJsonConfig(PROPERTITY_FOOTER_TEMPLATE, getFooterTemplate());
        context.addJsonConfig(PROPERTITY_GROUP_TEMPLATE, getGroupTemplate());
        context.addJsonConfig(PROPERTITY_HEIGHT, getHeight());
        context.addJsonConfig(PROPERTITY_HIGHLIGHT_FIRST, getHighlightFirst());
        context.addJsonConfig(PROPERTITY_IGNORE_CASE, getIgnoreCase());
        context.addJsonConfig(PROPERTITY_MIN_LENGTH, getMinLength());
        context.addJsonConfig(PROPERTITY_NO_DATA_TEMPLATE, getNoDataTemplate());
        context.addJsonConfig(PROPERTITY_POPUP, getPopup());
        context.addJsonConfig(PROPERTITY_SEPARATOR, getSeparator());
        context.addJsonConfig(PROPERTITY_SUGGEST, getSuggest());
        context.addJsonConfig(PROPERTITY_HEADER_TEMPLATE, getHeaderTemplate());
        context.addJsonConfig(PROPERTITY_TEMPLATE, getTemplate());
        context.addJsonConfig(PROPERTITY_VALUE_PRIMITIVE, getValuePrimitive());
        context.addJsonConfig(PROPERTITY_VIRTUAL, getVirtual());
    }

}
