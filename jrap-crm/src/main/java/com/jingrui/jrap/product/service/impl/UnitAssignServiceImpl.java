package com.jingrui.jrap.product.service.impl;

import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.product.mapper.UnitAssignMapper;
import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jingrui.jrap.product.dto.UnitAssign;
import com.jingrui.jrap.product.service.IUnitAssignService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class UnitAssignServiceImpl extends BaseServiceImpl<UnitAssign> implements IUnitAssignService{
  @Autowired
  UnitAssignMapper unitAssignMapper;
  public List<UnitAssign> selectByproductCode(Long ProductCode,IRequest request,int page, int pageSize){
    return unitAssignMapper.selectByproductCode(ProductCode);
  }

}