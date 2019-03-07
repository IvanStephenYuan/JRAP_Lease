package com.jingrui.jrap.core.web.view;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class JacksonUtil {

    private static ObjectMapper objectMapper;

    private JacksonUtil() {
    }

    static {
        objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(ReferenceType.class, new ReferenceSerializer());
        objectMapper.registerModule(module);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        DateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        objectMapper.setDateFormat(myDateFormat);
        objectMapper.setDateFormat(myDateFormat);
    }

    public static ObjectMapper getInstance() {
        return objectMapper;
    }

    // public static void main(String[] args) throws JsonGenerationException,
    // JsonMappingException, IOException{
    // Map map = new HashMap();
    // map.put("hanlder", new ReferenceType("test"));
    // map.put("date", new Date());
    // System.out.println(JacksonMapper.getInstance().writeValueAsString(map));
    //
    // }
}
