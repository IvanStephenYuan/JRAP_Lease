package com.jingrui.jrap.item.service.impl;

import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.item.mapper.ItemEvaluateMapper;
import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jingrui.jrap.item.dto.ItemEvaluate;
import com.jingrui.jrap.item.service.IItemEvaluateService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class ItemEvaluateServiceImpl extends BaseServiceImpl<ItemEvaluate> implements
    IItemEvaluateService {

  @Autowired
  ItemEvaluateMapper itemevaluateMapper;
  public List<ItemEvaluate> selectByItemId(Long ItemId,IRequest request,int page, int pageSize)
  {
    return itemevaluateMapper.selectByItemId(ItemId);
  }
}