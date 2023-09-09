package com.websocket.rawwebsocket;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.logging.Logger;


/**
 * This class is on <a href="https://github.com/eugenp/tutorials/blob/master/spring-websockets/src/main/java/com/baeldung/rawwebsocket/ServerWebSocketHandler.java">tutorial</a>
 */
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
        logger.info(String.format("server connection closed %s with status %d", session.getId(), status.getCode()));
        sessions.remove(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        Greeting greeting = new Greeting();
        greeting.readValueFromJson(message.getPayload());

        String jsonResponse = greeting.toJsonString();
        session.sendMessage(new TextMessage(jsonResponse));
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
