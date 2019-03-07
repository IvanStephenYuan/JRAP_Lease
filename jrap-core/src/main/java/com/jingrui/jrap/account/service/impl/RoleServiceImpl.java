package com.jingrui.jrap.account.service.impl;

import com.github.pagehelper.PageHelper;
import com.jingrui.jrap.account.dto.Role;
import com.jingrui.jrap.account.dto.RoleExt;
import com.jingrui.jrap.account.dto.User;
import com.jingrui.jrap.account.exception.RoleException;
import com.jingrui.jrap.account.mapper.RoleMapper;
import com.jingrui.jrap.account.mapper.UserRoleMapper;
import com.jingrui.jrap.account.service.IRole;
import com.jingrui.jrap.account.service.IRoleService;
import com.jingrui.jrap.account.service.IUserService;
import com.jingrui.jrap.cache.Cache;
import com.jingrui.jrap.cache.CacheManager;
import com.jingrui.jrap.core.BaseConstants;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.core.annotation.StdWho;
import com.jingrui.jrap.function.service.IRoleFunctionService;
import com.jingrui.jrap.mybatis.common.Criteria;
import com.jingrui.jrap.mybatis.common.query.Comparison;
import com.jingrui.jrap.mybatis.common.query.WhereField;
import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色服务接口 - 实现类.
 *
 * @author shengyang.zhou@jingrui.com
 * @author njq.niu@jingrui.com
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<Role> implements IRoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private IRoleFunctionService roleFunctionService;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private IUserService iUserService;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Role> selectRoles(IRequest request, Role role, int pageNum, int pageSize) {
        Criteria criteria = new Criteria(role);
        criteria.where(Role.FIELD_ROLE_ID, new WhereField(Role.FIELD_ROLE_CODE, Comparison.LIKE),
                new WhereField(Role.FIELD_ROLE_NAME, Comparison.LIKE),
                new WhereField(Role.FIELD_ROLE_DESCRIPTION, Comparison.LIKE));
        return super.selectOptions(request, role, criteria, pageNum, pageSize);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<IRole> selectRoleNotUserRoles(IRequest request, RoleExt roleExt, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        List list = roleMapper.selectRoleNotUserRoles(roleExt);
        return list;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<IRole> selectRolesByUser(IRequest requestContext, User user) {
        List rootRoles = roleMapper.selectUserRoles(user.getUserId());
        return rootRoles;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<IRole> selectUserRolesByUserId(Long userId) {
        List rootRoles = roleMapper.selectUserRolesByUserId(userId);
        return rootRoles;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public void checkUserRoleExists(Long userId, Long roleId) throws RoleException {
        if (roleMapper.selectUserRoleCount(userId, roleId) != 1) {
            throw new RoleException(RoleException.MSG_INVALID_USER_ROLE, RoleException.MSG_INVALID_USER_ROLE, null);
        }
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public IRole selectRoleByCode(String roleCode) {
        IRole role = getRoleCache().getValue(roleCode);
        if (role == null) {
            Role record = new Role();
            record.setRoleCode(roleCode);
            role = roleMapper.selectOne(record);
            if (role != null) {
                getRoleCache().setValue(roleCode, role);
            }
        }
        return role;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<IRole> selectActiveRolesByUser(IRequest requestContext, User user) {
        if (null == user.getRoleCode()) {
            user = iUserService.selectByUserName(user.getUserName());
        }

        List<IRole> roles = new ArrayList();
        for (String roleCode : user.getRoleCode()) {
            roles.add(selectRoleByCode(roleCode));
        }

        return roles.stream().filter(role -> role.isActive()).collect(Collectors.toList());
    }

    @Override
    protected boolean useSelectiveUpdate() {
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteByPrimaryKey(Role record) {
        int ret = super.deleteByPrimaryKey(record);
        userRoleMapper.deleteByRoleId(record.getRoleId());
        roleFunctionService.removeRoleFunctionByRoleId(record.getRoleId());
        getRoleCache().remove(record.getRoleCode());
        return ret;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Role updateByPrimaryKey(IRequest request, @StdWho Role record) {
        int count = roleMapper.updateByPrimaryKey(record);
        checkOvn(count, record);
        getRoleCache().remove(record.getRoleCode());
        getRoleCache().setValue(record.getRoleCode(), record);
        return record;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Role insertSelective(IRequest request, Role record) {
        int count = roleMapper.insertSelective(record);
        checkOvn(count, record);
        getRoleCache().setValue(record.getRoleCode(), record);
        return record;
    }

    /**
     * 获取角色redis缓存.
     *
     * @return 角色redis缓存
     */
    private Cache<IRole> getRoleCache() {
        return cacheManager.getCache(BaseConstants.CACHE_ROLE_CODE);
    }
}
