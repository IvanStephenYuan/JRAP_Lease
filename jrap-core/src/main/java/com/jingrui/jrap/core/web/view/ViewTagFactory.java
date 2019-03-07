package com.jingrui.jrap.core.web.view;

import static org.springframework.util.Assert.notNull;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.StringUtils;

import com.jingrui.jrap.core.web.view.ui.ViewTag;

/**
 * 
 * @author njq.niu@jingrui.com
 *
 */
public class ViewTagFactory implements BeanDefinitionRegistryPostProcessor, InitializingBean {

    private ViewTag defaultTag;
    private HashMap<String, Map<String, Object>> tagMap = new HashMap<>(100);
    private String basePackage;

    public HashMap<String, Map<String, Object>> getTagMap() {
        return tagMap;
    }

    public void setTagMap(HashMap<String, Map<String, Object>> tagMap) {
        this.tagMap = tagMap;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public void afterPropertiesSet() throws Exception {
        notNull(this.basePackage, "Property 'basePackage' is required");
    }

    public ViewTag getTag(String nameSpace, String name) {
        Map<String, Object> map = tagMap.get(nameSpace);
        if (map != null) {
            return (ViewTag) map.get(name);
        }
        return getDefaultTag();
    }

    private ViewTag getDefaultTag() {
        if (defaultTag == null) {
            defaultTag = new XMLTag();
        }
        return defaultTag;
    }

    public void postProcessBeanFactory(ConfigurableListableBeanFactory arg0) throws BeansException {
    }

    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        ViewComponentScanner scanner = new ViewComponentScanner(registry);
        scanner.setViewFatory(this);
        scanner.addIncludeFilter(new AnnotationTypeFilter(UITag.class));
        scanner.scan(StringUtils.tokenizeToStringArray(this.basePackage, ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS));
    }

}
