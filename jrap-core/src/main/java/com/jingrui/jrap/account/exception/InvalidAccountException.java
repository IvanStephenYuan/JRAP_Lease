package com.jingrui.jrap.account.exception;

import com.jingrui.jrap.core.exception.BaseException;

/**
 * 账户有效性异常.
 *
 * @author jessen
 * @date 2015/12/30
 */
public class InvalidAccountException extends BaseException {

    private static final long serialVersionUID = -2410004314859717665L;

    public InvalidAccountException(String code) {
        this(code, code, new Object[0]);
    }

    public InvalidAccountException(String code, String descriptionKey, Object[] parameters) {
        super(code, descriptionKey, parameters);
    }
}
