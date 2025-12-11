package com.alquama00s.realtimenotificationservice.websockethandlers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;


@Component
public class DefaultHandler extends TextWebSocketHandler {

    private static final Logger log = LoggerFactory.getLogger(DefaultHandler.class);

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("connection established: "+session.getId());
        session.sendMessage(new TextMessage("start"));

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("connection closed: "+session.getId());

    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.info("msg recived: "+message.getPayload());

        session.sendMessage(new TextMessage(message.getPayload()));
    }
}