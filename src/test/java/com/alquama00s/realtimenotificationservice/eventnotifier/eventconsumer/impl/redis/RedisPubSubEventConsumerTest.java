package com.alquama00s.realtimenotificationservice.eventnotifier.eventconsumer.impl.redis;

import io.lettuce.core.RedisClient;
import io.lettuce.core.codec.RedisCodec;
import io.lettuce.core.pubsub.RedisPubSubListener;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import io.lettuce.core.pubsub.api.sync.RedisPubSubCommands;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RedisPubSubEventConsumerTest {

    RedisClient client;
    StatefulRedisPubSubConnection<String,String> connection;
    RedisPubSubCommands<String,String> commands;

    @BeforeEach
    void setUp(){
        client=mock(RedisClient.class);
        connection=mock(StatefulRedisPubSubConnection.class);
        commands=mock(RedisPubSubCommands.class);
        when(client.connectPubSub(any(RedisCodec.class))).thenReturn(connection);
        when(connection.sync()).thenReturn(commands);
    }


    private RedisPubSubEventConsumer<String> createConsumer(){
        return (RedisPubSubEventConsumer<String>) RedisPubSubEventConsumer.<String>builder()
                .client(client)
                .channel("chan")
                .clazz(String.class)
                .build();
    }


    @Test
    void testConsumer(){
        String[] msgArray = new String[]{
                "message 1",
                "two",
                "three"
        };
        Set<String> consumedMsg = new HashSet<>();
        var consumer = createConsumer();
        ArgumentCaptor<RedisPubSubListener<String,String>> listenerCaptor = ArgumentCaptor.forClass(RedisPubSubListener.class);
        consumer.setConsumer(consumedMsg::add);
        consumer.init();
        verify(connection).addListener(listenerCaptor.capture());
        var listener = listenerCaptor.getValue();
        for(var msg:msgArray){
            listener.message("chan",msg);
        }
        for(var msg:msgArray){
            assertTrue(consumedMsg.contains(msg));
        }
    }

//    @Test
//    void nextBlocksWhenQueueIsEmpty() {
//        RedisPubSubEventConsumer<String> consumer = createConsumer();
//
//        ExecutorService executor = Executors.newSingleThreadExecutor();
//
//        Future<String> future = executor.submit(consumer::next);
//
//        assertThrows(
//                TimeoutException.class,
//                () -> future.get(200, TimeUnit.MILLISECONDS)
//        );
//
//        executor.shutdownNow();
//    }



}
