package com.jingrui.jrap.product.service;

import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.core.ProxySelf;
import com.jingrui.jrap.system.service.IBaseService;
import com.jingrui.jrap.product.dto.UnitAssign;
import java.util.List;

public interface IUnitAssignService extends IBaseService<UnitAssign>, ProxySelf<IUnitAssignService>{
  List<UnitAssign> selectByproductCode(String ProductCode,IRequest request,int page, int pageSize);

}