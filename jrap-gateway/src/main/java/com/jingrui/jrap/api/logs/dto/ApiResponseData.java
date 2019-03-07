package com.jingrui.jrap.api.logs.dto;

import com.jingrui.jrap.system.dto.ResponseData;

/**
 * api调用返回data DTO.
 *
 * @author peng.jiang@jingrui.com
 * @date2017/10/30.
 */
public class ApiResponseData extends ResponseData {

    private String requestId;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}
