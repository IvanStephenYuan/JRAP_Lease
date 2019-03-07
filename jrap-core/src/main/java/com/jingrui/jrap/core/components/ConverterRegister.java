package com.jingrui.jrap.core.components;

import com.jingrui.jrap.core.AppContextInitListener;
import com.jingrui.jrap.core.util.TimeZoneUtil;
import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 应用启动注册BeanUtils日期转换器.
 *
 * @author qiang.zeng@jingrui.com
 * @date 2018/1/2
 */
@Component
public class ConverterRegister implements AppContextInitListener {

    @Override
    public void contextInitialized(ApplicationContext applicationContext) {
        ConvertUtils.register(new Converter() {

            @Override
            public Object convert(Class type, Object value) {
                // Handle Date
                if (value instanceof java.util.Date) {
                    return value;
                }

                // Handle Calendar
                if (value instanceof java.util.Calendar) {
                    return ((java.util.Calendar)value).getTime();
                }
                if (value == null) {
                    return null;
                }
                if (value instanceof Long) {
                    Long longObj = (Long) value;
                    return type.cast(new Date(longObj));
                }
                if (value instanceof String) {
                    String str = StringUtils.trim((String) value);
                    if (StringUtils.isEmpty(str)) {
                        return null;
                    }
                    try {
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        format.setTimeZone(TimeZoneUtil.getTimeZone());
                        return format.parse(str);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }
                return null;
            }
        }, Date.class);
    }
}
