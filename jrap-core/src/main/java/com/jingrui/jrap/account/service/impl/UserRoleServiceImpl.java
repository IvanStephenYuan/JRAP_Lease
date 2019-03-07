package com.jingrui.jrap.account.service.impl;

import com.jingrui.jrap.account.dto.UserRole;
import com.jingrui.jrap.account.mapper.UserRoleMapper;
import com.jingrui.jrap.account.service.IUserRoleService;
import com.jingrui.jrap.cache.impl.UserCache;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.system.dto.DTOStatus;
import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户角色分配服务接口 - 实现.
 *
 * @author xiawang.liu@jingrui.com
 * @author lijian.yin@jingrui.com
 */

@Service
public class UserRoleServiceImpl extends BaseServiceImpl<UserRole> implements IUserRoleService {

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private UserCache userCache;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<UserRole> batchUpdate(IRequest request, List<UserRole> list) {
        for (UserRole userRole : list) {
            switch (userRole.get__status()) {
                case DTOStatus.ADD:
                    self().insertSelective(request, userRole);
                    break;
                case DTOStatus.DELETE:
                    self().deleteByPrimaryKey(userRole);
                    break;
                default:
                    break;
            }
            userCache.remove(userRole.getUserName());
        }
        return list;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteByPrimaryKey(UserRole record) {
        if (record.getSurId() != null) {
            return super.deleteByPrimaryKey(record);
        }
        int updateCount = userRoleMapper.deleteByRecord(record);
        checkOvn(updateCount, record);
        return updateCount;
    }
}