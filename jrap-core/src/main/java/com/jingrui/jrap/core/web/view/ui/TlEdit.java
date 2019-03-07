package com.jingrui.jrap.core.web.view.ui;

import com.jingrui.jrap.core.web.view.ReferenceType;
import com.jingrui.jrap.core.web.view.UITag;
import com.jingrui.jrap.core.web.view.ViewContext;
import com.jingrui.jrap.core.web.view.XMap;

/**
 * 多语言编辑器
 *
 * @author jinqin.ma@jingrui.com
 */
@UITag
public class TlEdit extends InputField {
    private static final String PROPERTITY_IDFIELD = "idField";
    private static final String PROPERTITY_FIELD = "field";
    private static final String PROPERTITY_DTO = "dto";
    private static final String PROPERTITY_MODEL = "model";
    private static final String PROPERTITY_ENABLE = "enable";

    private static final String PROPERTITY_OPEN = "open";
    private static final String PROPERTITY_CLOSE = "close";

    public static TlEdit createInstance() {
        XMap view = new XMap(DEFAULT_TAG_PREFIX, DEFAULT_NAME_SPACE, "tlEdit");
        TlEdit tlEdit = new TlEdit();
        tlEdit.initPrototype(view);
        return tlEdit;
    }

    public Boolean getEnable() {
        return getPrototype().getBoolean(PROPERTITY_ENABLE);
    }

    public void setEnable(Boolean enable) {
        setPropertity(PROPERTITY_ENABLE, enable);
    }

    public ReferenceType getModel() {
        if (getPrototype().getString(PROPERTITY_MODEL) != null) {
            return new ReferenceType(getPrototype().getString(PROPERTITY_MODEL));
        }
        return null;
    }

    public void setModel(ReferenceType model) {
        setPropertity(PROPERTITY_MODEL, model);
    }

    public String getIdField() {
        return getPrototype().getString(PROPERTITY_IDFIELD);
    }

    public void setIdField(String idField) {
        setPropertity(PROPERTITY_IDFIELD, idField);
    }

    public String getField() {
        return getPrototype().getString(PROPERTITY_FIELD);
    }

    public void setField(String field) {
        setPropertity(PROPERTITY_FIELD, field);
    }

    public String getDto() {
        return getPrototype().getString(PROPERTITY_DTO);
    }

    public void setDto(String dto) {
        setPropertity(PROPERTITY_DTO, dto);
    }

    protected void initPrototype(XMap view) {
        super.initPrototype(view);
        addEvents(PROPERTITY_CLOSE, PROPERTITY_OPEN);
    }

    public void init(XMap view, ViewContext context) throws Exception {
        super.init(view, context);
        context.addJsonConfig(PROPERTITY_MODEL, getModel());
        context.addJsonConfig(PROPERTITY_ENABLE, getEnable());
        context.addJsonConfig(PROPERTITY_IDFIELD, getIdField());
        context.addJsonConfig(PROPERTITY_FIELD, getField());
        context.addJsonConfig(PROPERTITY_DTO, getDto());
    }

}
