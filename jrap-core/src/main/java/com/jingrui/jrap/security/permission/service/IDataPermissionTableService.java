package com.jingrui.jrap.security.permission.service;

import com.jingrui.jrap.core.ProxySelf;
import com.jingrui.jrap.system.service.IBaseService;
import com.jingrui.jrap.security.permission.dto.DataPermissionTable;

import java.util.List;

/**
 * @author jialong.zuo@jingrui.com on 2017/12/8
 */
public interface IDataPermissionTableService extends IBaseService<DataPermissionTable>, ProxySelf<IDataPermissionTableService> {

    /**通过规则删除表
     * @param dataMaskTables 将要删除的表规则
     */
    void removeTableWithRule(List<DataPermissionTable> dataMaskTables);
}