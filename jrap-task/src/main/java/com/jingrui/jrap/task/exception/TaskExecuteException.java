package com.jingrui.jrap.task.exception;

import com.jingrui.jrap.core.exception.BaseException;

/**
 * 任务执行记录异常.
 *
 * @author lijian.yin@jingrui.com
 * @date 2017-11-13
 **/

public class TaskExecuteException extends BaseException {

    public static final String CODE_EXECUTE_ERROR = "TASK_EXECUTE_FAIL";

    public static final String MSG_SERVER_BUSY = "The server resource is busy. Please try again later";

    /**
     * 不应该直接实例化,应该定义子类.
     *
     * @param code           异常 code,通常与模块 CODE 对应
     * @param descriptionKey 异常消息代码,系统描述中定义
     * @param parameters     显示参数
     */
    protected TaskExecuteException(String code, String descriptionKey, Object[] parameters) {
        super(code, descriptionKey, parameters);
    }

    public TaskExecuteException(String code, String descriptionKey) {
        super(code, descriptionKey, null);
    }
}
