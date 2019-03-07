package com.jingrui.jrap.cache.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jingrui.jrap.function.dto.ResourceCustomization;
import com.jingrui.jrap.function.mapper.ResourceCustomizationMapper;

/**
 * 
 * @author njq.niu@jingrui.com
 *
 */
public class ResourceCustomizationCache extends HashStringRedisCache<String[]> {
    
    private String resourceCustomizationAllQuerySqlId = ResourceCustomizationMapper.class.getName() + ".selectAllResourceCustomizations";
    private String resourceCustomizationQuerySqlId = ResourceCustomizationMapper.class.getName() + ".loadResourceCustomizationsByResourceId";
    
    
    private final Logger logger = LoggerFactory.getLogger(ResourceCustomizationCache.class);

    {
        setLoadOnStartUp(true);
        setType(String[].class);
    }

    @Override
    public String[] getValue(String key) {
        return super.getValue(key);
    }

 
    @Override
    public void setValue(String key, String[] values) {
        super.setValue(key, values);
    }
    
    @SuppressWarnings("unchecked")
    protected void initLoad() {
        Map<String, List<String>> resourceCustomization = new HashMap<>();
        try (SqlSession sqlSession = getSqlSessionFactory().openSession()) {
            sqlSession.select(resourceCustomizationAllQuerySqlId, (resultContext) -> {
                Map<String, Object> value = (Map<String, Object>) resultContext.getResultObject();
                String resourceId = "" + value.get("RESOURCE_ID");
                List<String> sets = resourceCustomization.get(resourceId);
                if (sets == null) {
                    sets = new ArrayList<>();
                    resourceCustomization.put(resourceId, sets);
                }
                String url = (String) value.get("URL");
                sets.add(url);
            });

            resourceCustomization.forEach((k, v) -> {
                setValue(k, v.toArray(new String[v.size()]));
            });
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error("init resource customization cache exception: ", e);
            }
        }
    }
    
    
    public void load(String resourceId){
        Map<String, List<String>> resourceCustomization = new HashMap<>();
        super.remove(resourceId);
        try (SqlSession sqlSession = getSqlSessionFactory().openSession()) {
            sqlSession.select(resourceCustomizationQuerySqlId, resourceId, (resultContext) -> {
                ResourceCustomization value = (ResourceCustomization) resultContext.getResultObject();
                List<String> sets = resourceCustomization.get(resourceId);
                if (sets == null) {
                    sets = new ArrayList<>();
                    resourceCustomization.put(resourceId, sets);
                }
                sets.add(value.getUrl());
            });

            resourceCustomization.forEach((k, v) -> {
                setValue(k, v.toArray(new String[v.size()]));
            });
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error("load resource customization cache exception: ", e);
            }
        }
    }

}
