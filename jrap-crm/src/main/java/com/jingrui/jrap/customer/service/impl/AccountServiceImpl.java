package com.jingrui.jrap.customer.service.impl;

import com.github.pagehelper.PageHelper;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.customer.mapper.AccountMapper;
import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jingrui.jrap.customer.dto.Account;
import com.jingrui.jrap.customer.service.IAccountService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class AccountServiceImpl extends BaseServiceImpl<Account> implements IAccountService{
    @Autowired
    private AccountMapper mapper;

    @Override
    public List<Account> selectAccount(IRequest request, Account account, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return mapper.selectAccount(account);
    }
}