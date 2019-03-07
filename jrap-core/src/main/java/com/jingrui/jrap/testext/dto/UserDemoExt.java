/*
 * Copyright ZheJiang JingRui Co.,Ltd.
 */

package com.jingrui.jrap.testext.dto;

import org.apache.ibatis.type.JdbcType;

import com.jingrui.jrap.mybatis.annotation.ColumnType;

/**
 * @author shengyang.zhou@jingrui.com
 */
public interface UserDemoExt {

    @ColumnType(jdbcType = JdbcType.VARCHAR)
    void setUserPhone(String userPhone);

    String getUserPhone();

}
