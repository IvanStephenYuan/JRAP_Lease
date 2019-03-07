package com.jingrui.jrap.core.web.view.ui;

import com.jingrui.jrap.core.web.view.ReferenceType;
import com.jingrui.jrap.core.web.view.UITag;
import com.jingrui.jrap.core.web.view.ViewContext;
import com.jingrui.jrap.core.web.view.XMap;

/**
 * 
 * 下拉框控件.
 * 
 * @author zhizheng.yang@jingrui.com
 * 
 */
@UITag
public class DropDownList extends Select {

    private static final String PROPERTITY_OPTION_LABEL = "optionLabel";
    private static final String PROPERTITY_OPTION_LABEL_TEMPLATE = "optionLabelTemplate";
    private static final String PROPERTITY_VALUE_TEMPLATE = "valueTemplate";
    
    public static DropDownList createInstance() {
        XMap view = new XMap(DEFAULT_TAG_PREFIX, DEFAULT_NAME_SPACE, "dropDownList");
        DropDownList dropDownList = new DropDownList();
        dropDownList.initPrototype(view);
        return dropDownList;
    }

    public String getOptionLabel() {
        return getPrototype().getString(PROPERTITY_OPTION_LABEL);
    }
    
    public void setOptionLabel(String label) {
        setPropertity(PROPERTITY_OPTION_LABEL, label);
    }
    

    public Object getOptionLabelTemplate() {
        return getPropertity(PROPERTITY_OPTION_LABEL_TEMPLATE);
    }
    
    public void setOptionLabelTemplate(String template) {
        setPropertity(PROPERTITY_OPTION_LABEL_TEMPLATE, template);
    }
    
    public void setOptionLabelTemplate(ReferenceType templateFunction) {
        setPropertity(PROPERTITY_OPTION_LABEL_TEMPLATE, templateFunction);
    }

    public Object getValueTemplate() {
        return getPropertity(PROPERTITY_VALUE_TEMPLATE);
    }
    
    public void setValueTemplate(String template) {
        setPropertity(PROPERTITY_VALUE_TEMPLATE, template);
    }
    
    public void setValueTemplate(ReferenceType templateFunction) {
        setPropertity(PROPERTITY_VALUE_TEMPLATE, templateFunction);
    }

    public void init(XMap view, ViewContext context) throws Exception {
        super.init(view, context);
        context.addJsonConfig(PROPERTITY_OPTION_LABEL, getOptionLabel());
        context.addJsonConfig(PROPERTITY_OPTION_LABEL_TEMPLATE, getOptionLabelTemplate());
        context.addJsonConfig(PROPERTITY_VALUE_TEMPLATE, getValueTemplate());
    }

}
