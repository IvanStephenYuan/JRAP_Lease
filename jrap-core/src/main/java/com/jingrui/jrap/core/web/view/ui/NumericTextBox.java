package com.jingrui.jrap.core.web.view.ui;

import java.text.NumberFormat;

import com.jingrui.jrap.core.web.view.UITag;
import com.jingrui.jrap.core.web.view.ViewContext;
import com.jingrui.jrap.core.web.view.XMap;

/**
 * 
 * 数字框.
 * 
 * @author hailin.xu@jingrui.com
 * @author njq.niu@jingrui.com
 * 
 */
@UITag
public class NumericTextBox extends InputField {

    public static final String PROPERTITY_DECIMALS = "decimals";
    public static final String PROPERTITY_DOWN_ARROW_TEXT = "downArrowText";
    public static final String PROPERTITY_FORMAT = "format";
    public static final String PROPERTITY_MAX = "max";
    public static final String PROPERTITY_MIN = "min";
    public static final String PROPERTITY_PLACEHOLDER = "placeholder";
    public static final String PROPERTITY_RESTRICT_DECIMALS = "restrictDecimals";
    public static final String PROPERTITY_ROUND = "round";
    public static final String PROPERTITY_SPINNERS = "spinners";
    public static final String PROPERTITY_STEP = "step";
    public static final String PROPERTITY_UP_ARROW_TEXT = "upArrowText";

    public static final String PROPERTITY_SPIN = "spin";

    public Object getValue() throws Exception {
        Object value = super.getValue();
        if (value != null) {
            return NumberFormat.getInstance().parse(value.toString());
        }
        return null;
    }

    public void setValue(Number value) {
        super.setValue(value);
    }

    public Double getDecimals() {
        return getPrototype().getDouble(PROPERTITY_DECIMALS);
    }

    public String getDownArrowText() {
        return getPrototype().getString(PROPERTITY_DOWN_ARROW_TEXT);
    }

    public String getFormat() {
        return getPrototype().getString(PROPERTITY_FORMAT);
    }

    public Double getMax() {
        return getPrototype().getDouble(PROPERTITY_MAX);
    }

    public Double getMin() {
        return getPrototype().getDouble(PROPERTITY_MIN);
    }

    public Boolean getRestrictDecimals() {
        return getPrototype().getBoolean(PROPERTITY_RESTRICT_DECIMALS);
    }

    public Boolean getRound() {
        return getPrototype().getBoolean(PROPERTITY_ROUND);
    }

    public Boolean getSpinners() {
        return getPrototype().getBoolean(PROPERTITY_SPINNERS);
    }

    public Double getStep() {
        return getPrototype().getDouble(PROPERTITY_STEP);
    }

    public String getUpArrowText() {
        return getPrototype().getString(PROPERTITY_UP_ARROW_TEXT);
    }

    protected void initPrototype(XMap view) {
        super.initPrototype(view);
        addEvent(PROPERTITY_SPIN);
    }

    public void init(XMap view, ViewContext context) throws Exception {
        super.init(view, context);
        context.addJsonConfig(PROPERTITY_DECIMALS, getDecimals());
        context.addJsonConfig(PROPERTITY_DOWN_ARROW_TEXT, getDownArrowText());
        context.addJsonConfig(PROPERTITY_FORMAT, getFormat());
        context.addJsonConfig(PROPERTITY_MAX, getMax());
        context.addJsonConfig(PROPERTITY_MIN, getMin());
        context.addJsonConfig(PROPERTITY_RESTRICT_DECIMALS, getRestrictDecimals());
        context.addJsonConfig(PROPERTITY_ROUND, getRound());
        context.addJsonConfig(PROPERTITY_SPINNERS, getSpinners());
        context.addJsonConfig(PROPERTITY_STEP, getStep());
        context.addJsonConfig(PROPERTITY_UP_ARROW_TEXT, getUpArrowText());
    }

}
