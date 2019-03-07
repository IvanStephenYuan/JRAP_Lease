package com.jingrui.jrap.activiti.exception;

import com.jingrui.jrap.core.exception.BaseException;

/**
 * @author shengyang.zhou@jingrui.com
 */
public class TaskActionException extends BaseException {

    public static final String ERROR_CODE = "WFL_ERROR";

    public static final String DELEGATE_NO_ASSIGNEE = "wfl.error.delegate_without_assignee";
    public static final String DELEGATE_TO_OWNER = "wfl.error.delegate_to_owner";
    public static final String DELEGATE_NEED_OWNER_OR_ADMIN = "wfl.error.delegate_need_owner_or_admin";
    public static final String RESOLVE_NEED_OWNER_OR_ADMIN = "wfl.error.resolve_need_owner_or_admin";
    public static final String UNKNOWN_OPERATOR = "wfl.error.unknown_operator";
    public static final String COMPLETE_TASK_NEED_ASSIGNEE_OR_ADMIN = "wfl.error.complete_task_need_assignee_or_admin";
    public static final String DELEGATE_IN_PENDING = "wfl.error.delegate_in_pending";

    public TaskActionException(String code, String descriptionKey, Object[] parameters) {
        super(code, descriptionKey, parameters);
    }

    public TaskActionException(String descriptionKey) {
        this(ERROR_CODE, descriptionKey, null);
    }
}
