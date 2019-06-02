package com.jingrui.jrap.order.mapper;

import com.jingrui.jrap.mybatis.common.Mapper;
import com.jingrui.jrap.order.dto.Order;
import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrderMapper extends Mapper<Order> {

  /**
   * 获取订单
   */
  List<Order> selectOrder(Order order);

  /**
   * 获取销售排行
   */
  List<Order> selectleaseAmount(@Param("begintime") String begintime, @Param("endtime") String endtime,
      @Param("empcode") String empcode);

  /**
   * 获取销售额
   */
  List<Order> selectleaseShow(@Param("begintime") String begintime, @Param("endtime") String endtime,
      @Param("empcode") String empcode);

}