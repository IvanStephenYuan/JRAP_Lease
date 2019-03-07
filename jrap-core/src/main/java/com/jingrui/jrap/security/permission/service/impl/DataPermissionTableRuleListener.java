package com.jingrui.jrap.security.permission.service.impl;

import com.jingrui.jrap.message.IMessageConsumer;
import com.jingrui.jrap.message.TopicMonitor;
import com.jingrui.jrap.security.permission.dto.DataPermissionTableRule;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author jialong.zuo@jingrui.com
 * @date 2017/9/13.
 */

@Component
@TopicMonitor(channel = {"dataPermission.tableRuleUpdate", "dataPermission.tableRuleRemove"})
public class DataPermissionTableRuleListener implements IMessageConsumer<DataPermissionTableRule> {
    @Autowired
    DataPermissionCacheContainer container;

    @Override
    public void onMessage(DataPermissionTableRule message, String pattern) {
        if (StringUtils.equals(pattern, "dataPermission.tableRuleUpdate")) {
            container.updateMaskTableRuleMap(message, container.CACHE_UPDATE);
        } else if (StringUtils.equals(pattern, "dataPermission.tableRuleRemove")) {
            container.updateMaskTableRuleMap(message, container.CACHE_DELETE);

        }
    }
}
