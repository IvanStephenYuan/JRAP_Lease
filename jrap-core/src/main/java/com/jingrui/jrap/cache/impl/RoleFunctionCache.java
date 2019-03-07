/*
 * #{copyright}#
 */
package com.jingrui.jrap.cache.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jingrui.jrap.function.mapper.RoleFunctionMapper;

/**
 * @author njq.niu@jingrui.com
 *
 * 2016年1月27日
 */
public class RoleFunctionCache extends HashStringRedisCache<Long[]> {
    
    private String roleFunctionQuerySqlId = RoleFunctionMapper.class.getName() + ".selectAllRoleFunctions";

    private final Logger logger = LoggerFactory.getLogger(RoleFunctionCache.class);

    {
        setLoadOnStartUp(true);
        setType(Long[].class);
    }


    /**
     * key 为roleId.
     *
     * @param key
     *            roleId
     * @return values
     */
    @Override
    public Long[] getValue(String key) {
        return super.getValue(key);
    }

    /**
     *
     * @param key
     *            code.lang
     * @param values values
     */
    @Override
    public void setValue(String key, Long[] values) {
        super.setValue(key, values);
    }

    @SuppressWarnings("unchecked")
    protected void initLoad() {
        Map<String, Set<Long>> roleFunctions = new HashMap<>();
        try (SqlSession sqlSession = getSqlSessionFactory().openSession()) {
            sqlSession.select(roleFunctionQuerySqlId, (resultContext) -> {
                Map<String, Object> value = (Map<String, Object>) resultContext.getResultObject();
                String roleId = "" + value.get("ROLE_ID");
                Set<Long> sets = roleFunctions.get(roleId);
                if (sets == null) {
                    sets = new HashSet<>();
                    roleFunctions.put(roleId, sets);
                }
                Long resourceId = ((Number) value.get("FUNCTION_ID")).longValue();
                sets.add(resourceId);
            });

            roleFunctions.forEach((k, v) -> {
                setValue(k, v.toArray(new Long[v.size()]));
            });
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error("init role function cache exception: ", e);
            }
        }
    }
}
