package com.alquama00s.realtimenotificationservice.event.eventconsumer.impl.redis;

import com.alquama00s.realtimenotificationservice.event.InitializationException;
import com.alquama00s.realtimenotificationservice.event.eventproducer.ProducerException;
import com.alquama00s.realtimenotificationservice.event.rediscodecs.JSONCodec;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.codec.RedisCodec;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RedisPubSubEventProducerTest {

    RedisClient client;
    StatefulRedisConnection<String, String> connection;
    RedisCommands<String, String> commands;

    @BeforeEach
    void setup() {
        client = mock(RedisClient.class);
        connection = mock(StatefulRedisConnection.class);
        commands = mock(RedisCommands.class);

        when(client.connect(any(RedisCodec.class))).thenReturn(connection);
        when(connection.sync()).thenReturn(commands);
    }

    private RedisPubSubEventProducer<String> getProducer() throws InitializationException {
        return RedisPubSubEventProducer.<String>builder()
                .client(client)
                .redisCodec(new JSONCodec<>(String.class))
                .channel("chan")
                .build();
    }

    @Test
    void publishDelegatesToCommands() throws InitializationException, ProducerException {
        RedisPubSubEventProducer<String> producer = getProducer();
        // produce should call commands.publish
        producer.produce("hello");
        verify(commands, times(1)).publish("chan", "hello");
    }

    @Test
    void initIsIdempotent() throws InitializationException {
        RedisPubSubEventProducer<String> producer = getProducer();
        // second init should be no-op
        producer.init("chan");
        verify(client, times(1)).connect(any(RedisCodec.class));
    }

    @Test
    void closeIsIdempotent() throws Exception {
        RedisPubSubEventProducer<String> producer = getProducer();
        producer.close();
        producer.close();
        verify(connection, times(1)).close();
    }

    @Test
    void publishAfterCloseThrowsOrNoop() throws Exception {
        RedisPubSubEventProducer<String> producer = getProducer();
        producer.close();
        // depending on implementation, produce may NPE or do nothing; assert that it does not call commands.publish again
        try {
            producer.produce("x");
        } catch (Exception ignored) {}
        verify(commands, never()).publish("chan", "x");
    }

}

