package com.alquama00s.realtimenotificationservice.controller;

import com.alquama00s.realtimenotificationservice.DTO.Event;
import com.alquama00s.realtimenotificationservice.eventnotifier.eventconsumer.EventConsumer;
import com.alquama00s.realtimenotificationservice.eventnotifier.eventconsumer.impl.redis.DefaultRedisPubSubEventConsumerFactory;
import com.alquama00s.realtimenotificationservice.utils.ErrorHandlingUtils;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;

@Slf4j
@Component
public class EventPollWebSocketController extends TextWebSocketHandler {

    @Setter(onMethod_ = @Autowired)
    private DefaultRedisPubSubEventConsumerFactory eventConsumerFactory;


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        var ec = eventConsumerFactory.build(String.class);
        ec.setConsumer(event->{
            try {
                session.sendMessage(new TextMessage(event));
            } catch (IOException e) {
                ErrorHandlingUtils.handleException(e);
            }
        });
        ec.init();
        super.afterConnectionEstablished(session);
    }
}
