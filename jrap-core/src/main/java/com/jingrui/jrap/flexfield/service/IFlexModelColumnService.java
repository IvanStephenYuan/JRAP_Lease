package com.jingrui.jrap.flexfield.service;

import com.jingrui.jrap.core.ProxySelf;
import com.jingrui.jrap.system.dto.ResponseData;
import com.jingrui.jrap.system.service.IBaseService;
import com.jingrui.jrap.flexfield.dto.FlexModelColumn;

import java.util.List;

public interface IFlexModelColumnService extends IBaseService<FlexModelColumn>, ProxySelf<IFlexModelColumnService> {

    /** 通过表名获取表中列
     * @param tableName 表名
     * @return 表所包含的列
     */
    List<String> getTableColumn(String tableName);

}