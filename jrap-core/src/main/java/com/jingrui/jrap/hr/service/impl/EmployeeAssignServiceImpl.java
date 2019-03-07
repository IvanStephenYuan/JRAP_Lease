package com.jingrui.jrap.hr.service.impl;

import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.hr.dto.EmployeeAssign;
import com.jingrui.jrap.hr.mapper.EmployeeAssignMapper;
import com.jingrui.jrap.hr.service.IEmployeeAssignService;
import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 员工岗位分配服务接口实现.
 *
 * @author yuliao.chen@jingrui.com
 */
@Service
public class EmployeeAssignServiceImpl extends BaseServiceImpl<EmployeeAssign> implements IEmployeeAssignService {

    @Autowired
    private EmployeeAssignMapper employeeAssignMapper;

    @Override
    public List<EmployeeAssign> selectByEmployeeId(IRequest requestContext, Long employeeId, int page, int pagesize) {
        return employeeAssignMapper.selectByEmployeeId(employeeId);
    }

    @Override
    public Long getCompanyByEmployeeId(Long employeeId) {
        return employeeAssignMapper.getCompanyByEmployeeId(employeeId);
    }

}
