package com.jingrui.jrap.core.web.view.ui;

import com.jingrui.jrap.core.web.view.UITag;
import com.jingrui.jrap.core.web.view.ViewContext;
import com.jingrui.jrap.core.web.view.XMap;

/**
 * 
 * 下拉提示框.
 * 
 * @author hailin.xu@jingrui.com
 * 
 */
@UITag
public class Combobox extends Select {

    private static final String PROPERTITY_HIGHLIGHT_FIRST = "highlightFirst";
    private static final String PROPERTITY_SUGGEST = "suggest";

    public static Combobox createInstance() {
        XMap view = new XMap(DEFAULT_TAG_PREFIX, DEFAULT_NAME_SPACE, "combobox");
        Combobox combobox = new Combobox();
        combobox.initPrototype(view);
        return combobox;
    }

    public Boolean getHighlightFirst() {
        return getPrototype().getBoolean(PROPERTITY_HIGHLIGHT_FIRST);
    }

    public void setHighlightFirst(Boolean highlightFirst) {
        setPropertity(PROPERTITY_HIGHLIGHT_FIRST, highlightFirst);
    }

    public Boolean getSuggest() {
        return getPrototype().getBoolean(PROPERTITY_SUGGEST);
    }

    public void setSuggest(Boolean suggest) {
        setPropertity(PROPERTITY_SUGGEST, suggest);
    }

    public void init(XMap view, ViewContext context) throws Exception {
        super.init(view, context);
        context.addJsonConfig(PROPERTITY_HIGHLIGHT_FIRST, getHighlightFirst());
        context.addJsonConfig(PROPERTITY_SUGGEST, getSuggest());
    }

}
