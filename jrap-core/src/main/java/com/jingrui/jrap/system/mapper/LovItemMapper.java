package com.jingrui.jrap.system.mapper;

import java.util.List;

import com.jingrui.jrap.system.dto.LovItem;
import com.jingrui.jrap.mybatis.common.Mapper;

/**
 * lov子项 mapper.
 *
 * @author njq.niu@jingrui.com
 * @date 2016/2/1
 */
public interface LovItemMapper extends Mapper<LovItem> {

    /**
     * 根据lovId查询lov子项.
     *
     * @param lovId
     * @return
     */
    List<LovItem> selectByLovId(Long lovId);

    /**
     * 根据lovId删除lov子项.
     *
     * @param lovId
     * @return
     */
    int deleteByLovId(Long lovId);
}