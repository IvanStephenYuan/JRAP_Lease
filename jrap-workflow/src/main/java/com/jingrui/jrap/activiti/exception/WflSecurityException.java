package com.jingrui.jrap.activiti.exception;

import com.jingrui.jrap.core.exception.BaseException;

/**
 * @author shengyang.zhou@jingrui.com
 */
public class WflSecurityException extends BaseException {
    public static final String ERROR_CODE = "WFL_SECURITY_ERROR";

    public static final String NEED_ASSIGNEE_OR_ADMIN = "wfl.security.error.need_assignee_or_admin";

    public static final String USER_NOT_RELATE_EMP = "wfl.security.error.user_not_relate_emp";

    public WflSecurityException(String code, String descriptionKey, Object[] parameters) {
        super(code, descriptionKey, parameters);
    }

    public WflSecurityException(String descriptionKey) {
        this(ERROR_CODE, descriptionKey, null);
    }
}
