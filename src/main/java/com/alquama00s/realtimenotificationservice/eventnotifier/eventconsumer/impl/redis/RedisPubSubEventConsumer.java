package com.alquama00s.realtimenotificationservice.eventnotifier.eventconsumer.impl.redis;

import com.alquama00s.realtimenotificationservice.eventnotifier.InitializationException;
import com.alquama00s.realtimenotificationservice.eventnotifier.builders.RedisPubSubNotifierBuilder;
import com.alquama00s.realtimenotificationservice.eventnotifier.eventconsumer.EventConsumer;
import com.alquama00s.realtimenotificationservice.eventnotifier.rediscodecs.JSONCodec;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.codec.RedisCodec;

import java.io.IOException;
import java.util.function.Consumer;

public class RedisPubSubEventConsumer<T> implements EventConsumer<T> {

    private final RedisClient client;
    private StatefulRedisConnection<String,T> connection;
    private RedisCommands<String,T> commands;
    private final RedisCodec<String,T> redisCodec;
    private boolean initialized = false;
    private final String channel;

    public RedisPubSubEventConsumer(String channel, RedisCodec<String, T> redisCodec, RedisClient client) {
        this.client = client;
        this.redisCodec = redisCodec;
        this.channel = channel;
    }

    public static <T> RedisPubSubNotifierBuilder<T> builder(){
        return new RedisPubSubNotifierBuilder<>();
    }

    @Override
    public void setConsumer(Consumer<T> consumer) {

    }

    @Override
    public void init(String consumerGroupId, String channel) throws InitializationException {

    }

    @Override
    public void close() throws IOException {

    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public T next() {
        return null;
    }

}
