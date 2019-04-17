package com.jingrui.jrap.product.mapper;

import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.mybatis.common.Mapper;
import com.jingrui.jrap.product.dto.UnitAssign;

import java.util.List;

public interface UnitAssignMapper extends Mapper<UnitAssign> {
    /**
     * 获取分配信息
     * @param ProductCode
     * @return
     */
    List<UnitAssign> selectByproductCode(String ProductCode);
}