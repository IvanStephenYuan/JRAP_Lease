package com.jingrui.jrap.customer.mapper;

import com.jingrui.jrap.mybatis.common.Mapper;
import com.jingrui.jrap.customer.dto.Account;

import java.util.List;

public interface AccountMapper extends Mapper<Account>{
    /**
     * 重新查询语句
     * @param account
     * @return
     */
    List<Account> selectAccount(Account account);
}