package com.jingrui.jrap.activiti.service.impl;

import com.jingrui.jrap.activiti.custom.IActivitiBean;
import com.jingrui.jrap.activiti.service.IActivitiEntityService;
import com.jingrui.jrap.activiti.util.ActivitiUtils;
import com.jingrui.jrap.hr.dto.Employee;
import com.jingrui.jrap.hr.dto.Position;
import com.jingrui.jrap.hr.mapper.EmployeeMapper;
import com.jingrui.jrap.hr.mapper.PositionMapper;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author shengyang.zhou@jingrui.com
 */
@Service
public class ActivitiEntityServiceImpl implements IActivitiEntityService, IActivitiBean {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private PositionMapper positionMapper;

    @Override
    public String getDirector(String employeeCode) {
        List<Employee> director = employeeMapper.getDirector(employeeCode);
        if (director == null || director.isEmpty()) {
            return "ADMIN";
        }
        Set<String> directorCode = new HashSet<>();
        for (Employee employee : director) {
            directorCode.add(employee.getEmployeeCode());
        }
        return StringUtils.collectionToCommaDelimitedString(directorCode);
    }

    @Override
    public String getDeptDirector(String employeeCode) {
        List<Employee> director = employeeMapper.getDeptDirector(employeeCode);
        if (director == null || director.isEmpty()) {
            return "ADMIN";
        }
        Set<String> directorCode = new HashSet<>();
        for (Employee employee : director) {
            directorCode.add(employee.getEmployeeCode());
        }
        return StringUtils.collectionToCommaDelimitedString(directorCode);
    }

    @Override
    public boolean isMemberOfGroup(String userId, String groupId) {
        return true;
    }

    @Override
    public String getName(String userId) {
        return getEmployee(userId).getFirstName();
    }

    @Override
    public String getEmail(String userId) {
        return getEmployee(userId).getEmail();
    }

    @Override
    public UserEntity getEmployee(String employeeCode) {
        return ActivitiUtils.toActivitiUser(employeeMapper.queryByCode(employeeCode));
    }

    @Override
    public String getGroupName(String groupId) {
        Position pos = positionMapper.getPositionByCode(groupId);
        if (pos == null) {
            return groupId;
        }
        return pos.getName();
    }

    @Override
    public String getEmployeeCode(String employeeCode) {
        return employeeCode;
    }

    @Override
    public List<String> getPositionEmp(String positionCode) {
        return employeeMapper.selectByPostionCode(positionCode).stream()
                .map(t -> t.getEmployeeCode()).collect(Collectors.toList());
    }

    @Override
    public List<String> getRoleEmp(String roleCode) {
        List<Employee> employees = employeeMapper.selectByRoleCode(roleCode);
        return employees.stream().map(t -> t.getEmployeeCode()).collect(Collectors.toList());
    }


    @Override
    public String getBeanName() {
        return "empService";
    }
}
