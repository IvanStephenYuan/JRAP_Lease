package com.jingrui.jrap.task.exception;

import com.jingrui.jrap.core.exception.BaseException;

/**
 * @author peng.jiang@jingrui.com
 * @date 2017/12/6
 **/
public class TaskInvalidException extends BaseException {

    public static final String CODE_TASK_INVALID_ERROR = "TASK_INVALID";

    public static final String MSG_TASK_INVALID = "error.task.invalid";

    public TaskInvalidException(String descriptionKey, Object[] parameters) {
        super(CODE_TASK_INVALID_ERROR, descriptionKey, parameters);
    }

    public TaskInvalidException(String code, String descriptionKey) {
        super(code, descriptionKey, null);
    }
}
