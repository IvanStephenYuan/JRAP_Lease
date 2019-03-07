package com.jingrui.jrap.system.mapper;

import java.util.List;

import com.jingrui.jrap.mybatis.common.Mapper;
import com.jingrui.jrap.system.dto.DashBoard;

/**
 * 
 * @author zhizheng.yang@jingrui.com
 */
public interface DashBoardMapper extends Mapper<DashBoard> {

    List<DashBoard> selectDashBoards(DashBoard dashBoard);

}
