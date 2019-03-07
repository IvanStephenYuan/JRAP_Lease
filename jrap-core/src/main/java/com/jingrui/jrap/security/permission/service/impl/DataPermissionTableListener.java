package com.jingrui.jrap.security.permission.service.impl;

import com.jingrui.jrap.message.IMessageConsumer;
import com.jingrui.jrap.message.TopicMonitor;
import com.jingrui.jrap.security.permission.dto.DataPermissionTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author jialong.zuo@jingrui.com
 * @date 2017/9/13.
 */
@Service
@TopicMonitor(channel = {"dataPermission.tableRemove"})
public class DataPermissionTableListener implements IMessageConsumer<DataPermissionTable> {

    @Autowired
    DataPermissionCacheContainer container;

    @Override
    public void onMessage(DataPermissionTable message, String pattern) {

        container.removeMaskTableRuleMap(message.getTableName());
    }
}
