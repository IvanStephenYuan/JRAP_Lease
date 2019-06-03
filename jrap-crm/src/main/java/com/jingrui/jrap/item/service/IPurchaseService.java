package com.jingrui.jrap.item.service;

import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.core.ProxySelf;
import com.jingrui.jrap.system.dto.ResponseData;
import com.jingrui.jrap.system.service.IBaseService;
import com.jingrui.jrap.item.dto.Purchase;

import java.util.List;

public interface IPurchaseService extends IBaseService<Purchase>, ProxySelf<IPurchaseService>{

    ResponseData purchaseApply(IRequest iRequest, Purchase dto);
}