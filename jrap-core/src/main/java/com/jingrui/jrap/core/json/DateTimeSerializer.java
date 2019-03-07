/*
 * #{copyright}#
 */
package com.jingrui.jrap.core.json;

import java.io.IOException;
import java.util.Date;

import com.jingrui.jrap.core.util.TimeZoneUtil;
import org.apache.commons.lang.time.DateFormatUtils;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;

/**
 * 时间序列化类.
 * 
 * @author njq.niu@jingrui.com
 *
 *         2016年3月16日
 */
public class DateTimeSerializer extends JsonSerializer<Date> implements ContextualSerializer {

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
    public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider aSerializerProvider)
            throws IOException {
        jsonGenerator.writeString(DateFormatUtils.format(date, getPattern(), TimeZoneUtil.getTimeZone()));
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property)
            throws JsonMappingException {
        DateTimeSerializer serializer = new DateTimeSerializer();
        if(property != null){
            JsonFormat jf = property.getMember().getAnnotation(JsonFormat.class);
            if (jf != null && !"".equals(jf.pattern())) {
                serializer.setPattern(jf.pattern());
            }
        } else {
            serializer.setPattern(pattern);
        }
        return serializer;
    }
}
