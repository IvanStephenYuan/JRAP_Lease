package com.jingrui.jrap.item.mapper;

import com.jingrui.jrap.item.dto.PurchaseDetail;
import com.jingrui.jrap.mybatis.common.Mapper;

import java.util.List;

public interface PurchaseDetailMapper extends Mapper<PurchaseDetail>{
    /**
     * 查询采购明细(从视图查询)
     * @param purchaseDetail
     * @return
     */
    List<PurchaseDetail> queryPurchaseDetail(PurchaseDetail purchaseDetail);

}