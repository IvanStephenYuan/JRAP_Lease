package com.jingrui.jrap.activiti.mapper;

import com.jingrui.jrap.activiti.dto.ApproveChainLine;
import com.jingrui.jrap.mybatis.common.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ApproveChainLineMapper extends Mapper<ApproveChainLine> {

    List<ApproveChainLine> selectByHeaderId(Long headerId);

    List<ApproveChainLine> selectByNodeId(@Param("processKey") String processKey, @Param("nodeId") String nodeId);
}