package com.jingrui.jrap.core.web.view;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import freemarker.template.Configuration;
import freemarker.template.TemplateModelException;

/**
 * 
 * @author njq.niu@jingrui.com
 *
 */
public class ViewContext {
    
    private HttpServletRequest request;
    private HttpServletResponse response;
    private Map<String, Object> contextMap;
    private ScreenTemplate template;
    private String contextPath;
    private ViewTagFactory screenTagFactory;
    private Configuration configuration;

    private Map<String, Object> jsonConfig = new HashMap<>(100);
    private Map<String, Object> localMap = new HashMap<>(100);

    private ViewContext() {
    }

    public ViewContext(Configuration configuration, ViewTagFactory screenTagFactory, Map<String, Object> model,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        setContextMap(model);
        setScreenTagFactory(screenTagFactory);
        setConfiguration(configuration);
        setRequest(request);
        setResponse(response);
        initContext();
    }

    public ViewContext createCloneInstance() throws Exception {
        ViewContext instance = new ViewContext();
        instance.setConfiguration(getConfiguration());
        instance.setScreenTagFactory(getScreenTagFactory());
        instance.setContextMap(getContextMap());
        instance.setRequest(getRequest());
        instance.setRequest(getRequest());
        instance.setContextPath(getContextPath());
        return instance;
    }

    public void initContext() throws Exception {
        setContextPath(request.getContextPath());
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        put("request", request);
        this.request = request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    public Map<String, Object> getContextMap() {
        return contextMap;
    }

    public void setContextMap(Map<String, Object> model) {
        this.contextMap = model;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public ViewTagFactory getScreenTagFactory() {
        return screenTagFactory;
    }

    public void setScreenTagFactory(ViewTagFactory screenTagFactory) {
        this.screenTagFactory = screenTagFactory;
    }

    public String getUserAgent() {
        return getRequest().getHeader("User-Agent");
    }

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public void put(String key, Object value) {
        localMap.put(key, value);
    }

    public Object get(String key) {
        return localMap.get(key);
    }

    public Map<String, Object> getMap() {
        return localMap;
    }

    public Map<String, Object> getFreeMarkerModel() throws TemplateModelException {
        Map<String, Object> result = new HashMap<>();
        result.putAll(this.getContextMap());
        result.putAll(this.getMap());
        return result;
    }

    public String getJsonConfig() throws Exception {
        return JacksonUtil.getInstance().writeValueAsString(jsonConfig);
    }

    public void removeJsonConfig(String key) {
        jsonConfig.remove(key);
    }

    public void addJsonConfig(String key, Object value) {
        if (key != null && value != null)
            jsonConfig.put(key, value);
    }

    public ScreenTemplate getTemplate() {
        return template;
    }

    public void setTemplate(ScreenTemplate template) {
        this.template = template;
    }

}
