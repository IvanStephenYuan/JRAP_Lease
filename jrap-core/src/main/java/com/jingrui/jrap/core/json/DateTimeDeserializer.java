/*
 * #{copyright}#
 */
package com.jingrui.jrap.core.json;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.jingrui.jrap.core.util.TimeZoneUtil;

/**
 * 时间序反列化类.
 * 
 * @author njq.niu@jingrui.com
 *
 *         2016年3月16日
 */
public class DateTimeDeserializer extends JsonDeserializer<Date> implements ContextualDeserializer {

    private static Logger log = LoggerFactory.getLogger(DateTimeDeserializer.class);
    private String pattern = "yyyy-MM-dd HH:mm:ss";

    /**
     * @return the pattern
     */
    public String getPattern() {
        return pattern;
    }

    /**
     * @param pattern
     *            the pattern to set
     */
    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public Date deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        String str = jp.getText();
        if(StringUtils.isEmpty(str)) {
            return null;
        }
        try {
            // TODO:优化!
            SimpleDateFormat sb = new SimpleDateFormat(getPattern());
            sb.setTimeZone(TimeZoneUtil.getTimeZone());
            return sb.parse(str);
        } catch (ParseException e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage(), e);
            }
        }
        return null;
    }

    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property)
            throws JsonMappingException {
        DateTimeDeserializer deserializer = new DateTimeDeserializer();
        if(property != null){
            JsonFormat jf = property.getMember().getAnnotation(JsonFormat.class);
            if (jf != null && !"".equals(jf.pattern())) {
                deserializer.setPattern(jf.pattern());
            }
        } else {
            deserializer.setPattern("yyyy-MM-dd HH:mm:ss");
        }
        return deserializer;
    }

}
