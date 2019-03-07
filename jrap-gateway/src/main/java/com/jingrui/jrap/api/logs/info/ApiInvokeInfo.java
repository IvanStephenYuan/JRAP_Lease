package com.jingrui.jrap.api.logs.info;

import com.jingrui.jrap.api.logs.dto.ApiInvokeRecord;

/**
 * @author peng.jiang@jingrui.com
 * @date 2017/10/16.
 */
public class ApiInvokeInfo {

    public static final String RESPONSE_SUCESS = "success";

    public static final String RESPONSE_FAILURE = "failure";

    public static final ThreadLocal<ApiInvokeRecord> API_INVOKE_RECORD = new ThreadLocal<>();

    public static final ThreadLocal<Long> API_START_TIME = new ThreadLocal<>();

    public static void clear(){
        API_INVOKE_RECORD.remove();
        API_START_TIME.remove();
    }


}
