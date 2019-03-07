/*
 * Copyright ZheJiang JingRui Co.,Ltd.
 */

package com.jingrui.jrap.extensible.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.jingrui.jrap.core.AppContextInitListener;
import com.jingrui.jrap.core.annotation.ServiceListener;
import com.jingrui.jrap.core.util.CommonUtils;
import com.jingrui.jrap.extensible.base.IServiceListener;

/**
 * @author shengyang.zhou@jingrui.com
 */
@Component
public class ServiceListenerManager implements AppContextInitListener {

    Map<Class<?>, List<IServiceListener>> listenerMapping = new HashMap<>();

    @Override
    public void contextInitialized(ApplicationContext applicationContext) {
        Map<String, IServiceListener> map = applicationContext.getBeansOfType(IServiceListener.class);
        map.forEach((k, v) -> {
            ServiceListener annotation = v.getClass().getAnnotation(ServiceListener.class);
            if (annotation == null) {
                System.err.println(v + " has no @ServiceListener");
            } else {
                Class<?> clazz = annotation.target();
                List<IServiceListener> list = listenerMapping.get(clazz);
                if (list == null) {
                    list = new ArrayList<>();
                    listenerMapping.put(clazz, list);
                }
                list.add(v);
            }
        });

        listenerMapping.forEach((k, v) -> {
            v.sort((o1, o2) -> {
                ServiceListener a1 = o1.getClass().getAnnotation(ServiceListener.class);
                ServiceListener a2 = o1.getClass().getAnnotation(ServiceListener.class);
                if (CommonUtils.in(a2, a1.before())) {
                    return -1;
                }
                if (CommonUtils.in(a1, a2.before())) {
                    return 1;
                }
                if (CommonUtils.in(a2, a1.after())) {
                    return 1;
                }
                if (CommonUtils.in(a1, a2.after())) {
                    return -1;
                }
                return 0;
            });
        });

    }

    public List<IServiceListener> getRegisteredServiceListener(Class<?> clazz) {
        return listenerMapping.get(clazz);
    }
}
