/*
 * #{copyright}#
 */
package com.jingrui.jrap.attachment.exception;

import com.jingrui.jrap.core.exception.BaseException;

/**
 * @author hua.xiao@jingrui.com
 *
 *         2016年3月03日
 */
public class FileReadIOException extends BaseException {

    private static final long serialVersionUID = 9046687211507280533L;
    
    /**
     * 发生了IO异常.
     */
    private static final String ATTACH_FILE_IO_ERROR = "msg.error.system.attach.file_io_error";

    public FileReadIOException() {
        super(ATTACH_FILE_IO_ERROR, ATTACH_FILE_IO_ERROR, new Object[0]);
    }
}
