package com.jingrui.jrap.security.permission.service.impl;

import com.jingrui.jrap.message.IMessageConsumer;
import com.jingrui.jrap.message.TopicMonitor;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author jialong.zuo@jingrui.com on 2017/9/13.
 */

@Component
@TopicMonitor(channel = {"dataPermission.ruleRefresh", "dataPermission.ruleRemove"})
public class DataPermissionRuleRefreshListener implements IMessageConsumer<Long> {

    @Autowired
    DataPermissionCacheContainer container;

    @Override
    public void onMessage(Long message, String pattern) {
        if (StringUtils.equals(pattern, "dataPermission.ruleRefresh")) {
            container.updateMaskRuleMap(message);
        } else if (StringUtils.equals(pattern, "dataPermission.ruleRemove")) {
            container.removeMaskRuleMap(message);
        }
    }
}
