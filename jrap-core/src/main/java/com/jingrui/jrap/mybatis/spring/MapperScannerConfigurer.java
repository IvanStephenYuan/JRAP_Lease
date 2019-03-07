/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2016 abel533@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.jingrui.jrap.mybatis.spring;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;

import com.jingrui.jrap.mybatis.common.Marker;
import com.jingrui.jrap.mybatis.mapperhelper.MapperHelper;
import com.jingrui.jrap.mybatis.util.StringUtil;

public class MapperScannerConfigurer extends org.mybatis.spring.mapper.MapperScannerConfigurer {
    private MapperHelper mapperHelper = new MapperHelper();


    private Map<String,String> propertiesMap;


    public void setMarkerInterface(Class<?> superClass) {
        super.setMarkerInterface(superClass);
        if (Marker.class.isAssignableFrom(superClass)) {
            mapperHelper.registerMapper(superClass);
        }
    }

    public MapperHelper getMapperHelper() {
        return mapperHelper;
    }

    public void setMapperHelper(MapperHelper mapperHelper) {
        this.mapperHelper = mapperHelper;
    }

    /**
     * 属性注入
     *
     * @param properties
     */
    public void setProperties(Properties properties) {
        mapperHelper.setProperties(properties);
    }

    public void setPropertiesMap(Map<String, String> propertiesMap) {
        if (propertiesMap.get("ORDER") == null) {
            if ("JDBC".equalsIgnoreCase(propertiesMap.get("IDENTITY"))) {
                propertiesMap.put("ORDER", "AFTER");
            } else {
                propertiesMap.put("ORDER", "BEFORE");
            }
        }
        this.propertiesMap = propertiesMap;
        //Properties properties = new Properties();
        //propertiesMap.forEach((k, v) -> {
        //    properties.put(k, v);
        //});
        //setProperties(properties);
    }

    /**
     * 注册完成后，对MapperFactoryBean的类进行特殊处理
     *
     * @param registry
     */
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) {

        Properties config = new Properties();
        Properties p = new Properties();
        try {
            config.load(getClass().getResourceAsStream("/config.properties"));
            if (propertiesMap.get("ORDER") == null) {
                if ("JDBC".equalsIgnoreCase(propertiesMap.get("IDENTITY"))) {
                    p.put("ORDER", "AFTER");
                } else {
                    p.put("ORDER", "BEFORE");
                }
            }
            propertiesMap.forEach((k,v)->{
                if (v.startsWith("${") && v.endsWith("}")) {
                    p.put(k, config.getProperty(v.substring(2, v.length() - 1), v));
                } else {
                    p.put(k, v);
                }
            });
            setProperties(p);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        super.postProcessBeanDefinitionRegistry(registry);
        //如果没有注册过接口，就注册默认的Mapper接口
        this.mapperHelper.ifEmptyRegisterDefaultInterface();
        String[] names = registry.getBeanDefinitionNames();
        GenericBeanDefinition definition;
        for (String name : names) {
            BeanDefinition beanDefinition = registry.getBeanDefinition(name);
            if (beanDefinition instanceof GenericBeanDefinition) {
                definition = (GenericBeanDefinition) beanDefinition;
                if (StringUtil.isNotEmpty(definition.getBeanClassName())
                        && definition.getBeanClassName().equals("org.mybatis.spring.mapper.MapperFactoryBean")) {
                    definition.setBeanClass(MapperFactoryBean.class);
                    definition.getPropertyValues().add("mapperHelper", this.mapperHelper);
                }
            }
        }
    }
}