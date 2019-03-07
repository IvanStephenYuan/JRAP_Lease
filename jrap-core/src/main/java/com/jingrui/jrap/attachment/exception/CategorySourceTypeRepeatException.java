/*
 * #{copyright}#
 */
package com.jingrui.jrap.attachment.exception;

import com.jingrui.jrap.core.exception.BaseException;

/**
 * @author hua.xiao@jingrui.com
 *
 *         2016年3月08日
 */
public class CategorySourceTypeRepeatException extends BaseException {

    private static final long serialVersionUID = 9046687211507280533L;
    
    /**
     * 创建附件分类时，sourceType重复错误.
     */
    private static final String ATTACH_CATEGORY_REPEAT = "msg.warning.dto.attachcategory.sourcetype.repeaterror";

    public CategorySourceTypeRepeatException(String code) {
        super(ATTACH_CATEGORY_REPEAT, ATTACH_CATEGORY_REPEAT, new String[]{code});
    }
}
