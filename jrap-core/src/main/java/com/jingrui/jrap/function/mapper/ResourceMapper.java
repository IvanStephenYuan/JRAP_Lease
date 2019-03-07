package com.jingrui.jrap.function.mapper;

import com.jingrui.jrap.function.dto.Function;
import com.jingrui.jrap.function.dto.Resource;
import com.jingrui.jrap.mybatis.common.Mapper;

import java.util.List;

/**
 * 资源mapper.
 *
 * @author wuyichu
 */
public interface ResourceMapper extends Mapper<Resource> {
    /**
     * 根据资源的url查询资源数据.
     *
     * @param url 资源的路径
     * @return 资源
     */
    Resource selectResourceByUrl(String url);

    /**
     * 查询功能下的资源.
     *
     * @param function 功能
     * @return 资源集合
     */
    List<Resource> selectResourcesByFunction(Function function);
}