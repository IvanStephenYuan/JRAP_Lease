package com.jingrui.jrap.core.web.view.ui;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;

import com.jingrui.jrap.core.web.view.ViewContext;
import com.jingrui.jrap.core.web.view.XMap;

/**
 * 
 * @author njq.niu@jingrui.com
 *
 */
public class DateField extends InputField {
    
    private static final String PROPERTITY_ANIMATION = "animation";
    private static final String PROPERTITY_ARIATEMPLATE = "ARIATemplate";
    private static final String PROPERTITY_CULTURE = "culture";
    private static final String PROPERTITY_DEPTH = "depth";
    private static final String PROPERTITY_FOOTER = "footer";
    private static final String PROPERTITY_FORMAT = "format";
    private static final String PROPERTITY_MAX = "max";
    private static final String PROPERTITY_MIN = "min";
    private static final String PROPERTITY_START = "start";
//    private static final String PROPERTITY_DISABLE_DATES = "disableDates";
    private static final String PROPERTITY_MONTH = "month";
    
    private static final String PROPERTITY_CLOSE = "close";
    private static final String PROPERTITY_OPEN = "open";
    
    
    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    
    public Object getValue() throws Exception {
        Object value = super.getValue();
        if (value != null) {
            return DateUtils.parseDate(value.toString(), new String[] { DEFAULT_DATE_FORMAT });
        }
        return null;
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
    
    public void setValue(Date value){
        super.setValue(value);
    }

    public String getARIAtemplate() {
        return getPrototype().getString(PROPERTITY_ARIATEMPLATE);
    }

    public void setARIAtemplate(String APIAtemplate) {
        setPropertity(PROPERTITY_ARIATEMPLATE, APIAtemplate);
    }

    public String getCulture() {
        return getPrototype().getString(PROPERTITY_CULTURE);
    }

    public void setCulture(String culture) {
        setPropertity(PROPERTITY_CULTURE, culture);
    }

    public String getDepth() {
        return getPrototype().getString(PROPERTITY_DEPTH);
    }

    public void setDepth(String depth) {
        setPropertity(PROPERTITY_DEPTH, depth);
    }

    public String getFooter() {
        return getPrototype().getString(PROPERTITY_FOOTER);
    }

    public void setFooter(String footer) {
        setPropertity(PROPERTITY_FOOTER, footer);
    }

    public String getFormat() {
        return getPrototype().getString(PROPERTITY_FORMAT);
    }

    public void setFormat(String format) {
        setPropertity(PROPERTITY_FOOTER, format);
    }

    public Date getMax() throws ParseException {
        String max = getPrototype().getString(PROPERTITY_MAX);
        if (max != null) {
            return DateUtils.parseDate(max, new String[] { DEFAULT_DATE_FORMAT });
        }
        return null;
    }

    public void setMax(String max) {
        setPropertity(PROPERTITY_MAX, max);
    }

    public Date getMin() throws ParseException {
        String min = getPrototype().getString(PROPERTITY_MIN);
        if (min != null) {
            return DateUtils.parseDate(min, new String[] { DEFAULT_DATE_FORMAT });
        }
        return null;
    }

    public void setMin(String min) {
        setPropertity(PROPERTITY_MIN, min);
    }

    public String getStart() {
        return getPrototype().getString(PROPERTITY_START);
    }

    public void setStart(String start) {
        setPropertity(PROPERTITY_START, start);
    }

    public Object getMonth() {
        return getPrototype().getChild(PROPERTITY_MONTH);
    }

    public void setMonth(Map<String, Object> month) {
        XMap map =  getPrototype().getChild(PROPERTITY_MONTH);
        if(map == null){
            map = getPrototype().createChild(DEFAULT_TAG_PREFIX, DEFAULT_NAME_SPACE, PROPERTITY_MONTH);
        }
        for(String key : month.keySet()){
            map.put(key, month.get(key));
        }
    }

    
    protected void initPrototype(XMap view) {
        super.initPrototype(view);
        addEvents(PROPERTITY_OPEN, PROPERTITY_CLOSE);
    }

    public void init(XMap view, ViewContext context) throws Exception {
        super.init(view, context);
        context.addJsonConfig(PROPERTITY_ARIATEMPLATE, getARIAtemplate());
        context.addJsonConfig(PROPERTITY_CULTURE, getCulture());
        context.addJsonConfig(PROPERTITY_DEPTH, getDepth());
        context.addJsonConfig(PROPERTITY_FOOTER, getFooter());
        context.addJsonConfig(PROPERTITY_FORMAT, getFormat());
        context.addJsonConfig(PROPERTITY_MAX, getMax());
        context.addJsonConfig(PROPERTITY_MIN, getMin());
        context.addJsonConfig(PROPERTITY_START, getStart());
        context.addJsonConfig(PROPERTITY_ANIMATION, getAnimation());
        context.addJsonConfig(PROPERTITY_MONTH,getMonth());
    }
}
