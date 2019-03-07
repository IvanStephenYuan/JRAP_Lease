package com.jingrui.jrap.excel;

import com.jingrui.jrap.core.exception.BaseException;

/**
 * Created by zjl on 2017/1/22.
 */
public class ExcelException extends BaseException {
    public static final String TABLE_NOT_EXIST="table is not existed";

    /**
     * 不应该直接实例化,应该定义子类.
     *
     * @param code           异常 code,通常与模块 CODE 对应
     * @param descriptionKey 异常消息代码,系统描述中定义
     * @param parameters
     */
    public ExcelException(String code, String descriptionKey, Object[] parameters) {
        super(code, descriptionKey, parameters);
    }

    public ExcelException(String descriptionKey) {
        super(null, descriptionKey, null);
    }
}