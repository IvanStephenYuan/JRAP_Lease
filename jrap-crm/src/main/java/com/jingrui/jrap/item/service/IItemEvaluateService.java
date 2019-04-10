package com.jingrui.jrap.item.service;

import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.core.ProxySelf;
import com.jingrui.jrap.system.service.IBaseService;
import com.jingrui.jrap.item.dto.ItemEvaluate;
import java.util.List;

public interface IItemEvaluateService extends IBaseService<ItemEvaluate>, ProxySelf<IItemEvaluateService>{
  List<ItemEvaluate> selectByItemId(Long ItemId,IRequest request,int page, int pageSize);
}