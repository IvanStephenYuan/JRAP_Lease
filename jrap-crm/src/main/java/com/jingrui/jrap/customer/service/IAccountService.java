package com.jingrui.jrap.customer.service;

import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.core.ProxySelf;
import com.jingrui.jrap.system.service.IBaseService;
import com.jingrui.jrap.customer.dto.Account;

import java.util.List;

public interface IAccountService extends IBaseService<Account>, ProxySelf<IAccountService>{
    /**
     * 重新查询方法
     * @param request
     * @param account
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<Account> selectAccount(IRequest request, Account account, int pageNum, int pageSize);
}