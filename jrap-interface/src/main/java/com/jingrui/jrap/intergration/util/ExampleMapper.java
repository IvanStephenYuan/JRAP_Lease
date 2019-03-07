package com.jingrui.jrap.intergration.util;

import com.jingrui.jrap.intergration.beans.JrapTransferDataMapper;
import net.sf.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

/**
 * Copyright (c) 2016. ZheJiang JingRui Company. All right reserved.
 * Project Name:HmapParent
 * Package Name:hmap.core.util
 * Date:2016/8/18
 * Create By:jiguang.sun@jingrui.com
 */


/*
* 映射类，处理项目不同参数格式问题
* */
public class ExampleMapper extends JrapTransferDataMapper {

    @Override
    public String requestDataMap(JSONObject params) {
        String resultData = "";
        if (params != null && params.size() > 0) {
            Iterator iterator = params.keys();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                try {
                    resultData += key + "=" + URLEncoder.encode(params.get(key).toString(), "utf-8") + "&";
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

        }

        return resultData.substring(0, resultData.length() - 1);
    }

    @Override
    public String responseDataMap(String params) {
        Map<String, Object> map;
        try {
            map = JSONAndMap.xml2map(params);
        } catch (Exception e) {
            throw new RuntimeException();
        }
        JSONObject jsonObject = new JSONObject();
        if (map != null && map.size() > 0) {
            jsonObject = JSONObject.fromObject(map);
        }

        return jsonObject.toString();
    }
}
