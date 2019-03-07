package com.jingrui.jrap.account.mapper;

import com.jingrui.jrap.account.dto.SendRetrieve;
import com.jingrui.jrap.mybatis.common.Mapper;

/**
 * 发送次数限制接口.
 *
 * @author shengyang.zhou@jingrui.com
 */
public interface SendRetrieveMapper extends Mapper<SendRetrieve> {

    int insertRecord(SendRetrieve record);

    int query(SendRetrieve sendRetrieve);
}