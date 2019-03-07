package com.jingrui.jrap.api.logs.components;

import java.util.List;

import com.jingrui.jrap.api.logs.InvokeApiStrategy;
import com.jingrui.jrap.api.logs.dto.ApiInvokeRecord;
import com.jingrui.jrap.api.logs.service.IApiInvokeRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jingrui.jrap.core.IRequest;

/**
 * 默认api调用记录保存策略.
 *
 * @author peng.jiang@jingrui.com
 * @date 2017/9/23.
 */

@Component
public class DefaultInvokeApiStrategy implements InvokeApiStrategy {

    @Autowired
    private IApiInvokeRecordService apiInvokeRecordService;

    @Override
    public void saveApiInvokeRecord(ApiInvokeRecord invokeRecord) {
        apiInvokeRecordService.insertApiInvokeRecord(invokeRecord);
    }

    @Override
    public List<ApiInvokeRecord> queryInvokeRecord(IRequest request, ApiInvokeRecord condition, int pageNum, int pageSize) {
        return apiInvokeRecordService.selectList(request, condition, pageNum, pageSize);
    }

    @Override
    public List<ApiInvokeRecord> selectById(Long recordId) {
           return apiInvokeRecordService.selectById(recordId);
    }
}
