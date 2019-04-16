package com.jingrui.jrap.product.mapper;

import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.mybatis.common.Mapper;
import com.jingrui.jrap.product.dto.UnitAssign;
import java.util.List;

public interface UnitAssignMapper extends Mapper<UnitAssign>{


  List<UnitAssign> selectByproductCode(String ProductCode);


}