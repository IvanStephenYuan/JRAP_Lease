package com.jingrui.jrap.message.websocket;

import com.jingrui.jrap.message.IMessageConsumer;
import com.jingrui.jrap.message.TopicMonitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;

/**
 * Created by xuhailin on 2017/7/18.
 */
@TopicMonitor(channel = {BadgeManager.CHANNEL_BADGE})
public class BadgeManager implements IMessageConsumer<CommandMessage> {

    public static final String CHANNEL_BADGE = "channel_badge";

    @Autowired
    WebSocketSessionManager webSocketSessionManager;


    @Override
    public void onMessage(CommandMessage commandMessage, String channel) {
        List<WebSocketSession> sessions = webSocketSessionManager.getSession(commandMessage.getUserName());
        sessions.stream().forEach(webSocketSession -> {
            webSocketSessionManager.sendCommandMessage(webSocketSession, commandMessage);
        });
    }


}
