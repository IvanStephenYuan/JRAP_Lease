package com.jingrui.jrap.cache;

import com.jingrui.jrap.mybatis.common.query.JoinCache;
import com.jingrui.jrap.mybatis.common.query.JoinCode;
import com.jingrui.jrap.mybatis.common.query.JoinLov;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by jialong.zuo@jingrui on 2017/6/1.
 */
public abstract class CacheResolve {

    public abstract Object resolve(Object cacheEntity, Object resultMap, String lang) throws NoSuchFieldException, IllegalAccessException;

    protected Object getJoinKey(Object cacheEntity, Object resultMap) throws NoSuchFieldException, IllegalAccessException {
        Field field = null;
        if (cacheEntity instanceof JoinCache) {
            JoinCache joinCache = (JoinCache) cacheEntity;
            field = resultMap.getClass().getDeclaredField(joinCache.joinKey());
        } else if (cacheEntity instanceof JoinCode) {
            JoinCode joinCode = (JoinCode) cacheEntity;
             field = resultMap.getClass().getDeclaredField(joinCode.joinKey());
        } else if(cacheEntity instanceof JoinLov){
            JoinLov joinCode = (JoinLov) cacheEntity;
             field = resultMap.getClass().getDeclaredField(joinCode.joinKey());
        }
        field.setAccessible(true);
        return field.get(resultMap);
    }
}
