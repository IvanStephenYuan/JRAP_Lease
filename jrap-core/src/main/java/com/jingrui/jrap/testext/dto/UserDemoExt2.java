/*
 * Copyright ZheJiang JingRui Co.,Ltd.
 */

package com.jingrui.jrap.testext.dto;

import java.util.Date;

import org.apache.ibatis.type.JdbcType;

import com.jingrui.jrap.mybatis.annotation.ColumnType;

/**
 * @author shengyang.zhou@jingrui.com
 */
public interface UserDemoExt2 extends UserDemoExt {

    @ColumnType(jdbcType = JdbcType.TIMESTAMP)
    void setEndActiveTime(Date date);

    Date getEndActiveTime();

}
