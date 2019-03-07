package com.jingrui.jrap.core.web.view;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;

/**
 * 扫描包下所有带有UITag注解类.
 * 
 * @author njq.niu@jingrui.com
 */
public class ViewComponentScanner extends ClassPathBeanDefinitionScanner {

    protected final Logger logger = LoggerFactory.getLogger(ViewComponentScanner.class);

    private ViewTagFactory viewFatory;

    public ViewComponentScanner(BeanDefinitionRegistry registry) {
        super(registry);
    }

    public ViewTagFactory getViewFatory() {
        return viewFatory;
    }

    public void setViewFatory(ViewTagFactory viewFatory) {
        this.viewFatory = viewFatory;
    }

    @Override
    public Set<BeanDefinitionHolder> doScan(String... basePackages) {
        Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);
        if (beanDefinitions.isEmpty()) {
            if (logger.isWarnEnabled()) {
                logger.warn("No component was found in '{}' package. Please check your configuration.",
                        Arrays.toString(basePackages));
            }
        } else {
            try {
                processBeanDefinitions(beanDefinitions);
            } catch (Exception e) {
                throw new ApplicationContextException("Unable to scan components from package:" + basePackages, e);
            }
        }
        return beanDefinitions;
    }

    private void registerTagBean(String nameSpace, String name, Object inst) {
        HashMap<String, Map<String, Object>> tagMap = getViewFatory().getTagMap();
        Map<String, Object> tagInsMap = tagMap.get(nameSpace);
        if (tagInsMap == null) {
            tagInsMap = new HashMap<String, Object>(100);
            tagMap.put(nameSpace, tagInsMap);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Register tag bean '{} {}' ", nameSpace, name);
        }
        tagInsMap.put(name, inst);
    }

    private void processBeanDefinitions(Set<BeanDefinitionHolder> beanDefinitions)
            throws InstantiationException, IllegalAccessException, ClassNotFoundException {

        for (BeanDefinitionHolder holder : beanDefinitions) {
            Class<?> clazz = Class.forName(holder.getBeanDefinition().getBeanClassName());
            UITag vc = clazz.getAnnotation(UITag.class);
            if (vc != null) {
                String name = vc.name();
                String nameSpace = vc.nameSpace();
                if ("".equals(name)) {
                    StringBuilder builder = new StringBuilder(holder.getBeanName());
                    if (Character.isUpperCase(builder.charAt(0))) {
                        builder.replace(0, 1, String.valueOf(Character.toLowerCase(builder.charAt(0))));
                    }
                    name = builder.toString();
                }
                Object inst = clazz.newInstance();
                if (logger.isDebugEnabled()) {
                    logger.debug("init component: {}", name);
                }
                registerTagBean(nameSpace, name, inst);
            }
        }
    }

}
