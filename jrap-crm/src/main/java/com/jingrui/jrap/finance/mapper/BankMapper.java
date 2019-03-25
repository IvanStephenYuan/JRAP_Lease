package com.jingrui.jrap.finance.mapper;

import com.jingrui.jrap.mybatis.common.Mapper;
import com.jingrui.jrap.finance.dto.Bank;
import java.util.List;

public interface BankMapper extends Mapper<Bank>{
    /**
     * 查询开户行
     * @param bank
     * @return
     */
    List<Bank> selectBank(Bank bank);
}