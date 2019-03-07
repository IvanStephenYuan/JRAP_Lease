package com.jingrui.jrap.system.service;

import java.util.List;

import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.core.ProxySelf;
import com.jingrui.jrap.core.annotation.StdWho;
import com.jingrui.jrap.system.dto.DashBoard;
import com.jingrui.jrap.system.dto.UserDashboard;

/**
 * @author zhizheng.yang@jingrui.com
 */
public interface IDashBoardService extends IBaseService<DashBoard>, ProxySelf<IDashBoardService> {

    /**
     * 仪表盘多功能查询
     * @param request
     * @param function
     * @param page
     * @param pageSize
     * @return
     */
    List<DashBoard> selectDashBoard(IRequest request, DashBoard dashBoard, int page, int pageSize);
	
	/**
	 * 查询我的仪表盘配置.
	 * @param request
	 * @return
	 */
	List<UserDashboard> selectMyDashboardConfig(IRequest request,UserDashboard dashboard);
	
	/**
	 * 更新仪表盘顺序.
	 * 
	 * @param request
	 * @param dashboards
	 */
	void updateMyDashboardConfig(IRequest request,@StdWho List<UserDashboard> dashboards);
	
	/**
     * 增加仪表盘.
     * 
     * @param request
     * @param dashboard
     */
	UserDashboard insertMyDashboard(IRequest request,@StdWho UserDashboard dashboard);
	
	/**
	 * 删除某个仪表盘.
	 * 
	 * @param request
	 * @param dashboard
	 */
	void removeDashboard(IRequest request, UserDashboard dashboard);
}
