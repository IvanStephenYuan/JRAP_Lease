package com.jingrui.jrap.attachment.exception;

import com.jingrui.jrap.core.exception.BaseException;

/**
 * @author njq.niu@jingrui.com
 */
public class AttachmentException extends BaseException {

    public AttachmentException(String code, String descriptionKey, Object[] parameters) {
        super(code, descriptionKey, parameters);
    }
}
