package com.jingrui.jrap.core.web.view.ui;

import com.jingrui.jrap.core.web.view.UITag;
import com.jingrui.jrap.core.web.view.ViewContext;
import com.jingrui.jrap.core.web.view.XMap;

/**
 * 
 * 日期选择器.
 * 
 * @author hailin.xu@jingrui.com
 * @author njq.niu@jingrui.com
 * 
 */
@UITag
public class DateTimePicker extends DateField {

    private static final String PROPERTITY_INTERVAL = "interval";
    private static final String PROPERTITY_TIME_FORMAT = "timeFormat";
    
    public Integer getInterval() {
        return getPrototype().getInteger(PROPERTITY_INTERVAL);
    }

    public void setInterval(Integer interval) {
        setPropertity(PROPERTITY_INTERVAL, interval);
    }

    public String getTimeFormat() {
        return getPrototype().getString(PROPERTITY_TIME_FORMAT);
    }

    public void setTimeFormat(String timeFormat) {
        setPropertity(PROPERTITY_TIME_FORMAT, timeFormat);
    }
    
    public static DatePicker createInstance() {
        XMap view = new XMap(DEFAULT_TAG_PREFIX, DEFAULT_NAME_SPACE, "dateTimePicker");
        DatePicker datePicker = new DatePicker();
        datePicker.initPrototype(view);
        return datePicker;
    }
    
    public void init(XMap view, ViewContext context) throws Exception {
        super.init(view, context);
        context.addJsonConfig(PROPERTITY_INTERVAL, getInterval());
        context.addJsonConfig(PROPERTITY_TIME_FORMAT, getTimeFormat());
    }

    
}
