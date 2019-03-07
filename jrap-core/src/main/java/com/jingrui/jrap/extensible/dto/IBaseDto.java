/*
 * Copyright ZheJiang JingRui Co.,Ltd.
 */

package com.jingrui.jrap.extensible.dto;

/**
 * @author shengyang.zhou@jingrui.com
 */
public interface IBaseDto {
    String __ID = "__id";
    String __STATUS = "__status";
    String __TLS = "__tls";
    String SORTNAME = "sortname";
    String SORTORDER = "sortorder";

    String _TOKEN = "_token";

    Object getAttribute(String key);

    void setAttribute(String key,Object value);
}
