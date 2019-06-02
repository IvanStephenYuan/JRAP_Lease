package com.jingrui.jrap.cache.impl;

import com.jingrui.jrap.cache.CacheResolve;
import com.jingrui.jrap.core.components.ApplicationContextHelper;
import com.jingrui.jrap.core.impl.RequestHelper;
import com.jingrui.jrap.mybatis.common.query.JoinLov;
import com.jingrui.jrap.system.dto.Lov;
import com.jingrui.jrap.system.service.ILovService;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jialong.zuo@jingrui.com on 2018/1/10.
 */
public class LovRedisCacheGroupResolve extends CacheResolve {

    LovCache lovCache;
    ILovService lovService;

    @Override
    public Object resolve(Object cacheEntity, Object resultMap, String lang) throws NoSuchFieldException, IllegalAccessException {

        if(lovCache == null) {
            lovCache= (LovCache) ApplicationContextHelper.getApplicationContext().getBean("lovCache");
        }
        if(lovService == null){
            lovService= ApplicationContextHelper.getApplicationContext().getBean(ILovService.class);
        }
        JoinLov joinCode = (JoinLov) cacheEntity;
        Field joinKeyField = resultMap.getClass().getDeclaredField(joinCode.joinKey());
        joinKeyField.setAccessible(true);
        Object joinKey= joinKeyField.get(resultMap);

        if(joinKey == null || joinKey ==""){
            return null;
        }


        Field lovCodeField;
        String lovCode;
        if(!joinCode.dynamicLovColumn().isEmpty()){
            lovCodeField = resultMap.getClass().getDeclaredField(joinCode.dynamicLovColumn());
            lovCodeField.setAccessible(true);
            Object o=lovCodeField.get(resultMap);
            if(o == null){
                return null;
            }
            lovCode = o.toString();
        }else if(!joinCode.lovCode().isEmpty()){
            lovCode = joinCode.lovCode();
        }else{
            throw new RuntimeException("dynamicLovColumn 或 lovCode 不能同时为空");

        }


        Lov lov=lovCache.getValue(lovCode);
        Map map = new HashMap();
        map.put(lov.getValueField(), joinKey);
        List<?> list=lovService.selectDatas(RequestHelper.getCurrentRequest(true), lovCode, map, 1, 10);

        if(list.size()!=0) {
            Object o1 =list.get(0);
            if(o1 instanceof Map){
                Map o=(Map) o1;
                return o.get(lov.getTextField());
            }else{
                Field field = o1.getClass().getDeclaredField(lov.getTextField());
                field.setAccessible(true);
                return field.get(o1);
            }
        }else{
            return null;
        }
    }
}
