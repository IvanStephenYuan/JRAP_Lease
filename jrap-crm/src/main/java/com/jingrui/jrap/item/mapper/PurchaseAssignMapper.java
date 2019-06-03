package com.jingrui.jrap.item.mapper;

import com.jingrui.jrap.item.dto.PurchaseAssign;
import com.jingrui.jrap.mybatis.common.Mapper;

import java.util.List;

public interface PurchaseAssignMapper extends Mapper<PurchaseAssign>{

    List<PurchaseAssign> queryPurAssign(PurchaseAssign dto);
}