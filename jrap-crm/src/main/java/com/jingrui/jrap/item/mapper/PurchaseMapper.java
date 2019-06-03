package com.jingrui.jrap.item.mapper;

import com.jingrui.jrap.item.dto.Purchase;
import com.jingrui.jrap.mybatis.common.Mapper;

import java.util.List;

public interface PurchaseMapper extends Mapper<Purchase>{

    /**
     * 查询采购信息
     * @param purchase
     * @return
     */
    List<Purchase> queryPurchase(Purchase purchase);

}