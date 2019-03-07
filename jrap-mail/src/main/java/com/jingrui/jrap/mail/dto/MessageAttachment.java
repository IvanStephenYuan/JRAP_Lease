/*
 * #{copyright}#
 */
package com.jingrui.jrap.mail.dto;

import com.jingrui.jrap.system.dto.BaseDTO;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 消息附件.
 *
 * @author njq.niu@jingrui.com
 * @author 2016年3月2日
 */
@Table(name = "SYS_MESSAGE_ATTACHMENT")
public class MessageAttachment extends BaseDTO {

    private static final long serialVersionUID = -8831715672578562022L;

    @Id
    @GeneratedValue(generator = GENERATOR_TYPE)
    private Long attachmentId;

    private Long fileId;

    private Long messageId;

    public Long getAttachmentId() {
        return attachmentId;
    }

    public Long getFileId() {
        return fileId;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setAttachmentId(Long attachmentId) {
        this.attachmentId = attachmentId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

}