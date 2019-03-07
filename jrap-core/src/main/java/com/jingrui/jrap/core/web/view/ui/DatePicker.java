package com.jingrui.jrap.core.web.view.ui;

import com.jingrui.jrap.core.web.view.UITag;
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
public class DatePicker extends DateField {

    public static DatePicker createInstance() {
        XMap view = new XMap(DEFAULT_TAG_PREFIX, DEFAULT_NAME_SPACE, "datePicker");
        DatePicker datePicker = new DatePicker();
        datePicker.initPrototype(view);
        return datePicker;
    }
}
