package com.websocket.rawwebsocket;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.util.HtmlUtils;

import java.io.IOException;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.logging.Logger;

@Component
@EnableScheduling
public class ServerWebSocketHandler extends TextWebSocketHandler implements SubProtocolCapable {

    private static final Logger logger = Logger.getLogger(ServerWebSocketHandler.class.getName());

    private final Set<WebSocketSession> sessions = new CopyOnWriteArraySet<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        logger.info(String.format("server connection opened %s", session.getId()));
        sessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        logger.info(String.format("server connection closed %s", session.getId()));
        sessions.remove(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String request = message.getPayload();
        String response = String.format("response from server to '%s'", HtmlUtils.htmlEscape(request));
        session.sendMessage(new TextMessage(response));
    }

    @Scheduled(fixedRate = 5000)
    void sendPeriodicMessage() throws IOException {
        for(WebSocketSession session : sessions) {
            if(session.isOpen()) {
                String broadcast = String.format("server periodic message %s", LocalTime.now().toString());
                logger.info(String.format("broadcast: %s", broadcast));
                session.sendMessage(new TextMessage(broadcast));
            }
        }
    }

    @Override
    public List<String> getSubProtocols() {
        return Collections.singletonList("subprotocol.demo.websocket");
    }
}
