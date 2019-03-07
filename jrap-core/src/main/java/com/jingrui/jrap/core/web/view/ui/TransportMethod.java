package com.jingrui.jrap.core.web.view.ui;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jingrui.jrap.core.web.view.ReferenceType;
import com.jingrui.jrap.core.web.view.XMap;

/**
 * 
 * @author hailin.xu@jingrui.com
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransportMethod {

    private Boolean cache;
    private String contentType;
    private Object data;
    private String dataType;
    private String type;
    private Object url;

    private static final String PROPERTITY_CACHE = "cache";
    private static final String PROPERTITY_CONTENT_TYPE = "contentType";
    private static final String PROPERTITY_DATA = "data";
    private static final String PROPERTITY_DATA_TYPE = "dataType";
    private static final String PROPERTITY_TYPE = "type";
    private static final String PROPERTITY_URL = "url";

    public static TransportMethod parse(XMap view) {

        TransportMethod transportMethod = new TransportMethod();
        if (view != null) {
            transportMethod.setCache(view.getBoolean(PROPERTITY_CACHE));
            transportMethod.setContentType(view.getString(PROPERTITY_CONTENT_TYPE));
            if (view.getString(PROPERTITY_DATA) != null) {
                transportMethod.setData(new ReferenceType(view.getString(PROPERTITY_DATA)));
            }
            transportMethod.setDataType(view.getString(PROPERTITY_DATA_TYPE));
            transportMethod.setType(view.getString(PROPERTITY_TYPE));
            transportMethod.setUrl(view.getPropertity(PROPERTITY_URL));
            return transportMethod;
        }
        return null;
    }

    public XMap toXMap(String name) {
        XMap map = new XMap(ViewTag.DEFAULT_TAG_PREFIX, ViewTag.DEFAULT_NAME_SPACE, name);
        map.put(PROPERTITY_CACHE, getCache());
        map.put(PROPERTITY_CONTENT_TYPE, getContentType());
        map.put(PROPERTITY_DATA, getData());
        map.put(PROPERTITY_DATA_TYPE, getDataType());
        map.put(PROPERTITY_TYPE, getType());
        map.put(PROPERTITY_URL, getUrl());
        return map;
    }

    public Boolean getCache() {
        return cache;
    }

    public void setCache(Boolean cache) {
        this.cache = cache;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Object getData() {
        return data;
    }

    /*
     * TODO:XXX
     */
    public void setData(ReferenceType data) {
        this.data = data;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getUrl() {
        return url;
    }

    public void setUrl(Object url) {
        this.url = url;
    }

}
