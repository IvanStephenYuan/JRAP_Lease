package com.jingrui.jrap.activiti.mapper;

import com.jingrui.jrap.activiti.dto.ApproveChainHeader;
import com.jingrui.jrap.mybatis.common.Mapper;
import org.apache.ibatis.annotations.Param;

public interface ApproveChainHeaderMapper extends Mapper<ApproveChainHeader> {

    ApproveChainHeader selectByUserTask(@Param("processKey") String processKey,
                                        @Param("usertaskId") String usertaskId);
}