package com.jingrui.jrap.security.permission.service.impl;

import com.jingrui.jrap.message.IMessagePublisher;
import com.jingrui.jrap.security.permission.dto.DataPermissionTableRule;
import com.jingrui.jrap.security.permission.mapper.DataPermissionTableRuleMapper;
import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jingrui.jrap.security.permission.dto.DataPermissionTable;
import com.jingrui.jrap.security.permission.service.IDataPermissionTableService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author jialong.zuo@jingrui.com
 */
@Service
public class DataPermissionTableServiceImpl extends BaseServiceImpl<DataPermissionTable> implements IDataPermissionTableService {

    @Autowired
    DataPermissionTableRuleMapper dataPermissionTableRuleMapper;

    @Autowired
    IMessagePublisher iMessagePublisher;

    @Override
    public void removeTableWithRule(List<DataPermissionTable> dataMaskTables) {
        batchDelete(dataMaskTables);
        dataMaskTables.forEach(v -> {
            DataPermissionTableRule rule = new DataPermissionTableRule();
            rule.setTableId(v.getTableId());
            dataPermissionTableRuleMapper.delete(rule);
            iMessagePublisher.publish("dataPermission.tableRemove", v);
        });
    }
}