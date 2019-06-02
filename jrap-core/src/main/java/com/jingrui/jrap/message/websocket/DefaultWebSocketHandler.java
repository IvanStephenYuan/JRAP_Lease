package com.jingrui.jrap.message.websocket;

import com.jingrui.jrap.system.service.IBadgeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.*;

/**
 * @author hailin.xu@jingrui.com
 * @author njq.niu@jingrui.com
 */
public class DefaultWebSocketHandler implements WebSocketHandler {

    private final Logger logger = LoggerFactory.getLogger(DefaultWebSocketHandler.class);

    @Autowired
    private WebSocketSessionManager webSocketSessionManager;

    @Autowired
    private IBadgeService badgeService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        webSocketSessionManager.addSession(session);
        badgeService.initBadgeMessage(session);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if(session.isOpen()){
            session.close();
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        webSocketSessionManager.removeSession(session);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }




}