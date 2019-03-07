/**
 * Copyright (c) 2016. ZheJiang JingRui Company. All right reserved.
 * Project Name:HmapParent
 * Package Name:hmap.core.beans 
 * Date:2016/8/12 0012
 * Create By:zongyun.zhou@jingrui.com
 *
 */
package com.jingrui.jrap.intergration.beans;

import net.sf.json.JSONObject;

public abstract class JrapTransferDataMapper {
    public abstract String requestDataMap(JSONObject params);

    public abstract String responseDataMap(String params);

}
