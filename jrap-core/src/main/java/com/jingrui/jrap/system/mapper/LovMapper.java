package com.jingrui.jrap.system.mapper;

import com.jingrui.jrap.system.dto.Lov;

import java.util.List;

import com.jingrui.jrap.mybatis.common.Mapper;

/**
 * lov mapper.
 *
 * @author njq.niu@jingrui.com
 * @date 2016/2/1
 */
public interface LovMapper extends Mapper<Lov> {

    /**
     * 根据Lov代码查询lov.
     *
     * @param code
     * @return
     */
    Lov selectByCode(String code);

    /**
     * 条件查询lov.
     *
     * @param lov
     * @return
     */
    List<Lov> selectLovs(Lov lov);

}