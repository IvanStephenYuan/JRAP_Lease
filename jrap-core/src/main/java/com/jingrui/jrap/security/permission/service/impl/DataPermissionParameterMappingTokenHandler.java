package com.jingrui.jrap.security.permission.service.impl;

import com.jingrui.jrap.core.IRequest;
import org.apache.ibatis.parsing.TokenHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @author jialong.zuo@jingrui.com
 * @date 2017/9/5.
 */
public class DataPermissionParameterMappingTokenHandler implements TokenHandler {

    private IRequest iRequest;

    public DataPermissionParameterMappingTokenHandler(IRequest iRequest) {
        this.iRequest = iRequest;
    }

    private static Logger log = LoggerFactory.getLogger(DataPermissionParameterMappingTokenHandler.class);

    @Override
    public String handleToken(String content) {
        String value;
        value = getValueByBean(content, iRequest);
        if (!"".equals(value)) {
            return value;
        }
        return "";
    }

    public String getValueByBean(String key, Object object) {
        String value = "";
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.getName().equalsIgnoreCase(key)) {
                field.setAccessible(true);
                try {
                    Object fieldValue = field.get(object);
                    //类型处理demo
                    if (fieldValue instanceof List) {
                        List fe = (List) fieldValue;
                        for (int i = 0; i < fe.size(); i++) {
                            value += fe.get(i).toString();
                            if (i != fe.size() - 1) {
                                value += ",";
                            }
                        }
                    } else {
                        value = fieldValue.toString();
                    }
                } catch (IllegalAccessException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }

        return value;
    }

}
