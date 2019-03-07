package com.jingrui.jrap.function.mapper;

import com.jingrui.jrap.function.dto.FunctionResource;
import com.jingrui.jrap.function.dto.Resource;
import com.jingrui.jrap.mybatis.common.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 功能资源mapper.
 *
 * @author wuyichu
 */
public interface FunctionResourceMapper extends Mapper<FunctionResource> {
    /**
     * 根据资源删除功能资源.
     *
     * @param resource 资源
     * @return int
     */
    int deleteByResource(Resource resource);

    /**
     * 根据功能Id删除功能资源.
     *
     * @param functionId 功能Id
     * @return int
     */
    int deleteByFunctionId(Long functionId);

    /**
     * 根据功能Id和资源Id删除功能资源.
     *
     * @param functionId 功能Id
     * @param resourceId 资源Id
     * @return int
     */
    int deleteFunctionResource(@Param(value = "functionId") Long functionId,
                               @Param(value = "resourceId") Long resourceId);
}