package com.alquama00s.realtimenotificationservice.configs;

import com.alquama00s.realtimenotificationservice.websockethandlers.DefaultHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

public class WebSocketConfigurations implements WebSocketConfigurer {

    @Autowired
    private DefaultHandler defaultHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(defaultHandler,"/ws")
                .setAllowedOrigins("*");
    }

}
