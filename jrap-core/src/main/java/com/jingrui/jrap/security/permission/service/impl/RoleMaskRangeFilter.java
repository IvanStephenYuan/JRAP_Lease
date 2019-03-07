package com.jingrui.jrap.security.permission.service.impl;

import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.security.permission.dto.DataPermissionTableRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * @author jialong.zuo@jingrui.com
 * @date 2017/8/30.
 */
@Component
public class RoleMaskRangeFilter extends DataPermissionRangeFilter {

    @Autowired
    DataPermissionCacheContainer container;

    private final String maskRange = "LOV_ROLE";

    @Override
    public void doFilter(IRequest iRequest, String tableName, Map data) throws ExecutionException {
        if (iRequest.getAllRoleId().length == 0) {
            return;
        }

        List<DataPermissionTableRule> ruleCodeList = container.getTableRule(tableName);
        if (ruleCodeList.size() == 0) {
            return;
        }
        Long[] roleIds = iRequest.getAllRoleId();
        for (Long roleId : roleIds) {
            setRuleDetail(ruleCodeList, maskRange, roleId.toString(), container, data);
        }
    }

}
