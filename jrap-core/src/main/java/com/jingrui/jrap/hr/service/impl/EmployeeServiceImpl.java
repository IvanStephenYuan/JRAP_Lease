package com.jingrui.jrap.hr.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.StringUtil;
import com.jingrui.jrap.account.dto.User;
import com.jingrui.jrap.account.dto.UserRole;
import com.jingrui.jrap.account.mapper.UserRoleMapper;
import com.jingrui.jrap.account.service.IUserRoleService;
import com.jingrui.jrap.account.service.IUserService;
import com.jingrui.jrap.cache.impl.UserCache;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.hr.dto.Employee;
import com.jingrui.jrap.hr.dto.UserAndRoles;
import com.jingrui.jrap.hr.mapper.EmployeeAssignMapper;
import com.jingrui.jrap.hr.mapper.EmployeeMapper;
import com.jingrui.jrap.hr.service.IEmployeeService;
import com.jingrui.jrap.message.IMessagePublisher;
import com.jingrui.jrap.mybatis.common.Criteria;
import com.jingrui.jrap.system.dto.DTOStatus;
import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 员工服务接口实现.
 *
 * @author yuliao.chen@jingrui.com
 */
@Service
public class EmployeeServiceImpl extends BaseServiceImpl<Employee> implements IEmployeeService {

    @Autowired
    EmployeeMapper employeeMapper;

    @Autowired
    private IMessagePublisher messagePublisher;

    @Autowired
    private IUserRoleService userRoleService;

    @Autowired
    private IUserService userService;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private EmployeeAssignMapper employeeAssignMapper;

    @Autowired
    private UserCache userCache;

    @Override
    public List<Employee> submit(IRequest request, List<Employee> list) {
        self().batchUpdate(request, list);
        for (Employee e : list) {
            messagePublisher.publish("employee.change", e);
        }
        return list;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Employee> batchUpdate(IRequest request, List<Employee> list) {
        Criteria criteria = new Criteria();
        criteria.update(Employee.FIELD_BORN_DATE, Employee.FIELD_CERTIFICATE_ID, Employee.FIELD_CERTIFICATE_TYPE,
                Employee.FIELD_NAME, Employee.FIELD_EFFECTIVE_END_DATE, Employee.FIELD_EFFECTIVE_START_DATE,
                Employee.FIELD_EMAIL, Employee.FIELD_ENABLED_FLAG, Employee.FIELD_GENDER, Employee.FIELD_JOIN_DATE,
                Employee.FIELD_MOBIL, Employee.FIELD_STATUS);
        criteria.updateExtensionAttribute();
        for (Employee employee : list) {
            switch (employee.get__status()) {
                case DTOStatus.ADD:
                    self().insertSelective(request, employee);
                    break;
                case DTOStatus.UPDATE:
                    self().updateByPrimaryKeyOptions(request, employee, criteria);
                    break;
                case DTOStatus.DELETE:
                    self().deleteByPrimaryKey(employee);
                    break;
                default:
                    break;
            }
        }
        return list;
    }

    @Override
    public List<Employee> queryAll(IRequest requestContext, Employee employee, int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        return employeeMapper.queryAll(employee);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createUserByEmployee(IRequest request, UserAndRoles roles) {
        User u = userService.insertSelective(request, roles.getUser());
        if (CollectionUtils.isNotEmpty(roles.getRoles())) {
            Long userId = u.getUserId();
            List<UserRole> roles1 = roles.getRoles();
            for (UserRole role : roles1) {
                role.setUserId(userId);
                userRoleService.insertSelective(request, role);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUser(IRequest irequest, UserAndRoles userAndRoles,String userName) {
        if (userAndRoles.getRoles() != null) {
            List<UserRole> newRoles = userAndRoles.getRoles();
            User user = userAndRoles.getUser();
            userRoleMapper.deleteByUserId(user.getUserId());
            for (UserRole role : newRoles) {
                role.setUserId(user.getUserId());
                userRoleService.insertSelective(irequest, role);
            }
            userCache.remove(userName.toLowerCase());
        }
    }

    @Override
    public Employee queryInfoByCode(String employeeCode) {
        return employeeMapper.queryInfoByCode(employeeCode);
    }

    @Override
    public List<Employee> selectByPostionCode(String code) {
        return employeeMapper.selectByPostionCode(code);
    }

    @Override
    public List<User> selectExistingUser(IRequest request, User user) {
        if (user != null && StringUtil.isNotEmpty(user.getUserName())) {
            user.setUserName(user.getUserName().toLowerCase());
        }
        Criteria criteria = new Criteria(user);
        criteria.where(User.FIELD_USER_NAME);
        criteria.select(User.FIELD_USER_ID, User.FIELD_USER_NAME, User.FIELD_EMPLOYEE_ID,
                User.FIELD_EMPLOYEE_NAME, User.FIELD_EMPLOYEE_CODE, User.FIELD_EMAIL, User.FIELD_PHONE,
                User.FIELD_STATUS, User.FIELD_START_ACTIVE_DATE, User.FIELD_END_ACTIVE_DATE, User.FIELD_DESCRIPTION);
        return userService.selectOptions(request, user, criteria);
    }

    @Override
    public List<Employee> getDirector(String employeeCode) {
        return employeeMapper.getDirector(employeeCode);
    }

    @Override
    public List<Employee> getDeptDirector(String employeeCode) {
        return employeeMapper.getDeptDirector(employeeCode);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteByPrimaryKey(Employee employee) {
        int result = super.deleteByPrimaryKey(employee);
        employeeAssignMapper.deleteByEmployeeId(employee.getEmployeeId());
        return result;
    }
}
