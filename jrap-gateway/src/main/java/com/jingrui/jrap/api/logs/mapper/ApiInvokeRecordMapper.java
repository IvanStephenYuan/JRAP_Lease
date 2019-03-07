package com.jingrui.jrap.api.logs.mapper;

import com.jingrui.jrap.mybatis.common.Mapper;
import com.jingrui.jrap.api.logs.dto.ApiInvokeRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * ApiInvokeRecordMapper.
 *
 * @author peng.jiang@jingrui.com
 * @date 2017/9/23.
 */
public interface ApiInvokeRecordMapper extends Mapper<ApiInvokeRecord>{

    /**
     * 通过ID获取记录，记录详情
     * @param recordId
     * @return
     */
    List<ApiInvokeRecord> selectById(@Param(value = "recordId") Long recordId);

    /**
     * 查询记录
     * @param apiInvokeRecord
     * @return
     */
    List<ApiInvokeRecord> selectList(ApiInvokeRecord apiInvokeRecord);

}