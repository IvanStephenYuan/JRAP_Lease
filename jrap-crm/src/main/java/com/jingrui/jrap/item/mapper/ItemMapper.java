package com.jingrui.jrap.item.mapper;

import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.mybatis.common.Mapper;
import com.jingrui.jrap.item.dto.Item;
import java.util.List;

public interface ItemMapper extends Mapper<Item>{
  List<Item> selectAllItem(Item dto,IRequest request,int page, int pageSize);
}