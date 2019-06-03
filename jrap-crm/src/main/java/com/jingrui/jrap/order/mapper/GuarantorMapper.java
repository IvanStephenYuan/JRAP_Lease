package com.jingrui.jrap.order.mapper;

import com.jingrui.jrap.mybatis.common.Mapper;
import com.jingrui.jrap.order.dto.Guarantor;
import java.util.List;

public interface GuarantorMapper extends Mapper<Guarantor>{
  /**
   *获取担保人信息
   * */
  List<Guarantor> selectguaranter(Guarantor guarantor);

}