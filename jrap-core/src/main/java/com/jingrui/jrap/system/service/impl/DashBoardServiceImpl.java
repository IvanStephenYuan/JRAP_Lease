package com.jingrui.jrap.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.system.dto.DashBoard;
import com.jingrui.jrap.system.dto.UserDashboard;
import com.jingrui.jrap.system.mapper.DashBoardMapper;
import com.jingrui.jrap.system.mapper.UserDashboardMapper;
import com.jingrui.jrap.system.service.IDashBoardService;

/**
 * @author zhizheng.yang@jingrui.com
 */

@Service
@Transactional
public class DashBoardServiceImpl extends BaseServiceImpl<DashBoard> implements IDashBoardService {

    @Autowired
    private DashBoardMapper dashBoardMapper;
	
	@Autowired
	private UserDashboardMapper userDashboardMapper;

    @Override
    public List<DashBoard> selectDashBoard(IRequest request, DashBoard dashBoard, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<DashBoard> dashBoardDisplays = dashBoardMapper.selectDashBoards(dashBoard);
        return dashBoardDisplays;
    }

    @Override
    public List<UserDashboard> selectMyDashboardConfig(IRequest request,UserDashboard dashboard) {
        return userDashboardMapper.selectMyDashboardConfig(dashboard);
    }

    @Override
    public void updateMyDashboardConfig(IRequest request, List<UserDashboard> dashboards) {
        dashboards.forEach(d->{
            int count = userDashboardMapper.updateByPrimaryKeySelective(d);
            checkOvn(count,d);
        });
        
    }

    @Override
    public void removeDashboard(IRequest request, UserDashboard dashboard) {
        int count  = userDashboardMapper.deleteByPrimaryKey(dashboard);
        checkOvn(count,dashboard);
    }

    @Override
    public UserDashboard insertMyDashboard(IRequest request, UserDashboard dashboard) {
        userDashboardMapper.insert(dashboard);
        return dashboard;
    }

}
