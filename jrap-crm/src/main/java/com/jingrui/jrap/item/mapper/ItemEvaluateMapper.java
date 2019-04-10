package com.jingrui.jrap.item.mapper;

import com.jingrui.jrap.mybatis.common.Mapper;
import com.jingrui.jrap.item.dto.ItemEvaluate;
import java.util.List;

public interface ItemEvaluateMapper extends Mapper<ItemEvaluate>{
  List<ItemEvaluate> selectByItemId(Long ItemId);
}