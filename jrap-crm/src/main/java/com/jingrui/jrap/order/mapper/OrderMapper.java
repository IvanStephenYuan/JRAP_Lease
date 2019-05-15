package com.jingrui.jrap.order.mapper;

import com.jingrui.jrap.mybatis.common.Mapper;
import com.jingrui.jrap.order.dto.Order;
import java.util.List;

public interface OrderMapper extends Mapper<Order> {

  /**
   * 获取订单
   */
  List<Order> selectOrder(Order order);

}