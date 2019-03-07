package com.jingrui.jrap.system.mapper;

import java.util.List;

import com.jingrui.jrap.mybatis.common.Mapper;
import com.jingrui.jrap.system.dto.Code;

/**
 * 快速编码Mapper.
 *
 * @author shengyang.zhou@jingrui.com
 * @date 2016/6/9.
 */
public interface CodeMapper extends Mapper<Code> {

    /**
     * 查询code列表
     * @param code
     * @return list
     */
    List<Code> selectCodes(Code code);

    /**
     * 根据codeName获取Code
     * @param codeName
     * @return
     */
    Code getByCodeName(String codeName);
}