/*
 * *
 *  @file com.maddyhome.idea.copyright.pattern.JavaCopyrightVariablesProvider$1@5bbdd058$
 *  @CopyRight (C) 2018 ZheJiangJingRui Co. Ltd.
 *  @brief JingRui Application Platform
 *  @author $name$
 *  @email yulong.yuan@jr-info.cn
 *  @date $date$
 * /
 */

package com.jingrui.jrap.fnd.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;

public class GetLatAndLngByBaidu {
    public static final String AK = "1YGxFcoI4mGwuNpnB1uMEsQOhYMKstop";
    public static final String url = "http://api.map.baidu.com/geocoder/v2/?address=";

    public static Map<String, String> getLatitude(String address) {
        try {
            //1, 将地址转换成utf-8的16进制
            address = URLEncoder.encode(address, "UTF-8");

            //2, 拼写发送http请求的url，注意需使用第一步申请的ak。
            //3, 接收http请求返回的数据（支持json和xml格式）本次采用json形式
            URL resjson = new URL(url + address + "&output=json&ak=" + AK);
            BufferedReader in = new BufferedReader(new InputStreamReader(resjson.openStream()));

            String res;
            StringBuilder sb = new StringBuilder("");
            while ((res = in.readLine()) != null) {
                sb.append(res.trim());
            }
            in.close();
            String str = sb.toString();

            Map<String, String> map = null;
            if (str != null) {
                int lngStart = str.indexOf("lng\":");
                int lngEnd = str.indexOf(",\"lat");
                int latEnd = str.indexOf("},\"precise");
                if (lngStart > 0 && lngEnd > 0 && latEnd > 0) {
                    String lng = str.substring(lngStart + 5, lngEnd);
                    String lat = str.substring(lngEnd + 7, latEnd);
                    map = new HashMap<String, String>();
                    map.put("lng", lng);
                    map.put("lat", lat);
                    return map;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String args[]){
        String address = "湖北省鄂州市华容区葛店镇袁家咀";
        Map<String,String> map = GetLatAndLngByBaidu.getLatitude(address);
        System.out.println(map.get("lng") + "," + map.get("lat"));
    }
}
