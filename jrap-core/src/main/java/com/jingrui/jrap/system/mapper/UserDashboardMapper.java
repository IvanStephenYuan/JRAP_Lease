package com.jingrui.jrap.system.mapper;

import java.util.List;

import com.jingrui.jrap.mybatis.common.Mapper;
import com.jingrui.jrap.system.dto.UserDashboard;

public interface UserDashboardMapper extends Mapper<UserDashboard> {
    
    List<UserDashboard>  selectMyDashboardConfig(UserDashboard userDashboard);
}
