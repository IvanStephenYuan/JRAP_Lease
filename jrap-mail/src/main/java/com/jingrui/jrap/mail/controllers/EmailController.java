package com.jingrui.jrap.mail.controllers;

import com.jingrui.jrap.mail.dto.Message;
import com.jingrui.jrap.mail.mapper.MessageMapper;
import com.jingrui.jrap.mail.service.IEmailService;
import com.jingrui.jrap.system.controllers.BaseController;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 对邮件的操作.
 *
 * @author zjl on 2016/11/22.
 */
@RestController
public class EmailController extends BaseController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    IEmailService emailService;

    @Autowired
    MessageMapper messageMapper;

    @RequestMapping(value = {"/send_all_email", "/mail/send_all_email", "/api/mail/send_all_email"})
    public boolean sendAllEmail() {
        Map pa = new HashedMap();
        pa.put("batch", 0);
        pa.put("isVipQueue", false);
        boolean result = false;
        try {
            result = emailService.sendMessages(pa);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            return result;
        }
    }

    @RequestMapping(value = {"/mail/resend_email", "/api/mail/resend_email"})
    public boolean reSendEmail(@RequestBody List<Message> messages) {
        boolean result = false;
        List<Message> newMessages = new ArrayList<>();
        for (Message mess : messages) {
            Message s1 = messageMapper.selectByPrimaryKey(mess);
            if (s1 != null && "F".equals(s1.getSendFlag())) {
                newMessages.add(s1);
            }
        }
        try {
            result = emailService.reSendMessages(newMessages, new HashedMap());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            return result;
        }
    }
}
