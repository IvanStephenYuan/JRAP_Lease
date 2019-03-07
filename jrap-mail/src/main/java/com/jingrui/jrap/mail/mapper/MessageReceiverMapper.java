package com.jingrui.jrap.mail.mapper;

import com.jingrui.jrap.mail.dto.MessageReceiver;
import com.jingrui.jrap.mybatis.common.Mapper;

import java.util.List;

/**
 * 消息接收者Mapper.
 *
 * @author chuangsheng.zhang
 * @author xiawang.liu@jingrui.com
 */
public interface MessageReceiverMapper extends Mapper<MessageReceiver> {
    /**
     * 根据消息Id删除消息接收者.
     *
     * @param messageId 消息Id
     * @return int
     */
    int deleteByMessageId(Long messageId);

    /**
     * 根据消息Id查询消息接收者.
     *
     * @param messageId 消息Id
     * @return 消息接收者列表
     */
    List<MessageReceiver> selectByMessageId(Long messageId);

    /**
     * 根据消息Id查询消息接收者.
     *
     * @param messageReceiver 消息接收者
     * @return 消息接收者列表
     */
    List<MessageReceiver> selectMessageAddressesByMessageId(MessageReceiver messageReceiver);
}