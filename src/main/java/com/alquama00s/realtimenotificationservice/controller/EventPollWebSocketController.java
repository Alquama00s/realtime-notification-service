package com.alquama00s.realtimenotificationservice.controller;

import com.alquama00s.realtimenotificationservice.DTO.Event;
import com.alquama00s.realtimenotificationservice.eventnotifier.eventconsumer.EventConsumer;
import com.alquama00s.realtimenotificationservice.eventnotifier.eventconsumer.impl.redis.DefaultRedisPubSubEventConsumerFactory;
import com.alquama00s.realtimenotificationservice.eventnotifier.eventconsumer.impl.redis.RedisPubSubEventConsumer;
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

    public static final String uri = "/ws/events/poll";

    @Setter(onMethod_ = @Autowired)
    private DefaultRedisPubSubEventConsumerFactory eventConsumerFactory;

    private HashMap<String,RedisPubSubEventConsumer<String>> consumerCache = new HashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String path = session.getUri().getPath();
        RedisPubSubEventConsumer<String> ec;
        if(path.equals(uri)){
            ec = eventConsumerFactory.build(String.class);
        }else{
            String chan = path.substring(path.lastIndexOf('/')+1);
            log.info("connecting to channel: {}",chan);
            ec = eventConsumerFactory.build(String.class,chan);
        }
        ec.setConsumer(event->{
            try {
                session.sendMessage(new TextMessage(event));
            } catch (IOException e) {
                ErrorHandlingUtils.handleException(e);
            }
        });
        ec.init();
        consumerCache.put(session.getId(),ec);
        super.afterConnectionEstablished(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        if(consumerCache.containsKey(session.getId())){
            consumerCache.get(session.getId()).close();
            consumerCache.remove(session.getId());
        }
        super.afterConnectionClosed(session, status);
    }
}
