package com.jingrui.jrap.security.permission.service.impl;

import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.message.IMessagePublisher;
import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jingrui.jrap.security.permission.dto.DataPermissionTableRule;
import com.jingrui.jrap.security.permission.service.IDataPermissionTableRuleService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author jialong.zuo@jingrui.com
 */
@Service
public class DataPermissionTableRuleServiceImpl extends BaseServiceImpl<DataPermissionTableRule> implements IDataPermissionTableRuleService {

    @Autowired
    IMessagePublisher iMessagePublisher;

    @Override
    public void removeRule(List<DataPermissionTableRule> list) {
        batchDelete(list);
        updateCache(list, "dataPermission.tableRuleRemove");
    }

    @Override
    public List<DataPermissionTableRule> updateRule(IRequest request, List<DataPermissionTableRule> list) {
        List<DataPermissionTableRule> dto = batchUpdate(request, list);
        updateCache(list, "dataPermission.tableRuleUpdate");
        return dto;
    }

    private void updateCache(List<DataPermissionTableRule> list, String channel) {
        list.forEach(v -> {
            iMessagePublisher.publish(channel, v);
        });
    }
}