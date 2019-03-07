/**
 * Copyright (c) 2016. ZheJiang JingRui Company. All right reserved. Project Name:HmapParent
 * Package Name:hmap.core.hms.service.impl Date:2016/8/12 0012 Create By:zongyun.zhou@jingrui.com
 *
 */
package com.jingrui.jrap.intergration.service;

import com.jingrui.jrap.intergration.dto.JrapInterfaceHeader;
import net.sf.json.JSONObject;

public interface IJrapApiService {
   JSONObject invoke(JrapInterfaceHeader hapInterfaceHeader, JSONObject inbound) throws Exception;
}
